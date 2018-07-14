package com.selema.newproject.Card;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.selema.newproject.R;
import com.vinaygaba.creditcardview.CreditCardView;

import java.util.List;

/**
 * Created by selema on 6/24/18.
 */

public class Deposit_adapter extends RecyclerView.Adapter<Deposit_adapter.MyViewHolder> {
    Context mContext;
    private List<Card> myCardsModels;
    Intent i;
    SharedPreferences settings;
    String ID;
    String name;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CreditCardView creditCardView;


        public MyViewHolder(View view) {

            super(view);
            creditCardView = view.findViewById(R.id.card_list_item);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Toast.makeText(mContext, position + " ", Toast.LENGTH_SHORT).show();
            i = new Intent(mContext, BankDepositandwithDraw.class);
            i.putExtra("id", ID);
            i.putExtra("CardNumber", myCardsModels.get(position).getCardNumber().toString());
            mContext.startActivity(i);

        }
    }

    public Deposit_adapter(Context context, List<Card> Mycards, String ID) {
        this.myCardsModels = Mycards;
        mContext = context;
        this.ID = ID;
     /*   if (extras != null)
            ID = extras.getString("id");
        //getMyData();
*/


    }

    @Override
    public Deposit_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mycards_item_recycler, parent, false);

        settings = PreferenceManager.getDefaultSharedPreferences(mContext);
        ID = settings.getString("phoneNumber", "01212260464");
        name = settings.getString("name", "01212260464");
        return new Deposit_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Deposit_adapter.MyViewHolder holder, int position) {

        myCardsModels.get(position);

        holder.creditCardView.setCardNumber(myCardsModels.get(position).getCardNumber().toString());
        holder.creditCardView.setExpiryDate(myCardsModels.get(position).getExpiredDate().toString());
        holder.creditCardView.setCardName(name);


    }

    @Override
    public int getItemCount() {
        return myCardsModels.size();
    }


}