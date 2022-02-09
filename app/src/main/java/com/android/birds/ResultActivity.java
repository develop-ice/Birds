package com.android.birds;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView tvInfo = findViewById(R.id.tv_congratulations);
        TextView tvScore = findViewById(R.id.tv_score);
        TextView tvRecord = findViewById(R.id.tv_record);
        Button btnRetry = findViewById(R.id.btn_play_again);

        int score = getIntent().getIntExtra("SCORE", 0);
        tvScore.setText("Puntuación: " + score);

        SharedPreferences preferences = getSharedPreferences("score", MODE_PRIVATE);
        int highestScore = preferences.getInt("highestScore", 0);

        if (score >= 100) {
            tvInfo.setText("Felicidades, has ganado");
            preferences.edit().putInt("highestScore", score).apply();
            tvRecord.setText("Record: " + score);
        } else if (score >= highestScore) {
            tvInfo.setText("Lo siento, perdiste el juego");
            preferences.edit().putInt("highestScore", score).apply();
            tvRecord.setText("Record: " + score);
        } else {
            tvInfo.setText("Lo siento, perdiste el juego");
            tvRecord.setText("Record: " + highestScore);
        }

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivity.this, MainActivity.class));
                finish();
            }
        });

    }

}