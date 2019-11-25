package com.example.youngeun.parentapp;

import java.io.Serializable;
public class loginData  implements Serializable{

    public String loginID;
    public String loginPW;

    public loginData(){

    }
    public loginData(String loginID, String loginPW) {
        this.loginID = loginID;
        this.loginPW = loginPW;
    }

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public String getLoginPW() {
        return loginPW;
    }

    public void setLoginPW(String loginPW) {
        this.loginPW = loginPW;
    }
}

