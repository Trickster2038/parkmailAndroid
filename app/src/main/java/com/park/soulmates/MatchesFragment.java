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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MatchesFragment extends Fragment {
    private RecyclerMatchesAdapter mAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth userAuth = FirebaseAuth.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("users")
                .child(userAuth.getUid())
                .child("matches");
        Log.d("MatchesFragment", databaseReference.toString());
        FirebaseRecyclerOptions<MatchModel> options
                = new FirebaseRecyclerOptions
                .Builder<MatchModel>()
                .setQuery(databaseReference, MatchModel.class)
                .build();
        mAdapter = new RecyclerMatchesAdapter(options);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        RecyclerView mMatchesRecycler = view.findViewById(R.id.recyclerFeed);
        mMatchesRecycler.setLayoutManager(new LinearLayoutManager(inflater.getContext(), LinearLayoutManager.VERTICAL, false));
        mMatchesRecycler.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}
