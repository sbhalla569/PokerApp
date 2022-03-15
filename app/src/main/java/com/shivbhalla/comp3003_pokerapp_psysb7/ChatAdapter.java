package com.shivbhalla.comp3003_pokerapp_psysb7;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private GameInfo data;
    private Context context;
    private LayoutInflater layoutInflater;


    public ChatAdapter(Context context){
        this.data = null;
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating custom made database layout view and wrapping around RecyclerViewHolder object
        View itemView = layoutInflater.inflate(R.layout.fragment_multiplayer_chat, parent, false);
        return new ChatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        // Binding data to the correct position
        if(data != null){
            holder.bind(data.getChat().get(position), data);
        }
    }

    @Override
    public int getItemCount() {
        return data.getChat().size();
    }

    public void setData(GameInfo newData){
        data = newData;
    }

    class ChatViewHolder extends RecyclerView.ViewHolder{

        TextView playerName;
        TextView message;

        ChatViewHolder(View itemView){
            super(itemView);

            playerName = itemView.findViewById(R.id.player_name);
            message = itemView.findViewById(R.id.message_sent);
        }


        void bind(GameInfo.Message chatMessage, GameInfo gameInfo){

            if(gameInfo != null){
                playerName.setText(gameInfo.players.get(chatMessage.getPlayer()).getUsername());
                message.setText(chatMessage.getMessage());
            }
        }
    }
}
