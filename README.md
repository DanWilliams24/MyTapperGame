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

Problem #3
==========================================================
Error: Gradle build finished with 2 error(s) in 2s 857ms

Effect of Problem: App would crash on startup,and app would crash when the GameOverActivity was loaded.

Issue(s):

1. Incorrectly declared method.

		private showBannerAd() { //This is the problem
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("TEST_EMULATOR")
                .build();
        mBannerAd.loadAd(adRequest);
		}
	Code Solution to Issue 1:
	
	 	private void showBannerAd() { //Forgot to add "void"
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("TEST_EMULATOR")
                .build();
        mBannerAd.loadAd(adRequest);
    	}
	Always double check code for any errors identified by Android Studio. Not doing so can lead to needless troubleshooting.

2. Could not find the Device ID for testing the ads on the Emulator.

 		private void loadIntAdd() {
        restartButton = (Button)findViewById(R.id.restartButton);
        restartButton.setEnabled(false);
        AdRequest adRequest =  new AdRequest.Builder()
                .addTestDevice("Add Test Device ID here") //This is the Issue
                .build();
        mInterstitialAd.loadAd(adRequest);
    	}
	
		private void showBannerAd() { 
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("Add Test Device ID here")
                .build();
        mBannerAd.loadAd(adRequest);
    	}
	
	Code Solution for Issue 2:
	
 		private void loadIntAdd() {
        restartButton = (Button)findViewById(R.id.restartButton);
        restartButton.setEnabled(false);
        AdRequest adRequest =  new AdRequest.Builder()
                .addTestDevice("ca-app-pub-3940256099942544/1033173712") //This was the test device Id provided by Google in their tutorials
                .build();
        mInterstitialAd.loadAd(adRequest);
    	}
    
    	private void showBannerAd() { 
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("ca-app-pub-3940256099942544/6300978111") //Another test device Id provided by google
                .build();
        mBannerAd.loadAd(adRequest);
    	}
When submitting my app to a store, I will have to delete these test ads. As for issue 2:
	
These are the test device Id's that can be used to TEST ads

Banner :  ca-app-pub-3940256099942544/6300978111

Interstitial: ca-app-pub-3940256099942544/1033173712

Found here:
https://stackoverflow.com/questions/24268888/how-to-test-admob-in-real-device-if-without-ad-unit-id


