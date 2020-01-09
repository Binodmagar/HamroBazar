package com.binod.hamrobazar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class SafetyView extends AppCompatActivity {

    TextView tvTermSV;
    Button btnTermSV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_view);

        tvTermSV = findViewById(R.id.tvSafetySV);
        btnTermSV = findViewById(R.id.btnTermSV);
    }
}
