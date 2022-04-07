package com.shivbhalla.comp3003_pokerapp_psysb7;

import java.util.ArrayList;
import java.util.List;

// Retrieves info from database
public class GameInfo {
    public static interface IGameReceiver{
        void receiveGame(GameInfo game);
    }
    public static interface IGameList{
        void receiveList(GameInfo[] list);
    }

    int actingPlayer;
    List<Integer> deck;
    int gameID;
    List<Player> players;
    int pot;
    int stage;
    List<Integer> table;
    int currentPlayer;
    int gameState;
    int lastActed;
    int dealer;

    public GameInfo(){
        actingPlayer = 0;
        gameID = 0;
        stage = 0;
        deck = new ArrayList<>();
        table = new ArrayList<>();
        players = new ArrayList<>();
        pot = 0;
        currentPlayer = 0;
        gameState = 0;
        lastActed = 0;
        dealer = 0;

        table.add(-1);
        table.add(-1);
        table.add(-1);
        table.add(-1);
        table.add(-1);
    }

    public int getDealer() {
        return dealer;
    }

    public void setDealer(int dealer) {
        this.dealer = dealer;
    }

    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getActingPlayer() {
        return actingPlayer;
    }

    public int getLastActed() {
        return lastActed;
    }

    public void setLastActed(int lastActed) {
        this.lastActed = lastActed;
    }

    public void setActingPlayer(int actingPlayer) {
        this.actingPlayer = actingPlayer;
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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getPot() {
        return pot;
    }

    public void setPot(int pot) {
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

    public void buildDeck(){
        Deck source = new Deck();
        for(int i=0; i<52; i++){
            try{
                deck.add(source.drawCard());
            }catch(Exception ignored){

            }
        }
    }
}
