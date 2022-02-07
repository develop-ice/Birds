package com.android.birds;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    // UI
    private ImageView player, coin, enemy1, enemy2, enemy3, volume;
    private Button btnPlay;
    // Audio
    private MediaPlayer mediaPlayer;
    private boolean status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        startAnimation();

        startAudio();

        onClickVolume();

        onClickPlay();

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Audio setup
        mediaPlayer = MediaPlayer.create(this, R.raw.audio);
        mediaPlayer.start();

    }

    private void onClickVolume() {
        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!status) {
                    mediaPlayer.setVolume(0, 0);
                    volume.setImageResource(R.drawable.ic_volume_off);
                    status = true;
                } else {
                    mediaPlayer.setVolume(1, 1);
                    volume.setImageResource(R.drawable.ic_volume_up);
                    status = false;
                }
            }
        });
    }

    private void onClickPlay() {
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.reset();
                volume.setImageResource(R.drawable.ic_volume_up);
                startActivity(new Intent(MainActivity.this, GameActivity.class));
            }
        });
    }

    private void startAudio() {

    }

    private void startAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_anim);

        player.setAnimation(animation);
        coin.setAnimation(animation);
        enemy1.setAnimation(animation);
        enemy2.setAnimation(animation);
        enemy3.setAnimation(animation);
    }

    private void initViews() {
        player = findViewById(R.id.img1);
        coin = findViewById(R.id.img2);
        enemy1 = findViewById(R.id.img3);
        enemy2 = findViewById(R.id.img4);
        enemy3 = findViewById(R.id.img5);
        volume = findViewById(R.id.volume);
        btnPlay = findViewById(R.id.btn_play);
    }

}
