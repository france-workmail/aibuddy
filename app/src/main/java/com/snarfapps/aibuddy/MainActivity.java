package com.snarfapps.aibuddy;

import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity implements OpenAIClient.OnTaskCompleted {


    private EditText etInput,etResult;
    private Button btnSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etInput = findViewById(R.id.etInput);
        etResult = findViewById(R.id.etResult);
        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new OpenAIClient(MainActivity.this).execute(etInput.getText().toString().trim());
            }
        });

    }

    @Override
    public void onTaskCompleted(String result){

        etResult.setText(result);
        Log.e("OPEN AI",result.toString());
    }

    @Override
    public void onTaskError(String er) {
        Toast.makeText(getApplicationContext(), er, Toast.LENGTH_SHORT).show();
    }
}