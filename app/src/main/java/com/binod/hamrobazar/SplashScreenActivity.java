package com.binod.hamrobazar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //using a thread and halt screen for 2 seconds
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("Terms", MODE_PRIVATE);
                Boolean agreed = sharedPreferences.getBoolean("agreed",false);

                if(agreed.equals(true)){
                    Toast.makeText(SplashScreenActivity.this, "Successed", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(SplashScreenActivity.this, TermsCondition.class);
                    startActivity(intent);
                    finish();
                }

            }
        },2000);
    }
}
