package com.toelve.i_tollemployee;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
/**
 * Created by boyke purnomo 085883746312 on 7/12/2016.
 * copyright this script is under license of boyke purnomo please respect the author
 */
public class Buatpintu extends AppCompatActivity {
    private EditText etNamaPintu;
    private EditText etJumlahPintu;
    private EditText etAlamat;
    private int CAM_REQUEST1=1;
    private Bitmap bitmap,realimage;
    private String foto;
    private Button btDaftar;
    private ImageView ivFoto;
    private  EditText etLatitude;
    private EditText etLongitude;
    private GPSTracker gpsTracker;
    private String namapintu,jumlahpintu,alamat,latitude,longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buatpintu);
        ivFoto=findViewById(R.id.ivFoto);
        etNamaPintu=findViewById(R.id.etNamaPintu);
        etJumlahPintu=findViewById(R.id.etJumlahPintu);
        etAlamat=findViewById(R.id.etAlamat);
        etLatitude=findViewById(R.id.etLatitude);
        etLongitude=findViewById(R.id.etLongitude);
        btDaftar= findViewById(R.id.btSubmit);
        gpsTracker=new GPSTracker(this);
        etLatitude.setText(String.valueOf(gpsTracker.getLatitude()));
        etLongitude.setText(String.valueOf(gpsTracker.getLongitude()));
        etAlamat.setText(gpsTracker.ambilalamat(this,String.valueOf(gpsTracker.getLatitude()), String.valueOf(gpsTracker.getLongitude())));
        ivFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = {"Take Photo",
                        "Cancel"};

                AlertDialog.Builder builder = new AlertDialog.Builder(Buatpintu.this);
                builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (items[item].equals("Take Photo")) {
                            ivFoto.setAlpha((float) 0.5);
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            File fileb = new File(Environment.getExternalStorageDirectory() + File.separator + "fotoitoll.jpg");
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
        btDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                namapintu = etNamaPintu.getText().toString();
                jumlahpintu = etJumlahPintu.getText().toString();
                alamat = etAlamat.getText().toString();
                latitude = etLatitude.getText().toString();
                longitude = etLongitude.getText().toString();
                if ((namapintu.equalsIgnoreCase("")) ||
                        (jumlahpintu.equalsIgnoreCase("")) ||
                        (alamat.equalsIgnoreCase(""))
                        ) {
                    Snackbar.make(btDaftar,"Semua Field Harus Diisi.",Snackbar.LENGTH_LONG).show();
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
                }
                else {

                    if (isNetworkAvailable()) {

                        if(isNetworkAvailable()) {
                         AlertDialog.Builder   alertDialogBuilder = new AlertDialog.Builder(Buatpintu.this);
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
                                            String method = "buat";
                                            GPSTracker gpsTracker=new GPSTracker(Buatpintu.this);
                                            String latitude= String.valueOf(gpsTracker.getLatitude());
                                            String longitude= String.valueOf(gpsTracker.getLongitude());
                                            BuatpintuTask buatpintuTask = new BuatpintuTask(Buatpintu.this);
                                            buatpintuTask.execute(method, namapintu, jumlahpintu,
                                                    foto,latitude,longitude,alamat);

                                        }
                                    });
                          AlertDialog  alert = alertDialogBuilder.create();
                            alert.show();

                        }
                    }
                }
            }
        });
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
}
