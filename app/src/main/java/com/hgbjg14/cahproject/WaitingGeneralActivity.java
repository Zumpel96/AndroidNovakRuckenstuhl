package com.hgbjg14.cahproject;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.concurrent.TimeUnit;

public class WaitingGeneralActivity extends AppCompatActivity {
    private Handler mHandler = new Handler();

    @Override
    public void onBackPressed(){
        //super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_general);

        mHandler.postDelayed(new Runnable() {
            public void run() {
                redirect();
            }
        }, 3000);


    }

    private void redirect(){
        Intent i = new Intent(this, MainGameActivity.class);
        startActivity(i);
    }
}
