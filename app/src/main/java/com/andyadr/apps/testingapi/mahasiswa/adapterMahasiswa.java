package com.andyadr.apps.testingapi.mahasiswa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andyadr.apps.testingapi.API.ApiClient;
import com.andyadr.apps.testingapi.API.ApiEndpoints;
import com.andyadr.apps.testingapi.API.responseGetMahasiswa;
import com.andyadr.apps.testingapi.API.responsePostPutDelMahasiswa;
import com.andyadr.apps.testingapi.BuildConfig;
import com.andyadr.apps.testingapi.MainActivity;
import com.andyadr.apps.testingapi.R;
import com.andyadr.apps.testingapi.model.mahasiswa;
import com.andyadr.apps.testingapi.model.mahasiswa;
import com.andyadr.apps.testingapi.updateActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class adapterMahasiswa extends RecyclerView.Adapter<adapterMahasiswa.MahasiswaViewHolder> {
    private Context context;
    private ArrayList<mahasiswa> AllMahasiswa = new ArrayList<>();
    public adapterMahasiswa(Context context) {
        this.context = context;
    }
    public void setData(ArrayList<mahasiswa> Mahasiswaall) {
        this.AllMahasiswa = Mahasiswaall;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public adapterMahasiswa.MahasiswaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_mhs, viewGroup, false);
        return new adapterMahasiswa.MahasiswaViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterMahasiswa.MahasiswaViewHolder holder, int i) {
        holder.bind(AllMahasiswa.get(i));


    }

    @Override
    public int getItemCount() {
        return AllMahasiswa.size();
    }

    public class MahasiswaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_nama_mhs)
        TextView tv_nama_mhs;
        @BindView(R.id.tv_nim)
        TextView tv_nim;
        @BindView(R.id.tv_alamat)
        TextView tv_alamat;
        @BindView(R.id.iv_mhs)
        ImageView iv_mhs;

        private Button btnupdate,btndelete;
        private EditText etUid;
        private ApiEndpoints apiService = ApiClient.getClient().create(ApiEndpoints.class);
        ProgressDialog progressDialog;

        MahasiswaViewHolder(@NonNull final View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
            btnupdate = itemView.findViewById(R.id.btnUpdate);
            btndelete = itemView.findViewById(R.id.btnDelete);
            etUid = itemView.findViewById(R.id.etuid);
        }

        public void bind(final mahasiswa datamhs) {
            Glide.with(itemView.getContext())
                    .load( BuildConfig.BASE_URL_API + datamhs.getFoto())
                    .apply(new RequestOptions())
                    .into(iv_mhs);

            tv_nama_mhs.setText(datamhs.getNama());
            tv_alamat.setText(datamhs.getAlamat());
            tv_nim.setText(datamhs.getNim());
            etUid.setText(datamhs.getUid());

            btnupdate.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View view) {
                    Intent details = new Intent(context, updateActivity.class);
                    details.putExtra("mahasiswa", AllMahasiswa.get(getAdapterPosition()));
                    context.startActivity(details);
                }
            });

            btndelete.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(itemView.getContext())
                            .setTitle("Hapus Mahasiswa")
                            .setMessage("Ingin Menghapus Mahasiswa ini Dari Keranjang ?")
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(final DialogInterface dialog, int which) {
                                    //Toast.makeText(getActivity(), "Kamu Memilih YES", Toast.LENGTH_LONG).show();\
                                    Call<responsePostPutDelMahasiswa> call = apiService.deleteMhs(etUid.getText().toString());
                                    call.enqueue(new Callback<responsePostPutDelMahasiswa>() {
                                        @Override
                                        public void onResponse(Call<responsePostPutDelMahasiswa> call, Response<responsePostPutDelMahasiswa> response) {
                                            responsePostPutDelMahasiswa serverResponse = response.body();
                                            if (serverResponse != null) {
                                                if (serverResponse.getMessage()=="success") {
                                                    Toast.makeText(context, serverResponse.getMessage(), Toast.LENGTH_LONG).show();
                                                    dialog.cancel();
                                                } else {
                                                    Toast.makeText(itemView.getContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                                    dialog.cancel();
                                                }
                                                Intent details = new Intent(context, MainActivity.class);
                                                context.startActivity(details);
                                            } else {
                                                assert serverResponse != null;
                                                Log.v("Response", serverResponse.toString());
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<responsePostPutDelMahasiswa> call, Throwable t) {
                                            Toast.makeText(itemView.getContext(), "error", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }



                            })
                            .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();

                }
            });


        }

        @Override
        public void onClick(View v) {

        }
    }
}
