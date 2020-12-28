package com.example.appvmusic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.appvmusic.Adapter.DanhSachTatCaChuDeAdapter;
import com.example.appvmusic.Model.ChuDe;
import com.example.appvmusic.R;
import com.example.appvmusic.Service.APIService;
import com.example.appvmusic.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachCacChuDeActivity extends AppCompatActivity {

    Toolbar toolbarTatCaChuDe;
    RecyclerView recyclerViewTatCaChuDe;
    DanhSachTatCaChuDeAdapter danhSachTatCaChuDeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_cac_chu_de);
        init();
        getData();
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<ChuDe>> callback = dataService.GetDanhSachCacChuDe();
        callback.enqueue(new Callback<List<ChuDe>>() {
            @Override
            public void onResponse(Call<List<ChuDe>> call, Response<List<ChuDe>> response) {
                ArrayList<ChuDe> mangChuDe = (ArrayList<ChuDe>) response.body();
                danhSachTatCaChuDeAdapter = new DanhSachTatCaChuDeAdapter(DanhSachCacChuDeActivity.this,mangChuDe);
                recyclerViewTatCaChuDe.setLayoutManager(new GridLayoutManager(DanhSachCacChuDeActivity.this,1));
                recyclerViewTatCaChuDe.setAdapter(danhSachTatCaChuDeAdapter);
            }

            @Override
            public void onFailure(Call<List<ChuDe>> call, Throwable t) {

            }
        });
    }

    private void init() {
        recyclerViewTatCaChuDe = findViewById(R.id.recyclerViewTatCaChuDe);
        toolbarTatCaChuDe =findViewById(R.id.toobarTatCaChuDe);
        setSupportActionBar(toolbarTatCaChuDe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tất cả chủ đề");
        toolbarTatCaChuDe.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}