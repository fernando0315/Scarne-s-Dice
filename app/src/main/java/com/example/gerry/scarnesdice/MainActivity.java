package com.example.gerry.scarnesdice;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gerry.scarnesdice.model.Player;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private Player user;
    private Player computer;

    private Button rollButton;
    private Button holdButton;
    private Button resetButton;
    private ImageView diceImage;
    private TextView scoreTextView;

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {

            if(computerTurn() != 1 && computer.getTurnScore() < 20)
                timerHandler.postDelayed(this, 1000);
            else
                cleanUp();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = new Player(0, 0);
        computer = new Player(0, 0);

        diceImage = (ImageView) findViewById(R.id.diceImage);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);

        rollButton = (Button) findViewById(R.id.rollButton);
        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int diceValue = rollDice();
                user.updateTurnScoreWithDiceValue(diceValue);

                printScore("user");
                if (diceValue == 1)
                    timerHandler.postDelayed(timerRunnable, 1000);
            }
        });

        holdButton = (Button) findViewById(R.id.holdButton);
        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.updateOverallScore();
                user.setTurnScore(0);
                printScore("computer");

                timerHandler.postDelayed(timerRunnable, 1000);
            }
        });

        resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerHandler.removeCallbacks(timerRunnable);
                computer.setOverallScore(0);
                computer.setTurnScore(0);
                user.setOverallScore(0);
                user.setTurnScore(0);
                cleanUp();
            }
        });
    }

    private int computerTurn() {
        rollButton.setEnabled(false);
        holdButton.setEnabled(false);

        int diceValue;
        //do{
            diceValue = rollDice() ;
            computer.updateTurnScoreWithDiceValue(diceValue);
            printScore("computer");
        //} while(computer.getTurnScore() < 20 && diceValue != 1);
            return diceValue;
    }

    private void printScore(String turnId) {
        scoreTextView.setText("Your score:" + user.getOverallScore() +
                " computer score: " + computer.getOverallScore() +
                " " + turnId + " turn score: " +
                (turnId.equals("user") ? user.getTurnScore() : computer.getTurnScore()));
    }

    private void cleanUp() {
        computer.updateOverallScore();
        computer.setTurnScore(0);
        printScore("user");
        rollButton.setEnabled(true);
        holdButton.setEnabled(true);
    }

    private int rollDice() {
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
