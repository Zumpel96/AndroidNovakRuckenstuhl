package com.hgbjg14.cahproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static java.util.Collections.addAll;

public class RankingActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onBackPressed(){
        //super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        ArrayList<_Player> ranking = new ArrayList<>();
        ranking.addAll(MainActivity.players);
        Collections.sort(ranking, new Comparator<_Player>() {
            @Override
            public int compare(_Player player1, _Player player2){
                if(player1.points > player2.points){
                    return -1;
                } else {
                    return 1;
                }
            }
        });

        ArrayList<String> sorted_ranking = new ArrayList<>();
        int i = 1;
        for(_Player p:ranking){
            sorted_ranking.add(i + ") " + p.name + " - " + p.points);
            i++;
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sorted_ranking);

        ListView lv = (ListView)findViewById(R.id.ranking_list);
        lv.setAdapter(adapter);

        Button button = null;
        button = (Button)findViewById(R.id.ranking_next_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, MainGameActivity.class);
        startActivity(i);
    }
}
