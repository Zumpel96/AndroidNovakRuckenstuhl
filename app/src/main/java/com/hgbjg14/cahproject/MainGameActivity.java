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

public class MainGameActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "CAH";

    //DUMMY DATA WHICH WE NORMALLY GET FROM HOST
    private ArrayList<_BlackCard> blackCards = new ArrayList<_BlackCard>();
    private ArrayList<String> whiteCards = new ArrayList<String>();
    private Integer currentWhiteCardCounter = 0;
    private Integer currentBlackCardCounter = 0;

    //ACTUAL PLAYER DATA
    private _BlackCard currentBlackCard;
    private ArrayList<String> playerCards = new ArrayList<String>();
    private Integer currentShownWhiteCard = 0;
    private Integer playerId = 0;
    private ArrayList<String> chosenCards = new ArrayList<>();

    private void getDataFromHost(){
        doHostStuff();
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
        ptv.setText(playerCards.get(currentShownWhiteCard));
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
        if(chosenCards.contains(playerCards.get(currentShownWhiteCard))){
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
                if(chosenCards.contains(playerCards.get(currentShownWhiteCard))){
                    int i = chosenCards.indexOf(playerCards.get(currentShownWhiteCard));
                    chosenCards.remove(i);
                } else {
                    if(chosenCards.size() < currentBlackCard.pick){
                        chosenCards.add(playerCards.get(currentShownWhiteCard));
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
                        submitCards.add(new _WhiteCard(card, this.playerId));
                    }
                    //DO MAGIC

                    Intent i = new Intent(MainGameActivity.this, SelectorActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(this, "Zu wenig Antworten!", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
        satv.setText(chosenCards.size() + " von " + currentBlackCard.pick + " Antworten ausgewählt");

        ptv.setText(playerCards.get(currentShownWhiteCard));

        TextView itv = (TextView)findViewById(R.id.maingame_current_index);
        if(chosenCards.contains(playerCards.get(currentShownWhiteCard))){
            itv.setText("Karte " + (currentShownWhiteCard + 1) + " von " + "10 (" + (chosenCards.indexOf(playerCards.get(currentShownWhiteCard)) + 1) + ")");
        } else {
            itv.setText("Karte " + (currentShownWhiteCard + 1) + " von " + "10");
        }

        generateBorder();

    }

    private void doHostStuff(){
        loadCards("base_set");
        currentBlackCard = blackCards.get(currentBlackCardCounter);
        currentBlackCardCounter = (currentBlackCardCounter + 1) % blackCards.size();

        for(int i = 0; i < 10; i++){
            playerCards.add(whiteCards.get(currentWhiteCardCounter));
            currentWhiteCardCounter = (currentWhiteCardCounter + 1) % blackCards.size();
        }
    }

    private void loadCards(String... params){
        blackCards.clear();
        whiteCards.clear();
        for (String param : params) {
            try {

                int tempId = getResources().getIdentifier(param, "raw", getPackageName());
                String jsonString = GetStringFromRawResource(tempId);

                JSONObject reader = new JSONObject(jsonString);
                JSONArray blackCardsArray = reader.getJSONArray("blackCards");
                JSONArray whiteCardsArray = reader.getJSONArray("whiteCards");

                JSONObject currentObject = null;
                int temp = 0;
                for (int i = 0; i < blackCardsArray.length(); i++) {
                    currentObject = blackCardsArray.getJSONObject(i);
                    blackCards.add(new _BlackCard(currentObject.getString("text"), currentObject.getInt("pick")));

                    temp = i;
                }
                for (int i = 0; i < whiteCardsArray.length(); i ++) {
                    whiteCards.add(whiteCardsArray.getString(i));
                    temp = i;
                }
            }
            catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }
        }
        long seed = System.nanoTime();
        Collections.shuffle(whiteCards, new Random(seed));
        Collections.shuffle(blackCards, new Random(seed));
    }

    private String GetStringFromRawResource(int id) {
        String retString = "";
        try {
            InputStream inStream = getResources().openRawResource(id);
            retString = StreamToString(inStream);
            inStream.close();
        }
        catch (Exception e) {

        }

        return retString;
    }

    private String StreamToString(InputStream inStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();
        }
        catch (Exception e) {

        }
        return sb.toString();
    }
}


