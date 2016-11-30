package com.dogsavior.dogsavior.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.dogsavior.dogsavior.R;

/**
 * Created by KL on 2016/11/28 0028.
 */

public class WelcomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.welcome_activity);

        initIntent();
    }

    private void initIntent() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    Intent intent = new Intent(getApplication(),MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
