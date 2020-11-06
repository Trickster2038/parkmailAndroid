package com.park.soulmates;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
    private String currentUid;

    public RecyclerChatAdapter(
            @NonNull FirebaseRecyclerOptions<MessageModel> options) {
        super(options);
        currentUid = FirebaseAuth.getInstance().getUid();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onBindViewHolder(@NonNull messagesViewHolder holder,
                                    int position, @NonNull MessageModel model) {
        //String currentUId = FirebaseAuth.getInstance().getUid();
        holder.messageText.setText(model.getMessageText());
        holder.messageUser.setText(model.getMessageUser());
        if(model.getMessageUser().equals(currentUid)){
            Log.d("dev_chat_userMsg", model.getMessageUser() + " " + currentUid);
            // FIXME: remove hardcode to resources
            holder.messageInnerCard.setBackgroundColor(Color.rgb(150,0,200));
            holder.messageText.setTextColor(Color.WHITE);
        } else {
            holder.messageInnerCard.setBackgroundColor(Color.LTGRAY);
            holder.messageSpace.setVisibility(View.GONE);
        }
        Log.d("dev_chat", "msg download tick");

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
        ViewGroup messageCard;
        FrameLayout messageInnerCard, messageSpace;

        public messagesViewHolder(@NonNull View v) {
            super(v);
            messageSpace = v.findViewById(R.id.msg_space);
            messageInnerCard = v.findViewById(R.id.msg_inner_container);
            messageCard  = v.findViewById(R.id.msg_container);
            messageText = (TextView)v.findViewById(R.id.message_text);
            messageUser = (TextView)v.findViewById(R.id.message_user);
            messageTime = (TextView)v.findViewById(R.id.message_time);
        }
    }
}
