package com.example.user.tidsnote;




import java.net.*;
import java.io.*;
public class Server_suram{



    public static void main(String[] args){

        final int SERVER_PORT = 12345;
        //ServerSocket serverSocket = null;


        try{

            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);

            while(true){

                Socket client = serverSocket.accept();
                Taking thread = new Taking(client);
                thread.start();

            }

        }catch (IOException e){
            e.printStackTrace();
        }

    }








}
