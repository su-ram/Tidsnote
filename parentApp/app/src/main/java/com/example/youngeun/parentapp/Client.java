package com.example.youngeun.parentapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;


public class Client extends Thread {


    private static final String SERVER_IP="gozipds.iptime.org";
    private static final int SERVER_PORT=6666;
    String type;
    private Handler handler;
    private Object object;
    private String string;
    android.os.Message msg=new Message();
    private MainActivity mainActivity;
    private MessageActivity messageActivity;
    private TodayTab todayTab;
    private RequestTab requestTab;
    private ReadFragment readFragment;
    private MonthTab monthTab;
    private CommentDialog commentDialog;
    private Login login;
    private MyFirebaseMessagingService myFirebaseMessagingService;
    private ArrayList<String> datalist;

    Client(Handler handler,String type){

        this.type=type;
        this.handler=handler;
    }

    Client(String type){

        this.type=type;
    }
    Client(MainActivity mainActivity,String type){
        this.mainActivity=mainActivity;
        this.type=type;
    }
    Client(MyFirebaseMessagingService myFirebaseMessagingService,String type){
        this.myFirebaseMessagingService=myFirebaseMessagingService;
        this.type=type;
    }

    Client(ReadFragment readFragment,String type){
        this.readFragment=readFragment;
        this.type=type;
    }
    Client(Login login,String type){
        this.login=login;
        this.type=type;
    }
    Client(RequestTab requestTab,String type){
        this.requestTab=requestTab;
        this.type=type;
    }

    Client(MessageActivity messageActivity,String type){
        this.messageActivity=messageActivity;
        this.type=type;
    }
    Client(TodayTab todayTab,String type){
        this.todayTab=todayTab;
        this.type=type;
    }
    Client(MonthTab monthTab,String type){
        this.monthTab=monthTab;
        this.type=type;
    }
    Client(CommentDialog commentDialog,String type){
        this.commentDialog=commentDialog;
        this.type=type;
    }



    public void setData(Object data){
        object=data;
    }
    public void setData(String data){
        string=data;
    }

    public void setData(ArrayList<String> data){
        datalist=data;
    }
    public void setHandler(Handler handler) {
        this.handler=handler;
    }


    public ArrayList<Bitmap> getImage(ArrayList<byte[]> b){

        ArrayList<Bitmap> result = new ArrayList<>();
        Bitmap bitmap;
        for (int i=0;i<b.size();i++){
            bitmap = BitmapFactory.decodeByteArray(b.get(i),0,b.get(i).length);
            result.add(bitmap);

        }
        return result;
    }

    @Override
    synchronized public void run(){
        Socket socket=null;
        String message="";
        String reply="";

        try{
            socket=new Socket();
            socket.connect(new InetSocketAddress(SERVER_IP,SERVER_PORT));

            OutputStream outputStream=socket.getOutputStream();
            InputStream inputStream=socket.getInputStream();
            ObjectOutputStream oos=new ObjectOutputStream(outputStream);
            ObjectInputStream ois=new ObjectInputStream(inputStream);

            oos.writeObject(type);

            switch (type){

                case "init":
                    Object iData=(Object)object;
                    oos.writeObject(iData);
                    break;
                case "get_message":
                    String str=(String)string;
                    oos.writeObject(str);
                    break;
                case "send_message":
                    Object mData=(Object)object;
                    oos.writeObject(mData);
                    break;

                case "today_comment":
                    Object rData=(Object)object;
                    oos.writeObject(rData);
                    break;
                case "today_notification":
                    Object nData=(Object)object;
                    oos.writeObject(nData);
                    break;
                case "get_mid":
                    String p_id=(String)string;
                case "choose_comment":
                    Object rData2=(Object)object;
                    oos.writeObject(rData2);
                    break;

                case "login":
                    Object lData=(Object)object;
                    oos.writeObject(lData);
                    break;

                case "update_token":
                    String token=(String)string;
                    oos.writeObject(token);
                    break;

                case "get_info":
                    Object gData=(Object)object;
                    oos.writeObject(gData);
                    break;
                case "temp_count":
                    String id=(String)string;
                    oos.writeObject(id);
                    break;
                case "get_temp_message":
                    String id2=(String)string;
                    oos.writeObject(id2);
                    break;

                case "teacher_active":
                    String id3=(String)string;
                    oos.writeObject(id3);
                    break;
                default: break;

            }

            try{
                switch (type) {

                    case "get_message":
                        //todayTab.commentArrayList=(ArrayList<String>)ois.readObject();
                        this.mainActivity.messageList=(ArrayList<messageData>)ois.readObject();

                        for(int i=0; i<this.mainActivity.messageList.size();i++){

                            messageData m=(messageData)this.mainActivity.messageList.get(i);
                            Log.i("ok",m.toString());
                        }
                        break;
                    case "today_comment":
                        this.todayTab.commentArrayList=(ArrayList<String>)ois.readObject();
                        this.todayTab.photolist = getImage((ArrayList<byte[]>)ois.readObject());

                        break;
                    case "today_notification":
                        this.readFragment.todaycommentList=(ArrayList<String>)ois.readObject();
                        break;
                    case "choose_comment":
                        this.commentDialog.commentArrayList2=(ArrayList<String>)ois.readObject();
                        this.commentDialog.photolist = getImage((ArrayList<byte[]>)ois.readObject());
                        break;
                    case "login":
                        this.login.loginArrayList=(ArrayList<String>)ois.readObject();
                        break;
                    case "get_info":
                        this.requestTab.infoArrayList=(ArrayList<String>)ois.readObject();
                        break;
                    case "get m_id":
                        this.mainActivity.midList=(ArrayList<String>)ois.readObject();
                        break;

                    case "lately_mid":
                        this.messageActivity.lately_mid=(ArrayList<String>)ois.readObject();
                        break;

                    case "temp_count":
                        this.messageActivity.temp_count=(ArrayList<String>)ois.readObject();
                        break;

                        default:break;
                    case "get_temp_message":
                        this.messageActivity.tmpMsgList=(ArrayList<messageData>)ois.readObject();
                        break;

                    case "teacher_active":
                        this.messageActivity.teacher_active=(ArrayList<String>)ois.readObject();
                        break;

                }
                }catch (Exception e){
                e.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                if(socket!=null&&!socket.isClosed()){
                    socket.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }



}
