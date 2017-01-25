package com.hgbjg14.cahproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HostGameActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_game);

        Button button = null;
        button = (Button)findViewById(R.id.button_host_submit);
        button.setOnClickListener(this);
        button = (Button)findViewById(R.id.button_back_host);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_host_submit:{
                EditText gamename = (EditText)findViewById(R.id.hostgame_name);
                if( gamename.getText().toString().trim().equals("")){
                    Toast.makeText(this, "Spielname wird benötigt!", Toast.LENGTH_LONG).show();
                } else {

                }
            }
            break;
            case R.id.button_back_host:{
                finish();
            }
            break;
        }
    }
}
