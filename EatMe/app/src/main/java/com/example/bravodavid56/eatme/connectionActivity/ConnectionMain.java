package com.example.bravodavid56.eatme.connectionActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.bravodavid56.eatme.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bravodavid56 on 8/4/2017.
 */

public class ConnectionMain extends AppCompatActivity implements  WifiP2pManager.PeerListListener , WifiP2pManager.ConnectionInfoListener{

    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private BroadcastReceiver mReceiver;
    private final IntentFilter mIntentFilter = new IntentFilter();
    private WifiP2pDevice device = new WifiP2pDevice();
    private WifiP2pConfig config = new WifiP2pConfig();

    private final String TAG = "HEY YOU MADE IT ";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        //Create the necessary manager and Channel
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);

        //Register for the events we need to capture

        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                // Toast.makeText(ConnectionMain.this, "HEY DUDE", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int reason) {

            }
        });

    }


    @Override
    public void onPeersAvailable(WifiP2pDeviceList peers) {
        List<WifiP2pDevice> peerList = new ArrayList<>();

        peerList.addAll(peers.getDeviceList());

        if (peerList.size() > 0) {
            WifiP2pDevice device = peerList.get(0);
            WifiP2pConfig config = new WifiP2pConfig();
            config.wps.setup = WpsInfo.PBC;
            config.deviceAddress = device.deviceAddress;
            mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {

                @Override
                public void onSuccess() {
                     //Toast.makeText(ConnectionMain.this, "YOU ARE CONNECTED", Toast.LENGTH_LONG).show();
                    //success logic
                }

                @Override
                public void onFailure(int reason) {
                    //failure logic
                }
            });


        }


    }


    @Override
    protected void onResume() {
        super.onResume();
        mReceiver = new ConnectionReceiver(mManager, mChannel, this);
        registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }


    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo info) {
        if (info.isGroupOwner) {
            Toast.makeText(this, "YOU ARE THE OWNER", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "YOU ARE NOT THE OWNER", Toast.LENGTH_SHORT).show();
        }
        Log.e(TAG, "onConnectionInfoAvailable: "+info.toString());
    }
}
