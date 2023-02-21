package com.example.myapplication.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Obj.PlaylistIn4;
import com.example.myapplication.Obj.VideoIn4;
import com.example.myapplication.PlayVideo;
import com.example.myapplication.PlaylistVideo;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlaylistAdapter  extends RecyclerView.Adapter<ViewHolderPlaylist> {
    private List<PlaylistIn4> playlist_data;
    private Activity activity;
    private int userID;

    public PlaylistAdapter(List<PlaylistIn4> playlist_data, Activity activity, int userID) {
        this.playlist_data = playlist_data;
        this.activity = activity;
        this.userID = userID;
    }

    @NonNull
    @Override
    public ViewHolderPlaylist onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderPlaylist(LayoutInflater.from(activity).inflate(R.layout.playlist_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPlaylist holder, int position) {
        final PlaylistIn4 plsIn4 = playlist_data.get(position);
        String pls_id = playlist_data.get(position).getPlaylistId();
        String title_pre = playlist_data.get(position).getTitle();
        String thumb = playlist_data.get(position).getThumbnail();

        String title = "";
        if(title_pre.length() >= 20){
            for(int j = 0; j < 17; j++){
                title += title_pre.charAt(j);
            }
            title+= "...";
        }
        else{
            title = title_pre;
        }
        holder.title.setText(title);
        Picasso.get().load(thumb).into(holder.thumb); // push img into img block

        //bat su kien on click trong tung item
        holder.layoutItemPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPlayVideo(pls_id);
            }
        });
    }

    private void onClickPlayVideo(String pls_id){
        Intent i = new Intent();
        i.setClass(activity, PlaylistVideo.class);
        i.putExtra("pls_id", pls_id);
        i.putExtra("user_id",userID);
        activity.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return playlist_data == null ? 0 : playlist_data.size();
    }

}
