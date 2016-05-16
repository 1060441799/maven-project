package com.newframe.core.mail;

import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Created by xm on 2016/4/2.
 */
public interface MailManager {
    Future<Boolean> send(String from, String[] toList, String[] ccList, String[] bccList, String sublect, String htmlContent);

    Future<Boolean> sendException(String uri, String servedBy, String user, HttpStatus status, Throwable e);

    Future<Boolean> sendException(String uri, String servedBy, String user, HttpStatus status, Throwable e, String replaceMessage);

    Future<Boolean> sendJobException(String jobName, String server, Date timeStart, Date timeEnd, Throwable e);

    Map<String, String> defaultModel(String team, String template);

    String buildTemplate(String templatepath, Object model);
}
