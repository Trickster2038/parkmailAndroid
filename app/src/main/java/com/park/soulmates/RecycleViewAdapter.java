package com.park.soulmates;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private ArrayList<UserModel> mData;

    public RecycleViewAdapter(ArrayList<UserModel> data) {
        mData = data;
    }

    @NonNull
    @Override
    public RecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_feed_profile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mProfileDescription.setText(mData.get(position).textDescription);
        Button likeButton = holder.itemView.findViewById(R.id.likeBtn);
        likeButton.setOnClickListener(v -> {
            TextView textUid = holder.itemView.findViewById(R.id.cardUID);
            LikePush.push(textUid.getText().toString());
            Log.d("log DB_status", "db_feed - OK");
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mProfileDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mProfileDescription = itemView.findViewById(R.id.profileDescription);
        }
    }
}