package com.hgbjg14.cahproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

/**
 * Created by Zumpel on 23.01.2017.
 */

public class CardView extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MyActivity";

    private String inString = null;
    private ArrayList<BlackCard> blackCards = new ArrayList<BlackCard>();
    private ArrayList<String> whiteCards = new ArrayList<String>();
    private Integer currentWhiteCardCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        new CardView.LoadCards().execute("base_set");
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, whiteCards.get(currentWhiteCardCounter));
        currentWhiteCardCounter = (currentWhiteCardCounter + 1) % whiteCards.size();
    }


    private class LoadCards extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
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
                        blackCards.add(new BlackCard(currentObject.getString("text"), currentObject.getInt("pick")));

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

            return null;
        }
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

