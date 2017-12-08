package com.toelve.itoll;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

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

public class AmpinTask extends AsyncTask<String,Void,String> {
   private  Context ctx;
    private String latitude,longitude;
    AmpinTask(Context ctx) {
        this.ctx = ctx;
    }

    private SharedPreferences boyprefs;

    @Override
    protected void onPreExecute() {
        boyprefs =ctx. getSharedPreferences("itoll-management", Context.MODE_PRIVATE);
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String login_url = "http://toelve.com/itoll/mobile/driver/ambilpintu";
        String method = params[0];
        if (method.equalsIgnoreCase("ambilString")) {
            latitude = params[1];
            longitude = params[2];



            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
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
       // Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
        try {

            if (result.contains("datapintu")) {
                boyprefs.edit().putString("datapintu",result).apply();
            } else {

                Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();


            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ctx, "Data Error you have to relogin to be save", Toast.LENGTH_SHORT).show();
            boyprefs.edit().putBoolean("saving",false).apply();
        }
    }
}
