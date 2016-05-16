package com.newframe.core.sigar;

import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;

public class MemoryData {
	private Mem memory;
	private Swap swap;
	
	public static MemoryData getMemoryData() throws SigarException{
		Sigar sigar = SigarFactory.getInstance();
		
		MemoryData memoryData = new MemoryData();
		memoryData.memory = sigar.getMem();
		memoryData.swap = sigar.getSwap();
		
		return memoryData;
	}

	public Mem getMemory() {
		return memory;
	}

	public void setMemory(Mem memory) {
		this.memory = memory;
	}

	public Swap getSwap() {
		return swap;
	}

	public void setSwap(Swap swap) {
		this.swap = swap;
	}
}
