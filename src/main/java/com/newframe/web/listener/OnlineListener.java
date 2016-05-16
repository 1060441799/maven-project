package com.newframe.web.listener;

import com.newframe.core.manager.ClientManager;
import com.newframe.web.websocket.WebSocketHander;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.socket.TextMessage;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class OnlineListener implements ServletContextListener, HttpSessionListener {

    private static ApplicationContext ctx = null;

    public OnlineListener() {
    }


    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        WebSocketHander wsh = ctx.getBean(WebSocketHander.class);
        wsh.sendMessageToUsers(new TextMessage("Session created."));
    }


    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        ClientManager.getInstance().removeClinet(httpSessionEvent.getSession().getId());
    }

    /**
     * 服务器初始化
     */

    public void contextInitialized(ServletContextEvent evt) {
        ctx = WebApplicationContextUtils.getWebApplicationContext(evt.getServletContext());
    }

    public static ApplicationContext getCtx() {
        return ctx;
    }


    public void contextDestroyed(ServletContextEvent paramServletContextEvent) {

    }

}
