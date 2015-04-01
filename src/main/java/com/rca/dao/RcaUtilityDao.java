package com.rca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.rca.entity.ConnectionProvider;
import com.rca.entity.ProjectDetails;
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
	    			"ccb_product_backlog, ccb_qa, ccb_uat, ccb_prod, ad_product_backlog, ad_qa, " +
	    			"ad_uat, ad_prod, dup_product_backlog, dup_qa, dup_uat, dup_prod, nad_product_backlog, nad_qa, nad_uat, nad_prod, bsi_product_backlog, " +
	    			"bsi_qa, bsi_uat, bsi_prod, utr_product_backlog, utr_qa, utr_uat, utr_prod, pd_product_backlog, pd_qa, pd_uat, pd_prod, " +
	    			"di_product_backlog, di_qa, di_uat,di_prod,ro_qa,ro_uat,ro_prod, plan_product_backlog, plan_qa, plan_uat, plan_prod, " +
	    			"rate_product_backlog, rate_qa, rate_uat, rate_prod, rpa_product_backlog, rpa_qa, rpa_uat, rpa_prod, ac_product_backlog, ac_qa, ac_uat, ac_prod, " +
	    			"ti_product_backlog, ti_qa, ti_uat, ti_prod, dp_product_backlog, dp_qa, dp_uat, dp_prod, env_product_backlog, env_qa, env_uat, env_prod, co_product_backlog, co_qa, co_uat, co_prod, " +
	    			"ffm_product_backlog, ffm_qa, ffm_uat, ffm_prod, crmesb_product_backlog, crmesb_qa, crmesb_uat, crmesb_prod, otp_product_backlog, otp_qa, otp_uat, otp_prod, pmuu_product_backlog, pmuu_qa, pmuu_uat, pmuu_prod, io_product_backlog, io_qa, io_uat, io_prod, week,project_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
	    			" ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    	
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
	    			"ccb_product_backlog = ?, ccb_qa = ?, ccb_uat = ?, ccb_prod = ?, ad_product_backlog = ?, ad_qa = ?, " +
	    			"ad_uat = ?, ad_prod = ?, dup_product_backlog = ?, dup_qa = ?, dup_uat = ?, dup_prod = ?, nad_product_backlog = ?, nad_qa = ?, nad_uat = ?, nad_prod = ?, bsi_product_backlog = ?, " +
	    			"bsi_qa = ?, bsi_uat = ?, bsi_prod = ?, utr_product_backlog = ?, utr_qa = ?, utr_uat = ?, utr_prod = ?, pd_product_backlog = ?, pd_qa = ?, pd_uat = ?, pd_prod = ?, " +
	    			"di_product_backlog = ?, di_qa = ?, di_uat = ?,di_prod = ?,ro_qa = ?,ro_uat = ?,ro_prod = ?, plan_product_backlog = ?, plan_qa = ?, plan_uat = ?, plan_prod = ?, rate_product_backlog = ?, rate_qa = ?, rate_uat = ?, rate_prod = ?,rpa_product_backlog = ?, rpa_qa = ?, rpa_uat = ?, rpa_prod = ?,ac_product_backlog = ?, ac_qa = ?, ac_uat = ?, ac_prod = ?,ti_product_backlog = ?, ti_qa = ?, ti_uat = ?, ti_prod = ?,dp_product_backlog = ?, dp_qa = ?, dp_uat = ?, dp_prod = ?,env_product_backlog = ?, env_qa = ?, env_uat = ?, env_prod = ?,co_product_backlog = ?, co_qa = ?, co_uat = ?, co_prod = ?,ffm_product_backlog = ?, ffm_qa = ?, ffm_uat = ?, ffm_prod = ?,crmesb_product_backlog = ?, crmesb_qa = ?, crmesb_uat = ?, crmesb_prod = ?,otp_product_backlog = ?, otp_qa = ?, otp_uat = ?, otp_prod = ?,pmuu_product_backlog = ?, pmuu_qa = ?, pmuu_uat = ?, pmuu_prod = ?,io_product_backlog = ?, io_qa = ?, io_uat = ?, io_prod = ? where week = ? and project_id = ?";
	    	
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
	    	stmt.setInt(9, rca.ccb_product_backlog);
	    	stmt.setInt(10, rca.ccb_qa);
	    	stmt.setInt(11, rca.ccb_uat);
	    	stmt.setInt(12, rca.ccb_prod);
	    	stmt.setInt(13, rca.ad_product_backlog);
	    	stmt.setInt(14, rca.ad_qa);
	    	stmt.setInt(15, rca.ad_uat);
	    	stmt.setInt(16, rca.ad_prod);
	    	stmt.setInt(17, rca.dup_product_backlog);
	    	stmt.setInt(18, rca.dup_qa);
	    	stmt.setInt(19, rca.dup_uat);
	    	stmt.setInt(20, rca.dup_prod);
	    	stmt.setInt(21, rca.nad_product_backlog);
	    	stmt.setInt(22, rca.nad_qa);
	    	stmt.setInt(23, rca.nad_uat);
	    	stmt.setInt(24, rca.nad_prod);
	    	stmt.setInt(25, rca.bsi_product_backlog);
	    	stmt.setInt(26, rca.bsi_qa);
	    	stmt.setInt(27, rca.bsi_uat);
	    	stmt.setInt(28, rca.bsi_prod);
	    	stmt.setInt(29, rca.utr_product_backlog);
	    	stmt.setInt(30, rca.utr_qa);
	    	stmt.setInt(31, rca.utr_uat);
	    	stmt.setInt(32, rca.utr_prod);
	    	stmt.setInt(33, rca.pd_product_backlog);
	    	stmt.setInt(34, rca.pd_qa);
	    	stmt.setInt(35, rca.pd_uat);	    
	    	stmt.setInt(36, rca.pd_prod);
	    	stmt.setInt(37, rca.di_product_backlog);
	    	stmt.setInt(38, rca.di_qa);
	    	stmt.setInt(39, rca.di_uat);
	    	stmt.setInt(40, rca.di_prod);
	    	
	    	stmt.setInt(41, rca.ro_qa);
	    	stmt.setInt(42, rca.ro_uat);
	    	stmt.setInt(43, rca.ro_prod);	    	
	    	stmt.setInt(44, rca.plan_product_backlog);
	    	stmt.setInt(45, rca.plan_qa);
	    	stmt.setInt(46, rca.plan_uat);
	    	stmt.setInt(47, rca.plan_prod);
	    	
	    	stmt.setInt(48, rca.rate_product_backlog);
	    	stmt.setInt(49, rca.rate_qa);
	    	stmt.setInt(50, rca.rate_uat);
	    	stmt.setInt(51, rca.rate_prod);
			stmt.setInt(52, rca.rpa_product_backlog);
	    	stmt.setInt(53, rca.rpa_qa);
	    	stmt.setInt(54, rca.rpa_uat);
	    	stmt.setInt(55, rca.rpa_prod);
			stmt.setInt(56, rca.ac_product_backlog);
	    	stmt.setInt(57, rca.ac_qa);
	    	stmt.setInt(58, rca.ac_uat);
	    	stmt.setInt(59, rca.ac_prod);
			stmt.setInt(60, rca.ti_product_backlog);
	    	stmt.setInt(61, rca.ti_qa);
	    	stmt.setInt(62, rca.ti_uat);
	    	stmt.setInt(63, rca.ti_prod);
			stmt.setInt(64, rca.dp_product_backlog);
	    	stmt.setInt(65, rca.dp_qa);
	    	stmt.setInt(66, rca.dp_uat);
	    	stmt.setInt(67, rca.dp_prod);
			stmt.setInt(68, rca.env_product_backlog);
	    	stmt.setInt(69, rca.env_qa);
	    	stmt.setInt(70, rca.env_uat);
	    	stmt.setInt(71, rca.env_prod);
			stmt.setInt(72, rca.co_product_backlog);
	    	stmt.setInt(73, rca.co_qa);
	    	stmt.setInt(74, rca.co_uat);
	    	stmt.setInt(75, rca.co_prod);
	    
	    	stmt.setInt(76, rca.ffm_product_backlog);
	    	stmt.setInt(77, rca.ffm_qa);
	    	stmt.setInt(78, rca.ffm_uat);
	    	stmt.setInt(79, rca.ffm_prod);
			stmt.setInt(80, rca.crmesb_product_backlog);
	    	stmt.setInt(81, rca.crmesb_qa);
	    	stmt.setInt(82, rca.crmesb_uat);
	    	stmt.setInt(83, rca.crmesb_prod);
			stmt.setInt(84, rca.otp_product_backlog);
	    	stmt.setInt(85, rca.otp_qa);
	    	stmt.setInt(86, rca.otp_uat);
	    	stmt.setInt(87, rca.otp_prod);
			stmt.setInt(88, rca.pmuu_product_backlog);
	    	stmt.setInt(89, rca.pmuu_qa);
	    	stmt.setInt(90, rca.pmuu_uat);
	    	stmt.setInt(91, rca.pmuu_prod);
			stmt.setInt(92, rca.io_product_backlog);
	    	stmt.setInt(93, rca.io_qa);
	    	stmt.setInt(94, rca.io_uat);
	    	stmt.setInt(95, rca.io_prod);
	    
	    	stmt.setString(96, rca.week);
	    	stmt.setInt(97, rca.project_id);
	    	
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
	            	rca1.setDi_product_backlog(rs.getInt("di_product_backlog"));
	            	rca1.setDi_qa(rs.getInt("di_qa"));
	            	rca1.setDi_uat(rs.getInt("di_uat"));
	            	rca1.setDi_prod(rs.getInt("di_prod"));	            	
	            	rca1.setRo_qa(rs.getInt("ro_qa"));
	            	rca1.setRo_uat(rs.getInt("ro_uat"));
	            	rca1.setRo_prod(rs.getInt("ro_prod"));
	            	rca1.setProject_id(projectId);
	            	
	            	rca1.setPlan_product_backlog(rs.getInt("plan_product_backlog"));
	            	rca1.setPlan_qa(rs.getInt("plan_qa"));
	            	rca1.setPlan_uat(rs.getInt("plan_uat"));
	            	rca1.setPlan_prod(rs.getInt("plan_prod"));
	            	rca1.setRate_product_backlog(rs.getInt("rate_product_backlog"));
	            	rca1.setRate_qa(rs.getInt("rate_qa"));
	            	rca1.setRate_uat(rs.getInt("rate_uat"));
	            	rca1.setRate_prod(rs.getInt("rate_prod"));
	            	rca1.setRpa_product_backlog(rs.getInt("rpa_product_backlog"));
	            	rca1.setRpa_qa(rs.getInt("rpa_qa"));
	            	rca1.setRpa_uat(rs.getInt("rpa_uat"));
	            	rca1.setRpa_prod(rs.getInt("rpa_prod"));
	            	rca1.setAc_product_backlog(rs.getInt("ac_product_backlog"));
	            	rca1.setAc_qa(rs.getInt("ac_qa"));
	            	rca1.setAc_uat(rs.getInt("ac_uat"));
	            	rca1.setAc_prod(rs.getInt("ac_prod"));
	            	rca1.setTi_product_backlog(rs.getInt("ti_product_backlog"));
	            	rca1.setTi_qa(rs.getInt("ti_qa"));
	            	rca1.setTi_uat(rs.getInt("ti_uat"));
	            	rca1.setTi_prod(rs.getInt("ti_prod"));
	            	rca1.setDp_product_backlog(rs.getInt("dp_product_backlog"));
	            	rca1.setDp_qa(rs.getInt("dp_qa"));
	            	rca1.setDp_uat(rs.getInt("dp_uat"));
	            	rca1.setDp_prod(rs.getInt("dp_prod"));
	            	rca1.setEnv_product_backlog(rs.getInt("env_product_backlog"));
	            	rca1.setEnv_qa(rs.getInt("env_qa"));
	            	rca1.setEnv_uat(rs.getInt("env_uat"));
	            	rca1.setEnv_prod(rs.getInt("env_prod"));
	            	rca1.setCo_product_backlog(rs.getInt("co_product_backlog"));
	            	rca1.setCo_qa(rs.getInt("co_qa"));
	            	rca1.setCo_uat(rs.getInt("co_uat"));
	            	rca1.setCo_prod(rs.getInt("co_prod"));
	            	rca1.setFfm_product_backlog(rs.getInt("ffm_product_backlog"));
	            	rca1.setFfm_qa(rs.getInt("ffm_qa"));
	            	rca1.setFfm_uat(rs.getInt("ffm_uat"));
	            	rca1.setFfm_prod(rs.getInt("ffm_prod"));
	            	rca1.setCrmesb_product_backlog(rs.getInt("crmesb_product_backlog"));
	            	rca1.setCrmesb_qa(rs.getInt("crmesb_qa"));
	            	rca1.setCrmesb_uat(rs.getInt("crmesb_uat"));
	            	rca1.setCrmesb_prod(rs.getInt("crmesb_prod"));
	            	rca1.setOtp_product_backlog(rs.getInt("otp_product_backlog"));
	            	rca1.setOtp_qa(rs.getInt("otp_qa"));
	            	rca1.setOtp_uat(rs.getInt("otp_uat"));
	            	rca1.setOtp_prod(rs.getInt("otp_prod"));
	            	rca1.setPmuu_product_backlog(rs.getInt("pmuu_product_backlog"));
	            	rca1.setPmuu_qa(rs.getInt("pmuu_qa"));
	            	rca1.setPmuu_uat(rs.getInt("pmuu_uat"));
	            	rca1.setPmuu_prod(rs.getInt("pmuu_prod"));
	            	rca1.setIo_product_backlog(rs.getInt("io_product_backlog"));
	            	rca1.setIo_qa(rs.getInt("io_qa"));
	            	rca1.setIo_uat(rs.getInt("io_uat"));
	            	rca1.setIo_prod(rs.getInt("io_prod"));
	            	
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
            	rca.setDi_product_backlog(rs.getInt("di_product_backlog"));
            	rca.setDi_qa(rs.getInt("di_qa"));
            	rca.setDi_uat(rs.getInt("di_uat"));
            	rca.setDi_prod(rs.getInt("di_prod"));
            	
            	rca.setRo_qa(rs.getInt("ro_qa"));
            	rca.setRo_uat(rs.getInt("ro_uat"));
            	rca.setRo_prod(rs.getInt("ro_prod"));
            	
            	rca.setPlan_product_backlog(rs.getInt("plan_product_backlog"));
            	rca.setPlan_qa(rs.getInt("plan_qa"));
            	rca.setPlan_uat(rs.getInt("plan_uat"));
            	rca.setPlan_prod(rs.getInt("plan_prod"));
            	rca.setRate_product_backlog(rs.getInt("rate_product_backlog"));
            	rca.setRate_qa(rs.getInt("rate_qa"));
            	rca.setRate_uat(rs.getInt("rate_uat"));
            	rca.setRate_prod(rs.getInt("rate_prod"));
            	rca.setRpa_product_backlog(rs.getInt("rpa_product_backlog"));
            	rca.setRpa_qa(rs.getInt("rpa_qa"));
            	rca.setRpa_uat(rs.getInt("rpa_uat"));
            	rca.setRpa_prod(rs.getInt("rpa_prod"));
            	rca.setAc_product_backlog(rs.getInt("ac_product_backlog"));
            	rca.setAc_qa(rs.getInt("ac_qa"));
            	rca.setAc_uat(rs.getInt("ac_uat"));
            	rca.setAc_prod(rs.getInt("ac_prod"));
            	rca.setTi_product_backlog(rs.getInt("ti_product_backlog"));
            	rca.setTi_qa(rs.getInt("ti_qa"));
            	rca.setTi_uat(rs.getInt("ti_uat"));
            	rca.setTi_prod(rs.getInt("ti_prod"));
            	rca.setDp_product_backlog(rs.getInt("dp_product_backlog"));
            	rca.setDp_qa(rs.getInt("dp_qa"));
            	rca.setDp_uat(rs.getInt("dp_uat"));
            	rca.setDp_prod(rs.getInt("dp_prod"));
            	rca.setEnv_product_backlog(rs.getInt("env_product_backlog"));
            	rca.setEnv_qa(rs.getInt("env_qa"));
            	rca.setEnv_uat(rs.getInt("env_uat"));
            	rca.setEnv_prod(rs.getInt("env_prod"));
            	rca.setCo_product_backlog(rs.getInt("co_product_backlog"));
            	rca.setCo_qa(rs.getInt("co_qa"));
            	rca.setCo_uat(rs.getInt("co_uat"));
            	rca.setCo_prod(rs.getInt("co_prod"));
            	rca.setFfm_product_backlog(rs.getInt("ffm_product_backlog"));
            	rca.setFfm_qa(rs.getInt("ffm_qa"));
            	rca.setFfm_uat(rs.getInt("ffm_uat"));
            	rca.setFfm_prod(rs.getInt("ffm_prod"));
            	rca.setCrmesb_product_backlog(rs.getInt("crmesb_product_backlog"));
            	rca.setCrmesb_qa(rs.getInt("crmesb_qa"));
            	rca.setCrmesb_uat(rs.getInt("crmesb_uat"));
            	rca.setCrmesb_prod(rs.getInt("crmesb_prod"));
            	rca.setOtp_product_backlog(rs.getInt("otp_product_backlog"));
            	rca.setOtp_qa(rs.getInt("otp_qa"));
            	rca.setOtp_uat(rs.getInt("otp_uat"));
            	rca.setOtp_prod(rs.getInt("otp_prod"));
            	rca.setPmuu_product_backlog(rs.getInt("pmuu_product_backlog"));
            	rca.setPmuu_qa(rs.getInt("pmuu_qa"));
            	rca.setPmuu_uat(rs.getInt("pmuu_uat"));
            	rca.setPmuu_prod(rs.getInt("pmuu_prod"));
            	rca.setIo_product_backlog(rs.getInt("io_product_backlog"));
            	rca.setIo_qa(rs.getInt("io_qa"));
            	rca.setIo_uat(rs.getInt("io_uat"));
            	rca.setIo_prod(rs.getInt("io_prod"));
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
            	rca.setDi_product_backlog(0);
            	rca.setDi_qa(0);
            	rca.setDi_uat(0);
            	rca.setDi_prod(0);
            	
            	rca.setRo_qa(0);
            	rca.setRo_uat(0);
            	rca.setRo_prod(0);
            	
            	rca.setPlan_product_backlog(0);
            	rca.setPlan_qa(0);
            	rca.setPlan_uat(0);
            	rca.setPlan_prod(0);
            	rca.setRate_product_backlog(0);
            	rca.setRate_qa(0);
            	rca.setRate_uat(0);
            	rca.setRate_prod(0);
            	rca.setRpa_product_backlog(0);
            	rca.setRpa_qa(0);
            	rca.setRpa_uat(0);
            	rca.setRpa_prod(0);
            	rca.setAc_product_backlog(0);
            	rca.setAc_qa(0);
            	rca.setAc_uat(0);
            	rca.setAc_prod(0);
            	rca.setTi_product_backlog(0);
            	rca.setTi_qa(0);
            	rca.setTi_uat(0);
            	rca.setTi_prod(0);
            	rca.setDp_product_backlog(0);
            	rca.setDp_qa(0);
            	rca.setDp_uat(0);
            	rca.setDp_prod(0);
            	rca.setEnv_product_backlog(0);
            	rca.setEnv_qa(0);
            	rca.setEnv_uat(0);
            	rca.setEnv_prod(0);
            	rca.setCo_product_backlog(0);
            	rca.setCo_qa(0);
            	rca.setCo_uat(0);
            	rca.setCo_prod(0);
            	rca.setFfm_product_backlog(0);
            	rca.setFfm_qa(0);
            	rca.setFfm_uat(0);
            	rca.setFfm_prod(0);
            	rca.setCrmesb_product_backlog(0);
            	rca.setCrmesb_qa(0);
            	rca.setCrmesb_uat(0);
            	rca.setCrmesb_prod(0);
            	rca.setOtp_product_backlog(0);
            	rca.setOtp_qa(0);
            	rca.setOtp_uat(0);
            	rca.setOtp_prod(0);
            	rca.setPmuu_product_backlog(0);
            	rca.setPmuu_qa(0);
            	rca.setPmuu_uat(0);
            	rca.setPmuu_prod(0);
            	rca.setIo_product_backlog(0);
            	rca.setIo_qa(0);
            	rca.setIo_uat(0);
            	rca.setIo_prod(0);
            }
            conn.close();
	    }
	    catch(SQLException e)
	    {	    
	    	System.out.println("problem in geting rca details");
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
  
  public static ArrayList<String> getProjectDetails() throws SQLException{
	  Connection conn  = null;
	  ArrayList<String> projectList = new ArrayList<String>();
	  conn = ConnectionProvider.getConnection();	 
  	String sql1;
  	sql1 = "select * from project_details";
  	
  	PreparedStatement  stmt = conn.prepareStatement(sql1);	    	    		    	
  	ResultSet rs = stmt.executeQuery();
  	
  	while(rs.next()){
  		projectList.add(rs.getString("project_name"));
  		
  	}
  	return projectList;
  }
  
  public static ArrayList<RCA> getProjectsDetails() throws SQLException{
	  Connection conn  = null;
	  ArrayList<RCA> projectList = new ArrayList<RCA>();
	  conn = ConnectionProvider.getConnection();
	 
  	String sql1;
  	sql1 = "select * from project_details";
  	
  	PreparedStatement  stmt = conn.prepareStatement(sql1);	    	    		    	
  	ResultSet rs = stmt.executeQuery();
  	
  	while(rs.next()){
    RCA rca = new RCA();
  	rca.setProjectName(rs.getString("project_name"));
  	rca.setProjectStatus(rs.getString("status"));
  	projectList.add(rca);	
  	}
  return projectList;
  }
  
	public static String addProject(String pName, String pStatus)
			throws SQLException {
		Connection conn = null;
		conn = ConnectionProvider.getConnection();
		String sql1;
		PreparedStatement stmt;
		boolean isExist = false;
		String updateStatus = "";
		sql1 = "select * from project_details where project_name = ?";
		stmt = conn.prepareStatement(sql1);
		stmt.setString(1, pName);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			isExist = true;
			updateStatus = "exist";
			break;
		}
		if (!isExist) {
			String query = " insert into project_details (project_name, status)"
					+ " values (?, ?)";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, pName);
			stmt.setString(2, pStatus.toLowerCase());
			stmt.execute();
			updateStatus = "inserted";
		}
		return updateStatus;
	}

}
