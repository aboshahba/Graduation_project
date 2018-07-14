package com.selema.newproject.Messages;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.selema.newproject.R;
import com.selema.newproject.utils.BaseApiService;
import com.selema.newproject.utils.UtilsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyMessages extends AppCompatActivity {

    Context mContext;

    BaseApiService mApiService;
    private RecyclerView recyclerView;
    private MessageAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    View.OnClickListener RecyclerItemClickListener;

    SharedPreferences settings;
    String ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_messages);
        settings = PreferenceManager.getDefaultSharedPreferences(this);
         ID = settings.getString("phoneNumber", "01212260464");


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mContext = this;
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        getMessages();

    }

    private void getMessages() {
        mApiService.GetMessages(ID)
                .enqueue(new Callback<MessageList>() {
                    @Override
                    public void onResponse(Call<MessageList> call, Response<MessageList> response) {

                        if (response.isSuccessful()) {

                            Toast.makeText(mContext, " My messages" + "success", Toast.LENGTH_SHORT).show();

                            List<Inbox> inbox = response.body().getInbox();
                            List<Messages> messages = inbox.get(0).getMessages();

                            mAdapter = new MessageAdapter(messages, inbox);
                            recyclerView.setAdapter(mAdapter);
                            recyclerView.setLayoutManager(mLayoutManager);


                        } else {
                            Toast.makeText(mContext, " a7a", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<MessageList> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                    }
                });


    }
}
