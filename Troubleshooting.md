# MyTapperGame
This is the beginning of my work with android.
This file will document all of the various errors I encounter and my solutions to them.

Problem #1
========================================================================================
Error Log: Attempt to invoke virtual method 'android.view.Window$Callback android.view.Window.getCallback()' on a null object reference

Effect of error: App crashes during build

Problem: I attempted to create instances of the UI elements before the view was loaded. 

    public class MainActivity extends AppCompatActivity {
    
      TextView scoreLabel = (TextView) findViewById(R.id.textView2); //This is the problem
    
      Integer score = 0;
      Integer time = 60;
      Integer delay = 1000;
      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);
       
        
          scoreLabel.setText("Score: " + score);

          Button button = (Button) findViewById(R.id.button);
          button.setOnClickListener(new View.OnClickListener(){
              @Override
              public void onClick(View view){
                addScore();
              }
          });
     }


Code Solution:

    public class MainActivity extends AppCompatActivity {
    
        Integer score = 0;
        Integer time = 60;
        Integer delay = 1000;
    
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        
            //Code to be run after the scene is loaded
            TextView scoreLabel = (TextView) findViewById(R.id.textView2); //Notice how I moved this line after the "setContentView"
            scoreLabel.setText("Score: " + score);

            Button button = (Button) findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    addScore();
                }
            });
    }

By moving the TextView line to after the initialization of the view, the problem is solved. In the long run, I will need to find
a way of making values like these accesible to all functions without having to declare a new variable in every function.

Problem #2
=====================================================
Error Log: NONE

Effect of Problem: The timer would not decrease sequencially from 60 to 0 and instead would decrease erratically. 

Problem: I used a OnClickListener on the relative layout of the scene. So whenever the user would tap the screen, the countdown timer would be run again. This caused the the erratic decrease.

	private void uiSetup() { //Function used for configuring the view before the game starts

        final TextView directionsLabel = (TextView) findViewById(R.id.directionsLabel);
        final TextView timeLabel = (TextView) findViewById(R.id.timeLabel);
        final Button tapMeButton = (Button) findViewById(R.id.tapMeButton);
        final TextView scoreLabel = (TextView)findViewById(R.id.scoreLabel);
        final TextView tapToStartLabel = (TextView)findViewById(R.id.tapToStartLabel);
        
        //sets some of the UI elements to be invisible to the User
        directionsLabel.setVisibility(View.VISIBLE); 
        timeLabel.setVisibility(View.INVISIBLE);
        tapMeButton.setVisibility(View.INVISIBLE);
        scoreLabel.setVisibility(View.INVISIBLE);
        tapToStartLabel.setVisibility(View.VISIBLE);
        tapToStartLabel.setEnabled(true);

        RelativeLayout gameView = (RelativeLayout) findViewById(R.id.activity_main);
        gameView.setOnClickListener(new View.OnClickListener(){ //Notice how the CountDownTimer function is inside OnClick()
            @Override
            public void onClick(View view){

                directionsLabel.setVisibility(View.VISIBLE);
                timeLabel.setVisibility(View.VISIBLE);
                tapMeButton.setVisibility(View.VISIBLE);
                scoreLabel.setVisibility(View.VISIBLE);
                tapToStartLabel.setVisibility(View.INVISIBLE);
                currentState = gameState.duringGame;
                gameDidStart = true;
                
                new CountDownTimer(60000, 1000){ //The Problem
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
        
Code Solution: 

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


        uiSetup(); // Here I run the function shown above
        
        RelativeLayout gameView = (RelativeLayout) findViewById(R.id.activity_main);
        tapToStartLabel.setOnClickListener(new View.OnClickListener(){ //Instead of using RelativeLayout(gameView) I decide to set the OnClick Listener on the tapToStartLabel 
            @Override
            public void onClick(View view) {
                directionsLabel.setVisibility(View.VISIBLE);
                timeLabel.setVisibility(View.VISIBLE);
                tapMeButton.setVisibility(View.VISIBLE);
                scoreLabel.setVisibility(View.VISIBLE);
                tapToStartLabel.setVisibility(View.INVISIBLE); //Hides label from user's view
                tapToStartLabel.setEnabled(false); //Disables User Interaction

                new CountDownTimer(60000, 1000){ //I moved the CountdownTimer function into onCreate() so it is run when the tap to start label is clicked
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
            

By using the tapToStartLabel instead of the RelativeLayout, and disabling the label after the User tapped the screen, the timer now decreases sequencially. I will need to pay more attention to small problems like these when I am testing the app, so that I do not push issue-ridden files to the master branch.

