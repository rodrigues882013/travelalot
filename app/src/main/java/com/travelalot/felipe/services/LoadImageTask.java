package com.travelalot.felipe.services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by rodri on 24/01/2017.
 */

public class LoadImageTask extends AsyncTask<String, Void, InputStream> {


    @Override
    protected InputStream doInBackground(String... params) {
        InputStream is = null;
        try {
            URL localUrl = new URL(params[0]);
            is = localUrl.openConnection().getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ERROR", e.getMessage());
        }
        return is;
    }

    @Override
    protected void onPostExecute(InputStream inputStream) {
        super.onPostExecute(inputStream);
    }
}
