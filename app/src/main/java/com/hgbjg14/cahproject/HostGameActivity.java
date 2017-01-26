package com.hgbjg14.cahproject;

import android.content.Intent;
import android.Manifest;
import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class HostGameActivity extends AppCompatActivity implements View.OnClickListener, WifiP2pManager.PeerListListener {

    private NsdManager nsdManager;
    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_game);


        this.manager = (WifiP2pManager)this.getSystemService(Context.WIFI_P2P_SERVICE);
        this.channel = this.manager.initialize(this, this.getMainLooper(), (WifiP2pManager.ChannelListener)null);
        Button button = null;
        button = (Button)findViewById(R.id.button_host_submit);
        button.setOnClickListener(this);
        button = (Button)findViewById(R.id.button_back_host);
        button.setOnClickListener(this);


    }

    private void registerService(Context context) {
        /*NsdServiceInfo serviceInfo = new NsdServiceInfo();
        serviceInfo.setServiceName("CaH");
        serviceInfo.setServiceType("_http._tcp.");
        serviceInfo.setPort(MainActivity.PORT);
        nsdManager = (NsdManager)context.getSystemService(Context.NSD_SERVICE);

        nsdManager.registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, );*/
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_host_submit:{
                EditText gamename = (EditText)findViewById(R.id.hostgame_name);
                if( gamename.getText().toString().trim().equals("")){
                    Toast.makeText(this, "Spielname wird benötigt!", Toast.LENGTH_LONG).show();
                } else {
                    Map record = new HashMap();
                    record.put("listenport", String.valueOf(MainActivity.PORT));
                    record.put("buddyname", "CaH");
                    record.put("available", "visible");
                    WifiP2pDnsSdServiceInfo serviceInfo =
                            WifiP2pDnsSdServiceInfo.newInstance("cahP2P", "_presence.tcp", record);
                    manager.addLocalService(channel, serviceInfo, new WifiP2pManager.ActionListener() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFailure(int reason) {

                        }
                    });
                }
            }
            break;
            case R.id.button_back_host:{
                finish();
            }
            break;
        }
    }

    @Override
    public void onPeersAvailable(WifiP2pDeviceList peers) {

    }
}
