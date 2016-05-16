package com.newframe.core.web.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.newframe.core.config.AppConfig;
import com.newframe.core.web.model.json.AjaxJson;

/**
 * Created by xm on 2016/4/2.
 */
public abstract class AbstractController {

	private static final Logger log = Logger.getLogger(AbstractController.class);

	@Autowired
	private AppConfig config;

	protected boolean ingoreException(Throwable exception) {
		if (exception != null
				&& "org.apache.catalina.connector.ClientAbortException"
						.equalsIgnoreCase(exception.getClass().getName())) {
			log.info("Exception ignored => " + exception.getClass().getName());
			return true;
		}
		return false;
	}

	@ExceptionHandler(java.lang.Exception.class)
	@ResponseBody
	public AjaxJson handleError(HttpServletRequest req,
								HttpServletResponse resp, final Exception exception) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		log.info("Exception => " + exception.getClass().getName());
		AjaxJson j = new AjaxJson();
		j.setMsg("错误状态码:" + status);
		j.setMsgType(6);
		j.setSuccess(false);
		return j;
	}

	// 忽略自定义Exception

}
