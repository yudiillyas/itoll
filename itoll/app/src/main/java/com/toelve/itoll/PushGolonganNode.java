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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PushGolonganNode extends AsyncTask<String,Void,String> {
   private Context ctx;
    private String username;
   private AlertDialog alertDialog;
    PushGolonganNode(Context ctx) {
        this.ctx = ctx;
    }
    private ProgressDialog dialog=null;
    SharedPreferences boyprefs;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
         boyprefs =ctx. getSharedPreferences("itoll-management", Context.MODE_PRIVATE);
        alertDialog=new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Login Information");
        dialog = new ProgressDialog(ctx);
        dialog.setMessage(" Loading...");
        dialog.show();
        dialog.setCancelable(false);
    }

    @Override
    protected String doInBackground(String... params) {
        String login_url = "http://toelve.com/itoll/mobile/driver/pushgolongan";
        String method = params[0];
        if (method.equalsIgnoreCase("push")) {



            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
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
        try {

           // Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
            if(result.trim().contains("datagolongan")) {
                dialog.dismiss();
                boyprefs.edit().putString("datagolongan",result)
                .apply();
                ctx.startActivity(new Intent(ctx,Register.class));
                ((Activity)ctx).finish();
            }else  if(result.equalsIgnoreCase("kosong")) {
                dialog.dismiss();
                Toast.makeText(ctx, "tidak ADa Golongan.. Isi Dulu", Toast.LENGTH_SHORT).show();
            }

            else {
                dialog.dismiss();
                Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();


            }

        } catch (Exception e) {
            dialog.dismiss();
            Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
        }
    }
}
