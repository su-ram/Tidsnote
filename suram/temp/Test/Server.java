import java.net.*;
import java.io.*;
import java.sql.*;
import java.util.*;
public class Server{
	public static ArrayList<String> selectfromDB(int type,ArrayList<String> input){


		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String sql = "select * from studenttbl";
		ArrayList<String> datalist=new ArrayList<String>();
	
		
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			conn=DriverManager.getConnection(
					"jdbc:mysql://localhost/kidsnotedb?characterEncoding=UTF-8"+"&user=suram&password=suram");
			stmt=conn.createStatement();


			switch(type){
			
				case 0:

rs=stmt.executeQuery(sql);



						while(rs.next()){
			
			String id = rs.getString("id");
			String name = rs.getString("name");
			datalist.add(id);
			datalist.add(name);
			
			}
					break;
				case 1:
				sql = "select * from teacher where id ="+input.get(0)+" and time > CURRENT_DATE()"+"order by time DESC limit 1";
				rs=stmt.executeQuery(sql);
				if(!rs.next()){//값이 없으면. 미리 저장된 코멘트가 없는 경우
					//System.out.println("Not Empty");
					sql = "insert into teacher(id,status,health,meal,comment) values(?,?,?,?,?)";

pstmt=conn.prepareStatement(sql);
pstmt.setString(1, input.get(0));
pstmt.setString(2, input.get(1));
pstmt.setString(3, input.get(2));
pstmt.setString(4, input.get(3));
pstmt.setString(5, input.get(4));
pstmt.executeUpdate();


				}else{

					String comment = rs.getString("comment");
				//	System.out.println("미리 저장된 값 "+comment);

					//미리 저장된 코멘트가 있는 경우
					sql = "insert into teacher(id,status,health,meal,comment) values(?,?,?,?,?)";
					sql = "update teacher set status = ?, health = ?, meal = ?, comment = ?"+

					"where comment = ?";
					

pstmt=conn.prepareStatement(sql);
pstmt.setString(1, input.get(1));
pstmt.setString(2, input.get(2));
pstmt.setString(3, input.get(3));
pstmt.setString(4, input.get(4));
pstmt.setString(5, comment);
pstmt.executeUpdate();



				}



		

					break;
				
				case 2:
				sql = "insert into teacher(id,comment) values(?,?)";
				
pstmt=conn.prepareStatement(sql);
pstmt.setString(1, input.get(0));
pstmt.setString(2, input.get(1));
pstmt.executeUpdate();


					break;
				case 3:
				rs=stmt.executeQuery("select comment from teacher where id ="+input.get(0)+
				" and time > CURRENT_DATE() order by time DESC limit 1"
				);
				
				
				
										while(rs.next()){
							
							String id = rs.getString("comment");
							
							datalist.add(id);
							
							}
				
				rs=stmt.executeQuery("select * from menu where date = CURRENT_DATE()");
						while(rs.next()){
							String menu = rs.getString("menu");
							datalist.add(menu);
						}

					break;
				case 4:
				rs=stmt.executeQuery("select * from teacher where id = "+input.get(0)+
						" and time > CURRENT_DATE() order by time DESC limit 1");
				while(rs.next()){
				
					String health = rs.getString("health");
					String meal = rs.getString("meal");
					String status = rs.getString("status");
					String comment = rs.getString("comment");
					datalist.add(health);
					datalist.add(meal);
					datalist.add(status);
					datalist.add(comment);
				}


			
			}
			


						//System.out.println("SQL SUCCESS");
	}catch(Exception e){
		e.printStackTrace();
	}

	return datalist;

	}


	public static void main(String[] args){
	
	
		String[] data;
		String str;
		ArrayList<String> result=new ArrayList<String>();

		try{
		
			ServerSocket server = new ServerSocket(12345);
			while(true){
			
				Socket client = server.accept();
				InputStream inputStream = client.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(inputStream);
				OutputStream out = client.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(out);



				try{

					str=(String)ois.readObject();
					System.out.println(str);
				
			
			
			
			if(str.equals("0")){
			
			result=selectfromDB(0,result);
			//System.out.println(result);
			oos.writeObject(result);
			
			
			
			}else if(str.equals("1")){

				for(int i=0;i<5;i++){

					String temp = (String)ois.readObject();
					result.add(i,temp);
					//System.out.println(result.get(i));
				}
				
					selectfromDB(1,result);
			

			}else if(str.equals("2")){

				try{
					File file = new File("/home/suram/photo.jpg");
					FileOutputStream fileout = new FileOutputStream(file);
					byte[] buf = (byte[])ois.readObject();
					fileout.write(buf);
					
				}catch(Exception e){
					e.printStackTrace();
				}




			}else if(str.equals("3")){
				for(int i=0;i<2;i++){

					String temp = (String)ois.readObject();
					result.add(i,temp);
					//System.out.println(result.get(i));
				}
				selectfromDB(2,result);
			}else if(str.equals("4")){

				String temp = (String)ois.readObject();
				result.add(0,temp);
				result=selectfromDB(3, result);
				if(result.size()!=0){
					//System.out.println(result);
					oos.writeObject(result);

				}else{
					result.add(0,"none");
					oos.writeObject(result);
				}
				
				

			 }else if(str.equals("5")){
			 

				 String temp = (String)ois.readObject();
				 result.add(0,temp);
				 result=selectfromDB(4,result);

				 if(result.size()!=0){
				 	 //System.out.println(result);
				 	 oos.writeObject(result);
				 }else{
					result.add(0,"none");
				 	oos.writeObject(result);
				 }
			 }
			
	

				}catch(Exception e){
					e.printStackTrace();	
				}finally{
				client.close();

				}
			
			}
		
		
		}catch(Exception e){
		
			e.printStackTrace();
		
		}
	
	
	
	
	}




}
