package com.newframe.web.controller;

import com.newframe.core.config.AppConfig;
import com.newframe.core.extend.datasource.DataSourceContextHolder;
import com.newframe.core.extend.datasource.DataSourceType;

import com.newframe.core.pojo.pojoimpl.impl.*;
import com.newframe.core.util.*;

import com.newframe.core.web.base.BaseController;
import com.newframe.core.web.model.json.AjaxJson;
import com.newframe.web.service.SystemService;
import com.newframe.web.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/depart/")
public class DepartController extends BaseController {

    private static final Logger log = Logger.getLogger(DepartController.class);

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

}
