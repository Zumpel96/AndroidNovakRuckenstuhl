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
    private WifiP2pManager manager;
    private boolean isWifiP2pEnabled = false;
    private boolean retryChannel = false;
    private final IntentFilter intentFilter = new IntentFilter();
    private Channel channel;
    private BroadcastReceiver receiver = null;

    /*
    private List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();

    private PeerListListener peerListListener = new PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peerList) {

            List<WifiP2pDevice> refreshedPeers = peerList.getDeviceList();
            if (!refreshedPeers.equals(peers)) {
                peers.clear();
                peers.addAll(refreshedPeers);

                // If an AdapterView is backed by this data, notify it
                // of the change.  For instance, if you have a ListView of
                // available peers, trigger an update.
                //((WiFiPeerListAdapter) getListAdapter()).notifyDataSetChanged();

                // Perform any other updates needed based on the new list of
                // peers connected to the Wi-Fi P2P network.
            }

            if (peers.size() == 0) {
                Log.d(MainActivity.TAG, "No devices found");
                return;
            }
        }
    }*/

    public MainActivity() {
    }

    public void setIsWifiP2pEnabled(boolean isWifiP2pEnabled) {
        this.isWifiP2pEnabled = isWifiP2pEnabled;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.intentFilter.addAction("android.net.wifi.p2p.STATE_CHANGED");
        this.intentFilter.addAction("android.net.wifi.p2p.PEERS_CHANGED");
        this.intentFilter.addAction("android.net.wifi.p2p.CONNECTION_STATE_CHANGE");
        this.intentFilter.addAction("android.net.wifi.p2p.THIS_DEVICE_CHANGED");
        this.manager = (WifiP2pManager)this.getSystemService(Context.WIFI_P2P_SERVICE);
        this.channel = this.manager.initialize(this, this.getMainLooper(), (ChannelListener)null);

        Button button = null;
        button = (Button) findViewById(R.id.search_game_button);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.host_game_button);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.settings_button);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.about_button);
        button.setOnClickListener(this);
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
