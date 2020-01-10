package com.binod.hamrobazar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.UserDictionary;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.binod.api.UserLoginAPI;
import com.binod.model.UserLogin;
import com.binod.serverresponse.ImageResponse;
import com.binod.serverresponse.SignUpResponse;
import com.binod.url.Url;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import strictmode.StrictModeClass;

public class RegisterActivity extends AppCompatActivity{
    CircleImageView imgProfile;
    EditText etEmailR,etFullName,etPasswordR,etConfirmPassword,etPhoneNo,etMobileNo,etStreet,etArea;
    Spinner etCity;
    CheckBox cbHidePhone, cbAgree,cbNewsLetter;
    Button btnRegister;
    String imagePath;
    String imgName = "";

    //static value of city
    public static final String City[] = {
            "Pokhara",
            "Kathmandu",
            "Butwal",
            "Baglung",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //binding
        imgProfile = findViewById(R.id.imgProfile);
        etEmailR = findViewById(R.id.etEmailR);
        etFullName = findViewById(R.id.etFullName);
        etPasswordR = findViewById(R.id.etPasswordR);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etPhoneNo = findViewById(R.id.etPhoneNo);
        etMobileNo = findViewById(R.id.etMobileNo);
        etStreet = findViewById(R.id.etStreet);
        etArea = findViewById(R.id.etArea);
        etCity = findViewById(R.id.etCity);
        cbAgree = findViewById(R.id.cbAgree);
        cbHidePhone = findViewById(R.id.cbHidePhone);
        cbNewsLetter = findViewById(R.id.cbNewsLetter);
        btnRegister = findViewById(R.id.btnRegister);

        //setting arraydapter for city
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1,City

        );
        etCity.setAdapter(arrayAdapter);

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageBrowse();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etConfirmPassword.getText().toString().equals(etPasswordR.getText().toString())){
                    if(validation()){
                        saveImage();
                        register();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }else{
                    Toast.makeText(RegisterActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                    etPasswordR.requestFocus();
                    return;
                }
            }
        });
    }
    //validation for register user
    private boolean validation(){
        boolean status = true;
        if(TextUtils.isEmpty(etEmailR.getText().toString())){
            etEmailR.setError("Please enter your email address");
            etEmailR.requestFocus();
            status = false;
        }
        if(TextUtils.isEmpty(etFullName.getText().toString())){
            etFullName.setError("Please enter your full name");
            etFullName.requestFocus();
            status = false;
        }
        if(etPasswordR.getText().toString().length() < 6){
            etPasswordR.setError("Your password must be at least 6 character");
            etPasswordR.requestFocus();
            status = false;
        }
        if(TextUtils.isEmpty(etPhoneNo.getText().toString())){
            etPhoneNo.setError("Please enter your phone number");
            etPhoneNo.requestFocus();
            status = false;
        }
        if(etMobileNo.getText().toString().length() < 10){
            etMobileNo.setError("Your phone number must be of 10 digits");
            etMobileNo.requestFocus();
            status = false;
        }
        if(TextUtils.isEmpty(etStreet.getText().toString())){
            etStreet.setError("Please enter your street");
            etStreet.requestFocus();
            status = false;
        }
        if(TextUtils.isEmpty(etArea.getText().toString())){
            etArea.setError("Please enter your area");
            etArea.requestFocus();
            status = false;
        }
        cbNewsLetter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cbNewsLetter.setTag(isChecked? true : false);
            }
        });
        cbHidePhone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cbHidePhone.setTag(isChecked? true : false);
            }
        });
        cbAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cbAgree.setTag(isChecked? true : false);
            }
        });
        return status;
    }

    private void ImageBrowse(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(data == null){
                Toast.makeText(this, "Please select your image", Toast.LENGTH_SHORT).show();
            }
        }

        Uri uri = data.getData();
        imgProfile.setImageURI(uri);
        imagePath = getRealPathFormUri(uri);
    }

    private String getRealPathFormUri(Uri uri){
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(),
                uri, projection, null, null,null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return  result;
    }

    private void saveImage(){
        File file = new File((imagePath));
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile", file.getName(), requestBody);

        UserLoginAPI userLoginAPI = Url.getInstance().create(UserLoginAPI.class);
        Call<ImageResponse> responseCall = userLoginAPI.uploadImage(body);

        StrictModeClass.StrictMode();

        //Synchronous method
        try{
            Response<ImageResponse> imageResponseResponse = responseCall.execute();
            imgName = imageResponseResponse.body().getFilename();
            Toast.makeText(this, "Image inserted successs", Toast.LENGTH_SHORT).show();
        }
        catch (IOException e){
            Toast.makeText(this, "Erros", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private  void register(){
        String username = etEmailR.getText().toString();
        String fullName = etFullName.getText().toString();
        String password = etPasswordR.getText().toString();
        String phone = etPhoneNo.getText().toString();
        String mobilePhone = etMobileNo.getText().toString();
        String street = etStreet.getText().toString();
        String area = etArea.getText().toString();
        String city = etCity.getSelectedItem().toString();
        boolean newsletter = Boolean.parseBoolean(String.valueOf(cbNewsLetter.isChecked()? true : false));
        boolean hidePhone = Boolean.parseBoolean(String.valueOf(cbHidePhone.isChecked()? true : false));
        boolean agree = Boolean.parseBoolean(String.valueOf(cbAgree.isChecked()?true : false));

        UserLogin userLogin = new UserLogin(fullName, username, password, phone, mobilePhone, street, area, city, newsletter, hidePhone, agree, imgName);


        UserLoginAPI userLoginAPI = Url.getInstance().create(UserLoginAPI.class);
        Call<SignUpResponse> signUpResponseCall = userLoginAPI.registerUser(userLogin);
        CheckPermission();
        signUpResponseCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Error Code", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error code" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void CheckPermission()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED ||ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        }
    }
}


