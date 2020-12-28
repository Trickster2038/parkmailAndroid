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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.park.soulmates.R;
import com.park.soulmates.models.AdvancedUserModel;
import com.park.soulmates.views.chat.ChatActivity;

import java.util.ArrayList;

public class CachedMatchesAdapter extends RecyclerView.Adapter<CachedMatchesAdapter.PersonsViewHolder> {
    private final ArrayList<AdvancedUserModel> mData;
    private final Context mContext;

    public CachedMatchesAdapter(ArrayList<AdvancedUserModel> dataArg, Context context) {
        this.mData = dataArg;
        mContext = context;
    }

    @NonNull
    @Override
    public PersonsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_matches_profile, parent, false);
        return new PersonsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonsViewHolder holder, int position) {
        AdvancedUserModel user = mData.get(position);
        holder.title.setText(user.getName().concat(" ").concat(user.getSurname()));
        holder.uid.setText(user.getUid());
        holder.contacts.setText(user.getContacts());

        //TODO: interests are not compatable with current Room annotations
        //holder.interestsField.setText(user.getInterests().toString());

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

        View card = holder.itemView.findViewById(R.id.profileMatchesCard);
        card.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ChatActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.putExtra("targetUID", user.getUid());
            intent.putExtra("targetName", holder.title.getText().toString());
            mContext.startActivity(intent);
            Log.d("dev_Chat", "chat intent sent");
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class PersonsViewHolder extends RecyclerView.ViewHolder {
        TextView title, uid, interestsField, contacts;
        ImageView avatarView;

        public PersonsViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarView = itemView.findViewById(R.id.profileMatchesPhoto);
            contacts = itemView.findViewById(R.id.profileMatchesContacts);
            title = itemView.findViewById(R.id.profileMatchesTitle);
            uid = itemView.findViewById(R.id.cardMatchUID);
            interestsField = itemView.findViewById(R.id.profileMatchesInterests);
        }
    }

}
