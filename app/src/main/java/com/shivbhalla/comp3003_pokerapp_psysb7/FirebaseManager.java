package com.shivbhalla.comp3003_pokerapp_psysb7;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FirebaseManager {

    // Function to get the game information from the game database
    public static void getGameInfo(int gameID, GameInfo.IGameReceiver onComplete){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("games").whereEqualTo("gameID", gameID).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<GameInfo> games = queryDocumentSnapshots.toObjects(GameInfo.class);
                if(onComplete != null){
                    if(games.size() < 1){
                        onComplete.receiveGame(null);
                        return;
                    }
                    onComplete.receiveGame(games.get(0));
                }
            }
        }).addOnFailureListener(e -> {
            if(onComplete != null){
                onComplete.receiveGame(null);
            }
        });
    }

    // Function to get the gameID from the database
    public static void getNewGameID(GameInfo.IGameReceiver onComplete){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("games").orderBy("gameID", Query.Direction.DESCENDING).limit(1).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<GameInfo> games = queryDocumentSnapshots.toObjects(GameInfo.class);
                if(onComplete != null){
                    if(games.size() < 1){
                        onComplete.receiveGame(null);
                        return;
                    }
                    onComplete.receiveGame(games.get(0));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(onComplete != null){
                    onComplete.receiveGame(null);
                }
            }
        });
    }

    // Function used in the lobby system to set the display data
    public static void getGameList(GameInfo.IGameList onComplete){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("games").whereLessThan("gameState",2).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<GameInfo> games = queryDocumentSnapshots.toObjects(GameInfo.class);
                if(onComplete != null){
                    if(games.size() < 1){
                        onComplete.receiveList(null);
                        return;
                    }
                    onComplete.receiveList(games.toArray(new GameInfo[0]));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(onComplete != null){
                    onComplete.receiveList(null);
                }
            }
        });
    }

    // Function to set the game information in the game database
    public static void setGameInfo(GameInfo info){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("games").whereEqualTo("gameID", info.gameID).get().addOnSuccessListener(
                queryDocumentSnapshots -> {
                    if(queryDocumentSnapshots.getDocuments().size() > 0){
                        db.collection("games").document(queryDocumentSnapshots.getDocuments().get(0).getId())
                        .set(info);
                        return;
                    }
                    db.collection("games").add(info);
                }
        );

    }

    // Function to get the statistics from the statistics database
    public static void getStatistics(String email, Statistics.IStatReceiver onComplete){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("stats").whereEqualTo("email", email).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Statistics> stats = queryDocumentSnapshots.toObjects(Statistics.class);
                if(onComplete != null){
                    if(stats.size() < 1){
                        onComplete.onReceive(null);
                        return;
                    }
                    onComplete.onReceive(stats.get(0));
                }
            }
        }).addOnFailureListener(e -> {
            if(onComplete != null){
                onComplete.onReceive(null);
            }
        });
    }

    // Function to set the statistics in the statistics database
    public static void setStatistics(Statistics info){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("stats").whereEqualTo("email", info.getEmail()).get().addOnSuccessListener(
                queryDocumentSnapshots -> {
                    if(queryDocumentSnapshots.getDocuments().size() > 0){
                        db.collection("stats").document(queryDocumentSnapshots.getDocuments().get(0).getId())
                                .set(info);
                        return;
                    }
                    db.collection("stats").add(info);
                }
        );

    }
}
