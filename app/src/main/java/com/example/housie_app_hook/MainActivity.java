package com.example.housie_app_hook;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private static String TAG = "AppHooker";
    public static String SHOW_ANSWER_KEY = "SHOW_ANSWER";
    public static String AUTO_ANSWER_KEY = "AUTO_ANSWER";
    static{
       Log.i(TAG,"Class Loaded");
    }

    private SharedPreferences settings;
    Switch showAnswerSwitch,setAutoAnswerSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = this.getSharedPreferences(AppConfig.SHARED_PREF_NAME, MODE_PRIVATE);

        showAnswerSwitch= findViewById(R.id.showAnswerSwitch);
        setAutoAnswerSwitch = findViewById(R.id.setAutoAnswerSwitch);

        setAutoAnswerSwitch.setChecked(settings.getBoolean(AUTO_ANSWER_KEY,false));
        showAnswerSwitch.setChecked(settings.getBoolean(SHOW_ANSWER_KEY,false));

        showAnswerSwitch.setOnCheckedChangeListener(this);
        setAutoAnswerSwitch.setOnCheckedChangeListener(this);


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SharedPreferences.Editor editor = settings.edit();

        switch (buttonView.getId())
        {
            case R.id.showAnswerSwitch:
                editor.putBoolean(SHOW_ANSWER_KEY,isChecked);
                break;

            case R.id.setAutoAnswerSwitch:
                editor.putBoolean(AUTO_ANSWER_KEY,isChecked);
                break;
        }

        showAnswerSwitch.getId();
        editor.apply();
    }
}
