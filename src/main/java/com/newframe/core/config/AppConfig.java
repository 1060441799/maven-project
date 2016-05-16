package com.newframe.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {
	@Value(value = "${app.version}")
	private String appVersion;
	@Value(value = "${app.name}")
	private String appName;
	@Value(value = "${app.sql.query.threshold}")
	private String sqlQueryThreshold;
	@Value(value = "${app.retry.timeout.default}")
	private String retryTimeoutDefault;

	public String getAppVersion() {
		return appVersion;
	}
	
	public String getAppName(){
		return appName;
	}

	public String getSqlQueryThreshold() {
		return sqlQueryThreshold;
	}

	public String getRetryTimeoutDefault() {
		return retryTimeoutDefault;
	}
}
