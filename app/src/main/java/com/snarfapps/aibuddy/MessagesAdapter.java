package com.snarfapps.aibuddy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder> {

    private ArrayList<Message> messageArrayList;
    public MessagesAdapter(ArrayList<Message> messageArrayList) {
        this.messageArrayList = messageArrayList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View messageView = LayoutInflater.from(parent.getContext()).inflate( (viewType==0?R.layout.chat_ai_item:R.layout.chat_me_item), null);
        return new MessageViewHolder(messageView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        holder.tvMessage.setText(messageArrayList.get(position).message);
    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {

        return messageArrayList.get(position).sentBy == Message.SENT_BY.AI? 0:1;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{

        public TextView tvMessage;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tvMessageText);
        }
    }
}
