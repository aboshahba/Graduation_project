package com.selema.newproject.MyTransactions;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

public class MyTransaction extends AppCompatActivity {

    Context mContext;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    BaseApiService mApiService;
    TransactionAdapter mAdapter;
    SharedPreferences settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_transaction);

        mContext = this;
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        String ID = settings.getString("phoneNumber", "01212260464");
        recyclerView = (RecyclerView) findViewById(R.id.transaction_recycler);
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        getTransaction(ID);

    }

    private void getTransaction(final String key) {


        mApiService.GetAllTransacion(key)
                .enqueue(new Callback<model>() {
                    @Override
                    public void onResponse(Call<model> call, Response<model> response) {

                        if (response.isSuccessful()) {

                            Toast.makeText(mContext, " My transaction" + "success", Toast.LENGTH_SHORT).show();
                            List<TranactionModel> tranactionModels= response.body().getTransactions();
                            mAdapter = new TransactionAdapter(tranactionModels);
                            recyclerView.setAdapter(mAdapter);
                            recyclerView.setLayoutManager(mLayoutManager);

                        } else {Toast.makeText(mContext, "error" , Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<model> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());

                    }
                });

    }

}
