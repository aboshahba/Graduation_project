package com.selema.newproject.Card;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.selema.newproject.R;

public class Credit extends AppCompatActivity {

    Context mconContext;
    Button addcard;
    Button Deposit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);
        mconContext =getApplicationContext();

        addcard = findViewById(R.id.add_card);
        addcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent i = new Intent(mconContext, AddCard.class);
                startActivity(i);
            }
        });

        Deposit = findViewById(R.id.DepositBtn);
        Deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent i = new Intent(mconContext, com.selema.newproject.Card.Deposit.class);
                startActivity(i);
            }
        });
    }
}
