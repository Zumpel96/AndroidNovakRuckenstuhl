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
import java.io.Console;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SelectorActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "CAH";

    //DUMMY DATA WHICH WE NORMALLY GET FROM HOST
    private ArrayList<_BlackCard> blackCards = new ArrayList<_BlackCard>();
    private ArrayList<String> whiteCards = new ArrayList<String>();
    private Integer currentWhiteCardCounter = 0;
    private Integer currentBlackCardCounter = 0;

    //ACTUAL PLAYER DATA
    private _BlackCard currentBlackCard;
    private ArrayList<ArrayList<_WhiteCard>> playerCards = new ArrayList<>();
    private Integer currentShownWhiteCards = 0;
    private ArrayList<_WhiteCard> chosenCards = null;
    private Integer numberOfPlayers = 0;

    private void getDataFromHost(){
        doHostStuff();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);

        numberOfPlayers = 6;

        getDataFromHost();
        long seed = System.nanoTime();
        Collections.shuffle(playerCards, new Random(seed));

        //Set Blackcard
        TextView tv = (TextView)findViewById(R.id.selector_current_blackcard);
        tv.setBackgroundColor(Color.BLACK);
        tv.setTextColor(Color.WHITE);
        tv.setText(currentBlackCard.text);

        //Set Whitecard
        ArrayList<String> currentPlayerCards = new ArrayList<>();

        for(_WhiteCard wc:playerCards.get(currentShownWhiteCards)){
            currentPlayerCards.add(wc.text);
        }

        TextView ptv = (TextView)findViewById(R.id.selector_current_whitecard);
        ptv.setText(android.text.TextUtils.join("\n\n", currentPlayerCards));;
        ptv.setTextColor(Color.BLACK);

        //Set Card Index
        TextView itv = (TextView)findViewById(R.id.selector_current_index);
        itv.setText("Spieler " + (currentShownWhiteCards + 1) + " von " + (numberOfPlayers - 1));

        Button button = null;
        button = (Button)findViewById(R.id.selector_button_left);
        button.setOnClickListener(this);
        button = (Button)findViewById(R.id.selector_button_right);
        button.setOnClickListener(this);
        button = (Button)findViewById(R.id.selector_button_chose);
        button.setOnClickListener(this);
        button = (Button)findViewById(R.id.selector_button_submit);
        button.setOnClickListener(this);
    }

    private void resetBorder(){
        TextView ptv = (TextView)findViewById(R.id.selector_current_whitecard);
        ptv.setBackground(getResources().getDrawable(R.drawable.border_style_normal));
    }

    private void generateBorder(){
        TextView ptv = (TextView)findViewById(R.id.selector_current_whitecard);
        if(chosenCards == playerCards.get(currentShownWhiteCards)){
            ptv.setBackground(getResources().getDrawable(R.drawable.border_style_selected));
        }
    }

    @Override
    public void onClick(View v) {
        TextView ptv = (TextView)findViewById(R.id.selector_current_whitecard);
        resetBorder();
        switch(v.getId()) {
            case R.id.selector_button_left:{
                Log.d("BLABLA", String.valueOf(currentShownWhiteCards));
                if(currentShownWhiteCards == 0){
                    currentShownWhiteCards = (numberOfPlayers - 2);
                } else {
                    currentShownWhiteCards--;
                }
            }
            break;
            case R.id.selector_button_right:{
                if(currentShownWhiteCards + 1 == numberOfPlayers - 1){
                    currentShownWhiteCards = 0;
                } else {
                    currentShownWhiteCards++;
                }
            }
            break;
            case R.id.selector_button_chose:{
                if(chosenCards == playerCards.get(currentShownWhiteCards)){
                    chosenCards = null;
                } else {
                    if(chosenCards == null){
                        chosenCards = playerCards.get(currentShownWhiteCards);
                    } else {
                        Toast.makeText(this, "Auswahl vorhanden!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case R.id.selector_button_submit:{
                if(chosenCards != null){
                    String winner = chosenCards.get(0).playerName;
                    //DO MAGIC
                    Toast.makeText(this, "Magix! (Btw... " + winner + " won this round xd", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Kein Sieger ausgewählt!", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }

        ArrayList<String> currentPlayerCards = new ArrayList<>();
        for(_WhiteCard wc:playerCards.get(currentShownWhiteCards)){
            currentPlayerCards.add(wc.text);
        }

        ptv.setText(android.text.TextUtils.join("\n\n", currentPlayerCards));;
        ptv.setTextColor(Color.BLACK);

        TextView itv = (TextView)findViewById(R.id.selector_current_index);
        itv.setText("Spieler " + (currentShownWhiteCards + 1) + " von " + (numberOfPlayers - 1));

        generateBorder();

    }

    private void doHostStuff(){
        loadCards("base_set");
        currentBlackCard = blackCards.get(currentBlackCardCounter);
        currentBlackCardCounter = (currentBlackCardCounter + 1) % blackCards.size();

        for(int i = 0; i < numberOfPlayers - 1; i++){
            ArrayList<_WhiteCard> tempList = new ArrayList<>();
            for(int j = 0; j < currentBlackCard.pick; j++){
                tempList.add(new _WhiteCard(MainActivity.whiteCards.get(currentWhiteCardCounter), "You"));
                currentWhiteCardCounter = (currentWhiteCardCounter + 1) % whiteCards.size();
            }
            playerCards.add(tempList);
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
