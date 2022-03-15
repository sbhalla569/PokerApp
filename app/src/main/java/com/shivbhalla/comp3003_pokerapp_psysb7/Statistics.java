package com.shivbhalla.comp3003_pokerapp_psysb7;

import java.util.ArrayList;
import java.util.List;

public class Statistics {

    public static interface IStatReceiver{
        public void onReceive(Statistics statistics);
    };

    private int timesFolded;
    private int timesWon;
    private int timesLost;
    private int timesShouldHaveWon;
    private List<Integer> cardRaiseValue;
    private String email;

    public Statistics() {
        timesFolded = 0;
        timesWon = 0;
        timesLost = 0;
        timesShouldHaveWon = 0;
        email = "";
        cardRaiseValue = new ArrayList<>(52);
        for(int i = 0; i<52; i++){
            cardRaiseValue.add(0);
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTimesFolded() {
        return timesFolded;
    }

    public void setTimesFolded(int timesFolded) {
        this.timesFolded = timesFolded;
    }

    public int getTimesWon() {
        return timesWon;
    }

    public void setTimesWon(int timesWon) {
        this.timesWon = timesWon;
    }

    public int getTimesLost() {
        return timesLost;
    }

    public void setTimesLost(int timesLost) {
        this.timesLost = timesLost;
    }

    public int getTimesShouldHaveWon() {
        return timesShouldHaveWon;
    }

    public void setTimesShouldHaveWon(int timesShouldHaveWon) {
        this.timesShouldHaveWon = timesShouldHaveWon;
    }

    public List<Integer> getCardRaiseValue() {
        return cardRaiseValue;
    }

    public void setCardRaiseValue(List<Integer> cardRaiseValue) {
        this.cardRaiseValue = cardRaiseValue;
    }
}
