package com.selema.newproject.MainActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.felix.bottomnavygation.BottomNav;
import com.felix.bottomnavygation.ItemNav;
import com.google.android.gms.vision.barcode.Barcode;
import com.selema.newproject.Friends.FriendsActivity;
import com.selema.newproject.uploadImages.Images;
import com.selema.newproject.LoginActivity;
import com.selema.newproject.Messages.MyMessages;
import com.selema.newproject.MyTransactions.MyTransaction;
import com.selema.newproject.QR.GenQr;
import com.selema.newproject.QR.ScanActivity;
import com.selema.newproject.R;
import com.selema.newproject.SearchResult;
import com.selema.newproject.edit.Edit_activity;
import com.selema.newproject.uploadImages.profile_Activity;
import com.selema.newproject.utils.BaseApiService;
import com.selema.newproject.utils.Search_response;
import com.selema.newproject.utils.UtilsApi;
import com.selema.newproject.viewpageer.viewpageActivity;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;
    /* @BindView(R.id.search_id)
     EditText searchView;
    */
    CardView Messages;
    CardView Image;
    CardView FriendsCard;
    CardView Creditcardview;
    CardView Transacions;
    CardView Logout;
    CardView setting;
    CardView profile;
    String ID;
    BaseApiService mApiService;
    SearchView searchViews;

    Context mContext;
    GridLayout mainGrid;
    /* @BindView(R.id.search_btn)

     Button search_btn;
 */ TextView result;
    String key;
    SharedPreferences settings;
    public static final String MY_PREFS_NAME = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainGrid = (GridLayout) findViewById(R.id.mainGrid);

        settings = PreferenceManager.getDefaultSharedPreferences(this);
        ID = settings.getString("phoneNumber", "01212260464");


        FriendsCard = findViewById(R.id.frinedsCard);
        Messages = findViewById(R.id.Messages);
        Transacions = findViewById(R.id.Transacions);
        Logout = findViewById(R.id.LogOutCard);
        Creditcardview = findViewById(R.id.Credit);
        setting = findViewById(R.id.SettingCard);
        profile = findViewById(R.id.LogOutCardz);

        //Set Event
        setSingleEvent(mainGrid);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent i = new Intent(mContext, profile_Activity.class);
                startActivity(i);
            }
        });
        FriendsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent i = new Intent(mContext, FriendsActivity.class);
                startActivity(i);
            }
        });
    /*    Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent i = new Intent(mContext, Images.class);
                i.putExtra("id", ID);
                startActivity(i);
            }
        });
    */    Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent i = new Intent(mContext, LoginActivity.class);
                i.putExtra("id", ID);
                startActivity(i);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Edit_activity.class);
                intent.putExtra("id", ID);
                startActivity(intent);
            }
        });
        Messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent i = new Intent(mContext, MyMessages.class);
                i.putExtra("id", ID);
                startActivity(i);
            }
        });

        Transacions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent i = new Intent(mContext, MyTransaction.class);
                i.putExtra("id", ID);
                startActivity(i);
            }
        });
        Creditcardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent i = new Intent(mContext, viewpageActivity.class);
                i.putExtra("id", ID);
                startActivity(i);
            }
        });


        searchViews = (SearchView) findViewById(R.id.searchView);
        //getMyData();

        BottomNav bottomNav = findViewById(R.id.bottomNav);
        bottomNav.addItemNav(new ItemNav(this, R.drawable.qrcodegenerate, "My QR Code").addColorAtive(R.color.colorAccent));
        bottomNav.addItemNav(new ItemNav(this, R.drawable.scanqr, "Scan").addColorAtive(R.color.colorAccent));
        bottomNav.build();

        bottomNav.setTabSelectedListener(listener);


        mContext = this;
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }


    }

    private void setSingleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Toast.makeText(mContext, "This is activity from card item index  " + finalI, Toast.LENGTH_SHORT).show();


                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //    Search(newText);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    BottomNav.OnTabSelectedListener listener = new BottomNav.OnTabSelectedListener() {
        @Override
        public void onTabSelected(int position) {
            Toast.makeText(MainActivity.this, "Click position " + position, Toast.LENGTH_SHORT).show();
            if (position == 0) {
                Intent intent = new Intent(MainActivity.this, GenQr.class);
                intent.putExtra("email", ID);
                startActivity(intent);
            } else {
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        }

        @Override
        public void onTabLongSelected(int i) {

        }


    };


    public void Search(final String key) {

        mApiService.SearchRequest(ID, key)
                .enqueue(new Callback<Search_response>() {
                    @Override
                    public void onResponse(Call<Search_response> call, Response<Search_response> response) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(MainActivity.this, SearchResult.class);
                            intent.putExtra("accountOwner", key);
                            intent.putExtra("email", response.body().getEmail().toString());
                            intent.putExtra("profile_picture", response.body().getProfilePicture().toString());
                            intent.putExtra("phone", response.body().getPhoneNumber().toString());
                            intent.putExtra("name", response.body().getName().toString());
                            intent.putExtra("Home", response.body().getHomeCity().toString());
                            intent.putExtra("Current", response.body().getCurrentCity().toString());
                            intent.putExtra("Bio", response.body().getBio().toString());
                            intent.putExtra("getFriends", response.body().getFriends().toString());
                            startActivity(intent);

                        } else {
                            Toast.makeText(mContext, " not success", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Search_response> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                final Barcode barcode = data.getParcelableExtra("barcode");
                result.post(new Runnable() {
                    @Override
                    public void run() {
                        Search(barcode.displayValue.toString());
                        result.setText(barcode.displayValue.toString());
                    }
                });
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

}
