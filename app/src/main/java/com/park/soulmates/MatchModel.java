package com.park.soulmates;

public class MatchModel {
    Integer exist;
    String targetUID;

    MatchModel() {
        this.exist = 1;
        this.targetUID = "noUID";
    }
    MatchModel(String targUID){
        this.exist = 1;
        this.targetUID = targUID;
    }

    public void setExist(Integer exist) {
        this.exist = exist;
    }

    public String getTargetUID() {
        return targetUID;
    }

    public void setTargetUID(String targetUID) {
        this.targetUID = targetUID;
    }

    public Integer getExist() {
        return exist;
    }
}
