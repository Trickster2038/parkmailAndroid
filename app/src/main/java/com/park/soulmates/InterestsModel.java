package com.park.soulmates;

import java.util.ArrayList;
import java.util.Arrays;


// Changing constructor signature to ArrayList<> might be uneffective (on test/dev phase?)
public class InterestsModel {
    // IT, music, sport, videogames, reading
    private ArrayList<Boolean> mInterests;

    public InterestsModel(){
        this.mInterests = new ArrayList<>(5);
        for(int i=0; i<5; i++){
            mInterests.add(false);
        }
    }

    // TODO: maybe change to arrayList(cant use literals to init, but clearer archS)
    public InterestsModel(Boolean[] interests) {
        this.mInterests = new ArrayList<>(5);
        mInterests.addAll(Arrays.asList(interests).subList(0, 5));
    }

    public ArrayList<Boolean> getInterests() {
        return mInterests;
    }

    public void setInterests(ArrayList<Boolean> interests) {
        this.mInterests = interests;
    }
}
