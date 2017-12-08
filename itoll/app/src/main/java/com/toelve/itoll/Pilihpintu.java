package com.toelve.itoll;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Pilihpintu extends AppCompatActivity {
    private SharedPreferences boyprefs;
    private ImageView ivFoto;
    private TextView tvNama,tvAlamat;
    private String foto,namapintu,alamat;
    private LinearLayout lnDinamis;
    private String jml;
    private int jumlahpintu=0;
    private boolean aktif;
    private  IntentFilter boyke;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilihpintu);
        boyke = new IntentFilter("boyike");
        boyprefs = getSharedPreferences("itoll-management", MODE_PRIVATE);
        boyprefs.edit().putBoolean("sedangmasuk",true)
                .putBoolean("memasuki",true).apply();
    }

    @Override
    protected void onStart() {
        super.onStart();
        namapintu=boyprefs.getString("namapintu","");
        foto=boyprefs.getString("fotopintu","");
        alamat=boyprefs.getString("alamatpintu","");
        jml=boyprefs.getString("jumlahpintu","");
        aktif = boyprefs.getBoolean("aktiftrack", false);
        if(!namapintu.equalsIgnoreCase("")&&!foto.equalsIgnoreCase("null")&&
                !alamat.equalsIgnoreCase("null")&&!jml.equalsIgnoreCase("null")){
            jumlahpintu= Integer.parseInt(jml);
            tvNama=findViewById(R.id.tvNama);
            tvAlamat=findViewById(R.id.tvAlamat);
            lnDinamis=findViewById(R.id.lnDinamis);
            ivFoto=findViewById(R.id.ivFoto);
            Picasso.with(Pilihpintu.this)
                    .load(foto)
                    .transform(new RoundedTransformation(190, 0))
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .fit()
                    .placeholder(R.drawable.profilkosong)
                    .into(ivFoto);
            tvNama.setText(String.format("Anda Memasuki Area Parkir %s", namapintu));
            tvAlamat.setText(String.valueOf(alamat));
            EditText store[] = new EditText[jumlahpintu];

            for(int i = 0; i< jumlahpintu; i++) {
                final Button addAnsMCQ = new Button(this);
                addAnsMCQ.setBackgroundResource(R.drawable.boxes4);
                addAnsMCQ.setId(i);
                Typeface typeface = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    typeface = getResources().getFont(R.font.autour_one);
                }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0,54,0,0);
                addAnsMCQ.setTypeface(typeface);
                addAnsMCQ.setText("Buka Pintu "+(i+1));
                addAnsMCQ.setLayoutParams(params);
                lnDinamis.addView(addAnsMCQ);
                final int finalI = i;
                addAnsMCQ.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addAnsMCQ.setAlpha((float)0.5);
                        String username=boyprefs.getString("username","");
                        String password=boyprefs.getString("password","");
                        String token=boyprefs.getString("token","");
                         boyprefs.edit().remove("sedangmasuk").apply();
                        @SuppressLint("SimpleDateFormat") String tanggal= new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                        @SuppressLint("SimpleDateFormat") String jam = new SimpleDateFormat("HH:mm a").format(new Date());
                        String namauser=boyprefs.getString("namauser","");
                        String noplat=boyprefs.getString("noplat","");
                        String nomerhp=boyprefs.getString("nomerhpuser","");
                        String harga=boyprefs.getString("harga","");
                        String point=boyprefs.getString("point","");
                        boyprefs.edit().putString("pintuke",String.valueOf(finalI+1)).apply();
                        boyprefs.edit().putBoolean("tunggukeluar",true).apply();
                      //  Toast.makeText(Pilihpintu.this,jam+" "+tanggal,Toast.LENGTH_LONG).show();
                        new UploadDataTask(Pilihpintu.this).execute("upload", username, namauser,
                                "SLOT "+(finalI +1),noplat,"masuk",namapintu,jam,tanggal,nomerhp,harga,point);
                        Toast.makeText(Pilihpintu.this,"Pintu "+(finalI +1)+" Terbuka",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    @Override
    public void onBackPressed() {

    }



}
