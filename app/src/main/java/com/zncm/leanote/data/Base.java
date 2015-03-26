package com.zncm.leanote.data;

import com.activeandroid.Model;
import com.google.gson.Gson;

import java.io.Serializable;

public class Base implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        String obj = new Gson().toJson(this);
        return obj;
    }

}
