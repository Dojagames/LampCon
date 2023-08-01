package com.example.lampcon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Switch switch_wa;
    private Switch switch_sms;
    private Switch switch_ig;
    private Switch switch_sc;
    private Switch switch_telegram;
    private Switch switch_signal;
    private Switch switch_mail;
    private Switch switch_phone;

    Spinner spinner_msg;
    Spinner spinner_call;

    private final String[] color= {"White", "RGB", "current" };

    // TinyDB tinydb = new TinyDB(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);



        Button btn_main = (Button) findViewById(R.id.btn_back);
        btn_main.setOnClickListener(v -> startActivity(new Intent(SettingActivity.this, MainActivity.class)));

        Button btn_save = (Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(v -> save());

        spinner_msg = findViewById(R.id.spinner_color_msg);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, color);
        spinner_msg.setAdapter(adapter);
        spinner_msg.setOnItemSelectedListener(this);

        spinner_call = findViewById(R.id.spinner_color_call);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, color);
        spinner_call.setAdapter(adapter1);
        spinner_call.setOnItemSelectedListener(this);

        switch_wa = (Switch) findViewById(R.id.switch_wa);
        switch_sms = (Switch) findViewById(R.id.switch_sms);
        switch_ig = (Switch) findViewById(R.id.switch_ig);
        switch_sc = (Switch) findViewById(R.id.switch_sc);
        switch_telegram = (Switch) findViewById(R.id.switch_telegram);
        switch_signal = (Switch) findViewById(R.id.switch_signal);
        switch_mail = (Switch) findViewById(R.id.switch_mail);
        switch_phone = (Switch) findViewById(R.id.switch_phone);


        load();
        updateVars();
    }

    private void save() {
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(MainActivity.SWITCH_WA, switch_wa.isChecked());
        editor.putBoolean(MainActivity.SWITCH_SMS, switch_sms.isChecked());
        editor.putBoolean(MainActivity.SWITCH_IG, switch_ig.isChecked());
        editor.putBoolean(MainActivity.SWITCH_SC, switch_sc.isChecked());
        editor.putBoolean(MainActivity.SWITCH_TELEGRAM, switch_telegram.isChecked());
        editor.putBoolean(MainActivity.SWITCH_SIGNAL, switch_signal.isChecked());
        editor.putBoolean(MainActivity.SWITCH_MAIL, switch_mail.isChecked());
        editor.putBoolean(MainActivity.SWITCH_PHONE, switch_phone.isChecked());

        editor.putString(MainActivity.TEXT_CALL, NotificationListener.call_color );
        editor.putString(MainActivity.TEXT_MSG, NotificationListener.msg_color);

        editor.apply();

        Toast.makeText(this, "Preferences saved", Toast.LENGTH_SHORT).show();

        //tinydb.putListString(MainActivity.TEXT_BL, NotificationListener.ListBlacklist);
    }

    private void load() {
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);

        MainActivity.wa = sharedPreferences.getBoolean(MainActivity.SWITCH_WA, false);
        MainActivity.sms = sharedPreferences.getBoolean(MainActivity.SWITCH_SMS, false);
        MainActivity.ig = sharedPreferences.getBoolean(MainActivity.SWITCH_IG, false);
        MainActivity.sc = sharedPreferences.getBoolean(MainActivity.SWITCH_SC, false);
        MainActivity.telegram = sharedPreferences.getBoolean(MainActivity.SWITCH_TELEGRAM, false);
        MainActivity.signal = sharedPreferences.getBoolean(MainActivity.SWITCH_SIGNAL, false);
        MainActivity.mail = sharedPreferences.getBoolean(MainActivity.SWITCH_MAIL, false);
        MainActivity.phone = sharedPreferences.getBoolean(MainActivity.SWITCH_PHONE, false);

        NotificationListener.call_color = sharedPreferences.getString(MainActivity.TEXT_CALL, "white");
        NotificationListener.msg_color = sharedPreferences.getString(MainActivity.TEXT_MSG, "white");
    }

    private void updateVars() {
        switch_wa.setChecked(MainActivity.wa);
        switch_sms.setChecked(MainActivity.sms);
        switch_ig.setChecked(MainActivity.ig);
        switch_sc.setChecked(MainActivity.sc);
        switch_telegram.setChecked(MainActivity.telegram);
        switch_signal.setChecked(MainActivity.signal);
        switch_mail.setChecked(MainActivity.mail);
        switch_phone.setChecked(MainActivity.phone);

        switch (NotificationListener.msg_color) {
            case "White":
                spinner_msg.setSelection(0);
                break;
            case "RGB":
                spinner_msg.setSelection(1);
                break;
            case "current":
                spinner_msg.setSelection(2);
                break;
        }

        switch (NotificationListener.call_color) {
            case "White":
                spinner_call.setSelection(0);
                break;
            case "RGB":
                spinner_call.setSelection(1);
                break;
            case "current":
                spinner_call.setSelection(2);
                break;
        }

    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spinner_color_msg:
                NotificationListener.msg_color = parent.getItemAtPosition(position).toString();
                break;
            case R.id.spinner_color_call:
                NotificationListener.call_color = parent.getItemAtPosition(position).toString();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}