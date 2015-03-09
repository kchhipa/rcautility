package com.rca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.rca.entity.ConnectionProvider;

public class ProjectDAO {
	
	public static void getProjectIds(Map projectNameWithId, List projectList)
	  {
		  Connection conn  = null;
		  String result = null;
		  try
		    {
		    	conn = ConnectionProvider.getConnection();	   
		    	String sql = "select project_id,project_name from project_details";	    	
		    	PreparedStatement  stmt = conn.prepareStatement(sql);	    	    		    	
		    	ResultSet rs = stmt.executeQuery();	    	
		    	while(rs.next())
		    	{
		    		if(projectList.contains(rs.getString("project_name")))
		    		projectNameWithId.put(rs.getString("project_name"), rs.getInt("project_id"));
		    	}
		    	
		    	 conn.close();
		    }
		    catch(SQLException e)
		    {	    
		    	System.out.println("problem in geting project ids with name");
		    	
		    }
	  }

}
