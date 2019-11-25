package com.example.user.tidsnote;
import com.example.user.tidsnote.*;
import com.sun.istack.internal.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.sql.*;


public class Taking extends Thread{

    Socket client;
    String type;

    Taking(Socket socket){
        this.client=socket;
        type="";


    }


    public void run(){


        try{



            InputStream inputStream = client.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(inputStream);
            OutputStream outputStream = client.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);

            try {



                type=(String)ois.readObject();
		        System.out.println(type);

                switch(type){
                    case "init":
                    break;
                    case "add_notice":
                    Notice newNotice = new Notice();
                    newNotice = (Notice)ois.readObject();
                    requestDB(type, newNotice);


                    System.out.println("read new Notice");
                    System.out.println(newNotice.getTitle());
                    System.out.println(newNotice.getContent());




                    break;
                }



                







            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }catch(Exception e){
            e.printStackTrace();


        }

    }

    public Connection connectDB(Connection conn){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://localhost/tidsnotedb?characterEncoding=UTF-8" + "&user=suram&password=suram");
        }catch (Exception e){
            e.printStackTrace();
        }

        return conn;

    }

    public ArrayList<String> requestDB(String type, @Nullable Object object){


        Connection conn = null;
        Statement stmt = null;
        ResultSet resultSet = null;
        PreparedStatement pstmt = null;
        String sql = "";
        ArrayList<String> result=new ArrayList<String>();

        conn = connectDB(conn);


        try{
            switch (type) {



                    case "init":

                        stmt = conn.createStatement();
                        sql = "select * from student_tbl";
                        resultSet = stmt.executeQuery(sql);

                        while(resultSet.next()){
                            result.add(resultSet.getString("s_name"));
                            }



                        break;

                    case "add_student":
                        break;

                    case "send_all":
                        break;

                    case "add_notice":

                            Notice newNotice = (Notice)object;

                            sql = "insert into notice_tbl values(?,?,?,?,?,?)";
                            pstmt = conn.prepareStatement(sql);
                            pstmt.setInt(1, 0);
                            pstmt.setDate(2, newNotice.getDate());
                            pstmt.setInt(3, getT_id());
                            pstmt.setString(4, getTitle());
                            pstmt.setString(5,getContent());
                            pstmt.setString(6, "김빨강");





                        break;

                    default:
                        break;


            }}
            catch (Exception e){
            e.printStackTrace();
            }

            return result;


        }
    }



