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

public class FeedFragment extends Fragment {
    private RecyclerFeedAdapter mAdapter;

    // TODO: change placeholders - Serge
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseReference dataBase = FirebaseDatabase.getInstance().getReference().child("users");
        FirebaseRecyclerOptions<UserModel> options
                = new FirebaseRecyclerOptions.Builder<UserModel>()
                .setQuery(dataBase, UserModel.class)
                .build();
        Log.d("DbRefFeed", dataBase.toString());
        mAdapter = new RecyclerFeedAdapter(options);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        RecyclerView recyclerFeed = view.findViewById(R.id.recyclerFeed);
        recyclerFeed.setLayoutManager(new LinearLayoutManager(inflater.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerFeed.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        mAdapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override
    public void onStop()
    {
        super.onStop();
        mAdapter.stopListening();
    }


}
