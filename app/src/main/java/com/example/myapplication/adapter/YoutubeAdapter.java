package com.example.myapplication.adapter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Database;
import com.example.myapplication.PlayVideo;
import com.example.myapplication.R;
import com.example.myapplication.Obj.VideoIn4;
import com.squareup.picasso.Picasso;

import java.util.List;

public class YoutubeAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<VideoIn4> video_data;
    private Activity activity;
    private int userID;

    private Database database;

    public YoutubeAdapter(List<VideoIn4> video_data, Activity activity, int userID, Database database) {
        this.video_data = video_data;
        this.activity = activity;
        this.userID = userID;
        this.database = database;
    }

    public YoutubeAdapter(List<VideoIn4> video_data, Activity activity) {
        this.video_data = video_data;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.video_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final VideoIn4 vidIn4 = video_data.get(position);
        String title_pre = video_data.get(position).getTitle();
        String owner = video_data.get(position).getOwner();
        String thumb = video_data.get(position).getThumbnail();
        String videoID = video_data.get(position).getVideoId();

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

        holder.owner.setText(owner);
        holder.title.setText(title);
        Picasso.get().load(thumb).into(holder.thumb); // push img into img block

        holder.option_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(activity,holder.option_btn);
                popupMenu.getMenuInflater().inflate(R.menu.video_option,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.add_fav:{
                                if(!CheckIsDataAlreadyInDBorNot("Favorite",videoID)) {
                                    database.QueryData("INSERT INTO Favorite VALUES(null, '" + videoID + "', " + userID + ")");
                                    Toast.makeText(activity, "This video id added to favorite", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(activity, "This video already in favorite", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            }
                            default:{
                                break;
                            }
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        //bat su kien on click trong tung item
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPlayVideo(vidIn4);
            }
        });
    }

    public boolean CheckIsDataAlreadyInDBorNot(String table, String field) {

        String query = "Select * from "+table+" where Id_video = '" + field+"'";

        Cursor dataUser = database.GetData(query);
        if(dataUser.moveToFirst()){
            dataUser.close();
            return true;
        }
        dataUser.close();
        return false;
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

//
//    @Override
//    public int getCount() {
//        return video_data.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return video_data.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        convertView =activity.getLayoutInflater().inflate(R.layout.video_item,null);
//
//        ImageView thumb = convertView.findViewById(R.id.thumb);
//        TextView title = convertView.findViewById(R.id.vid_title);
//        TextView owner = convertView.findViewById(R.id.owner);
//
//        VideoIn4 videoIn4 = video_data.get(position);
//
//        title.setText(videoIn4.getTitle());
//        owner.setText(videoIn4.getOwner());
//        Picasso.get().load(videoIn4.getThumbnail()).into(thumb); // push img into img block
//
//        return convertView;
//    }
