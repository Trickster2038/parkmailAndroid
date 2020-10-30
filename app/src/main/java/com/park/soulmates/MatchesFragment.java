package com.park.soulmates;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class MatchesFragment extends Fragment {
    private FirebaseAuth mAuth;
    private DatabaseReference mbase;
    private RecyclerMatchesAdapter adapter;
    private RecyclerView mMatchesRecycler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        mMatchesRecycler = view.findViewById(R.id.recyclerFeed);
        mMatchesRecycler.setLayoutManager(new LinearLayoutManager(inflater.getContext(), LinearLayoutManager.VERTICAL, false));

        FirebaseAuth userAuth = FirebaseAuth.getInstance();
        mbase
                = FirebaseDatabase.getInstance().getReference().child("users").child(userAuth.getUid()).child("matches");

        Log.d("dev_DbRefFeed", mbase.toString());

        FirebaseRecyclerOptions<Match> options
                = new FirebaseRecyclerOptions.Builder<Match>()
                .setQuery(mbase, Match.class)
                .build();

        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new RecyclerMatchesAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        mMatchesRecycler.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }
}
