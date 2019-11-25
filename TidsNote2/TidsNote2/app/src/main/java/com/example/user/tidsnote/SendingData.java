package com.example.user.tidsnote;

import android.os.Handler;
import android.os.Message;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;


import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SendingData extends Thread{



    Socket socket;
    private Handler handler;
    String type;
    String [] data;
    Message msg = new Message();
    String photopath;






/*
    public void requestPush(String token){
        //firebase에 push 알림을 보내도록 요청하는 메소드


        POST /fcm/send HTTP/1.1
Host: fcm.googleapis.com
Content-Type: application/json
Authorization: key=AAAASZkKR-Y:APA91bEoM03nSEtuq_JwhxSDr-ebK13cwyIa3dxeG7EJbqblKGdyYw-mNM1x16hLvphdzfbkT4gpjGGm8n46r3_XkBKPXd2HWBXc1xlHvzbyEvnWDf_i4MdaaIm_Rqz1i60PHHNoolTY
User-Agent: PostmanRuntime/7.15.2
Accept:
        Cache-Control: no-cache
        Postman-Token: 8174ddcc-548a-48c1-8fde-d6fc842658ae,5a532609-00e4-4559-835f-9ac2432ba302
        Host: fcm.googleapis.com
        Accept-Encoding: gzip, deflate
        Content-Length: 325
        Connection: keep-alive
        cache-control: no-cache

        { "to" : "eY_u98T9Z6Q:APA91bGsgzeRCHfRTdeC4VPnFwzoD3LwbbzqaGMcrOvn18aHoSA4E5bwagJla_PUcjjinSKSujqwo7QGEkcdNioL8EBzkbZaoYn0e2ivfXUx5mNF-ggc3vF3SVrgMHN_a0v9VuJ5cNK1", "priority" : "high",
        "notification" : { "body" : "This is test", "title" : "Tidsnote" }, "data" : { "title" : "FG Title", "message" : "Foreground Message" } }




        String auth = "AAAASZkKR-Y:APA91bEoM03nSEtuq_JwhxSDr-ebK13cwyIa3dxeG7EJbqblKGdyYw-mNM1x16hLvphdzfbkT4gpjGGm8n46r3_XkBKPXd2HWBXc1xlHvzbyEvnWDf_i4MdaaIm_Rqz1i60PHHNoolTY";




        try{

            JSONObject jsonObject = new JSONObject();
            Json

            jsonObject.put("to", "eY_u98T9Z6Q:APA91bGsgzeRCHfRTdeC4VPnFwzoD3LwbbzqaGMcrOvn18aHoSA4E5bwagJla_PUcjjinSKSujqwo7QGEkcdNioL8EBzkbZaoYn0e2ivfXUx5mNF-ggc3vF3SVrgMHN_a0v9VuJ5cNK1");
            jsonObject.put("priority", "high");

            JSONObject data = new JSONObject();
            data.put("body", "This is push test");
            data.put("title", "Tidsnote");

            jsonObject.put("notification", data);
            data.put("title", "FG Title");
            data.put("message", "Foreground Message");
            jsonObject.put("data",data);
            String result = jsonObject.toString();

            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.addRequestProperty("Authorization", "key="+auth); //key값 설정
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type","application/json");
            DataOutputStream osw = new DataOutputStream(con.getOutputStream());
            osw.write(result.getBytes("UTF-8"));
            osw.flush();

        }catch(Exception e){

            e.printStackTrace();
        }





    }




*/















    public SendingData(Handler handler, String type) {
        this.handler=handler;
        this.type=type;

    }

    public SendingData(String type, String[] data){
        this.type=type;
        this.data=data;

    }

    public SendingData(String type, String photopath){
        this.type=type;
        this.photopath=photopath;
    }
    public void setSocket(Socket socket){
        this.socket=socket;


        try {
            ServerSocket serverSocket = new ServerSocket(12345);

            serverSocket.close();
            serverSocket.bind(new InetSocketAddress(12345));



        }catch (Exception e){
            e.printStackTrace();
        }



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
