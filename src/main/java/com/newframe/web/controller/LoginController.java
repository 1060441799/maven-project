package com.newframe.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Constants;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.newframe.core.config.AppConfig;
import com.newframe.core.constant.Globals;
import com.newframe.core.extend.datasource.DataSourceContextHolder;
import com.newframe.core.extend.datasource.DataSourceType;
import com.newframe.core.manager.ClientManager;
import com.newframe.core.pojo.pojoimpl.impl.*;
import com.newframe.core.util.ContextHolderUtils;
import com.newframe.core.util.IpUtil;
import com.newframe.core.util.NumberComparator;
import com.newframe.core.util.ResourceUtil;
import com.newframe.core.vo.Client;
import com.newframe.core.web.base.BaseController;
import com.newframe.core.web.model.json.AjaxJson;
import com.newframe.web.model.SignupUserFacade;
import com.newframe.web.model.SystemConfigMap;
import com.newframe.web.model.TerritoryFacade;
import com.newframe.web.service.SystemService;
import com.newframe.web.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.Map.Entry;

@Controller
@RequestMapping(value = "/login/")
public class LoginController extends BaseController {

	private static final Logger log = Logger.getLogger(LoginController.class);

	@Autowired
	private AppConfig appConfig;
	private SystemService systemService;
	private UserService userService;

	private String message = null;

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "denied")
	public ModelAndView denied() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("common/denied");
		return mv;
	}
	
	@RequestMapping(value = "test")
	public ModelAndView test() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		return mv;
	}

	@RequestMapping(value = "signup")
	public ModelAndView signup() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("APPVERSION", appConfig.getAppVersion());
		mv.addObject("APPNAME", appConfig.getAppName());
		// department
		List<Depart> departments = systemService.loadAll(Depart.class);
		mv.addObject("departments", departments);
		// territories
		List<Territory> territories = userService.getTerritoriesByParentId("1");
		mv.addObject("territories", territories);

		mv.setViewName("signup/signup");
		return mv;
	}

	@RequestMapping(value = "getTerritory")
	@ResponseBody
	public AjaxJson getTerritory(@RequestParam String id, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		List<Territory> territories = userService.getTerritoriesByParentId(id);
		List<TerritoryFacade> territoryFacades = Lists.newArrayList();
		System.out.println("territories=" + territories.size());
		TerritoryFacade tf = null;
		for (Territory t : territories) {
			tf = new TerritoryFacade();
			tf.setName(t.getTerritoryName());
			tf.setId(t.getId());
			territoryFacades.add(tf);
		}
		j.setObj(territoryFacades);
		return j;
	}

	@RequestMapping(value = "index")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse res) {
		DataSourceContextHolder.setDataSourceType(DataSourceType.dataSource_main);
		User user = ResourceUtil.getSessionUser();
		ModelAndView mv = new ModelAndView();
		mv.addObject("APPVERSION", appConfig.getAppVersion());
		mv.addObject("APPNAME", appConfig.getAppName());
		String roles = "";
		if (user != null) {
			System.out.println("user:" + user.getId());
			List<RelRoleUser> rUsers = systemService.findByProperty(RelRoleUser.class, "user.id", user.getId());
			for (RelRoleUser ru : rUsers) {
				Role role = ru.getRole();
				roles += role.getRoleName() + ",";
			}
			if (roles.length() > 0) {
				roles = roles.substring(0, roles.length() - 1);
			}
			mv.addObject("roleName", roles);
			mv.addObject("userName", user.getUserName());
			mv.addObject("signatureFile", user.getSignatureFile());
			request.getSession().setAttribute("CKFinder_UserRole", "admin");
			mv.setViewName("index");
		} else {
			mv.setViewName("signin/signin");
		}
		return mv;
	}

	@RequestMapping(value = "checkuser")
	@ResponseBody
	public AjaxJson checkuser(User user, HttpServletRequest req) {
		HttpSession session = ContextHolderUtils.getSession();
		DataSourceContextHolder.setDataSourceType(DataSourceType.dataSource_main);
		AjaxJson j = new AjaxJson();
		String randCode = req.getParameter("randCode");
		String sessionCode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if (StringUtils.isEmpty(randCode)) {
			j.setMsgType(3);
			j.setMsg("请输入验证码");
			j.setSuccess(false);
		} else if (!randCode.equalsIgnoreCase(sessionCode)) {
			j.setMsgType(3);
			j.setMsg("验证码错误！");
			j.setSuccess(false);
		} else {
			int users = userService.getList(User.class).size();
			if (users == 0) {
				j.setMsg("a");
				j.setSuccess(false);
			} else {
				User u6 = userService.checkUserByName(user);
				if (u6 == null) {
					j.setMsg("用户名不存在!");
					j.setMsgType(1);
					j.setSuccess(false);
					return j;
				}
				User u = userService.checkUserExits(user);
				if (u6 != null && u == null) {
					j.setMsg("密码错误!");
					j.setMsgType(2);
					j.setSuccess(false);
					return j;
				}
				User u2 = userService.getEntity(User.class, u.getId());
				if (u != null && "0".equalsIgnoreCase(u2.getStatus())) {
					message = "用户: " + user.getUserName() + "登录成功";
					Client client = new Client();
					client.setIp(IpUtil.getIpAddr(req));
					client.setLogindatetime(new Date());
					client.setUser(u);
					ClientManager.getInstance().addClinet(session.getId(), client);
					// 添加登陆日志
					systemService.addLog(message, Globals.Log_Type_LOGIN, Globals.Log_Leavel_INFO);
					j.setMsg("登录成功!");
					j.setMsgType(5);
					j.setSuccess(true);
				} else {
					j.setMsg("用户状态无效!");
					j.setMsgType(4);
					j.setSuccess(false);
				}
			}
		}
		return j;
	}

	@RequestMapping(value = "checkUserName")
	@ResponseBody
	public AjaxJson checkUserName(@RequestParam String userName) {
		AjaxJson j = new AjaxJson();
		User user = new User();
		user.setUserName(userName);
		User u6 = userService.checkUserByName(user);
		if (u6 != null) {
			j.setMsg("用户名已存在");
			j.setMsgType(1);
			j.setSuccess(false);
		} else {
			j.setMsg("");
			j.setMsgType(1);
			j.setSuccess(true);
		}
		return j;
	}

	@RequestMapping(value = "checkRandCode")
	@ResponseBody
	public AjaxJson checkRandCode(@RequestParam String randCode) {
		AjaxJson j = new AjaxJson();
		HttpSession session = ContextHolderUtils.getSession();
		String sessionCode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
		System.out.println("sessionCode = " + sessionCode);
		if (!randCode.equalsIgnoreCase(sessionCode)) {
			j.setMsgType(3);
			j.setMsg("验证码错误");
			j.setSuccess(false);
		} else {
			j.setMsg("");
			j.setMsgType(1);
			j.setSuccess(true);
		}
		return j;
	}

	/**
	 * 获取权限的map
	 *
	 * @param user
	 * @return
	 * @throws Exception
	 */
	private Map<Integer, List<Function>> getFunctionMap(User user) {
		Map<Integer, List<Function>> functionMap = Maps.newHashMap();
		Map<String, Function> loginActionlist = getUserFunction(user);
		if (loginActionlist == null) {
			return null;
		}
		if (loginActionlist.size() > 0) {
			Collection<Function> allFunctions = loginActionlist.values();
			for (Function function : allFunctions) {
				if (!functionMap.containsKey(function.getFunctionLevel() + 0)) {
					functionMap.put(function.getFunctionLevel() + 0, new ArrayList<Function>());
				}
				functionMap.get(function.getFunctionLevel() + 0).add(function);
			}
			// 菜单栏排序
			Collection<List<Function>> c = functionMap.values();
			for (List<Function> list : c) {
				Collections.sort(list, new NumberComparator());
			}
		}
		return functionMap;
	}

	@RequestMapping(value = "left")
	public void getLeftNav(HttpServletResponse res) throws IOException{
		User user = ResourceUtil.getSessionUser();
		HttpSession session = ContextHolderUtils.getSession();
		// 登陆者的权限
		PrintWriter pw;
		pw = res.getWriter();
		AjaxJson j = new AjaxJson();
		if (user == null) {
			session.removeAttribute(Globals.USER_SESSION);
			try {
				res.sendRedirect("login/main");
			} catch (IOException e) {
				e.printStackTrace();
			}
			j.setMsg("Fail");
			j.setSuccess(false);
		} else {
			List<SystemConfig> configs = userService.loadAll(SystemConfig.class);
			Map<String, String> map = Maps.newHashMap();
			for (SystemConfig tsConfig : configs) {
				map.put(tsConfig.getCode(), tsConfig.getContents());
			}
			SystemConfigMap tsConfigMap = new SystemConfigMap();
			tsConfigMap.setConfigs(map);
			Map<Integer, List<Function>> mMap = getFunctionMap(user);
			Map<String, List<Function>> mmMap = new HashMap();
			Set<Entry<Integer, List<Function>>> set = mMap.entrySet();
			Iterator<Entry<Integer, List<Function>>> iterator = set.iterator();
			while (iterator.hasNext()) {
				Entry<Integer, List<Function>> e = iterator.next();
				List<Function> list = e.getValue();
				mmMap.put(e.getKey() + "", list);
			}
			tsConfigMap.setMap(mmMap);
			j.setMsg("Success");
			j.setSuccess(true);
			j.setObj(tsConfigMap);
		}
		pw.write(JSONObject.toJSONString(j));
	}

	/**
	 * 获取用户菜单列表
	 *
	 * @param user
	 * @return
	 * @throws Exception
	 */
	private Map<String, Function> getUserFunction(User user) {
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		if (client == null) {
			return null;
		}
		if (client.getFunctions() == null || client.getFunctions().size() == 0) {

			Map<String, Function> loginActionlist = Maps.newHashMap();

			List<RelRoleUser> rUsers = systemService.findByProperty(RelRoleUser.class, "user.id", user.getId());

			for (RelRoleUser ru : rUsers) {
				Role role = ru.getRole();
				List<RelRoleFunction> roleFunctionList = systemService.findByProperty(RelRoleFunction.class, "role.id",
						role.getId());
				for (RelRoleFunction roleFunction : roleFunctionList) {
					Function function = roleFunction.getFunction();
					Hibernate.initialize(function.getFunctions());
					Hibernate.initialize(function.getParentFunction());
					loginActionlist.put(function.getId(), function);
				}
			}

			client.setFunctions(loginActionlist);
		}
		return client.getFunctions();
	}

	@RequestMapping(value = "logout")
	@ResponseBody
	public AjaxJson logout(HttpServletRequest request) {
		HttpSession session = ContextHolderUtils.getSession();
		User user = ResourceUtil.getSessionUser();
		AjaxJson re = new AjaxJson();
		systemService.addLog("用户" + user.getUserName() + "已退出", Globals.Log_Type_EXIT, Globals.Log_Leavel_INFO);
		ClientManager.getInstance().removeClinet(session.getId());
		re.setMsg("Logout success.");
		re.setSuccess(true);
		return re;
	}

	@RequestMapping(value = "saveUser")
	@ResponseBody
	public AjaxJson saveUser(@RequestBody SignupUserFacade user, HttpServletRequest req) throws Exception {
		AjaxJson j = new AjaxJson();
		if (user == null) {
			return j;
		}
		log.info("user=" + user);
		User u = userService.saveUser(user, req);
		if (u != null) {

		}
		// String randCode = req.getParameter("randCode");
		// if (StringUtils.isEmpty(randCode)) {
		// j.setMsgType(3);
		// j.setMsg("请输入验证码");
		// j.setSuccess(false);
		// } else if (!randCode.equalsIgnoreCase(String.valueOf(session
		// .getAttribute("code")))) {
		// j.setMsgType(3);
		// j.setMsg("验证码错误！");
		// j.setSuccess(false);
		// } else {
		// User u6 = userService.checkUserByName(user);
		// if (u6 != null) {
		// j.setMsg("用户名已经存在!");
		// j.setMsgType(1);
		// j.setSuccess(false);
		// return j;
		// } else {
		// user.setBrowser(BrowserUtils.checkBrowse(req));
		// if (userService.saveUser(user) != null) {
		// j.setMsg("注册成功!");
		// j.setMsgType(4);
		// j.setSuccess(true);
		// }
		// }
		// }
		return j;
	}
}
