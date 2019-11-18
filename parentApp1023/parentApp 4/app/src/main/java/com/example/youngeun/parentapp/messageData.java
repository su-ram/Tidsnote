package com.example.youngeun.parentapp;

import java.io.Serializable;

public class messageData implements Serializable {

    public String p_id;
    public String mdate;
    public String message;
    public String request;
    public String m_id;
    public String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getM_id() {
        return m_id;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }
    public messageData(String p_id, String mdate, String message, String request, String m_id,String state) {

        this.mdate = mdate;
        this.message = message;
        this.request=request;
        this.p_id=p_id;
        this.m_id=m_id;
        this.state=state;

    }

    public messageData(){

    }
    public String getP_id() {
        return p_id;
    }
    public void setP_id(String p_id) {
        this.p_id = p_id;
    }
    public String getMdate() {
        return mdate;
    }

    public void setMdate(String mdate) {
        this.mdate = mdate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
