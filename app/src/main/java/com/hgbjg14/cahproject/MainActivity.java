package com.hgbjg14.cahproject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button = null;
        button = (Button) findViewById(R.id.search_game_button);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.host_game_button);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.settings_button);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.about_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = null;
        switch(v.getId()){
            case R.id.search_game_button:{
                i = new Intent(this, MainGameActivity.class);
                startActivity(i);
            }
            break;
            case R.id.host_game_button:{

            }
            break;
            case R.id.settings_button:{
                i = new Intent(this, SettingsActivity.class);
                startActivity(i);
            }
            break;
            case R.id.about_button:{
                i = new Intent(this, AboutActivity.class);
                startActivity(i);
            }
            break;
        }
    }
}
