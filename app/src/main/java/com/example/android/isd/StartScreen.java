package com.example.android.isd;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.VideoView;

public class StartScreen extends AppCompatActivity {
    public Context cs=this;
    VideoView videoview;
    MediaPlayer  mediaPlayer;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_page);

          videoview = (VideoView) findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.bkground1wat);
        videoview.setVideoURI(uri);
        videoview.start();
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });



        Button strt=(Button) findViewById(R.id.strtapp);
        strt.setOnClickListener(new View.OnClickListener() {
 public void onClick(View v) {
Intent i=new Intent(cs,Sign_in.class);
startActivity(i);
finish();
}
        });
    }
}
