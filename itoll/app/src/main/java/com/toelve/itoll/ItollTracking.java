package com.toelve.itoll;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import java.util.ArrayList;

public class ItollTracking extends Service implements android.location.LocationListener {
    private static final String TAG = "Location";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 5000;
    private static final float LOCATION_DISTANCE = 0;
    private Context mContext;
    Intent boyke;
    LocationManager locationManager;
    private Criteria criteria;
    public double lat,longi,lat2,longi2;
    private boolean memasuki;
    private String namapintunya,jumlahpintunya,alamatnya,fotonya;
    Intent broadcastIntent;
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
    public static String ada = "ada";
    private float distance;
    private SharedPreferences boyprefs;

    @Override
    public void onLocationChanged(Location location) {
         boyprefs = getBaseContext().getSharedPreferences("itoll-management", Context.MODE_PRIVATE);
        Log.w("latitude: ", String.valueOf(location.getLatitude()));
        Log.w("longitude: ", String.valueOf(location.getLongitude()));
        location.set(location);
        String username = boyprefs.getString("username", "");
        String token = boyprefs.getString("token", "");
        Log.w("kesave", "latlang");
        String speed = String.valueOf(location.getSpeed());

        if (!username.equalsIgnoreCase("")) {
            MasukinLatLangTask masukinLatLangTask = new MasukinLatLangTask(mContext);
            masukinLatLangTask.execute("update", username, String.valueOf(location.getLatitude()),
                    String.valueOf(location.getLongitude()), token, speed);
        }


    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        return START_STICKY;
    }

    @Override
    public void onCreate() {
    }
    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();

    }



    }



