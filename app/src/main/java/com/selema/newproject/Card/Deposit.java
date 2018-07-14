package com.selema.newproject.Card;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.selema.newproject.R;
import com.selema.newproject.utils.BaseApiService;
import com.selema.newproject.utils.UtilsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Deposit extends AppCompatActivity {

    Context mContext;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    BaseApiService mApiService;
    private Deposit_adapter mAdapter;
    String ID = "2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            ID = extras.getString("id");

        recyclerView = (RecyclerView) findViewById(R.id.Deposit_recycler);
        mContext = this;
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        getMyCard(ID);
    }

    private void getMyCard(final String key) {

        mApiService.getMyCards(key)
                .enqueue(new Callback<MyCardsModel>() {
                    @Override
                    public void onResponse(Call<MyCardsModel> call, Response<MyCardsModel> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(mContext, " My Cards" + "success", Toast.LENGTH_SHORT).show();
                            List<Card> cards = response.body().getCards();
                            mAdapter = new Deposit_adapter(mContext, cards,ID);
                            recyclerView.setAdapter(mAdapter);
                            recyclerView.setLayoutManager(mLayoutManager);
                        } else {
                            Toast.makeText(mContext, "what is the fuck", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MyCardsModel> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());

                    }
                });
    }
}
