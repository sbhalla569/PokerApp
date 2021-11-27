package com.shivbhalla.comp3003_pokerapp_psysb7;

public class Player {
    private int card1;
    private int card2;
    private int chipValue;
    private boolean folded;

    public Player() {
        card1 = 0;
        card2 = 0;
        chipValue = 0;
        folded = false;
    }

    public int getCard1() {
        return card1;
    }

    public void setCard1(int card1) {
        this.card1 = card1;
    }

    public int getCard2() {
        return card2;
    }

    public void setCard2(int card2) {
        this.card2 = card2;
    }

    public int getChipValue() {
        return chipValue;
    }

    public void setChipValue(int chipValue) {
        this.chipValue = chipValue;
    }

    public boolean isFolded() {
        return folded;
    }

    public void setFolded(boolean folded) {
        this.folded = folded;
    }
}
