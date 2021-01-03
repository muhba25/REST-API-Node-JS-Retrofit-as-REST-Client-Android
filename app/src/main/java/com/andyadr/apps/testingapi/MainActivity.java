package com.andyadr.apps.testingapi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.andyadr.apps.testingapi.mahasiswa.adapterMahasiswa;
import com.andyadr.apps.testingapi.model.MainViewModel;
import com.andyadr.apps.testingapi.model.mahasiswa;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.rvMahasiswaList)
    RecyclerView listMhs;
    @BindView(R.id.btIns)
    Button btnInsert;
    private RecyclerView mRecyclerView;
    private adapterMahasiswa adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, insertActivity.class));
            }
        });
        mRecyclerView = findViewById(R.id.rvMahasiswaList);
        listMhs.setLayoutManager(new LinearLayoutManager(this));
        adapter = new adapterMahasiswa(this);
        listMhs.setAdapter(adapter);

        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getMahasiswa().observe(this, getMovies);
        mainViewModel.setMahasiswa();
    }

    private Observer<ArrayList<mahasiswa>> getMovies = new Observer<ArrayList<mahasiswa>>() {
        @Override
        public void onChanged(@Nullable ArrayList<mahasiswa> mahasiswa) {
            if (mahasiswa!= null) {
                adapter.setData(mahasiswa);
            }
        }
    };



}
