package com.park.soulmates.models;

public class LikeModel {
    private Integer mExist;
    private String mTargetUid;

    public LikeModel() {
        this.mExist = 1;
        this.mTargetUid = "noUID";
    }

    public LikeModel(String targUID) {
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