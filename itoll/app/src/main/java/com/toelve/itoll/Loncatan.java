package com.toelve.itoll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Loncatan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loncatan);
        startActivity(new Intent(this,Pilihpintu.class));
        finish();
    }
}
