package com.example.user.tidsnote;

import android.app.Notification;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.ClientInfoStatus;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.io.FileOutputStream;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Client extends Thread{

    private static final String SERVER_IP ="gozipds.iptime.org";
    private static final int SERVER_PORT = 9999;
    String type;
    private Handler handler;
    private Object object;
    android.os.Message msg = new Message();





    ArrayList<byte[]> urilist;
    MainActivity mainActivity;
    Fragment fragment;

    int rowid = 0;

    public ArrayList<byte[]> getUrilist() {
        return urilist;
    }

    public void setUrilist(ArrayList<byte[]> urilist) {
        this.urilist = urilist;
    }

    public int getRowid() {
        return rowid;
    }

    public void setRowid(int rowid) {
        this.rowid = rowid;
    }

    Client(@Nullable Fragment fragment, String type){
        this.type=type;
        this.fragment=fragment;

    }
    Client(String type){
        this.type=type;
    }

    public void setHandler(Handler handler){
        this.handler=handler;
    }

    Client(@Nullable MainActivity mainActivity, String type){

        this.mainActivity=mainActivity;
        this.type=type;

    }

    public void setData(Object data){
        object=data;
    }
    @Override


    synchronized public void run() {
        Socket socket  = null;
        String message="",reply="";

        try{
            // 2. 서버와 연결을 위한 소켓을 생성
            socket = new Socket();

            // 3. 생성한 소켓을 서버의 소켓과 연결(connect)
            socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));

            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);

            oos.writeObject(type);
            oos.flush();

            switch (type){

                case "Notice_photo":
                    oos.writeObject((int)object);
                    break;

                case "upload_pdf":
                    MenuFile menuFile=(MenuFile)object;
                    oos.writeObject((MenuFile)object);
                    break;

                case "upload_check":
                    oos.writeObject((String)object);
                    break;

                case "student_photos":
                    urilist = (ArrayList<byte[]>)object;

                    if (urilist == null | urilist.size() == 0 ){
                        oos.writeObject(-1);

                    }else {
                        oos.writeObject(0);
                        oos.writeObject(object);
                    }
                    break;
                case "send_all_photo":

                    if (urilist != null){
                        oos.writeObject(urilist.size());
                        oos.writeObject(urilist);
                        oos.writeObject((String)object);
                    }else{
                        oos.writeObject(-1);
                    }



                    break;

                case "alert_parent":
                    oos.writeObject((Student)object);
                    break;

                case "alert_teacher":
                    oos.writeObject((String)object);

                    break;

                case "on":
                    oos.writeObject((String)object);



                    break;
                case "off":

                    oos.writeObject((String)object);





                    break;
                case "push":

                    oos.writeObject((String)object);


                    break;
                case "update_token":
                    oos.writeObject((String)object);


                    break;
                case "login":
                    String login[] = (String[])object;
                    oos.writeObject(login);
                    break;
                case "init":
                    String init = (String)object;
                    oos.writeObject(init);


                    break;
                case "add_student":
                    Student newStudent = (Student)object;
                    oos.writeObject(newStudent);

                    break;
                case "send_all":

                    String[] common = (String[])object;
                    oos.writeObject(common);

                    break;
                case "add_notice":
                    Notice newNotice = (Notice)object;
                    oos.writeObject(newNotice);
                    break;
                case "writing":
                    Student student = (Student)object;
                    oos.writeObject(student);

                    if (urilist != null){
                        oos.writeObject(urilist.size());
                        oos.writeObject(urilist);
                    }else{
                        oos.writeObject(-1);
                    }
                    //Log.i("URILIST",String.valueOf(urilist.size()));

                    break;
                case "add_stt":
                    Student stt = (Student)object;
                    oos.writeObject(stt);

                    break;

                case "record_list":

                    oos.writeObject((Student)object);

                    break;

                case "new_intro":

                    oos.writeObject((String[])object);


                    default:
                        break;


            }
            oos.flush();




            try{

                /*
                ArrayList<String> result = (ArrayList<String>)ois.readObject();
                for (int i=0;i<result.size();i++){
                    Log.i(type,result.get(i));
                }

                msg.obj = result;
                handler.sendMessage(msg);
                */

               // ((MainActivity)MainActivity.context).noticeList=(ArrayList<Notice>)ois.readObject();




                ObjectInputStream ois = new ObjectInputStream(inputStream);

                switch (type){
                    case "init":
                        this.mainActivity.arrayList=(ArrayList<Student>)ois.readObject();
                        this.mainActivity.noticeList=(ArrayList<Notice>)ois.readObject();
                        this.mainActivity.requestList=(ArrayList<Request>)ois.readObject();
                        ArrayList<Class_> c = (ArrayList<Class_>)ois.readObject();
                        ArrayList<String> menu = (ArrayList<String>) ois.readObject();
                        this.mainActivity.menulist=menu;

                        msg.obj = c.get(0);
                        handler.sendMessage(msg);

                    case "login":

                        ArrayList<String> login_result = (ArrayList<String>)ois.readObject();
                        if ((login_result.get(0)).equals("fail")){
                            msg.obj = null;
                        }else
                            msg.obj = login_result;
                        handler.sendMessage(msg);

                        break;




                    case "Notice_photo":
                        urilist= (ArrayList<byte[]>)ois.readObject();
                        msg.obj=urilist;
                        handler.sendMessage(msg);

                        break;
                    case "add_notice":

                        break;
                    case "record_list":

                        ArrayList<stt_msg> result = (ArrayList<stt_msg>)ois.readObject();

                        for (int i=0;i<result.size();i++){
                            Log.i("stt_msg",result.get(i).getMsg());
                        }
                        ((TabFragment3)fragment).list=result;

                        break;

                    case "upload_check":

                        ArrayList<String> check=(ArrayList<String>)ois.readObject();
                        ((menu2fragment)fragment).monthArrayList=check;
                        break;

                    case "writing":

                        /*
                        msg.obj = (ArrayList<String>)ois.readObject();
                        handler.sendMessage(msg);
                        break;
                        */
                        break;
                    default:
                        break;


                }


            }catch(Exception e){
                e.printStackTrace();
            }






        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            try{
                if( socket != null && !socket.isClosed()){
                    Log.i("Socket","Closed.");
                    socket.close();
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }


}
