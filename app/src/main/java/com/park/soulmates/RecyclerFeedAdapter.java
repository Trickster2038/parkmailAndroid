
package com.park.soulmates;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

// FirebaseRecyclerAdapter is a class provided by
// FirebaseUI. it provides functions to bind, adapt and show
// database contents in a Recycler View

public class RecyclerFeedAdapter extends FirebaseRecyclerAdapter<
        AdvancedUserModel, RecyclerFeedAdapter.personsViewholder> {

    public RecyclerFeedAdapter(
            @NonNull FirebaseRecyclerOptions<AdvancedUserModel> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull personsViewholder holder,
                                    int position, @NonNull AdvancedUserModel model)
    {
        holder.bio.setText(model.getBio());
        holder.title.setText(model.getName().concat(" ").concat(model.getSurname()));
        holder.uid.setText(model.getUid());
        holder.interestsField.setText(model.getInterests().toString());

        // URL изображения, который мы получили выше
        //String url="https://firebasestorage.googleapis.com/v0/b/retrieve-images-958e5.appspot.com/o/9.PNG?alt=media&token=6bd05383-0070-4c26-99cb-dcb17a23f7eb";

        //Glide.with(holder.avatarView).load(url).into(holder.avatarView);

        //FirebaseAuth userAuth = FirebaseAuth.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference().child("users/" + model.getUid() + "/avatar" );

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    Glide.with(holder.avatarView).load(uri).into(holder.avatarView);
                } catch (Exception e){
                    Log.d("dev_avatar_download", "404");
                }
                // Got the download URL for 'users/me/profile.png'
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        // TODO: disable after click + add simple filter using "gone" view attribute
        Button likeButton = holder.itemView.findViewById(R.id.likeBtn);
        likeButton.setOnClickListener(v -> {
            TextView textUid = holder.itemView.findViewById(R.id.cardUID);
            LikePusher.push(textUid.getText().toString());
            Log.d("dev_DB_status", "db_feed - OK");
        });
    }

    @NonNull
    @Override
    public personsViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_feed_profile, parent, false);
        return new RecyclerFeedAdapter.personsViewholder(view);
    }

    class personsViewholder
            extends RecyclerView.ViewHolder {
        TextView bio, title, uid, interestsField;
        ImageView avatarView;
        public personsViewholder(@NonNull View itemView)
        {
            super(itemView);
            avatarView = itemView.findViewById(R.id.feedAvatar);
            bio = itemView.findViewById(R.id.profileBio);
            title = itemView.findViewById(R.id.profileTitle);
            uid  = itemView.findViewById(R.id.cardUID);
            interestsField = itemView.findViewById(R.id.profileInterests);
        }
    }
}
