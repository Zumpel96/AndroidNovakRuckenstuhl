package com.hgbjg14.cahproject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.ChannelListener;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ChannelListener {

    public static final String TAG = "MyActivity";
    public static final int PORT = 11111;
    private WifiP2pManager manager;
    private boolean isWifiP2pEnabled = false;
    private boolean retryChannel = false;
    private final IntentFilter intentFilter = new IntentFilter();
    private Channel channel;
    private BroadcastReceiver receiver = null;
    public static String testString = "hi";

    public MainActivity() {
    }

    public void setIsWifiP2pEnabled(boolean isWifiP2pEnabled) {
        this.isWifiP2pEnabled = isWifiP2pEnabled;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  Indicates a change in the Wi-Fi P2P status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);

        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);

        // Indicates the state of Wi-Fi P2P connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);

        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        this.manager = (WifiP2pManager)this.getSystemService(Context.WIFI_P2P_SERVICE);
        this.channel = this.manager.initialize(this, this.getMainLooper(), (ChannelListener)null);

        receiver = new CaHBroadcastReceiver(manager, channel, this);
        registerReceiver(receiver, intentFilter);

        Button button = null;
        button = (Button) findViewById(R.id.search_game_button);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.host_game_button);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.settings_button);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.about_button);
        button.setOnClickListener(this);
        testString = "hallo";
    }

    @Override
    public void onClick(View v) {
        Intent i = null;
        switch(v.getId()){
            case R.id.search_game_button:{
                i = new Intent(this, FindGameActivity.class);
                startActivity(i);
            }
            break;
            case R.id.host_game_button:{
                i = new Intent(this, HostGameActivity.class);
                startActivity(i);
            }
            break;
            case R.id.settings_button:{
                i = new Intent(this, SettingsActivity.class);
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

    public void onResume() {
        super.onResume();
        this.receiver = new CaHBroadcastReceiver(this.manager, this.channel, this);
        this.registerReceiver(this.receiver, this.intentFilter);
    }

    public void onPause() {
        super.onPause();
        this.unregisterReceiver(this.receiver);
    }


    @Override
    public void onChannelDisconnected() {

    }
}
