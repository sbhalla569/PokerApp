package com.shivbhalla.comp3003_pokerapp_psysb7;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

// Retrieves info from database
public class GameInfo {
    public static interface IGameReceiver{
        void receiveGame(GameInfo game);
    }
    int dealer;
    List<Integer> deck;
    int gameID;
    Map<String,Map<String, Object>> players;
    Map<String,Object> pot;
    int stage;
    List<Integer> table;
    int currentPlayer;

    public GameInfo(){
        dealer = 0;
        gameID = 0;
        stage = 0;
        deck = new ArrayList<>();
        table = new ArrayList<>();
        players = new TreeMap<>();
        pot = new TreeMap<>();
        currentPlayer = 0;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getDealer() {
        return dealer;
    }

    public void setDealer(int dealer) {
        this.dealer = dealer;
    }

    public List<Integer> getDeck() {
        return deck;
    }

    public void setDeck(List<Integer> deck) {
        this.deck = deck;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public Map<String, Map<String, Object>> getPlayers() {
        return players;
    }

    public void setPlayers(Map<String, Map<String, Object>> players) {
        this.players = players;
    }

    public Map<String, Object> getPot() {
        return pot;
    }

    public void setPot(Map<String, Object> pot) {
        this.pot = pot;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public List<Integer> getTable() {
        return table;
    }

    public void setTable(List<Integer> table) {
        this.table = table;
    }
}
