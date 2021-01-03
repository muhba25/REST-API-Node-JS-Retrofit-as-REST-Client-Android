package com.andyadr.apps.testingapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.andyadr.apps.testingapi.API.ApiClient;
import com.andyadr.apps.testingapi.API.ApiEndpoints;
import com.andyadr.apps.testingapi.API.responsePostPutDelMahasiswa;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.List;

public class insertActivity extends AppCompatActivity  {
    @BindView(R.id.btBackGo)
    Button btnBack;
    @BindView(R.id.btInserting)
    Button btnInserting;
    @BindView(R.id.edtNama)
    EditText edtNama;

    @BindView(R.id.edtNim)
    EditText edtNim;
    @BindView(R.id.edtAlamat)
    EditText edtAlamat;
    private ApiEndpoints apiService = ApiClient.getClient().create(ApiEndpoints.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        ButterKnife.bind(this);
        btnInserting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<responsePostPutDelMahasiswa> postMahasiswaCall = apiService.postMhs(edtNama.getText().toString(), edtNim.getText().toString(),edtAlamat.getText().toString());
                postMahasiswaCall.enqueue(new Callback<responsePostPutDelMahasiswa>() {
                    @Override
                    public void onResponse(Call<responsePostPutDelMahasiswa> call, Response<responsePostPutDelMahasiswa> response) {
                        startActivity(new Intent(insertActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailure(Call<responsePostPutDelMahasiswa> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(insertActivity.this, MainActivity.class));
                finish();
            }
        });


    }


}
