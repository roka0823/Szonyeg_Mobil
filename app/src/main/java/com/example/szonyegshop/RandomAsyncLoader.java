package com.example.szonyegshop;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.Random;

public class RandomAsyncLoader extends AsyncTaskLoader<String> {
    public RandomAsyncLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        Random random = new Random();
        int valamiszam = random.nextInt(11);
        int ido = valamiszam * 800;
        try{
            Thread.sleep(ido);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        return "Üdv, újra itt! :)";
    }
}
