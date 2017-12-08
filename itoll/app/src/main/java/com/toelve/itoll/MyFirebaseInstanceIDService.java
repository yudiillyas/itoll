package com.toelve.itoll;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static android.content.ContentValues.TAG;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private SharedPreferences boyprefs;
    public MyFirebaseInstanceIDService() {

    }

    @SuppressLint("WrongThread")
    @Override
    public void onTokenRefresh() {
        boyprefs=getApplicationContext().getSharedPreferences("itoll-management", MODE_PRIVATE);
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        boyprefs.edit().putString("token",refreshedToken).apply();
        String email=boyprefs.getString("email","");
        if(!email.equals("")){
            new UpdateTokenNode(getBaseContext()).execute("updatetoken",email,refreshedToken);
        }

    }
}
