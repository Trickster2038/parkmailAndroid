package com.park.soulmates;

public class Like {
    Integer exist;
    String targetUID;

    Like() {
        this.exist = 1;
        this.targetUID = "noUID";
    }

    Like(String targUID){
        this.exist = 1;
        this.targetUID = targUID;
    }

    public String getTargetUID() {
        return targetUID;
    }

    public void setTargetUID(String targetUID) {
        this.targetUID = targetUID;
    }

    public void setExist(Integer exist) {
        this.exist = exist;
    }
    public Integer getExist() {
        return exist;
    }
}