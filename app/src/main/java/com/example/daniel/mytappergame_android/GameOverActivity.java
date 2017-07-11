package com.example.daniel.mytappergame_android;

import android.content.Intent;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Integer tapsPerSecond = MainActivity.score / 60;


        TextView scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        scoreLabel.setText("Score: " + MainActivity.score);

        TextView tapsLabel = (TextView) findViewById(R.id.tapsPerSecondLabel);
        tapsLabel.setText(tapsPerSecond + " taps/sec");

        if(tapsPerSecond > 2) {
            Toast speedDemon = Toast.makeText(this, "This is an Achievement, finish this feature!",Toast.LENGTH_LONG);
            speedDemon.show();
        }

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
