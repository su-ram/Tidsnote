package com.example.youngeun.parentapp;

import java.text.SimpleDateFormat;
import com.example.youngeun.parentapp.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.*;
import java.text.SimpleDateFormat;



public class Server_Thread extends Thread{

	Socket client;
	String type = "";
	ArrayList<String> result;
	ArrayList<Object> obj_result;
	String str_result;
	int int_result;
    Server_Thread(Socket socket){
        this.client=socket;
	type="";
    
    }

    public void run(){
    
   
	    try{



            System.out.println("make client!");
            InputStream inputStream = client.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(inputStream);
            OutputStream outputStream = client.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);

	    try{
	    type=(String)ois.readObject();
           // ArrayList<String> result=new ArrayList<String>();
            System.out.println(type);

                ArrayList<String> inputs=new ArrayList<>();
                String input="";
                switch(type){

			case "init":
				//messageTab setting
				break;
                        case "today_comment":
				CommentRequest rData=new CommentRequest();
                                rData=(CommentRequest)ois.readObject();
                                result=selectDB(0,rData);
								oos.writeObject(result);
								SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
								String curDate=s.format(new java.util.Date());
								rData.setC_date(curDate);
			
								obj_result = getPhoto("get_Photos", rData);
																

								oos.writeObject(obj_result);
                                break;
			 case "today_notification":      
                                CommentRequest nData=new CommentRequest();
                                nData=(CommentRequest)ois.readObject();
                                result=selectDB(6,nData);
			        oos.writeObject(result);
				System.out.println(result);
			        break;	
                        case "choose_comment":
                               CommentRequest rData2=new CommentRequest();
			       rData2=(CommentRequest)ois.readObject();
                                result=selectDB(1,rData2);
								oos.writeObject(result);
								obj_result = getPhoto("get_Photos", rData2);
								oos.writeObject(obj_result);
                                break;
			case "send_message": 
				 messageData mData=new messageData();
				 mData=(messageData)ois.readObject();	
                                 insertDB(2,mData);
                                 break;
		        case "get_message":
				 String s_id=new String();
				 s_id=(String)ois.readObject();
				 System.out.println("get_messge's s_id:"+s_id);
				 obj_result=selectDB_obj(0,s_id);
				 oos.writeObject(obj_result);
				 System.out.println(obj_result);
				 break;
			case "login":
			         loginData lData=new loginData();
				 lData=(loginData)ois.readObject();
				 System.out.println(lData);
				 result=selectDB(4,lData);
				 
				 oos.writeObject(result);

				 System.out.println("datalist:"+result);
				 break;
			case "get_info":
				 CommentRequest gData=new CommentRequest();
				 gData=(CommentRequest)ois.readObject();
				 result= selectDB(5,gData);
				 oos.writeObject(result);
				 break;
			case "update_token":
				 String token=new String();
				 token=(String)ois.readObject();
				 System.out.println(token);
				 sendTK(0,token);
				 break;
		       case "get_mid":
		                 String p_id=new String();
		                 p_id=(String)ois.readObject();
		                 result=selectDB_str(0,p_id);
				 oos.writeObject(result);
				 System.out.println(result);
		                 break;
		       case "lately_mid":
		                 result=selectDB_str(1,"");
		                 System.out.println(result);
				 oos.writeObject(result);
		                 break;
		       case "temp_count":
		                 String id=new String();
		                 id=(String)ois.readObject();
		                 result=selectDB_str(2,id);
		                 oos.writeObject(result);
				 System.out.println(result);
		                 break;		 
		       case "get_temp_message":
	         	         String p_id2=new String();
		                 p_id2=(String)ois.readObject();
		                 obj_result=selectDB_obj(1,p_id2);
		                 oos.writeObject(obj_result);
				 System.out.println(obj_result);
		                 break;
		      case "teacher_active":
		                String id3=new String();
	                        id3=(String)ois.readObject();
	                        result=selectDB_str(3,id3);
	                        oos.writeObject(result);
	                        break;			
        }
            }catch(ClassNotFoundException e){
            e.printStackTrace();}

	}catch(Exception e){
            e.printStackTrace();
    }

    
    }

    
    public static String state2String(String i){
    
    
	    String rsString="";
     	    
	    switch(i){
		    case "0":
			    rsString="좋음";
			    break;
	            case "1":
			    rsString="보통";
			    break;
		    case "2":
			    rsString="나쁨";
			    break;
		    default:
			    rsString="보통";
			    break;
	    }

	    return rsString;


    }

   
    public void sendTK(int type, String token){
   
	     Connection conn=null;
            Statement stmt=null;
            ResultSet rs,rs2,rs3,rs4,rs5=null;
            PreparedStatement pstmt=null;
            ArrayList<String> datalist=new ArrayList<>();
            String sql="";

            try{

                    Class.forName("com.mysql.cj.jdbc.Driver");
                    conn=DriverManager.getConnection("jdbc:mysql://localhost/tidsnotedb?characterEncoding=UTF-8"+"&user=suram&password=suram");
                    stmt=conn.createStatement();

                    switch(type){

			    case 0://요청완료 토큰저장
				    String tk=(String)token;
				    sql="insert into push(rownum,token) values(0,'";
				    sql=sql+tk+"');";

				    rs=stmt.executeQuery(sql);
				    break;

		    }
	    }catch(Exception e){
                    e.printStackTrace();
   
    }

    
    }
 
    public static ArrayList<String> selectDB_str(int type, String string){


            Connection conn=null;
            Statement stmt=null;
            ResultSet rs,rs2,rs3,rs4,rs5=null;
            PreparedStatement pstmt=null;
            ArrayList<String> datalist=new ArrayList<>();
            String sql="";

            try{

                    Class.forName("com.mysql.cj.jdbc.Driver");
                    conn=DriverManager.getConnection("jdbc:mysql://localhost/tidsnotedb?characterEncoding=UTF-8"+"&user=suram&password=suram");
                    stmt=conn.createStatement();

                    switch(type){
			    case 0:
                                    String p_id=(String)string;
                                    sql="select m_id from message_tbl where p_id='"+p_id+"';";
                                    rs=stmt.executeQuery(sql);

                                    while(rs.next()){

                                           
                                            datalist.add(rs.getString("m_id"));
                                            System.out.println(rs.getString("m_id"));
				    }
				    break;
                           case 1:
				    sql="select MAX(m_id) from message_tbl;";
				    rs=stmt.executeQuery(sql);

				    if(rs.next()){
                                       String str_mid;
				       int int_mid=rs.getInt("MAX(m_id)");
				       str_mid=String.valueOf(int_mid);
				       datalist.add(str_mid);
				    }
				    break;
		          case 2://임시저장개수

                                    String tmp_id=(String)string;
				    System.out.println("case2: p_id"+tmp_id);
                                    sql="select count(*) from message_tbl where p_id='"+tmp_id+"' and final=0;";
                                    rs=stmt.executeQuery(sql);

                                    if(rs.next()){
					    String str_count;
					    int int_count=rs.getInt("count(*)");
					    str_count=String.valueOf(int_count);
                                            datalist.add(str_count);
                                    }
                                    break;		    

		 	  case 3:

			           String id3=(String)string;
		                   sql="select active from teacher_tbl where t_id=(select t_id from student_tbl where s_id='"+id3+"');";

		                   rs=stmt.executeQuery(sql);
		                   if(rs.next()){
					   String t_id=rs.getString("active");
					   datalist.add(t_id);
				   }		   
				   break;
		    }
	    }catch(Exception e){
                    e.printStackTrace();
            }

	    return datalist;
	}
	
	public static ArrayList<Object> getPhoto(String type, Object object){

		Connection conn=null;
	    Statement stmt=null;
		ResultSet rs=null;
	
	    PreparedStatement pstmt=null;
		ArrayList<Object> result=new ArrayList<>();
		String sql="";
		int rowid=-1;
		
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
		    conn=DriverManager.getConnection("jdbc:mysql://localhost/tidsnotedb?characterEncoding=UTF-8"+"&user=suram&password=suram");
			stmt=conn.createStatement();
			
			switch(type){
				case "get_Photos":


				CommentRequest rData=(CommentRequest)object;
				SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
				java.sql.Date newdate =  java.sql.Date.valueOf(rData.getC_date());

				Calendar cal = Calendar.getInstance();
				cal.setTime(newdate);
				cal.add(Calendar.DATE, 1);
				


	    /*
				    sql="select C.c_id, C.final,S.s_name,C.c_date,";
		                    sql=sql+"C.c_feel,C.c_meal,C.c_health,C.c_comment,C.c_same";
				    sql=sql+" from student_tbl S,comment_tbl C ";
				    sql=sql+"where C.s_id='"+rData.getS_id()+"' and S.s_id=C.s_id and";
				    sql=sql+" C.c_date>C";//m_menu  
					rs=stmt.executeQuery(sql);
					*/
				sql="select C.c_id, C.final,S.s_name,C.c_date, C.c_feel,C.c_meal,C.c_health,C.c_comment,C.c_same";
				sql=sql+" from student_tbl S,comment_tbl C ";
				sql=sql+"where C.s_id=(?) and S.s_id=C.s_id and C.c_date>=(?) and C.c_date < (?)";//m_menu 
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, rData.getS_id());
				pstmt.setDate(2, newdate);
				java.sql.Date tomorrow = java.sql.Date.valueOf(transFormat.format(cal.getTime()));
				pstmt.setDate(3, tomorrow);
				rs=pstmt.executeQuery();

				ResultSet rs2 = null;
				Blob photo = null;
                int length =0 ;
				byte[] bytearray = null;
				
					if(rs.next() ){
						rowid = rs.getInt("C.c_id");
						System.out.println(rowid);
						sql = "select * from images where type = 's' and id = (?)";
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1,rowid);
						rs2=pstmt.executeQuery();
						
				
					while(rs2.next()){

						photo = rs2.getBlob("data");
                    	length = (int)photo.length();
                    	bytearray = photo.getBytes(1, length);
                    	photo.free();
						result.add(bytearray);
			
					}
					
					}

					
					sql = "select c_id from student_tbl where s_id = '"+rData.getS_id()+"'";
					rs=stmt.executeQuery(sql);
					System.out.println(rData.getS_id());
					if(rs.next()){
						String cid = rs.getString("c_id");
											System.out.println(cid);

					
						sql = "select * from images where type = (?) and time >= (?) and time < (?)";
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1,cid);
						pstmt.setDate(2,newdate);
						pstmt.setDate(3,tomorrow);
						rs2=pstmt.executeQuery();

						while(rs2.next()){
							photo = rs2.getBlob("data");
                    		length = (int)photo.length();
                    		bytearray = photo.getBytes(1, length);
                    		photo.free();
							result.add(bytearray);
						}




						
					}

				

				break;
			}
		}catch (Exception e){

		}


		return result;

	}
    public static ArrayList<String> selectDB(int type, Object object){
   
   
	    Connection conn=null;
	    Statement stmt=null;
	    ResultSet rs,rs2,rs3,rs4,rs5,mn1,mn2,mn3=null;
	    PreparedStatement pstmt=null;
		ArrayList<String> datalist=new ArrayList<>();
	    String sql="";

	    try{
	    
		    Class.forName("com.mysql.cj.jdbc.Driver");
		    conn=DriverManager.getConnection("jdbc:mysql://localhost/tidsnotedb?characterEncoding=UTF-8"+"&user=suram&password=suram");
		    stmt=conn.createStatement();

		    switch(type){
		   
			    
			    case 0://코멘트 조회(새로고침)-당일
				    CommentRequest rData=(CommentRequest)object;
	    
				    sql="select C.final,S.s_name,C.c_date,";
		                    sql=sql+"C.c_feel,C.c_meal,C.c_health,C.c_comment,C.c_same";
				    sql=sql+" from student_tbl S,comment_tbl C ";
				    sql=sql+"where C.s_id='"+rData.getS_id()+"' and S.s_id=C.s_id and";
				    sql=sql+" C.c_date>CURRENT_DATE();";//m_menu 
				    rs=stmt.executeQuery(sql);


				    if(!rs.next()){//교사가 코멘트를 작성하지 않았을 경우
					   System.out.println("today's comment not yet..");
				    
					    sql="select S.s_name";
                                    sql=sql+" from student_tbl S ";
                                    sql=sql+"where S.s_id='"+rData.getS_id()+"';";
                                    rs4=stmt.executeQuery(sql);

				   if(rs4.next()){
					   String name=rs4.getString("s_name");
					   SimpleDateFormat simpleformat=new SimpleDateFormat("yyyy-MM-dd");
					   String date=simpleformat.format(new java.util.Date());
					   
                                        datalist.add(name);
				        datalist.add(date);
                                    datalist.add("");
                                    datalist.add("");
                                    datalist.add("");
                                   // datalist.add(m_menu);
                                    datalist.add(" 작성되지 않았습니다.");
                                    datalist.add("");
				   } 
				     sql="select t_name from teacher_tbl where t_id=";
                                    sql=sql+"(select t_id from student_tbl where s_id='"+rData.getS_id()+"');";

                                    rs2=stmt.executeQuery(sql);

                                    if(rs2.next()){
                                            String t_name=rs2.getString("t_name");
                                            datalist.add(t_name);
                                 }

                                 sql="select c_name from class_tbl,comment_tbl where s_id='"+rData.getS_id()+"';";
                                  rs3=stmt.executeQuery(sql);

                                    if(rs3.next()){
                                            String t_name=rs3.getString("c_name");
                                            datalist.add(t_name);
                                 }


				 sql="select m_menu from menu_tbl where m_date=CURRENT_DATE();";
                                 mn1=stmt.executeQuery(sql);

                                 if(mn1.next()){
                                         String menu1=mn1.getString("m_menu");
                                         datalist.add(menu1);
                                 }else{
				        datalist.add("정보없음");
				  }


					   break;

				 }else{ //교사가 코멘트를 작성했을 경우
			
				 if (rs.getInt("final")!=0){	 
			           // String s_id=rs.getString("s_id");
				    String name=rs.getString("s_name");
				    String date=rs.getString("c_date");
				   // String t_name=rs.getString("t_name");
                                   // String class_name=rs.getString("c_name");
                                    String c_feel=state2String(rs.getString("c_feel"));
                                    String c_meal=state2String(rs.getString("c_meal"));
                                    String c_health=state2String(rs.getString("c_health"));
                                    String c_comment=rs.getString("c_comment");
                                    String c_same=rs.getString("c_same");

				   				    // String m_menu=rs.getString("m_menu");

				   // datalist.add(s_id);//s_id
                                    datalist.add(name);
				    datalist.add(date);
                                   // datalist.add(class_name);
				   // datalist.add(t_name);
                                    datalist.add(c_feel);
                                    datalist.add(c_meal);
                                    datalist.add(c_health);
				   // datalist.add(m_menu);
                                    datalist.add(c_comment);
                                    datalist.add(c_same);

				     sql="select t_name from teacher_tbl where t_id=";
                                    sql=sql+"(select t_id from student_tbl where s_id='"+rData.getS_id()+"');";

                                    rs2=stmt.executeQuery(sql);

				    if(rs2.next()){
					    String t_name=rs2.getString("t_name");
					    datalist.add(t_name);
				 }

				 sql="select c_name from class_tbl,comment_tbl where s_id='"+rData.getS_id()+"';";
				  rs3=stmt.executeQuery(sql);

                                    if(rs3.next()){
                                            String t_name=rs3.getString("c_name");
                                            datalist.add(t_name);
                                 }

				    sql="select m_menu from menu_tbl where m_date=CURRENT_DATE();";
                                 mn2=stmt.executeQuery(sql);

                                 if(mn2.next()){
                                         String menu2=mn2.getString("m_menu");
                                         datalist.add(menu2);
                                 }else{
                                        datalist.add("정보없음");
                                  }

				 }else{
					// String s_id=rs.getString("s_id");
                                    String name=rs.getString("s_name");
                                    String date=rs.getString("c_date");
                                    String c_feel="";
                                    String c_meal="";
                                    String c_health="";
                                    String c_comment="작성 중입니다.";
                                    String c_same="";

                                                                    // String m_menu=rs.getString("m_menu");

                                   // datalist.add(s_id);//s_id
                                    datalist.add(name);
                                    datalist.add(date);
                                   // datalist.add(class_name);
                                   // datalist.add(t_name);
                                    datalist.add(c_feel);
                                    datalist.add(c_meal);
                                    datalist.add(c_health);
                                   // datalist.add(m_menu);
                                    datalist.add(c_comment);
                                    datalist.add(c_same);

                                     sql="select t_name from teacher_tbl where t_id=";
                                    sql=sql+"(select t_id from student_tbl where s_id='"+rData.getS_id()+"');";

                                    rs2=stmt.executeQuery(sql);

                                    if(rs2.next()){
                                            String t_name=rs2.getString("t_name");
                                            datalist.add(t_name);
                                 }

                                 sql="select c_name from class_tbl,comment_tbl where s_id='"+rData.getS_id()+"';";
                                  rs3=stmt.executeQuery(sql);

                                    if(rs3.next()){
                                            String t_name=rs3.getString("c_name");
                                            datalist.add(t_name);
                                 }
	 
				    sql="select m_menu from menu_tbl where m_date=CURRENT_DATE();";
                                 mn3=stmt.executeQuery(sql);

                                 if(mn3.next()){
                                         String menu3=mn3.getString("m_menu");
                                         datalist.add(menu3);
                                 }else{
                                        datalist.add("정보없음");
                                  }
			 	 break;
				 }
				 }

	
			    case 1://월별 코멘트
				    CommentRequest rData2=(CommentRequest)object;
				    sql="select C.final,S.s_name,C.c_date,";
                                    sql=sql+"C.c_feel,C.c_meal,C.c_health,C.c_comment,C.c_same";
                                    sql=sql+" from student_tbl S,comment_tbl C ";
                                    sql=sql+"where C.s_id='"+rData2.getS_id()+"' and S.s_id=C.s_id and";
                                    sql=sql+" C.c_date>'"+rData2.getC_date()+"'and C.c_date<'"+rData2.getN_date()+"';";//m_menu
                                    rs=stmt.executeQuery(sql);

				    

                                    if(!rs.next()){//교사가 코멘트를 작성하지 않았을 경우
                                           System.out.println("today's comment not yet.."+rData2.getC_date());
                                 
					     sql="select S.s_name";
                                    sql=sql+" from student_tbl S ";
                                    sql=sql+"where S.s_id='"+rData2.getS_id()+"';";
                                    rs4=stmt.executeQuery(sql);

                                   if(rs4.next()){
                                           String name=rs4.getString("s_name");
                                           SimpleDateFormat simpleformat=new SimpleDateFormat("yyyy-MM-dd");
                                           String date=simpleformat.format(new java.util.Date());

                                        datalist.add(name);
                                        datalist.add(date);
                                    datalist.add("");
                                    datalist.add("");
                                    datalist.add("");
                                   // datalist.add(m_menu);
                                    datalist.add(" 작성되지 않았습니다.");
                                    datalist.add("");
                                   }
                                     sql="select t_name from teacher_tbl where t_id=";
                                    sql=sql+"(select t_id from student_tbl where s_id='"+rData2.getS_id()+"');";

                                    rs2=stmt.executeQuery(sql);

                                    if(rs2.next()){
                                            String t_name=rs2.getString("t_name");
                                            datalist.add(t_name);
                                 }

				  sql="select c_name from class_tbl,comment_tbl where s_id='"+rData2.getS_id()+"';";
                                  rs3=stmt.executeQuery(sql);

                                    if(rs3.next()){
                                            String c_name=rs3.getString("c_name");
                                            datalist.add(c_name);
                                 }
				// sql="select m_menu from menu_tbl where m_date=CURRENT_DATE();";
				  sql="select m_menu from menu_tbl where m_date='"+rData2.getC_date()+"';";
                                 mn2=stmt.executeQuery(sql);

                                 if(mn2.next()){
                                         String menu2=mn2.getString("m_menu");
                                         datalist.add(menu2);
                                 }else{
                                        datalist.add("정보없음");
                                  }


				 break;

                                 }else{ //교사가 코멘트를 작성했을 경우

					 System.out.println("was written");
					 if(rs.getInt("final")!=0){
						 System.out.println("final ok");
                                    String name=rs.getString("s_name");
				    String date=rs.getString("c_date");
                                    String c_feel=state2String(rs.getString("c_feel"));
                                    String c_meal=state2String(rs.getString("c_meal"));
                                    String c_health=state2String(rs.getString("c_health"));
                                    String c_comment=rs.getString("c_comment");
                                    String c_same=rs.getString("c_same");
                                    datalist.add(name);
                                    datalist.add(date);
				    datalist.add(c_feel);
                                    datalist.add(c_meal);
                                    datalist.add(c_health);
                                    datalist.add(c_comment);
                                    datalist.add(c_same);
					
					
				     sql="select t_name from teacher_tbl where t_id=";
                                    sql=sql+"(select t_id from student_tbl where s_id='"+rData2.getS_id()+"');";

                                    rs2=stmt.executeQuery(sql);

                                    if(rs2.next()){
                                            String t_name=rs2.getString("t_name");
                                            datalist.add(t_name);
                                 }

                                 sql="select c_name from class_tbl,comment_tbl where s_id='"+rData2.getS_id()+"';";
                                  rs3=stmt.executeQuery(sql);

                                    if(rs3.next()){
                                            String c_name=rs3.getString("c_name");
                                            datalist.add(c_name);
                                 }

				 // sql="select m_menu from menu_tbl where m_date=CURRENT_DATE();";
                                 sql="select m_menu from menu_tbl where m_date='"+rData2.getC_date()+"';";
				 mn3=stmt.executeQuery(sql);

                                 if(mn3.next()){
                                         String menu3=mn3.getString("m_menu");
                                         datalist.add(menu3);
                                 }else{
                                        datalist.add("정보없음");
                                  }

				 break;
					 }else{
						 System.out.println("but not upload..");
						  String name=rs.getString("s_name");
                                    String date=rs.getString("c_date");
                                    String c_feel="";
                                    String c_meal="";
                                    String c_health="";
                                    String c_comment="작성되지 않았습니다.";
                                    String c_same="";

                                                                    // String m_menu=rs.getString("m_menu");

                                   // datalist.add(s_id);//s_id
                                    datalist.add(name);
                                    datalist.add(date);
                                   // datalist.add(class_name);
                                   // datalist.add(t_name);
                                    datalist.add(c_feel);
                                    datalist.add(c_meal);
                                    datalist.add(c_health);
                                   // datalist.add(m_menu);
                                    datalist.add(c_comment);
                                    datalist.add(c_same);

                                     sql="select t_name from teacher_tbl where t_id=";
                                    sql=sql+"(select t_id from student_tbl where s_id='"+rData2.getS_id()+"');";

                                    rs2=stmt.executeQuery(sql);

                                    if(rs2.next()){
                                            String t_name=rs2.getString("t_name");
                                            datalist.add(t_name);
                                 }
				  sql="select c_name from class_tbl,comment_tbl where s_id='"+rData2.getS_id()+"';";
                                  rs3=stmt.executeQuery(sql);

                                    if(rs3.next()){
                                            String t_name=rs3.getString("c_name");
                                            datalist.add(t_name);
                                 }
				// sql="select m_menu from menu_tbl where m_date=CURRENT_DATE();";
                                 sql="select m_menu from menu_tbl where m_date='"+rData2.getC_date()+"';";
				 mn2=stmt.executeQuery(sql);

                                 if(mn2.next()){
                                         String menu2=mn2.getString("m_menu");
                                         datalist.add(menu2);
                                 }else{
                                        datalist.add("정보없음");
                                  }


				 break;
					 } //else
				 }

			    case 4: 
				    loginData lData=(loginData)object;
				    System.out.println(lData.getLoginID());
				    System.out.println(lData.getLoginPW());
				    sql="select id from login where id='"+lData.getLoginID()+"';";//answer id
				    rs=stmt.executeQuery(sql);

				   if(rs.next()){
					  String id=rs.getString("id");
					  datalist.add(id);
					  System.out.println("add success"+id);
				   }

				    sql="select aes_decrypt(unhex(pw),'youngeun') from login where id='"+lData.getLoginID()+"';";//answerpw
                                     rs5=stmt.executeQuery(sql);


				   if(rs5.next()){
                                               String idpw=rs5.getString("aes_decrypt(unhex(pw),'youngeun')");
                                               if(idpw.equals(lData.getLoginPW())){
                                                       datalist.add("check");
                                               }
                                          }

				   sql="select s_id from parent_tbl where p_id='"+lData.getLoginID()+"';";
				   rs2=stmt.executeQuery(sql);

				   if(rs2.next()){
					   String s_id=rs2.getString("s_id");
					   datalist.add(s_id);
				   }

					   break;
				   

			
			    case 5:
					   CommentRequest gData=(CommentRequest)object;
					   sql="select s_name,s_pnum from student_tbl where s_id='";
					   sql=sql+gData.getS_id()+"';";
					   rs=stmt.executeQuery(sql);

					   if(rs.next()){
						   String s_name=rs.getString("s_name");
						   String s_pnum=rs.getString("s_pnum");

						   datalist.add(s_name);
						   datalist.add(s_pnum);
					   }

					    sql="select t_name,t_pnum from teacher_tbl where t_id=";
                                           sql=sql+"(select t_id from student_tbl where s_id='"+gData.getS_id()+"');";
  
                                           rs2=stmt.executeQuery(sql);
 
                                           if(rs2.next()){
                                                String t_name=rs2.getString("t_name");
						String t_pnum=rs2.getString("t_pnum");
                                                datalist.add(t_name);
						datalist.add(t_pnum);
                                 }

				         sql="select c_name from class_tbl,comment_tbl where s_id='"+gData.getS_id()+"';";
                                         rs3=stmt.executeQuery(sql);

                                         if(rs3.next()){
                                                 String c_name=rs3.getString("c_name");
                                                 datalist.add(c_name);
                                          }


					 break;
				  
			    case 6:
				            CommentRequest nData=(CommentRequest)object;
				            sql="select final from comment_tbl where s_id='";
				            sql=sql+nData.getS_id()+"' and c_date>CURRENT_DATE();";
				            rs=stmt.executeQuery(sql);

				            if(rs.next()){
					    
						    int f=rs.getInt("final");
						    System.out.println(f);
						    String msg=String.valueOf(f);

						    datalist.add(msg);
					    }else{
					    
						    datalist.add("0");
						}
						

		    }//switch

	    
	    }catch(Exception e){
		    e.printStackTrace();
	    }

	    return datalist;

   
    }



    public ArrayList<Object> selectDB_obj(int type,String string){
	        Connection conn = null;
                Statement stmt = null;
                ResultSet rs,rs2 = null;
                PreparedStatement pstmt = null;
                String sql = "";
                ArrayList<Object> datalist=new ArrayList<Object>();

		  try {

                        Class.forName("com.mysql.cj.jdbc.Driver");
                        conn=DriverManager.getConnection(
                                        "jdbc:mysql://localhost/tidsnotedb?characterEncoding=UTF-8"+"&user=suram&password=suram");
                        stmt=conn.createStatement();


                        switch(type){

				case 0:
					  String s_id=(String)string;
					  System.out.println(s_id);

					  sql="select p_id from parent_tbl where s_id='"+s_id+"';";
					  rs2=stmt.executeQuery(sql);
					  
					  if(rs2.next()){
						s_id=rs2.getString("p_id");
					  }
                                    sql="select * from message_tbl where final=1 and p_id='"+s_id+"' order by m_id desc";
                                    rs=stmt.executeQuery(sql);

                                    messageData messagedata;

                                    while(rs.next()){

                                            messagedata= new messageData();
					    messagedata.setP_id(rs.getString("p_id"));
                                            messagedata.setMdate(rs.getString("m_date"));
                                            messagedata.setMessage(rs.getString("m_cont"));
					    messagedata.setM_id(rs.getString("m_id"));

					    if (rs.getInt("done")==1){
					    
						    messagedata.setRequest(rs.getString("m_req")+"처리가 완료된 요청입니다.");
					    }else{
					    
						    messagedata.setRequest(rs.getString("m_req"));
					    }
                                            datalist.add(messagedata);
                                    }//while
                                   break;

			       case 1:
				        String p_id2=(String)string;
					sql="select * from message_tbl where final=0 and p_id='"+p_id2+"';";
					rs=stmt.executeQuery(sql);

					messageData tempMSG;

					if(rs.next()){
					
					       tempMSG=new messageData();	
					       tempMSG.setP_id(rs.getString("p_id"));
					       tempMSG.setMessage(rs.getString("m_cont"));
					       tempMSG.setRequest(rs.getString("m_req"));
					       tempMSG.setM_id(rs.getString("m_id"));
					       
					       datalist.add(tempMSG);
					}
					break;
			}//switch

			}catch(Exception e){
			       	e.printStackTrace();}

		  return datalist;
    
    }
    public static void insertDB(int type,Object object ){
    
                Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String sql = "";
		ArrayList<String> datalist=new ArrayList<String>();
	
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			conn=DriverManager.getConnection(
					"jdbc:mysql://localhost/tidsnotedb?characterEncoding=UTF-8"+"&user=suram&password=suram");
			stmt=conn.createStatement();


			switch(type){
			
				case 2:
				messageData mData= (messageData)object;

				sql="select count(*) from message_tbl where m_id='"+mData.getM_id()+"';";
				rs=stmt.executeQuery(sql);

				if(rs.next()){
				
					if((rs.getInt("count(*)"))!=0){
					
						sql="delete from message_tbl where m_id='"+mData.getM_id()+"';";
						stmt.executeUpdate(sql);
					}
				}

		        	  sql="insert into message_tbl(m_id,m_cont,m_date,p_id,m_req,final) values(?,?,?,?,?,?);";                                
				 
				  pstmt=conn.prepareStatement(sql);
				  pstmt.setString(1,mData.getM_id());
                                  pstmt.setString(2,mData.getMessage());
                                  pstmt.setString(3,mData.getMdate());
                                  pstmt.setString(4,mData.getP_id());
				  pstmt.setString(5,mData.getRequest());
				  if((mData.getState()).equals("final")){	
		                     pstmt.setInt(6,1);			  
				  }
				  else{
				     pstmt.setInt(6,0);
				  }


                                  pstmt.executeUpdate();

			
                                  break;
			
			   
			}

		}catch(Exception e){
		e.printStackTrace();}
		
    
    }
   

    
}
