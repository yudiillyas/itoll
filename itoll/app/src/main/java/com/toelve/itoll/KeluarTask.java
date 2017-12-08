package com.toelve.itoll;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

public class KeluarTask extends AsyncTask<String,Void,String> {
   private  Context ctx;
    private String username;
   private  AlertDialog alertDialog;
    KeluarTask(Context ctx) {
        this.ctx = ctx;
    }
    private ProgressDialog dialog=null;
    private SharedPreferences boyprefs;

    @Override
    protected void onPreExecute() {
        boyprefs =ctx. getSharedPreferences("itoll-management", Context.MODE_PRIVATE);
        alertDialog=new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Login Information");
        dialog = new ProgressDialog(ctx);
        dialog.setMessage(" Loading...");
        dialog.show();
        dialog.setCancelable(false);
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String login_url = "http://toelve.com/itoll/mobile/driver/keluar";
        String method = params[0];
        if (method.equalsIgnoreCase("keluar")) {
            username = params[1];
            String namapintu=params[2];
            String pintuke=params[3];
            String noplat=params[4];
            String jam=params[5];
            String tanggal=params[6];


            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" +
                        URLEncoder.encode("namapintu", "UTF-8") + "=" + URLEncoder.encode(namapintu, "UTF-8") + "&" +
                        URLEncoder.encode("pintuke", "UTF-8") + "=" + URLEncoder.encode(pintuke, "UTF-8") + "&" +
                        URLEncoder.encode("jam", "UTF-8") + "=" + URLEncoder.encode(jam, "UTF-8")+ "&" +
                URLEncoder.encode("tanggal", "UTF-8") + "=" + URLEncoder.encode(tanggal, "UTF-8")+ "&" +
                URLEncoder.encode("noplat", "UTF-8") + "=" + URLEncoder.encode(noplat, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
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
        try {
                dialog.dismiss();

            if (result.trim().equalsIgnoreCase("Sukses")) {
                String username=boyprefs.getString("username","");
                String password=boyprefs.getString("password","");
                String token=boyprefs.getString("token","");
                boyprefs.edit().remove("tunggukeluar").apply();
                new LoginTask(ctx).execute("login", username, password, token);
            } else {
                boyprefs.edit().putBoolean("saving",false).apply();
                Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();


            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ctx, "Data Error you have to relogin to be save", Toast.LENGTH_SHORT).show();
            boyprefs.edit().putBoolean("saving",false).apply();
        }
    }
}
