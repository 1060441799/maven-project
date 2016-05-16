package com.newframe.core.sigar;

import org.hyperic.sigar.*;

/** 
 * Cpu数据 
 * 使用Sigar获得CPU的基本信息、使用百分比、使用时间 
 */
public class CpuData {
	private CpuInfo info;
	private CpuPerc perc;
	private Cpu timer;
	private int count;

	private void populate(Sigar sigar) throws SigarException {
		count = sigar.getCpuInfoList().length;
		info = sigar.getCpuInfoList()[0];
		perc = sigar.getCpuPerc();
		timer = sigar.getCpu();
	}

	public static CpuData getCpuData() throws SigarException {
		Sigar sigar = SigarFactory.getInstance();
		CpuData data = new CpuData();
		data.populate(sigar);
		return data;
	}

	public CpuInfo getInfo() {
		return info;
	}

	public CpuPerc getPerc() {
		return perc;
	}

	public Cpu getTimer() {
		return timer;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setInfo(CpuInfo info) {
		this.info = info;
	}

	public void setPerc(CpuPerc perc) {
		this.perc = perc;
	}

	public void setTimer(Cpu timer) {
		this.timer = timer;
	}
	
}
