package com.andyadr.apps.testingapi.API;

import com.andyadr.apps.testingapi.model.mahasiswa;
import com.andyadr.apps.testingapi.model.mahasiswa;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class responseGetMahasiswa {
    @SerializedName("data")
    ArrayList<mahasiswa> results;
    @SerializedName("status")
    String status;

    public ArrayList<mahasiswa> getListDataMahasiswa() {
        return results;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setListDataProduk(ArrayList<mahasiswa> listDataProduk) {
        this.results = listDataProduk;
    }
}
