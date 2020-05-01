package com.example.mytube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Song_List extends AppCompatActivity {
    private String[] itemsAll;
    private static final int MY_PERMISSION_REQUEST = 1;
    ArrayList<String> arrayList;
    MediaPlayer mediaPlayer = new MediaPlayer();
//    ArrayList<MediaPlayer> listSongs = new ArrayList<>();
    ArrayList<Long> list = new ArrayList<>();
    ListView listView;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song__list2);

        if (ContextCompat.checkSelfPermission(Song_List.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Song_List.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(Song_List.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            } else {
                ActivityCompat.requestPermissions(Song_List.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }
        }
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

    public void getMusic() {
        ContentResolver contentResolver = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null, null, null, null);
        if (songCursor != null && songCursor.moveToFirst()) {
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int idColumn = songCursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
            do {
                long thisId = songCursor.getLong(idColumn);
                String currentTitle = songCursor.getString(songTitle);
                String currentArtist = songCursor.getString(songArtist);
                arrayList.add(currentTitle + "\n" + currentArtist);
                list.add(thisId);
                // nyt
//                listSongs.add(MediaPlayer.create(getApplicationContext(), songUri));
            } while (songCursor.moveToNext());
        }
    }

    public void doStuff() {
//        listView = (ListView) findViewById(R.id.listView);
//        arrayList = new ArrayList<>();
//        getMusic();
        final ArrayList<File> audioSongs = readOnlyAudioSongs(Environment.getExternalStorageDirectory());
        itemsAll = new String[audioSongs.size()];
        for (int songCounter = 0; songCounter < audioSongs.size(); songCounter++) {
            itemsAll[songCounter] = audioSongs.get(songCounter).getName();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Song_List.this, android.R.layout.simple_list_item_1, itemsAll);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String songName = listView.getItemAtPosition(i).toString();
                Intent intent = new Intent(Song_List.this, MainActivity.class);
                intent.putExtra("song", audioSongs);
                intent.putExtra("name", songName);
                intent.putExtra("position", i);
                startActivity(intent);
            }
        });

//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
//        listView.setAdapter(adapter);
//        int songNameAndArtist = 0;
//        final String[] nameAndArtist = new String[arrayList.size()];
//        for (final String value : arrayList) {
//            nameAndArtist[songNameAndArtist++] = value;
//        }
//        int SongID = 0;
//        final long[] songs = new long[list.size()];
//        for (final Long value : list) {
//            songs[SongID++] = value;
//        }
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(Song_List.this, MainActivity.class);
//                intent.putExtra("SongName", nameAndArtist);
//                intent.putExtra("Song", songs);
//                startActivity(intent);
//                if (mediaPlayer.isPlaying()) {
//                    mediaPlayer.reset();
//                }
//                Uri contentUri = ContentUris.withAppendedId(
//                        android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, songs[i]);
//                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                try {
//                    mediaPlayer.setDataSource(getApplicationContext(), contentUri);
//                    mediaPlayer.prepare();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                    @Override
//                    public void onPrepared(MediaPlayer mp) {
//                        mediaPlayer.start();
//                    }
//                });
//                ///////////////
////                Intent intent = new Intent(Song_List.this, MainActivity.class);
////                startActivity(intent);
////                listSongs.get(i).setAudioStreamType(AudioManager.STREAM_MUSIC);
////                                    listSongs.get(i).setDataSource(getApplicationContext(), contentUri);
////                    listSongs.get(i).prepare();
////                listSongs.get(i).start();
////                open music player to play desired song
//            }
//        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(Song_List.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                        doStuff();
                    }
                } else {
                    Toast.makeText(this, "No permission granted", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
        }
    }

}
