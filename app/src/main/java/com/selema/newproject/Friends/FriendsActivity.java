package com.selema.newproject.Friends;

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

public class FriendsActivity extends AppCompatActivity {


    Context mContext;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    BaseApiService mApiService;
    Friends_adapter friends_adapter;
    String ID ;
    public static final String MY_PREFS_NAME = "MyLogin";
    SharedPreferences settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        settings = PreferenceManager.getDefaultSharedPreferences(this);
        String ID = settings.getString("phoneNumber", "01212260464");

        recyclerView = (RecyclerView) findViewById(R.id.friends_recycler);
        mContext = this;
        mApiService = UtilsApi.getAPIService();
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        getfrineds(ID);

    }

    void getfrineds(String key) {


        mApiService.getFriends(key)
                .enqueue(new Callback<FriendsModel>() {
                    @Override
                    public void onResponse(Call<FriendsModel> call, Response<FriendsModel> response) {

                        if (response.isSuccessful()) {
                            Toast.makeText(mContext, " Frineds are here" + "success", Toast.LENGTH_SHORT).show();
                            List<Friend> friendList = response.body().getFriends();
                            friends_adapter = new Friends_adapter(mContext, friendList);
                            recyclerView.setAdapter(friends_adapter);
                            recyclerView.setLayoutManager(mLayoutManager);

                        } else {
                            Toast.makeText(mContext, "what is the fuck", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<FriendsModel> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());

                    }
                });

    }
}
