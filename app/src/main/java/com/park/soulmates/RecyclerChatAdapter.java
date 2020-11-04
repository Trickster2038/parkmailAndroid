package com.park.soulmates;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class RecyclerChatAdapter extends FirebaseRecyclerAdapter
        <MessageModel, RecyclerChatAdapter.messagesViewHolder> {

    public RecyclerChatAdapter(
            @NonNull FirebaseRecyclerOptions<MessageModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull messagesViewHolder holder,
                                    int position, @NonNull MessageModel model) {
        holder.messageText.setText(model.getMessageText());
        holder.messageUser.setText(model.getMessageUser());

//        DatabaseReference ref = FirebaseDatabase
//                .getInstance()
//                .getReference()
//                .child("users")
//                .child(FirebaseAuth.getInstance().getUid())
//                .child("chats");
//                // TODO: find target user
//               // .child(get);
//        getSnapshots().
//        Log.d("RecyclerMatchesAdapter", ref.toString());
//
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                  MessageModel msg = dataSnapshot.getValue(MessageModel.class);
//
//                  holder.messageText.setText(msg.getMessageText());
//                  holder.messageUser.setText(msg.getMessageUser());
                  //holder.messageTime.setText(msg.getMessageTime());


//                holder.title.setText(user.getName().concat(" ").concat(user.getSurname()));
//                holder.uid.setText(user.getUid());
//                holder.contacts.setText(user.getContacts());
//                holder.interestsField.setText(user.getInterests().toString());
//
//                FirebaseStorage storage = FirebaseStorage.getInstance();
//                StorageReference storageReference = storage.getReference().child("users/" + user.getUid() + "/avatar" );
//
//                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        try {
//                            Glide.with(holder.avatarView).load(uri).into(holder.avatarView);
//                        } catch (Exception e){
//                            Log.d("dev_avatar_download", "404");
//                        }
//                        // Got the download URL for 'users/me/profile.png'
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        // Handle any errors
//                    }
//                });
//                Log.d("RecyclerMessagesAdapter", "listener tick");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.d("RecyclerMessagesAdapter", String.valueOf(databaseError.getCode()));
//            }
//        });

    }

    @NonNull
    @Override
    public messagesViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_message, parent, false);
        return new messagesViewHolder(view);
    }

    static class messagesViewHolder extends RecyclerView.ViewHolder {
        TextView messageText, messageUser, messageTime;

        public messagesViewHolder(@NonNull View v) {
            super(v);
            messageText = (TextView)v.findViewById(R.id.message_text);
            messageUser = (TextView)v.findViewById(R.id.message_user);
            messageTime = (TextView)v.findViewById(R.id.message_time);
        }
    }
}
