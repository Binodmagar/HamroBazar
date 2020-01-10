package com.binod.hamrobazar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.binod.adpater.ProductAdapter;
import com.binod.api.ProductAPI;
import com.binod.api.UserLoginAPI;
import com.binod.model.Product;
import com.binod.model.UserLogin;
import com.binod.url.Url;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    ImageView imgIcon;
    Dialog Dialog;
    ViewFlipper viewFlipper;
    private RecyclerView rvFirst, rvSecond;



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

        //for images slides
        int[] imgFile = {R.drawable.laptopa,R.drawable.bike,R.drawable.sofa,R.drawable.refre};
        viewFlipper = findViewById(R.id.viewFilpper);

        for(int image:imgFile){
            flipperimg(image);
        }


        //recycleview first
        rvFirst = findViewById(R.id.rvFirst);
        ProductAPI productAPI = Url.getInstance().create(ProductAPI.class);
        Call<List<Product>> listCall = productAPI.getTrendProduct();
        listCall.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
//                List<Product> treandingAdsList=new ArrayList<>();
//                treandingAdsList.add(new Product("Samsung Phone","2131230847",1000,"new",true));

                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Code"+ response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Product> productList = response.body();
                ProductAdapter productAdapter = new ProductAdapter(MainActivity.this, productList);
                rvFirst.setAdapter(productAdapter);
                rvFirst.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL,false));

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "failed" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //recycleview second
        rvSecond = findViewById(R.id.rvSecond);
        ProductAPI productAPI1 = Url.getInstance().create(ProductAPI.class);
        Call<List<Product>> listCall1 = productAPI1.getRecentProduct();
        listCall1.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Code" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Product> productList1 = response.body();
                ProductAdapter productAdapter1 = new ProductAdapter(MainActivity.this,productList1);
                rvSecond.setAdapter(productAdapter1);
                rvSecond.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erros" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        loadCurrentUser();


    }
//    public void ShowPopUp(View v){
//        Dialog.setContentView(R.layout.activity_login);
//        Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        Dialog.show();
//    }

    public void flipperimg (int image)
    {
        ImageView imageView=new ImageView(this);
        imageView.setBackgroundResource(image);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);

        //animation
        viewFlipper.setInAnimation(this,android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this,android.R.anim.slide_out_right);

    }

    private void loadCurrentUser(){
        UserLoginAPI userLoginAPI = Url.getInstance().create(UserLoginAPI.class);
        Call<UserLogin> userLoginCall = userLoginAPI.getUserDetails(Url.token);

        userLoginCall.enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Code" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                String imgPath = Url.imagePath + response.body().getImage();

                Picasso.get().load(imgPath).into(imgIcon);
            }

            @Override
            public void onFailure(Call<UserLogin> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Errors" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}



