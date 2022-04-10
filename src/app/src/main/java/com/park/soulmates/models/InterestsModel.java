package com.park.soulmates.models;

import androidx.annotation.NonNull;

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

    @NonNull
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        int i;
        Boolean flag;
        for (i = 0; i < 5; i++) {
            flag = mInterests.get(i);
            if (flag) {
                switch (i) {
                    case 0:
                        result.append("#IT ");
                        break;
                    case 1:
                        result.append("#Music ");
                        break;
                    case 2:
                        result.append("#Sport ");
                        break;
                    case 3:
                        result.append("#Videogames ");
                        break;
                    case 4:
                        result.append("#Reading ");
                        break;
                }
            }
        }
        return result.toString();
    }

    public ArrayList<Boolean> getInterests() {
        return mInterests;
    }

    public void setInterests(ArrayList<Boolean> interests) {
        this.mInterests = interests;
    }
}
