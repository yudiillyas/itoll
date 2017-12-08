package com.toelve.itoll;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tunggukeluar extends AppCompatActivity {
    private SharedPreferences boyprefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boyprefs = getSharedPreferences("itoll-management", MODE_PRIVATE);
        setContentView(R.layout.activity_tunggukeluar);
        String foto=boyprefs.getString("fotouser","");
        ImageView ivFoto=(ImageView)findViewById(R.id.ivFoto);
        Button btKeluar=(Button) findViewById(R.id.btKeluar);
        Picasso.with(Tunggukeluar.this)
                .load(foto)
                .transform(new RoundedTransformation(190, 0))
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .fit()
                .placeholder(R.drawable.profilkosong)
                .into(ivFoto);
        btKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                @SuppressLint("SimpleDateFormat") String tanggal= new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                @SuppressLint("SimpleDateFormat") String jam = new SimpleDateFormat("HH:mm a").format(new Date());
                String username=boyprefs.getString("username","");
                String namapintu=boyprefs.getString("namapintu","");
                String pintuke=boyprefs.getString("pintuke","");
                String noplat=boyprefs.getString("noplat","");
                new KeluarTask(Tunggukeluar.this).execute("keluar", username, namapintu,
                        pintuke,noplat,jam,tanggal);
            }
        });
    }
}
