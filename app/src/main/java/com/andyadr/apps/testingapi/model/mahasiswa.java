package com.andyadr.apps.testingapi.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class mahasiswa implements Parcelable {
    @SerializedName("id_mhs")
    private String uid;

    @SerializedName(value = "nama_mhs")
    private String nama;

    @SerializedName(value = "nim")
    private String nim;

    @SerializedName(value = "alamat")
    private String alamat;

    @SerializedName(value = "foto")
    private String foto;


    public String getUid() {
        return uid;
    }

    public mahasiswa(String uid, String nama, String nim, String alamat, String foto) {
        this.uid = uid;
        this.nama = nama;
        this.nim = nim;
        this.alamat = alamat;
        this.foto = foto;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uid);
        parcel.writeString(nama);
        parcel.writeString(nim);
        parcel.writeString(alamat);
        parcel.writeString(foto);
    }

    protected mahasiswa(Parcel in) {
        uid = in.readString();
        nama = in.readString();
        nim = in.readString();
        alamat = in.readString();
        foto = in.readString();
    }

    public static final Creator<mahasiswa> CREATOR = new Creator<mahasiswa>() {
        @Override
        public mahasiswa createFromParcel(Parcel in) {
            return new mahasiswa(in);
        }

        @Override
        public mahasiswa[] newArray(int size) {
            return new mahasiswa[size];
        }
    };

}
