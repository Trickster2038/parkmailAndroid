package com.park.soulmates;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FeedFragment extends Fragment {
    public FeedFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewFeed = inflater.inflate(R.layout.fragment_feed, container, false);
        return viewFeed;
    }
}
