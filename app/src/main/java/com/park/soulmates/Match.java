package com.park.soulmates;

public class Match {
    Integer mExist;
    String mTargetUid;

    Match() {
        this.mExist = 1;
        this.mTargetUid = "noUID";
    }

    Match(String targetUid) {
        this.mExist = 1;
        this.mTargetUid = targetUid;
    }

    public String getTargetUid() {
        return mTargetUid;
    }

    public void setTargetUid(String targetUid) {
        this.mTargetUid = targetUid;
    }

    public Integer getExist() {
        return mExist;
    }

    public void setExist(Integer exist) {
        this.mExist = exist;
    }
}
