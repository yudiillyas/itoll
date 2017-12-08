package com.toelve.i_tollemployee;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.media.ExifInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import static com.toelve.i_tollemployee.Loginpage.getDeviceId;
/**
 * Created by boyke purnomo 085883746312 on 7/12/2016.
 * copyright this script is under license of boyke purnomo please respect the author
 */
public class Register extends AppCompatActivity {
    private EditText etNamaLengkap,etUsername,etNohp,etAlamat,etNewPassword,
            etRePassword,etDeviceid;
    private ImageView ivFoto;
    private Button btDaftar;
    private SharedPreferences boyprefs;
    private int CAM_REQUEST1 = 1;

    Bitmap bitmap ,realimage;
    private String username,nama,nomerhp,password,repassword,token,foto,alamat,device_id,devid;
    private AlertDialog alert = null;
    private AlertDialog.Builder alertDialogBuilder = null;
    private Snackbar snackbar = null;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        boyprefs = getSharedPreferences("itoll-employee", MODE_PRIVATE);

        setContentView(R.layout.activity_register);
        if (isNetworkAvailable()) {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            if (locationManager != null) {
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                        (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))) {
                    GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();

                    int resultCode = googleAPI.isGooglePlayServicesAvailable(this);
                    if (ConnectionResult.SUCCESS != resultCode) {
                        if(googleAPI.isUserResolvableError(resultCode)) {
                            googleAPI.getErrorDialog(this, resultCode,
                                    PLAY_SERVICES_RESOLUTION_REQUEST).show();

                        } else {

                            Snackbar.make(ivFoto,"This device does not support for Google Play Service!",Snackbar.LENGTH_LONG).show();

                        }

                    } else {
                        Intent itent = new Intent(this, MyFirebaseInstanceIDService.class);
                        startService(itent);
                        Intent itent2 = new Intent(this, MyFirebaseMessagingService.class);
                        startService(itent2);
                    }


                    devid=getDeviceId(Register.this);
                    etNamaLengkap= findViewById(R.id.etNamaLengkap);
                    btDaftar= findViewById(R.id.btSubmit);
                    ivFoto= findViewById(R.id.ivFoto);
                    etNohp= findViewById(R.id.etNohp);
                    etNewPassword= findViewById(R.id.etNewPassword);
                    etRePassword = findViewById(R.id.etRePassword);
                    etUsername= findViewById(R.id.etUsername);
                    etAlamat= findViewById(R.id.etAlamat);
                    etDeviceid= findViewById(R.id.etDeviceid);
                    etDeviceid.setText(devid);
                    ivFoto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final CharSequence[] items = {"Take Photo",
                                    "Cancel"};

                            AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                            builder.setTitle("Add Photo!");
                            builder.setItems(items, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int item) {

                                    if (items[item].equals("Take Photo")) {
                                        ivFoto.setAlpha((float) 0.5);
                                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                        File fileb = new File(Environment.getExternalStorageDirectory()+ File.separator + "fotoitoll.jpg");
                                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileb));
                                        startActivityForResult(intent, CAM_REQUEST1);






                                    } else if (items[item].equals("Cancel")) {
                                        ivFoto.setAlpha((float) 0.5);
                                        dialog.dismiss();
                                        ivFoto.setAlpha((float) 1.0);

                                    } else {
                                        ivFoto.setAlpha((float) 1.0);

                                    }
                                }
                            });
                            builder.show();
                        }
                    });


                }else {
                    showGPSDisabledAlertToUser();
                }
            }

        }
        else{
            snackbar = Snackbar.make(findViewById(android.R.id.content),"No Internet",Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("Refresh",new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackbar.dismiss();
                    startActivity(new Intent(Register.this,Register.class));
                    finish();
                }
            });

            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        }


        btDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = etNamaLengkap.getText().toString();
                username = etUsername.getText().toString();
                nomerhp = etNohp.getText().toString();
                password = etNewPassword.getText().toString();
                repassword = etRePassword.getText().toString();
                device_id = etDeviceid.getText().toString();
                alamat=etAlamat.getText().toString();
                token = boyprefs.getString("token", "");
                if ((nama.equalsIgnoreCase("")) ||
                        (username.equalsIgnoreCase("")) ||
                        (nomerhp.equalsIgnoreCase("")) ||
                        (password.equalsIgnoreCase("")) ||
                        (alamat .equalsIgnoreCase(""))
                        ) {
                    Snackbar.make(btDaftar,"Semua Field Harus Diisi.",Snackbar.LENGTH_LONG).show();
                }else
                if (!repassword.equalsIgnoreCase(password)) {
                    Snackbar.make(btDaftar,"Konfirmasi Password Tidak Sama dengan Password",Snackbar.LENGTH_LONG).show();
                }
                else
                if (password.length()<6) {
                    Snackbar.make(btDaftar,"Password Minimal Harus 6 Character",Snackbar.LENGTH_LONG).show();
                }else
                if (bitmap == null ) {
                    Snackbar.make(btDaftar,"Semua Foto Harus Komplit",Snackbar.LENGTH_LONG).show();
                }else
                if (foto == null) {
                    Snackbar.make(btDaftar,"Semua Foto Harus Komplit",Snackbar.LENGTH_LONG).show();
                }
                else
                if (foto.equals("")) {
                    Snackbar.make(btDaftar,"Semua Foto Harus Komplit",Snackbar.LENGTH_LONG).show();
                }else if (device_id.equals("")) {
                    Snackbar.make(btDaftar,"HP Anda Tidak Mendukung Untuk Dijadikan Employee",Snackbar.LENGTH_LONG).show();
                }
                else {

                    if (isNetworkAvailable()) {

                        if(isNetworkAvailable()) {
                            alertDialogBuilder = new AlertDialog.Builder(Register.this);
                            alertDialogBuilder.setMessage("Yakin Data Sudah Benar?")
                                    .setCancelable(false)
                                    .setPositiveButton("Cek Lagi",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.dismiss();
                                                }
                                            });
                            alertDialogBuilder.setNegativeButton("Yakin",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                            String method = "register";
                                            GPSTracker gpsTracker=new GPSTracker(Register.this);
                                            String latitude= String.valueOf(gpsTracker.getLatitude());
                                            String longitude= String.valueOf(gpsTracker.getLongitude());
                                            RegisterTask registerTask = new RegisterTask(Register.this);
                                            registerTask.execute(method, nama, username, nomerhp,password,
                                                    foto,token,latitude,longitude,alamat,device_id);

                                        }
                                    });
                            alert = alertDialogBuilder.create();
                            alert.show();

                        }
                    }
                }
            }

        });
    }
    public boolean  isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        if (activeNetwork != null) {
            if((activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) && (activeNetwork.getState()== NetworkInfo.State.CONNECTED)){
                return true;
            } else if ((activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) && (activeNetwork.getState()== NetworkInfo.State.CONNECTED)){
                return true;
            }
        } else {

            return false;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Batalkan Registrasi??")
                .setCancelable(false)
                .setPositiveButton("Batalkan",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                startActivity(new Intent(Register.this,Loginpage.class));
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
    protected void showGPSDisabledAlertToUser() {
        alertDialogBuilder = new AlertDialog.Builder(this);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAM_REQUEST1 && resultCode == Activity.RESULT_OK) {
            File file = new File(Environment.getExternalStorageDirectory()+ File.separator +
                    "fotoitoll.jpg");
            ExifInterface exif = null;
            realimage=null;
            ByteArrayOutputStream byteArrayOutputStream;
            byte[] byteArray;
            bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath());
            try {
                exif = new ExifInterface(file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert exif != null;
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            // Toast.makeText(Register.this,String.valueOf(orientation),Toast.LENGTH_LONG).show();
            switch (orientation) {
                case ExifInterface.ORIENTATION_UNDEFINED:
                    //   bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 400, 360);
                    realimage=rotate(bitmap, 0);
                    ivFoto.setImageBitmap(realimage);
                    ivFoto.setAlpha((float) 1.0);
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    realimage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byteArray = byteArrayOutputStream.toByteArray();
                    foto = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    android.view.ViewGroup.LayoutParams layoutParams = ivFoto.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    ivFoto.setLayoutParams(layoutParams);
                    break;
                case ExifInterface.ORIENTATION_NORMAL:
                    //   bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 400, 360);
                    realimage=rotate(bitmap, 0);
                    ivFoto.setImageBitmap(realimage);
                    ivFoto.setAlpha((float) 1.0);
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    realimage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byteArray = byteArrayOutputStream.toByteArray();
                    foto = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    layoutParams = ivFoto.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    ivFoto.setLayoutParams(layoutParams);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    //   bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 400, 360);
                    realimage=rotate(bitmap, 180);
                    ivFoto.setImageBitmap(realimage);
                    ivFoto.setAlpha((float) 1.0);
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    realimage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byteArray = byteArrayOutputStream.toByteArray();
                    foto = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    layoutParams = ivFoto.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    ivFoto.setLayoutParams(layoutParams);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    //   bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 400, 360);
                    realimage=rotate(bitmap, 90);
                    ivFoto.setImageBitmap(realimage);
                    ivFoto.setAlpha((float) 1.0);
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    realimage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byteArray = byteArrayOutputStream.toByteArray();
                    foto = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    layoutParams = ivFoto.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    ivFoto.setLayoutParams(layoutParams);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    //   bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 400, 360);
                    realimage=rotate(bitmap, 270);
                    ivFoto.setImageBitmap(realimage);
                    ivFoto.setAlpha((float) 1.0);
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    realimage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byteArray = byteArrayOutputStream.toByteArray();
                    foto = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    layoutParams = ivFoto.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    ivFoto.setLayoutParams(layoutParams);
                    break;
            }

        }
        else
        if (requestCode == CAM_REQUEST1 && resultCode == Activity.RESULT_CANCELED) {
            ivFoto.setAlpha((float) 1.0);
            foto="";
            ivFoto.setImageResource(R.drawable.tblfoto);
            android.view.ViewGroup.LayoutParams layoutParams = ivFoto.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            final float scale = getResources().getDisplayMetrics().density;
            layoutParams.height = (int) (120 * scale);
            ivFoto.setLayoutParams(layoutParams);
        }



    }

    public static Bitmap decodeSampledBitmapFromFile(String path)
    {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > 600)
        {
            inSampleSize = Math.round((float)height / (float) 600);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > 800)
        {
            inSampleSize = Math.round((float)width / (float) 800);
        }

        options.inSampleSize = inSampleSize;

        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    public static Bitmap rotate(Bitmap bitmap, int degree) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix mtx = new Matrix();
        //       mtx.postRotate(degree);
        mtx.setRotate(degree);

        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }
}
