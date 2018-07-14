package com.selema.newproject.Card;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.selema.newproject.R;
import com.selema.newproject.utils.BaseApiService;
import com.selema.newproject.utils.UtilsApi;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BankDepositandwithDraw extends AppCompatActivity {
    Context mContext;
    BaseApiService mApiService;
    Button Doit;
    Button withdraw;
    String jsonRESULTS;

    String ID;
    String CardNumber;

    String mydate;
    SharedPreferences settings;
    EditText moneyamount;
    EditText transaction_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_depositandwith_draw);

        moneyamount = findViewById(R.id.editText2);
        transaction_password = findViewById(R.id.editText);

        Intent intent = getIntent();
        CardNumber = intent.getStringExtra("CardNumber");
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        ID = settings.getString("phoneNumber", "01212260464");

        Doit = findViewById(R.id.DOITbtn);
        withdraw = findViewById(R.id.withDrawbank);
        mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        mContext = this;
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper
        initComponents();


    }

    private void initComponents() {
        Doit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoIt("depoist");
            }
        });
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoIt("draw");
            }
        });
    }

    //id=2&cardNumber=11&transactionType=depoist&amount=10&transaction_password=2
    private void DoIt(String kind) {
        mApiService.TransferBankMoney(
                ID,
                CardNumber,
                kind,
                transaction_password.getText().toString().trim(),
                moneyamount.getText().toString().trim(),mydate)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            jsonRESULTS = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (response.isSuccessful()) {
                            if (jsonRESULTS.equals("Your Money is Moved Successfully TO Your Card")) {


                                Toast.makeText(mContext, jsonRESULTS, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(BankDepositandwithDraw.this, Credit.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(mContext, jsonRESULTS, Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(mContext, jsonRESULTS, Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());


                    }
                });

    }


}
