package com.example.nina.test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Nina on 16/12/31.
 */

public class IntroActivity extends Activity {

    TextView mintroduction;


    @Override

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        mintroduction = (TextView) findViewById(R.id.Introduction);
        mintroduction.setText("这是一个执杖的游戏");
    }
}
