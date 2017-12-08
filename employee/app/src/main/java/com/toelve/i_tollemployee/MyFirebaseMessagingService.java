package com.toelve.i_tollemployee;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import static android.content.ContentValues.TAG;
/**
 * Created by boyke purnomo 085883746312 on 7/12/2016.
 * copyright this script is under license of boyke purnomo please respect the author
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private String isi,message;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());


        if (remoteMessage.getData().size() > 0) {
            Log.d("pesan :", "Message data payload: " + remoteMessage.getData());
            Map<String, String> params = remoteMessage.getData();
            JSONObject object = new JSONObject(params);
            try {
                isi=object.getString("title");
                message=object.getString("message");
                sendNotification(isi,message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("JSON_OBJECT", object.toString());
            if ( true) {


            } else {


            }

        }


        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            Log.d(TAG, "Message tag: " + remoteMessage.getNotification().getTag());
        }

    }



    private void sendNotification(String messageBody, String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_web_client_id);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.logo)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo))
                        .setContentTitle(message)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setTicker(messageBody)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setColor(Color.parseColor( "#9999ff"))
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null) {
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        }
    }
}