package com.hgbjg14.cahproject;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainGameActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "CAH";

    //ACTUAL PLAYER DATA
    private _BlackCard currentBlackCard;
    private Integer currentShownWhiteCard = 0;
    private String playerName = "You";
    private ArrayList<String> chosenCards = new ArrayList<>();

    private void getDataFromHost(){
        currentBlackCard = MainActivity.blackCards.get(MainActivity.currentBlackCardCounter);
        MainActivity.currentBlackCardCounter = (MainActivity.currentBlackCardCounter + 1) % MainActivity.blackCards.size();

        for(int i = 0; i < 10; i++){
            MainActivity.playerCards.add(MainActivity.whiteCards.get(MainActivity.currentWhiteCardCounter));
            MainActivity.currentWhiteCardCounter = (MainActivity.currentWhiteCardCounter + 1) % MainActivity.blackCards.size();
        }
    }

    @Override
    public void onBackPressed(){
        //super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        //Init
        getDataFromHost();

        //Set Blackcard
        TextView tv = (TextView)findViewById(R.id.maingame_current_blackcard);
        tv.setBackgroundColor(Color.BLACK);
        tv.setTextColor(Color.WHITE);
        tv.setText(currentBlackCard.text);

        //Set Whitecard
        TextView ptv = (TextView)findViewById(R.id.maingame_current_whitecard);
        ptv.setText(MainActivity.playerCards.get(currentShownWhiteCard));
        ptv.setTextColor(Color.BLACK);

        //Set Card Index
        TextView itv = (TextView)findViewById(R.id.maingame_current_index);
        itv.setText("Karte " + (currentShownWhiteCard + 1) + " von " + "10");

        Button button = null;
        button = (Button)findViewById(R.id.button_left);
        button.setOnClickListener(this);
        button = (Button)findViewById(R.id.button_right);
        button.setOnClickListener(this);
        button = (Button)findViewById(R.id.button_chose);
        button.setOnClickListener(this);
        button = (Button)findViewById(R.id.button_submit);
        button.setOnClickListener(this);
    }

    private void resetBorder(){
        TextView ptv = (TextView)findViewById(R.id.maingame_current_whitecard);
        ptv.setBackground(getResources().getDrawable(R.drawable.border_style_normal));
    }

    private void generateBorder(){
        TextView ptv = (TextView)findViewById(R.id.maingame_current_whitecard);
        if(chosenCards.contains(MainActivity.playerCards.get(currentShownWhiteCard))){
            ptv.setBackground(getResources().getDrawable(R.drawable.border_style_selected));
        }
    }

    @Override
    public void onClick(View v) {
        TextView ptv = (TextView)findViewById(R.id.maingame_current_whitecard);
        resetBorder();
        TextView satv = (TextView)findViewById(R.id.maingame_selected_answers);
        satv.setText(chosenCards.size() + " von " + currentBlackCard.pick + " Antworten ausgewählt");
        switch(v.getId()) {
            case R.id.button_left: {
                if(currentShownWhiteCard == 0){
                    currentShownWhiteCard = 9;
                } else {
                    currentShownWhiteCard--;
                }
            }
            break;
            case R.id.button_right:{
                if(currentShownWhiteCard == 9){
                    currentShownWhiteCard = 0;
                } else {
                    currentShownWhiteCard++;
                }
            }
            break;
            case R.id.button_chose:{
                if(chosenCards.contains(MainActivity.playerCards.get(currentShownWhiteCard))){
                    int i = chosenCards.indexOf(MainActivity.playerCards.get(currentShownWhiteCard));
                    chosenCards.remove(i);
                } else {
                    if(chosenCards.size() < currentBlackCard.pick){
                        chosenCards.add(MainActivity.playerCards.get(currentShownWhiteCard));
                    } else {
                        Toast.makeText(this, "Maximale Antwortenanzahl!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case R.id.button_submit:{
                if(chosenCards.size() == currentBlackCard.pick){
                    ArrayList<_WhiteCard> submitCards = new ArrayList<>();
                    for(String card:chosenCards){
                        submitCards.add(new _WhiteCard(card, this.playerName));
                    }
                    int playerWin = (int )(Math.random() * 100 + 1);
                    if(playerWin <= 25){
                        MainActivity.players.get(0).points  += currentBlackCard.pick;
                        MainActivity.winningCards = submitCards;
                    } else {
                        int botWin = (int )(Math.random() * 7 + 1);
                        MainActivity.players.get(botWin).points += currentBlackCard.pick;
                        ArrayList<_WhiteCard> botCards = new ArrayList<>();
                        for(int i = 0; i < currentBlackCard.pick; i++){
                            botCards.add(new _WhiteCard(MainActivity.whiteCards.get(MainActivity.currentWhiteCardCounter), MainActivity.players.get(botWin).name));
                            MainActivity.currentWhiteCardCounter = (MainActivity.currentWhiteCardCounter + 1) % MainActivity.blackCards.size();
                        }
                        MainActivity.winningCards = botCards;
                    }
                    MainActivity.playerCards.removeAll(chosenCards);
                    for(int i = 0; i < currentBlackCard.pick; i++){
                        MainActivity.playerCards.add(MainActivity.whiteCards.get(MainActivity.currentWhiteCardCounter));
                        MainActivity.currentWhiteCardCounter = (MainActivity.currentWhiteCardCounter + 1) % MainActivity.blackCards.size();
                    }

                    Intent i = new Intent(MainGameActivity.this, WaitingWinnerActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(this, "Zu wenig Antworten!", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
        satv.setText(chosenCards.size() + " von " + currentBlackCard.pick + " Antworten ausgewählt");

        ptv.setText(MainActivity.playerCards.get(currentShownWhiteCard));

        TextView itv = (TextView)findViewById(R.id.maingame_current_index);
        if(chosenCards.contains(MainActivity.playerCards.get(currentShownWhiteCard))){
            itv.setText("Karte " + (currentShownWhiteCard + 1) + " von " + "10 (" + (chosenCards.indexOf(MainActivity.playerCards.get(currentShownWhiteCard)) + 1) + ")");
        } else {
            itv.setText("Karte " + (currentShownWhiteCard + 1) + " von " + "10");
        }

        generateBorder();

    }
}


