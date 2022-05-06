package com.example.szonyegshop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class CartActivity extends AppCompatActivity implements View.OnClickListener{
        private static final String LOG_TAG = com.example.szonyegshop.CartActivity.class.getName();
        private Context mContext;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_cart);

            Button vissza = (Button) findViewById(R.id.vissza);

            vissza.setOnClickListener(this);
        }

         @Override
         public void onClick(View view) {

             if (view.getId() == R.id.vissza) {
                 Intent intent = new Intent(this, ShopListActivity.class);
                 startActivity(intent);

                 overridePendingTransition(R.anim.slide_in_row,R.anim.swipe_reverse);
             }
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
