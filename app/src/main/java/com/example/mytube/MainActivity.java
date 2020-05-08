package com.example.mytube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
    public static MediaPlayer mediaPlayer;
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
        } catch (NullPointerException e) {

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
        seek_Bar.setMax(mediaPlayer.getDuration());
        seekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);
        if (seek_Bar.getMax() == mediaPlayer.getCurrentPosition()) {
            playNextSong();
        }
        seek_Bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                    seek_Bar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        checkbarHandler.post(CheckSeekbar);
    }

    private Handler seekbarUpdateHandler = new Handler();
    private Runnable mUpdateSeekbar = new Runnable() {
        @Override
        public void run() {
            try {
                seek_Bar.setProgress(mediaPlayer.getCurrentPosition());
                seekbarUpdateHandler.postDelayed(this, 50);
            } catch (Exception e) {

            }
        }
    };
    private Handler checkbarHandler = new Handler();
    private Runnable CheckSeekbar = new Runnable() {
        @Override
        public void run() {
            try {
                if (mediaPlayer.getDuration() == mediaPlayer.getCurrentPosition()) {
                    playNextSong();
                }
                checkbarHandler.post(this);
            } catch (Exception e) {

            }
        }
    };

    private void playPauseSong() {
        if (mediaPlayer.isPlaying()) {
            seekbarUpdateHandler.removeCallbacks(mUpdateSeekbar);
            mediaPlayer.pause();
        } else {
            seekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);
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

}
