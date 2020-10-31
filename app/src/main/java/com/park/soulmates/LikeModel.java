package com.park.soulmates;

public class LikeModel {
    private Integer mExist;
    private String mTargetUid;

    LikeModel() {
        this.mExist = 1;
        this.mTargetUid = "noUID";
    }

    LikeModel(String targUID) {
        this.mExist = 1;
        this.mTargetUid = targUID;
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