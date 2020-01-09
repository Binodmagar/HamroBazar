package com.binod.api;

import com.binod.model.UserLogin;
import com.binod.serverresponse.ImageResponse;
import com.binod.serverresponse.SignUpResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserLoginAPI {

    @POST("users/signup")
    Call<SignUpResponse> registerUser(@Body UserLogin users);

    @FormUrlEncoded
    @POST("users/login")
    Call<SignUpResponse> checkUser(@Field("email") String username, @Field("password") String password);


    @Multipart
    @POST("upload")
    Call<ImageResponse> uploadImage(@Part MultipartBody.Part img);

    @GET
    Call<UserLogin> getUserDetails(@Header("Authorization") String token);
}
