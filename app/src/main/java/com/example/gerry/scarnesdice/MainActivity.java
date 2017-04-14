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
                Random rand = new Random();
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
                scoreTextView.setText("Your score:" + user.getOverallScore() +
                        " computer score: " + computer.getOverallScore() +
                        " your turn score: " + user.getTurnScore());
            }
        });

        holdButton = (Button) findViewById(R.id.holdButton);
        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.updateOverallScore();
                user.setTurnScore(0);
                scoreTextView.setText("Your score:" + user.getOverallScore() +
                        " computer score: " + computer.getOverallScore() +
                        " your turn score: " + user.getTurnScore());
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
            }
        });
    }



}
