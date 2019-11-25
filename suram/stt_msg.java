package com.example.user.tidsnote;
import com.example.user.tidsnote.*;

import java.util.Date;
import java.io.Serializable;

public class stt_msg implements Serializable{
    String stt;
    String msg;
    boolean stt_selected=false,msg_selected=false;
    Date date;


    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isStt_selected() {
        return stt_selected;
    }

    public void setStt_selected(boolean stt_selected) {
        this.stt_selected = stt_selected;
    }

    public boolean isMsg_selected() {
        return msg_selected;
    }

    public void setMsg_selected(boolean msg_selected) {
        this.msg_selected = msg_selected;
    }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
