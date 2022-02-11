package com.android.birds;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {
    // UI
    private ImageView player, coin1, coin2, enemy1, enemy2, enemy3, heart1, heart2, heart3;
    private TextView tvInfo, tvCoinCount;
    private ConstraintLayout container;
    // Touch
    private boolean touchControl = false;
    private boolean beginControl = false;
    // Run
    private Runnable runnable, runnableTmp;
    private Handler handler, handlerTmp;
    // Positions
    private int playerX, enemy1X, enemy2X, enemy3X, coin1X, coin2X;
    private int playerY, enemy1Y, enemy2Y, enemy3Y, coin1Y, coin2Y;
    // Screen dimens
    private int screenWidth, screenHeight;
    // Heart remaining
    private int heart = 3;
    // Score
    private int score = 0;
    // Sound
    private SoundPool soundPool;
    private int soundCoin, soundLose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Sound
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        soundCoin = soundPool.load(this, R.raw.coinsplash, 0);
        soundLose = soundPool.load(this, R.raw.ouch, 1);

        initViews();

        setupContainer();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupContainer() {
        container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                tvInfo.setVisibility(View.GONE);

                if (!beginControl) {

                    beginControl = true;

                    screenWidth = container.getWidth();
                    screenHeight = container.getHeight();

                    playerX = (int) player.getX();
                    playerY = (int) player.getY();

                    handler = new Handler();
                    runnable = new Runnable() {
                        @Override
                        public void run() {

                            movePlayer();

                            moveEnemies();

                            collisionControl();

                        }
                    };
                    handler.post(runnable);

                } else {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        touchControl = true;
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        touchControl = false;
                    }
                }

                return true;
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void collisionControl() {
        /**
         * Enemy 1
         */
        int centerEnemy1X = enemy1X + enemy1.getWidth() / 2;
        int centerEnemy1Y = enemy1Y + enemy1.getHeight() / 2;

        if (centerEnemy1X >= playerX
                && centerEnemy1X <= (playerX + player.getWidth())
                && centerEnemy1Y >= playerY
                && centerEnemy1Y <= (playerY + player.getHeight())) {

            soundPool.play(soundLose, 1, 1, 1, 0, 1);
            enemy1X = screenWidth + 200;
            heart--;
        }
        /**
         * Enemy 2
         */
        int centerEnemy2X = enemy2X + enemy2.getWidth() / 2;
        int centerEnemy2Y = enemy2Y + enemy2.getHeight() / 2;

        if (centerEnemy2X >= playerX
                && centerEnemy2X <= (playerX + player.getWidth())
                && centerEnemy2Y >= playerY
                && centerEnemy2Y <= (playerY + player.getHeight())) {

            soundPool.play(soundLose, 1, 1, 1, 0, 1);
            enemy2X = screenWidth + 200;
            heart--;
        }
        /**
         * Enemy 3
         */
        int centerEnemy3X = enemy3X + enemy3.getWidth() / 2;
        int centerEnemy3Y = enemy3Y + enemy3.getHeight() / 2;

        if (centerEnemy3X >= playerX
                && centerEnemy3X <= (playerX + player.getWidth())
                && centerEnemy3Y >= playerY
                && centerEnemy3Y <= (playerY + player.getHeight())) {

            soundPool.play(soundLose, 1, 1, 1, 0, 1);
            enemy3X = screenWidth + 200;
            heart--;
        }
        /**
         * Coin 1
         */
        int centerCoin1X = coin1X + coin1.getWidth() / 2;
        int centerCoin1Y = coin1Y + coin1.getHeight() / 2;

        if (centerCoin1X >= playerX
                && centerCoin1X <= (playerX + player.getWidth())
                && centerCoin1Y >= playerY
                && centerCoin1Y <= (playerY + player.getHeight())) {

            soundPool.play(soundCoin, 1, 1, 0, 0, 1);
            coin1X = screenWidth + 200;
            score = score + 10;
            tvCoinCount.setText("" + score);
        }
        /**
         * Coin 2
         */
        int centerCoin2X = coin2X + coin2.getWidth() / 2;
        int centerCoin2Y = coin2Y + coin2.getHeight() / 2;

        if (centerCoin2X >= playerX
                && centerCoin2X <= (playerX + player.getWidth())
                && centerCoin2Y >= playerY
                && centerCoin2Y <= (playerY + player.getHeight())) {

            soundPool.play(soundCoin, 1, 1, 0, 0, 1);
            coin2X = screenWidth + 200;
            score = score + 10;
            tvCoinCount.setText("" + score);
        }

        /**
         * GAME OVER
         */
        if (heart > 0 && score < 100) { // LIFE
            if (heart == 2) {
                heart1.setImageResource(R.drawable.ic_favorite_gray);
            }
            if (heart == 1) {
                heart2.setImageResource(R.drawable.ic_favorite_gray);
            }
            handler.postDelayed(runnable, 20);

        } else if (score >= 100) { // WIN

            handler.removeCallbacks(runnable);
            container.setEnabled(false);
            tvInfo.setVisibility(View.VISIBLE);
            tvInfo.setText("Wiiiiii \nHas ganado el juego");

            enemy1.setVisibility(View.GONE);
            enemy2.setVisibility(View.GONE);
            enemy3.setVisibility(View.GONE);
            coin1.setVisibility(View.GONE);
            coin2.setVisibility(View.GONE);

            handlerTmp = new Handler();
            runnableTmp = new Runnable() {
                @Override
                public void run() {
                    playerX = playerX + (screenWidth / 300);
                    player.setX(playerX);
                    player.setY(screenHeight / 2f);
                    if (playerX <= screenWidth) {
                        handlerTmp.postDelayed(runnableTmp, 20);
                    } else {
                        handlerTmp.removeCallbacks(runnableTmp);
                        Intent intent = new Intent(GameActivity.this, ResultActivity.class);
                        intent.putExtra("SCORE", score);
                        startActivity(intent);
                        finish();
                    }
                }
            };
            handlerTmp.post(runnableTmp);

        } else if (heart == 0) { // LOST
            handler.removeCallbacks(runnable);
            heart3.setImageResource(R.drawable.ic_favorite_gray);
            Intent intent = new Intent(GameActivity.this, ResultActivity.class);
            intent.putExtra("SCORE", score);
            startActivity(intent);
            finish();
        }
    }

    private void moveEnemies() {

        enemy1.setVisibility(View.VISIBLE);
        enemy2.setVisibility(View.VISIBLE);
        enemy3.setVisibility(View.VISIBLE);
        coin1.setVisibility(View.VISIBLE);
        coin2.setVisibility(View.VISIBLE);

        /**
         * Enemy 1
         */
        enemy1X = enemy1X - (screenWidth / 150);

        if (enemy1X < 0) {
            enemy1X = screenWidth + 200;
            enemy1Y = (int) Math.floor(Math.random() * screenHeight);
        }

        if (enemy1Y <= 0) {
            enemy1Y = 0;
        }

        if (enemy1Y >= (screenHeight - enemy1.getHeight())) {
            enemy1Y = screenHeight - enemy1.getHeight();
        }

        enemy1.setX(enemy1X);
        enemy1.setY(enemy1Y);

        /**
         * Enemy 2
         */
        enemy2X = enemy2X - (screenWidth / 140);

        if (enemy2X < 0) {
            enemy2X = screenWidth + 200;
            enemy2Y = (int) Math.floor(Math.random() * screenHeight);
        }

        if (enemy2Y <= 0) {
            enemy2Y = 0;
        }

        if (enemy2Y >= (screenHeight - enemy2.getHeight())) {
            enemy2Y = screenHeight - enemy2.getHeight();
        }

        enemy2.setX(enemy2X);
        enemy2.setY(enemy2Y);

        /**
         * Enemy 3
         */
        enemy3X = enemy3X - (screenWidth / 130);

        if (enemy3X < 0) {
            enemy3X = screenWidth + 200;
            enemy3Y = (int) Math.floor(Math.random() * screenHeight);
        }

        if (enemy3Y <= 0) {
            enemy3Y = 0;
        }

        if (enemy3Y >= (screenHeight - enemy3.getHeight())) {
            enemy3Y = screenHeight - enemy3.getHeight();
        }

        enemy3.setX(enemy3X);
        enemy3.setY(enemy3Y);

        /**
         * Coin 1
         */
        coin1X = coin1X - (screenWidth / 120);

        if (coin1X < 0) {
            coin1X = screenWidth + 200;
            coin1Y = (int) Math.floor(Math.random() * screenHeight);
        }

        if (coin1Y <= 0) {
            coin1Y = 0;
        }

        if (coin1Y >= (screenHeight - coin1.getHeight())) {
            coin1Y = screenHeight - coin1.getHeight();
        }

        coin1.setX(coin1X);
        coin1.setY(coin1Y);

        /**
         * Coin 2
         */
        coin2X = coin2X - (screenWidth / 110);

        if (coin2X < 0) {
            coin2X = screenWidth + 200;
            coin2Y = (int) Math.floor(Math.random() * screenHeight);
        }

        if (coin2Y <= 0) {
            coin2Y = 0;
        }

        if (coin2Y >= (screenHeight - coin2.getHeight())) {
            coin2Y = screenHeight - coin2.getHeight();
        }

        coin2.setX(coin2X);
        coin2.setY(coin2Y);

    }


    private void movePlayer() {

        if (touchControl) {
            playerY = playerY - (screenHeight / 50);
        } else {
            playerY = playerY + (screenHeight / 50);
        }

        if (playerY <= 0) {
            playerY = 0;
        }

        if (playerY >= (screenHeight - player.getHeight())) {
            playerY = screenHeight - player.getHeight();
        }

        player.setY(playerY);
    }

    private void initViews() {
        player = findViewById(R.id.img_player);
        coin1 = findViewById(R.id.img_coin_1);
        coin2 = findViewById(R.id.img_coin_2);
        enemy1 = findViewById(R.id.img_enemy_1);
        enemy2 = findViewById(R.id.img_enemy_2);
        enemy3 = findViewById(R.id.img_enemy_3);
        heart1 = findViewById(R.id.imgHeart1);
        heart2 = findViewById(R.id.imgHeart2);
        heart3 = findViewById(R.id.imgHeart3);
        tvInfo = findViewById(R.id.tv_info);
        tvCoinCount = findViewById(R.id.tv_coin);
        container = findViewById(R.id.container);
    }
}
