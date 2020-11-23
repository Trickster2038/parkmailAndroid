package com.park.soulmates.models;

import java.util.ArrayList;
import java.util.Arrays;


// Changing constructor signature to ArrayList<> might be uneffective (on test/dev phase?)
public class InterestsModel {
    // IT, music, sport, videogames, reading
    private ArrayList<Boolean> mInterests;

    public InterestsModel() {
        this.mInterests = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            mInterests.add(false);
        }
    }

    // TODO: maybe change to arrayList(cant use literals to init, but clearer arch)
    public InterestsModel(Boolean[] interests) {
        this.mInterests = new ArrayList<>(5);
        mInterests.addAll(Arrays.asList(interests).subList(0, 5));
    }

    @Override
    public String toString(){
        String result = "";
        int i;
        Boolean flag;
        for (i = 0; i < 5; i++) {
            flag = mInterests.get(i);
            if(flag){
                switch (i) {
                    case 0:
                        result += "#IT ";
                        break;
                    case 1:
                        result += "#Music ";
                        break;
                    case 2:
                        result += "#Sport ";
                        break;
                    case 3:
                        result += "#Videogames ";
                        break;
                    case 4:
                        result += "#Reading ";
                        break;
                }
            }
        }
        return result;
    }

    public ArrayList<Boolean> getInterests() {
        return mInterests;
    }

    public void setInterests(ArrayList<Boolean> interests) {
        this.mInterests = interests;
    }
}
