package com.park.soulmates;

public class UserModel {
    public String bio;

    public UserModel(String textDescription) {
        this.bio = textDescription;
    }

    // not used really, but needed to initialize some RecycleView mechanics
    public UserModel(){this.bio = "no constructor bio";}

    public String getBio() {
        return bio;
    }

    public void setBio(String textDescription) {
        this.bio = textDescription;
    }
}
