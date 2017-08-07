package com.example.bravodavid56.eatme.connectionActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.bravodavid56.eatme.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by bravodavid56 on 8/6/2017.
 */



public class ClientSocket extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Void>{
    private static String TAG = "ClientSocket";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        load();




    }

    @Override
    public Loader<Void> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Void>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                // start loading
            }

            @Override
            public Void loadInBackground() {
                // do socket stuff
                String iNetAddress = "Empty";
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    iNetAddress = extras.getString("host");
                    Log.e(TAG, "onCreate: " +"SUCCESS"+ " "+iNetAddress );

                }

                try {
                    Socket socket = new Socket();
                    socket.setReuseAddress(true);
                    socket.bind(null);
                    socket.connect(new InetSocketAddress(iNetAddress, 8000), 500);



                } catch (IOException e) {
                    Log.e(TAG, "loadInBackground: Could not connect." + e.toString());

                }

                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {

    }

    @Override
    public void onLoaderReset(Loader<Void> loader) {

    }

    public void load() {
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(1, null, this).forceLoad();
    }
}
