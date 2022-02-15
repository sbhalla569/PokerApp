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

    public static void getGameList(GameInfo.IGameList onComplete){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("games").whereGreaterThan("gameID",0).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<GameInfo> games = queryDocumentSnapshots.toObjects(GameInfo.class);
                if(onComplete != null){
                    if(games.size() < 1){
                        onComplete.receiveList(null);
                        return;
                    }
                    games = games.stream().filter(gameInfo -> gameInfo.players.size() < 4).collect(Collectors.toList());
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
}
