package com.example.daniel.mytappergame_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button playButton = (Button)findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startGame();
            }
        });


    }

    private void startGame() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
