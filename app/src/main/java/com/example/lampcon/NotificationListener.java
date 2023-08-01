package com.example.lampcon;

import android.app.Notification;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;


import androidx.core.app.NotificationCompat;


import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class NotificationListener extends NotificationListenerService {

    private static final String TAG = "NotificationListener";
    private static final String WA_PACKAGE = "com.whatsapp";
    private static final String CALL_PACKAGE = "com.android.phone";
    private static final String SMS_PACKAGE = "com.android.mms";
    private static final String IG_PACKAGE = "com.instagram.android";
    private static final String SC_PACKAGE = "com.snapchat.android";
    private static final String TELEGRAM_PACKAGE = "org.telegram.messenger";
    private static final String SIGNAL_PACKAGE = "org.thoughtcrime.securesms";
    private static final String GMAIL_MAIL_PACKAGE = "com.google.android.gm";
    private static final String GMX_MAIL_PACKAGE = "de.gmx.mobile.android.mail";


    static boolean wa = MainActivity.wa;
    static boolean sms = MainActivity.sms;
    static boolean ig = MainActivity.ig;
    static boolean sc = MainActivity.sc;
    static boolean telegram = MainActivity.telegram;
    static boolean signal = MainActivity.signal;
    static boolean mail = MainActivity.mail;
    static boolean phone = MainActivity.phone;


    static String msg_color;
    static String call_color;

    boolean call_status = false;

    String message;

    static List ListBlacklist = new ArrayList();



    @Override
    public void onListenerConnected() {
        Log.i(TAG, "Notification Listener connected");
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        if (sbn.getPackageName().equals(WA_PACKAGE) && wa) {
            Notification notification = sbn.getNotification();
            Bundle bundle = notification.extras;
            message = bundle.getString(NotificationCompat.EXTRA_TEXT);

            if (message.contains("WhatsApp Web is currently active")) return;
            if (message.contains("Missed Calls")) return;
            if (message.contains("Message deleted")) return;

            if (message.contains("Incoming voice call") || (message.contains("Incoming video call")) && phone) {
                sendRequest("call", call_color);
                call_status = true;
            } else sendRequest("msg", msg_color);

        }

        if (sbn.getPackageName().equals(SMS_PACKAGE) && sms){
            Notification notification = sbn.getNotification();
            Bundle bundle = notification.extras;
            message = bundle.getString(NotificationCompat.EXTRA_TEXT);

            if (message.contains("Missed Phone Call") || message.contains("verpasster Anruf")) return;

            sendRequest("msg", msg_color);
        }

        if (sbn.getPackageName().equals(IG_PACKAGE) && ig){
            Notification notification = sbn.getNotification();
            Bundle bundle = notification.extras;
            message = bundle.getString(NotificationCompat.EXTRA_TEXT);

            if (message.contains("started audio call") && phone) {
                sendRequest("call", call_color);
                call_status = true;
            } else if (message.contains("started audio call")) {
                return;
            } else sendRequest("msg", call_color);


        }

        if (sbn.getPackageName().equals(CALL_PACKAGE) && phone) {sendRequest("call", msg_color); call_status = true;}
        if (sbn.getPackageName().equals(SC_PACKAGE) && sc) sendRequest("msg", msg_color);
        if (sbn.getPackageName().equals(GMAIL_MAIL_PACKAGE) || sbn.getPackageName().equals(GMX_MAIL_PACKAGE) && sms) sendRequest("msg", msg_color);
        if (sbn.getPackageName().equals(SMS_PACKAGE) && sms) sendRequest("msg", msg_color);
        if (sbn.getPackageName().equals(TELEGRAM_PACKAGE) && telegram) sendRequest("msg", msg_color);
        if (sbn.getPackageName().equals(SIGNAL_PACKAGE) && signal) sendRequest("msg", msg_color);

    }

        public void onNotificationRemoved (StatusBarNotification sbn){
            if (sbn.getPackageName().equals(CALL_PACKAGE) && call_status) {
                sendRequest("end_call", "");
                call_status = false;
            }
            if (sbn.getPackageName().equals(WA_PACKAGE) && call_status) {
                sendRequest("end_call", "");
                call_status = false;
            }
            if (sbn.getPackageName().equals(IG_PACKAGE) && call_status) {
                sendRequest("end_call","");
                call_status = false;
            }
        }





    private void sendRequest(String val, String color) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://DeskLamp/" + val  + "_" + color;


        Request request = new Request.Builder().url(url).build();
        client.newCall(request);
    }
}