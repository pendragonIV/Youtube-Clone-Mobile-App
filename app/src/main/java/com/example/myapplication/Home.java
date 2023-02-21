package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Obj.VideoIn4;
import com.example.myapplication.adapter.NewVidAdapter;
import com.example.myapplication.adapter.YoutubeAdapter;
import com.example.myapplication.setting_site.SettingSite;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private Button btfavor, btplist, bthistoric;
    private ImageView iconsearch;

    private TextView userIcon,userName;
    static Database database;
    RecyclerView newestVid;
    private int userID;
    private NewVidAdapter adapter;
    private List<VideoIn4> videoIn4s;
    private String userN = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        getSupportActionBar().hide();

        btfavor = (Button) findViewById(R.id.btfavor);
        btplist = (Button) findViewById(R.id.btplist);
        bthistoric = (Button) findViewById(R.id.bthistoric);
        iconsearch = (ImageView) findViewById(R.id.iconsearch);
        userIcon = (TextView) findViewById(R.id.user_icon);
        userName = (TextView) findViewById(R.id.user_name);
        newestVid = (RecyclerView) findViewById(R.id.new_vid_home);
        videoIn4s = new ArrayList<>();


        //lay user ID tu login de su dung
        Intent login_data = getIntent();
        userID = login_data.getIntExtra("user_id",0);

        database = new Database(this,"love_u_tube.sqlite",null,1);
        Cursor dataUser = database.GetData("SELECT * FROM User WHERE Id_user = "+userID);
        while (dataUser.moveToNext()){
            userN = dataUser.getString(1);
            userIcon.setText(Character.toString(userN.charAt(0)).toUpperCase());
            userName.setText(userN);
        }

        Cursor getVid = database.GetData("SELECT * FROM Video ORDER BY Id DESC LIMIT 10");
        while (getVid.moveToNext()){
            String title = getVid.getString(3);
            String thumb = getVid.getString(2);
            String owner = getVid.getString(4);
            String vidID = getVid.getString(1);
            String description = getVid.getString(5);
            videoIn4s.add(new VideoIn4(title,thumb,owner,vidID,description));
        }

        adapter = new NewVidAdapter(videoIn4s,this,userID);
        newestVid.setAdapter(adapter);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(Home.this, LinearLayoutManager.HORIZONTAL, false);
        newestVid.setLayoutManager(horizontalLayoutManagaer);

        btfavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, FavoriteActivity.class);
                i.putExtra("user_id",userID);
                startActivity(i);
            }
        });
        btplist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, PlaylistActivity.class);
                i.putExtra("user_id",userID);
                startActivity(i);
            }
        });
        bthistoric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, HistoricActivity.class);
                i.putExtra("user_id",userID);
                startActivity(i);
            }
        });
        iconsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, SearchActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        videoIn4s.clear();
        Cursor getVid = database.GetData("SELECT * FROM Video ORDER BY Id DESC LIMIT 10");
        while (getVid.moveToNext()){
            String title = getVid.getString(3);
            String thumb = getVid.getString(2);
            String owner = getVid.getString(4);
            String vidID = getVid.getString(1);
            String description = getVid.getString(5);
            videoIn4s.add(new VideoIn4(title,thumb,owner,vidID,description));
        }
        adapter.notifyDataSetChanged();
        super.onResume();
    }
}