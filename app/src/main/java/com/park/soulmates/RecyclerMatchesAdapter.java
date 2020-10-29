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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RecyclerMatchesAdapter extends FirebaseRecyclerAdapter<
        Match, RecyclerMatchesAdapter.personsViewholder> {
    AdvancedUserModel mUserProfile = new AdvancedUserModel();
    static String testName="no name test";

    public RecyclerMatchesAdapter(
            @NonNull FirebaseRecyclerOptions<Match> options)
    {
        super(options);
    }

    // Function to bind the view in Card view(here
    // "person.xml") iwth data in
    // model class(here "person.class")
    @Override
    protected void onBindViewHolder(@NonNull RecyclerMatchesAdapter.personsViewholder holder,
                                    int position, @NonNull Match model)
    {

        // Add firstname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference ref =
                FirebaseDatabase.getInstance().getReference().child("users").child(model.targetUID);
        Log.d("DB_matches_list_target", ref.toString());
        Log.d("DB_matches_list_account", mAuth.getUid());

        //AdvancedUserModel userProfile;

        ref.addValueEventListener(new ValueEventListener() {
                                      @Override
                                      public void onDataChange(DataSnapshot dataSnapshot) {
                                          AdvancedUserModel user = dataSnapshot.getValue(AdvancedUserModel.class);
                                          holder.title.setText(user.getName().concat(" ").concat(user.getSurname()));
                                          holder.uid.setText(user.getUid());
                                          holder.contacts.setText(user.getContacts());
                                          Log.d("DB_matches","listener tick");
                                      }
                                      @Override
                                      public void onCancelled(DatabaseError databaseError) {
                                          Log.d("The read failed: ", String.valueOf(databaseError.getCode()));
                                      }
        });



        //holder.title.setText(mUserProfile.getName().concat(" ").concat(mUserProfile.getSurname()));
        //holder.uid.setText(mUserProfile.getUid());


//        Button likeButton = holder.itemView.findViewById(R.id.likeBtn);
//        likeButton.setOnClickListener(v -> {
//            TextView textUid = holder.itemView.findViewById(R.id.cardUID);
//            LikePush.push(textUid.getText().toString());
//            Log.d("log DB_status", "db_feed - OK");
//        });
    }

    // Function to tell the class about the Card view (here
    // "person.xml")in
    // which the data will be shown
    @NonNull
    @Override
    public RecyclerMatchesAdapter.personsViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_matches_profile, parent, false);
        return new RecyclerMatchesAdapter.personsViewholder(view);
    }


    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class personsViewholder
            extends RecyclerView.ViewHolder {
        TextView title, uid, interestsField, contacts;
        public personsViewholder(@NonNull View itemView)
        {
            super(itemView);
            contacts = itemView.findViewById(R.id.profileMatchesContacts);
            title = itemView.findViewById(R.id.profileMatchesTitle);
            uid  = itemView.findViewById(R.id.cardMatchUID);
            interestsField = itemView.findViewById(R.id.profileMatchesInterests);
        }
    }
}
