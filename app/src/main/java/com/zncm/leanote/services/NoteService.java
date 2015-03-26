package com.zncm.leanote.services;


import com.zncm.leanote.data.Msg;
import com.zncm.leanote.data.Note;
import com.zncm.leanote.data.NoteDetails;
import com.zncm.leanote.data.NoteEx;
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

public interface NoteService {

    @GET("/note/getNotes")
    void getNotes(@Query("token") String token, @Query("notebookId") String notebookId, Callback<List<Note>> callback);


    @POST("/note/addNote")
    void addNote(@Query("token") String token, @Query("notebookId") String notebookId, @Query("Title") String title, @Query("Tags") String tags, @Query("Content") String content, @Query("abstract") String Abstract, @Query("IsMarkdown") boolean isMarkdown, Callback<Note> callback);

    @POST("/note/deleteTrash")
    void deleteNote(@Query("token") String token, @Query("noteId") String noteId, @Query("usn") int usn, Callback<Msg> callback);


    @POST("/note/getNoteContent")
    void getNoteContent(@Query("token") String token, @Query("noteId") String noteId, Callback<NoteDetails> callback);


    @POST("/note/updateNote")
    void updateNote(@Query("token") String token, @Query("noteId") String noteId, @Query("Usn") int usn, @Query("NotebookId") String notebookId, @Query("Title") String title, @Query("Tags") String tags, @Query("Content") String content, @Query("Abstract") String myAbstract, @Query("IsMarkdown") boolean isMarkdown, @Query("IsTrash") boolean isTrash, Callback<Msg> callback);

}
