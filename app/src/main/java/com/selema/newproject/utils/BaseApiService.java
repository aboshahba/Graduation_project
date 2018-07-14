package com.selema.newproject.utils;

import com.selema.newproject.Card.MyCardsModel;
import com.selema.newproject.Friends.FriendsModel;
import com.selema.newproject.Messages.MessageList;
import com.selema.newproject.MyTransactions.model;
import com.selema.newproject.UploadObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;


public interface BaseApiService {


    @FormUrlEncoded
    @POST("login")
    Call<UserLogin> loginRequest(@Field("id") String phone,
                                 @Field("password") String password);

    @FormUrlEncoded
    @POST("regist")
    Call<ResponseBody> registerRequest(@Field("email") String email,
                                       @Field("password") String password,
                                       @Field("frist") String fristname,
                                       @Field("last") String lastname,
                                       @Field("home") String home,
                                       @Field("phoneNumber") String phone,
                                       @Field("current") String currentaddress,
                                       @Field("transaction_password") String transaction_password,
                                       @Field("bio") String bio);

    @FormUrlEncoded
    @PUT("https://ee-wallet.herokuapp.com/profile/update")
    Call<example> updateRequest(@Field("password") String password,
                                @Field("transaction_password") String transaction_password,
                                @Field("frist") String fristname,
                                @Field("home") String home,
                                @Field("current") String currentaddress,
                                @Field("bio") String bio,
                                @Field("last") String lastname,
                                @Field("id") String phone);


    @FormUrlEncoded
    @POST("search")
    Call<Search_response> SearchRequest(@Field("id") String id,
                                        @Field("key") String key);

    @FormUrlEncoded
    @POST("add-message")
    Call<ResponseBody> RequestMoney(@Field("senderID") String senderId,
                                    @Field("receiverID") String reciverId,
                                    @Field("message-time") String messageTime,
                                    @Field("message-content") String messageContent,
                                    @Field("requested-money") String requestedMoney);

    @FormUrlEncoded
    @POST("get-messages")
    Call<MessageList> GetMessages(@Field("id") String id);

    @FormUrlEncoded
    @POST("add-transaction")
    Call<ResponseBody> SendMoney(@Field("senderID") String senderId,
                                 @Field("receiverID") String reciverId,
                                 @Field("transaction-time") String messageTime,
                                 @Field("money") String money,
                                 @Field("password") String password,
                                 @Field("transaction-message") String transaction_message);

    @FormUrlEncoded
    @POST("get-transactions")
    Call<model> GetAllTransacion(@Field("id") String id);


    @FormUrlEncoded
    @POST("/bank/AddCard")
    Call<ResponseBody> AddCard(@Field("id") String id,
                               @Field("cardNumber") String cardNumberz,
                               @Field("expiredDate") String expiredDatez,
                               @Field("cvv") String cvvz);

    @FormUrlEncoded
    @POST("bank/getCard")
    Call<MyCardsModel> getMyCards(@Field("id") String id);

    @FormUrlEncoded
    @POST("get-contacts")
    Call<FriendsModel> getFriends(@Field("id") String id);

    @FormUrlEncoded
    @POST("/bank/transaction")
    Call<ResponseBody> TransferBankMoney(@Field("id") String id,
                                         @Field("cardNumber") String cardNumberz,
                                         @Field("transactionType") String transactionType,
                                         @Field("transaction_password") String transaction_password,
                                         @Field("amount") String amount, @Field("time") String time);


    @FormUrlEncoded
    @POST("follow")
    Call<ResponseBody> Follow(@Field("myID") String myID,
                              @Field("friendID") String friendID);

    @FormUrlEncoded
    @POST("unfollow")
    Call<ResponseBody> UNFollow(@Field("myID") String myID,
                                @Field("friendID") String friendID);


    @Multipart
    @POST("/imagefolder/index.php")
    Call<UploadObject> uploadImage(@Part MultipartBody.Part profile_picture, @Part("id") RequestBody id);
    //https://ee-wallet.herokuapp.com/upload?profile_picture=&id=
}
