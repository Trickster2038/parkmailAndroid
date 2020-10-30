package com.park.soulmates;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    public TextView bio, title, uid, interestsField, contacts;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        bio = itemView.findViewById(R.id.profileBio);
        title = itemView.findViewById(R.id.profileTitle);
        uid = itemView.findViewById(R.id.cardUID);
        interestsField = itemView.findViewById(R.id.profileInterests);
        contacts = itemView.findViewById(R.id.profileMatchesContacts);
    }
}
