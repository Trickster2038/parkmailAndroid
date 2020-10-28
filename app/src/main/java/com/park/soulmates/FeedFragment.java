package com.park.soulmates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FeedFragment extends Fragment {
    private final int ELEM_COUNT = 3;
    private ArrayList<UserModel> mResource;
    private RecycleViewAdapter mAdapter;
    private RecyclerView mFeedRecycler;

    // TODO: change placeholders - Serge
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mResource = new ArrayList<>();
        for (int j = 1; j <= ELEM_COUNT; j++)
            mResource.add(new UserModel(Integer.toString(j).concat(" Lorem ipsum is a nice kind \n" +
                    "of text to fill some design placeholders as Artemii Lebedev should say,\n" +
                    "so let's go to the next feed card")));
        mAdapter = new RecycleViewAdapter(mResource);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        mFeedRecycler = view.findViewById(R.id.recyclerFeed);
        mFeedRecycler.setLayoutManager(new LinearLayoutManager(inflater.getContext(), LinearLayoutManager.VERTICAL, false));
        mFeedRecycler.setAdapter(mAdapter);
        // TODO: fix null context
        return view;
    }


}
