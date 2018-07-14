package com.selema.newproject.edit;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.selema.newproject.R;
import com.selema.newproject.utils.BaseApiService;
import com.selema.newproject.utils.UtilsApi;
import com.selema.newproject.utils.example;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Edit_activity extends AppCompatActivity {
    EditText etNama;
    EditText etPassword;
    EditText Lastname_edit;
    EditText Adress_edit;
    EditText home_edit;
    EditText bio_edit;
    EditText transaction_password;
    Button btnupdate;
    ProgressDialog loading;
    Context mContext;

    BaseApiService mApiService;

    SharedPreferences settings;
    public static int App_REQUEST_CODE = 1;
    String ID="0";
    String pass="0";
    String bio="0";
    String transaction_pass="0";
    String current="0";
    String first_name="0";
    String last_name="0";
    String home="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        ID = settings.getString("phoneNumber", "0");
        String Email = settings.getString("email", "0");
        home= settings.getString("homeCity", "0");
        bio = settings.getString("bio", "0");
    //    String profile_picture= settings.getString("profile_picture", "0");


        setContentView(R.layout.activity_edit);
        etNama = (EditText) findViewById(R.id.first_name);
        Lastname_edit=(EditText) findViewById(R.id.last_name);
        etPassword = (EditText) findViewById(R.id.password);
        Adress_edit=(EditText) findViewById(R.id.address);
        bio_edit = (EditText) findViewById(R.id.bio);
        home_edit =(EditText) findViewById(R.id.current_address);
        transaction_password = (EditText) findViewById(R.id.transaction);
        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService();
        btnupdate = (Button) findViewById(R.id.btnupdate);

        first_name = etNama.getText().toString();
        pass = etPassword.getText().toString();
        last_name = Lastname_edit.getText().toString();
        current = Adress_edit.getText().toString();
        bio = bio_edit.getText().toString();
        home = home_edit.getText().toString();
        transaction_pass = transaction_password.getText().toString();
        initComponents();


        //if (current.matches("")) {

    }

    private void initComponents() {
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Welcome", true, false);
                //Toast.makeText(mContext, "wait", Toast.LENGTH_SHORT).show();
                requestupdate();


            }
        });
    }

    private void requestupdate() {
        // Toast.makeText(mContext, "waiting", Toast.LENGTH_SHORT).show();
        mApiService.updateRequest(
                pass,
                transaction_pass,
                first_name,
                home,
                current,
                bio,
                last_name,
                ID).enqueue(new Callback<example>() {
            @Override
            public void onResponse(Call<example> call, Response<example> response) {
                if (response.isSuccessful()) {
                    Log.e("CALL Back", "-----------Succeffuly----------");
                    loading.dismiss();

                    String jsonRESULTS = response.body().getResponseMessage();
                    if (jsonRESULTS.equals("user data is updated Successfully and there is new User data to2mr b7aga tany ya brns :  ")) {

                        Toast.makeText(mContext, jsonRESULTS, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(mContext, jsonRESULTS, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(mContext, " no resposne ", Toast.LENGTH_SHORT).show();
                    Log.i("debug", "onResponse: GA BERHASIL");
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<example> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                Toast.makeText(mContext, "Internet error", Toast.LENGTH_SHORT).show();
            }
        });


    }

}


