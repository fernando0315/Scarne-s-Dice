package com.example.gerry.scarnesdice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gerry.scarnesdice.model.Player;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Player user;
    private Player computer;
    private Random rand = new Random();

    private Button rollButton;
    private Button holdButton;
    private Button resetButton;
    private ImageView diceImage;
    private TextView scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = new Player(0,0);
        computer = new Player(0,0);

        rollButton = (Button) findViewById(R.id.rollButton);
        diceImage = (ImageView) findViewById(R.id.diceImage);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int diceValue = rand.nextInt(6) + 1;

                switch(diceValue) {
                    case 1: diceImage.setImageResource(R.drawable.dice1);
                        break;
                    case 2: diceImage.setImageResource(R.drawable.dice2);
                        break;
                    case 3: diceImage.setImageResource(R.drawable.dice3);
                        break;
                    case 4: diceImage.setImageResource(R.drawable.dice4);
                        break;
                    case 5: diceImage.setImageResource(R.drawable.dice5);
                        break;
                    default:diceImage.setImageResource(R.drawable.dice6);
                        break;
                }

                user.updateTurnScoreWithDiceValue(diceValue);
                printScore("user");

                if(diceValue == 1)
                    computerTurn();
            }
        });

        holdButton = (Button) findViewById(R.id.holdButton);
        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.updateOverallScore();
                user.setTurnScore(0);
                printScore("user");
                computerTurn();
            }
        });

        resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                computer.setOverallScore(0);
                computer.setTurnScore(0);
                user.setOverallScore(0);
                user.setTurnScore(0);
                printScore("user");
            }
        });

    }

    private void computerTurn() {
        rollButton.setEnabled(false);
        holdButton.setEnabled(false);

        int diceValue;
        do{
            diceValue = rand.nextInt(6) + 1;
            computer.updateTurnScoreWithDiceValue(diceValue);
            printScore("computer");
        } while(computer.getTurnScore() < 20 && diceValue != 1);

        computer.updateOverallScore();
        printScore("user");


        rollButton.setEnabled(true);
        holdButton.setEnabled(true);
    }

    private void printScore(String turnId) {

        scoreTextView.setText("Your score:" + user.getOverallScore() +
                " computer score: " + computer.getOverallScore() +
                " " + turnId + " turn score: " +
                (turnId.equals("user") ? user.getTurnScore() : computer.getTurnScore()));
    }

}
