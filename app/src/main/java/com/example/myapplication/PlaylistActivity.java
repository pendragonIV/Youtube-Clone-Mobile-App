package com.example.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Obj.PlaylistIn4;
import com.example.myapplication.Obj.VideoIn4;
import com.example.myapplication.adapter.PlaylistAdapter;
import com.example.myapplication.adapter.YoutubeAdapter;
import com.example.myapplication.add_in.DeveloperKey;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PlaylistActivity extends AppCompatActivity {


    private Database database;
    private List<PlaylistIn4> playlist_data  = new ArrayList<>();
    private List<VideoIn4> videoIn4s = new ArrayList<>();
    private PlaylistAdapter adapter;
    private RecyclerView listPlaylist;

    private String title="";
    private String thumbnailPlaylist="" ;
    private String playlistId = "";
    private String urlJson ="";
    private int userID;

    ImageView back,add_pls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist_layout);
        getSupportActionBar().hide();

        //lay user ID tu activity truoc do de su dung
        Intent login_data = getIntent();
        userID = login_data.getIntExtra("user_id",-1);

        //Tao va lay du lieu tu database cho vao Playlist object
        database = new Database(this,"love_u_tube.sqlite",null,1);
        Cursor dataUser = database.GetData("SELECT * FROM Playlist WHERE User_id = "+userID);
        while (dataUser.moveToNext()){
            String playlistID = dataUser.getString(1);
            String playlistName = dataUser.getString(2);
            String playlistImg = dataUser.getString(3);
            playlist_data.add(new PlaylistIn4(playlistName,playlistImg,playlistID));
        }

        adapter = new PlaylistAdapter(playlist_data,this,userID);
        back = (ImageView) findViewById(R.id.back);
        add_pls = (ImageView) findViewById(R.id.imageView);
        listPlaylist = (RecyclerView) findViewById(R.id.listplaylist);
        listPlaylist.setAdapter(adapter);
        listPlaylist.setLayoutManager(new GridLayoutManager(this, 2));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        add_pls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAdd();
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void DialogAdd(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_pls);
        dialog.setCanceledOnTouchOutside(false);

        final EditText edlink = (EditText) dialog.findViewById(R.id.edlink);
        final EditText edtitle = (EditText) dialog.findViewById(R.id.edtitle);
        TextView ok = (TextView) dialog.findViewById(R.id.ok);
        TextView cancel = (TextView) dialog.findViewById(R.id.cancel);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pls_link = edlink.getText().toString().trim();
                String recent_id ="";
                int index_char = pls_link.indexOf("list=");
                if(index_char == -1){
                    Toast.makeText(PlaylistActivity.this, "error!", Toast.LENGTH_SHORT).show();
                }
                else {
                    for(int i = index_char + 5; i < pls_link.length();i++){
                        recent_id += pls_link.charAt(i) ;
                    }
                    playlistId = recent_id;
                }
                title = edtitle.getText().toString().trim();

                urlJson = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId="+playlistId+"&key="+ DeveloperKey.API_KEY+"&maxResults=50";

                getJsonYoutube(urlJson);

                dialog.dismiss();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    //thanh dieu huong
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


    //Tao play list
    private void createPlaylist(String playlistId, String urlJson, String playlistTitle){

        getJsonYoutube(urlJson);

    }



    //lay thong tin tu file json
    private void getJsonYoutube(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonItems =response.getJSONArray("items");
                        //lay ra doi tuong thu i
                        JSONObject item0 = jsonItems.getJSONObject(0);
                        //vao snippet cua doi tuong thu i
                        JSONObject snippet0 = item0.getJSONObject("snippet");

                        //truy cap vao thumbnails cua snippet
                        JSONObject thumbnail0 = snippet0.getJSONObject("thumbnails");
                        JSONObject thumbMed0 = thumbnail0.getJSONObject("medium");
                        thumbnailPlaylist = thumbMed0.getString("url");

                    database.QueryData("INSERT INTO Playlist VALUES(null,'"+playlistId+"', '"+title+"', '"+thumbnailPlaylist+"', "+userID+")");
                    playlist_data.add(new PlaylistIn4(title,thumbnailPlaylist,playlistId));

                    //lay ra index cua playlist
                    Cursor dataUser = database.GetData("SELECT Id_playlist FROM Playlist WHERE Link_playlist = '"+playlistId+"'");
                    int playlistIndex = 0;
                    while (dataUser.moveToNext()){
                        playlistIndex = dataUser.getInt(0);
                    }

                    adapter.notifyDataSetChanged();
                    //set ve mac dinh
                    title="";
                    thumbnailPlaylist="" ;
                    playlistId = "";
                    urlJson ="";

                    for(int i = 0; i < jsonItems.length(); i++){
                        //lay ra doi tuong thu i
                        JSONObject item = jsonItems.getJSONObject(i);
                        //vao snippet cua doi tuong thu i
                        JSONObject snippet = item.getJSONObject("snippet");

                        //lay title
                        String titleVidPre = snippet.getString("title");

                        // Validate lai chuoi ki tu
                        String titleVid = "";
                        for (int j = 0; j < titleVidPre.length(); j++){
                            if(Character.toString(titleVidPre.charAt(j)).equals("'")){
                                titleVid+= "`";
                            }
                            else{
                                titleVid+= titleVidPre.charAt(j);
                            }
                        }

                        //lay owner
                        String ownerVidPre = snippet.getString("videoOwnerChannelTitle");
                        String ownerVid = "";
                        for (int j = 0; j < ownerVidPre.length(); j++){
                            if(Character.toString(ownerVidPre.charAt(j)).equals("'")){
                                ownerVid+= "`";
                            }
                            else{
                                ownerVid+= ownerVidPre.charAt(j);
                            }
                        }

                        //lay description
                        String descriptionPre = snippet.getString("description");
                        String description = "";
                        for (int j = 0; j < descriptionPre.length(); j++){
                            if(Character.toString(descriptionPre.charAt(j)).equals("'")){
                                description+= "`";
                            }
                            else{
                                description+= descriptionPre.charAt(j);
                            }
                        }

                        //truy cap vao thumbnails cua snippet
                        JSONObject thumbnails = snippet.getJSONObject("thumbnails");
                        JSONObject thumb_med = thumbnails.getJSONObject("medium");
                        String thumb_url = thumb_med.getString("url");
//                        Toast.makeText(PlaylistVideo.this, thumb_url, Toast.LENGTH_LONG).show();

                        //lay id video
                        JSONObject resource = snippet.getJSONObject( "resourceId");
                        String vidId = resource.getString("videoId");

                        if(!CheckIsDataAlreadyInDBorNot("Video",vidId)){
                            database.QueryData("INSERT INTO Video VALUES(null,'"+vidId+"', '"+thumb_url+"', '"+titleVid+"', '"+ownerVid+"', '"+description+"')");
                        }
                        if(!CheckIsDataAlreadyInDBorNot("Playlist_video",vidId)) {
                            database.QueryData("INSERT INTO Playlist_video VALUES(null,'" + vidId + "', '" + playlistIndex + "')");
                        }
                        videoIn4s.add(new VideoIn4(titleVid,thumb_url,ownerVid,vidId,description));

                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
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
}
