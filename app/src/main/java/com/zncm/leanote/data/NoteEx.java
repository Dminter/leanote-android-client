package com.zncm.leanote.data;

import java.io.Serializable;
import java.util.List;

public class NoteEx extends Note implements Serializable {

    public String Content;
    public String Abstract;


    public NoteEx() {
    }

    public NoteEx(String content, String anAbstract, Note note) {
        Content = content;
        Abstract = anAbstract;
        setUser(note);
    }


}