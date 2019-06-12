package com.zhangteng.ijkplayer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.kk.taurus.ui.VideoActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VideoActivity.startVideoActivity(MainActivity.this, "", new int[2], new int[2]);
    }
}
