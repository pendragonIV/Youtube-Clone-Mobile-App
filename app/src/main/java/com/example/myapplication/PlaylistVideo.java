package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Obj.PlaylistIn4;
import com.example.myapplication.Obj.VideoIn4;
import com.example.myapplication.adapter.YoutubeAdapter;
import com.example.myapplication.add_in.DeveloperKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PlaylistVideo extends AppCompatActivity {
    private ImageView back;
    private TextView playlistName;
    private RecyclerView listVideo;
    private int userID;
    private YoutubeAdapter adapter;
    private static Database database;
    private List<VideoIn4> videoIn4s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_video);
        getSupportActionBar().hide();



        back = (ImageView) findViewById(R.id.back);
        listVideo = (RecyclerView) findViewById(R.id.list_vid);
        playlistName = (TextView) findViewById(R.id.playlist_name);

        Intent i = getIntent();
        String playlistID = i.getStringExtra("pls_id").toString();
        userID = i.getIntExtra("user_id",-1);

        if (playlistID == null || playlistID == "" ) {
            Toast.makeText(this, "error!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


        int playlistIndex = 0;
        database = new Database(this,"love_u_tube.sqlite",null,1);
        Cursor dataUser = database.GetData("SELECT * FROM Playlist WHERE Link_playlist = '"+playlistID+"'");
        while (dataUser.moveToNext()){
            playlistIndex = dataUser.getInt(0);
            playlistName.setText(dataUser.getString(2));
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        videoIn4s = new ArrayList<>();
        Cursor getVid = database.GetData("SELECT * FROM playlist_video JOIN video ON playlist_video.Id_video = video.Id_video Where playlist_id = "+playlistIndex);
        while (getVid.moveToNext()){
            String title = getVid.getString(6);
            String thumb = getVid.getString(5);
            String owner = getVid.getString(7);
            String vidID = getVid.getString(4);
            String description = getVid.getString(8);
            videoIn4s.add(new VideoIn4(title,thumb,owner,vidID,description));
        }

        adapter = new YoutubeAdapter(videoIn4s,this,userID,database);
        listVideo.setAdapter(adapter);
        listVideo.setLayoutManager(new LinearLayoutManager(this));
    }
}