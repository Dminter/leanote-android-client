package com.zncm.leanote.data;

import java.util.Date;

/**
 * Created by MX on 3/11 0011.
 */
public class Notebooks extends Base {
    public String NotebookId;
    public String UserId;
    public String ParentNotebookId;
    public int Seq;
    public String Title;
    public String UrlTitle;
    public int NumberNotes;
    public String IsTrash;
    public String IsBlog;
    public String CreatedTime;
    public String UpdatedTime;
    public int Usn;
    public boolean IsDeleted;
//    public String Subs;
}
