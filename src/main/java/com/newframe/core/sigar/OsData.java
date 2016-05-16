package com.newframe.core.sigar;

import org.hyperic.sigar.OperatingSystem;

public class OsData {
	
	private OperatingSystem os = OperatingSystem.getInstance();

	public OperatingSystem getOs() {
		return os;
	}

	public void setOs(OperatingSystem os) {
		this.os = os;
	}
	
}
