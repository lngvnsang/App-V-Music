package com.example.appvmusic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.appvmusic.Adapter.DanhSachTheLoaiTheoChuDeAdapter;
import com.example.appvmusic.Model.ChuDe;
import com.example.appvmusic.Model.TheLoai;
import com.example.appvmusic.R;
import com.example.appvmusic.Service.APIService;
import com.example.appvmusic.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangSachTheLoaiTheoChuDeActivity extends AppCompatActivity {

    ChuDe chuDe;
    DanhSachTheLoaiTheoChuDeAdapter danhSachTheLoaiTheoChuDeAdapter;
    Toolbar toolbarTheLoaiTheoChuDe;
    RecyclerView recyclerViewTheLoaiTheoChuDe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_sach_the_loai_theo_chu_de);
        GetIntent();
        init();
        GetData(chuDe.getIdChuDe());
    }

    private void GetData(String idchude) {
        DataService dataService = APIService.getService();
        Call<List<TheLoai>> callback = dataService.GetTheLoaiTheoChuDe(idchude);
        callback.enqueue(new Callback<List<TheLoai>>() {
            @Override
            public void onResponse(Call<List<TheLoai>> call, Response<List<TheLoai>> response) {
                ArrayList<TheLoai> mangTheLoai = (ArrayList<TheLoai>) response.body();
                danhSachTheLoaiTheoChuDeAdapter = new DanhSachTheLoaiTheoChuDeAdapter(DangSachTheLoaiTheoChuDeActivity.this,mangTheLoai);
                recyclerViewTheLoaiTheoChuDe.setLayoutManager(new GridLayoutManager(DangSachTheLoaiTheoChuDeActivity.this,2));
                recyclerViewTheLoaiTheoChuDe.setAdapter(danhSachTheLoaiTheoChuDeAdapter);
            }

            @Override
            public void onFailure(Call<List<TheLoai>> call, Throwable t) {

            }
        });
    }

    private void init() {
        toolbarTheLoaiTheoChuDe = findViewById(R.id.toolbarTheLoaiTheoChuDe);
        recyclerViewTheLoaiTheoChuDe = findViewById(R.id.recyclerViewTheLoaiTheoChuDe);
        setSupportActionBar(toolbarTheLoaiTheoChuDe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(chuDe.getTenChuDe());
        toolbarTheLoaiTheoChuDe.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("chude")){
            chuDe = (ChuDe) intent.getSerializableExtra("chude");
        }
    }
}