package com.park.soulmates;

public class MatchModel {
    private Integer mExist;
    private String mTargetUid;

    MatchModel() {
        this.mExist = 1;
        this.mTargetUid = "noUID";
    }

    MatchModel(String targetUid) {
        this.mExist = 1;
        this.mTargetUid = targetUid;
    }

    public String getTargetUID() {
        return mTargetUid;
    }

    public void setTargetUID(String targetUid) {
        this.mTargetUid = targetUid;
    }

    public Integer getExist() {
        return mExist;
    }

    public void setExist(Integer exist) {
        this.mExist = exist;
    }
}
