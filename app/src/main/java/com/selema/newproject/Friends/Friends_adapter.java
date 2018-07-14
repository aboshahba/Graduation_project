package com.selema.newproject.Friends;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.vision.barcode.Barcode;
import com.selema.newproject.MainActivity.MainActivity;
import com.selema.newproject.R;
import com.selema.newproject.SearchResult;
import com.selema.newproject.utils.BaseApiService;
import com.selema.newproject.utils.Search_response;
import com.selema.newproject.utils.UtilsApi;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.selema.newproject.utils.UtilsApi.BASE_URL_API;

/**
 * Created by selema on 6/24/18.
 */

public class Friends_adapter extends RecyclerView.Adapter<Friends_adapter.MyViewHolder> {
    Context mContext;
    private List<Friend> friends;
    Intent i;
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;
    SharedPreferences settings;
    BaseApiService mApiService;
    Intent intent;
    String ID;
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView FriendName;
        de.hdodenhof.circleimageview.CircleImageView profileImage;

        String profile_image_search;
        public MyViewHolder(View view) {
            super(view);
            settings = PreferenceManager.getDefaultSharedPreferences(mContext);
            ID = settings.getString("phoneNumber", "01212260464");

            mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper
            FriendName = (TextView) view.findViewById(R.id.friendname);
            profileImage = view.findViewById(R.id.friend_image_list_item);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Toast.makeText(mContext, position + " ", Toast.LENGTH_SHORT).show();
            Search(friends.get(position).getPhoneNumber().toString());
        }
    }

    public Friends_adapter(Context context, List<Friend> friends) {
        this.friends = friends;
        mContext = context;

    }

    @Override
    public Friends_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friends_list_item, parent, false);

        this.mContext = parent.getContext();
        return new Friends_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Friends_adapter.MyViewHolder holder, int position) {

        friends.get(position);
        Toast.makeText(mContext, friends.get(position).getProfilePicture().toString(), Toast.LENGTH_SHORT).show();

        holder.FriendName.setText(friends.get(position).getName().toString());

        //    if(friends.get(position).getName().toString()!= null)
        Picasso.with(mContext).load("https://ee-wallet.herokuapp.com/images/" +
                friends.get(position).getProfilePicture().toString()).into(holder.profileImage);

    }

    public void Search(final String key) {

        mApiService.SearchRequest(ID, key)
                .enqueue(new Callback<Search_response>() {
                    @Override
                    public void onResponse(Call<Search_response> call, Response<Search_response> response) {
                        if (response.isSuccessful()) {
                            intent = new Intent(mContext, SearchResult.class);
                            intent.putExtra("accountOwner", key);
                            intent.putExtra("email", response.body().getEmail().toString());
                            intent.putExtra("profile_picture", response.body().getProfilePicture().toString());
                            intent.putExtra("phone", response.body().getPhoneNumber().toString());
                            intent.putExtra("name", response.body().getName().toString());
                            intent.putExtra("Home", response.body().getHomeCity().toString());
                            intent.putExtra("Current", response.body().getCurrentCity().toString());
                            intent.putExtra("Bio", response.body().getBio().toString());
                            intent.putExtra("getFriends", response.body().getFriends().toString());
                            mContext.startActivity(intent);

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
    public int getItemCount() {
        return friends.size();
    }


}
