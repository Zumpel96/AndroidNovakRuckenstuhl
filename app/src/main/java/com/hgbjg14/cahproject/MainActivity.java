package com.hgbjg14.cahproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.ChannelListener;
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
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "MyActivity";
    public static ArrayList<_Player> players = new ArrayList<>();
    public static ArrayList<_BlackCard> blackCards = new ArrayList<_BlackCard>();
    public static ArrayList<String> whiteCards = new ArrayList<String>();
    public static Integer currentWhiteCardCounter = 0;
    public static Integer currentBlackCardCounter = 0;
    public static ArrayList<_WhiteCard> winningCards = new ArrayList<>();
    public static ArrayList<String> playerCards = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        players.add(new _Player("You", 0));
        players.add(new _Player("POTUS44", 0));
        players.add(new _Player("@realDonaldTrump", 0));
        players.add(new _Player("Dickface", 0));
        players.add(new _Player("TheLegend27", 0));
        players.add(new _Player("Deez Nuts", 0));
        players.add(new _Player("Cyka Blyat", 0));
        players.add(new _Player("Bob", 0));

        Button button = null;
        button = (Button) findViewById(R.id.search_game_button);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.about_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = null;
        switch(v.getId()){
            case R.id.search_game_button:{
                loadCards("base_set", "expansion_1");
                i = new Intent(this, WaitingGeneralActivity.class);
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
