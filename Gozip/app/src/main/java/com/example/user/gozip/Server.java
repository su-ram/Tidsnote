package com.example.user.gozip;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args){

        try{

            ServerSocket server= new ServerSocket(7777);
            Socket recv = server.accept();
            System.out.println("클라이언트 연결");

            InputStream inputStream = recv.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(inputStream);

            String data = (String)ois.readObject();
            System.out.println("Receive : "+data);

            ois.close();

            inputStream.close();
            server.close();
            recv.close();

        }catch (IOException e){

        }catch (ClassNotFoundException e){

        }
    }




}
