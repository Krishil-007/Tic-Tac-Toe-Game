package com.example.Tic_Tac_Toe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView; // imports all necessary imports
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    /* 0 = x
       1 = o */
    MediaPlayer player;

    int t=0;
    boolean gameActive = true;/* <-- describe that the game is active or not */
    int activePlayer = 0; //<-- it will decide wheather to put o or x
    int[] gameState ={ 2, 2, 2, 2, 2, 2, 2, 2, 2};/* <-- 9 cell data will be stored
     i.e it will stored that weather the box has o or x or it is blank
     also 2 means blank box*/

    int[][] winPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};/* winning positions
     representing the possible winning positions of the game
     It is a 2D array*/
//    Button playAgain=(Button)findViewById(R.id.button_playAgain);
    public void playerTap(View view) // <-- this function will be called if we tap in any of the box
    {
//        playAgain.setVisibility(View.INVISIBLE);
        ImageView img = (ImageView)view;/* <-- the img is a image view  */
        int tappedImage = Integer.parseInt(img.getTag().toString());/* <-- getTag() is used to take the tag
          this tag is converted into string then this string is parse into integer */
        if (!gameActive){
            gameReset(view);/* <-- This function will reset the game */
        }
        if(gameState[tappedImage] == 2 && gameActive) {
            gameState[tappedImage] = activePlayer;
            img.setTranslationY(-1000f);
            /* Below if else is for changing the active player and updating the status*/
            /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
            if (activePlayer == 0) {
                img.setImageResource(R.drawable.x);/* <-- Sets image X */
                activePlayer = 1;
                TextView status = findViewById(R.id.status);/* <-- this will find the status */
                status.setText("O's Turn");/* <-- this will update status */
            }
            else {
                img.setImageResource(R.drawable.o);/* <-- sets image of o */
                activePlayer = 0;
                TextView status = findViewById(R.id.status);
                status.setText("X's Turn");
            }
            /*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/
            img.animate().translationYBy(1000f).setDuration(300);/*this will provide animatation to the image.
             duration(300) means it will come in 300 ms */
        }

        String winnerstr;
        /* below code is to check the winner */
        /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
        for (int[] winPosition: winPositions)/* <-- This for loop will check weather any
         player is having winning position */
        {
            if(gameState[winPosition[0]] == gameState[winPosition[1]] &&
                    gameState[winPosition[1]] == gameState[winPosition[2]] &&
                    gameState[winPosition[0]]!= 2/* <-- this is written because
                     the winning positions must not be blank */)
            {
                gameActive=false;
                /* below if else will check that who has won */
                /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
                if(gameState[winPosition[0]]==0)
                {
                    winnerstr="X has Won !!!";
                    Toast.makeText(this, ""+winnerstr,Toast.LENGTH_SHORT).show();
//                    playAgain.setText("Play Again");
//                    playAgain.setVisibility(View.VISIBLE);
                }
                else /*if(gameState[winPosition[0]]==1)*/{
                    winnerstr="O has Won !!!";
                    Toast.makeText(this,""+winnerstr,Toast.LENGTH_SHORT).show();
//                    playAgain.setText("Play Again");
//                    playAgain.setVisibility(View.VISIBLE);
                }
                /*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/
                TextView status = findViewById(R.id.status);/* This will print who is the winner */
                status.setText(winnerstr);

            }
        }
        boolean empty=false;
        for(t=0;t<9;t++){
            if(gameState[t]==2){
                empty=true;
                break;
            }
        }
        if(!empty && gameActive)
        {
            winnerstr="Game Tied!";
            TextView status=findViewById(R.id.status);
            status.setText(winnerstr);
            Toast.makeText(this,"Game Tied!",Toast.LENGTH_LONG).show();
            gameActive=false;
//            playAgain.setText("Play Again");
//            playAgain.setVisibility(View.VISIBLE);
        }

        /*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/

    }
    public void gameReset(View view)/* <-- This will reset the game */
    {
    gameActive = true;
    activePlayer=0;
            for(int i=0;i<gameState.length;i++)/* <-- this will reset the game states to 2 */
            {
                gameState[i]=2;
            }
        /* below code will remove all the images */
        /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
        ((ImageView)findViewById(R.id.imageView0)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView1)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView2)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView3)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView4)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView5)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView6)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView7)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView8)).setImageResource(0);
        /*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/
        TextView status = findViewById(R.id.status);
        status.setText("X's Turn");
        playerTap(view);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Tic Tac Toe");
        player = MediaPlayer.create(this, R.raw.background_music);
        player.start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Reset Game");
//        menu.add("Change Mode");
        menu.add("Settings");
//        menu.add("Contact Developer");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals("Reset Game"))
        {
            gameActive=false;
            activePlayer=0;
            for(int i=0;i<gameState.length;i++)/* <-- this will reset the game states to 2 */
            {
                gameState[i]=2;
            }
            /* below code will remove all the images */
            /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
            ((ImageView)findViewById(R.id.imageView0)).setImageResource(0);
            ((ImageView)findViewById(R.id.imageView1)).setImageResource(0);
            ((ImageView)findViewById(R.id.imageView2)).setImageResource(0);
            ((ImageView)findViewById(R.id.imageView3)).setImageResource(0);
            ((ImageView)findViewById(R.id.imageView4)).setImageResource(0);
            ((ImageView)findViewById(R.id.imageView5)).setImageResource(0);
            ((ImageView)findViewById(R.id.imageView6)).setImageResource(0);
            ((ImageView)findViewById(R.id.imageView7)).setImageResource(0);
            ((ImageView)findViewById(R.id.imageView8)).setImageResource(0);
            /*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/
            TextView status = findViewById(R.id.status);
            status.setText("X's Turn");
        }
        else if(item.getTitle().equals("Change Mode"))
        {

        }
        else if(item.getTitle().equals("Settings")){
            Intent intent=new Intent(MainActivity.this,Settings.class);
            startActivity(intent);
        }
        else if(item.getTitle().equals("Contact Developer")){

        }
        else {

        }
        return super.onOptionsItemSelected(item);
    }
}
