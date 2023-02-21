package com.example.myapplication.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;


public class ViewHolder extends RecyclerView.ViewHolder {
    LinearLayout layoutItem;
    ImageView thumb,option_btn;
    TextView title,owner;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        option_btn = (ImageView) itemView.findViewById(R.id.video_option);
        layoutItem = (LinearLayout) itemView.findViewById(R.id.video_on_list);
        thumb = (ImageView) itemView.findViewById(R.id.thumb);
        title = (TextView) itemView.findViewById(R.id.vid_title);
        owner = (TextView) itemView.findViewById(R.id.owner);
    }
}
