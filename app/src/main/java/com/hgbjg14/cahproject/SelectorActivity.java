package com.hgbjg14.cahproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class SelectorActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "CAH";

    //ACTUAL PLAYER DATA
    private _BlackCard currentBlackCard;
    private ArrayList<ArrayList<String>> playerCards = new ArrayList<>();
    private Integer currentShownWhiteCards = 0;
    private Integer playerId = 0;
    private ArrayList<String> chosenCards = new ArrayList<>();

    private void getDataFromHost(){
        //doHostStuff();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);
    }

    @Override
    public void onClick(View v) {

    }
}
