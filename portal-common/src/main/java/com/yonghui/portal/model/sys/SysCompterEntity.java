package com.yonghui.portal.model.sys;


/**
 * 系统配置信息
 */
public class SysCompterEntity {
    // jvm最大内存
    private long maxControl;
    // jvm已占用内存
    private long currentUse;
    // jvm已占用且已使用内存
    private long jvmUse;
    // jvm已占用剩余可用内存
    private long freeMemory;
    // jvm实际可用内存
    private long jvmFreeMemory;
    // 系统内存
    private long sysTotalPhysicalMemorySize;
    // 系统可用内存
    private long sysFreePhysicalMemorySize;
    // jvm可用处理器数量
    private long availableProcessors;

    public long getAvailableProcessors() {
        return availableProcessors;
    }

    public void setAvailableProcessors(long availableProcessors) {
        this.availableProcessors = availableProcessors;
    }

    public long getSysTotalPhysicalMemorySize() {
        return sysTotalPhysicalMemorySize;
    }

    public void setSysTotalPhysicalMemorySize(long sysTotalPhysicalMemorySize) {
        this.sysTotalPhysicalMemorySize = sysTotalPhysicalMemorySize;
    }

    public long getSysFreePhysicalMemorySize() {
        return sysFreePhysicalMemorySize;
    }

    public void setSysFreePhysicalMemorySize(long sysFreePhysicalMemorySize) {
        this.sysFreePhysicalMemorySize = sysFreePhysicalMemorySize;
    }

    public long getJvmFreeMemory() {
        return maxControl - currentUse + freeMemory;
    }

    public void setJvmFreeMemory(long jvmFreeMemory) {
        this.jvmFreeMemory = jvmFreeMemory;
    }

    public long getJvmUse() {
        return currentUse - freeMemory;
    }

    public void setJvmUse(long jvmUse) {
        this.jvmUse = jvmUse;
    }

    public long getMaxControl() {
        return maxControl;
    }

    public void setMaxControl(long maxControl) {
        this.maxControl = maxControl;
    }

    public long getCurrentUse() {
        return currentUse;
    }

    public void setCurrentUse(long currentUse) {
        this.currentUse = currentUse;
    }

    public long getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(long freeMemory) {
        this.freeMemory = freeMemory;
    }
}
