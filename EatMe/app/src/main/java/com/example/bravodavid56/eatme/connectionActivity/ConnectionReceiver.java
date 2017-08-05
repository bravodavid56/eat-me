package com.example.bravodavid56.eatme.connectionActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;

import com.example.bravodavid56.eatme.MainActivity;
import com.example.bravodavid56.eatme.R;

/**
 * Created by bravodavid56 on 8/4/2017.
 */

public class ConnectionReceiver extends BroadcastReceiver {
    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private ConnectionMain mActivity;

    public ConnectionReceiver() {
    }

    public ConnectionReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel,
                                       ConnectionMain activity) {
        super();
        this.mManager = manager;
        this.mChannel = channel;
        this.mActivity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

            if(mManager != null){
                mManager.requestPeers(mChannel,mActivity);

            }


        }

    }
}
