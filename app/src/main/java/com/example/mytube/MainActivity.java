package com.example.mytube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    Button queue_Button, songButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onstart();
    }

    public void onstart() {
        queue_Button = findViewById(R.id.Sang_Liste_Knap);
        songButton = findViewById(R.id.Mine_Sange_knap);
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



    public void onBackPressed() {
        setContentView(R.layout.activity_main);
        onstart();
    }
}
