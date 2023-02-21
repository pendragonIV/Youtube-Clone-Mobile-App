package com.example.myapplication.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class NewVidViewHolder  extends RecyclerView.ViewHolder {
    LinearLayout layoutItemNew;
    ImageView thumb;
    TextView title;
    public NewVidViewHolder(@NonNull View itemView) {
        super(itemView);
        layoutItemNew = (LinearLayout) itemView.findViewById(R.id.new_on_list);
        thumb = (ImageView) itemView.findViewById(R.id.image_newest_vid);
        title = (TextView) itemView.findViewById(R.id.title_newest_vid);
    }
}
