package com.newframe.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.newframe.core.config.AppConfig;
import com.newframe.core.pojo.pojoimpl.impl.User;
import com.newframe.core.util.ResourceUtil;
import com.newframe.core.web.base.BaseController;
import com.newframe.core.web.model.json.AjaxJson;
import com.newframe.web.service.SystemService;
import com.newframe.web.service.UserService;

@Controller
@RequestMapping(value = "/chat/")
public class ChatController extends BaseController {

	private static final Logger log = Logger.getLogger(ChatController.class);

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

	@RequestMapping(value = "getChatList")
	@ResponseBody
	public AjaxJson getTerritory(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		User user = ResourceUtil.getSessionUser();
		if(user != null){

		}
		return j;
	}
}
