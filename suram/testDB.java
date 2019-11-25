

import java.sql.*;
public class testDB{



    public static void main(String[] args){

	    Connection conn;
	    Statement stmt = null;

        try{

		Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://localhost/suram?characterEncoding=UTF-8" + "&user=suram&password=suram");

	    System.out.println("JDBC");

	    stmt=conn.createStatement();
	    String sql = "insert into sr values('안녕하세요 시발 ^*^')";
	    stmt.executeUpdate(sql);
        }catch (Exception e){
            e.printStackTrace();
        }




    }








}

