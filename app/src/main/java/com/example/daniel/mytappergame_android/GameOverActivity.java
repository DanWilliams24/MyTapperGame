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

        TextView scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        scoreLabel.setText("Score: " + MainActivity.score);

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




}
