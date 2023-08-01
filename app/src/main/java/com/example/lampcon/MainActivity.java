package com.example.lampcon;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import static android.provider.Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    public static final String TEXT = "text";
    public static final String SHARED_PREFS = "sharedPrefs";

    public static final String SWITCH_WA = "switch_wa";
    public static final String SWITCH_SMS = "switch_sms";
    public static final String SWITCH_IG = "switch_ig";
    public static final String SWITCH_SC = "switch_sc";
    public static final String SWITCH_TELEGRAM = "switch_telegram";
    public static final String SWITCH_SIGNAL = "switch_signal";
    public static final String SWITCH_MAIL = "switch_mail";
    public static final String SWITCH_PHONE = "switch_phone";

    public static final String TEXT_CALL = "text_call";
    public static final String TEXT_MSG = "text_msg";

    public static final String TEXT_BL = "text_bl";


    int NotPerm;


    static boolean wa;
    static boolean sms;
    static boolean ig;
    static boolean sc;
    static boolean telegram;
    static boolean signal;
    static boolean mail;
    static boolean phone;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        load();


        Button btn_menu = (Button)findViewById(R.id.btn_menu);

        btn_menu.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SettingActivity.class)));


        webView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webView.setWebViewClient(new WebViewClient());
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("http://lampe-v2/");

        startService( new Intent( this, NotificationListener.class));


       if (NotPerm != 1) ShowDialogFunc();

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            int read_Permission_Code = 1;
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, read_Permission_Code);
        }

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            int write_Permission_Code = 1;
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, write_Permission_Code);
        }

        load();
    }

    private void ShowDialogFunc() {
        new AlertDialog.Builder(this).setTitle("Notification Permission").setMessage("Notification Permission Granted?").setPositiveButton("yes", (dialog, which) -> {
            NotPerm = 1;
            save();
        }
        ).setNegativeButton("no", (dialog, which) -> startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS))).show();
    }


    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onDestroy() {
        save();
        super.onDestroy();
    }

    public void save() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(TEXT, NotPerm);
        editor.apply();
    }

    public void load() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        NotPerm = sharedPreferences.getInt(TEXT, 2);

        NotificationListener.wa = wa = sharedPreferences.getBoolean(MainActivity.SWITCH_WA, false);
        NotificationListener.sms = sms = sharedPreferences.getBoolean(MainActivity.SWITCH_SMS, false);
        NotificationListener.ig = ig = sharedPreferences.getBoolean(MainActivity.SWITCH_IG, false);
        NotificationListener.sc = sc = sharedPreferences.getBoolean(MainActivity.SWITCH_SC, false);
        NotificationListener.telegram = telegram = sharedPreferences.getBoolean(MainActivity.SWITCH_TELEGRAM, false);
        NotificationListener.signal = signal = sharedPreferences.getBoolean(MainActivity.SWITCH_SIGNAL, false);
        NotificationListener.mail = mail = sharedPreferences.getBoolean(MainActivity.SWITCH_MAIL, false);
        NotificationListener.phone = phone = sharedPreferences.getBoolean(MainActivity.SWITCH_PHONE, false);

        NotificationListener.call_color = sharedPreferences.getString(TEXT_CALL, "white");
        NotificationListener.msg_color = sharedPreferences.getString(TEXT_MSG, "white");



    }

}