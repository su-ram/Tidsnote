package com.example.youngeun.parentapp;

import java.io.Serializable;
import com.example.youngeun.parentapp.*;
public class CommentRequest implements Serializable{
    
    String s_id,c_date,n_date;

    public CommentRequest(String s_id) {
        this.s_id = s_id;
     }
    public CommentRequest(String s_id, String c_date,String n_date) {
        this.s_id = s_id;
        this.c_date = c_date;
	this.n_date = n_date;
    }

     public CommentRequest(){

    }
    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getC_date() {
        return c_date;
    }

    public void setC_date(String c_date) {
        this.c_date = c_date;
    }
     public void setN_date(String n_date) {
        this.n_date = n_date;
    }
    public String getN_date() {
        return n_date;
    }
}

