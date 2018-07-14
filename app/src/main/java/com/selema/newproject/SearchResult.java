package com.selema.newproject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.selema.newproject.MainActivity.MainActivity;
import com.selema.newproject.utils.BaseApiService;
import com.selema.newproject.utils.Login_Response;
import com.selema.newproject.utils.UserLogin;
import com.selema.newproject.utils.UtilsApi;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResult extends AppCompatActivity {

    String name;
    String phone;
    String email;
    String current;
    String home;
    String bio;
    String ID;

    EditText transacion_money;
    TextView Name;
    TextView Phone;
    TextView Email;
    TextView Current;
    TextView Home;
    TextView Bio;

    String profile_image_search;
    Button SendMoney;
    Button RequestMoney;
    Button send;
    Button close;
    Dialog MyDialog;
    Dialog SendDialog;

    BaseApiService mApiService;
    EditText transacion_money_send_dialog;
    EditText message_content;
    EditText amount;
    EditText Transaction_password;
    EditText amount_send_money;
    EditText message_transacion_money_send_dialog;
    Context mcontext;
    EditText message_content_request_money_edtxt;
    String mydate;
    boolean getFriends;
    com.like.LikeButton likeButton;

    de.hdodenhof.circleimageview.CircleImageView profile_image;
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        settings = PreferenceManager.getDefaultSharedPreferences(this);
        ID = settings.getString("phoneNumber", "01212260464");


        profile_image = findViewById(R.id.profile);

        likeButton = findViewById(R.id.likebtn);
        mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        mcontext = this;

        mApiService = UtilsApi.getAPIService();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            email = extras.getString("email");
            profile_image_search = extras.getString("profile_picture");
            phone = extras.getString("phone");
            name = extras.getString("name");
            current = extras.getString("Home");
            home = extras.getString("Current");
            bio = extras.getString("Bio");
            getFriends = Boolean.valueOf(extras.getString("getFriends"));
        }

        Name = findViewById(R.id.name_txt);
        Home = findViewById(R.id.home_txt);
        Phone = findViewById(R.id.phone_txt);
        Current = findViewById(R.id.current_txt);
        Email = findViewById(R.id.email_txt);
        Home = findViewById(R.id.home_txt);
        Bio = findViewById(R.id.bio_txt);

        Picasso.with(mcontext).load("https://ee-wallet.herokuapp.com/images/"+ profile_image_search).into(profile_image);

        Name.setText(name);
        Home.setText(home);
        Phone.setText(phone);
        Current.setText(current);
        Bio.setText(bio);
        Email.setText(email);
        likeButton.setLiked(getFriends);

        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {

                AddFriend();
            }

            @Override
            public void unLiked(LikeButton likeButton) {

                RemoveFriend();
            }
        });
        SendMoney = (Button) findViewById(R.id.send_money_btn);
        RequestMoney = (Button) findViewById(R.id.request_money_btn);

        SendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "send money", Toast.LENGTH_LONG).show();
                Send_moneyDialog();
            }
        });
        RequestMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Request money", Toast.LENGTH_LONG).show();
                Request_Money_Dialog();
            }
        });
    }

    String jsonRESULTS;

    private void AddFriend() {
        mApiService.Follow(ID, phone)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            jsonRESULTS = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (response.isSuccessful()) {

                            if (jsonRESULTS.equals("you are now follow")) {

                                Toast.makeText(mcontext, jsonRESULTS, Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(mcontext, jsonRESULTS, Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(mcontext, jsonRESULTS, Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                    }
                });
    }

    private void RemoveFriend() {
        mApiService.UNFollow(ID, phone)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            jsonRESULTS = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (response.isSuccessful()) {

                            //  if (jsonRESULTS.equals("you are now follow")) {

                            Toast.makeText(mcontext, jsonRESULTS, Toast.LENGTH_SHORT).show();

                            //} else {
                            //   Toast.makeText(mcontext, jsonRESULTS, Toast.LENGTH_SHORT).show();
                            // }

                        } else {
                            Toast.makeText(mcontext, jsonRESULTS, Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                    }
                });
    }


    public void Request_Money_Dialog() {
        MyDialog = new Dialog(SearchResult.this);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.request_money);
        MyDialog.setTitle("Request Money");


        send = (Button) MyDialog.findViewById(R.id.send_request_money_btn);
        close = (Button) MyDialog.findViewById(R.id.cancle_request_money_btn);
        amount = MyDialog.findViewById(R.id.money_amount_edtxt);


        message_content_request_money_edtxt = MyDialog.findViewById(R.id.message_content_request_money_edtxt);
        send.setEnabled(true);
        close.setEnabled(true);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Send Request money "
                        + amount.getText().toString() + "  " + message_content_request_money_edtxt.getText().toString(), Toast.LENGTH_LONG).show();
                RequestMoney();
                MyDialog.cancel();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialog.cancel();
            }
        });

        MyDialog.show();
    }

    private void RequestMoney() {
        mApiService.RequestMoney(ID, phone, mydate, message_content_request_money_edtxt.getText().toString()
                , amount.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            String jsonRESULTS = response.body().toString();
                            if (jsonRESULTS.equals("message sent successfully")) {
                                Toast.makeText(mcontext, jsonRESULTS, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mcontext, jsonRESULTS, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mcontext, response.message().toString() + "hala ", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());

                    }
                });
    }

    private void SendMoney() {
        Toast.makeText(mcontext, "make transaction", Toast.LENGTH_SHORT).show();

        mApiService.SendMoney(ID
                , phone
                , mydate
                , transacion_money.getText().toString()
                , Transaction_password.getText().toString()
                , transacion_money_send_dialog.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if (response.isSuccessful()) {

                            try {
                                if (response.body().string().equals("Successful transaction")) {
                                    Toast.makeText(mcontext, response.body().string(), Toast.LENGTH_SHORT).show();
                                    Log.e("debug", "Send money" + response.body().string());

                                } else {
                                    Toast.makeText(mcontext, response.body().string(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(mcontext, response.message().toString(), Toast.LENGTH_SHORT).show();

                        }
                    }


                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());

                    }
                });
    }

    public void Send_moneyDialog() {
        SendDialog = new Dialog(SearchResult.this);
        SendDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        SendDialog.setContentView(R.layout.send_money);
        SendDialog.setTitle("Send Money");


        Transaction_password = SendDialog.findViewById(R.id.send_dialog_transaction_password_edtxt);

        Button sendmoney = (Button) SendDialog.findViewById(R.id.send_money_dialog_btn);
        Button closemoney = (Button) SendDialog.findViewById(R.id.cancle_send_money_dialog_btn);

        transacion_money = SendDialog.findViewById(R.id.send_dialog_money_amount_edtxt);
        transacion_money_send_dialog = SendDialog.findViewById(R.id.message_transacion_money_send_dialog);

        sendmoney.setEnabled(true);
        closemoney.setEnabled(true);

        sendmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMoney();
                Toast.makeText(getApplicationContext(), "Send Transaction money "
                        + ID + " " + phone + " " + mydate + " " +
                        transacion_money_send_dialog.getText().toString().trim()
                        + " " + transacion_money.getText().toString().trim()
                        + " " + Transaction_password.getText().toString(), Toast.LENGTH_LONG).show();
                SendDialog.cancel();
            }
        });
        closemoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendDialog.cancel();
            }
        });

        SendDialog.show();
    }
}





