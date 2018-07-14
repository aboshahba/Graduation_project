package com.selema.newproject.viewpageer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;
import com.selema.newproject.Card.Credit;
import com.selema.newproject.R;
import com.selema.newproject.utils.BaseApiService;
import com.selema.newproject.utils.UtilsApi;
import com.vinaygaba.creditcardview.CreditCardView;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddNewCardFragment extends Fragment {
    BaseApiService mApiService;
    CardForm cardForm;
    Context mContext;

    Button AddCard;

    String ID;
    SharedPreferences settings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_new_card, container, false);
        mContext = getActivity();
        mApiService = UtilsApi.getAPIService();
        settings = PreferenceManager.getDefaultSharedPreferences(mContext);
        ID = settings.getString("phoneNumber", "01212260464");

        cardForm = (CardForm) root.findViewById(R.id.card_form);
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS is required on this number")
                .actionLabel("Purchase")
                .setup(getActivity());
        AddCard = root.findViewById(R.id.add_card_btn);
        AddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ADD();
            }
        });


        return root;
    }

    void ADD() {
        Toast.makeText(mContext, cardForm.getExpirationMonth().toString().trim() +
                cardForm.getExpirationYear().toString().trim() + " " + cardForm.getCardNumber(), Toast.LENGTH_SHORT).show();

        mApiService.AddCard(
                ID,
                cardForm.getCardNumber().toString(),
                cardForm.getExpirationMonth().toString().trim() + cardForm.getExpirationYear().toString().trim(),
                cardForm.getCvv().toString()
        ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.e("CALL Back", "-----------Succeffuly----------");
                    try {
                        String jsonRESULTS = response.body().string();
                        if (jsonRESULTS.equals("your Card is Addedd Successfully you can use it know within your transactions")) {

                            //         startActivity(new Intent(mContext, Credit.class));
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