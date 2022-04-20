package com.example.szonyegshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();

    EditText userNameET;
    EditText passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameET = findViewById(R.id.EditTextUsername);
        passwordET = findViewById(R.id.EditTextPassword);
    }

    public void login(View view) {

        String usernameStr = userNameET.getText().toString();
        String passwordStr = passwordET.getText().toString();

        Log.i(LOG_TAG, "Bejelentkezett " + usernameStr + ", jelszó " + passwordStr);
    }
}