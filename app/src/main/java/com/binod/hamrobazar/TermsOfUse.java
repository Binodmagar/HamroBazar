package com.binod.hamrobazar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TermsOfUse extends AppCompatActivity {

    TextView tvTermT;
    Button btnTermT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_use);

        tvTermT = findViewById(R.id.tvTermT);
        btnTermT = findViewById(R.id.btnTermT);
    }
}
