package com.example.appvmusic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appvmusic.Activity.DanhSachBaiHatActivity;
import com.example.appvmusic.Model.Album;
import com.example.appvmusic.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DanhSachAllAlbumAdapter extends RecyclerView.Adapter<DanhSachAllAlbumAdapter.ViewHolder> {
    Context context;
    ArrayList<Album> mangAlbum;

    public DanhSachAllAlbumAdapter(Context context, ArrayList<Album> mangAlbum) {
        this.context = context;
        this.mangAlbum = mangAlbum;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_danh_sach_tat_ca_album,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album album = mangAlbum.get(position);
        Picasso.with(context).load(album.getHinhAlbum()).into(holder.imgDanhSaChAlbum);
        holder.txtTenCaSi.setText(album.getTenCaSiAlbum());
        holder.txtTenAlbum.setText(album.getTenAlbum());
    }

    @Override
    public int getItemCount() {
        return mangAlbum.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDanhSaChAlbum;
        TextView txtTenAlbum,txtTenCaSi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDanhSaChAlbum = itemView.findViewById(R.id.imageViewDanhSachTatCaAlbum);
            txtTenAlbum = itemView.findViewById(R.id.textViewTenDanhSachTatCaAlbum);
            txtTenCaSi = itemView.findViewById(R.id.textViewTenCaSiDanhSachAlbum);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhSachBaiHatActivity.class);
                    intent.putExtra("album",mangAlbum.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
