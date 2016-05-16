package com.newframe.web.controller;

import com.newframe.core.web.base.BaseController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by xm on 2016/4/10.
 */
@Controller
@RequestMapping(value = "/security/")
public class SecurityController extends BaseController {
    private static final Logger log = Logger.getLogger(SecurityController.class);

    /**
     * 指定无访问额权限页面
     *
     * @return
     */
    @RequestMapping(value = "/denied", method = RequestMethod.GET)
    public String getDeniedPage() {
        log.debug("Received request to show denied page");
        return "deniedpage";
    }
}
