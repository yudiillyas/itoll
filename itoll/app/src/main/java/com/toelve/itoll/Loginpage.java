package com.toelve.itoll;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class Loginpage extends AppCompatActivity {
    private Button btRegister;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alert;
    private ImageView ivProfile;
    private EditText etUsername, etPassword;
    private String username, password;
    private LocationManager locationManager;
    private Snackbar snackbar = null;
    private SharedPreferences boyprefs;
    private String token;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        boyprefs = getSharedPreferences("itoll-management", MODE_PRIVATE);
        setContentView(R.layout.activity_loginpage);
        btRegister = (Button) findViewById(R.id.btRegister);
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PushGolonganNode(Loginpage.this).execute("push");
            }
        });
        if (isNetworkAvailable()) {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            if (locationManager != null) {
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                        (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))) {
                    Log.w("ok", "ok");
                    GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
                    int resultCode = googleAPI.isGooglePlayServicesAvailable(this);
                    if (ConnectionResult.SUCCESS != resultCode) {
                        if (googleAPI.isUserResolvableError(resultCode)) {
                            int PLAY_SERVICES_RESOLUTION_REQUEST = 1972;
                            googleAPI.getErrorDialog(this, resultCode,
                                    PLAY_SERVICES_RESOLUTION_REQUEST).show();
                        }
                    } else {
                        Intent itent = new Intent(this, MyFirebaseMessagingService.class);
                        Intent itent2 = new Intent(this, MyFirebaseInstanceIDService.class);
                        startService(itent2);
                        startService(itent);
                    }

                    etUsername = (EditText) findViewById(R.id.etUsername);
                    etPassword = (EditText) findViewById(R.id.etPassword);
                    Button btLogin = (Button) findViewById(R.id.btLogin);

                    btLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            username = etUsername.getText().toString();
                            password = etPassword.getText().toString();
                            boyprefs.edit().putString("username", username)
                                    .putString("password", password).apply();
                            token = boyprefs.getString("token", "");
                            if (username.equals("") || password.equals("")) {
                                Toast.makeText(Loginpage.this, "Harap Semua Diisi Dengan Benar", Toast.LENGTH_SHORT).show();
                            } else {
                                if (isNetworkAvailable()) {
                                    new LoginTask(Loginpage.this).execute("login", username, password, token);
                                } else {
                                    snackbar = Snackbar.make(etPassword, "No Internet", Snackbar.LENGTH_INDEFINITE);
                                    snackbar.setAction("Refresh", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            snackbar.dismiss();
                                            startActivity(new Intent(Loginpage.this, MainActivity.class));
                                            finish();
                                        }
                                    });

                                    snackbar.setActionTextColor(Color.RED);
                                    snackbar.show();
                                }
                            }
                        }
                    });
                } else {
                    showGPSDisabledAlertToUser();
                }
            }

        } else {
            snackbar = Snackbar.make(etPassword, "No Internet", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("Refresh", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackbar.dismiss();
                    startActivity(new Intent(Loginpage.this, MainActivity.class));
                    finish();
                }
            });

            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        }
    }



    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        if (activeNetwork != null) {
            if ((activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) && (activeNetwork.getState() == NetworkInfo.State.CONNECTED)) {
                return true;
            } else if ((activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) && (activeNetwork.getState() == NetworkInfo.State.CONNECTED)) {
                return true;
            }
        } else {

            return false;
        }
        return false;
    }


    @SuppressLint("HardwareIds")
    public String getIMEI(Context context) {

        TelephonyManager mngr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (mngr != null) {
            return mngr.getDeviceId();
        }else {
            return "kosong";
        }


    }



    protected void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS Kamu Gak aktif. mau mengaktifkan GPS?")
                .setCancelable(false)
                .setPositiveButton("Aktifkan GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Keluar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        alert.dismiss();
                        finish();
                    }
                });
        alert = alertDialogBuilder.create();
        alert.show();

    }

    @Override
    public void onBackPressed() {
        alertDialogBuilder = new AlertDialog.Builder(this);
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

