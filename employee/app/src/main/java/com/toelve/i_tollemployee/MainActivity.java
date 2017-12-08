package com.toelve.i_tollemployee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/**
 * Created by boyke purnomo 085883746312 on 7/12/2016.
 * copyright this script is under license of boyke purnomo please respect the author
 */
public class MainActivity extends AppCompatActivity {
    private SharedPreferences boyprefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boyprefs = getSharedPreferences("itoll-employee", MODE_PRIVATE);
        boolean saving=boyprefs.getBoolean("savelogin",false);
        if(saving){
            String username=boyprefs.getString("username","");
            String password=boyprefs.getString("password","");
            String token=boyprefs.getString("token","");
            String device_id=boyprefs.getString("device_id","");

            new LoginTask(MainActivity.this).execute("login", username, password, token,device_id);
        }else{


            setContentView(R.layout.activity_main);
            runThread();
        }
    }

    private void runThread() {

        new Thread() {
            public void run() {
                int jh = 0;
                while (jh < 40) {
                    jh++;
                    try {
                        final int finalJh = jh;
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                            }
                        });
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                Intent intent = new Intent(MainActivity.this, Loginpage.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }

}
