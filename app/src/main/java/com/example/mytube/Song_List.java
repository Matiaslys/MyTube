package com.example.mytube;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class Song_List extends AppCompatActivity {
    private String[] itemsAll;
    MediaPlayer mediaPlayer = new MediaPlayer();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list);
        listView = findViewById(R.id.listView);

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        doStuff();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    public ArrayList<File> readOnlyAudioSongs(File file) {
        ArrayList<File> arrayList = new ArrayList<>();
        File[] allFiles = file.listFiles();
        for (File individualFile : allFiles) {
            if (individualFile.isDirectory() && !individualFile.isHidden()) {
                arrayList.addAll(readOnlyAudioSongs(individualFile));
            } else {
                if (individualFile.getName().endsWith(".mp3") ||
                        individualFile.getName().endsWith(".aac") ||
                        individualFile.getName().endsWith(".wav") ||
                        individualFile.getName().endsWith(".wma")) {
                    arrayList.add(individualFile);
                }
            }
        }
        return arrayList;
    }

    public void doStuff() {

        final ArrayList<File> audioSongs = readOnlyAudioSongs(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));
        audioSongs.addAll(readOnlyAudioSongs(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)));
        itemsAll = new String[audioSongs.size()];
        for (int songCounter = 0; songCounter < audioSongs.size(); songCounter++) {
            itemsAll[songCounter] = audioSongs.get(songCounter).getName();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Song_List.this, android.R.layout.simple_list_item_1, itemsAll);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    if (MainActivity.mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        mediaPlayer.stop();
                        mediaPlayer.release();
                    }
                } catch (Exception e) {

                }
                String songName = listView.getItemAtPosition(i).toString();
                Intent intent = new Intent(Song_List.this, MainActivity.class);
                intent.putExtra("song", audioSongs);
                intent.putExtra("name", songName);
                intent.putExtra("position", i);
                startActivity(intent);
            }
        });
    }

    public void onBackPressed() {
        Intent intent = new Intent(Song_List.this, MainActivity.class);

        startActivity(intent);
    }
}
