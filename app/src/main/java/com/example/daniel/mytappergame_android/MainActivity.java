package com.example.daniel.mytappergame_android;

import android.net.wifi.p2p.WifiP2pManager;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    Integer score = 0;
    Integer time = 60;
    Integer delay = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Code to be run after the scene is loaded
        TextView scoreLabel = (TextView) findViewById(R.id.textView2);//Resets score label
        scoreLabel.setText("Score: " + score);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                addScore();
            }
        });

        new CountDownTimer(60000, 1000){ //New Timer of duration 60 seconds, running every second(1000milliseconds)
            public void onTick(long millisUntilFinished) {
                TextView timeLabel = (TextView) findViewById(R.id.timeLabel);
                timeLabel.setText("Time: " + millisUntilFinished / 1000);
                time -= 1;
            }

            public void onFinish() {
                TextView timeLabel = (TextView) findViewById(R.id.timeLabel);
                timeLabel.setText("Timer Done!");
            }
        }.start();

    }

    private void addScore() {
        if(time > 0){
            score += 1;
            System.out.print("Added one to the score");
            TextView scoreLabel = (TextView) findViewById(R.id.textView2);
            scoreLabel.setText("Score: " + score);
        }
    }


}
