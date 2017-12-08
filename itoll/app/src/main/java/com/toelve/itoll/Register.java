package com.toelve.itoll;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Register extends AppCompatActivity {
    private EditText etNamaLengkap,etUsername,etNohp,etAlamat,etNewPassword,
            etRePassword,etKtp,etSim,etPlat,etEmail;
    private ImageView ivFoto,ivKtp,ivSim,ivKk;
    private Button btDaftar;
    private SharedPreferences boyprefs;
    private Spinner spPilih;
    private int CAM_REQUEST1 = 1;
    private static final int MY_CAMERA_REQUEST_CODE = 100,MY_CAMERA_REQUEST_CODE2=200,MY_CAMERA_REQUEST_CODE3=300,
            MY_CAMERA_REQUEST_CODE4=400;
    private int CAM_REQUEST2 = 2;
    Bitmap bitmap,bitmap2,bitmap3,bitmap4,bitmap5,realimage,realimage2,realimage3,realimage4;
    private int CAM_REQUEST3 = 3;private int CAM_REQUEST4 = 4;private int CAM_REQUEST5 = 5;
    private String username,nama,noplat,nomerhp,password,ktp,sim,repassword,token,foto,fotoktp,
            fotosim,fotokk,alamat,email,golongannya,jenisnya,op;
    private AlertDialog alert = null;
    private ArrayList<String> golongan=new ArrayList<>();
    private ArrayList<String> jenis=new ArrayList<>();
    private ArrayList<String> gabungan=new ArrayList<>();
    private AlertDialog.Builder alertDialogBuilder = null;
    private Snackbar snackbar = null;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        setContentView(R.layout.activity_register);
        if (isNetworkAvailable()) {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

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



                etNamaLengkap=(EditText)findViewById(R.id.etNamaLengkap);
                btDaftar=(Button) findViewById(R.id.btSubmit);
                ivFoto=(ImageView) findViewById(R.id.ivFoto);
                ivKtp=(ImageView) findViewById(R.id.ivKtp);
                ivSim=(ImageView) findViewById(R.id.ivSim);
                ivKk=(ImageView) findViewById(R.id.ivKk);
                spPilih=(Spinner) findViewById(R.id.spPilih);
                etNohp=(EditText)findViewById(R.id.etNohp);
                etSim=(EditText)findViewById(R.id.etSim);
                etNewPassword=(EditText)findViewById(R.id.etNewPassword);
                etRePassword = (EditText) findViewById(R.id.etRePassword);
                etKtp=(EditText)findViewById(R.id.etKtp);
                etUsername=(EditText)findViewById(R.id.etUsername);
                etAlamat=(EditText)findViewById(R.id.etAlamat);
                etPlat = (EditText) findViewById(R.id.etPlat);
                etEmail = (EditText) findViewById(R.id.etEmail);
                boyprefs = getSharedPreferences("itoll-management", MODE_PRIVATE);
                String datagolongan=boyprefs.getString("datagolongan","");
                golongan=PojoMion.AmbilArray(datagolongan,"golongan","datagolongan");
                jenis=PojoMion.AmbilArray(datagolongan,"jenis","datagolongan");
                for(int i=0;i<jenis.size();i++){
                    gabungan.add(golongan.get(i)+"-"+jenis.get(i));
                }
                ArrayAdapter<String> boykeadaptor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gabungan);
                boykeadaptor.setDropDownViewResource(android.R.layout.simple_spinner_item);
                spPilih.setAdapter(boykeadaptor);
                spPilih.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        op = adapterView.getItemAtPosition(i).toString();
                        String[] bagi = op.split("-");
                        golongannya=bagi[0];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
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
                                    File fileb = new File(Environment.getExternalStorageDirectory()+ File.separator + "fotobentors.jpg");
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


                ivKtp.setOnClickListener(new View.OnClickListener() {
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
                                    ivKtp.setAlpha((float) 0.5);
                                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                    File fileb2 = new File(Environment.getExternalStorageDirectory()+ File.separator + "fotobentors2.jpg");
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileb2));
                                    startActivityForResult(intent, CAM_REQUEST2);
                                } else if (items[item].equals("Cancel")) {
                                    ivKtp.setAlpha((float) 0.5);
                                    dialog.dismiss();
                                    ivKtp.setAlpha((float) 1.0);

                                } else {
                                    ivKtp.setAlpha((float) 1.0);

                                }
                            }
                        });
                        builder.show();
                    }
                });



                ivSim.setOnClickListener(new View.OnClickListener() {
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
                                    ivSim.setAlpha((float) 0.5);
                                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                    File fileb3 = new File(Environment.getExternalStorageDirectory()+ File.separator + "fotobentors3.jpg");
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileb3));
                                    startActivityForResult(intent, CAM_REQUEST3);
                                } else if (items[item].equals("Cancel")) {
                                    ivSim.setAlpha((float) 0.5);
                                    dialog.dismiss();
                                    ivSim.setAlpha((float) 1.0);

                                } else {
                                    ivSim.setAlpha((float) 1.0);

                                }
                            }
                        });
                        builder.show();
                    }
                });


                ivKk.setOnClickListener(new View.OnClickListener() {
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
                                    ivKk.setAlpha((float) 0.5);
                                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                    File fileb4 = new File(Environment.getExternalStorageDirectory()+ File.separator + "fotobentors4.jpg");
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileb4));
                                    startActivityForResult(intent, CAM_REQUEST4);

                                } else if (items[item].equals("Cancel")) {
                                    ivKk.setAlpha((float) 0.5);
                                    dialog.dismiss();
                                    ivKk.setAlpha((float) 1.0);

                                } else {
                                    ivKk.setAlpha((float) 1.0);

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
                ktp = etKtp.getText().toString();
                sim = etSim.getText().toString();
                noplat = etPlat.getText().toString();
                alamat=etAlamat.getText().toString();
                email=etEmail.getText().toString();
                token = boyprefs.getString("token", "");
                if ((nama.equalsIgnoreCase("")) ||
                        (username.equalsIgnoreCase("")) ||
                        (nomerhp.equalsIgnoreCase("")) ||
                        (password.equalsIgnoreCase("")) ||
                        (ktp .equalsIgnoreCase(""))||
                        (alamat .equalsIgnoreCase(""))||
                        (noplat .equalsIgnoreCase(""))||
                        (email .equalsIgnoreCase(""))||
                        (sim.equalsIgnoreCase(""))
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
                if ((bitmap == null) || (bitmap2 == null) || (bitmap3 == null) || (bitmap4 == null)) {
                    Snackbar.make(btDaftar,"Semua Foto Harus Komplit",Snackbar.LENGTH_LONG).show();
                }else
                if ((foto == null) || (fotokk == null) || (fotoktp == null) || (fotosim == null)) {
                    Snackbar.make(btDaftar,"Semua Foto Harus Komplit",Snackbar.LENGTH_LONG).show();
                }
                else
                if ((foto.equals("")) || (fotokk.equals("")) || (fotoktp.equals("")) || (fotosim.equals(""))) {
                    Snackbar.make(btDaftar,"Semua Foto Harus Komplit",Snackbar.LENGTH_LONG).show();
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
                                            registerTask.execute(method, nama, username, nomerhp,password, ktp,
                                                    sim,noplat, foto, fotoktp, fotosim, fotokk,token,
                                                    latitude,longitude,alamat,email,golongannya);

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
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_CAMERA_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    File fileb = new File(Environment.getExternalStorageDirectory()+ File.separator + "fotobentors.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileb));
                    startActivityForResult(intent, CAM_REQUEST1);
                    Snackbar.make(ivFoto,"Permission Granted, Now you can using Camera.",Snackbar.LENGTH_LONG).show();

                } else {

                    Snackbar.make(ivFoto,"Permission Denied, You cannot using Camera.",Snackbar.LENGTH_LONG).show();

                }
                break;

            case MY_CAMERA_REQUEST_CODE2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    File fileb2 = new File(Environment.getExternalStorageDirectory()+ File.separator + "fotobentors2.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileb2));
                    startActivityForResult(intent, CAM_REQUEST2);

                    Snackbar.make(ivFoto,"Permission Granted, Now you can using Camera.",Snackbar.LENGTH_LONG).show();

                } else {

                    Snackbar.make(ivFoto,"Permission Denied, You cannot using Camera.",Snackbar.LENGTH_LONG).show();

                }
                break;
            case MY_CAMERA_REQUEST_CODE3:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    File fileb3 = new File(Environment.getExternalStorageDirectory()+ File.separator + "fotobentors3.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileb3));
                    startActivityForResult(intent, CAM_REQUEST3);

                    Snackbar.make(ivFoto,"Permission Granted, Now you can using Camera.",Snackbar.LENGTH_LONG).show();

                } else {

                    Snackbar.make(ivFoto,"Permission Denied, You cannot using Camera.",Snackbar.LENGTH_LONG).show();

                }
                break;
            case MY_CAMERA_REQUEST_CODE4:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    File fileb4 = new File(Environment.getExternalStorageDirectory()+ File.separator + "fotobentors4.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileb4));
                    startActivityForResult(intent, CAM_REQUEST4);

                    Snackbar.make(ivFoto,"Permission Granted, Now you can using Camera.",Snackbar.LENGTH_LONG).show();

                } else {

                    Snackbar.make(ivFoto,"Permission Denied, You cannot using Camera.",Snackbar.LENGTH_LONG).show();

                }
                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAM_REQUEST1 && resultCode == Activity.RESULT_OK) {
            File file = new File(Environment.getExternalStorageDirectory()+ File.separator +
                    "fotobentors.jpg");
            ExifInterface exif = null;
            realimage=null;
            ByteArrayOutputStream byteArrayOutputStream;
            byte[] byteArray;
            bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 800, 600);
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

        }else if (requestCode == CAM_REQUEST2 && resultCode == Activity.RESULT_OK) {
            File file2 = new File(Environment.getExternalStorageDirectory()+ File.separator +
                    "fotobentors2.jpg");
            ExifInterface exif = null;
            realimage2=null;
            ByteArrayOutputStream byteArrayOutputStream;
            byte[] byteArray;
            bitmap2 = decodeSampledBitmapFromFile(file2.getAbsolutePath(), 800, 600);
            try {
                exif = new ExifInterface(file2.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert exif != null;
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            // Toast.makeText(Register.this,String.valueOf(orientation),Toast.LENGTH_LONG).show();
            switch (orientation) {
                case ExifInterface.ORIENTATION_UNDEFINED:
                    //   bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 400, 360);
                    realimage2=rotate(bitmap2, 0);
                    ivKtp.setImageBitmap(realimage2);
                    ivKtp.setAlpha((float) 1.0);
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    realimage2.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byteArray = byteArrayOutputStream.toByteArray();
                    fotoktp = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    android.view.ViewGroup.LayoutParams layoutParams = ivKtp.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    ivKtp.setLayoutParams(layoutParams);
                    break;
                case ExifInterface.ORIENTATION_NORMAL:
                    //   bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 400, 360);
                    realimage2=rotate(bitmap2, 0);
                    ivKtp.setImageBitmap(realimage2);
                    ivKtp.setAlpha((float) 1.0);
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    realimage2.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byteArray = byteArrayOutputStream.toByteArray();
                    fotoktp = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    layoutParams = ivKtp.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    ivKtp.setLayoutParams(layoutParams);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    //   bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 400, 360);
                    realimage2=rotate(bitmap2, 180);
                    ivKtp.setImageBitmap(realimage2);
                    ivKtp.setAlpha((float) 1.0);
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    realimage2.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byteArray = byteArrayOutputStream.toByteArray();
                    fotoktp = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    layoutParams = ivKtp.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    ivKtp.setLayoutParams(layoutParams);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    //   bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 400, 360);
                    realimage2=rotate(bitmap2, 90);
                    ivKtp.setImageBitmap(realimage2);
                    ivKtp.setAlpha((float) 1.0);
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    realimage2.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byteArray = byteArrayOutputStream.toByteArray();
                    fotoktp = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    layoutParams = ivKtp.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    ivKtp.setLayoutParams(layoutParams);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    //   bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 400, 360);
                    realimage2=rotate(bitmap2, 270);
                    ivKtp.setImageBitmap(realimage2);
                    ivKtp.setAlpha((float) 1.0);
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    realimage2.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byteArray = byteArrayOutputStream.toByteArray();
                    fotoktp = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    layoutParams = ivKtp.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    ivKtp.setLayoutParams(layoutParams);
                    break;
            }

        }
        else if (requestCode == CAM_REQUEST3 && resultCode == Activity.RESULT_OK) {
            File file3 = new File(Environment.getExternalStorageDirectory()+ File.separator +
                    "fotobentors3.jpg");
            ExifInterface exif = null;
            realimage3=null;
            ByteArrayOutputStream byteArrayOutputStream;
            byte[] byteArray;
            bitmap3 = decodeSampledBitmapFromFile(file3.getAbsolutePath(), 800, 600);
            try {
                exif = new ExifInterface(file3.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert exif != null;
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            // Toast.makeText(Register.this,String.valueOf(orientation),Toast.LENGTH_LONG).show();
            switch (orientation) {
                case ExifInterface.ORIENTATION_UNDEFINED:
                    //   bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 400, 360);
                    realimage3=rotate(bitmap3, 0);
                    ivSim.setImageBitmap(realimage3);
                    ivSim.setAlpha((float) 1.0);
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    realimage3.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byteArray = byteArrayOutputStream.toByteArray();
                    fotosim = Base64.encodeToString(byteArray, Base64.DEFAULT);


                    android.view.ViewGroup.LayoutParams layoutParams = ivSim.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    ivSim.setLayoutParams(layoutParams);
                    break;
                case ExifInterface.ORIENTATION_NORMAL:
                    //   bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 400, 360);
                    realimage3=rotate(bitmap3, 0);
                    ivSim.setImageBitmap(realimage3);
                    ivSim.setAlpha((float) 1.0);
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    realimage3.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byteArray = byteArrayOutputStream.toByteArray();
                    fotosim = Base64.encodeToString(byteArray, Base64.DEFAULT);


                    layoutParams = ivSim.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    ivSim.setLayoutParams(layoutParams);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    //   bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 400, 360);
                    realimage3=rotate(bitmap3, 180);
                    ivSim.setImageBitmap(realimage3);
                    ivSim.setAlpha((float) 1.0);
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    realimage3.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byteArray = byteArrayOutputStream.toByteArray();
                    fotosim = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    layoutParams = ivSim.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    ivSim.setLayoutParams(layoutParams);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    //   bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 400, 360);
                    realimage3=rotate(bitmap3, 90);
                    ivSim.setImageBitmap(realimage3);
                    ivSim.setAlpha((float) 1.0);
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    realimage3.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byteArray = byteArrayOutputStream.toByteArray();
                    fotosim = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    layoutParams = ivSim.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    ivSim.setLayoutParams(layoutParams);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    //   bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 400, 360);
                    realimage3=rotate(bitmap3, 270);
                    ivSim.setImageBitmap(realimage3);
                    ivSim.setAlpha((float) 1.0);
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    realimage3.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byteArray = byteArrayOutputStream.toByteArray();
                    fotosim = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    layoutParams = ivSim.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    ivSim.setLayoutParams(layoutParams);
                    break;
            }


        }
        else if (requestCode == CAM_REQUEST4 && resultCode == Activity.RESULT_OK) {

            File file4 = new File(Environment.getExternalStorageDirectory()+ File.separator +
                    "fotobentors4.jpg");
            ExifInterface exif = null;
            realimage4=null;
            ByteArrayOutputStream byteArrayOutputStream;
            byte[] byteArray;
            bitmap4 = decodeSampledBitmapFromFile(file4.getAbsolutePath(), 800, 600);
            try {
                exif = new ExifInterface(file4.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert exif != null;
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            // Toast.makeText(Register.this,String.valueOf(orientation),Toast.LENGTH_LONG).show();
            switch (orientation) {
                case ExifInterface.ORIENTATION_UNDEFINED:
                    //   bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 400, 360);
                    realimage4=rotate(bitmap4, 0);
                    ivKk.setImageBitmap(realimage4);
                    ivKk.setAlpha((float) 1.0);
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    realimage4.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byteArray = byteArrayOutputStream.toByteArray();
                    fotokk = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    android.view.ViewGroup.LayoutParams layoutParams = ivKk.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    ivKk.setLayoutParams(layoutParams);

                    break;
                case ExifInterface.ORIENTATION_NORMAL:
                    //   bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 400, 360);
                    realimage4=rotate(bitmap4, 0);
                    ivKk.setImageBitmap(realimage4);
                    ivKk.setAlpha((float) 1.0);
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    realimage4.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byteArray = byteArrayOutputStream.toByteArray();
                    fotokk = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    layoutParams = ivKk.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    ivKk.setLayoutParams(layoutParams);

                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    //   bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 400, 360);
                    realimage4=rotate(bitmap4, 180);
                    ivKk.setImageBitmap(realimage4);
                    ivKk.setAlpha((float) 1.0);
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    realimage4.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byteArray = byteArrayOutputStream.toByteArray();
                    fotokk = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    layoutParams = ivKk.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    ivKk.setLayoutParams(layoutParams);

                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    //   bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 400, 360);
                    realimage4=rotate(bitmap4, 90);
                    ivKk.setImageBitmap(realimage4);
                    ivKk.setAlpha((float) 1.0);
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    realimage4.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byteArray = byteArrayOutputStream.toByteArray();
                    fotokk = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    layoutParams = ivKk.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    ivKk.setLayoutParams(layoutParams);

                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    //   bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 400, 360);
                    realimage4=rotate(bitmap4, 270);
                    ivKk.setImageBitmap(realimage4);
                    ivKk.setAlpha((float) 1.0);
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    realimage4.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byteArray = byteArrayOutputStream.toByteArray();
                    fotokk = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    layoutParams = ivKk.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    ivKk.setLayoutParams(layoutParams);

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
        }else
        if (requestCode == CAM_REQUEST2 && resultCode == Activity.RESULT_CANCELED) {
            ivKtp.setAlpha((float) 1.0);
            fotoktp="";
            ivKtp.setImageResource(R.drawable.tblfoto);
            android.view.ViewGroup.LayoutParams layoutParams = ivKtp.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            float scale = getResources().getDisplayMetrics().density;
            layoutParams.height = (int) (120 * scale);
            ivKtp.setLayoutParams(layoutParams);
        }
        else
        if (requestCode == CAM_REQUEST3 && resultCode == Activity.RESULT_CANCELED) {
            ivSim.setAlpha((float) 1.0);
            fotosim="";
            ivSim.setImageResource(R.drawable.tblfoto);
            android.view.ViewGroup.LayoutParams layoutParams = ivSim.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            float scale = getResources().getDisplayMetrics().density;
            layoutParams.height = (int) (120 * scale);
            ivSim.setLayoutParams(layoutParams);
        }
        else
        if (requestCode == CAM_REQUEST4 && resultCode == Activity.RESULT_CANCELED) {
            ivKk.setAlpha((float) 1.0);
            fotokk="";
            ivKk.setImageResource(R.drawable.tblfoto);
            android.view.ViewGroup.LayoutParams layoutParams = ivKk.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            final float scale = getResources().getDisplayMetrics().density;
            layoutParams.height = (int) (120 * scale);
            ivKk.setLayoutParams(layoutParams);

        }



    }

    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight)
    {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight)
        {
            inSampleSize = Math.round((float)height / (float)reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth)
        {
            inSampleSize = Math.round((float)width / (float)reqWidth);
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
