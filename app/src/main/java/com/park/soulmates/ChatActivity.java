package com.park.soulmates;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.park.soulmates.models.MessageModel;

public class ChatActivity extends AppCompatActivity {
    private RecyclerChatAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        TextView title = findViewById(R.id.chatTitle);
        title.setText(getIntent().getStringExtra("targetName"));


        FloatingActionButton fab =
                (FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.input);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                FirebaseDatabase.getInstance()
                        .getReference()
                        .child("users")
                        .child(FirebaseAuth.getInstance().getUid())
                        .child("chats")
                        .child(getIntent().getStringExtra("targetUID"))
                        .push()
                        .setValue(new MessageModel(input.getText().toString(),
                                FirebaseAuth.getInstance()
                                        .getUid())
                        );
                FirebaseDatabase.getInstance()
                        .getReference()
                        .child("users")
                        .child(getIntent().getStringExtra("targetUID"))
                        .child("chats")
                        .child(FirebaseAuth.getInstance().getUid())
                        .push()
                        .setValue(new MessageModel(input.getText().toString(),
                                FirebaseAuth.getInstance()
                                        .getUid())
                        );

                // Clear the input
                input.setText("");
            }
        });


        DatabaseReference base = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getUid())
                .child("chats")
                .child(getIntent().getStringExtra("targetUID"));
        FirebaseRecyclerOptions<MessageModel> options
                = new FirebaseRecyclerOptions.Builder<MessageModel>()
                .setQuery(base, MessageModel.class)
                .build();
        mAdapter = new RecyclerChatAdapter(options);

        RecyclerView feedRecycler = findViewById(R.id.list_of_messages);
        feedRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        feedRecycler.setAdapter(mAdapter);
    }



//    @Override
//    public View onCreateView(ViewGroup container,
//                             Bundle savedInstanceState) {
//        //View view = inflater.inflate(R.layout.activity_chat, container, false);
//        RecyclerView feedRecycler = view.findViewById(R.id.list_of_messages);
//        feedRecycler.setLayoutManager(new LinearLayoutManager(inflater.getContext(), LinearLayoutManager.VERTICAL, false));
//        feedRecycler.setAdapter(mAdapter);
//        return view;
//    }

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