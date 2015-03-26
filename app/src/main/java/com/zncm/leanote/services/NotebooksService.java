package com.zncm.leanote.services;


import com.zncm.leanote.data.Msg;
import com.zncm.leanote.data.Notebooks;
import com.zncm.leanote.data.User;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
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

public interface NotebooksService {

    @GET("/notebook/getNotebooks")
    void getNotebooks(@Query("token") String token, Callback<List<Notebooks>> callback);

    @GET("/notebook/deleteNotebook")
    void deleteNotebook(@Query("token") String token, @Query("notebookId") String notebookId, @Query("usn") int usn, Callback<Msg> callback);

    @POST("/notebook/updateNotebook")
    void updateNotebook(@Query("token") String token, @Query("notebookId") String notebookId, @Query("title") String title, @Query("parentNotebookId") String parentNotebookId, @Query("seq") int seq, @Query("usn") int usn, Callback<Msg> callback);

    @POST("/notebook/addNotebook")
    void addNotebook(@Query("token") String token, @Query("title") String title, @Query("parentNotebookId") String parentNotebookId, @Query("seq") int seq, Callback<Msg> callback);

}
