package com.example.user.gozip;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.renderscript.ScriptGroup;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class SendToServer extends Thread{



    Socket socket;
    private Handler handler;
    String type;
    String [] data;
    Message msg = new Message();
    String photopath;

    public SendToServer(Handler handler, String type) {
        this.handler=handler;
        this.type=type;

    }

    public SendToServer(String type,String[] data){
        this.type=type;
        this.data=data;

    }

    public SendToServer(String type, String photopath){
        this.type=type;
        this.photopath=photopath;
    }
    public void setSocket(Socket socket){
        this.socket=socket;
    }


    @Override
    public void run() {

        ArrayList<String> result=null;

        try{
            socket = new Socket("gozipds.iptime.org", 9999);
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);


            /*
            BufferedWriter buffer = new BufferedWriter(new OutputStreamWriter(
                    outputStream));

            PrintWriter writer = new PrintWriter(buffer);
*/
            if (type.equals("getNames")) {

                oos.writeObject("0");
                result = (ArrayList<String>) objectInputStream.readObject();
                msg.obj = result;
                handler.sendMessage(msg);


/*

                writer.println("0");
                writer.flush();

                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

                result = (ArrayList<String>) objectInputStream.readObject();

                msg.obj = result;
                handler.sendMessage(msg);

                writer.close();

                */

            }else if (type.equals("sendData")){


                oos.writeObject("1");
                for (int i=0;i<data.length;i++){
                    oos.writeObject(data[i]);

                }
                /*
                writer.println("1");
                writer.flush();
                buffer.flush();
                for (int i=0;i<data.length;i++){
                    writer.println(data[i]);
                    writer.flush();
                }

                writer.close();
*/
            }else if (type.equals("photo")){
                //식단 사진 전송

                oos.writeObject("2");
                FileInputStream fileInputStream = new FileInputStream(photopath);
                byte[] data = new byte[fileInputStream.available()];
                fileInputStream.read(data);
                oos.writeObject(data);


/*
                try {

                    Socket photosocket = new Socket("gozipds.iptime.org", 6666);
                    FileInputStream fileInputStream = new FileInputStream(photopath);
                    byte[] data = new byte[fileInputStream.available()];
                    fileInputStream.read(data);
                    ObjectOutputStream oos = new ObjectOutputStream(photosocket.getOutputStream());

                    oos.writeObject(data);
                    oos.close();

                    Log.i("SENDPHOTO","SUCCESS");

                }catch (Exception e){
                    e.printStackTrace();
                }
                */

            }else if(type.equals("STT")){
                oos.writeObject("3");
                oos.writeObject(data[0]);
                oos.writeObject(data[1]);


            }else if(type.equals("getComment")){
                oos.writeObject("4");
                oos.writeObject(data[0]);

                msg.obj = (ArrayList<String>) objectInputStream.readObject();

                handler.sendMessage(msg);

            }


        }catch(Exception e){
            e.printStackTrace();
        }


    }



}
