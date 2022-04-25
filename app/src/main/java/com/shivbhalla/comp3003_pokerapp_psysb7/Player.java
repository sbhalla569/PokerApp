package com.shivbhalla.comp3003_pokerapp_psysb7;

// Class to initialise the Player
public class Player {
    private String username;
    private String email;
    private int card1;
    private int card2;
    private int chipValue;
    private int raiseValue;
    private boolean folded;

    public Player() {
        card1 = 0;
        card2 = 0;
        chipValue = 0;
        raiseValue = 0;
        folded = false;
        username = "";
        email = "";
    }

    public Player(int chipValue, String username, String email) {
        this.chipValue = chipValue;
        card1 = 0;
        card2 = 0;
        raiseValue = 0;
        folded = false;
        this.username = username;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRaiseValue() {
        return raiseValue;
    }

    public void setRaiseValue(int raiseValue) {
        this.raiseValue = raiseValue;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isFolded() {
        return folded;
    }

    public void setFolded(boolean folded) {
        this.folded = folded;
    }

    public void addChipValue(int chipValue){
        this.chipValue += chipValue;
    }
}
