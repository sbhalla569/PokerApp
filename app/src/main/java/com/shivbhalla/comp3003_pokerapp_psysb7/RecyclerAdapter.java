//package com.shivbhalla.comp3003_pokerapp_psysb7;
//import android.annotation.SuppressLint;
//import android.app.Application;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.Spinner;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.recyclerview.widget.RecyclerView;
//
//import org.w3c.dom.Text;
//
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//
//public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
//
//    private List<MovementData> data;
//    private Context context;
//    private LayoutInflater layoutInflater;
//    private MovementViewModel viewModel;
//
//
//    public RecyclerAdapter(Context context, MovementViewModel viewModel){
//        this.data = new ArrayList<>();
//        this.context = context;
//        // View model created for use
//        this.viewModel = viewModel;
//        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//    }
//
//    @NonNull
//    @Override
//    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        // Inflating custom made database layout view and wrapping around RecyclerViewHolder object
//        View itemView = layoutInflater.inflate(R.layout.fragment_lobby, parent, false);
//        return new RecyclerViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
//        // Binding data to the correct position
//        holder.bind(data.get(position));
//    }
//
//    @Override
//    public int getItemCount() {
//        return data.size();
//    }
//
//    public void setData(List<MovementData> newData){
//        if(data != null){
//            data.clear();
//            data.addAll(newData);
//            notifyDataSetChanged();
//        }else{
//            data = newData;
//        }
//    }
//
//    class RecyclerViewHolder extends RecyclerView.ViewHolder{
//
//        TextView lobbyName;
//        TextView playerCount;
//        Button joinButton;
//        GameInfo gameInfo;
//
//
//        RecyclerViewHolder(View itemView){
//            super(itemView);
//
//            lobbyName = itemView.findViewById(R.id.name_lobby);
//            playerCount = itemView.findViewById(R.id.players_lobby);
//            joinButton = itemView.findViewById(R.id.join_button);
//
//        }
//
//
//        void bind(GameInfo gameInfo){
//
//            if(gameInfo != null){
//                lobbyName.setText(String.format(Locale.US, "Lobby: %d", gameInfo.getGameID()));
//                playerCount.setText(String.format(Locale.US, "%d/4", gameInfo.getPlayers().size()));
//                joinButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        int ID = gameInfo.getGameID();
//                        Intent intent = new Intent(context.getApplicationContext(), MultiPlayerGame.class);
//                        intent.putExtra("gameID", ID);
//                    }
//                });
//            }
//        }
//    }
//}
