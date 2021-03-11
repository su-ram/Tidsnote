
package com.example.user.tidsnote;
import com.example.user.tidsnote.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.sql.*;
import java.lang.annotation.*;
import java.util.Date;
import javax.lang.model.util.ElementScanner6;
import org.json.simple.JSONObject;

import java.net.HttpURLConnection;
import java.io.OutputStreamWriter;
import java.net.URL;

public class Taking extends Thread{

    Socket client;
    String type, loginid;
    ArrayList<Object> result;
    Student writing;

    Taking(Socket socket){

        this.client=socket;
        type="";

    }

    public void run(){

        try{

            try {

            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());

                type=(String)ois.readObject();
		        System.out.println(type);

                switch(type){

                    case "Notice_photo":
                    result = requestDB(type, (int)ois.readObject());
                    oos.writeObject(result);
                    break;

                    case "student_photos":
                    if((int)ois.readObject() == 0 ){

                        requestDB(type, (ArrayList<byte[]>)ois.readObject());

                    }
                    break;

                    case "parent_photo":
                    result = requestDB(type,type);
                    oos.writeObject(result);
                    break;

                    case "send_all_photo":
                    int size = (int)ois.readObject();
                    if(size != -1){

                        Photo photo = new Photo((ArrayList<byte[]>)ois.readObject(),0);
                        photo.setType((String)ois.readObject());
                        requestDB("common_photos", photo);

                    }
                    break;
                    
                    case "alert_parent":
                    requestDB(type, (Student)ois.readObject());
                    break;

                    case "alert_teacher":
                    requestDB(type, null);
                    break;

                    case "on":
                    requestDB(type, (String)ois.readObject());
                    break;

                    case "off":
                    requestDB(type, (String)ois.readObject());
                    break;

                    case "push":
                    requestDB(type,(String)ois.readObject());
                    break;

                    case "update_token":
                    requestDB(type,(String)ois.readObject());
                    break;

                    case "login":
                    String[] login = (String[])ois.readObject();
                    result = requestDB(type,login);
                    oos.writeObject(result);
                    break;

                    case "init":
                    loginid = (String)ois.readObject();//t_id
                    requestDB("on", loginid);

                    result = requestDB("getStudent", loginid);
                    oos.writeObject(result);//학생 리스트

                    result = requestDB("getNotice", type);
                    oos.writeObject(result);//공지사항 리스트

                    result = requestDB("getRequest",loginid);
                    oos.writeObject(result);

                    result = requestDB("getClass",loginid);
                    oos.writeObject(result);
                    
                    result = requestDB("menu",type);
                    oos.writeObject(result);
                    break;

                    case "record_list":
                    writing = (Student)ois.readObject();
                    result = requestDB(type, writing);
                    oos.writeObject(result);
                    break;

                    case "add_stt":
                    Student newSTT = (Student)ois.readObject();
                    requestDB(type, newSTT);
                    break;

                    case "writing":
                    writing = (Student)ois.readObject();
                    result = requestDB(type, writing);
                    int rowid =0;
                    rowid = (int)result.get(0);

                    if((int)ois.readObject()!=-1){

                    Photo photo = new Photo((ArrayList<byte[]>)ois.readObject(),rowid);
                    requestDB("student_photos", photo);

                    }
                    break;

                    case "add_notice":
                    Notice newNotice = new Notice();
                    newNotice = (Notice)ois.readObject();
                    requestDB(type, newNotice);
                    break;

                    case "send_all":
                    String[] comments = (String[])ois.readObject();
                    requestDB("comment", comments);
                    break;

                    case "new_intro":
                    String[] new_intro = (String[])ois.readObject();
                    requestDB("new_intro",new_intro);
                    break;

                    case "add_student":
                    Student newStudent = (Student)ois.readObject();
                    requestDB(type,newStudent);
                    break;

                    case "upload_pdf":
                        MenuFile newMenuFile=(MenuFile)ois.readObject();
                        requestDB(type,newMenuFile);
                        break;

		    case "upload_check":
                    String newMonth=(String)ois.readObject();
                    result= requestDB("upload_check",newMonth);
                    oos.writeObject(result);
                    System.out.println(result);
                    break;    			
                }
                ois.close();
                oos.close();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Student findStudent(ArrayList<Object> students, String id){

        Student found = null;

        for(int i=0;i<students.size();i++){

            Student temp = (Student)students.get(i);
            if((temp.getStudent_id()).equals(id)){
               
                found=(Student)students.get(i);

            }
        }
        return found;
    }

    public boolean doneInit(Student s){

        String cm = s.getComment();
        int[] st = s.getStatus();

        if(!cm.equals("comment") && !cm.equals("") && st[0] != -1 && st[1] != -1 && st[2] != -1 && cm != null){
            return true;
        }else{
            return false;
        }
    }

    public void requestPush(String token,String request_id){
        //firebase에 push 알림을 보내도록 요청하는 메소드

        try{

            String auth = "AAAASZkKR-Y:APA91bEoM03nSEtuq_JwhxSDr-ebK13cwyIa3dxeG7EJbqblKGdyYw-mNM1x16hLvphdzfbkT4gpjGGm8n46r3_XkBKPXd2HWBXc1xlHvzbyEvnWDf_i4MdaaIm_Rqz1i60PHHNoolTY";

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("to",token);
            jsonObject.put("priority", "high");

            JSONObject data = new JSONObject();
            data.put("body", "");
            data.put("title", "요청이 완료되었습니다. ");
            jsonObject.put("notification", data);

            data = new JSONObject();
            data.put("request_id",request_id);
            jsonObject.put("data",data);
            String result = jsonObject.toString();
            
            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.addRequestProperty("Authorization", "key="+auth); //key값 설정
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type","application/json");
            DataOutputStream wr = new DataOutputStream(con.getOutputStream()); 
            wr.write(result.getBytes("UTF-8"));
            wr.flush(); 
            wr.close();

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

    /*shell command*/
    public void callCommand() throws IOException{

        String cmd="python menu.py";
        Runtime rt=Runtime.getRuntime();
        Process p=rt.exec(cmd);
        System.out.println("cdm ok");}

   /*convert PDF*/
    public void SavePDF(String file_name,byte[] bytes){

           String filePath="/root/suram/menufiles/"+file_name+".pdf";
           File pdfFile=new File(filePath);
           try{
                   FileOutputStream fos=new FileOutputStream(pdfFile);
                   fos.write(bytes);
                   fos.flush();
                   fos.close();
                   System.out.println("convert pdf");
           }catch(Exception e){
                   e.printStackTrace();
            }
   }

    public ArrayList<Object> requestDB(String type, Object object){


        Connection conn = null;
        Statement stmt = null;
        ResultSet resultSet = null;
        PreparedStatement pstmt = null;
        String sql = "";
        java.sql.Timestamp sqldate;
        int pos=0, rowid =0;
        ArrayList<Object> result=new ArrayList<>();
        Student student;
        String id;
        File file;

        conn = connectDB(conn);

        try{
            switch (type) {

                case "Notice_photo":
                rowid = (int)object;
                sql = "select * from images where type = 'n' and id = (?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,rowid);
                resultSet = pstmt.executeQuery();

                Blob nphoto = null;
                int length =0 ;
				byte[] bytearray = null;

                while(resultSet.next()){

                    nphoto = resultSet.getBlob("data");
                    length = (int)nphoto.length();
                    bytearray = nphoto.getBytes(1, length);
                    nphoto.free();
                    result.add(bytearray);
                }
                break;

                case "student_photos":

                stmt = conn.createStatement();
                Photo photodata = (Photo)object;
                ArrayList<byte[]> bytelist = photodata.getBytes();
                rowid = photodata.getRowid();     

                for(int i=0;i<bytelist.size();i++){
                    sql = "insert into images values(0,?,now(),'s',?)";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setBytes(1,bytelist.get(i));
                    pstmt.setInt(2, rowid);
                    pstmt.executeUpdate();
                    }

                break;

                case "common_photos":
                stmt = conn.createStatement();
                photodata = (Photo)object;
                bytelist = photodata.getBytes();

                for(int i=0;i<bytelist.size();i++){
                    sql = "insert into images values(0,?,now(),?,?)";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setBytes(1,bytelist.get(i));
                    pstmt.setString(2, photodata.getType());
                    pstmt.setInt(3, 0);
                    pstmt.executeUpdate();
                    }
                break;
                
                case "get_photo":
                InputStream in;
                stmt = conn.createStatement();
                sql = "select * from images";
                resultSet = stmt.executeQuery(sql);

                if(resultSet.next()){
                    Blob blob = resultSet.getBlob("data");
                InputStream inputStream = blob.getBinaryStream();
                OutputStream outputStream = new FileOutputStream("/home/suram/getphoto_result.jpg");
 
                int bytesRead = -1;
                byte[] buffer = new byte[4096];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
 
                inputStream.close();
                outputStream.close();

                }
                break;

                case "add_photo":
                file = (File)object;
                sql = "insert into images values(0,?,now())";
                pstmt = conn.prepareStatement(sql);
                pstmt.setBinaryStream(1,new FileInputStream(file),(int)file.length());
                pstmt.executeUpdate();
                break;

                case "on":
                id=(String)object;
                stmt = conn.createStatement();
                sql = "update teacher_tbl set active=1 where t_id = '"+id+"'";
                stmt.executeUpdate(sql);
                break;

                case "off":
                id=(String)object;
                stmt = conn.createStatement();
                sql = "update teacher_tbl set active=0 where t_id = '"+id+"'";
                stmt.executeUpdate(sql);
                break;

                case "alert_parent":
                stmt = conn.createStatement();
                sql = "select token from push where who = 'parent' order by rownum desc limit 1";
                resultSet = stmt.executeQuery(sql);

                if(resultSet.next()){
                    requestPush(resultSet.getString("token"),(String)object);
                }

                break;

                case "alert_teacher":
                stmt = conn.createStatement();
                sql = "select token from push where who = 'teacher' order by rownum desc limit 1";
                resultSet = stmt.executeQuery(sql);

                if(resultSet.next()){
                    requestPush(resultSet.getString("token"),null);
                }

                break;

                case "push":
                stmt = conn.createStatement();
                sql = "select * from push where who = 'parent' order by rownum desc limit 1";
                resultSet = stmt.executeQuery(sql);

                if(resultSet.next()){
                    requestPush(resultSet.getString("token"),(String)object);
                }

                sql = "update message_tbl set done=1 where m_id = (?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, (String)object);
                pstmt.executeUpdate();
                break;

                case "update_token":
                    sql = "insert into push values (0,?)";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, (String)object);
                    pstmt.executeUpdate();                  

                break;

                    case "login":

                    String login[] = (String[])object;
                    stmt = conn.createStatement();
                    sql = "select aes_decrypt(unhex(pw), 'suram') as pw from login where id = '"+login[0]+"'";
                    resultSet = stmt.executeQuery(sql);

                    if(resultSet.next()){

                        if(login[1].equals(resultSet.getString("pw"))){

                            stmt = conn.createStatement();
                            sql = "select * from teacher_tbl where t_id = '"+login[0]+"'";
                            resultSet = stmt.executeQuery(sql);
                            resultSet.next();
                            
                            result.add(login[0]);
                            result.add(resultSet.getString("t_name"));
                            result.add(resultSet.getString("t_pnum"));

                            stmt = conn.createStatement();
                        
                            sql = "select count(*) as sum from student_tbl";
                            resultSet = stmt.executeQuery(sql);
                            resultSet.next();
                            result.add(Integer.toString(resultSet.getInt("sum")));

                        }else{
                            result.add("fail");
                        }
                }else{
                    result.add("fail");
                }
                    break;

                    case "getStudent":

                        stmt = conn.createStatement();
                        id=(String)object;
                        
                        sql = "select * from student_tbl as s, class_tbl as c, teacher_tbl as t "+
                        "where c.c_id = s.c_id and c.t_id = t.t_id and t.t_id = '"+object+
                        "' order by s.s_id";
                        resultSet = stmt.executeQuery(sql);

                        while(resultSet.next()){

                            student = new Student();
                            student.setStudent_id(resultSet.getString("s.s_id"));
                            student.setStudent_name(resultSet.getString("s.s_name"));
                            student.setPos(pos++);
                            student.setClass_id(resultSet.getString("s.c_id"));
                            student.setGender(resultSet.getInt("s.s_sex"));
                            student.setPhone(resultSet.getString("s.s_pnum"));
                            student.setAge(resultSet.getInt("s.s_age"));
                            result.add(student);
                            }

                            sql = "select * from voice_tbl where v_time > curdate()";
                            resultSet = stmt.executeQuery(sql);

                            while(resultSet.next()){

                                id = resultSet.getString("s_id");
                                student = findStudent(result, id);

                                if(student != null){
                                    student.addStt_message(resultSet.getString("v_cont"));
                                }
                            }

                            stmt = conn.createStatement();
                            sql = "select * from comment_tbl where c_date > curdate()";
                            resultSet = stmt.executeQuery(sql);

                            while(resultSet.next()){

                                id = resultSet.getString("s_id");
                                student = findStudent(result, id);

                                if(student != null){

                                    student.setComment(resultSet.getString("c_comment"));
                                    student.setCommon(resultSet.getString("c_same"));
                                    student.setStatus(resultSet.getInt("c_health"), resultSet.getInt("c_meal"), resultSet.getInt("c_feel"));

                                    if(doneInit(student)){
                                        //이미 모두 입련한 경우라면,
                                        student.setDone(true);
                                    }
                                }
                            }
                            //학부모 메세지 추가
                            stmt = conn.createStatement();
                            ResultSet temp;
                            sql = "select * from comment_tbl where c_date > curdate()";
                            resultSet = stmt.executeQuery(sql);

                            for(int i=0;i<result.size();i++){

                                student = (Student)result.get(i);
                                id = student.getStudent_id();
                                stmt = conn.createStatement();
                                sql = "select m.m_cont "+
                                "from message_tbl as m, parent_tbl as p, student_tbl as s "+
                                "where m.p_id = p.p_id and s.s_id = p.s_id and m.final = 1 and s.s_id = '"+id+"' order by m.m_date desc limit 1";
                                temp = stmt.executeQuery(sql);

                                if(temp.next()){
                                    student.setParent_message(temp.getString("m.m_cont"));
                                }
                            }
                        break;

                        case "menu":
                        stmt = conn.createStatement();
                        sql = "select * from menu_tbl where m_date = curdate()";
                        resultSet = stmt.executeQuery(sql);

                        if(resultSet.next()){

                        String menu [] = (resultSet.getString("m_menu")).split(",");

                        for(int i=0;i<menu.length;i++){
                            result.add(menu[i]);
                        }

                    }else{
                        result.add(null);
                    }
                        break;

                        case "new_intro":

                        String[] new_intro = (String[])object;
                        sql = "update class_tbl set c_intro = (?) where t_id = (?)";
                                    pstmt = conn.prepareStatement(sql);
                                    pstmt.setString(1, new_intro[1]);
                                    pstmt.setString(2, new_intro[0]);
                                    pstmt.executeUpdate();

                        break;

                        case "getClass":
                        id = (String)object;
                        stmt = conn.createStatement();
                        sql = "select * from class_tbl where t_id = '"+id+"'";
                        resultSet = stmt.executeQuery(sql);
                        resultSet.next();

                        Class_ c = new Class_();
                        c.setIntro(resultSet.getString("c_intro"));
                        c.setT_id(resultSet.getString("t_id"));
                        c.setC_id(resultSet.getString("c_id"));
                        c.setAge(resultSet.getInt("c_age"));
                        c.setC_name(resultSet.getString("c_name"));

                        stmt = conn.createStatement();
                        sql = "select count(*) as sum from student_tbl where c_id = '"+c.getC_id()+"'";
                        resultSet = stmt.executeQuery(sql);
                        resultSet.next();
                        c.setSum(resultSet.getInt("sum"));

                        stmt = conn.createStatement();
                        sql = "select t.t_name from teacher_tbl as t, class_tbl as c where c.t_id = t.t_id and c.c_id = '"+c.getC_id()+"'";
                        resultSet = stmt.executeQuery(sql);
                        resultSet.next();
                        c.setT_name(resultSet.getString("t.t_name"));
                        result.add(c);

                        break;

                        case "record_list":

                        stt_msg val;
                        student = (Student)object;
                        id = student.getStudent_id();
                        stmt = conn.createStatement();
                        sql = "select m.m_cont, m.m_date "+
                                "from message_tbl as m, parent_tbl as p, student_tbl as s "+
                                "where m.p_id = p.p_id and s.s_id = p.s_id and s.s_id = '"+id+"' order by m.m_date desc";
                        temp = stmt.executeQuery(sql);

                                while(temp.next()){
                                    val = new stt_msg();
                                    val.setMsg(temp.getString("m.m_cont"));
                                    val.setDate(temp.getDate("m.m_date"));
                                    result.add(val);
                                }
                        student = (Student)object;
                        break;

                        case "add_stt":

                        Student stt= (Student)object;
                        stmt = conn.createStatement();
                        sql = "select t.t_id from teacher_tbl as t, class_tbl as c "+
                        "where c.t_id = t.t_id and c.c_id = '"+stt.getClass_id()+"'";
                        resultSet = stmt.executeQuery(sql);
                        resultSet.next();

                        String t_id = resultSet.getString("t.t_id");
                        sqldate = new java.sql.Timestamp(stt.getDate().getTime());

                        sql = "insert into voice_tbl values(?,?,?,?,?)";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setInt(1,0);
                        pstmt.setString(2, stt.showLast());
                        pstmt.setString(3, t_id);
                        pstmt.setString(4,stt.getStudent_id());
                        pstmt.setTimestamp(5, sqldate);
                        pstmt.executeUpdate();

                        break;

                    case "writing"://임시저장

                        Student writing = (Student)object;
                        stmt = conn.createStatement();
                        sql = "select * from comment_tbl where c_date > curdate() and s_id ='"+writing.getStudent_id()+"'";
                        resultSet = stmt.executeQuery(sql);

                        if(resultSet.next()){

                            rowid = resultSet.getInt("c_id");
                            result.add(rowid);

                            sql = "update comment_tbl set c_feel = ?, c_meal = ?, c_health=?, c_comment='"+writing.getComment()+"', c_same = '"+writing.getCommon()+"' where c_date > curdate() and s_id = '"
                            +writing.getStudent_id()+"'";

                            pstmt = conn.prepareStatement(sql);
                            pstmt.setInt(1, writing.getStatus()[2]);
                            pstmt.setInt(2, writing.getStatus()[1]);
                            pstmt.setInt(3, writing.getStatus()[0]);
                            pstmt.executeUpdate();

                        }else{

                            sqldate = new java.sql.Timestamp(writing.getDate().getTime());

                        sql = "insert into comment_tbl values(?,?,?,?,?,?,?,?,?)";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setInt(1, 0);
                        pstmt.setInt(2, writing.getStatus()[2]);
                        pstmt.setInt(3, writing.getStatus()[1]);
                        pstmt.setInt(4, writing.getStatus()[0]);
                        pstmt.setString(5,writing.getComment());
                        pstmt.setString(6, writing.getCommon());
                        pstmt.setTimestamp(7, sqldate);
                        pstmt.setString(8,writing.getStudent_id());
                        pstmt.setInt(9,0);
                        pstmt.executeUpdate();

                        stmt = conn.createStatement();
                        sql = "select * from comment_tbl where c_date > curdate() and s_id ='"+writing.getStudent_id()+"'";
                        resultSet = stmt.executeQuery(sql);

                        if(resultSet.next()){
                        
                            rowid = resultSet.getInt("c_id");
                            result.add(rowid);

                        }
                        }

                    break;

                    case "add_student":

                    Student newStudent = (Student)object;
                    stmt = conn.createStatement();
                    sql = "select t_id from class_tbl where c_id = '"+newStudent.getClass_id()+"'";
                    resultSet = stmt.executeQuery(sql);
                    resultSet.next();
                    String ID = resultSet.getString("t_id");
                    
                    sql = "insert into student_tbl (s_id,s_name,s_age,s_sex,s_pnum,s_memo,c_id,t_id)values (?,?,?,?,?,?,?,?)";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, newStudent.getStudent_id());
                    pstmt.setString(2, newStudent.getStudent_name());
                    pstmt.setInt(3, newStudent.getAge());
                    pstmt.setInt(4, newStudent.getGender());
                    pstmt.setString(5, newStudent.getPhone());
                    pstmt.setString(6, "");
                    pstmt.setString(7, newStudent.getClass_id());
                    pstmt.setString(8, ID);
                    pstmt.executeUpdate();

                        break;

                      /*upload_file*/
                    case "upload_pdf":

                           MenuFile newMenuFile=(MenuFile)object;
                           sql="insert into upload(file_name,pdf_file) values(?,?)";
                           pstmt=conn.prepareStatement(sql);
                           pstmt.setString(1,newMenuFile.getFileName());
                           pstmt.setBytes(2,newMenuFile.getPdfFile());
                           pstmt.executeUpdate();

                            try{
                                    SavePDF(newMenuFile.getFileName(),newMenuFile.getPdfFile());

                           }catch(Exception e){
                                    e.printStackTrace();}

                            try{
                                    callCommand();
                            }catch(IOException ioe){
                                    ioe.printStackTrace();
                            }

                           break;

	            case "upload_check":

                           id=(String)object;
                           System.out.println("object:"+id);
                           stmt=conn.createStatement();
                           sql="select file_name from upload where file_name='"+id+"' and rcv_python='1';";
                           resultSet = stmt.executeQuery(sql);

                           if(resultSet.next()){
                                    result.add("1");

                           }else{
                                   result.add("0");

                           }

                           break;		   
                    case "add_notice":

                            Notice newnotice = (Notice)object;
                            sqldate = new java.sql.Timestamp(newnotice.getDate().getTime());
                            sql = "insert into notice_tbl values(?,?,?,?,?,?)";
                            pstmt = conn.prepareStatement(sql);
                            pstmt.setInt(1, 0);
                            pstmt.setTimestamp(2, sqldate);
                            pstmt.setString(3,newnotice.getWriter());
                            pstmt.setString(4, newnotice.getT_id());
                            pstmt.setString(5,newnotice.getTitle());
                            pstmt.setString(6, newnotice.getContent());
                            pstmt.executeUpdate();
                            
                            Blob b = conn.createBlob();
                            ArrayList<byte[]> bytes = newnotice.getPhotos();
                            stmt = conn.createStatement();

                            sql = "select n_id from notice_tbl order by n_id desc limit 1";
                            resultSet = stmt.executeQuery(sql);
                            if(resultSet.next()){
                                rowid= resultSet.getInt("n_id");
                             }

                            for(int i=0;i<bytes.size();i++){
                                sql = "insert into images values(0,?,now(),   'n',?)";
                                pstmt = conn.prepareStatement(sql);
                                pstmt.setBytes(1,bytes.get(i));
                                pstmt.setInt(2, rowid);
                                pstmt.executeUpdate();
                            }

                        break;

                        case "getNotice":
                        stmt = conn.createStatement();
                        sql = "select * from notice_tbl order by n_date desc";
                        resultSet = stmt.executeQuery(sql);

                        Notice notice;
                        while(resultSet.next()){

                            notice = new Notice();
                            notice.setContent(resultSet.getString("n_content"));

                            sqldate = resultSet.getTimestamp("n_date");
                            Date newDate = new Date(sqldate.getTime());
                            notice.setDate(newDate);
		  	                notice.setWriter(resultSet.getString("n_writer"));
			                notice.setT_id(resultSet.getString("t_id"));
			                notice.setTitle(resultSet.getString("n_title"));
                            notice.setRowid(resultSet.getInt("n_id"));

                            result.add(notice);

                            }
                            
                    break;

                    case "getRequest":

                    id = (String)object;
                    stmt = conn.createStatement();
                    sql = "select * from class_tbl where t_id = '"+id+"'";
                    resultSet = stmt.executeQuery(sql);
                    resultSet.next();
                    id = resultSet.getString("c_id");

                    stmt = conn.createStatement();
                    sql = "select m.m_date, m.m_req, s.s_name, s.s_id, m.m_id "
                    +"from message_tbl as m, parent_tbl as p, student_tbl as s "
                    +"where m.p_id = p.p_id and p.s_id = s.s_id and s.c_id = '"+id
                    +"' and m.done = 0 and m.final = 1 and m.m_date >= date_add(curdate(), interval -1 day)"+" order by m_date desc";
                
                    resultSet = stmt.executeQuery(sql);

                    Request request;

                    while(resultSet.next()){

                        request = new Request();
                        request.setContent(resultSet.getString("m_req"));
                        request.setDate(resultSet.getDate("m_date"));
                        request.setStu_name(resultSet.getString("s_name"));
                        request.setStu_id(resultSet.getString("s_id"));
                        request.setRequest_id(Integer.toString(resultSet.getInt("m_id")));
                        result.add(request);

                    }
                    break;

                    case "comment":

                        String[] common = (String[])object;
                        sql = "update comment_tbl as c inner join student_tbl as s "+
                        "on c.s_id = s.s_id set c.final = 1, c.c_same = '"+common[1]+
                        "' where c.c_date > curdate() and s.c_id = '"+common[0]+"'";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.executeUpdate();
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
