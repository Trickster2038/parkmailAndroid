package com.park.soulmates.views.chat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.park.soulmates.R;
import com.park.soulmates.models.MessageModel;
import com.park.soulmates.utils.FirebaseUtils;

public class ChatActivity extends AppCompatActivity {
    private RecyclerChatAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        prepareSending();
        initAdapter();
    }

    private void prepareSending() {
        //TextView title = findViewById(R.id.chatTitle);
        setTitle(getIntent().getStringExtra("targetName"));
        FloatingActionButton fab =
                (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText) findViewById(R.id.input);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                FirebaseUtils.sendMessage(getIntent().getStringExtra("targetUID"), input.getText().toString());
                input.setText("");
            }
        });
    }

    private void initAdapter() {
        FirebaseRecyclerOptions<MessageModel> options
                = new FirebaseRecyclerOptions.Builder<MessageModel>()
                .setQuery(FirebaseUtils.getChatReference(getIntent().getStringExtra("targetUID")), MessageModel.class)
                .build();
        mAdapter = new RecyclerChatAdapter(options);

        RecyclerView chatRecycler = findViewById(R.id.list_of_messages);
        chatRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        chatRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}