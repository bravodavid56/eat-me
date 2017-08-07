package com.example.bravodavid56.eatme.connectionActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Handler;

/**
 * Created by bravodavid56 on 8/6/2017.
 */

public class BluetoothService {
    private final BluetoothAdapter mAdapter;
    private final Handler mHandler;

    public BluetoothService(Context context, Handler handler) {
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        mHandler = handler;
    }
}
