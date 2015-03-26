package com.zncm.leanote.services;


import com.zncm.leanote.data.Notebooks;
import com.zncm.leanote.data.User;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

public interface UserService {
    @GET("/auth/login")
    void login(@Query("email") String email, @Query("pwd") String pwd, Callback<User> callback);

    @FormUrlEncoded
    @POST("/auth/login")
    void login2(@Field("email") String email, @Field("pwd") String pwd, Callback<User> callback);


}
