package com.rca.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {

	public static Connection getConnection()  {

		Connection con = null;
		String URL = "jdbc:mysql://localhost:3306/rcautility";
		String USER = "root";
		String PASS = "root";
		try {
			   Class.forName("com.mysql.jdbc.Driver");
			   con = DriverManager.getConnection(URL,USER, PASS);
			}
			catch(ClassNotFoundException ex) {
			   System.out.println("Error: unable to load driver class!");			  
			}
			catch (SQLException e) {
				System.out.println("Connection Failed! Check output console");
				e.printStackTrace();			
			}
		return con;
	}
	/*public static void main(String [] arg)
	{
		ConnectionProvider cp = new ConnectionProvider();
		Connection conn = cp.getConnection();
		System.out.println("conn= "+conn);
	}*/
}
