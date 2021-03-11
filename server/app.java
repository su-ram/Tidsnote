package com.example.user.tidsnote;
import com.example.user.tidsnote.*;

import java.net.*;
import java.io.*;
import java.net.InetSocketAddress;
public class Server_suram{

    public static void main(String[] args){

        final int SERVER_PORT = 12345;
        ServerSocket serverSocket = null;
        Socket client;

        try{
            serverSocket= new ServerSocket();
            serverSocket.bind(new InetSocketAddress(SERVER_PORT));


            while(true){

                client = serverSocket.accept();
                Taking thread = new Taking(client);
                thread.start();

                try{

                    thread.join();
                    client.close();

                }catch(Exception e){
                    e.printStackTrace();
                }

            }

        }catch (IOException e){

            e.printStackTrace();

        }finally{
            
            if(serverSocket != null){
                try{
                    serverSocket.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
