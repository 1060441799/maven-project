package com.newframe.core.mail.impl;

import com.newframe.core.config.AppConfig;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.newframe.core.mail.MailManager;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import com.google.common.base.Charsets;

import freemarker.template.Configuration;

/**
 * Created by xm on 2016/4/2.
 */
@Component
public class MailManagerImpl implements MailManager {

	private static final Logger log = Logger.getLogger(MailManagerImpl.class);

	@Autowired
	private AppConfig config;

	@Resource(name = "mailSender")
	private JavaMailSender mailSender;

	@Resource(name = "freemarkerMailConfiguration")
	private Configuration configuration;

	public Future<Boolean> send(final String from, final String[] toList, final String[] ccList, final String[] bccList,
			final String sublect, final String htmlContent) {
		if (StringUtils.isEmpty(htmlContent)) {
			log.error("");
			return new AsyncResult<Boolean>(false);
		}
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				if (toList != null) {
					for (String to : toList) {
						mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
					}
				}
				if (ccList != null) {
					for (String cc : ccList) {
						mimeMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
					}
				}
				if (bccList != null) {
					for (String cc : ccList) {
						mimeMessage.addRecipient(Message.RecipientType.BCC, new InternetAddress(cc));
					}
				}
				mimeMessage.setFrom(new InternetAddress(from));
				mimeMessage.setSubject(sublect, "utf-8");
				mimeMessage.setText(htmlContent, Charsets.UTF_8.name(), "html");
				mimeMessage.setHeader("X-Message-Flag", "Internal Only");
			}
		};
		mailSender.send(preparator);
		return new AsyncResult<Boolean>(true);
	}

	public Future<Boolean> sendException(String uri, String servedBy, String user, HttpStatus status, Throwable e) {
		return null;
	}

	public Future<Boolean> sendException(String uri, String servedBy, String user, HttpStatus status, Throwable e,
			String replaceMessage) {
		return null;
	}

	public Future<Boolean> sendJobException(String jobName, String server, Date timeStart, Date timeEnd, Throwable e) {
		return null;
	}

	public Map<String, String> defaultModel(String team, String template) {
		return null;
	}

	public String buildTemplate(String templatepath, Object model) {
		return null;
	}
}
