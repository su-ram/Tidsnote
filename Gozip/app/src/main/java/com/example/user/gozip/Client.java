package com.example.user.gozip;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.spec.ECField;

public class Client extends Thread{




    String id, comment;
    Socket socket;

    public Client(String id, String comment) {
        super();
        this.id = id;
        this.comment = comment;

    }
    public void setSocket(Socket socket){
        this.socket=socket;
    }
    @Override
    public void run() {

        try{
            socket = new Socket("gozipds.iptime.org", 9999);

            BufferedWriter buffer = new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream()));
            PrintWriter writer = new PrintWriter(buffer);
            writer.println(id+","+comment);
            writer.flush();



        }catch(Exception e){
            e.printStackTrace();
    }
}}
