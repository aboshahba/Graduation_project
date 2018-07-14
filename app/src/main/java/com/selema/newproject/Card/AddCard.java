package com.selema.newproject.Card;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;
import com.selema.newproject.R;
import com.selema.newproject.utils.BaseApiService;
import com.selema.newproject.utils.UtilsApi;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCard extends AppCompatActivity {
    BaseApiService mApiService;
    CardForm cardForm;
    Context mContext;
    ProgressDialog loading;

    Button AddCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        mContext = getApplicationContext();
        mApiService = UtilsApi.getAPIService();

        cardForm = (CardForm) findViewById(R.id.card_form);
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS is required on this number")
                .actionLabel("Purchase")
                .setup(this);
        AddCard = findViewById(R.id.add_card_btn);
        AddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ADD();
            }
        });
    }

    void ADD() {
        Toast.makeText(mContext, cardForm.getExpirationMonth().toString().trim() +
                cardForm.getExpirationYear().toString().trim() + " " + cardForm.getCardNumber(), Toast.LENGTH_SHORT).show();

        mApiService.AddCard(
                "2",
                cardForm.getCardNumber().toString(),
                cardForm.getExpirationMonth().toString().trim()+cardForm.getExpirationYear().toString().trim() ,
                cardForm.getCvv().toString()
        ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.e("CALL Back", "-----------Succeffuly----------");
                    try {
                        String jsonRESULTS = response.body().string();
                        if (jsonRESULTS.equals("your Card is Addedd Successfully you can use it know within your transactions")) {

                            startActivity(new Intent(com.selema.newproject.Card.AddCard.this, Credit.class));
                            Toast.makeText(mContext, jsonRESULTS, Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(mContext, jsonRESULTS, Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.i("debug", "onResponse: GA BERHASIL");
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                Toast.makeText(mContext, "Internet error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
