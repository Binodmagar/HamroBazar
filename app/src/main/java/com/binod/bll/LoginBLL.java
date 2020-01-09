package com.binod.bll;

import com.binod.api.UserLoginAPI;
import com.binod.serverresponse.SignUpResponse;
import com.binod.url.Url;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class LoginBLL {
    boolean isSuccess = false;

    public boolean checkUser(String username, String password) {
        UserLoginAPI userLoginAPI = Url.getInstance().create(UserLoginAPI.class);
        Call<SignUpResponse> usersCall = userLoginAPI.checkUser(username, password);

        try {
            Response<SignUpResponse> loginResponse = usersCall.execute();
            if (loginResponse.isSuccessful() && loginResponse.body().getStatus().equals("Login success")) {
                Url.token += loginResponse.body().getToken();

                isSuccess = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }
    }

