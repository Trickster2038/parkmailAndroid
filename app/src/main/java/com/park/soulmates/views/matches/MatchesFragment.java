package com.park.soulmates.views.matches;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.park.soulmates.R;
import com.park.soulmates.models.AdvancedUserModel;
import com.park.soulmates.models.MessageDao;
import com.park.soulmates.models.MessageModel;
import com.park.soulmates.models.UserDao;
import com.park.soulmates.utils.AppSingletone;
import com.park.soulmates.utils.FirebaseUtils;
import com.park.soulmates.utils.MessageDB;
import com.park.soulmates.utils.UserDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MatchesFragment extends Fragment {
    ArrayList<AdvancedUserModel> matchesAccounts = new ArrayList<>();
    ArrayList<MessageModel> messagesList = new ArrayList<>();
    private RecyclerMatchesAdapter mAdapter;
    private CachedMatchesAdapter mCacheAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseAuth userAuth = FirebaseAuth.getInstance();
        matchesAccounts = new ArrayList<>();
        messagesList = new ArrayList<>();

        DatabaseReference databaseReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("users")
                .child(userAuth.getUid())
                .child("matches");
        Log.d("MatchesFragment", databaseReference.toString());
//        FirebaseRecyclerOptions<MatchModel> options
//                = new FirebaseRecyclerOptions
//                .Builder<MatchModel>()
//                .setQuery(databaseReference, MatchModel.class)
//                .build();
//        mAdapter = new RecyclerMatchesAdapter(options, getActivity());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> td = (HashMap<String, Object>) dataSnapshot.getValue();
                if (td != null) {
                    for (Object i : td.values()) {
                        Log.d("dev_fb_list", i.toString());
                        String targetUid = i.toString().split("=")[2].replace("}", "");
                        Log.d("dev_fb_list", targetUid);
                        FirebaseUtils.getMatchesAcc(matchesAccounts, targetUid, getActivity());
                        FirebaseUtils.cacheChat(targetUid, messagesList);
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mCacheAdapter = new CachedMatchesAdapter(matchesAccounts, getActivity());

        new Thread(() -> {
            try {
                UserDB db = AppSingletone.getInstance().getDatabase();
                UserDao dao = db.userDao();

                MessageDB dbMsg = AppSingletone.getInstance().getDatabaseMsg();
                MessageDao daoMsg = dbMsg.messageDao();

                Thread.sleep(500);

                Log.d("dev_fb_thread", matchesAccounts.toString());

                ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                Boolean connected = cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();

                if (connected) {
                    dao.deleteAll();
                    for (AdvancedUserModel user : matchesAccounts) {
                        dao.Insert(user);
                    }
                } else {
                    // fill accs
                    matchesAccounts = (ArrayList<AdvancedUserModel>) dao.getAll();
                    Log.d("dev_cache_thread", matchesAccounts.toString());
                }

                if (connected) {
                    daoMsg.deleteAll();
                    for (MessageModel msg : messagesList) {
                        daoMsg.Insert(msg);
                    }
                } else {
                    // fill chats
                    messagesList = (ArrayList<MessageModel>) daoMsg.getAll();
                }

                mCacheAdapter = new CachedMatchesAdapter(matchesAccounts, getActivity());


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        RecyclerView mMatchesRecycler = view.findViewById(R.id.recyclerFeed);
        mMatchesRecycler.setLayoutManager(new LinearLayoutManager(inflater.getContext(), LinearLayoutManager.VERTICAL, false));
        //mMatchesRecycler.setAdapter(mAdapter);

        new Thread(() -> {
            try {
                getActivity().runOnUiThread(() -> {
                    View tempView = inflater.inflate(R.layout.fragment_wait, container, false);
                });

                Thread.sleep(700);
                getActivity().runOnUiThread(() -> {
                    mMatchesRecycler.setAdapter(mCacheAdapter);
                    View view1 = inflater.inflate(R.layout.fragment_feed, container, false);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //mAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        //mAdapter.stopListening();
    }
}
