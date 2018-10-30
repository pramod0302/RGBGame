package com.example.android.rgbgame;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int test;
    TextView textView;
    LinearLayout linearLayout;
    String inpColor = "#220044";
    AlertDialog.Builder builder;
    String score, highScoreDisplay;
    final String HIGHSCORE = "highscore";
    final String MODEEASY = "modeeasy";
    final String MODEMORDERATE = "modemorderate";
    final String MODEHARD = "modehard";
    final String CURRENTGAME = "currentgame";
    Random rand = new Random();
    Integer randomValueR, randomValueG, randomValueB, targetValueRP, targetValueGP, targetValueBP;
    SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Start as easy game */
//        SharedData.currentGame = 0;

        resetView();

        populateView();

        builder = new AlertDialog.Builder(this);
    }

    public void resetView() {
        mPrefs = getSharedPreferences(HIGHSCORE, 0);
        SharedData.highScore3 = mPrefs.getInt(MODEEASY, 0);
        SharedData.highScore6 = mPrefs.getInt(MODEMORDERATE, 0);
        SharedData.highScore9 = mPrefs.getInt(MODEHARD, 0);
        SharedData.currentGame = mPrefs.getInt(CURRENTGAME, 3);

//        SharedPreferences.Editor mEditor = mPrefs.edit();
//        mEditor.putInt(MODEEASY, ++mInteger).apply();

        if (SharedData.currentGame == 6) {
            setContentView(R.layout.activity_main_6);
            highScoreDisplay = "High Score: " + SharedData.highScore6.toString();
        } else if (SharedData.currentGame == 9) {
            setContentView(R.layout.activity_main_9);
            highScoreDisplay = "High Score: " + SharedData.highScore9.toString();
        } else {
            setContentView(R.layout.activity_main_3);
            highScoreDisplay = "High Score: " + SharedData.highScore3.toString();
        }

        textView = (TextView) findViewById(R.id.id_high_score);
        textView.setText(highScoreDisplay);
    }

    /* This function is used to populate the current level of game*/
    public void populateView() {
        /*Get the current position where the correct RGB value is to be stored. This will give
        * the number from 1-3, 1-6 or 1-9 based on the level of the game */
        SharedData.currentJackpot = rand.nextInt(SharedData.currentGame) + 1;
        SharedData.currentPosition = 0;

        linearLayout = (LinearLayout) findViewById(R.id.id_box_1);
        linearLayout.setBackgroundColor(Color.parseColor(getRandomColor()));
        linearLayout = (LinearLayout) findViewById(R.id.id_box_2);
        linearLayout.setBackgroundColor(Color.parseColor(getRandomColor()));
        linearLayout = (LinearLayout) findViewById(R.id.id_box_3);
        linearLayout.setBackgroundColor(Color.parseColor(getRandomColor()));

        if (SharedData.currentGame > 3) {
            linearLayout = (LinearLayout) findViewById(R.id.id_box_4);
            linearLayout.setBackgroundColor(Color.parseColor(getRandomColor()));
            linearLayout = (LinearLayout) findViewById(R.id.id_box_5);
            linearLayout.setBackgroundColor(Color.parseColor(getRandomColor()));
            linearLayout = (LinearLayout) findViewById(R.id.id_box_6);
            linearLayout.setBackgroundColor(Color.parseColor(getRandomColor()));
        }
        if (SharedData.currentGame > 6) {
            linearLayout = (LinearLayout) findViewById(R.id.id_box_7);
            linearLayout.setBackgroundColor(Color.parseColor(getRandomColor()));
            linearLayout = (LinearLayout) findViewById(R.id.id_box_8);
            linearLayout.setBackgroundColor(Color.parseColor(getRandomColor()));
            linearLayout = (LinearLayout) findViewById(R.id.id_box_9);
            linearLayout.setBackgroundColor(Color.parseColor(getRandomColor()));
        }
        updatePercent(targetValueRP, targetValueGP, targetValueBP);
    }

    private String getRandomColor() {
        float tar;
        /*Increment the current position */
        SharedData.currentPosition++;

        randomValueR = rand.nextInt(256);
        randomValueG = rand.nextInt(256);
        randomValueB = rand.nextInt(256);

        /* If the current position matches the target value, store those value internally*/
        if(SharedData.currentPosition == SharedData.currentJackpot) {
            tar = (((float)randomValueR/(float)255) * (float)100);
            targetValueRP = Math.round(tar);
            tar = (((float)randomValueG/(float)255) * (float)100);
            targetValueGP = Math.round(tar);
            tar = (((float)randomValueB/(float)255) * (float)100);
            targetValueBP = Math.round(tar);
        }


        String rVR = String.format("%02X", randomValueR);
        String rVG = String.format("%02X", randomValueG);
        String rVB = String.format("%02X", randomValueB);

//        inpColor = "#" + Integer.toHexString(randomValueR) + Integer.toHexString(randomValueG) +Integer.toHexString(randomValueB);
        inpColor = "#" + rVR + rVG + rVB;
        return inpColor;
    }

    private void updatePercent(Integer r, Integer g, Integer b) {
        String str;
        textView = (TextView) findViewById(R.id.id_r_percent);
        str = "R = " + r.toString() + "%";
        textView.setText(str);
        textView = (TextView) findViewById(R.id.id_g_percent);
        str = "G = " + g.toString() + "%";
        textView.setText(str);
        textView = (TextView) findViewById(R.id.id_b_percent);
        str = "B = " + b.toString() + "%";
        textView.setText(str);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences mPrefs = getSharedPreferences(HIGHSCORE, 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        switch (item.getItemId()) {
            case R.id.menu_easy:
                setContentView(R.layout.activity_main_3);
                SharedData.currentGame = 3;
                mEditor.putInt(CURRENTGAME, SharedData.currentGame).apply();
                break;
            case R.id.menu_moderate:
                setContentView(R.layout.activity_main_6);
                SharedData.currentGame = 6;
                mEditor.putInt(CURRENTGAME, SharedData.currentGame).apply();
                break;
            case R.id.menu_hard:
                setContentView(R.layout.activity_main_9);
                SharedData.currentGame = 9;
                mEditor.putInt(CURRENTGAME, SharedData.currentGame).apply();
                break;
            default:
                break;
        }
        // Refresh the view
        resetView();
        populateView();
        return super.onOptionsItemSelected(item);
    }

    public void onBoxClick(View view) {
        SharedData.currentOptionSelected = 0;
        switch (view.getId()) {
            case R.id.id_box_1:
                SharedData.currentOptionSelected = 1;
                break;
            case R.id.id_box_2:
                SharedData.currentOptionSelected = 2;
                break;
            case R.id.id_box_3:
                SharedData.currentOptionSelected = 3;
                break;
            case R.id.id_box_4:
                SharedData.currentOptionSelected = 4;
                break;
            case R.id.id_box_5:
                SharedData.currentOptionSelected = 5;
                break;
            case R.id.id_box_6:
                SharedData.currentOptionSelected = 6;
                break;
            case R.id.id_box_7:
                SharedData.currentOptionSelected = 7;
                break;
            case R.id.id_box_8:
                SharedData.currentOptionSelected = 8;
                break;
            case R.id.id_box_9:
                SharedData.currentOptionSelected = 9;
                break;
            default:
                break;
        }
        if(checkResultAndUpdate()) {
            populateView();
        }
//        Toast.makeText(getApplicationContext(), "Clicked " + view.getId(), Toast.LENGTH_SHORT).show();
    }

    private boolean checkResultAndUpdate() {
        textView = (TextView) findViewById(R.id.id_current_score);
        if(SharedData.currentOptionSelected == SharedData.currentJackpot) {
            if(SharedData.currentGame == 3){
                SharedData.currentScore3++;
                score = "Score: " + SharedData.currentScore3.toString();
                textView.setText(score);
                if(SharedData.currentScore3 >= SharedData.highScore3) {
                    SharedData.highScore3 = SharedData.currentScore3;
                    SharedPreferences.Editor mEditor = mPrefs.edit();
                    mEditor.putInt(MODEEASY, SharedData.highScore3).apply();
                    textView = (TextView) findViewById(R.id.id_high_score);
                    score = "High Score: " + SharedData.highScore3.toString();
                    textView.setText(score);
                }
            } else if(SharedData.currentGame == 6) {
                SharedData.currentScore6++;
                score = "Score: " + SharedData.currentScore6.toString();
                textView.setText(score);
                if(SharedData.currentScore6 >= SharedData.highScore6) {
                    SharedData.highScore6 = SharedData.currentScore6;
                    SharedPreferences.Editor mEditor = mPrefs.edit();
                    mEditor.putInt(MODEMORDERATE, SharedData.highScore6).apply();
                    textView = (TextView) findViewById(R.id.id_high_score);
                    score = "High Score: " + SharedData.highScore6.toString();
                    textView.setText(score);
                }
            } else {
                SharedData.currentScore9++;
                score = "Score: " + SharedData.currentScore9.toString();
                textView.setText(score);
                if(SharedData.currentScore9 >= SharedData.highScore9) {
                    SharedData.highScore9 = SharedData.currentScore9;
                    SharedPreferences.Editor mEditor = mPrefs.edit();
                    mEditor.putInt(MODEHARD, SharedData.highScore9).apply();
                    textView = (TextView) findViewById(R.id.id_high_score);
                    score = "High Score: " + SharedData.highScore9.toString();
                    textView.setText(score);
                }
            }
            return true;
        } else {
            Integer curScoreTemp;
            if(SharedData.currentGame == 3){
                curScoreTemp = SharedData.currentScore3;
            } else if(SharedData.currentGame == 6) {
                curScoreTemp = SharedData.currentScore6;
            } else {
                curScoreTemp = SharedData.currentScore9;
            }
//            builder = new AlertDialog.Builder(this);
            //Setting message manually and performing action on button click
            builder.setMessage("SCORE: " + curScoreTemp.toString())
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(SharedData.currentGame == 3){
                                SharedData.currentScore3 = 0;
                                score = "Score: " + SharedData.currentScore3.toString();
                                textView.setText(score);
                            } else if(SharedData.currentGame == 6) {
                                SharedData.currentScore6 = 0;
                                score = "Score: " + SharedData.currentScore6.toString();
                                textView.setText(score);
                            } else {
                                SharedData.currentScore9 = 0;
                                score = "Score: " + SharedData.currentScore9.toString();
                                textView.setText(score);
                            }
                            populateView();
                        }
                    } );
            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle("GAME OVER");
            alert.show();
            return false;
        }
    }
}
