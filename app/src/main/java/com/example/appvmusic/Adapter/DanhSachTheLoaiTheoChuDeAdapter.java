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
import com.example.appvmusic.Model.TheLoai;
import com.example.appvmusic.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DanhSachTheLoaiTheoChuDeAdapter extends RecyclerView.Adapter<DanhSachTheLoaiTheoChuDeAdapter.ViewHolder> {
    Context context;
    ArrayList<TheLoai> mangTheLoai;

    public DanhSachTheLoaiTheoChuDeAdapter(Context context, ArrayList<TheLoai> mangTheLoai) {
        this.context = context;
        this.mangTheLoai = mangTheLoai;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_the_loai_theo_chu_de,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TheLoai theLoai = mangTheLoai.get(position);
        holder.txtTheLoai.setText(theLoai.getTenTheLoai());
        Picasso.with(context).load(theLoai.getHinhTheLoai()).into(holder.imghinhNen);
    }

    @Override
    public int getItemCount() {
        return mangTheLoai.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imghinhNen;
        TextView txtTheLoai;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imghinhNen = itemView.findViewById(R.id.imageViewTheLoaiTheoChuDe);
            txtTheLoai = itemView.findViewById(R.id.textViewTheLoaiTheoChuDe);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhSachBaiHatActivity.class);
                    intent.putExtra("idtheloai",mangTheLoai.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
