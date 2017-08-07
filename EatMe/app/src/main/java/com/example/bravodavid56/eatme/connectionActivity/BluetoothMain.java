package com.example.bravodavid56.eatme.connectionActivity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.bravodavid56.eatme.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;



public class BluetoothMain extends AppCompatActivity {

    private String TAG = "BLUETOOTH_MAIN";
    private BluetoothAdapter mBluetoothAdapter = null;
    private String uuid ="b35b79b4-65ca-4e1f-bf33-99694cb85dd3";
    private final IntentFilter mIntentFilter = new IntentFilter();
    private AcceptThread mAcceptThread;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private String deviceDetails = "";

    private int PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION;

    private Handler mHandler;

    private BluetoothDevice device2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_main);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        mIntentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        mIntentFilter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
        mIntentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        mIntentFilter.addAction(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        mIntentFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);


        registerReceiver(mReceiver, mIntentFilter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: Receiver has been resumed." );
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "onResume: PERMISSION NOT GRANTED" );
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
        } else {
            Log.e(TAG, "onResume: PERMISSION GRANTED" );
            mIntentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        }
        registerReceiver(mReceiver, mIntentFilter);
    }



    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
        Log.e(TAG, "onPause: Receiver has been paused." );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onPause: The receiver has been unregistered." );
    }

    private class AcceptThread extends Thread {
        private final BluetoothServerSocket mServerSocket;
        public AcceptThread() {
            BluetoothServerSocket tmp = null;
            try {
                Log.e(TAG, "AcceptThread: Listening on adapter." );
                tmp = mBluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord("eatme", UUID.fromString(uuid));
            } catch (IOException e) {
                Log.e(TAG, "AcceptThread: " + e.toString() );
            }
            mServerSocket = tmp;
        }

        public void run() {
            BluetoothSocket socket = null;
            while (true) {
                try {
                    socket = mServerSocket.accept();
                } catch (IOException e) {
                    Log.e(TAG, "run: " + "Socket's accept() method failed" + e.toString());
                    break;
                }

                if (socket != null) {
                    // manage the socket on another thread;
                    cancel();
                    break;
                }

            }
        }

        public void cancel() {
            try {
                mServerSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "cancel: Failed closing the connect thread." );
            }
        }
    }

    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket
            // because mmSocket is final.
            BluetoothSocket tmp = null;
            mmDevice = device;

            try {
                // Get a BluetoothSocket to connect with the given BluetoothDevice.
                // MY_UUID is the app's UUID string, also used in the server code.
                tmp = device.createRfcommSocketToServiceRecord(UUID.fromString(uuid));
            } catch (IOException e) {
                Log.e(TAG, "Socket's create() method failed", e);
            }
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it otherwise slows down the connection.
            mBluetoothAdapter.cancelDiscovery();

            try {
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and return.
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                    Log.e(TAG, "Could not close the client socket", closeException);
                }
                return;
            }

            synchronized (BluetoothMain.this) {
                mConnectThread = null;
            }

            // The connection attempt succeeded. Perform work associated with
            // the connection in a separate thread.
            // manageMyConnectedSocket(mmSocket);
        }

        // Closes the client socket and causes the thread to finish.
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close the client socket", e);
            }
        }
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mSocket;
        private final InputStream mInput;
        private final OutputStream mOutput;
        private byte[] mBuffer;

        public ConnectedThread(BluetoothSocket socket) {
            mSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "ConnectedThread: On input/output stream" );
            }
            mInput = tmpIn;
            mOutput = tmpOut;
        }

        public void run(){
            mBuffer = new byte[1024];
            int numBytes;

            while (true) {
                try {
                    numBytes = mInput.read(mBuffer);
                    Message readMsg = mHandler.obtainMessage(0, numBytes, -1, mBuffer);
                    readMsg.sendToTarget();
                } catch (IOException e) {
                    Log.e(TAG, "run: on Connected thread" + e.toString());
                    break;
                }
            }
        }

        public void write(byte[] bytes) {
            try {
                mOutput.write(bytes);
                Message writtenMsg = mHandler.obtainMessage(1, -1,-1,mBuffer);
                writtenMsg.sendToTarget();
            } catch (IOException e) {
                Log.e(TAG, "write: in write() on Connected Thread" );
            }
        }

        public void cancel() {
            try {
                mSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }



    }


    @Override
    protected void onStart() {
        super.onStart();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enablebt = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enablebt,3);
        } else  {
            Log.e(TAG, "onStart: BLUETOOTH IS NOT ENABLED" );
            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE), 1);

        }
        Log.e(TAG, "onStart: STARTED" );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: RESULT" );
        mBluetoothAdapter.startDiscovery();
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();

                // 512 is the number indicating this bluetooth device is a phone
                if (device.getBluetoothClass().getMajorDeviceClass()==512) {
                    Log.e(TAG, "onReceive: "+deviceName + " IS A PHONE");
                    deviceDetails = deviceName + "\t" + device.getAddress();
                    device2 = device;
                    TextView tv = (TextView) findViewById(R.id.textView6);
                    tv.setText(deviceDetails);
                } else {
                    Log.e(TAG, "onReceive: "+deviceName + " IS NOT A PHONE");
                }
            } else if (BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED.equals(action)) {
                Log.e(TAG, "onReceive: CONNECTED" );
            } else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                Log.e(TAG, "onReceive: NOW IT IS CONNECTED");
            }
        }
    };

    public synchronized void serverStart(View view) {
        Log.e(TAG, "serverStart: SERVER STARTED" );
        if (mConnectThread != null) {
            mConnectedThread.cancel();
            mConnectThread = null;
        }
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        if (mAcceptThread == null) {
            mAcceptThread = new AcceptThread();
            Log.e(TAG, "serverStart: "+(mAcceptThread == null) );
            mAcceptThread.start();
        }
        Log.e(TAG, "serverStart: Now accepting connections." );
        TextView tv = (TextView) findViewById(R.id.textView6);
        tv.setText("Now accepting connections.");
    }

    public synchronized void clientStart(View view) {
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }
        if (device2 != null) {
            mConnectThread = new ConnectThread(device2);
            mConnectThread.start();
            TextView tv = (TextView) findViewById(R.id.textView6);
            tv.setText("Connecting. \t");
        }
    }

    public void writeTest(View view) {
        ConnectedThread r;
        Log.e(TAG, "writeTest: "+ mBluetoothAdapter.getBondedDevices().iterator().next().getName() );
        // TODO: Make sure the ConnectedThread is not null before writing to it.
//        synchronized(this) {
//            r = mConnectedThread;
//            if (r == null) {
//                Log.e(TAG, "writeTest: R is Null" );
//            }
//            r.write(new byte[]{1,0,0,1});
//        }

    }


}

