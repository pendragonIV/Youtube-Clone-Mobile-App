package com.example.myapplication.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class ViewHolderPlaylist extends RecyclerView.ViewHolder {
    LinearLayout layoutItemPlaylist;
    ImageView thumb;
    TextView title;
    public ViewHolderPlaylist(@NonNull View itemView) {
        super(itemView);
        layoutItemPlaylist = (LinearLayout) itemView.findViewById(R.id.playlist_on_list);
        thumb = (ImageView) itemView.findViewById(R.id.image_playlist);
        title = (TextView) itemView.findViewById(R.id.name_playlist);
    }
}
