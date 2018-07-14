package com.selema.newproject.viewpageer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.selema.newproject.Card.Card;
import com.selema.newproject.Card.Deposit;
import com.selema.newproject.Card.Deposit_adapter;
import com.selema.newproject.Card.MyCardsModel;
import com.selema.newproject.Friends.FriendsActivity;
import com.selema.newproject.R;
import com.selema.newproject.utils.BaseApiService;
import com.selema.newproject.utils.UtilsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DepositFragment extends Fragment implements View.OnClickListener {

    Context mContext;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    BaseApiService mApiService;
    private Deposit_adapter mAdapter;
    String ID ;
    public DepositFragment() {
        // Required empty public constructor
    }

    Button deposit;
    Button withdraw;

    SharedPreferences settings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =inflater.inflate(R.layout.fragment_two, container, false);

        settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String ID = settings.getString("phoneNumber", "01212260464");

        recyclerView = (RecyclerView) root.findViewById(R.id.Deposit_recycler);
        mContext = getActivity();
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        getMyCard(ID);

        return root;

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


    @Override
    public void onClick(View v) {

    }
}

