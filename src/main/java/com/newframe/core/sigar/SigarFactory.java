package com.newframe.core.sigar;

import org.hyperic.sigar.Sigar;

/**
 * 获取Sigar对象,获取之前需要设置libpath
 * @author xs06974
 */
public class SigarFactory {

	private static Sigar sigar = null;
	
	public static Sigar getInstance() {
		if (sigar != null) {
			return sigar;
		}
		SigarPreLoader preLoader = new SigarPreLoader();
		preLoader.setLibraryPath();
		sigar = new Sigar();
		return sigar;
	}

	
}
