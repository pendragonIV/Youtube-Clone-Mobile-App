package com.example.myapplication.add_in;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Database;
import com.example.myapplication.Home;
import com.example.myapplication.Login;
import com.example.myapplication.Obj.PlaylistIn4;
import com.example.myapplication.Obj.VideoIn4;
import com.example.myapplication.PlaylistVideo;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SplashScreen extends AppCompatActivity {
    //Set thoi gian cho 3s
    int SPLASH_TIME_OUT = 3000;

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        getSupportActionBar().hide();

        database = new Database(this,"love_u_tube.sqlite",null,1);


        database.QueryData("CREATE TABLE IF NOT EXISTS User(Id_user INTEGER PRIMARY KEY AUTOINCREMENT, Username VARCHAR(255), Email VARCHAR(255), Password VARCHAR(255))");
        database.QueryData("CREATE TABLE IF NOT EXISTS Favorite(Id_favorite INTEGER PRIMARY KEY AUTOINCREMENT, Id_video VARCHAR(255), User_id INTEGER, FOREIGN KEY (User_id) REFERENCES User(Id_user))");
        database.QueryData("CREATE TABLE IF NOT EXISTS Playlist(Id_playlist INTEGER PRIMARY KEY AUTOINCREMENT, Link_playlist VARCHAR(255), Playlist_name VARCHAR(255), Playlist_img TEXT, User_id INTEGER, FOREIGN KEY (User_id) REFERENCES User(Id_user))");
        database.QueryData("CREATE TABLE IF NOT EXISTS History(Id_historic INTEGER PRIMARY KEY AUTOINCREMENT, Id_video VARCHAR(255), User_id INTEGER, FOREIGN KEY (User_id) REFERENCES User(Id_user))");
        database.QueryData("CREATE TABLE IF NOT EXISTS Video(Id INTEGER PRIMARY KEY AUTOINCREMENT, Id_video VARCHAR(255), image_video VARCHAR(255), title_video VARCHAR(255), owner VARCHAR(255), description TEXT)");
        database.QueryData("CREATE TABLE IF NOT EXISTS Playlist_video(Id INTEGER PRIMARY KEY AUTOINCREMENT, Id_video VARCHAR(255),playlist_id INTEGER, FOREIGN KEY (playlist_id) REFERENCES Playlist(Id_playlist))");

//        getJsonYoutube("https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLhfk_v84FE_04BnxxcK8DR45kew7P7tbF&key=AIzaSyAJOCpfSvEkS5pk5SHh6_DrtHAdTbHW92M&maxResults=50");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, Login.class);
                startActivity(i);
                finish();
            }
        },SPLASH_TIME_OUT);
    }

//    private void getJsonYoutube(String url) {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    JSONArray jsonItems =response.getJSONArray("items");
//
//                    for(int i = 23; i < 30; i++){
//                        //lay ra doi tuong thu i
//                        JSONObject item = jsonItems.getJSONObject(i);
//                        //vao snippet cua doi tuong thu i
//                        JSONObject snippet = item.getJSONObject("snippet");
//
//                        //lay title
//                        String titleVid = snippet.getString("title");
//
//                        //lay owner
//                        String ownerVid = snippet.getString("videoOwnerChannelTitle");
//                        Toast.makeText(SplashScreen.this, ownerVid, Toast.LENGTH_SHORT).show();
//
//                        //lay description
//                        String description = snippet.getString("description");
//
//                        //truy cap vao thumbnails cua snippet
//                        JSONObject thumbnails = snippet.getJSONObject("thumbnails");
//                        JSONObject thumb_med = thumbnails.getJSONObject("medium");
//                        String thumb_url = thumb_med.getString("url");
////                        Toast.makeText(PlaylistVideo.this, thumb_url, Toast.LENGTH_LONG).show();
//
//                        //lay id video
//                        JSONObject resource = snippet.getJSONObject( "resourceId");
//                        String vidId = resource.getString("videoId");
//                    }
//
//
//
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        requestQueue.add(jsonObjectRequest);
//    }
//    public boolean CheckIsDataAlreadyInDBorNot(String table, String field) {
//
//        String query = "Select * from "+table+" where Id_video = '" + field+"'";
//
//        Cursor dataUser = database.GetData(query);
//        if(dataUser.moveToFirst()){
//            dataUser.close();
//            return true;
//        }
//        dataUser.close();
//        return false;
//    }
}