
package com.park.soulmates.views.feed;

import android.location.Location;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.park.soulmates.R;
import com.park.soulmates.models.AdvancedUserModel;
import com.park.soulmates.utils.CurrentUser;
import com.park.soulmates.utils.CustomLocationListener;
import com.park.soulmates.utils.FirebaseUtils;

import static java.lang.Math.round;

// FirebaseRecyclerAdapter is a class provided by
// FirebaseUI. it provides functions to bind, adapt and show
// database contents in a Recycler View

public class RecyclerFeedAdapter extends FirebaseRecyclerAdapter<
        AdvancedUserModel, RecyclerFeedAdapter.personsViewholder> {

    public RecyclerFeedAdapter(
            @NonNull FirebaseRecyclerOptions<AdvancedUserModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull personsViewholder holder,
                                    int position, @NonNull AdvancedUserModel model) {
        String currentUid = FirebaseAuth.getInstance().getUid();

        // TODO: must be already init, but bugged sometimes
        CurrentUser.init();

        if (!currentUid.equals(model.getUid())
                && !((CurrentUser.getIsRomantic() || model.getRomanticSearch())
                && CurrentUser.getGender() == model.getGender())) {

            String TAG = "dev_feed";
            Log.d("dev_feed", "item is nice");
            Log.d("dev_romantic", Boolean.toString(CurrentUser.getIsRomantic() || model.getRomanticSearch()));
            Log.d("dev_genders_eq", Boolean.toString(CurrentUser.getGender() == model.getGender()));

            holder.bio.setText(model.getBio());
            holder.title.setText(model.getName().concat(" ").concat(model.getSurname()));
            holder.uid.setText(model.getUid());
            holder.interestsField.setText(model.getInterests().toString());
            holder.birthday.setText(model.getBirthdate().toString());



            if(model.getLatitude()!=null && model.getLongitude()!=null) {
                Log.d("dev_location_feed", model.getLatitude() + " " + model.getLongitude());
            }

            if(model.getLatitude()!=null  && model.getLongitude()!=null && CustomLocationListener.getCurrentLocation() != null){
                double cardLatitude = Double.parseDouble(model.getLatitude());
                double cardLongitude = Double.parseDouble(model.getLongitude());
                Location cardLocation = new Location("point B");
                cardLocation.setLatitude(cardLatitude);
                cardLocation.setLongitude(cardLongitude);
                Float distance =  CustomLocationListener.getCurrentLocation().distanceTo(cardLocation);
                Log.d("dev_location_feed", "distance: " + distance.toString());
                Integer distanceKm = Math.round(distance / 1000);
                Log.d("dev_location_feed", "distance: " + distanceKm.toString() + " km");
            }


            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference().child("users/" + model.getUid() + "/avatar");

            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    try {
                        Glide.with(holder.avatarView).load(uri).into(holder.avatarView);
                    } catch (Exception e) {
                        Log.d("dev_avatar_download", "404");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });

            Button likeButton = holder.itemView.findViewById(R.id.likeBtn);
            likeButton.setOnClickListener(v -> {
                TextView textUid = holder.itemView.findViewById(R.id.cardUID);
                FirebaseUtils.pushLike(textUid.getText().toString());
                Log.d("dev_DB_status", "db_feed - OK");
            });
        } else {
            Log.d("dev_feed", "item is hidden");

            // hot-fix to remove auto-likes
            holder.card.setVisibility(View.GONE);
            holder.card.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
        }
    }

    @NonNull
    @Override
    public personsViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType) {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_feed_profile, parent, false);
        return new RecyclerFeedAdapter.personsViewholder(view);
    }

    class personsViewholder
            extends RecyclerView.ViewHolder {
        TextView bio, title, uid, interestsField, birthday;
        ImageView avatarView;
        View card;

        public personsViewholder(@NonNull View itemView) {
            super(itemView);
            card = itemView;
            avatarView = itemView.findViewById(R.id.feedAvatar);
            bio = itemView.findViewById(R.id.profileBio);
            title = itemView.findViewById(R.id.profileTitle);
            uid = itemView.findViewById(R.id.cardUID);
            birthday = itemView.findViewById(R.id.profileInfo);
            interestsField = itemView.findViewById(R.id.profileInterests);
        }
    }
}
