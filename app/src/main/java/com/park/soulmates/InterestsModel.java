package com.park.soulmates;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


// Changing constructor signature to ArrayList<> might be uneffective (on test/dev phase?)
public class InterestsModel {
    // IT, music, sport, videogames, reading
    public ArrayList<Boolean> interests;

    public InterestsModel(){
        this.interests = new ArrayList<Boolean>(5);
        for(int i=0; i<5; i++){
            interests.add(false);
        }
    }

    // TODO: maybe change to arrayList(cant use literals to init, but clearer archS)
    public InterestsModel(Boolean[] iInterests){
        this.interests = new ArrayList<Boolean>(5);
        for(int i=0; i<5; i++){
            interests.add(iInterests[i]);
        }
    }

    public ArrayList<Boolean> getInterests() {
        return interests;
    }

    public void setInterests(ArrayList<Boolean> iInterests) {
//        this.interests = new ArrayList<Boolean>(5);
//        for(int i=0; i<5; i++){
//            interests.add(iInterests[i]);
//        }
        this.interests = iInterests;
    }
}
