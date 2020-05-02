package com.example.mytube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private Button queue_Button, songButton;
    private ImageButton pause, previous, next;
    private SeekBar seek_Bar;
    private MediaPlayer mediaPlayer;
    private ArrayList<File> mySongs;
    private String mSongName;
    private TextView songNameTxt;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue_Button = findViewById(R.id.Sang_Liste_Knap);
        songButton = findViewById(R.id.Mine_Sange_knap);
        seek_Bar = findViewById(R.id.seekBar);
        pause = findViewById(R.id.pause);
        previous = findViewById(R.id.previous);
        next = findViewById(R.id.next);
        songNameTxt = findViewById(R.id.Sang);
        try {
            validateReceiveValuesAndStartPlaying();
        } catch (Exception e) {

        }

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPauseSong();
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.getCurrentPosition() > 0) {
                    playPreviousSong();
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.getCurrentPosition() > 0) {
                    playNextSong();
                }
            }
        });
        queue_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.queue_list);
            }
        });
        songButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Song_List.class);

                startActivity(intent);
            }
        });


//        Bundle bundle = getIntent().getExtras();
//        int[] songs = bundle.getIntArray("song");
//        String[] songsNameAndArtistArray = bundle.getStringArray("SongName");

    }

    private void validateReceiveValuesAndStartPlaying() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mySongs = (ArrayList) bundle.getParcelableArrayList("song");
        mSongName = mySongs.get(position).getName();
        String songName = getIntent().getStringExtra("name");
        songNameTxt.setText(songName);
        songNameTxt.setSelected(true);
        position = bundle.getInt("position", 0);
        Uri uri = Uri.parse(mySongs.get(position).toString());
        mediaPlayer = MediaPlayer.create(MainActivity.this, uri);
        mediaPlayer.start();
    }

    private void playPauseSong() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.start();
        }
    }

    private void playNextSong() {
        mediaPlayer.pause();
        mediaPlayer.stop();
        mediaPlayer.release();
        position = ((position + 1) % mySongs.size());
        Uri uri = Uri.parse(mySongs.get(position).toString());
        mediaPlayer = MediaPlayer.create(MainActivity.this, uri);
        mSongName = mySongs.get(position).toString();
        songNameTxt.setText(mSongName);
        mediaPlayer.start();
    }

    private void playPreviousSong() {
        mediaPlayer.pause();
        mediaPlayer.stop();
        mediaPlayer.release();
        position = ((position - 1) < 0 ? (mySongs.size() - 1) : (position - 1));
        Uri uri = Uri.parse(mySongs.get(position).toString());
        mediaPlayer = MediaPlayer.create(MainActivity.this, uri);
        mSongName = mySongs.get(position).toString();
        songNameTxt.setText(mSongName);
        mediaPlayer.start();
    }

    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);

        startActivity(intent);
    }
}
