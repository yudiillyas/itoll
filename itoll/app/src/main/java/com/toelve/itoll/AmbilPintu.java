package com.toelve.itoll;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by ToElvE on 06/12/2017.
 */

public class AmbilPintu extends AsyncTask<String,Void,String> {
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private String login_url;
    private SharedPreferences boyprefs;
    AmbilPintu(Context ctx) {
        this.context = ctx;

    }
    @Override
    protected void onPreExecute() {
        String login_url = "http://toelve.com/itoll/mobile/driver/ambilpintu";
        boyprefs=context.getSharedPreferences("itoll-management",Context.MODE_PRIVATE);
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String metodenya=params[0];
        if(metodenya.equalsIgnoreCase("ambilString")){
            String latitude=params[1];
            String longitude=params[2];
            try{
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String response = "";
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                response += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
            return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        if (result.contains("datapintu")){
            boyprefs = context.getSharedPreferences("itoll-management", Context.MODE_PRIVATE);
            boyprefs.edit().putString("datapintu", result).apply();
        }
    }
}
