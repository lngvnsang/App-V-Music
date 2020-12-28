package com.example.appvmusic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.appvmusic.Adapter.DanhSachAllAlbumAdapter;
import com.example.appvmusic.Model.Album;
import com.example.appvmusic.R;
import com.example.appvmusic.Service.APIService;
import com.example.appvmusic.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachTatCaAlbumActivity extends AppCompatActivity {

    RecyclerView recyclerViewAllAlbum;
    Toolbar toolbarAlAlbum;
    DanhSachAllAlbumAdapter danhSachAllAlbumAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_tat_ca_album);
        init();
        GetData();
    }

    private void GetData() {
        DataService dataService = APIService.getService();
        Call<List<Album>> callback = dataService.GetDanhSachTatCaAlbum();
        callback.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                ArrayList<Album> mangAlbum = (ArrayList<Album>) response.body();
                danhSachAllAlbumAdapter = new DanhSachAllAlbumAdapter(DanhSachTatCaAlbumActivity.this,mangAlbum);
                recyclerViewAllAlbum.setLayoutManager(new GridLayoutManager(DanhSachTatCaAlbumActivity.this,2));
                recyclerViewAllAlbum.setAdapter(danhSachAllAlbumAdapter);
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });
    }

    private void init() {
        recyclerViewAllAlbum = findViewById(R.id.recyclerViewTatCaAlbum);
        toolbarAlAlbum = findViewById(R.id.toolbarDanhSachTatCaAlbum);
        setSupportActionBar(toolbarAlAlbum);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("All Album");
        toolbarAlAlbum.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}