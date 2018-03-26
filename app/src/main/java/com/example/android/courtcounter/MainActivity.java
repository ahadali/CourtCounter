package com.example.android.courtcounter;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    int scoreTeamA = 0;

    int scoreTeamB = 0;

    private static final long START_TIME_IN_MILLIS = 15000;

    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;

    private Button A_threePoints;

    private Button A_twoPoints;

    private Button A_onePoint;

    private Button B_threePoints;

    private Button B_twoPoints;

    private Button B_onePoint;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewCountDown = (TextView) findViewById(R.id.text_view_countdown);

        mButtonStartPause = (Button) findViewById(R.id.button_start);
        mButtonReset = (Button) findViewById(R.id.button_reset);

        A_threePoints = (Button) findViewById(R.id.A_three_points);

        A_twoPoints = (Button) findViewById(R.id.A_two_points);

        A_onePoint = (Button) findViewById(R.id.A_one_point);

        B_threePoints = (Button) findViewById(R.id.B_three_points);

        B_twoPoints = (Button) findViewById(R.id.B_two_points);

        B_onePoint = (Button) findViewById(R.id.B_one_point);

        /* Disabling Buttons for Team A */
        A_threePoints.setEnabled(false);
        A_twoPoints.setEnabled(false);
        A_onePoint.setEnabled(false);

        /* Disabling Buttons for Team B */
        B_threePoints.setEnabled(false);
        B_twoPoints.setEnabled(false);
        B_onePoint.setEnabled(false);


        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        updateCountDownText();
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            /* This method is called when the Countdown Timer has finished */
            @Override
            public void onFinish() {
                mTimerRunning = false;
                mButtonStartPause.setEnabled(false);
                A_threePoints.setEnabled(false);
                A_twoPoints.setEnabled(false);
                A_onePoint.setEnabled(false);

                B_threePoints.setEnabled(false);
                B_twoPoints.setEnabled(false);
                B_onePoint.setEnabled(false);

                if (scoreTeamA > scoreTeamB) {
                    TextView textView = (TextView) findViewById(R.id.text_view_countdown);
                    textView.setText("Team A Won!");
                } else if (scoreTeamB > scoreTeamA) {
                    TextView textView = (TextView) findViewById(R.id.text_view_countdown);
                    textView.setText("Team B Won!");
                } else {
                    TextView textView = (TextView) findViewById(R.id.text_view_countdown);
                    textView.setText("Its a Tie!");
                }
                mButtonStartPause.setText("Start");

                mButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();

        mTimerRunning = true;
        mButtonStartPause.setText("pause");
        mButtonReset.setVisibility(View.INVISIBLE);

        A_threePoints.setEnabled(true);
        A_twoPoints.setEnabled(true);
        A_onePoint.setEnabled(true);

        B_threePoints.setEnabled(true);
        B_twoPoints.setEnabled(true);
        B_onePoint.setEnabled(true);
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("Start");
        A_threePoints.setEnabled(false);
        A_twoPoints.setEnabled(false);
        A_onePoint.setEnabled(false);

        B_threePoints.setEnabled(false);
        B_twoPoints.setEnabled(false);
        B_onePoint.setEnabled(false);
    }

    /* this method will reset the timer and points both */
    private void resetTimer() {
        mButtonStartPause.setEnabled(true);
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        scoreTeamA = 0;
        scoreTeamB = 0;
        displayForTeamA(scoreTeamA);
        displayForTeamB(scoreTeamB);
    }

    /* this will display the countdown time in the specified Time Format */
    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }


    /**
     * Increase the score for Team A by 1 point.
     */
    public void addOneForTeamA(View v) {
        scoreTeamA = scoreTeamA + 1;
        displayForTeamA(scoreTeamA);
    }

    /**
     * Increase the score for Team A by 2 points.
     */
    public void addTwoForTeamA(View v) {
        scoreTeamA = scoreTeamA + 2;
        displayForTeamA(scoreTeamA);
    }

    /**
     * Increase the score for Team A by 3 points.
     */
    public void addThreeForTeamA(View v) {
        scoreTeamA = scoreTeamA + 3;
        displayForTeamA(scoreTeamA);

    }

    /**
     * Increase the score for Team B by 1 point.
     */
    public void addOneForTeamB(View v) {
        scoreTeamB = scoreTeamB + 1;
        displayForTeamB(scoreTeamB);
    }

    /**
     * Increase the score for Team B by 2 points.
     */
    public void addTwoForTeamB(View v) {
        scoreTeamB = scoreTeamB + 2;
        displayForTeamB(scoreTeamB);
    }

    /**
     * Increase the score for Team B by 3 points.
     */
    public void addThreeForTeamB(View v) {
        scoreTeamB = scoreTeamB + 3;
        displayForTeamB(scoreTeamB);

    }

    /**
     * Displays the given score for Team A.
     */
    public void displayForTeamA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_a_score);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Displays the given score for Team B.
     */
    public void displayForTeamB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_b_score);
        scoreView.setText(String.valueOf(score));
    }
}