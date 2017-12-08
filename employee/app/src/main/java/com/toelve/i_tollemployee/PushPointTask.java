package com.toelve.i_tollemployee;

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
/**
 * Created by boyke purnomo 085883746312 on 7/12/2016.
 */
public class PushPointTask extends AsyncTask<String,Void,String> {
   private  Context ctx;
    private  AlertDialog alertDialog;
    PushPointTask(Context ctx) {
        this.ctx = ctx;
    }
    private ProgressDialog dialog=null;
    private SharedPreferences boyprefs;
    @Override
    protected void onPreExecute() {
        boyprefs =ctx. getSharedPreferences("itoll-employee", Context.MODE_PRIVATE);
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
        String login_url = "http://toelve.com/itoll/mobile/user/pushpoint";
        String method = params[0];
        if (method.equalsIgnoreCase("point")) {
            String username = params[1];





            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
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
                if(result.contains("databentor")){
                    boyprefs.edit().putString("databentor",result)
                            .putBoolean("savelogin",true)
                            .apply();
                    ctx.startActivity(new Intent(ctx,Home.class));
                    ((Activity)ctx).finish();
                }else{
                    Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
                }


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ctx, "Data Error ...", Toast.LENGTH_SHORT).show();
            boyprefs.edit().putBoolean("saving",false).apply();
        }
    }
}
