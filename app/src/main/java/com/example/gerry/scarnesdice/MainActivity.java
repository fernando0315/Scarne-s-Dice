package com.example.gerry.scarnesdice;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gerry.scarnesdice.model.Player;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private Player user;
    private Player computer;

    private Button rollButton;
    private Button holdButton;
    private Button resetButton;
    private ImageView diceImage1;
    private ImageView diceImage2;
    private TextView scoreTextView;

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {

            if(computerTurn() && computer.getTurnScore() < 20) {
                timerHandler.postDelayed(this, 500);
            } else if(computer.getOverallScore() > 99) {
                Toast toast = Toast.makeText(getApplicationContext(), "Computer win!!", Toast.LENGTH_SHORT);
                toast.show();
                resetGame();
            }
            else {
                cleanUp();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = new Player(0, 0);
        computer = new Player(0, 0);


        diceImage1 = (ImageView) findViewById(R.id.diceImage1);
        diceImage2 = (ImageView) findViewById(R.id.diceImage2);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);

        rollButton = (Button) findViewById(R.id.rollButton);
        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int diceValue1 = rollDice(diceImage1);
                int diceValue2 = rollDice(diceImage2);
                user.updateTurnScoreWithDiceValue(diceValue1, diceValue2);

                printScore("user");

                if(user.getOverallScore()+user.getTurnScore() > 99) {
                    Toast toast = Toast.makeText(getApplicationContext(), "User win!!", Toast.LENGTH_SHORT);
                    toast.show();
                    resetGame();
                }

                if ((diceValue1 == 1 || diceValue2 == 1) || diceValue1 == diceValue2)
                    timerHandler.postDelayed(timerRunnable, 1000);
            }
        });

        holdButton = (Button) findViewById(R.id.holdButton);
        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerHandler.postDelayed(timerRunnable, 1000);
            }
        });

        resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerHandler.removeCallbacks(timerRunnable);
                resetGame();
            }
        });
    }

    /*********************************************************
     * This function will reset the game
     */
    void resetGame() {
        timerHandler.removeCallbacks(timerRunnable);
        computer.setOverallScore(0);
        computer.setTurnScore(0);
        user.setOverallScore(0);
        user.setTurnScore(0);
        cleanUp();
    }

    /*********************************************************
     * This method is called when it is computer's turn. It
     * will disable some of the buttons and roll dice
     * automatically for the computer
     * @return false - if next turn is not needed
     *         true - if next turn is needed
     */
    private boolean computerTurn() {
        user.updateOverallScore();
        user.setTurnScore(0);
        printScore("computer");
        rollButton.setEnabled(false);
        holdButton.setEnabled(false);

        int diceValue1 = rollDice(diceImage1);
        int diceValue2 = rollDice(diceImage2);
        computer.updateTurnScoreWithDiceValue(diceValue1, diceValue2);
        printScore("computer");

        return ((diceValue1 != 1 && diceValue2 != 1) && (diceValue1 != diceValue2));
    }

    /*********************************************************
     * This is the method to print the score label view
     * @param turnId - whose turn is it now
     */
    private void printScore(String turnId) {
        scoreTextView.setText("Your score:" + user.getOverallScore() +
                " computer score: " + computer.getOverallScore() +
                " " + turnId + " turn score: " +
                (turnId.equals("user") ? user.getTurnScore() : computer.getTurnScore()));
    }

    /*********************************************************
     * This method is use to prepare the UI for the user turn
     */
    private void cleanUp() {
        computer.updateOverallScore();
        computer.setTurnScore(0);
        printScore("user");
        rollButton.setEnabled(true);
        holdButton.setEnabled(true);
    }


    /**********************************************************
     * This function will roll dice randomly and post to the
     * input param imageview and return the random value.
     * @param diceImage
     * @return random dice value
     */
    private int rollDice(ImageView diceImage) {
        Random rand = new Random();
        int diceValue = rand.nextInt(6) + 1;

        switch(diceValue) {
            case 1:
                diceImage.setImageResource(R.drawable.dice1);
                break;
            case 2:
                diceImage.setImageResource(R.drawable.dice2);
                break;
            case 3:
                diceImage.setImageResource(R.drawable.dice3);
                break;
            case 4:
                diceImage.setImageResource(R.drawable.dice4);
                break;
            case 5:
                diceImage.setImageResource(R.drawable.dice5);
                break;
            default:
                diceImage.setImageResource(R.drawable.dice6);
                break;
        }

        return diceValue;
    }
}
