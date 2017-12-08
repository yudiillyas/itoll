package com.toelve.i_tollemployee;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

/**
 * Created by boyke purnomo 085883746312 on 7/12/2016.
 * copyright this script is under license of boyke purnomo please respect the author
 */
public class RegisterTask extends AsyncTask <String,Void,String> {
    Context ctx;
    private ProgressDialog dialog;
    RegisterTask(Context ctx) {
        this.ctx = ctx;

    }
    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(ctx);
        dialog.setMessage("Loading..Please Wait ....");
        dialog.show();
        dialog.setCancelable(false);
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... boyi) {
        String reg = "http://toelve.com/itoll/mobile/user/register";
        String method = boyi[0];

        if (method.equalsIgnoreCase("register")) {
            String nama = boyi[1];
            String username = boyi[2];
            String nomerhp = boyi[3];
            String password=boyi[4];
            String foto=boyi[5];
            String token=boyi[6];
            String latitude=boyi[7];
            String longitude=boyi[8];
            String alamat=boyi[9];
            String device_id=boyi[10];
            try {

                URL url = new URL(reg);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("nama", "UTF-8") + "=" + URLEncoder.encode(nama, "UTF-8") + "&" +
                        URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" +
                        URLEncoder.encode("nomerhp", "UTF-8") + "=" + URLEncoder.encode(nomerhp, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&" +
                        URLEncoder.encode("foto", "UTF-8") + "=" + URLEncoder.encode(foto, "UTF-8") + "&" +
                        URLEncoder.encode("token", "UTF-8") + "=" + URLEncoder.encode(token, "UTF-8") + "&" +
                        URLEncoder.encode("latitude", "UTF-8") + "=" + URLEncoder.encode(latitude, "UTF-8") + "&" +
                        URLEncoder.encode("longitude", "UTF-8") + "=" + URLEncoder.encode(longitude, "UTF-8")+ "&" +
                         URLEncoder.encode("alamat", "UTF-8") + "=" + URLEncoder.encode(alamat, "UTF-8")+ "&" +
                       URLEncoder.encode("device_id", "UTF-8") + "=" + URLEncoder.encode(device_id, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                InputStream is = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));
                String response = "";
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }
                bufferedReader.close();
                is.close();
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
    protected void onPostExecute(String s) {
        dialog.cancel();
        dialog.dismiss();
        try {
            if (s.trim().equalsIgnoreCase("Registration Success!")) {
                ctx.startActivity(new Intent(ctx, MainActivity.class));
                ((Activity) ctx).finish();
                Toast.makeText(ctx,s,Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ctx,s,Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(ctx, "ada kesalahan silahkan coba beberapa saat lagi", Toast.LENGTH_LONG).show();
        }
    }
}
