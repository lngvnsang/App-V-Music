package com.example.appvmusic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.appvmusic.Adapter.DanhSachCacPlaylistAdapter;
import com.example.appvmusic.Model.Playlist;
import com.example.appvmusic.R;
import com.example.appvmusic.Service.APIService;
import com.example.appvmusic.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachCacPlaylistActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerViewDanhSachCacPlaylist;
    DanhSachCacPlaylistAdapter danhSachCacPlaylistAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_cac_playlist);
        anhXa();
        init();
        getData();
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<Playlist>> callback = dataService.GetDanhSachCacPlaylist();
        callback.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                ArrayList<Playlist> playlists = (ArrayList<Playlist>) response.body();
                Log.d("BBB",playlists.get(0).getTen() + playlists.get(1).getHinh());
                danhSachCacPlaylistAdapter = new DanhSachCacPlaylistAdapter(DanhSachCacPlaylistActivity.this,playlists);
                recyclerViewDanhSachCacPlaylist.setLayoutManager(new GridLayoutManager(DanhSachCacPlaylistActivity.this,2));
                recyclerViewDanhSachCacPlaylist.setAdapter(danhSachCacPlaylistAdapter);
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {

            }
        });
    }

    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Playlists");
        toolbar.setTitleTextColor(getResources().getColor(R.color.purple_200));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhXa() {
        toolbar = findViewById(R.id.toolbarDanhSachCacPlayList);
        recyclerViewDanhSachCacPlaylist = findViewById(R.id.recyclerViewDanhSachCacPlaylist);
    }
}