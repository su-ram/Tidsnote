
import java.io.*;
import java.net.*;
import java.sql.*;

public class Server2{


	


	public static void main(String[] args){

		String[] data;
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;




		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
		System.out.println("Driver OK");
			conn=DriverManager.getConnection(
					"jdbc:mysql://localhost/kidsnotedb?characterEncoding=UTF-8"+"&user=suram&password=suram"
					);
		System.out.println("Connection OK");


		
		}catch(Exception e){

			System.out.println("ERROR");
			e.printStackTrace();

	}
	}}

