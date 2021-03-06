package com.park.soulmates.views.matches;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.park.soulmates.R;
import com.park.soulmates.models.AdvancedUserModel;
import com.park.soulmates.models.MatchModel;
import com.park.soulmates.views.chat.ChatActivity;

public class RecyclerMatchesAdapter extends FirebaseRecyclerAdapter
        <MatchModel, RecyclerMatchesAdapter.personsViewHolder> {
    private final Context mContext;

    public RecyclerMatchesAdapter(
            @NonNull FirebaseRecyclerOptions<MatchModel> options, Context ctx) {
        super(options);
        mContext = ctx;
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
                holder.interestsField.setText(user.getInterests().toString());

                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReference().child("users/" + user.getUid() + "/avatar");

                storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    try {
                        Glide.with(holder.avatarView).load(uri).into(holder.avatarView);
                    } catch (Exception e) {
                        Log.d("dev_avatar_download", "404");
                    }
                }).addOnFailureListener(exception -> {
                    // Handle any errors
                });
                Log.d("RecyclerMatchesAdapter", "listener tick");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("RecyclerMatchesAdapter", String.valueOf(databaseError.getCode()));
            }


        });

        View card = holder.itemView.findViewById(R.id.profileMatchesCard);
        card.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ChatActivity.class);
            intent.putExtra("targetUID", model.getTargetUID());
            intent.putExtra("targetName", holder.title.getText().toString());
            mContext.startActivity(intent);
            Log.d("dev_Chat", "chat intent sent");
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
        ImageView avatarView;

        public personsViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarView = itemView.findViewById(R.id.profileMatchesPhoto);
            contacts = itemView.findViewById(R.id.profileMatchesContacts);
            title = itemView.findViewById(R.id.profileMatchesTitle);
            uid = itemView.findViewById(R.id.cardMatchUID);
            interestsField = itemView.findViewById(R.id.profileMatchesInterests);
        }
    }
}
