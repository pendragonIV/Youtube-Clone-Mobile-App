package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Obj.VideoIn4;
import com.example.myapplication.adapter.YoutubeAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class HistoricActivity extends AppCompatActivity {
    private ImageView back;
    private YoutubeAdapter adapter;
    private List<VideoIn4> videoIn4s;
    private RecyclerView recyclerView;
    private Database database;
    private int userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_layout);
        getSupportActionBar().hide();

        back = (ImageView) findViewById(R.id.back);

        videoIn4s = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.listhistory);

        Intent login_data = getIntent();
        userID = login_data.getIntExtra("user_id",-1);

        database = new Database(this,"love_u_tube.sqlite",null,1);

        Cursor getVid = database.GetData("SELECT * FROM History JOIN Video ON History.Id_video = video.Id_video Where User_id = "+userID+" ORDER BY Id_historic DESC");
        while (getVid.moveToNext()){
            String title = getVid.getString(6);
            String thumb = getVid.getString(5);
            String owner = getVid.getString(7);
            String vidID = getVid.getString(1);
            String description = getVid.getString(8);
            videoIn4s.add(new VideoIn4(title,thumb,owner,vidID,description));
        }

        adapter = new YoutubeAdapter(videoIn4s,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    finish();
                    return true;
            }
            return false;
        }
    };
}