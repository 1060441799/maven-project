package com.newframe.web.model;

import com.newframe.core.pojo.pojoimpl.impl.Function;

import java.util.List;
import java.util.Map;

public class SystemConfigMap {
	private Map<String,String> configs;
	private Map<String, List<Function>> map;

	public Map<String,String> getConfigs() {
		return configs;
	}

	public void setConfigs(Map<String,String> configs) {
		this.configs = configs;
	}

	public Map<String, List<Function>> getMap() {
		return map;
	}

	public void setMap(Map<String, List<Function>> map) {
		this.map = map;
	}

}
