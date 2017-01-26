package com.hgbjg14.cahproject;

import android.app.Activity;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.net.wifi.p2p.WifiP2pDevice;
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
import java.util.List;

public class FindGameActivity extends AppCompatActivity implements View.OnClickListener {
    private static ArrayList<String> connections;
    private  ArrayAdapter<String> adapter;
    private WifiP2pManager manager;
    private boolean isWifiP2pEnabled = false;
    private boolean retryChannel = false;
    private final IntentFilter intentFilter = new IntentFilter();
    private WifiP2pManager.Channel channel;
    private BroadcastReceiver receiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_game);

        this.intentFilter.addAction("android.net.wifi.p2p.STATE_CHANGED");
        this.intentFilter.addAction("android.net.wifi.p2p.PEERS_CHANGED");
        this.intentFilter.addAction("android.net.wifi.p2p.CONNECTION_STATE_CHANGE");
        this.intentFilter.addAction("android.net.wifi.p2p.THIS_DEVICE_CHANGED");
        this.manager = (WifiP2pManager)this.getSystemService(Context.WIFI_P2P_SERVICE);
        this.channel = this.manager.initialize(this, this.getMainLooper(), (WifiP2pManager.ChannelListener)null);

        getListData();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, connections);

        ListView lv = (ListView)findViewById(R.id.find_game_list);
        lv.setAdapter(adapter);
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = String.valueOf(parent.getItemAtPosition(position));
                Intent i = new Intent(FindGameActivity.this, WaitingGeneralActivity.class);
                startActivity(i);
            }
        });

        Button button = null;
        button = (Button)findViewById(R.id.find_game_refresh_button);
        button.setOnClickListener(this);
        button = (Button)findViewById(R.id.find_game_back_button);
        button.setOnClickListener(this);
        Log.d(MainActivity.TAG, MainActivity.testString);
    }

    private void getListData(){
        connections =  new ArrayList<>();
        connections.add("Empty Game");
        manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                // Code for when the discovery initiation is successful goes here.
                // No services have actually been discovered yet, so this method
                // can often be left blank.  Code for peer discovery goes in the
                // onReceive method, detailed below.
                Log.d(MainActivity.TAG, "success");
            }

            @Override
            public void onFailure(int reasonCode) {
                // Code for when the discovery initiation fails goes here.
                // Alert the user that something went wrong.
                Log.d(MainActivity.TAG, "failure");
            }
        });
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
