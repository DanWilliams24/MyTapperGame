package com.example.daniel.mytappergame_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class GameOverActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;
    private Button restartButton;

    Integer tapsPerSecond = MainActivity.score / 60;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);




        TextView scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        scoreLabel.setText("Score: " + MainActivity.score);

        TextView tapsLabel = (TextView) findViewById(R.id.tapsPerSecondLabel);
        tapsLabel.setText(tapsPerSecond + " taps/sec");

        newHighScore();



        Button mainMenuButton = (Button) findViewById(R.id.mainMenuButton);
        mainMenuButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                backToMain();
            }
        });
        //Creating and loading Interstitial ad
        mInterstitialAd = createNewIntAd();
        loadIntAdd();



        restartButton = (Button)findViewById(R.id.restartButton);
        restartButton.setEnabled(true);
        restartButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showIntAdd();


            }
        });


    }
    private void newHighScore(){ // When scene loads, this func checks to see if a new highscore is set
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();

        if(tapsPerSecond > prefs.getInt("highestTapPerSecond", 0)){
            saveHighScoreData(tapsPerSecond);
            highscoreMessage();

        }
        if(prefs.getInt("highestTapPerSecond", 0) == 0) {
            saveHighScoreData(tapsPerSecond);

        }
        editor.apply();
    }


     private void highscoreMessage() {
        if(tapsPerSecond == 2) {

            Toast beginnerMessage = Toast.makeText(this, "You beat the high score, but I know you can go faster than that!", Toast.LENGTH_LONG);
            beginnerMessage.show();
        } else if(tapsPerSecond == 3) {

            Toast speederMessage = Toast.makeText(this, "You beat the high score! Wow your getting pretty good at this.", Toast.LENGTH_LONG);
            speederMessage.show();
        } else if(tapsPerSecond >= 5) {

            Toast speedDemonMessage = Toast.makeText(this, "You beat the high score! Slow down a bit,you don't want break your screen!", Toast.LENGTH_LONG);
            speedDemonMessage.show();
        }

        }




    private void saveHighScoreData(Integer newHighScore){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("highestTapPerSecond", newHighScore);
                editor.apply();


    }

    private void restartGame(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void backToMain(){
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

    private void showIntAdd() {
       if(mInterstitialAd != null && mInterstitialAd.isLoaded()) {
           mInterstitialAd.show();
       } else {
           restartGame();
       }
    }

    private void loadIntAdd() {
        restartButton = (Button)findViewById(R.id.restartButton);
        restartButton.setEnabled(false);
        AdRequest adRequest =  new AdRequest.Builder()
                .addTestDevice("ca-app-pub-3940256099942544/1033173712")
                .build();
        mInterstitialAd.loadAd(adRequest);
    }


    private InterstitialAd createNewIntAd(){
        InterstitialAd intAd = new InterstitialAd(this);
        // set the adUnitId (defined in values/strings.xml)
        intAd.setAdUnitId(getString(R.string.ad_id_interstitial));
        intAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                restartButton = (Button)findViewById(R.id.restartButton);
                restartButton.setEnabled(true);

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                restartButton = (Button)findViewById(R.id.restartButton);
                restartButton.setEnabled(true);

            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                //Restart the game
                restartGame();
            }
        });
        return intAd;

    }


}
