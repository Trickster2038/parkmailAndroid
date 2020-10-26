package com.park.soulmates;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MatchesFragment extends Fragment {
    public MatchesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewMatches = inflater.inflate(R.layout.fragment_matches, container, false);
        return viewMatches;
    }
}
