package com.park.soulmates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedFragment extends Fragment {
    private DatabaseReference mbase;
    private RecycleViewAdapter adapter;
    private RecycleViewAdapter mAdapter;
    private RecyclerView mFeedRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        mFeedRecycler = view.findViewById(R.id.recyclerFeed);
        mFeedRecycler.setLayoutManager(new LinearLayoutManager(inflater.getContext(), LinearLayoutManager.VERTICAL, false));
        mFeedRecycler.setAdapter(mAdapter);

        mbase = FirebaseDatabase.getInstance().getReference().child("users");
        //Log.d("dev_DbRefFeed", mbase.toString());

        FirebaseRecyclerOptions<AdvancedUserModel> options
                = new FirebaseRecyclerOptions.Builder<AdvancedUserModel>()
                .setQuery(mbase, AdvancedUserModel.class)
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

    @Override
    public void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }
}
