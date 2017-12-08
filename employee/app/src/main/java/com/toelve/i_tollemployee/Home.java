package com.toelve.i_tollemployee;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
/**
 * Created by boyke purnomo 085883746312 on 7/12/2016.
 * copyright this script is under license of boyke purnomo please respect the author
 */
public class Home extends AppCompatActivity {
    private SharedPreferences boyprefs;
    private String datauser;
    private ArrayList<String> nama=new ArrayList<>();
    private ArrayList<String>alamat=new ArrayList<>();
    private ArrayList<String>latitude=new ArrayList<>();
    private ArrayList<String>longitude=new ArrayList<>();
    private ArrayList<String>foto=new ArrayList<>();
    private ArrayList<String>nomerhp=new ArrayList<>();
    private TextView tvNama,tvNomerTelpon,tvAlamat;
    private ImageView ivFoto;
    private Button btKeluar,tvBuatPintu;
    private AlertDialog alert;
    private String username,password,token;
    private boolean aktif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boyprefs = getSharedPreferences("itoll-employee", MODE_PRIVATE);
        aktif=boyprefs.getBoolean("switch",false);
        username=boyprefs.getString("username","");
        startService(new Intent(this, EmployeeTracking.class));
        setContentView(R.layout.activity_home);
        datauser=boyprefs.getString("datauser","");
        latitude=PojoMion.AmbilArray(datauser,"latitude","datauser");
        longitude=PojoMion.AmbilArray(datauser,"longitude","datauser");
        alamat=PojoMion.AmbilArray(datauser,"alamat","datauser");
        nama=PojoMion.AmbilArray(datauser,"nama","datauser");
        foto=PojoMion.AmbilArray(datauser,"foto","datauser");
        nomerhp=PojoMion.AmbilArray(datauser,"nomerhp","datauser");
        tvNama=(TextView)findViewById(R.id.tvNama);
        tvNomerTelpon=(TextView)findViewById(R.id.tvNomerTelpon);
        tvBuatPintu=(Button)findViewById(R.id.tvBuatpintu);
        tvAlamat=(TextView)findViewById(R.id.tvAlamat);
        ivFoto=(ImageView)findViewById(R.id.ivFoto);
        btKeluar=(Button) findViewById(R.id.btKeluar);
        tvNama.setText(nama.get(0));
        tvNomerTelpon.setText(nomerhp.get(0));
        tvAlamat.setText(alamat.get(0));
        Picasso.with(Home.this)
                .load(foto.get(0))
                .transform(new RoundedTransformation(190, 0))
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .fit()
                .placeholder(R.drawable.profilkosong)
                .into(ivFoto);




        btKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder  alertDialogBuilder = new AlertDialog.Builder(Home.this);
                alertDialogBuilder.setMessage("Mau Logout?")
                        .setCancelable(false)
                        .setPositiveButton("ya",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                        String token=boyprefs.getString("token","");
                                        boyprefs.edit().clear().apply();
                                        boyprefs.edit().putString("token",token).apply();
                                        stopService(new Intent(Home.this,EmployeeTracking.class));
                                        startActivity(new Intent(Home.this,MainActivity.class));
                                        finish();
                                    }
                                });
                alertDialogBuilder.setNegativeButton("Jangan",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                alert.dismiss();

                            }
                        });
                alert = alertDialogBuilder.create();
                alert.show();
            }
        });
        tvBuatPintu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this,Buatpintu.class));
                finish();
            }
        });
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder  alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Keluar?")
                .setCancelable(false)
                .setPositiveButton("Keluar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                finish();
                            }
                        });
        alertDialogBuilder.setNegativeButton("Jangan",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        alert.dismiss();

                    }
                });
        alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }
}