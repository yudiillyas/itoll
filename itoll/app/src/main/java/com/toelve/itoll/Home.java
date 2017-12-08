package com.toelve.itoll;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Home extends AppCompatActivity implements LocationListener {
    private SharedPreferences boyprefs;
    private String databentor;
    Switch swOn;
    public static String ada="ada";
    public double lat,longi,lat2,longi2;
    private boolean memasuki;
    private ArrayList<String> lats=new ArrayList<>();
    private ArrayList<String>longs=new ArrayList<>();
    private ArrayList<String>namapintu=new ArrayList<>();
    private ArrayList<String>jumlahpintu=new ArrayList<>();
    private ArrayList<String>fotopintu=new ArrayList<>();
    private ArrayList<String>alamatpintu=new ArrayList<>();
    private ArrayList<String> nama = new ArrayList<>();
    private ArrayList<String> ktp = new ArrayList<>();
    private ArrayList<String> email = new ArrayList<>();
    private ArrayList<String> alamat = new ArrayList<>();

    private ArrayList<String> sim = new ArrayList<>();
    private ArrayList<String> point = new ArrayList<>();
    private ArrayList<String> latitude = new ArrayList<>();
    private ArrayList<String> longitude = new ArrayList<>();
    private ArrayList<String> foto = new ArrayList<>();
    private ArrayList<String> nomerhp = new ArrayList<>();
    private ArrayList<String> harga = new ArrayList<>();
    private ArrayList<String> noplat = new ArrayList<>();
    private ArrayList<String> golongan = new ArrayList<>();
    private TextView tvNama, tvNomerTelpon, tvPlat, tvPoint, tvGolongan;
    private ImageView ivFoto;
    private Button btKeluar;
    private LocationManager mLocationManager;
    private AlertDialog alert;
    private String username, password, token;
    private boolean aktif,sedangmasuk;
    private IntentFilter boyke;
    private int mulai;
    private String namapintus,jumlahpintus,alamats,fotopintus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boyke = new IntentFilter("boyike");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        boyprefs = getSharedPreferences("itoll-management", MODE_PRIVATE);
        mulai=boyprefs.getInt("mulai",0);
        if(mulai==0){
            aktif=true;
            mulai++;
            boyprefs.edit().putInt("mulai",mulai).apply();
            boyprefs.edit().putBoolean("aktiftrack",true).apply();
        }else{
            aktif = boyprefs.getBoolean("aktiftrack", false);
        }

        sedangmasuk = boyprefs.getBoolean("sedangmasuk", false);
       // Toast.makeText(this,String.valueOf(aktif),Toast.LENGTH_LONG).show();
        username = boyprefs.getString("username", "");
        setContentView(R.layout.activity_home);

        swOn=findViewById(R.id.swOn);

        mLocationManager=(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (mLocationManager != null) {
            if(mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                mLocationManager.requestLocationUpdates( LocationManager.NETWORK_PROVIDER,
                        1,
                        0, Home.this);

            }else{
                mLocationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER,
                        1,
                        0, Home.this);
            }

        }
        isLocationEnabled();
        swOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(swOn.isChecked()){
                    swOn.setText("ON");
                    boyprefs.edit().putBoolean("aktiftrack",true).apply();
                    mLocationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);


                    if (mLocationManager != null) {
                        if(mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                            mLocationManager.requestLocationUpdates( LocationManager.NETWORK_PROVIDER,
                                    1,
                                    0, Home.this);

                        }else{
                            mLocationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER,
                                    1,
                                    0, Home.this);
                        }

                    }
                }else{
                    swOn.setText("OFF");
                    boyprefs.edit().putBoolean("aktiftrack",false).apply();
                   if(mLocationManager!=null){
                       mLocationManager.removeUpdates(Home.this);
                   }
                }
            }
        });

        if(aktif){
            swOn.setText("ON");
            swOn.setChecked(true);
                startService(new Intent(Home.this,ItollTracking.class));

        }else{
            swOn.setChecked(false);
            swOn.setText("OFF");
            stopService(new Intent(Home.this,ItollTracking.class));
        }

        databentor = boyprefs.getString("databentor", "");
        point = PojoMion.AmbilArray(databentor, "point", "databentor");
        golongan = PojoMion.AmbilArray(databentor, "golongan", "databentor");
        latitude = PojoMion.AmbilArray(databentor, "latitude", "databentor");
        longitude = PojoMion.AmbilArray(databentor, "longitude", "databentor");
        harga = PojoMion.AmbilArray(databentor, "harga", "databentor");
        sim = PojoMion.AmbilArray(databentor, "sim", "databentor");
        ktp = PojoMion.AmbilArray(databentor, "ktp", "databentor");
        email = PojoMion.AmbilArray(databentor, "email", "databentor");
        if (email != null) {
            if (!email.get(0).equalsIgnoreCase("")) {
                boyprefs.edit().putString("email", email.get(0)).apply();
            }

        }
        alamat = PojoMion.AmbilArray(databentor, "alamat", "databentor");
        nama = PojoMion.AmbilArray(databentor, "nama", "databentor");
        foto = PojoMion.AmbilArray(databentor, "foto", "databentor");
        nomerhp = PojoMion.AmbilArray(databentor, "nomerhp", "databentor");
        noplat = PojoMion.AmbilArray(databentor, "noplat", "databentor");
        boyprefs.edit().putString("namauser",nama.get(0))
                .putString("noplat",noplat.get(0))
                .putString("nomerhpuser",nomerhp.get(0))
                .putString("harga",harga.get(0))
                .putString("point",point.get(0))
                .putString("fotouser",foto.get(0))
                .apply();
        tvNama = (TextView) findViewById(R.id.tvNama);
        tvPoint = (TextView) findViewById(R.id.tvPoint);
        tvGolongan = (TextView) findViewById(R.id.tvGolongan);
        tvNomerTelpon = (TextView) findViewById(R.id.tvNomerTelpon);
        tvPlat = (TextView) findViewById(R.id.tvPlat);
        ivFoto = (ImageView) findViewById(R.id.ivFoto);
        btKeluar = (Button) findViewById(R.id.btKeluar);
        tvNama.setText(nama.get(0));
        tvNomerTelpon.setText(nomerhp.get(0));
        tvPlat.setText(noplat.get(0));
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
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Home.this);
                alertDialogBuilder.setMessage("Mau Logout?")
                        .setCancelable(false)
                        .setPositiveButton("ya",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                        String token = boyprefs.getString("token", "");
                                        boyprefs.edit().clear().apply();
                                        boyprefs.edit().putString("token", token).apply();
                                        startActivity(new Intent(Home.this, MainActivity.class));
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
        tvPoint.setText("Point :" + point.get(0));
        tvGolongan.setText(golongan.get(0));

    }

    private Bitmap ambilBuletan(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(100, 100, 90, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        bitmap.recycle();
        return output;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
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
        if(!aktif){
            if(mLocationManager!=null){
                mLocationManager.removeUpdates(this);
            }
        }

        boyprefs.edit().putBoolean("pausedihome",true).apply();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(aktif) {
            if (mLocationManager != null) {
                if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            1,
                            0, this);

                } else {
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            1,
                            0, this);
                }


                boyprefs.edit().putBoolean("pausedihome", false).apply();

            }
        }

    }
    private void sendNotification4(String idnotif) {
        String channelId =Home.this. getString(R.string.default_web_client_id);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(Home.this, channelId)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setTimeoutAfter(2)
                        .setAutoCancel(true)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setColor(Color.parseColor( "#9999ff"));

        NotificationManager notificationManager =
                (NotificationManager)Home.this. getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null) {
            notificationManager.notify(Integer.parseInt(idnotif) , notificationBuilder.build());
        }
    }



    @Override
    public void onLocationChanged(Location location) {
        Log.e("latitue",String.valueOf(location.getLatitude()));


        new AmpinTask(Home.this).execute("ambilString", String.valueOf(location.getLatitude()),
                String.valueOf(location.getLongitude()));
        lat = location.getLatitude();
        longi = location.getLongitude();

        String result=boyprefs.getString("datapintu","");
       /* Toast.makeText(Home.this,result,Toast.LENGTH_LONG).show();*/
        if(!result.equalsIgnoreCase("")){
            lats = PojoMion.AmbilArray(result, "latitude", "datapintu");
            longs = PojoMion.AmbilArray(result, "longitude", "datapintu");
            namapintu = PojoMion.AmbilArray(result, "namapintu", "datapintu");
            jumlahpintu = PojoMion.AmbilArray(result, "jumlahpintu", "datapintu");
            fotopintu = PojoMion.AmbilArray(result, "foto", "datapintu");
            alamatpintu = PojoMion.AmbilArray(result, "alamat", "datapintu");
            memasuki = boyprefs.getBoolean("memasuki", false);
                if(!memasuki){
                    for(int p=0;p<lats.size();p++){
                        lat2= Double.parseDouble(lats.get(p));
                        longi2= Double.parseDouble(longs.get(p));
                        Log.e("memasuki", String.valueOf(memasuki));
                        Location locationA = new Location("A");
                        locationA.setLatitude(lat);
                        locationA.setLongitude(longi);

                        Location locationB = new Location("B");
                        locationB.setLatitude(lat2);
                        locationB.setLongitude(longi2);

                      float  jarak = locationA.distanceTo(locationB);
                        Log.w("JARAK "+p, String.valueOf(jarak));
                        if (jarak < 500 && jarak!=0.0) {

                      //    Toast.makeText(Home.this,String.valueOf(jarak),Toast.LENGTH_LONG).show();
                            if(!username.equalsIgnoreCase("")){
                                int idnya = boyprefs.getInt("timin", 0);
                                idnya++;
                                boyprefs.edit().putInt("timin", idnya).apply();
                                String idNotif = String.valueOf(idnya);
                                boyprefs.edit().putString("namapintu", namapintu.get(p))
                                        .putString("jumlahpintu", jumlahpintu.get(p))
                                        .putString("fotopintu", fotopintu.get(p))
                                        .putString("latitudepintu", lats.get(p))
                                        .putString("longitudepintu", longs.get(p))
                                        .putString("alamatpintu", alamatpintu.get(p))
                                        .apply();
                                sendNotification4("1");
                                boyprefs.edit().putBoolean("aktiftrack",false).apply();
                                Intent intent=new Intent(Home.this,Pilihpintu.class);
                                startActivity(intent);
                                finish();
                            }


                        }
                    }
                }else{

                        String latitudepintu=boyprefs.getString("latitudepintu","");
                        double latpintu= Double.parseDouble(latitudepintu);
                        String longitudepintu=boyprefs.getString("longitudepintu","");
                        double longpintu= Double.parseDouble(longitudepintu);
                        Log.e("memasuki", String.valueOf(memasuki));
                        Location locationA = new Location("A");
                        locationA.setLatitude(latpintu);
                        locationA.setLongitude(longpintu);

                        Location locationB = new Location("B");
                        locationB.setLatitude(lat);
                        locationB.setLongitude(longi);

                      float  jarak = locationA.distanceTo(locationB);
                        Log.w("jarak", String.valueOf(jarak));
                        if (jarak >600 ) {
                            boyprefs.edit().remove("memasuki").apply();
                            Log.e("memasuki","dihapus");

                            }
                }

        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        Log.w("provider",s);
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.w("provider",s);
    }

    private void isLocationEnabled() {

        if(!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            AlertDialog.Builder alertDialog=new AlertDialog.Builder(Home.this);
            alertDialog.setTitle("Enable Location");
            alertDialog.setMessage("Your locations setting is not enabled. Please enabled it in settings menu.");
            alertDialog.setPositiveButton("Location Settings", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    dialog.cancel();
                }
            });
            AlertDialog alert=alertDialog.create();
            alert.show();
        }
        else{
            Log.d("location","enabled");
        }
    }
}
