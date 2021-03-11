package com.example.user.tidsnote;


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


                ArrayList<String> result = requestDB(type);
                oos.writeObject(result);








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

    public ArrayList<String> requestDB(String type){


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



