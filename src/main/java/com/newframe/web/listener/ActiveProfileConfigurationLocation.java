package com.newframe.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.env.AbstractEnvironment;

/**
 * Created by xm on 2016/3/31.
 */
public class ActiveProfileConfigurationLocation implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		String env = System.getProperty("CONFIG_MODE");
		if (env == null) {
			env = System.getenv("CONFIG_MODE");
		} else {
			env = "LOCAL";
		}
		System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, env);
		System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, env);
		
		System.out.println("[AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME]"
				+ System.getProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME));
		System.out.println("[AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME]"
				+ System.getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME));

		System.out.println("\n+++++++\n++++++\n++++++\n++++++++");
		for (Object propName : System.getProperties().keySet()) {
			System.out.println("[SYSTEM_PROPERTY]" + propName + " : " + System.getProperty(propName.toString()));
		}
		System.out.println("\n+++++++\n++++++\n++++++\n++++++++");

		System.out.println("\n+++++++\n++++++\n++++++\n++++++++");
		for (Object propName : System.getenv().keySet()) {
			System.out.println("[ENV_PROPERTY]" + propName + " : " + System.getenv(propName.toString()));
		}
		System.out.println("\n+++++++\n++++++\n++++++\n++++++++");

		String WEBFARM_ID = "";
		if (StringUtils.isNotBlank(System.getProperty("HOST"))) {
			String host = System.getProperty("HOST");
			String catalina_base = System.getProperty("catalina.basepojo");
			System.out.println("\n+++++++\n++++++\n++++++\n++++++++");
			System.out.println("[catalina_base]" + catalina_base);
			System.out.println("\n+++++++\n++++++\n++++++\n++++++++");
		}
	}

}
