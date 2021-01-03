package com.andyadr.apps.testingapi.model;

import android.util.Log;

import com.andyadr.apps.testingapi.API.ApiClient;
import com.andyadr.apps.testingapi.API.ApiEndpoints;
import com.andyadr.apps.testingapi.API.responseGetMahasiswa;
import com.andyadr.apps.testingapi.MainActivity;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private MutableLiveData<ArrayList<mahasiswa>> mahasiswaList = new MutableLiveData<>();
    private ApiEndpoints apiService = ApiClient.getClient().create(ApiEndpoints.class);

    public void setMahasiswa() {
        Call<responseGetMahasiswa> Produkcall = apiService.getMahasiswa();
        Produkcall.enqueue(new Callback<responseGetMahasiswa>() {
            @Override
            public void onResponse(Call<responseGetMahasiswa> call, Response<responseGetMahasiswa> response) {
                try {
                    ArrayList<mahasiswa> produk = response.body().getListDataMahasiswa();
                    mahasiswaList.postValue(produk);
                } catch (Exception e) {
                    Log.d(MainActivity.class.getSimpleName(), e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<responseGetMahasiswa> call, Throwable t) {
                Log.d(MainActivity.class.getSimpleName(), t.getLocalizedMessage());
            }
        });
    }


    public LiveData<ArrayList<mahasiswa>> getMahasiswa() {
        return mahasiswaList;
    }

}