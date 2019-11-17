package com.example.user.gozip;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Image {




    public static void main(String[] args) {

        try{
        ServerSocket serverSocket = new ServerSocket(9999);
        Socket client = serverSocket.accept();
            InputStream inputStream = client.getInputStream();
            String str=null;

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            str=reader.readLine();

            if(str.equals("2")){

        File file = new File("C:\\photo.jpeg");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int temp;

        while((temp=inputStream.read(buffer))>0){

            fileOutputStream.write(buffer);
            fileOutputStream.flush();


        }}
    }catch (Exception e){

        }


}}