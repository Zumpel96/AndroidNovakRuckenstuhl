package com.hgbjg14.cahproject;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class BlackCardActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MyActivity";

    private List<BlackCard> blackCards = new LinkedList<>();
    private List<String> whiteCards = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_cards);
        Button button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View cardView = vi.inflate(R.layout.fragment_card, R.id.cardView, false);
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
                    Log.d(TAG, Integer.toString(temp));
                    Log.d(TAG, Integer.toString(blackCards.size()));
                    for (int i = 0; i < whiteCardsArray.length(); i ++) {
                        whiteCards.add(whiteCardsArray.getString(i));
                        temp = i;
                    }
                    Log.d(TAG, Integer.toString(temp));
                    Log.d(TAG, Integer.toString(whiteCards.size()));

                }
                catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
            }

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
