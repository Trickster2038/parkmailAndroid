package com.park.soulmates;

public class Match {
    String userUid1, userUid2;

    Match(String uid1, String uid2) {
        this.userUid1 = uid1;
        this.userUid2 = uid2;
    }

    public String getUserUid2() {
        return userUid2;
    }

    public String getUserUid1() {
        return userUid1;
    }

    public void setUserUid2(String userUid2) {
        this.userUid2 = userUid2;
    }

    public void setUserUid1(String userUid1) {
        this.userUid1 = userUid1;
    }
}
