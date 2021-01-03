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
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andyadr.apps.testingapi.API.ApiClient;
import com.andyadr.apps.testingapi.API.ApiEndpoints;
import com.andyadr.apps.testingapi.API.responsePostPutDelMahasiswa;
import com.andyadr.apps.testingapi.model.mahasiswa;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.List;

public class updateActivity extends AppCompatActivity {
    @BindView(R.id.btBackGoup)
    Button btnBackup;
    @BindView(R.id.btUpdating)
    Button btnUpdating;
    @BindView(R.id.tvNamaupdate)
    TextView tvNamaupdate;
    @BindView(R.id.iv_upmhs)
    ImageView iv_upmhs;
    @BindView(R.id.edtNamaupd)
    EditText edtNamaupd;
    @BindView(R.id.edtNimupd)
    EditText edtNimupd;
    @BindView(R.id.btupImage)
    Button btupImage;
    @BindView(R.id.btpickImage)
    Button btpickImage;
    @BindView(R.id.edtUid)
    EditText edtUid;
    @BindView(R.id.filename)
    TextView str1;
    @BindView(R.id.edtAlamatupd)
    EditText edtAlamatupd;

    ProgressDialog progressDialog;
    String mediaPath, mediaPath1;
    String[] mediaColumns = {MediaStore.Video.Media._ID};
    TextView str2;
    private ApiEndpoints apiService = ApiClient.getClient().create(ApiEndpoints.class);
    private mahasiswa mhs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        ButterKnife.bind(this);
        Intent intent = getIntent();
        mhs = intent.getParcelableExtra("mahasiswa");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        showDetails(mhs);
        edtUid.setVisibility(View.GONE);
        Glide.with(updateActivity.this)
                .load( BuildConfig.BASE_URL_API + mhs.getFoto())
                .into(iv_upmhs);
        btnUpdating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<responsePostPutDelMahasiswa> postMahasiswaCall = apiService.putMhs(edtUid.getText().toString(), edtNamaupd.getText().toString(),edtNimupd.getText().toString(),edtAlamatupd.getText().toString());
                postMahasiswaCall.enqueue(new Callback<responsePostPutDelMahasiswa>() {
                    @Override
                    public void onResponse(Call<responsePostPutDelMahasiswa> call, Response<responsePostPutDelMahasiswa> response) {
                        startActivity(new Intent(updateActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailure(Call<responsePostPutDelMahasiswa> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btnBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(updateActivity.this, MainActivity.class));
                finish();
            }
        });

        btupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });

        btpickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
            }
        });
    }

    private void showDetails(mahasiswa mhsw) {
        edtNamaupd.setText(mhsw.getNama());
        edtNimupd.setText(mhsw.getNim());
        edtAlamatupd.setText(mhsw.getAlamat());
        tvNamaupdate.setText(mhsw.getNama());
        edtUid.setText(mhsw.getUid());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                str1.setText(mediaPath);
                // Set the Image in ImageView for Previewing the Media
                iv_upmhs.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();

            } // When an Video is picked
            else if (requestCode == 1 && resultCode == RESULT_OK && null != data) {

                // Get the Video from data
                Uri selectedVideo = data.getData();
                String[] filePathColumn = {MediaStore.Video.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedVideo, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                mediaPath1 = cursor.getString(columnIndex);
                str2.setText(mediaPath1);
                // Set the Video Thumb in ImageView Previewing the Media
                iv_upmhs.setImageBitmap(getThumbnailPathForLocalFile(updateActivity.this, selectedVideo));
                cursor.close();

            } else {
                Toast.makeText(this, "You haven't picked Image/Video", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }

    // Providing Thumbnail For Selected Image
    public Bitmap getThumbnailPathForLocalFile(Activity context, Uri fileUri) {
        long fileId = getFileId(context, fileUri);
        return MediaStore.Video.Thumbnails.getThumbnail(context.getContentResolver(),
                fileId, MediaStore.Video.Thumbnails.MICRO_KIND, null);
    }

    // Getting Selected File ID
    public long getFileId(Activity context, Uri fileUri) {
        Cursor cursor = context.managedQuery(fileUri, mediaColumns, null, null, null);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
            return cursor.getInt(columnIndex);
        }
        return 0;
    }

    // Uploading Image/Video
    private void uploadFile() {
        progressDialog.show();

        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(mediaPath);

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("photo", file.getName(), requestBody);
        RequestBody kodeuser = RequestBody.create(MediaType.parse("text/plain"), mhs.getUid());
        ApiEndpoints getResponse = ApiClient.getClient().create(ApiEndpoints.class);
        Call<responsePostPutDelMahasiswa> call = getResponse.postMhswithFoto(fileToUpload, kodeuser);
        call.enqueue(new Callback<responsePostPutDelMahasiswa>() {
            @Override
            public void onResponse(Call<responsePostPutDelMahasiswa> call, Response<responsePostPutDelMahasiswa> response) {
                responsePostPutDelMahasiswa serverResponse = response.body();
                if (serverResponse != null) {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(updateActivity.this, MainActivity.class));
                        finish();
                } else {
                    assert serverResponse != null;
                    Log.v("Response", serverResponse.toString());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<responsePostPutDelMahasiswa> call, Throwable t) {

            }
        });
    }
}
