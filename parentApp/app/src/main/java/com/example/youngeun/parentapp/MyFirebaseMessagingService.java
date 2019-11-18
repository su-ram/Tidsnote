package com.example.youngeun.parentapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private MainActivity mainActivity;

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message2 data payload: " + remoteMessage.getData());

            if(remoteMessage.getData().size()>0){

               /* String m_id=remoteMessage.getData().get("request_id");
                for (int i=0;i<mainActivity.messageList.size();i++){

                    if(mainActivity.messageList.get(i).m_id.equals(m_id)){
                        String plain_request=mainActivity.messageList.get(i).request;
                        mainActivity.messageList.get(i).setRequest(plain_request+"(처리완료)");


                    }
                }*/
            }
            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.

            } else {
                // Handle message within 10 seconds
                handleNow();
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            try {

                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        String deviceToken = instanceIdResult.getToken();
                        // Do whatever you want with your token now
                        // i.e. store it on SharedPreferences or DB
                        // or directly send it to server


                        Log.d("IDService","device token : "+deviceToken);

                    }
                });
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "Message2 Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    public void handleNow(){


    }
    @Override
    public void onNewToken(String s) {
        //super.onNewToken(s);
        sendRegistrationToServer(s);
        Log.e("NEW_TOKEN",s);
        send_Data("update_token",s);
    }
    public void printToken(String s){
        Log.i("Token",s);
    }
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
        Client c = new Client("update_token");
        c.setData(token);
        c.start();
    }
    private void sendNotification(String messageTitle, String messageBody) {

        Intent intent = new Intent(this, Launch.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        String channelId = getString(R.string.default_notification_channel_id);
        CharSequence name = getString(R.string.default_notification_channel_name);
        String description = getString(R.string.default_notification_channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(channelId, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
                        .setSmallIcon(R.drawable.send)
                        .setContentTitle(messageTitle)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent)
                ;
        notificationManager.notify(0, notificationBuilder.build());
    }



    public void send_Data(String type, @Nullable String obj) {


        Client client = new Client(this, type);
        client.setData(obj);
        client.start();
        try {
            client.join();
            Log.i("Init", "Finished");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

