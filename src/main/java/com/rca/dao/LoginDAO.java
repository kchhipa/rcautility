package com.rca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.rca.entity.ConnectionProvider;

public class LoginDAO {
	
	 public static String validateUser(String userName, String passWord)
	  {
		  Connection conn  = null;
		  String result = null;
		  try
		    {
		    	conn = ConnectionProvider.getConnection();	   
		    	String sql = "select * from login_details where login_id=? and password=?";
		    	
		    	PreparedStatement  stmt = conn.prepareStatement(sql);
		    	stmt.setString(1, userName);
		    	stmt.setString(2, passWord);    		    	
		    	ResultSet rs = stmt.executeQuery();
		    	if(rs.next())
		    		result =  "success";
		    	else
		    		result = "noUser";
		    	
		    	 conn.close();
		    }
		    catch(SQLException e)
		    {	    
		    	System.out.println("problem in executing the request");
		    	return "noUser";
		    }
		  return result;
	  }

}
