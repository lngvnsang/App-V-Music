package com.example.appvmusic.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appvmusic.Model.Playlist;
import com.example.appvmusic.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlaylistAdapter extends ArrayAdapter<Playlist> {

    public PlaylistAdapter(@NonNull Context context, int resource, @NonNull List<Playlist> objects) {
        super(context, resource, objects);
    }
    class ViewHolder{
        TextView txtTenPlaylist;
        ImageView imgBackground,imgPlaylist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.dong_playlist,null);
            holder = new ViewHolder();
            holder.txtTenPlaylist = convertView.findViewById(R.id.textViewTenPlaylist);
            holder.imgPlaylist = convertView.findViewById(R.id.imageViewPlayList);
            holder.imgBackground = convertView.findViewById(R.id.imageButtonBackgroudPlaylist);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Playlist playlist = getItem(position);
        Picasso.with(getContext()).load(playlist.getHinh()).into(holder.imgBackground);
        Picasso.with(getContext()).load(playlist.getIcon()).into(holder.imgPlaylist);
        holder.txtTenPlaylist.setText(playlist.getTen());
        return convertView;
    }
}
