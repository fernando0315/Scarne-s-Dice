package com.example.gerry.scarnesdice.model;

/**
 * Created by gerry on 4/13/17.
 */

public class Player {

    private int overallScore;
    private int turnScore;

    public Player(int overallScore, int turnScore) {
        this.overallScore = overallScore;
        this.turnScore = turnScore;
    }

    public int getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(int overallScore) {
        this.overallScore = overallScore;
    }

    public int getTurnScore() {
        return turnScore;
    }

    public void setTurnScore(int turnScore) {
        this.turnScore = turnScore;
    }

    public void updateTurnScoreWithDiceValue(int diceVal1, int diceVal2) {
        if(diceVal1 != 1 && diceVal2 != 1) {
            turnScore += diceVal1 + diceVal2;
        } else{
            turnScore = 0;
        }

        if(diceVal1 == diceVal2) {
            if(diceVal1 == 1) {
                overallScore = 0;
            } else {
                overallScore = overallScore + overallScore + turnScore;
            }
        }
    }

    public void updateOverallScore() {
        overallScore += turnScore;
    }
}
