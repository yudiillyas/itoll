package com.toelve.i_tollemployee;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
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
public class BuatpintuTask extends AsyncTask <String,Void,String> {
    Context ctx;
    private ProgressDialog dialog;
    private SharedPreferences boyprefs;
    BuatpintuTask(Context ctx) {
        this.ctx = ctx;

    }
    @Override
    protected void onPreExecute() {
        boyprefs =ctx. getSharedPreferences("itoll-employee", Context.MODE_PRIVATE);

        dialog = new ProgressDialog(ctx);
        dialog.setMessage("Loading..Please Wait ....");
        dialog.show();
        dialog.setCancelable(false);
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... boyi) {
        String reg = "http://toelve.com/itoll/mobile/user/buatpintu";
        String method = boyi[0];

        if (method.equalsIgnoreCase("buat")) {
            String namapintu = boyi[1];
            String jumlahpintu = boyi[2];
            String foto = boyi[3];
            String latitude=boyi[4];
            String longitude=boyi[5];
            String alamat=boyi[6];

            try {

                URL url = new URL(reg);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("namapintu", "UTF-8") + "=" + URLEncoder.encode(namapintu, "UTF-8") + "&" +
                        URLEncoder.encode("jumlahpintu", "UTF-8") + "=" + URLEncoder.encode(jumlahpintu, "UTF-8") + "&" +
                        URLEncoder.encode("foto", "UTF-8") + "=" + URLEncoder.encode(foto, "UTF-8") + "&" +
                        URLEncoder.encode("latitude", "UTF-8") + "=" + URLEncoder.encode(latitude, "UTF-8") + "&" +
                        URLEncoder.encode("longitude", "UTF-8") + "=" + URLEncoder.encode(longitude, "UTF-8") + "&" +
                        URLEncoder.encode("alamat", "UTF-8") + "=" + URLEncoder.encode(alamat, "UTF-8");
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
            if (s.trim().equalsIgnoreCase("sukses")) {
                String username=boyprefs.getString("username","");
                String password=boyprefs.getString("password","");
                String token=boyprefs.getString("token","");
                String device_id=boyprefs.getString("device_id","");

                new LoginTask(ctx).execute("login", username, password, token,device_id);
                Toast.makeText(ctx,s,Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ctx,s,Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            Log.w("kesalahan",s);
            Toast.makeText(ctx, "ada kesalahan silahkan coba beberapa saat lagi", Toast.LENGTH_LONG).show();
        }
    }
}
