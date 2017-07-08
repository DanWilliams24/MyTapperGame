# MyTapperGame
This is the beginning of my work with android.
This file will document all of the various errors I encounter and my solutions to them.

 ERROR #1
========================================================================================
Error: Attempt to invoke virtual method 'android.view.Window$Callback android.view.Window.getCallback()' on a null object reference

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



