package com.example.veterinaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Nos envia automaticamente al activity Login
        Thread Splash = new Thread(){
            @Override
            public void run(){
                try{
                    sleep(1500);
                    Intent intent = new Intent(getApplicationContext(), Login.class );
                    startActivity(intent);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };

        Splash.start();

    }




}