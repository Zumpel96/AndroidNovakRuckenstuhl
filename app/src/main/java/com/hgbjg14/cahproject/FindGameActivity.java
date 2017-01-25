package com.hgbjg14.cahproject;

import android.app.ListActivity;
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

public class FindGameActivity extends ListActivity implements View.OnClickListener{
    private static ArrayList<String> connections = new ArrayList<>();
    private  ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_game);

        for(int i = 0; i < 20; i++){
            connections.add("Empty Game " +  i);
        }

        getListData();
        adapter = new ArrayAdapter<String>(getListView().getContext(), android.R.layout.simple_list_item_1, connections);
        getListView().setAdapter(adapter);

        final ListView lv = getListView();
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                String name = (String)lv.getItemAtPosition(position);
                Log.d("JLK", name);
            }
        });

        Button button = null;
        button = (Button)findViewById(R.id.find_game_refresh_button);
        button.setOnClickListener(this);
        button = (Button)findViewById(R.id.find_game_back_button);
        button.setOnClickListener(this);
    }

    private void getListData(){
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
