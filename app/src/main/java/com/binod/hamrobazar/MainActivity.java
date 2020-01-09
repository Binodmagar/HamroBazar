package com.binod.hamrobazar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;


public class MainActivity extends AppCompatActivity {

    ImageView imgIcon;
    Dialog Dialog;
    ViewFlipper viewFlipper;
    private RecyclerView rvFirst, rvSecond;
    Button btnRegister;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.draw_menu,menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgIcon = findViewById(R.id.imgIcon);
        imgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        Dialog = new Dialog(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //recycleview first


    }
//    public void ShowPopUp(View v){
//        Dialog.setContentView(R.layout.activity_login);
//        Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        Dialog.show();
//    }


}



