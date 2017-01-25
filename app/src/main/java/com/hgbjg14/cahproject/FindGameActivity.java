package com.hgbjg14.cahproject;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FindGameActivity extends AppCompatActivity implements View.OnClickListener{
    private static ArrayList<String> connections;
    private  ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_game);

        getListData();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, connections);

        ListView lv = (ListView)findViewById(R.id.find_game_list);
        lv.setAdapter(adapter);
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = String.valueOf(parent.getItemAtPosition(position));
                Intent i = new Intent(FindGameActivity.this, MainGameActivity.class);
                startActivity(i);
            }
        });

        Button button = null;
        button = (Button)findViewById(R.id.find_game_refresh_button);
        button.setOnClickListener(this);
        button = (Button)findViewById(R.id.find_game_back_button);
        button.setOnClickListener(this);
    }

    private void getListData(){
        connections =  new ArrayList<>();
        connections.add("Empty Game");
        //MAKE MAGIC
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.find_game_refresh_button:{
                getListData();
                adapter.notifyDataSetChanged();
            }
            break;
            case R.id.find_game_back_button:{
                finish();
            }
            break;
        }
    }

}
