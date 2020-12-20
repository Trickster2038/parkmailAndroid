package com.park.soulmates.views.chat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.park.soulmates.R;
import com.park.soulmates.models.AdvancedUserModel;
import com.park.soulmates.models.MessageModel;

import java.util.ArrayList;

public class CachedChatAdapter extends RecyclerView.Adapter<CachedChatAdapter.messagesViewHolder>{
    private ArrayList<MessageModel> data;

    public CachedChatAdapter(ArrayList<MessageModel> inData) {
        this.data = inData;
    }

    @NonNull
    @Override
    public CachedChatAdapter.messagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_message, parent, false);
        return new CachedChatAdapter.messagesViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull CachedChatAdapter.messagesViewHolder holder, int position) {
        MessageModel model = data.get(position);
        String currentUid = FirebaseAuth.getInstance().getUid();

        holder.messageText.setText(model.getMessageText());
        holder.messageUser.setText(model.getMessageUser());
        if (model.getMessageUser().equals(currentUid)) {
            Log.d("dev_chat_userMsg", model.getMessageUser() + " " + currentUid);
            // FIXME: remove hardcode to resources
            int myMsgColor = holder.messageCard.getContext().getResources().getColor(R.color.colorPrimary);
            // Color.rgb(150, 0, 200)
            holder.messageInnerCard.setCardBackgroundColor(myMsgColor);
            holder.messageText.setTextColor(Color.WHITE);
        } else {
            holder.messageInnerCard.setCardBackgroundColor(Color.LTGRAY);
            holder.messageSpace.setVisibility(View.GONE);
        }
        Log.d("dev_chat", "msg download tick");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    static class messagesViewHolder extends RecyclerView.ViewHolder {
        TextView messageText, messageUser, messageTime;
        ViewGroup messageCard;
        FrameLayout messageSpace;
        CardView messageInnerCard;

        public messagesViewHolder(@NonNull View v) {
            super(v);
            messageSpace = v.findViewById(R.id.msg_space);
            messageInnerCard = v.findViewById(R.id.msg_inner_container);
            messageCard = v.findViewById(R.id.msg_container);
            messageText = (TextView) v.findViewById(R.id.message_text);
            messageUser = (TextView) v.findViewById(R.id.message_user);
            messageTime = (TextView) v.findViewById(R.id.message_time);
        }


    }
}
