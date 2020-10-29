package com.park.soulmates;

public class Match {
    Integer exist;
    String targetUID;

    Match() {
        this.exist = 1;
        this.targetUID = "noUID";
    }
    Match(String targUID){
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
