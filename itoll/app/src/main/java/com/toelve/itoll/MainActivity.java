package com.toelve.itoll;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences boyprefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boyprefs = getSharedPreferences("itoll-management", MODE_PRIVATE);
        boolean saving=boyprefs.getBoolean("savelogin",false);
        boolean sedangmasuk=boyprefs.getBoolean("sedangmasuk",false);
        boolean tunggukeluar=boyprefs.getBoolean("tunggukeluar",false);
        if(tunggukeluar){
            startActivity(new Intent(MainActivity.this,Tunggukeluar.class));
            finish();
        }else
         if(sedangmasuk){
            startActivity(new Intent(MainActivity.this,Pilihpintu.class));
            finish();
        }else
        if(saving){
            String username=boyprefs.getString("username","");
            String password=boyprefs.getString("password","");
            String token=boyprefs.getString("token","");

            new LoginTask(MainActivity.this).execute("login", username, password, token);
        }
        else{


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
