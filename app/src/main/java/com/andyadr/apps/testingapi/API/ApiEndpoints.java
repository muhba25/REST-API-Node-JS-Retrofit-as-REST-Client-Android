package com.andyadr.apps.testingapi.API;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiEndpoints {
    //produk
    @Headers({
            "apikey: 250299adradradr"
    })
    @GET("mahasiswa")
    Call<responseGetMahasiswa> getMahasiswa();

    @Multipart
    @POST("mahasiswafoto")
    Call<responsePostPutDelMahasiswa> postMhswithFoto(@Part MultipartBody.Part photo,@Part("uid") RequestBody uid);
    @FormUrlEncoded
    @POST("mahasiswa")
    Call<responsePostPutDelMahasiswa> postMhs(@Field("namalengkap") String nama, @Field("nim") String nim,
                                 @Field("alamat") String alamat);

    @FormUrlEncoded
    @PUT("mahasiswa")
    Call<responsePostPutDelMahasiswa> putMhs(@Field("uid") String uid, @Field("namalengkap") String nama,
                                           @Field("nim") String nim, @Field("alamat") String alamat);
    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "mahasiswa", hasBody = true)
    Call<responsePostPutDelMahasiswa> deleteMhs(@Field("uid") String uid);

}