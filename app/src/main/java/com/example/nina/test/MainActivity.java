package com.example.nina.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;


//view sourceprint?01

public class MainActivity extends AppCompatActivity {

    Button introButton;
    Button sButton;
    Button selPButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button restartButton = (Button)findViewById(R.id.restart);
        introButton = (Button)findViewById(R.id.introductionButton);
        sButton = (Button)findViewById(R.id.startButton);
        selPButton = (Button)findViewById(R.id.selectButton);
        introButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intro = new Intent(MainActivity.this, IntroActivity.class);
                startActivity(intro);
            }
        });
        sButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intro2 = new Intent(MainActivity.this, sokoban.class);
                startActivity(intro2);
            }
        });
        selPButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent sel = new Intent(MainActivity.this, selectActivity.class);
                startActivity(sel);
            }
        });

    }


}
