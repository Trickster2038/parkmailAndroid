package com.park.soulmates;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RecyclerMatchesAdapter extends FirebaseRecyclerAdapter
        <MatchModel, RecyclerMatchesAdapter.personsViewHolder> {

    public RecyclerMatchesAdapter(
            @NonNull FirebaseRecyclerOptions<MatchModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull personsViewHolder holder,
                                    int position, @NonNull MatchModel model) {
        DatabaseReference ref = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("users")
                .child(model.getTargetUID());
        Log.d("RecyclerMatchesAdapter", ref.toString());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AdvancedUserModel user = dataSnapshot.getValue(AdvancedUserModel.class);
                holder.title.setText(user.getName().concat(" ").concat(user.getSurname()));
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

    @NonNull
    @Override
    public personsViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_matches_profile, parent, false);
        return new personsViewHolder(view);
    }

    static class personsViewHolder extends RecyclerView.ViewHolder {
        TextView title, uid, interestsField, contacts;

        public personsViewHolder(@NonNull View itemView) {
            super(itemView);
            contacts = itemView.findViewById(R.id.profileMatchesContacts);
            title = itemView.findViewById(R.id.profileMatchesTitle);
            uid = itemView.findViewById(R.id.cardMatchUID);
            interestsField = itemView.findViewById(R.id.profileMatchesInterests);
        }
    }
}
