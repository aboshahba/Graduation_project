package com.selema.newproject.Messages;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.selema.newproject.R;

import java.util.List;

/**
 * Created by selema on 5/3/18.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    static final String TAG = ChatAdapter.class.getSimpleName();

    Context mContext;
    private List<Messages> messages;
    private List<Inbox> inbox;
    String senderphone ;
    Intent i;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        com.github.library.bubbleview.BubbleTextView message_left;
        com.github.library.bubbleview.BubbleTextView message_right;
        ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            message_left = view.findViewById(R.id.text_message_left);
            message_right = view.findViewById(R.id.text_message_right);
            imageView = view.findViewById(R.id.profile_image);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Toast.makeText(mContext, position + " ", Toast.LENGTH_SHORT).show();


        }
    }


    public ChatAdapter(List<Messages> messages, List<Inbox> inbox, String senderphone) {
        this.senderphone = senderphone;
        this.messages = messages;
        this.inbox = inbox;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_message_recieved, parent, false);

        this.mContext = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        Messages messag = messages.get(position);

//        Toast.makeText(mContext, " messages.size() " + messages.size(), Toast.LENGTH_SHORT).show();

        Log.i(TAG, "onBindViewHolder: " + messag.getMessageId());
        if (messag.getSenderID().equals(senderphone)) {
            holder.message_right.setVisibility(LinearLayout.VISIBLE);
            holder.message_right.setText(messag.getMessageContent());
            holder.message_left.setVisibility(LinearLayout.GONE);
            holder.imageView.setVisibility(LinearLayout.GONE);
            Toast.makeText(mContext, " sender phone " + messag.getMessageId(), Toast.LENGTH_SHORT).show();

        } else {
            holder.message_left.setVisibility(LinearLayout.VISIBLE);
            holder.message_left.setText(messag.getMessageContent());
            holder.message_right.setVisibility(LinearLayout.GONE);
            Toast.makeText(mContext, " sender phone " + messag.getMessageId(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


}



