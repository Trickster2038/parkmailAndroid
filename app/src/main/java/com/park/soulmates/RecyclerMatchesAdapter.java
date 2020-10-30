package com.park.soulmates;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class RecyclerMatchesAdapter extends
        FirebaseRecyclerAdapter<Match, RecyclerViewHolder> {


    public RecyclerMatchesAdapter(
            @NonNull FirebaseRecyclerOptions<Match> options) {
        super(options);
    }

    // Function to bind the view in Card view(here
    // "person.xml") iwth data in
    // model class(here "person.class")
    @Override
    protected void onBindViewHolder(@NonNull RecyclerViewHolder holder,
                                    int position, @NonNull Match model) {

        // Add firstname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        Log.d("RecyclerMatchesAdapter", model.getTargetUid() + model.getExist());
        DatabaseReference ref = FirebaseDatabase.
                getInstance().
                getReference().
                child("users").
                child(model.getTargetUid());
        Log.d("RecyclerMatchesAdapter", ref.toString());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel user = dataSnapshot.getValue(UserModel.class);
                holder.title.setText(Objects.requireNonNull(user).getName().concat(" ").concat(user.getSurname()));
                holder.uid.setText(user.getUid());
                holder.contacts.setText(user.getContacts());
                Log.d("RecyclerMatchesAdapter", "listener tick");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("RecyclerMatchesAdapter", String.valueOf(databaseError.getCode()));
            }
        });
    }

    // Function to tell the class about the Card view (here
    // "person.xml")in
    // which the data will be shown
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_matches_profile, parent, false);
        return new RecyclerViewHolder(view);
    }
}
