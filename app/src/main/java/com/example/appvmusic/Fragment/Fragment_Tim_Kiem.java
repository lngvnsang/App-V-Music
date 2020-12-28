package com.example.appvmusic.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appvmusic.Adapter.SearchBaiHatAdapter;
import com.example.appvmusic.Model.BaiHat;
import com.example.appvmusic.R;
import com.example.appvmusic.Service.APIService;
import com.example.appvmusic.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Tim_Kiem extends Fragment {
    View view;
    Toolbar toolbarSearch;
    RecyclerView recyclerViewSearchBaiHat;
    TextView txtKoCoDuLieu;
    SearchBaiHatAdapter searchBaiHatAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tim_kiem,container,false);
        toolbarSearch = view.findViewById(R.id.toolbarSearchBaiHat);
        recyclerViewSearchBaiHat = view.findViewById(R.id.recyclerViewSearchBaiHat);
        txtKoCoDuLieu = view.findViewById(R.id.textViewKhongCoDuLieu);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbarSearch);
        toolbarSearch.setTitle("");
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search,menu);
        MenuItem  menuItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchBaiHat(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
    private void searchBaiHat(String query){
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callback = dataService.GetSearchBaiHat(query);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                ArrayList<BaiHat> mangBaiHat = (ArrayList<BaiHat>) response.body();
                if (mangBaiHat.size()> 0){
                    searchBaiHatAdapter = new SearchBaiHatAdapter(getActivity(),mangBaiHat);
                    recyclerViewSearchBaiHat.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerViewSearchBaiHat.setAdapter(searchBaiHatAdapter);
                    txtKoCoDuLieu.setVisibility(View.GONE);
                    recyclerViewSearchBaiHat.setVisibility(View.VISIBLE);
                }else {
                    recyclerViewSearchBaiHat.setVisibility(View.GONE);
                    txtKoCoDuLieu.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }
}
