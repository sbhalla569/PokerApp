package com.shivbhalla.comp3003_pokerapp_psysb7;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(onComplete != null){
                    onComplete.receiveGame(null);
                }
            }
        });
    }

    public static void setGameInfo(GameInfo info){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("games").whereEqualTo("gameID", info.gameID).get().addOnSuccessListener(
                new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.getDocuments().size() > 0){
                            db.collection("games").document(queryDocumentSnapshots.getDocuments().get(0).getId())
                            .set(info);
                            return;
                        }
                        db.collection("games").add(info);
                    }
                }
        );

    }
}
