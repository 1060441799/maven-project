package com.newframe.web.model;

import com.newframe.core.sigar.CpuData;

/**
 * Created by xm on 2016/4/26.
 */
public class ServerInfoVO {
    private Runtime runTime = Runtime.getRuntime();

    private Long jvmTotalMemory;
    private Long jvmFreeMemory;
    private Long jvmMaxMemory;

    public Long getJvmTotalMemory() {
        return runTime.totalMemory();
    }

    public Long getJvmFreeMemory() {
        return runTime.freeMemory();
    }

    public Long getJvmMaxMemory() {
        return runTime.maxMemory();
    }
}
