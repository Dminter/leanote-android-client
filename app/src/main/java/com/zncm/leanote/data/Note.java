package com.zncm.leanote.data;

import java.util.Date;

/**
 * Created by MX on 3/11 0011.
 */
public class Note extends Base {
    public String NoteId;
    public String UserId;
    public String CreatedUserId;
    public String NotebookId;
    public String Title;
    public String Desc;
    public String ImgSrc;
    //    public String Tags;//[]
    public boolean IsTrash;
    public boolean IsBlog;
    public String UrlTitle;
    public boolean IsRecommend;
    public boolean IsTop;
    public boolean HasSelfDefined;
    public int ReadNum;
    public int LikeNum;
    public int CommentNum;
    public boolean IsMarkdown;
    public int AttachNum;
    public String CreatedTime;
    public String UpdatedTime;
    public String RecommendTime;
    public String PublicTime;
    public String UpdatedUserId;
    public int Usn;
    public boolean IsDeleted;

    public Note() {
    }


    public Note(String noteId, String userId, String createdUserId, String notebookId, String title, String desc, String imgSrc, boolean isTrash, boolean isBlog, String urlTitle, boolean isRecommend, boolean isTop, boolean hasSelfDefined, int readNum, int likeNum, int commentNum, boolean isMarkdown, int attachNum, String createdTime, String updatedTime, String recommendTime, String publicTime, String updatedUserId, int usn, boolean isDeleted) {
        NoteId = noteId;
        UserId = userId;
        CreatedUserId = createdUserId;
        NotebookId = notebookId;
        Title = title;
        Desc = desc;
        ImgSrc = imgSrc;
        IsTrash = isTrash;
        IsBlog = isBlog;
        UrlTitle = urlTitle;
        IsRecommend = isRecommend;
        IsTop = isTop;
        HasSelfDefined = hasSelfDefined;
        ReadNum = readNum;
        LikeNum = likeNum;
        CommentNum = commentNum;
        IsMarkdown = isMarkdown;
        AttachNum = attachNum;
        CreatedTime = createdTime;
        UpdatedTime = updatedTime;
        RecommendTime = recommendTime;
        PublicTime = publicTime;
        UpdatedUserId = updatedUserId;
        Usn = usn;
        IsDeleted = isDeleted;
    }


    public void setUser(Note note) {
        NoteId = note.NoteId;
        UserId = note.UserId;
        CreatedUserId = note.CreatedUserId;
        NotebookId = note.NotebookId;
        Title = note.Title;
        Desc = note.Desc;
        ImgSrc = note.ImgSrc;
        IsTrash = note.IsTrash;
        IsBlog = note.IsBlog;
        UrlTitle = note.UrlTitle;
        IsRecommend = note.IsRecommend;
        IsTop = note.IsTop;
        HasSelfDefined = note.HasSelfDefined;
        ReadNum = note.ReadNum;
        LikeNum = note.LikeNum;
        CommentNum = note.CommentNum;
        IsMarkdown = note.IsMarkdown;
        AttachNum = note.AttachNum;
        CreatedTime = note.CreatedTime;
        UpdatedTime = note.UpdatedTime;
        RecommendTime = note.RecommendTime;
        PublicTime = note.PublicTime;
        UpdatedUserId = note.UpdatedUserId;
        Usn = note.Usn;
        IsDeleted = note.IsDeleted;
    }
}
