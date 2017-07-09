package com.example.daniel.mytappergame_android;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public enum gameState {
        beforeGame, duringGame, afterGame
    }
    public static Integer score = 0;
    public static Integer time = 60;
    gameState currentState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Code to be run after the scene is loaded


        gameSetup();
        uiSetup();



        Button button = (Button) findViewById(R.id.tapMeButton);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                addScore();
            }
        });



    }

    private void gameSetup() {
        score = 0;
        time = 60;
        currentState = gameState.beforeGame;
        TextView scoreLabel = (TextView) findViewById(R.id.scoreLabel);//Resets score label
        scoreLabel.setText("Score: " + score);
    }

    private void uiSetup() {
        if(currentState == gameState.beforeGame){
            final TextView directionsLabel = (TextView) findViewById(R.id.directionsLabel);
            final TextView timeLabel = (TextView) findViewById(R.id.timeLabel);
            final Button tapMeButton = (Button) findViewById(R.id.tapMeButton);
            final TextView scoreLabel = (TextView)findViewById(R.id.scoreLabel);
            final TextView tapToStartLabel = (TextView)findViewById(R.id.tapToStartLabel);

            directionsLabel.setVisibility(View.VISIBLE);
            timeLabel.setVisibility(View.INVISIBLE);
            tapMeButton.setVisibility(View.INVISIBLE);
            scoreLabel.setVisibility(View.INVISIBLE);
            tapToStartLabel.setVisibility(View.VISIBLE);

            RelativeLayout gameView = (RelativeLayout) findViewById(R.id.activity_main);
            gameView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){

                    directionsLabel.setVisibility(View.VISIBLE);
                    timeLabel.setVisibility(View.VISIBLE);
                    tapMeButton.setVisibility(View.VISIBLE);
                    scoreLabel.setVisibility(View.VISIBLE);
                    tapToStartLabel.setVisibility(View.INVISIBLE);
                    currentState = gameState.duringGame;
                    new CountDownTimer(60000, 1000){ //New Timer of duration 60 seconds, running every second(1000milliseconds)
                        public void onTick(long millisUntilFinished) {
                            TextView timeLabel = (TextView) findViewById(R.id.timeLabel);
                            timeLabel.setText("Time: " + millisUntilFinished / 1000);
                            time -= 1;
                        }

                        public void onFinish() {
                            TextView timeLabel = (TextView) findViewById(R.id.timeLabel);
                            timeLabel.setText("Timer Done!");
                            moveToView();
                        }
                    }.start();
                }
            });
        }
    }


    private void addScore() {
        if(time > 0){
            score += 1;
            System.out.print("Added one to the score");
            TextView scoreLabel = (TextView) findViewById(R.id.scoreLabel);
            scoreLabel.setText("Score: " + score);
        }
    }

    private void moveToView() {
        Intent intent = new Intent(this, GameOverActivity.class);
        startActivity(intent);

    }



}
