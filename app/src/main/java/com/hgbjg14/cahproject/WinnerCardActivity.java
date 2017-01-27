package com.hgbjg14.cahproject;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class WinnerCardActivity extends AppCompatActivity implements View.OnClickListener  {

    @Override
    public void onBackPressed(){
        //super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner_card);

        //Set Blackcard
        TextView tv = (TextView)findViewById(R.id.winner_current_blackcard);
        tv.setBackgroundColor(Color.BLACK);
        tv.setTextColor(Color.WHITE);
        tv.setText(MainActivity.blackCards.get(MainActivity.currentBlackCardCounter - 1).text);

        //Set Whitecard
        ArrayList<String> currentPlayerCards = new ArrayList<>();

        for(_WhiteCard wc:MainActivity.winningCards){
            currentPlayerCards.add(wc.text);
        }

        TextView ptv = (TextView)findViewById(R.id.winner_current_whitecard);
        ptv.setText(android.text.TextUtils.join("\n\n", currentPlayerCards));;
        ptv.setTextColor(Color.BLACK);

        TextView wtv = (TextView)findViewById(R.id.winner_owner);
        wtv.setText("Best Card: " + MainActivity.winningCards.get(0).playerName);

        Button button = null;
        button = (Button)findViewById(R.id.winner_next_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, RankingActivity.class);
        startActivity(i);
    }
}
