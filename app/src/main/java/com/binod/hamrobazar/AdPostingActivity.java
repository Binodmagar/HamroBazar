package com.binod.hamrobazar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class AdPostingActivity extends AppCompatActivity {
TextView tvTermA;
Button btnTermA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_posting);

        tvTermA = findViewById(R.id.tvTermA);
        btnTermA = findViewById(R.id.btnTermA);
    }
}
