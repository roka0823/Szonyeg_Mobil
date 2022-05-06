package com.example.szonyegshop;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class CartActivity extends AppCompatActivity {
        private static final String LOG_TAG = com.example.szonyegshop.CartActivity.class.getName();
        private Context mContext;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_bovebben);

            Button vissza = (Button) findViewById(R.id.kosar_uritese);
        }


        @Override
        protected void onStart() {
            super.onStart();
            Log.i(LOG_TAG, "onStart");
        }

        @Override
        protected void onStop() {
            super.onStop();
            Log.i(LOG_TAG, "onStop");
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            Log.i(LOG_TAG, "onDestroy");
        }

        @Override
        protected void onPause() {
            super.onPause();
            Log.i(LOG_TAG, "onPause");
        }

        @Override
        protected void onResume() {
            super.onResume();
            Log.i(LOG_TAG, "onResume");
        }

    }
