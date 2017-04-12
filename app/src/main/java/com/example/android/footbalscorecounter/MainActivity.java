package com.example.android.footbalscorecounter;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.android.footbalscorecounter.R.id.team_a_text;
import static com.example.android.footbalscorecounter.R.id.team_b_text;
import static com.example.android.footbalscorecounter.R.id.timerValue;


public class MainActivity extends Activity {

    private Button startButton;
    private Button pauseButton;

    private TextView timerValue;

    private long startTime = 0L;

    private Handler customHandler = new Handler();

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    int scoreTeamA = 0;
    int scoreTeamB = 0;


    @Override

    // set buttons for timer
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerValue = (TextView) findViewById(R.id.timerValue);

        startButton = (Button) findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {

            // start timer
            public void onClick(View view) {
                startTime = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimerThread, 0);

            }
        });

        pauseButton = (Button) findViewById(R.id.pauseButton);

        pauseButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                timeSwapBuff += timeInMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);

            }
        });

    }

    // create timer
    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timerValue.setText("" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }

    };

    // add +1 to team A score
    // get team name form edit text view and display it as toast msg
    public void addOneA (View view) {
        scoreTeamA ++;
        displayScoreA(scoreTeamA);

        EditText getNameA = (EditText) findViewById(R.id.team_a_text);
        String nameOfTeamA = getNameA.getText().toString();

        Toast.makeText(MainActivity.this, "Team " + nameOfTeamA  + " scores", Toast.LENGTH_SHORT).show();
    }

    // add +1 to team B score
    // get team name form edit text view and display it as toast msg
    public void addOneB (View view) {
        scoreTeamB ++;
        displayScoreB(scoreTeamB);

        EditText getNameB = (EditText) findViewById(R.id.team_b_text);
        String nameOfTeamB = getNameB.getText().toString();

        Toast.makeText(MainActivity.this, "Team " + nameOfTeamB  + " scores", Toast.LENGTH_SHORT).show();
    }

    // display score of team A
    private void displayScoreA (int score) {
        TextView bTeamScoreTextView = (TextView) findViewById(R.id.score_Team_A);
        bTeamScoreTextView.setText(String.valueOf(score));
    }

    // display score of team B
    private void displayScoreB (int score) {
        TextView bTeamScoreTextView = (TextView) findViewById(R.id.score_Team_B);
        bTeamScoreTextView.setText(String.valueOf(score));
    }

    // 'reset game' button
    // pauses and sets timer to 0
    public void reset (View view) {
        scoreTeamA = 0;
        scoreTeamB = 0;
        displayScoreA(scoreTeamA);
        displayScoreB(scoreTeamB);

        EditText addNameTeamAText = (EditText) findViewById(team_a_text);
        addNameTeamAText.setText("Team A");

        EditText addNameTeamBText = (EditText) findViewById(team_b_text);
        addNameTeamBText.setText("Team B");

        timeSwapBuff += timeInMilliseconds;
        customHandler.removeCallbacks(updateTimerThread);
        timeSwapBuff = 0;
        timerValue.setText ("00:00:00");
    }


}