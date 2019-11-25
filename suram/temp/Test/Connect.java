

import java.sql.*;

public class Connect{


	public static void main(String[] args){


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

			stmt = conn.createStatement();
		if(stmt.execute(" INSERT INTO `student` (`id`, `name`) VALUES ('456', '김수람')  ")){


			System.out.println("SQL SUCCESS");

			}

		
		}catch(Exception e){

			System.out.println("ERROR");
			e.printStackTrace();

	}

}}
