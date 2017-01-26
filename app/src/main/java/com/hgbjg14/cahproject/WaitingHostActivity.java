package com.hgbjg14.cahproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WaitingHostActivity extends AppCompatActivity implements View.OnClickListener {

    public int playerNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_host);

        TextView tv = (TextView)findViewById(R.id.waiting_host_players);
        tv.setText(playerNumber + " Spieler");

        Button button = null;
        button = (Button)findViewById(R.id.waiting_host_start);
        button.setOnClickListener(this);
        button = (Button)findViewById(R.id.waiting_host_back);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.waiting_host_start:{
                Intent i = new Intent(WaitingHostActivity.this, MainGameActivity.class);
                startActivity(i);
            }
            break;
            case R.id.waiting_host_back:{
                finish();
            }
            break;
        }
    }
}
