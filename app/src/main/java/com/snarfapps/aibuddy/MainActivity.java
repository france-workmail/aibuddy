package com.snarfapps.aibuddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OpenAIClient.OnTaskCompleted {


    private EditText etInput;
    private Button btnSend;

    private RecyclerView rvMessages;
    private MessagesAdapter messagesAdapter;

    private ArrayList<Message> messageArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageArrayList = new ArrayList<>();
        etInput = findViewById(R.id.etInput);
        btnSend = findViewById(R.id.btnSend);

        rvMessages = findViewById(R.id.rvMessages);
        messagesAdapter = new MessagesAdapter(messageArrayList);
        rvMessages.setAdapter(messagesAdapter);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);

        rvMessages.setLayoutManager(llm);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!etInput.getText().toString().isEmpty()) {
                    addMessage(new Message(etInput.getText().toString().trim(), Message.SENT_BY.ME));
                    new OpenAIClient(MainActivity.this)
                            .execute(etInput.getText().toString().trim());
                    etInput.getText().clear();
                    etInput.clearFocus();
                } else {
                    Toast.makeText(getApplicationContext(), "Please write something for AI Buddy", Toast.LENGTH_SHORT).show();
                }
            }
        });

        generateFakeConvo();
    }

    @Override
    public void onTaskCompleted(String result){
        addMessage(new Message(result, Message.SENT_BY.AI));
    }

    @Override
    public void onTaskError(String er) {
        Toast.makeText(getApplicationContext(), er, Toast.LENGTH_SHORT).show();
    }

    private void generateFakeConvo(){
        addMessage(new Message("da fbkamghkab gh", Message.SENT_BY.ME));
        addMessage(new Message("dakufakhgmnshkgj s jafha", Message.SENT_BY.AI));
        addMessage(new Message("dakufakhgmnshkgj fbkamghkab jafha", Message.SENT_BY.ME));
        addMessage(new Message("dakufakhgmnshkgj fbkamghkab jafha", Message.SENT_BY.AI));
        addMessage(new Message("dakufakhgmnshkgj fbkamghkab abghjamvjahn da fac", Message.SENT_BY.AI));

    }

    private void addMessage(Message message){
        messageArrayList.add(message);
        messagesAdapter.notifyDataSetChanged();
        rvMessages.smoothScrollToPosition(messagesAdapter.getItemCount() - 1);
    }
}