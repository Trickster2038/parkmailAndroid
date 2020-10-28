
package com.park.soulmates;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.park.soulmates.R;

// FirebaseRecyclerAdapter is a class provided by 
// FirebaseUI. it provides functions to bind, adapt and show 
// database contents in a Recycler View

public class RecycleViewAdapter extends FirebaseRecyclerAdapter<
       UserModel, RecycleViewAdapter.personsViewholder> {

    public RecycleViewAdapter(
            @NonNull FirebaseRecyclerOptions<UserModel> options)
    {
        super(options);
    }

    // Function to bind the view in Card view(here 
    // "person.xml") iwth data in 
    // model class(here "person.class") 
    @Override
    protected void onBindViewHolder(@NonNull personsViewholder holder,
                     int position, @NonNull UserModel model)
    {

        // Add firstname from model class (here 
        // "person.class")to appropriate view in Card 
        // view (here "person.xml") 
        holder.bio.setText(model.getBio());

        Button likeButton = holder.itemView.findViewById(R.id.likeBtn);
        likeButton.setOnClickListener(v -> {
            TextView textUid = holder.itemView.findViewById(R.id.cardUID);
            LikePush.push(textUid.getText().toString());
            Log.d("log DB_status", "db_feed - OK");
            });
    }

    // Function to tell the class about the Card view (here 
    // "person.xml")in 
    // which the data will be shown 
    @NonNull
    @Override
    public personsViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_feed_profile, parent, false);
        return new RecycleViewAdapter.personsViewholder(view);
    }


    // Sub Class to create references of the views in Crad
    // view (here "person.xml") 
    class personsViewholder
            extends RecyclerView.ViewHolder {
        TextView bio;
        public personsViewholder(@NonNull View itemView)
        {
            super(itemView);
            bio
                    = itemView.findViewById(R.id.profileDescription);
        }
    }
}
