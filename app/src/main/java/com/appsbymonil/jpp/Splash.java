package com.appsbymonil.jpp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Space;
import android.widget.Toast;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread th = new Thread(){
            public void run(){
                try{
                    sleep(4000);

                }
                catch(Exception e){

                }
                finally{
                    try{
                        Intent i = new Intent(Splash.this,  MainActivity.class);
                        startActivity(i);
                        finish();
                    }catch (Exception e){
                        Toast.makeText(Splash.this , e.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        th.start();

    }
}
