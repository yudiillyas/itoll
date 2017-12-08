package com.toelve.i_tollemployee;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static android.content.ContentValues.TAG;
/**
 * Created by boyke purnomo 085883746312 on 7/12/2016.
 * copyright this script is under license of boyke purnomo please respect the author
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private SharedPreferences boyprefs;
    public MyFirebaseInstanceIDService() {

    }

    @SuppressLint("WrongThread")
    @Override
    public void onTokenRefresh() {
        boyprefs=getApplicationContext().getSharedPreferences("itoll-employee", MODE_PRIVATE);

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);


        boyprefs.edit().putString("token",refreshedToken).apply();
        String email=boyprefs.getString("email","");
        if(!email.equals("")){
            new UpdateTokenNode(getBaseContext()).execute("updatetoken",email,refreshedToken);
        }

    }
}
