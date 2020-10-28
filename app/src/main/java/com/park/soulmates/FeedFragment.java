package com.park.soulmates;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FeedFragment extends Fragment {
    private DatabaseReference mbase;
    private RecycleViewAdapter adapter;
    private final int ELEM_COUNT = 3;
    private ArrayList<UserModel> mResource;
    private RecycleViewAdapter mAdapter;
    private RecyclerView mFeedRecycler;

    // TODO: change placeholders - Serge
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mResource = new ArrayList<>();
//        for (int j = 1; j <= ELEM_COUNT; j++)
//            mResource.add(new UserModel(Integer.toString(j).concat(" Lorem ipsum is a nice kind \n" +
//                    "of text to fill some design placeholders as Artemii Lebedev should say,\n" +
//                    "so let's go to the next feed card")));
//        mAdapter = new RecycleViewAdapter(mResource);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        mFeedRecycler = view.findViewById(R.id.recyclerFeed);
        mFeedRecycler.setLayoutManager(new LinearLayoutManager(inflater.getContext(), LinearLayoutManager.VERTICAL, false));
        mFeedRecycler.setAdapter(mAdapter);

        // FIXME: change entrance point to real DATASET, write and swap to ADVANCED USER MODEL!!!
        mbase
                = FirebaseDatabase.getInstance().getReference().child("testUsers");

        Log.d("DBrefFeed", mbase.toString());

        // To display the Recycler view linearly

        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<UserModel> options
                = new FirebaseRecyclerOptions.Builder<UserModel>()
                .setQuery(mbase, UserModel.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new RecycleViewAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        mFeedRecycler.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stoping of the activity
    @Override
    public void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }


}
