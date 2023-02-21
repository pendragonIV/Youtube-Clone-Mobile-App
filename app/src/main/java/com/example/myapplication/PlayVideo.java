package com.example.myapplication;

import static android.os.PowerManager.PARTIAL_WAKE_LOCK;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PowerManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Obj.VideoIn4;
import com.example.myapplication.add_in.DeveloperKey;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import io.github.glailton.expandabletextview.ExpandableTextView;

public class PlayVideo extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{

    String playlist_link=  "PLhfk_v84FE_2TVk2aKDY25F1K-l8kdl-s";
    YouTubePlayerView youTubePlayerView;

    private TextView title,owner;
    private ExpandableTextView description;
    private String vid_id;
    private Database database;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);


        title = findViewById(R.id.title_plv);
        owner = findViewById(R.id.owner_plv);
        description = findViewById(R.id.expand_tv);

        database = new Database(this,"love_u_tube.sqlite",null,1);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            Toast.makeText(this, "error!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        VideoIn4 videoIn4 = (VideoIn4) bundle.get("vid_obj");
        userID = bundle.getInt("user_id");

        title.setText(videoIn4.getTitle());
        owner.setText(videoIn4.getOwner());
        description.setText(videoIn4.getDescription());
        vid_id = videoIn4.getVideoId();

        // gan doi tuong ytpw cho id trong file giao dien xml
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.play_video_scr);

        //Khoi tao dich vu voi API key da duoc tao ngay luc mo len
        youTubePlayerView.initialize(DeveloperKey.API_KEY, this);


    }


    // Truong hop khoi tao dich vu thanh cong
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.loadVideo(vid_id,0);
        if(!CheckIsDataAlreadyInDBorNot("History",vid_id)) {
            database.QueryData("INSERT INTO History VALUES(null, '" + vid_id + "', " + userID + ")");
        }
    }


    // Truong hop khoi tao that bai
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        //kiem tra xem co phai loi phia nguoi dung hay khong
        if(youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(PlayVideo.this,DeveloperKey.REQUEST_VIDEO);
        }
        else{
            Toast.makeText(this, "ERORR!", Toast.LENGTH_SHORT).show();
        }
    }


    //Neu o tren co xuat hien loi nguoi dung, thu khoi tao lai
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == DeveloperKey.REQUEST_VIDEO){
            youTubePlayerView.initialize(DeveloperKey.API_KEY,PlayVideo.this);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //ham check data
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

}