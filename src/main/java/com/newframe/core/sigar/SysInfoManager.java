package com.newframe.core.sigar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 用于 获取服务器系统信息
 * @author xs06974
 */
public class SysInfoManager {

	private static final Logger logger = LoggerFactory.getLogger(SysInfoManager.class);
	
	public static final String DEFAULT_ADDRESS = "00:00:00:00";
	
	/**
	 * 获取网卡的物理地址，若出异常则返回一个默认值
	 * @return 在主机的所有网卡中，返回最大的那个
	 */
	public static String getFirstMacAddress(){
		try {
			List<String> list = NetInterfaceData.getAllPhysicalAddress();
			Collections.sort(list, new Comparator<String>(){
				@Override
				public int compare(String s1, String s2) {
					return s2.compareTo(s1);
				}
			});
			String address = list.get(0);
			logger.info("Physical address:{}", address);
			return address;
		} catch (Exception e) {
			logger.warn("Get Physical address failed,use default value.", e);
			return DEFAULT_ADDRESS;
		}
	}
}
