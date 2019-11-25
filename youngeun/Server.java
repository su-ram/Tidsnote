package com.example.youngeun.parentapp;
import com.example.youngeun.parentapp.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.sql.*;
/*import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Connection;
*/
public class Server{


    public static void main(String[] args){

	    final int SERVER_PORT=6666;

	 
	 try{   
		 System.out.println("ok");
		 ServerSocket serverSocket= new ServerSocket(SERVER_PORT);

	    while(true){
	 
		 Socket client=serverSocket.accept();  
	    	 Server_Thread thread = new Server_Thread(client);
		 thread.start();
		 System.out.println("start!");
		

       }
	 }catch(IOException e){
		 e.printStackTrace();
	 }

    }

}
