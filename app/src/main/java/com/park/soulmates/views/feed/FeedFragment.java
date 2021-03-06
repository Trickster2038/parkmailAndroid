package com.park.soulmates.views.feed;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.park.soulmates.R;
import com.park.soulmates.models.AdvancedUserModel;

public class FeedFragment extends Fragment {
    private RecyclerFeedAdapter mAdapter;
    private RecyclerView feedRecycler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseReference base = FirebaseDatabase.getInstance().getReference().child("users");
        FirebaseRecyclerOptions<AdvancedUserModel> options
                = new FirebaseRecyclerOptions.Builder<AdvancedUserModel>()
                .setQuery(base, AdvancedUserModel.class)
                .build();
        mAdapter = new RecyclerFeedAdapter(options, getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.activity_google_auth, container, false);

        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        feedRecycler = view.findViewById(R.id.recyclerFeed);
        feedRecycler.setLayoutManager(new LinearLayoutManager(inflater.getContext(), LinearLayoutManager.VERTICAL, false));
        feedRecycler.setAdapter(mAdapter);
        feedRecycler.getLayoutManager().scrollToPosition(0);
        Log.d("dev_feed", "recreate");

        View altView = inflater.inflate(R.layout.fragment_reconnect, container, false);
        Button retryBtn = altView.findViewById(R.id.feedReconnect);
        Fragment currentFragment = this;

        retryBtn.setOnClickListener(v -> {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(currentFragment).attach(currentFragment).commit();
        });

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean connected = cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
        if (connected) {
            return view;
        } else {
            return altView;
        }
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
