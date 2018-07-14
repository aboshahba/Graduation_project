package com.selema.newproject.Messages;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.selema.newproject.R;

import java.util.List;

/**
 * Created by selema on 4/25/18.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    Context mContext;
    private List<Messages> messages;
    private List<Inbox> inbox;
    String contactId;
    Intent i;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView msgtime, sender;

        public MyViewHolder(View view) {
            super(view);
            msgtime = (TextView) view.findViewById(R.id.msgtimeid);
            sender = (TextView) view.findViewById(R.id.sendername);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Toast.makeText(mContext, position + " ", Toast.LENGTH_SHORT).show();
            i = new Intent(mContext, Chat.class);
            i.putExtra("senderphone", inbox.get(position).getPhoneNumber().toString());
            mContext.startActivity(i);


        }
    }


    public MessageAdapter(List<Messages> messages, List<Inbox> inbox) {
        this.messages = messages;
        this.inbox = inbox;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_messages_list_item, parent, false);

        this.mContext = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Inbox inboxes = inbox.get(position);
        List<Messages> messages = inboxes.getMessages();
        Toast.makeText(mContext, " My messages " + "inbox size" + inbox.size(), Toast.LENGTH_SHORT).show();

        holder.sender.setText(inboxes.getName() != null ? inboxes.getName() : "");
        if (messages != null && !messages.isEmpty()){
            holder.msgtime.setText(messages.get(0) != null ? messages.get(0).getMessageTime() : "");
        }

    }

    @Override
    public int getItemCount() {
        return inbox.size();
    }


}



