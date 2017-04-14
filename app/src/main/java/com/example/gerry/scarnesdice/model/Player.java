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

    public void updateScoreWithDiceValue(int diceVal) {
        if(diceVal != 1) {
            turnScore += diceVal;
        } else {
            turnScore = 0;
        }
    }
}
