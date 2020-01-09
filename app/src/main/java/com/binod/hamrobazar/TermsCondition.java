package com.binod.hamrobazar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class TermsCondition extends AppCompatActivity{

    CheckBox checkBox1, checkBox2, checkBox3;
    Button btnTerm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_condition);

        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        btnTerm = findViewById(R.id.btnTerm);

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBox1.setTag(isChecked ? "y" : "n");
                Intent intent = new Intent(TermsCondition.this, TermsOfUse.class);
                startActivity(intent);

            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBox2.setTag(isChecked? "y" : "n");
                Intent intent = new Intent(TermsCondition.this, SafetyView.class);
                startActivity(intent);

            }
        });

        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBox3.setTag(isChecked? "y" : "n");
                Intent intent = new Intent(TermsCondition.this, AdPostingActivity.class);
                startActivity(intent);
            }
        });

        btnTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox1.isChecked() && checkBox2.isChecked() && checkBox3.isChecked()){
                    StoreUserTerms();
                    Intent intent = new Intent(TermsCondition.this, SplashScreenActivity.class);
                    startActivity(intent);
                }else{
                    Validation();
                }
            }
        });

    }
private void StoreUserTerms(){
        SharedPreferences sharedPreferences = getSharedPreferences("Term", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("agreed", true);
        editor.commit();
}

private void Validation(){
    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
    dialog.setMessage("You have not read Terms of Use");
    dialog.setTitle("Terms");
    dialog.setPositiveButton("Yes",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
    AlertDialog alertDialog = dialog.create();
    alertDialog.show();
}


}

