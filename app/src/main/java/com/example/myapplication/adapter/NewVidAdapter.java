package com.example.myapplication.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.PlayVideo;
import com.example.myapplication.R;
import com.example.myapplication.Obj.VideoIn4;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewVidAdapter extends RecyclerView.Adapter<NewVidViewHolder> {

    private List<VideoIn4> video_data;
    private Activity activity;
    private int userID;

    public NewVidAdapter(List<VideoIn4> video_data, Activity activity, int userID) {
        this.video_data = video_data;
        this.activity = activity;
        this.userID = userID;
    }

    @NonNull
    @Override
    public NewVidViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new NewVidViewHolder(LayoutInflater.from(activity).inflate(R.layout.new_video_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewVidViewHolder holder, int position) {
        final VideoIn4 vidIn4 = video_data.get(position);
        String title_pre = video_data.get(position).getTitle();
        String thumb = video_data.get(position).getThumbnail();

        String title = "";
        if(title_pre.length() >= 30){
            for(int j = 0; j < 27; j++){
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
        holder.layoutItemNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPlayVideo(vidIn4);
            }
        });
    }


    private void onClickPlayVideo(VideoIn4 vidIn4){
        Intent i = new Intent();
        i.setClass(activity, PlayVideo.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("vid_obj", vidIn4);
        bundle.putInt("user_id",userID);
        i.putExtras(bundle);
        activity.startActivity(i);

    }

    @Override
    public int getItemCount() {
        return video_data == null ? 0 : video_data.size();
    }
}

