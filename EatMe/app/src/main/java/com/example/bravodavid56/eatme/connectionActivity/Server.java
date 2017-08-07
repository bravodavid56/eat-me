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
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Created by bravodavid56 on 8/6/2017.
 */

public class Server extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Void> {
    private static  String TAG = "Server";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

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
                ServerSocket serverSocket = null;
                try {
                    serverSocket = new ServerSocket();
                    serverSocket.setReuseAddress(true);
                    serverSocket.bind(new InetSocketAddress(8000));
                    Socket socket = serverSocket.accept();

                    Log.e(TAG, "loadInBackground: " + "Successfully created server");




                } catch (IOException e) {
                    Log.e(TAG, "loadInBackground: Could not start server." + e.toString());

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
