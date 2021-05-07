package com.hank.tracelib.db;

/**
 * author : Administrator
 * date   : 2020/5/18
 * desc   :
 */
public class MethodInfo {

    public String name;
    public long costTime;
    public long startTime;
    public long endTime;
    public boolean isMainThread;

    public static final String NAME = "name";
    public static final String COST_TIME = "costTime";
    public static final String START_TIME = "startTime";
    public static final String END_TIME = "endTime";
    public static final String IS_MAIN_THREAD = "isMainThread";
    public static final String TABLENAME = "methodInfo";

    public MethodInfo() {
    }

    public MethodInfo(String name, long costTime, long startTime, long endTime, boolean isMainThread) {
        this.name = name;
        this.costTime = costTime;
        this.endTime = endTime;
        this.isMainThread = isMainThread;
        this.startTime = startTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCostTime() {
        return costTime;
    }

    public void setCostTime(long costTime) {
        this.costTime = costTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public boolean isMainThread() {
        return isMainThread;
    }

    public void setMainThread(boolean mainThread) {
        isMainThread = mainThread;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "MethodInfo{" +
                "name='" + name + '\'' +
                ", costTime=" + costTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", isMainThread=" + isMainThread +
                '}';
    }
}
