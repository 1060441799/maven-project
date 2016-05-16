package com.newframe.core.sigar;

/** 
 * 网卡信息、接口数据、流量 
 * 使用Sigar获得网卡信息 
 */

import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.util.ArrayList;
import java.util.List;

public class NetInterfaceData {
	
	private static final String ZERO_ADDRESS = "0.0.0.0";

	private NetInterfaceConfig nifConfig;
	private NetInterfaceStat nifStat;

	private void populate(Sigar sigar, String name) throws SigarException {
		nifConfig = sigar.getNetInterfaceConfig(name);
		nifStat = sigar.getNetInterfaceStat(name);
	}

	private static NetInterfaceData gather(Sigar sigar, String name) throws SigarException {
		NetInterfaceData data = new NetInterfaceData();
		data.populate(sigar, name);
		return data;
	}

	public static List<NetInterfaceData> getNetInterfaceData() throws SigarException {
		Sigar sigar = SigarFactory.getInstance();
		String[] netIfs = sigar.getNetInterfaceList();
		List<NetInterfaceData> netIfList = new ArrayList<NetInterfaceData>();
		for (String name : netIfs) {
			NetInterfaceData netIfData1 = NetInterfaceData.gather(sigar, name);
			netIfList.add(netIfData1);
		}
		return netIfList;
	}
	
	/**
	 * 获取服务器所有网卡的物理地址
	 * @return List,List中包含主机的网卡的物理地址
	 * @throws SigarException
	 */
	public static List<String> getAllPhysicalAddress() throws SigarException{
		List<String> list = new ArrayList<String>();
		List<NetInterfaceData> netInterfaceDatas = getNetInterfaceData();
		for(NetInterfaceData data:netInterfaceDatas){
			NetInterfaceConfig conf = data.getNifconfig();
			if(!ZERO_ADDRESS.equals(conf.getAddress())){
				list.add(conf.getHwaddr());
			}
		}
		return list;
	}

	/**
	 * 网卡配置信息
	 * @return
	 */
	public NetInterfaceConfig getNifconfig() {
		return nifConfig;
	}
	/**
	 * 网卡流量信息
	 * @return
	 */
	public NetInterfaceStat getNifstat() {
		return nifStat;
	}

}