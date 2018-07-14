package com.selema.newproject.MyTransactions;

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
 * Created by selema on 5/14/18.
 */

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder> {

    Context mContext;
    private List<TranactionModel> tranactionModels;
    Intent i;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView msgtime, sender ,reciever, money ,message;

        public MyViewHolder(View view) {
            super(view);
            msgtime = (TextView) view.findViewById(R.id.time_txt_transacion);
            message= (TextView) view.findViewById(R.id.message_txt_transacion);
            money = (TextView) view.findViewById(R.id.money_txt_transacion);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Toast.makeText(mContext, position + " ", Toast.LENGTH_SHORT).show();


        }
    }


    public TransactionAdapter(List<TranactionModel> transactions) {
        this.tranactionModels = transactions;

    }

    @Override
    public TransactionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transacion_item, parent, false);

        this.mContext = parent.getContext();
        return new TransactionAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TransactionAdapter.MyViewHolder holder, int position) {

        tranactionModels.get(position).getSenderID();
        Toast.makeText(mContext, " My Transaction " + "inbox size" + tranactionModels.size(), Toast.LENGTH_SHORT).show();

        holder.money.setText(tranactionModels.get(position).getTransactionAmount().toString());
        holder.message.setText(tranactionModels.get(position).getTransactionMessage().toString());
        holder.msgtime.setText(tranactionModels.get(position).getTransactionTime().toString());

    }

    @Override
    public int getItemCount() {
        return tranactionModels.size();
    }


}
