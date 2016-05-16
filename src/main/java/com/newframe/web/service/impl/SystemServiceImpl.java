package com.newframe.web.service.impl;

import com.newframe.core.pojo.pojoimpl.impl.Function;
import com.newframe.core.pojo.pojoimpl.impl.Role;
import com.newframe.core.service.impl.CommonServiceImpl;
import com.newframe.core.util.StringUtil;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.newframe.core.pojo.pojoimpl.impl.RelRoleUser;
import com.newframe.core.pojo.pojoimpl.impl.Typegroup;
import com.newframe.core.util.oConvertUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newframe.core.vo.DictEntity;
import com.newframe.core.pojo.pojoimpl.impl.Icon;
import com.newframe.core.pojo.pojoimpl.impl.Log;
import com.newframe.core.pojo.pojoimpl.impl.RelRoleFunction;
import com.newframe.core.pojo.pojoimpl.impl.Type;
import com.newframe.core.pojo.pojoimpl.impl.User;
import com.newframe.core.hibernate.qbc.CriteriaQuery;
import com.newframe.core.util.BrowserUtils;
import com.newframe.core.util.ContextHolderUtils;
import com.newframe.core.util.ResourceUtil;
import com.newframe.web.service.SystemService;

@Service("systemService")
@Transactional
public class SystemServiceImpl extends CommonServiceImpl implements SystemService {
	public User checkUserExits(User user) throws Exception {
		return this.commonDao.getUserByUserIdAndUserNameExits(user);
	}

	/**
	 * 添加日志
	 */
	public void addLog(String logcontent, Short loglevel, Short operatetype) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String broswer = BrowserUtils.checkBrowse(request);
		Log log = new Log();
		log.setLogcontent(logcontent);
		log.setLoglevel(loglevel);
		log.setOrderNum(0);
		log.setOperatetype(operatetype);
		log.setNote(oConvertUtils.getIp());
		log.setBroswer(broswer);
		log.setCreatedOn(new Date());
		log.setModifiedOn(new Date());
		log.setModifiedBy("arrym");
		log.setCreatedBy("arrym");
		log.setUserId(ResourceUtil.getSessionUserId());
		commonDao.save(log);
	}

	/**
	 * 根据类型编码和类型名称获取Type,如果为空则创建一个
	 * 
	 * @param typecode
	 * @param typename
	 * @return
	 */
	public Type getType(String typecode, String typename, Typegroup tsTypegroup) {
		Type actType = commonDao.findUniqueByProperty(Type.class, "typecode", typecode);
		if (actType == null) {
			actType = new Type();
			actType.setTypecode(typecode);
			actType.setTypename(typename);
			actType.setTypegroup(tsTypegroup);
			commonDao.save(actType);
		}
		return actType;

	}

	/**
	 * 根据类型分组编码和名称获取TypeGroup,如果为空则创建一个
	 * 
	 * @param typecode
	 * @param typename
	 * @return
	 */
	public Typegroup getTypeGroup(String typegroupcode, String typgroupename) {
		Typegroup tsTypegroup = commonDao.findUniqueByProperty(Typegroup.class, "typegroupcode", typegroupcode);
		if (tsTypegroup == null) {
			tsTypegroup = new Typegroup();
			tsTypegroup.setTypegroupcode(typegroupcode);
			tsTypegroup.setTypegroupname(typgroupename);
			commonDao.save(tsTypegroup);
		}
		return tsTypegroup;
	}

	public Typegroup getTypeGroupByCode(String typegroupCode) {
		Typegroup tsTypegroup = commonDao.findUniqueByProperty(Typegroup.class, "typegroupcode", typegroupCode);
		return tsTypegroup;
	}

	public void initAllTypeGroups() {
		List<Typegroup> typeGroups = this.commonDao.loadAll(Typegroup.class);
		for (Typegroup tsTypegroup : typeGroups) {
			Typegroup.allTypeGroups.put(tsTypegroup.getTypegroupcode().toLowerCase(), tsTypegroup);
			List<Type> types = this.commonDao.findByProperty(Type.class, "typegroup.id", tsTypegroup.getId());
			Typegroup.allTypes.put(tsTypegroup.getTypegroupcode().toLowerCase(), types);
		}
	}

	public void refleshTypesCach(Type type) {
		Typegroup tsTypegroup = type.getTypegroup();
		Typegroup typeGroupEntity = this.commonDao.get(Typegroup.class, tsTypegroup.getId());
		List<Type> types = this.commonDao.findByProperty(Type.class, "typegroup.id", tsTypegroup.getId());
		Typegroup.allTypes.put(typeGroupEntity.getTypegroupcode().toLowerCase(), types);
	}

	public void refleshTypeGroupCach() {
		Typegroup.allTypeGroups.clear();
		List<Typegroup> typeGroups = this.commonDao.loadAll(Typegroup.class);
		for (Typegroup tsTypegroup : typeGroups) {
			Typegroup.allTypeGroups.put(tsTypegroup.getTypegroupcode().toLowerCase(), tsTypegroup);
		}
	}

	public Set<String> getOperationCodesByRoleIdAndFunctionId(String roleId, String functionId) {
		Set<String> operationCodes = new HashSet<String>();
		Role role = commonDao.get(Role.class, roleId);
		CriteriaQuery cq1 = new CriteriaQuery(RelRoleFunction.class);
		cq1.eq("Role.id", role.getId());
		cq1.eq("Function.id", functionId);
		cq1.add();
		List<RelRoleFunction> rFunctions = getListByCriteriaQuery(cq1, false);
		if (null != rFunctions && rFunctions.size() > 0) {
			RelRoleFunction tsRoleFunction = rFunctions.get(0);
			if (null != tsRoleFunction.getOperation()) {
				String[] operationArry = tsRoleFunction.getOperation().split(",");
				for (int i = 0; i < operationArry.length; i++) {
					operationCodes.add(operationArry[i]);
				}
			}
		}
		return operationCodes;
	}

	public Set<String> getOperationCodesByUserIdAndFunctionId(String userId, String functionId) {
		Set<String> operationCodes = new HashSet<String>();
		List<RelRoleUser> rUsers = findByProperty(RelRoleUser.class, "User.id", userId);
		for (RelRoleUser ru : rUsers) {
			Role role = ru.getRole();
			CriteriaQuery cq1 = new CriteriaQuery(RelRoleFunction.class);
			cq1.eq("Role.id", role.getId());
			cq1.eq("Function.id", functionId);
			cq1.add();
			List<RelRoleFunction> rFunctions = getListByCriteriaQuery(cq1, false);
			if (null != rFunctions && rFunctions.size() > 0) {
				RelRoleFunction tsRoleFunction = rFunctions.get(0);
				if (null != tsRoleFunction.getOperation()) {
					String[] operationArry = tsRoleFunction.getOperation().split(",");
					for (int i = 0; i < operationArry.length; i++) {
						operationCodes.add(operationArry[i]);
					}
				}
			}
		}
		return operationCodes;
	}

	public void flushRoleFunciton(String id, Function newFunction) {
		Function functionEntity = this.getEntity(Function.class, id);
		if (functionEntity.getIconId() == null || !StringUtil.isNotEmpty(functionEntity.getIconId())) {
			return;
		}
		Icon oldIcon = this.getEntity(Icon.class, functionEntity.getIconId());
		String iconId = newFunction.getIconId();
		Icon icon = this.findByProperty(Icon.class, "iconId", iconId).get(0);
		if (!oldIcon.getIconClas().equals(icon.getIconClas())) {
			// 刷新缓存
			HttpSession session = ContextHolderUtils.getSession();
			String userId = ResourceUtil.getSessionUserId();
			List<RelRoleUser> rUsers = this.findByProperty(RelRoleUser.class, "userId", userId);
			for (RelRoleUser ru : rUsers) {
				Role role = ru.getRole();
				session.removeAttribute(role.getId() + "");
			}
		}
	}

	public List<DictEntity> queryDict(String dicTable, String dicCode, String dicText) {
		// TODO Auto-generated method stub
		return null;
	}

}
