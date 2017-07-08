package com.example.daniel.mytappergame_android;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    Integer score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView scoreLabel = (TextView) findViewById(R.id.textView2);
        scoreLabel.setText("Score: " + score);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                addScore();
            }
        });
    }

    private void addScore() {
        score += 1;
        System.out.print("Added one to the score");
        TextView scoreLabel = (TextView) findViewById(R.id.textView2);
        scoreLabel.setText("Score: " + score);
    }
}
