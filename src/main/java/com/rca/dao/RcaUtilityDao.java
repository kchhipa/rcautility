package com.rca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.rca.entity.ConnectionProvider;
import com.rca.entity.RCA;

public class RcaUtilityDao {

	public static String saveRcaDetails(RCA rca)
	{
		Connection conn  = null;
	    try
	    {
	    	conn = ConnectionProvider.getConnection();
	    	PreparedStatement  stmt = null;
	    	
	    	String sql2 ="select * from rca_count where project_id=? and week=?";		    	 
    		stmt = conn.prepareStatement(sql2);
    		
	    	stmt.setInt(1, rca.getProject_id());
	    	stmt.setString(2, rca.week);    		    	
	    	ResultSet rs = stmt.executeQuery();
	    	if(rs.next())
	    	{
	    		String result = updateRcaDetailsDao(rca);
	    		return result;
	    	}
	    	
	    	String sql = "INSERT INTO rca_count(mr_product_backlog, mr_qa, mr_uat, mr_prod, cr_product_backlog, cr_qa, cr_uat, cr_prod," +
	    			"config_product_backlog, config_qa, config_uat, config_prod, ccb_product_backlog, ccb_qa, ccb_uat, ccb_prod, ad_product_backlog, ad_qa, " +
	    			"ad_uat, ad_prod, dup_product_backlog, dup_qa, dup_uat, dup_prod, nad_product_backlog, nad_qa, nad_uat, nad_prod, bsi_product_backlog, " +
	    			"bsi_qa, bsi_uat, bsi_prod, utr_product_backlog, utr_qa, utr_uat, utr_prod, pd_product_backlog, pd_qa, pd_uat, pd_prod, ii_product_backlog, " +
	    			"ii_qa, ii_uat, ii_prod, di_product_backlog, di_qa, di_uat,di_prod,ro_qa,ro_uat,ro_prod,week,project_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
	    			" ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    	
	        stmt = conn.prepareStatement(sql);
	    	setRcaFields(rca,stmt);	    	
	    		    	
	    	int row = stmt.executeUpdate();
            if (row > 0) {
                System.out.println("rca detail inserted successfully");
            }
            conn.close();
	    }
	    catch(SQLException e)
	    {	    
	    	System.out.println("problem in saving rca details");
	    	return "failure";
	    }	  
		return "success";
		
	}
	
	
	
	
	
	public static String updateRcaDetailsDao(RCA rca)
	{
		Connection conn  = null;
	    try
	    {
	    	conn = ConnectionProvider.getConnection();	   
	    	String sql = "update rca_count set mr_product_backlog = ?, mr_qa = ?, mr_uat = ?, mr_prod = ?, cr_product_backlog = ?, cr_qa = ?, cr_uat = ?, cr_prod = ?," +
	    			"config_product_backlog = ?, config_qa = ?, config_uat = ?, config_prod = ?, ccb_product_backlog = ?, ccb_qa = ?, ccb_uat = ?, ccb_prod = ?, ad_product_backlog = ?, ad_qa = ?, " +
	    			"ad_uat = ?, ad_prod = ?, dup_product_backlog = ?, dup_qa = ?, dup_uat = ?, dup_prod = ?, nad_product_backlog = ?, nad_qa = ?, nad_uat = ?, nad_prod = ?, bsi_product_backlog = ?, " +
	    			"bsi_qa = ?, bsi_uat = ?, bsi_prod = ?, utr_product_backlog = ?, utr_qa = ?, utr_uat = ?, utr_prod = ?, pd_product_backlog = ?, pd_qa = ?, pd_uat = ?, pd_prod = ?, ii_product_backlog = ?, " +
	    			"ii_qa = ?, ii_uat = ?, ii_prod = ?, di_product_backlog = ?, di_qa = ?, di_uat = ?,di_prod = ?,ro_qa = ?,ro_uat = ?,ro_prod = ? where week = ? and project_id = ?";
	    	
	    	PreparedStatement  stmt = conn.prepareStatement(sql);
	    	setRcaFields(rca,stmt);
	    	
	    		    	
	    	int row = stmt.executeUpdate();
            if (row > 0) {
                System.out.println("rca detail updated successfully");
            }
            conn.close();
	    }
	    catch(SQLException e)
	    {	    
	    	System.out.println("problem in updating rca details");
	    	return "udpateFailure";
	    }	  
		return "updated";
		
	}
	
	
	private static void setRcaFields(RCA rca,PreparedStatement stmt)
	{
		try {
			stmt.setInt(1, rca.mr_product_backlog);
			stmt.setInt(2, rca.mr_qa);
	    	stmt.setInt(3, rca.mr_uat);
	    	stmt.setInt(4, rca.mr_prod);
	    	stmt.setInt(5, rca.cr_product_backlog);
	    	stmt.setInt(6, rca.cr_qa);
	    	stmt.setInt(7, rca.cr_uat);
	    	stmt.setInt(8, rca.cr_prod);
	    	stmt.setInt(9, rca.config_product_backlog);
	    	stmt.setInt(10, rca.config_qa);
	    	stmt.setInt(11, rca.config_uat);
	    	stmt.setInt(12, rca.config_prod);
	    	stmt.setInt(13, rca.ccb_product_backlog);
	    	stmt.setInt(14, rca.ccb_qa);
	    	stmt.setInt(15, rca.ccb_uat);
	    	stmt.setInt(16, rca.ccb_prod);
	    	stmt.setInt(17, rca.ad_product_backlog);
	    	stmt.setInt(18, rca.ad_qa);
	    	stmt.setInt(19, rca.ad_uat);
	    	stmt.setInt(20, rca.ad_prod);
	    	stmt.setInt(21, rca.dup_product_backlog);
	    	stmt.setInt(22, rca.dup_qa);
	    	stmt.setInt(23, rca.dup_uat);
	    	stmt.setInt(24, rca.dup_prod);
	    	stmt.setInt(25, rca.nad_product_backlog);
	    	stmt.setInt(26, rca.nad_qa);
	    	stmt.setInt(27, rca.nad_uat);
	    	stmt.setInt(28, rca.nad_prod);
	    	stmt.setInt(29, rca.bsi_product_backlog);
	    	stmt.setInt(30, rca.bsi_qa);
	    	stmt.setInt(31, rca.bsi_uat);
	    	stmt.setInt(32, rca.bsi_prod);
	    	stmt.setInt(33, rca.utr_product_backlog);
	    	stmt.setInt(34, rca.utr_qa);
	    	stmt.setInt(35, rca.utr_uat);
	    	stmt.setInt(36, rca.utr_prod);
	    	stmt.setInt(37, rca.pd_product_backlog);
	    	stmt.setInt(38, rca.pd_qa);
	    	stmt.setInt(39, rca.pd_uat);	    
	    	stmt.setInt(40, rca.pd_prod);
	    	stmt.setInt(41, rca.ii_product_backlog);
	    	stmt.setInt(42, rca.ii_qa);
	    	stmt.setInt(43, rca.ii_uat);
	    	stmt.setInt(44, rca.ii_prod);
	    	stmt.setInt(45, rca.di_product_backlog);
	    	stmt.setInt(46, rca.di_qa);
	    	stmt.setInt(47, rca.di_uat);
	    	stmt.setInt(48, rca.di_prod);
	    	
	    	stmt.setInt(49, rca.ro_qa);
	    	stmt.setInt(50, rca.ro_uat);
	    	stmt.setInt(51, rca.ro_prod);	    	
	    	stmt.setString(52, rca.week);
	    	stmt.setInt(53, rca.project_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}
	
	
	public static List<RCA> getRcaDetailList(RCA rca)
	{
		Connection conn  = null;
		List<RCA> rcaList=new ArrayList<RCA>();
		String week=rca.week;
	    try
	    {
	    	conn = ConnectionProvider.getConnection();	 
	    	String sql1;
	    	String sql2;	    	 	
	    	sql1 = "select * from project_details";
	    	
	    	PreparedStatement  stmt = conn.prepareStatement(sql1);	    	    		    	
	    	ResultSet rs1 = stmt.executeQuery();
            while(rs1.next())
            {
            	int projectId=rs1.getInt("project_id");	    	    	
            	sql2 ="select * from rca_count where project_id=? and week=?";	
	    		stmt=null;
	    		stmt = conn.prepareStatement(sql2);
		    	stmt.setInt(1, rs1.getInt("project_id"));
		    	stmt.setString(2, week);    		    	
		    	ResultSet rs = stmt.executeQuery();
	            while(rs.next())
	            {
	            	RCA rca1=new RCA();
	            	rca1.setMr_product_backlog(rs.getInt("mr_product_backlog"));
	            	rca1.setMr_qa(rs.getInt("mr_qa"));
	            	rca1.setMr_uat(rs.getInt("mr_uat"));
	            	rca1.setMr_prod(rs.getInt("mr_prod"));
	            	rca1.setCr_product_backlog(rs.getInt("cr_product_backlog"));
	            	rca1.setCr_qa(rs.getInt("cr_qa"));
	            	rca1.setCr_uat(rs.getInt("cr_uat"));
	            	rca1.setCr_prod(rs.getInt("cr_prod"));
	            	rca1.setConfig_product_backlog(rs.getInt("config_product_backlog"));
	            	rca1.setConfig_qa(rs.getInt("config_qa"));
	            	rca1.setConfig_uat(rs.getInt("config_uat"));
	            	rca1.setConfig_prod(rs.getInt("config_prod"));
	            	rca1.setCcb_product_backlog(rs.getInt("ccb_product_backlog"));
	            	rca1.setCcb_qa(rs.getInt("ccb_qa"));
	            	rca1.setCcb_uat(rs.getInt("ccb_uat"));
	            	rca1.setCcb_prod(rs.getInt("ccb_prod"));
	            	rca1.setAd_product_backlog(rs.getInt("ad_product_backlog"));
	            	rca1.setAd_qa(rs.getInt("ad_qa"));
	            	rca1.setAd_uat(rs.getInt("ad_uat"));
	            	rca1.setAd_prod(rs.getInt("ad_prod"));
	            	rca1.setDup_product_backlog(rs.getInt("dup_product_backlog"));
	            	rca1.setDup_qa(rs.getInt("dup_qa"));
	            	rca1.setDup_uat(rs.getInt("dup_uat"));
	            	rca1.setDup_prod(rs.getInt("dup_prod"));
	            	rca1.setNad_product_backlog(rs.getInt("nad_product_backlog"));
	            	rca1.setNad_qa(rs.getInt("nad_qa"));
	            	rca1.setNad_uat(rs.getInt("nad_uat"));
	            	rca1.setNad_prod(rs.getInt("nad_prod"));
	            	rca1.setBsi_product_backlog(rs.getInt("bsi_product_backlog"));
	            	rca1.setBsi_qa(rs.getInt("bsi_qa"));
	            	rca1.setBsi_uat(rs.getInt("bsi_uat"));
	            	rca1.setBsi_prod(rs.getInt("bsi_prod"));
	            	rca1.setUtr_product_backlog(rs.getInt("utr_product_backlog"));
	            	rca1.setUtr_qa(rs.getInt("utr_qa"));
	            	rca1.setUtr_uat(rs.getInt("utr_uat"));
	            	rca1.setUtr_prod(rs.getInt("utr_prod"));            	
	            	rca1.setPd_product_backlog(rs.getInt("pd_product_backlog"));
	            	rca1.setPd_qa(rs.getInt("pd_qa"));
	            	rca1.setPd_uat(rs.getInt("pd_uat"));
	            	rca1.setPd_prod(rs.getInt("pd_prod"));
	            	rca1.setIi_product_backlog(rs.getInt("ii_product_backlog"));
	            	rca1.setIi_qa(rs.getInt("ii_qa"));                   	
	            	rca1.setIi_uat(rs.getInt("ii_uat"));
	            	rca1.setIi_prod(rs.getInt("ii_prod"));
	            	rca1.setDi_product_backlog(rs.getInt("di_product_backlog"));
	            	rca1.setDi_qa(rs.getInt("di_qa"));
	            	rca1.setDi_uat(rs.getInt("di_uat"));
	            	rca1.setDi_prod(rs.getInt("di_prod"));	            	
	            	rca1.setRo_qa(rs.getInt("ro_qa"));
	            	rca1.setRo_uat(rs.getInt("ro_uat"));
	            	rca1.setRo_prod(rs.getInt("ro_prod"));
	            	rca1.setProject_id(projectId);
	            	rcaList.add(rca1);
	            }
            }
	            conn.close();
		    }
		    catch(SQLException e)
		    {	    
		    	System.out.println("problem in Retrieve rca details");
		    }	  
	    	
		    return rcaList;
	  }
	    
	
	
	
	
	public static void getRcaDetail(RCA rca)
	{
		Connection conn  = null;
	    try
	    {
	    	conn = ConnectionProvider.getConnection();	   
	    	String sql = "select * from rca_count where project_id=? and week=?";
	    	
	    	PreparedStatement  stmt = conn.prepareStatement(sql);
	    	stmt.setInt(1, rca.project_id);
	    	stmt.setString(2, rca.week);    		    	
	    	ResultSet rs = stmt.executeQuery();
            if(rs.next())
            {
            	rca.setMr_product_backlog(rs.getInt("mr_product_backlog"));
            	rca.setMr_qa(rs.getInt("mr_qa"));
            	rca.setMr_uat(rs.getInt("mr_uat"));
            	rca.setMr_prod(rs.getInt("mr_prod"));
            	rca.setCr_product_backlog(rs.getInt("cr_product_backlog"));
            	rca.setCr_qa(rs.getInt("cr_qa"));
            	rca.setCr_uat(rs.getInt("cr_uat"));
            	rca.setCr_prod(rs.getInt("cr_prod"));
            	rca.setConfig_product_backlog(rs.getInt("config_product_backlog"));
            	rca.setConfig_qa(rs.getInt("config_qa"));
            	rca.setConfig_uat(rs.getInt("config_uat"));
            	rca.setConfig_prod(rs.getInt("config_prod"));
            	rca.setCcb_product_backlog(rs.getInt("ccb_product_backlog"));
            	rca.setCcb_qa(rs.getInt("ccb_qa"));
            	rca.setCcb_uat(rs.getInt("ccb_uat"));
            	rca.setCcb_prod(rs.getInt("ccb_prod"));
            	rca.setAd_product_backlog(rs.getInt("ad_product_backlog"));
            	rca.setAd_qa(rs.getInt("ad_qa"));
            	rca.setAd_uat(rs.getInt("ad_uat"));
            	rca.setAd_prod(rs.getInt("ad_prod"));
            	rca.setDup_product_backlog(rs.getInt("dup_product_backlog"));
            	rca.setDup_qa(rs.getInt("dup_qa"));
            	rca.setDup_uat(rs.getInt("dup_uat"));
            	rca.setDup_prod(rs.getInt("dup_prod"));
            	rca.setNad_product_backlog(rs.getInt("nad_product_backlog"));
            	rca.setNad_qa(rs.getInt("nad_qa"));
            	rca.setNad_uat(rs.getInt("nad_uat"));
            	rca.setNad_prod(rs.getInt("nad_prod"));
            	rca.setBsi_product_backlog(rs.getInt("bsi_product_backlog"));
            	rca.setBsi_qa(rs.getInt("bsi_qa"));
            	rca.setBsi_uat(rs.getInt("bsi_uat"));
            	rca.setBsi_prod(rs.getInt("bsi_prod"));
            	rca.setUtr_product_backlog(rs.getInt("utr_product_backlog"));
            	rca.setUtr_qa(rs.getInt("utr_qa"));
            	rca.setUtr_uat(rs.getInt("utr_uat"));
            	rca.setUtr_prod(rs.getInt("utr_prod"));            	
            	rca.setPd_product_backlog(rs.getInt("pd_product_backlog"));
            	rca.setPd_qa(rs.getInt("pd_qa"));
            	rca.setPd_uat(rs.getInt("pd_uat"));
            	rca.setPd_prod(rs.getInt("pd_prod"));
            	rca.setIi_product_backlog(rs.getInt("ii_product_backlog"));
            	rca.setIi_qa(rs.getInt("ii_qa"));                   	
            	rca.setIi_uat(rs.getInt("ii_uat"));
            	rca.setIi_prod(rs.getInt("ii_prod"));
            	rca.setDi_product_backlog(rs.getInt("di_product_backlog"));
            	rca.setDi_qa(rs.getInt("di_qa"));
            	rca.setDi_uat(rs.getInt("di_uat"));
            	rca.setDi_prod(rs.getInt("di_prod"));
            	
            	rca.setRo_qa(rs.getInt("ro_qa"));
            	rca.setRo_uat(rs.getInt("ro_uat"));
            	rca.setRo_prod(rs.getInt("ro_prod"));
            }
            else
            {
            	rca.setMr_product_backlog(0);
            	rca.setMr_qa(0);
            	rca.setMr_uat(0);
            	rca.setMr_prod(0);
            	rca.setCr_product_backlog(0);
            	rca.setCr_qa(0);
            	rca.setCr_uat(0);
            	rca.setCr_prod(0);
            	rca.setConfig_product_backlog(0);
            	rca.setConfig_qa(0);
            	rca.setConfig_uat(0);
            	rca.setConfig_prod(0);
            	rca.setCcb_product_backlog(0);
            	rca.setCcb_qa(0);
            	rca.setCcb_uat(0);
            	rca.setCcb_prod(0);
            	rca.setAd_product_backlog(0);
            	rca.setAd_qa(0);
            	rca.setAd_uat(0);
            	rca.setAd_prod(0);
            	rca.setDup_product_backlog(0);
            	rca.setDup_qa(0);
            	rca.setDup_uat(0);
            	rca.setDup_prod(0);
            	rca.setNad_product_backlog(0);
            	rca.setNad_qa(0);
            	rca.setNad_uat(0);
            	rca.setNad_prod(0);
            	rca.setBsi_product_backlog(0);
            	rca.setNad_qa(0);
            	rca.setNad_uat(0);
            	rca.setBsi_prod(0);
            	rca.setUtr_product_backlog(0);
            	rca.setUtr_qa(0);
            	rca.setUtr_uat(0);
            	rca.setUtr_prod(0);            	
            	rca.setPd_product_backlog(0);
            	rca.setPd_qa(0);
            	rca.setPd_uat(0);
            	rca.setPd_prod(0);
            	rca.setIi_product_backlog(0);
            	rca.setIi_qa(0);                   	
            	rca.setIi_uat(0);
            	rca.setIi_prod(0);
            	rca.setDi_product_backlog(0);
            	rca.setDi_qa(0);
            	rca.setDi_uat(0);
            	rca.setDi_prod(0);
            	
            	rca.setRo_qa(0);
            	rca.setRo_uat(0);
            	rca.setRo_prod(0);
            }
            conn.close();
	    }
	    catch(SQLException e)
	    {	    
	    	System.out.println("problem in saving rca details");
	    }	  
		
	}
	
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
  
  public static StringBuffer getProjectDetails() throws SQLException{
	  Connection conn  = null;
	  String result = null;
	  conn = ConnectionProvider.getConnection();	 
  	String sql1;
  	String sql2;	    	 	
  	sql1 = "select * from project_details";
  	
  	PreparedStatement  stmt = conn.prepareStatement(sql1);	    	    		    	
  	ResultSet rs = stmt.executeQuery();
  	StringBuffer stringBuffer = new StringBuffer();
  	while(rs.next()){
  		stringBuffer.append(" "+rs.getString("project_name")+"\n");
  		
  	}
  	return stringBuffer;
  }
}
