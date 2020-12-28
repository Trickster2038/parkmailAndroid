package com.park.soulmates.models;

public class UserModel {
    private String mName;
    private Boolean mRomanticSearch;

    public UserModel() {
        mName = "defaultName";
        mRomanticSearch = true;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public Boolean getRomanticSearch() {
        return mRomanticSearch;
    }

    public void setRomanticSearch(Boolean romanticSearch) {
        this.mRomanticSearch = romanticSearch;
    }
}
