package com.toelve.itoll;

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
 * Created by boyke on 7/12/2016.
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
        String reg = "http://toelve.com/itoll/mobile/driver/register";
        String method = boyi[0];
        if (method.equalsIgnoreCase("register")) {
            String nama = boyi[1];
            String username = boyi[2];
            String nomerhp = boyi[3];
            String password=boyi[4];
            String ktp=boyi[5];
            String sim=boyi[6];
            String noplat=boyi[7];
            String foto=boyi[8];
            String fotoktp=boyi[9];
          String fotosim=boyi[10];
          String fotokk=boyi[11];
            String token=boyi[12];
            String latitude=boyi[13];
            String longitude=boyi[14];
            String alamat=boyi[15];
            String email=boyi[16];
            String golongan=boyi[17];
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
                        URLEncoder.encode("ktp", "UTF-8") + "=" + URLEncoder.encode(ktp, "UTF-8") + "&" +
                        URLEncoder.encode("sim", "UTF-8") + "=" + URLEncoder.encode(sim, "UTF-8") + "&" +
                        URLEncoder.encode("noplat", "UTF-8") + "=" + URLEncoder.encode(noplat, "UTF-8") + "&" +
                        URLEncoder.encode("foto", "UTF-8") + "=" + URLEncoder.encode(foto, "UTF-8") + "&" +
                        URLEncoder.encode("fotoktp", "UTF-8") + "=" + URLEncoder.encode(fotoktp, "UTF-8") + "&" +
                        URLEncoder.encode("fotosim", "UTF-8") + "=" + URLEncoder.encode(fotosim, "UTF-8") + "&" +
                        URLEncoder.encode("fotokk", "UTF-8") + "=" + URLEncoder.encode(fotokk, "UTF-8") + "&" +
                        URLEncoder.encode("token", "UTF-8") + "=" + URLEncoder.encode(token, "UTF-8") + "&" +
                        URLEncoder.encode("latitude", "UTF-8") + "=" + URLEncoder.encode(latitude, "UTF-8") + "&" +
                        URLEncoder.encode("longitude", "UTF-8") + "=" + URLEncoder.encode(longitude, "UTF-8")+ "&" +
                         URLEncoder.encode("alamat", "UTF-8") + "=" + URLEncoder.encode(alamat, "UTF-8")+ "&" +
                       URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")+ "&" +
                URLEncoder.encode("golongan", "UTF-8") + "=" + URLEncoder.encode(golongan, "UTF-8");

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
