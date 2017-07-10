package com.example.daniel.mytappergame_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Integer tapsPerSecond = MainActivity.score / 60;

        TextView scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        scoreLabel.setText("Score: " + MainActivity.score);

        TextView tapsLabel = (TextView) findViewById(R.id.tapsPerSecondLabel);
        tapsLabel.setText(tapsPerSecond + " taps/sec");

        Button mainMenuButton = (Button) findViewById(R.id.mainMenuButton);
        mainMenuButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                backToMain();
            }
        });

        Button restartButton = (Button)findViewById(R.id.restartButton);
        restartButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                restartGame();
            }
        });


    }

    private void restartGame(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void backToMain(){
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }




}
