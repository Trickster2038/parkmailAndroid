package com.park.soulmates.models;

public class UserModel {
    private String name;
    private Boolean romanticSearch;

    public UserModel(){
        name = "defaultName";
        romanticSearch = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getRomanticSearch() {
        return romanticSearch;
    }

    public void setRomanticSearch(Boolean romanticSearch) {
        this.romanticSearch = romanticSearch;
    }
}