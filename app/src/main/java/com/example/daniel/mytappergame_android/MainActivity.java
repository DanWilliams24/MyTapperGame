package com.example.daniel.mytappergame_android;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {

    public enum gameState {
        beforeGame, duringGame, afterGame
    }
    public static Integer score = 0;
    public static Integer time = 60;
    gameState currentState;
    boolean gameDidStart;
    private AdView mBannerAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Code to be run after the scene is loaded
        gameDidStart = false;
        final TextView directionsLabel = (TextView) findViewById(R.id.directionsLabel);
        final TextView timeLabel = (TextView) findViewById(R.id.timeLabel);
        final Button tapMeButton = (Button) findViewById(R.id.tapMeButton);
        final TextView scoreLabel = (TextView)findViewById(R.id.scoreLabel);
        final TextView tapToStartLabel = (TextView)findViewById(R.id.tapToStartLabel);
        gameSetup();
        mBannerAd = (AdView)findViewById(R.id.banner_adView);
        showBannerAd();

        uiSetup();
        RelativeLayout gameView = (RelativeLayout) findViewById(R.id.activity_main);
        tapToStartLabel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                directionsLabel.setVisibility(View.VISIBLE);
                timeLabel.setVisibility(View.VISIBLE);
                tapMeButton.setVisibility(View.VISIBLE);
                scoreLabel.setVisibility(View.VISIBLE);
                tapToStartLabel.setVisibility(View.INVISIBLE);
                tapToStartLabel.setEnabled(false);

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



        Button button = (Button) findViewById(R.id.tapMeButton);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                addScore();
            }
        });



    }
    private void showBannerAd() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("TEST_EMULATOR")
                .build();
        mBannerAd.loadAd(adRequest);
    }

    private void gameSetup() {
        score = 0;
        time = 60;
        currentState = gameState.beforeGame;
        TextView scoreLabel = (TextView) findViewById(R.id.scoreLabel);//Resets score label
        scoreLabel.setText("Score: " + score);
    }

    private void uiSetup() {

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
        tapToStartLabel.setEnabled(true);

        RelativeLayout gameView = (RelativeLayout) findViewById(R.id.activity_main);
        /*
        gameView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                directionsLabel.setVisibility(View.VISIBLE);
                timeLabel.setVisibility(View.VISIBLE);
                tapMeButton.setVisibility(View.VISIBLE);
                scoreLabel.setVisibility(View.VISIBLE);
                tapToStartLabel.setVisibility(View.INVISIBLE);
                currentState = gameState.duringGame;
                gameDidStart = true;
                }
            });
            */

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
