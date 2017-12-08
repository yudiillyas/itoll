package com.toelve.i_tollemployee;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
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
class UpdateTokenNode extends AsyncTask<String,Void,String> {
   @SuppressLint("StaticFieldLeak")
   private Context ctx;
    private Snackbar snackbar;
    private String username,password;

    UpdateTokenNode(Context ctx) {
        this.ctx = ctx;

    }
    private ProgressDialog dialog;
    private String login_url;
    private SharedPreferences boyprefs;
    @Override

    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

            login_url = "http://toelve.com/itoll/mobile/user/updatetoken";
        String method = params[0];
        if (method.equalsIgnoreCase("updatetoken")) {
            String   token = params[1];
          String   email = params[2];




            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("token", "UTF-8") + "=" + URLEncoder.encode(token, "UTF-8") + "&" +
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
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
           if(result.trim().equalsIgnoreCase("sukses")){

               Log.e("insert token",result);
            }else if(result.contains("datauser")){
               Log.e("insert token",result);

            }else{
                Toast.makeText(ctx,result,Toast.LENGTH_SHORT).show();

           }

        } catch (Exception e) {
            Toast.makeText(ctx,result,Toast.LENGTH_SHORT).show();
        }
    }
}
