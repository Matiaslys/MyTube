package com.example.mytube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
//    private String[] itemsAll;
//    private static final int MY_PERMISSION_REQUEST = 1;
//    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song__list2);
//        listView = findViewById(R.id.songList);
//        appExternalStoragePermission();

//        if (ContextCompat.checkSelfPermission(Song_List.this,
//                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(Song_List.this,
//                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                ActivityCompat.requestPermissions(Song_List.this,
//                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
//            } else {
//                ActivityCompat.requestPermissions(Song_List.this,
//                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
//            }
//            doStuff();
//        }
    }

//    public void appExternalStoragePermission() {
//        Dexter.withActivity(this)
//                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//                .withListener(new PermissionListener() {
//                    @Override
//                    public void onPermissionGranted(PermissionGrantedResponse response) {
//                        doStuff();
//                    }
//
//                    @Override
//                    public void onPermissionDenied(PermissionDeniedResponse response) {
//
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
//                        token.continuePermissionRequest();
//                    }
//                }).check();
//    }
//
//    public ArrayList<File> readOnlyAudioSongs(File file) {
//        ArrayList<File> arrayList = new ArrayList<>();
//        File[] allFiles = file.listFiles();
//        for (File individualFile : allFiles) {
//            if (individualFile.isDirectory() && !individualFile.isHidden()) {
//                arrayList.addAll(readOnlyAudioSongs(individualFile));
//            } else {
//                if (individualFile.getName().endsWith(".mp3") ||
//                        individualFile.getName().endsWith(".aac") ||
//                        individualFile.getName().endsWith(".wav") ||
//                        individualFile.getName().endsWith(".wma")) {
//                    arrayList.add(individualFile);
//                }
//            }
//        }
//        return arrayList;
//    }
//
//    public void doStuff() {
//        final ArrayList<File> audioSongs = readOnlyAudioSongs(Environment.getExternalStorageDirectory());
//        itemsAll = new String[audioSongs.size()];
//        for (int songCounter = 0; songCounter < audioSongs.size(); songCounter++) {
//            itemsAll[songCounter] = audioSongs.get(songCounter).getName();
//        }
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Song_List.this, android.R.layout.simple_list_item_1, itemsAll);
//        listView.setAdapter(arrayAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String songName = listView.getItemAtPosition(i).toString();
//                Intent intent = new Intent(Song_List.this, MainActivity.class);
//                intent.putExtra("song", audioSongs);
//                intent.putExtra("name", songName);
//                intent.putExtra("position", i);
//                startActivity(intent);
//            }
//        });
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSION_REQUEST: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if (ContextCompat.checkSelfPermission(Song_List.this,
//                            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                        Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
//                        doStuff();
//                    }
//                } else {
//                    Toast.makeText(this, "No permission granted", Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//                return;
//            }
//        }
//    }
}
