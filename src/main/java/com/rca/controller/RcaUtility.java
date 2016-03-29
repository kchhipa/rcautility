package com.rca.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.Pattern;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.Border;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.rca.dao.RcaUtilityDao;
import com.rca.entity.ProjectDetails;
import com.rca.entity.RCA;
import com.rca.service.ProjectDetailsManager;



@SuppressWarnings("deprecation")
public class RcaUtility extends ActionSupport implements ModelDriven<RCA>,SessionAware{
	InputStream fileInputStream;
	private RCA rca=new RCA();
	boolean isdisabled = true;
	private String weekStr = null;
	private File rcaFile;
	private static Map projectNameWithId;
	private static Map session;
	ArrayList<RCA> projectList;
	String week2;
	String weekInterval;
	private ProjectDetailsManager projectDetailsManager;
	
	
		

	public ProjectDetailsManager getProjectDetailsManager() {
		return projectDetailsManager;
	}

	public void setProjectDetailsManager(ProjectDetailsManager projectDetailsManager) {
		this.projectDetailsManager = projectDetailsManager;
	}

	public String getWeekInterval() {
		return weekInterval;
	}

	public void setWeekInterval(String weekInterval) {
		this.weekInterval = weekInterval;
	}

	public String execute()
	{		
		RcaUtilityDao.getRcaDetail(rca);
		getWeekDates(rca.week);
		String role = (String) session.get("role");
		if(role.equals("manager"))
			isdisabled = false;
		
		return SUCCESS;
	}
	
	public String importData()
	{
		RcaUtilityDao.getRcaDetail(rca);
		getWeekDates(rca.week);
		return SUCCESS;
	}
	
	public String submitAddProject() throws SQLException{
		
		String daoInfo = RcaUtilityDao.addProject(rca.getProjectName(), rca.getProjectStatus());
		if(daoInfo.equals("inserted")){
			addActionMessage("Project name added successfuly");
			return SUCCESS;
		}
		else{
			addActionMessage("Project name already exist");
			return ERROR;
		}
	}
	
	public String viewAddProject(){
		rca.setProjectStatus("Active");
		return SUCCESS;
	}

	
	public String reportRcaView() throws SQLException {
		rca.setWeekType("Weekly");
		List<String> weeks = findWeeks();
		rca.setWeeks(weeks);
		ArrayList<String> projectList = RcaUtilityDao.getProjectDetails(); 
		rca.setProjectList(projectList);
		return SUCCESS;
	}
	
	public String changeProjectStatus() throws SQLException{
        
        RcaUtilityDao.updateProjectStatus(rca.getProjectName(), rca.getProjectStatus());
        return SUCCESS;            
	}
	
	public String showProjectDetails() throws SQLException {
		   projectList = RcaUtilityDao.getProjectsDetails();
		   Collections.sort(projectList, new RCAComparator());
		   rca.setProjectDetailList(projectList);
		   return SUCCESS;
		}
	
	public static class RCAComparator implements Comparator<RCA>
	  {
	    public int compare(RCA rca1, RCA rca2) {
	      if(rca1 == null && rca2 == null)
	        return 0;
	      if(rca1 == null && rca2 != null)
	        return -1;
	      if(rca1 != null && rca2 == null)
	        return 1;
	      return rca1.getProjectStatus().compareTo(rca2.getProjectStatus());
	    }
	  }
	
	public static List<String> findWeeks(){
	    List<String> weeks = new ArrayList<String>();
	    SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");

	    Calendar c1 = Calendar.getInstance();
	    c1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	    c1.add(Calendar.WEEK_OF_MONTH, -1);
	    for (int i = 0; i < 12; i++) {
	          String startDate = formatter.format(c1.getTime());
	          c1.add(Calendar.DAY_OF_WEEK, +6);
	          String endDate = formatter.format(c1.getTime());
	          String finalRange = startDate + "-" + endDate;
	          weeks.add(finalRange);
	          c1.add(Calendar.DAY_OF_WEEK, -14);
	          c1.add(Calendar.DAY_OF_WEEK, 1);
	    }

	    return weeks;
	}

	public static void main(String s[]){
		System.out.println(findWeeks());
	}
	
	public String templateDownload() throws IOException, WriteException
	{
		File file=new File("RCA.xlsx");
		WorkbookSettings wbSettings = new WorkbookSettings();

	    wbSettings.setLocale(new Locale("en", "EN"));

	    WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
	    WritableSheet sheet= workbook.createSheet("RCA", 0);	    
	    WritableCellFormat cellFormatRcaType=getCellFormatRcaType(Colour.GRAY_25, Pattern.GRAY_75);    
	    getRcaTypeLabelsForTemplateDownload(sheet, cellFormatRcaType,0,0);
	    getDefectOriginLabels(sheet, cellFormatRcaType);
	    
	    workbook.write();
	    fileInputStream = new FileInputStream(new File("RCA.xlsx")); 
	    
	    workbook.close();  
	    
		return "success";
	}
	public String templateUpload() throws IOException, WriteException, BiffException
	{
		
		Workbook workbook = Workbook.getWorkbook(rcaFile);
		 Sheet sheet = workbook.getSheet(0);
	    
		 int row = 0;		 
		 int col=0;
		 
         setTemplateData(sheet, row, col);
         
         String result = RcaUtilityDao.saveRcaDetails(rca);
 		
 		if(result.equals("success")||result.equals("updated"))
 			addActionMessage("Rca data uploaded successfully");
 		else if(result.equals("udpateFailure"))
 			addActionMessage("Problem in uploading Rca data");
 		else
 			addActionMessage("Problem in uploading Rca data");
 		
 		getWeekDates(rca.week);
		 
	    workbook.close();  	    
		return "success";
	}

	private void setTemplateData(Sheet sheet, int row, int col) {
		Cell cell;
		cell = sheet.getCell(++col, ++row);        
		 rca.setMr_qa(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));      
         cell = sheet.getCell(++col, row);        
		 rca.setMr_uat(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));  
		 cell = sheet.getCell(++col, row);        
		 rca.setMr_prod(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0")); 
		 cell = sheet.getCell(++col, row);        
		 rca.setMr_product_backlog(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));
         
		 col=0;
		 cell = sheet.getCell(++col, ++row);        
		 rca.setCr_qa(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));      
         cell = sheet.getCell(++col, row);        
		 rca.setCr_uat(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));  
		 cell = sheet.getCell(++col, row);        
		 rca.setCr_prod(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0")); 
		 cell = sheet.getCell(++col, row);        
		 rca.setCr_product_backlog(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));
		 
		 col=0;
		 cell = sheet.getCell(++col, ++row);        
		 rca.setPlan_qa(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));      
         cell = sheet.getCell(++col, row);        
		 rca.setPlan_uat(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));  
		 cell = sheet.getCell(++col, row);        
		 rca.setPlan_prod(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0")); 
		 cell = sheet.getCell(++col, row);        
		 rca.setPlan_product_backlog(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));
		 
		 col=0;
		 cell = sheet.getCell(++col, ++row);        
		 rca.setRate_qa(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));      
         cell = sheet.getCell(++col, row);        
		 rca.setRate_uat(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));  
		 cell = sheet.getCell(++col, row);        
		 rca.setRate_prod(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0")); 
		 cell = sheet.getCell(++col, row);        
		 rca.setRate_product_backlog(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));
		 
		 col=0;
		 cell = sheet.getCell(++col, ++row);        
		 rca.setRpa_qa(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));      
         cell = sheet.getCell(++col, row);        
		 rca.setRpa_uat(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));  
		 cell = sheet.getCell(++col, row);        
		 rca.setRpa_prod(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0")); 
		 cell = sheet.getCell(++col, row);        
		 rca.setRpa_product_backlog(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));
		 
		 col=0;
		 cell = sheet.getCell(++col, ++row);        
		 rca.setAc_qa(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));      
         cell = sheet.getCell(++col, row);        
		 rca.setAc_uat(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));  
		 cell = sheet.getCell(++col, row);        
		 rca.setAc_prod(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0")); 
		 cell = sheet.getCell(++col, row);        
		 rca.setAc_product_backlog(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));
		 
		 col=0;
		 cell = sheet.getCell(++col, ++row);        
		 rca.setTi_qa(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));      
         cell = sheet.getCell(++col, row);        
		 rca.setTi_uat(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));  
		 cell = sheet.getCell(++col, row);        
		 rca.setTi_prod(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0")); 
		 cell = sheet.getCell(++col, row);        
		 rca.setTi_product_backlog(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));
		 
		 col=0;
		 cell = sheet.getCell(++col, ++row);        
		 rca.setDp_qa(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));      
         cell = sheet.getCell(++col, row);        
		 rca.setDp_uat(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));  
		 cell = sheet.getCell(++col, row);        
		 rca.setDp_prod(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0")); 
		 cell = sheet.getCell(++col, row);        
		 rca.setDp_product_backlog(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));
		 
		 col=0;
		 cell = sheet.getCell(++col, ++row);        
		 rca.setEnv_qa(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));      
         cell = sheet.getCell(++col, row);        
		 rca.setEnv_uat(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));  
		 cell = sheet.getCell(++col, row);        
		 rca.setEnv_prod(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0")); 
		 cell = sheet.getCell(++col, row);        
		 rca.setEnv_product_backlog(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));
		 
		 col=0;
		 cell = sheet.getCell(++col, ++row);        
		 rca.setCo_qa(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));      
         cell = sheet.getCell(++col, row);        
		 rca.setCo_uat(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));  
		 cell = sheet.getCell(++col, row);        
		 rca.setCo_prod(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0")); 
		 cell = sheet.getCell(++col, row);        
		 rca.setCo_product_backlog(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));
         
		 col=0;
		 cell = sheet.getCell(++col, ++row);        
		 rca.setCcb_qa(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));      
         cell = sheet.getCell(++col, row);        
		 rca.setCcb_uat(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));  
		 cell = sheet.getCell(++col, row);        
		 rca.setCcb_prod(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0")); 
		 cell = sheet.getCell(++col, row);        
		 rca.setCcb_product_backlog(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));
		 
		 col=0;
		 cell = sheet.getCell(++col, ++row);        
		 rca.setAd_qa(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));      
         cell = sheet.getCell(++col, row);        
		 rca.setAd_uat(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));  
		 cell = sheet.getCell(++col, row);        
		 rca.setAd_prod(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0")); 
		 cell = sheet.getCell(++col, row);        
		 rca.setAd_product_backlog(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));
		
		 col=0;
		 cell = sheet.getCell(++col, ++row);        
		 rca.setDup_qa(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));      
         cell = sheet.getCell(++col, row);        
		 rca.setDup_uat(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));  
		 cell = sheet.getCell(++col, row);        
		 rca.setDup_prod(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0")); 
		 cell = sheet.getCell(++col, row);        
		 rca.setDup_product_backlog(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));

		 col=0;
		 cell = sheet.getCell(++col, ++row);        
		 rca.setNad_qa(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));      
         cell = sheet.getCell(++col, row);        
		 rca.setNad_uat(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));  
		 cell = sheet.getCell(++col, row);        
		 rca.setNad_prod(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0")); 
		 cell = sheet.getCell(++col, row);        
		 rca.setNad_product_backlog(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));

		 col=0;
		 cell = sheet.getCell(++col, ++row);        
		 rca.setBsi_qa(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));      
         cell = sheet.getCell(++col, row);        
		 rca.setBsi_uat(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));  
		 cell = sheet.getCell(++col, row);        
		 rca.setBsi_prod(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0")); 
		 cell = sheet.getCell(++col, row);        
		 rca.setBsi_product_backlog(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));

		 col=0;
		 cell = sheet.getCell(++col, ++row);        
		 rca.setUtr_qa(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));      
         cell = sheet.getCell(++col, row);        
		 rca.setUtr_uat(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));  
		 cell = sheet.getCell(++col, row);        
		 rca.setUtr_prod(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0")); 
		 cell = sheet.getCell(++col, row);        
		 rca.setUtr_product_backlog(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));

		 col=0;
		 cell = sheet.getCell(++col, ++row);        
		 rca.setPd_qa(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));      
         cell = sheet.getCell(++col, row);        
		 rca.setPd_uat(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));  
		 cell = sheet.getCell(++col, row);        
		 rca.setPd_prod(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0")); 
		 cell = sheet.getCell(++col, row);        
		 rca.setPd_product_backlog(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));

		 col=0;
		 cell = sheet.getCell(++col, ++row);        
		 rca.setFfm_qa(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));      
         cell = sheet.getCell(++col, row);        
		 rca.setFfm_uat(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));  
		 cell = sheet.getCell(++col, row);        
		 rca.setFfm_prod(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0")); 
		 cell = sheet.getCell(++col, row);        
		 rca.setFfm_product_backlog(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));
		 
		 col=0;
		 cell = sheet.getCell(++col, ++row);        
		 rca.setCrmesb_qa(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));      
         cell = sheet.getCell(++col, row);        
		 rca.setCrmesb_uat(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));  
		 cell = sheet.getCell(++col, row);        
		 rca.setCrmesb_prod(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0")); 
		 cell = sheet.getCell(++col, row);        
		 rca.setCrmesb_product_backlog(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));
		 
		 col=0;
		 cell = sheet.getCell(++col, ++row);        
		 rca.setOtp_qa(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));      
         cell = sheet.getCell(++col, row);        
		 rca.setOtp_uat(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));  
		 cell = sheet.getCell(++col, row);        
		 rca.setOtp_prod(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0")); 
		 cell = sheet.getCell(++col, row);        
		 rca.setOtp_product_backlog(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));
		 
		 col=0;
		 cell = sheet.getCell(++col, ++row);        
		 rca.setPmuu_qa(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));      
         cell = sheet.getCell(++col, row);        
		 rca.setPmuu_uat(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));  
		 cell = sheet.getCell(++col, row);        
		 rca.setPmuu_prod(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0")); 
		 cell = sheet.getCell(++col, row);        
		 rca.setPmuu_product_backlog(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));
		 
		 col=0;
		 cell = sheet.getCell(++col, ++row);        
		 rca.setIo_qa(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));      
         cell = sheet.getCell(++col, row);        
		 rca.setIo_uat(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));  
		 cell = sheet.getCell(++col, row);        
		 rca.setIo_prod(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0")); 
		 cell = sheet.getCell(++col, row);        
		 rca.setIo_product_backlog(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));
		
		 col=0;
		 cell = sheet.getCell(++col, ++row);        
		 rca.setDi_qa(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));      
         cell = sheet.getCell(++col, row);        
		 rca.setDi_uat(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));  
		 cell = sheet.getCell(++col, row);        
		 rca.setDi_prod(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0")); 
		 cell = sheet.getCell(++col, row);        
		 rca.setDi_product_backlog(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));
		 
		 col=0;
		 cell = sheet.getCell(++col, ++row);        
		 rca.setRo_qa(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));      
         cell = sheet.getCell(++col, row);        
		 rca.setRo_uat(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));  
		 cell = sheet.getCell(++col, row);        
		 rca.setRo_prod(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0")); 	
		 
		 /* Changes for Non RCA field addition */
		 col=0;
		 cell = sheet.getCell(++col, ++row);        
		 rca.setNr_qa(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));      
         cell = sheet.getCell(++col, row);        
		 rca.setNr_uat(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));  
		 cell = sheet.getCell(++col, row);        
		 rca.setNr_prod(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0")); 
		 cell = sheet.getCell(++col, row);        
		 rca.setNr_product_backlog(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));
		 
		 /* Changes for Close Ticket field addition */
		 col=0;
		 cell = sheet.getCell(++col, ++row);        
		 rca.setClose_qa(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));      
         cell = sheet.getCell(++col, row);        
		 rca.setClose_uat(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));  
		 cell = sheet.getCell(++col, row);        
		 rca.setClose_prod(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0")); 
		 cell = sheet.getCell(++col, row);        
		 rca.setClose_product_backlog(Integer.valueOf(cell.getContents()!=""?cell.getContents():"0"));
	}
	
	
	
	private void getDefectOriginLabels(WritableSheet sheet,
			WritableCellFormat cellFormatRcaType) throws RowsExceededException, WriteException {
		int i=0;
		Label label=null;
	    label = new Label(++i,0, "QA",cellFormatRcaType);
	    sheet.addCell(label);
	    label = new Label(++i,0, "UAT",cellFormatRcaType);
	    sheet.addCell(label);	    
	    label = new Label(++i,0, "PROD",cellFormatRcaType);
	    sheet.addCell(label);
	    label = new Label(++i,0, "OPEN",cellFormatRcaType);
	    sheet.addCell(label);
		
	}

	public String saveRcaDetail()
	{
		String result = RcaUtilityDao.saveRcaDetails(rca);
		
		if(result.equals("success"))
			addActionMessage("Rca data submitted successfully.");
		else if(result.equals("updated"))
			addActionMessage("Rca data updated successfully.");
		else if(result.equals("udpateFailure"))
			addActionError("Problem in updating Rca data.");
		else
			addActionError("Problem in submitting Rca data.");
		
		getWeekDates(rca.week);
		
		return "success";
	}
	
	/*public String udpateRcaDetail()
	{
		String resultUpdate = RcaUtilityDao.updateRcaDetailsDao(rca);
		if(resultUpdate.equals("success"))
			addActionMessage("Rca data udpated successfully");
		else
			addActionMessage("Problem in updating Rca data");
		
		getWeekDates(rca.week);
		
		return "success";
	}*/
	private void getWeekDates(String weekdates)
	{
		String dates[] = null;
		Date currentDate = new Date();
		long diffDays=0;
		if(weekdates!=null && !weekdates.isEmpty()){
			if(weekdates.contains("-"))
			{
				dates = weekdates.split("-");	
			    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				try {			 
						Date date = formatter.parse(dates[1]);								
						long diff = currentDate.getTime() - date.getTime();			 		
						diffDays = diff / (24 * 60 * 60 * 1000);
				}
				catch (ParseException e)
				{
					e.printStackTrace();
				}
				String role = (String) session.get("role");
				if(diffDays<2 || role.equals("manager"))
					isdisabled = false;
				Calendar cal = Calendar.getInstance();
			    cal.setTime(currentDate);
				weekStr = weekdates.replaceAll("/"+cal.get(Calendar.YEAR), "");
			}
		}
	}

	public String exportData() throws IOException, RowsExceededException,
			WriteException, BiffException {
		List<RCA> rcaList = null;
		if (rca.week == null) {
			addActionMessage("Week is Mandatory");
		} else {
			File file = new File("RCA.xlsx");
			WorkbookSettings wbSettings = new WorkbookSettings();
			wbSettings.setLocale(new Locale("en", "EN"));
			WritableWorkbook workbook = Workbook.createWorkbook(file,wbSettings);
			WritableSheet sheet = workbook.createSheet("RCA", 0);
			WritableCellFormat cellFormatProject = getCellFormatRcaType(Colour.GRAY_25, Pattern.GRAY_75);
			WritableCellFormat cellFormatRcaType = getCellFormatRcaType(Colour.GRAY_25, Pattern.GRAY_75);
			
			List<ProjectDetails>projectList=projectDetailsManager.getAllProjects();

			String[] weekIntervals = this.weekInterval!= null ? this.weekInterval.split(","): new String[]{rca.week};
			int rowNumber = 2;
			for (String week : weekIntervals) {
				getProjectLabels(sheet, cellFormatProject, rowNumber, projectList, week);
				getRcaTypeLabelsForExport(sheet, cellFormatRcaType, rowNumber-1, 1);
				rcaList = RcaUtilityDao.getRcaDetailList(week,projectList);
				if (rcaList != null && rcaList.size() > 0) {
					writeRcaData(sheet, rcaList, rowNumber, projectList);
				}
				rowNumber = rowNumber+28;  // Changes for Non RCA Bug field addition
			}
			workbook.write();
			fileInputStream = new FileInputStream(new File("RCA.xlsx"));
			workbook.close();
		}
		return "success";

	}

	private void writeRcaData(WritableSheet sheet, List<RCA> rcaList, int rowNumber, List<ProjectDetails> projectList) throws RowsExceededException, WriteException {
		int row=rowNumber;
		int col=1;
		boolean flag = true;
		
		int total_qa[] = new int[28];
		int total_uat[] = new int[28];
		int total_prod[] = new int[28];
		int total_product_backlog[] = new int[28];
			int i = 0;
			for (RCA rca : rcaList) {
				total_qa[0] = total_qa[0] +rca.mr_qa;
				total_qa[1] = total_qa[1] +rca.cr_qa;
				total_qa[2] = total_qa[2] ;
				total_qa[3] = total_qa[3] +rca.plan_qa;
				total_qa[4] = total_qa[4] +rca.rate_qa;
				total_qa[5] = total_qa[5] +rca.rpa_qa;
				total_qa[6] = total_qa[6] +rca.ac_qa;
				total_qa[7] = total_qa[7] +rca.ti_qa;
				total_qa[8] = total_qa[8] +rca.dp_qa;
				total_qa[9] = total_qa[9] +rca.env_qa;
				total_qa[10] = total_qa[10] +rca.co_qa;
				total_qa[11] = total_qa[11] +rca.ccb_qa;
				total_qa[12] = total_qa[12] +rca.ad_qa;
				total_qa[13] = total_qa[13] +rca.dup_qa;
				total_qa[14] = total_qa[14] +rca.nad_qa;
				total_qa[15] = total_qa[15] +rca.bsi_qa;
				total_qa[16] = total_qa[16] +rca.utr_qa;
				total_qa[17] = total_qa[17] +rca.pd_qa;
				total_qa[18] = total_qa[18];
				total_qa[19] = total_qa[19] +rca.ffm_qa;
				total_qa[20] = total_qa[20] +rca.crmesb_qa;
				total_qa[21] = total_qa[21] +rca.otp_qa;
				total_qa[22] = total_qa[22] +rca.pmuu_qa;
				total_qa[23] = total_qa[23] +rca.io_qa;
				total_qa[24] = total_qa[24] +rca.di_qa;
				total_qa[25] = total_qa[25] +rca.ro_qa;
				total_qa[26] = total_qa[26] +rca.nr_qa;  /* Changes for Non RCA field addition */
				total_qa[27] = total_qa[27] +rca.close_qa;  /* Changes for Close Ticket field addition */
				
				total_uat[0] = total_uat[0] +rca.mr_uat;
				total_uat[1] = total_uat[1] +rca.cr_uat;
				total_uat[2] = total_uat[2] ;
				total_uat[3] = total_uat[3] +rca.plan_uat;
				total_uat[4] = total_uat[4] +rca.rate_uat;
				total_uat[5] = total_uat[5] +rca.rpa_uat;
				total_uat[6] = total_uat[6] +rca.ac_uat;
				total_uat[7] = total_uat[7] +rca.ti_uat;
				total_uat[8] = total_uat[8] +rca.dp_uat;
				total_uat[9] = total_uat[9] +rca.env_uat;
				total_uat[10] = total_uat[10] +rca.co_uat;
				total_uat[11] = total_uat[11] +rca.ccb_uat;
				total_uat[12] = total_uat[12] +rca.ad_uat;
				total_uat[13] = total_uat[13] +rca.dup_uat;
				total_uat[14] = total_uat[14] +rca.nad_uat;
				total_uat[15] = total_uat[15] +rca.bsi_uat;
				total_uat[16] = total_uat[16] +rca.utr_uat;
				total_uat[17] = total_uat[17] +rca.pd_uat;
				total_uat[18] = total_uat[18];
				total_uat[19] = total_uat[19] +rca.ffm_uat;
				total_uat[20] = total_uat[20] +rca.crmesb_uat;
				total_uat[21] = total_uat[21] +rca.otp_uat;
				total_uat[22] = total_uat[22] +rca.pmuu_uat;
				total_uat[23] = total_uat[23] +rca.io_uat;
				total_uat[24] = total_uat[24] +rca.di_uat;
				total_uat[25] = total_uat[25] +rca.ro_uat;
				total_uat[26] = total_uat[26] +rca.nr_uat;   /* Changes for Non RCA field addition */
				total_uat[27] = total_uat[27] +rca.close_uat;   /* Changes for Close Ticket field addition */
				
				total_prod[0] = total_prod[0] +rca.mr_prod;
				total_prod[1] = total_prod[1] +rca.cr_prod;
				total_prod[2] = total_prod[2] ;
				total_prod[3] = total_prod[3] +rca.plan_prod;
				total_prod[4] = total_prod[4] +rca.rate_prod;
				total_prod[5] = total_prod[5] +rca.rpa_prod;
				total_prod[6] = total_prod[6] +rca.ac_prod;
				total_prod[7] = total_prod[7] +rca.ti_prod;
				total_prod[8] = total_prod[8] +rca.dp_prod;
				total_prod[9] = total_prod[9] +rca.env_prod;
				total_prod[10] = total_prod[10] +rca.co_prod;
				total_prod[11] = total_prod[11] +rca.ccb_prod;
				total_prod[12] = total_prod[12] +rca.ad_prod;
				total_prod[13] = total_prod[13] +rca.dup_prod;
				total_prod[14] = total_prod[14] +rca.nad_prod;
				total_prod[15] = total_prod[15] +rca.bsi_prod;
				total_prod[16] = total_prod[16] +rca.utr_prod;
				total_prod[17] = total_prod[17] +rca.pd_prod;
				total_prod[18] = total_prod[18];
				total_prod[19] = total_prod[19] +rca.ffm_prod;
				total_prod[20] = total_prod[20] +rca.crmesb_prod;
				total_prod[21] = total_prod[21] +rca.otp_prod;
				total_prod[22] = total_prod[22] +rca.pmuu_prod;
				total_prod[23] = total_prod[23] +rca.io_prod;
				total_prod[24] = total_prod[24] +rca.di_prod;
				total_prod[25] = total_prod[25] +rca.ro_prod;
				total_prod[26] = total_prod[26] +rca.nr_prod;   /* Changes for Non RCA field addition */
				total_prod[27] = total_prod[27] +rca.close_prod;   /* Changes for Close Ticket field addition */
				
				total_product_backlog[0] = total_product_backlog[0] +rca.mr_product_backlog;
				total_product_backlog[1] = total_product_backlog[1] +rca.cr_product_backlog;
				total_product_backlog[2] = total_product_backlog[2] ;
				total_product_backlog[3] = total_product_backlog[3] +rca.plan_product_backlog;
				total_product_backlog[4] = total_product_backlog[4] +rca.rate_product_backlog;
				total_product_backlog[5] = total_product_backlog[5] +rca.rpa_product_backlog;
				total_product_backlog[6] = total_product_backlog[6] +rca.ac_product_backlog;
				total_product_backlog[7] = total_product_backlog[7] +rca.ti_product_backlog;
				total_product_backlog[8] = total_product_backlog[8] +rca.dp_product_backlog;
				total_product_backlog[9] = total_product_backlog[9] +rca.env_product_backlog;
				total_product_backlog[10] = total_product_backlog[10] +rca.co_product_backlog;
				total_product_backlog[11] = total_product_backlog[11] +rca.ccb_product_backlog;
				total_product_backlog[12] = total_product_backlog[12] +rca.ad_product_backlog;
				total_product_backlog[13] = total_product_backlog[13] +rca.dup_product_backlog;
				total_product_backlog[14] = total_product_backlog[14] +rca.nad_product_backlog;
				total_product_backlog[15] = total_product_backlog[15] +rca.bsi_product_backlog;
				total_product_backlog[16] = total_product_backlog[16] +rca.utr_product_backlog;
				total_product_backlog[17] = total_product_backlog[17] +rca.pd_product_backlog;
				total_product_backlog[18] = total_product_backlog[18];
				total_product_backlog[19] = total_product_backlog[19] +rca.ffm_product_backlog;
				total_product_backlog[20] = total_product_backlog[20] +rca.crmesb_product_backlog;
				total_product_backlog[21] = total_product_backlog[21] +rca.otp_product_backlog;
				total_product_backlog[22] = total_product_backlog[22] +rca.pmuu_product_backlog;
				total_product_backlog[23] = total_product_backlog[23] +rca.io_product_backlog;
				total_product_backlog[24] = total_product_backlog[24] +rca.di_product_backlog;
				//total_product_backlog[25] = total_product_backlog[25] ;
				total_product_backlog[26] = total_product_backlog[26] +rca.nr_product_backlog;   /* Changes for Non RCA field addition */
				
				total_product_backlog[27] = total_product_backlog[27] +rca.close_product_backlog;   /* Changes for Close Ticket field addition */
			}
		
			RCA totalList = new RCA();
			totalList.mr_qa = total_qa[0]; 
			totalList.cr_qa = total_qa[1];
			totalList.plan_qa = total_qa[3];
			totalList.rate_qa = total_qa[4];
			totalList.rpa_qa = total_qa[5];
			totalList.ac_qa = total_qa[6];
			totalList.ti_qa = total_qa[7]; 
			totalList.dp_qa = total_qa[8];
			totalList.env_qa = total_qa[9];
			totalList.co_qa = total_qa[10];
			totalList.ccb_qa = total_qa[11];
			totalList.ad_qa = total_qa[12];
			totalList.dup_qa = total_qa[13]; 
			totalList.nad_qa = total_qa[14];
			totalList.bsi_qa = total_qa[15];
			totalList.utr_qa = total_qa[16];
			totalList.pd_qa = total_qa[17];
			//totalList.ac_qa = total_qa[18];
			totalList.ffm_qa = total_qa[19]; 
			totalList.crmesb_qa = total_qa[20];
			totalList.otp_qa = total_qa[21];
			totalList.pmuu_qa = total_qa[22];
			totalList.io_qa = total_qa[23];
			totalList.di_qa = total_qa[24];
			totalList.ro_qa = total_qa[25];
			totalList.nr_qa = total_qa[26];   /* Changes for Non RCA field addition */
			totalList.close_qa = total_qa[27];   /* Changes for Close Ticket field addition */
			
			totalList.mr_uat = total_uat[0]; 
			totalList.cr_uat = total_uat[1];
			totalList.plan_uat = total_uat[3];
			totalList.rate_uat = total_uat[4];
			totalList.rpa_uat = total_uat[5];
			totalList.ac_uat = total_uat[6];
			totalList.ti_uat = total_uat[7]; 
			totalList.dp_uat = total_uat[8];
			totalList.env_uat = total_uat[9];
			totalList.co_uat = total_uat[10];
			totalList.ccb_uat = total_uat[11];
			totalList.ad_uat = total_uat[12];
			totalList.dup_uat = total_uat[13]; 
			totalList.nad_uat = total_uat[14];
			totalList.bsi_uat = total_uat[15];
			totalList.utr_uat = total_uat[16];
			totalList.pd_uat = total_uat[17];
			//totalList.ac_uat = total_uat[18];
			totalList.ffm_uat = total_uat[19]; 
			totalList.crmesb_uat = total_uat[20];
			totalList.otp_uat = total_uat[21];
			totalList.pmuu_uat = total_uat[22];
			totalList.io_uat = total_uat[23];
			totalList.di_uat = total_uat[24];
			totalList.ro_uat = total_uat[25];
			totalList.nr_uat = total_uat[26];   /* Changes for Non RCA field addition */
			totalList.close_uat = total_uat[27];   /* Changes for Close Ticket field addition */
			
			totalList.mr_prod = total_prod[0]; 
			totalList.cr_prod = total_prod[1];
			totalList.plan_prod = total_prod[3];
			totalList.rate_prod = total_prod[4];
			totalList.rpa_prod = total_prod[5];
			totalList.ac_prod = total_prod[6];
			totalList.ti_prod = total_prod[7]; 
			totalList.dp_prod = total_prod[8];
			totalList.env_prod = total_prod[9];
			totalList.co_prod = total_prod[10];
			totalList.ccb_prod = total_prod[11];
			totalList.ad_prod = total_prod[12];
			totalList.dup_prod = total_prod[13]; 
			totalList.nad_prod = total_prod[14];
			totalList.bsi_prod = total_prod[15];
			totalList.utr_prod = total_prod[16];
			totalList.pd_prod = total_prod[17];
			//totalList.ac_prod = total_prod[18];
			totalList.ffm_prod = total_prod[19]; 
			totalList.crmesb_prod = total_prod[20];
			totalList.otp_prod = total_prod[21];
			totalList.pmuu_prod = total_prod[22];
			totalList.io_prod = total_prod[23];
			totalList.di_prod = total_prod[24];
			totalList.ro_prod = total_prod[25];
			totalList.nr_prod = total_prod[26];   /* Changes for Non RCA field addition */
			totalList.close_prod = total_prod[27];   /* Changes for Close Ticket field addition */
			
			totalList.mr_product_backlog = total_product_backlog[0]; 
			totalList.cr_product_backlog = total_product_backlog[1];
			totalList.plan_product_backlog = total_product_backlog[3];
			totalList.rate_product_backlog = total_product_backlog[4];
			totalList.rpa_product_backlog = total_product_backlog[5];
			totalList.ac_product_backlog = total_product_backlog[6];
			totalList.ti_product_backlog = total_product_backlog[7]; 
			totalList.dp_product_backlog = total_product_backlog[8];
			totalList.env_product_backlog = total_product_backlog[9];
			totalList.co_product_backlog = total_product_backlog[10];
			totalList.ccb_product_backlog = total_product_backlog[11];
			totalList.ad_product_backlog = total_product_backlog[12];
			totalList.dup_product_backlog = total_product_backlog[13]; 
			totalList.nad_product_backlog = total_product_backlog[14];
			totalList.bsi_product_backlog = total_product_backlog[15];
			totalList.utr_product_backlog = total_product_backlog[16];
			totalList.pd_product_backlog = total_product_backlog[17];
			//totalList.ac_product_backlog = total_product_backlog[18];
			totalList.ffm_product_backlog = total_product_backlog[19]; 
			totalList.crmesb_product_backlog = total_product_backlog[20];
			totalList.otp_product_backlog = total_product_backlog[21];
			totalList.pmuu_product_backlog = total_product_backlog[22];
			totalList.io_product_backlog = total_product_backlog[23];
			totalList.di_product_backlog = total_product_backlog[24];
			//totalList.ro_product_backlog = total_product_backlog[25];
			totalList.nr_product_backlog = total_product_backlog[26];   /* Changes for Non RCA field addition */
			
			totalList.close_product_backlog = total_product_backlog[27];   /* Changes for Close Ticket field addition */
			
			List<RCA> newList = new ArrayList<RCA>();
			newList.add(totalList);
			newList.addAll(1,rcaList);
		
			Iterator<ProjectDetails> projectIterator=projectList.iterator();
			
			 while(projectIterator.hasNext()){
				 int id=projectIterator.next().getProjectId();
				 for (RCA rca :newList)
				 {
					 if(flag){
						 
						 writeExceldata(col,row,sheet,rca);
						 flag = false;
						 col=col+4;
						 
					 }
					 if(rca.project_id==id){
						 writeExceldata(col,row,sheet,rca);
						 flag = false;
					 }
				 
			 }
			 
			 col=col+4;


		}
			
	}

private void writeExceldata(int col, int row,WritableSheet sheet,RCA rca) throws RowsExceededException, WriteException{
	
	
	int misregQa=0;
	
	writeMissedReq(col,row,sheet,rca);
	writeChangeReq(col,++row,sheet,rca);
	writeConfigrationSum(col,++row,sheet,rca);
	writePlanPackage(col,++row,sheet,rca);
	writeRatePackage(col,++row,sheet,rca);
	writeRulesPlanAdvisor(col,++row,sheet,rca);
	writeAppConfig(col,++row,sheet,rca);
	writeTemplateIssue(col,++row,sheet,rca);
	writeDeploymentProperties(col,++row,sheet,rca);
	writeEnvironment(col,++row,sheet,rca);
	writeConfigOthers(col,++row,sheet,rca);
	writeClientCodeBug(col,++row,sheet,rca);
	writeAsDesigned(col,++row,sheet,rca);
	writeDuplicate(col,++row,sheet,rca);
	writeNotDefect(col,++row,sheet,rca);
	writeBrowserSpecific(col,++row,sheet,rca);
	writeUnableToRep(col,++row,sheet,rca);
	writeProductDefect(col,++row,sheet,rca);
	writeIntegrationSum(col,++row,sheet,rca);
	writeFfmIssue(col,++row,sheet,rca);
	writeCrmEsbIssue(col,++row,sheet,rca);
	writeOtherThirdPartyIssue(col,++row,sheet,rca);
	writeProductMergeUpdateUpgradeIssue(col,++row,sheet,rca);
	writeIntegrationOthers(col,++row,sheet,rca);
	writeDataIssue(col,++row,sheet,rca);
	writeReOpen(col,++row,sheet,rca);
	writeNonRcaBug(col,++row,sheet,rca);
	writeCloseTicket(col, ++row, sheet, rca);
	
}
	
private void writeReOpen(int col, int row,WritableSheet sheet,RCA rca) throws RowsExceededException, WriteException {
	Label label;
	    
    label = new Label(++col,row, String.valueOf(rca.ro_qa));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.ro_uat));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.ro_prod));
    sheet.addCell(label);    

}

/* Changes for Non RCA field addition */
/**
 * @param col
 * @param row
 * @param sheet
 * @param rca
 * @throws RowsExceededException
 * @throws WriteException
 */
private void writeNonRcaBug(int col, int row,WritableSheet sheet,RCA rca) throws RowsExceededException, WriteException {
	Label label;
	    
    label = new Label(++col,row, String.valueOf(rca.nr_qa));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.nr_uat));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.nr_prod));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.nr_product_backlog));
    sheet.addCell(label);
}

/* Changes for Close Ticket field addition */
/**
 * @param col
 * @param row
 * @param sheet
 * @param rca
 * @throws RowsExceededException
 * @throws WriteException
 */
private void writeCloseTicket(int col, int row,WritableSheet sheet,RCA rca) throws RowsExceededException, WriteException {
	Label label;
	    
    label = new Label(++col,row, String.valueOf(rca.close_qa));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.close_uat));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.close_prod));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.close_product_backlog));
    sheet.addCell(label);
}

private void writeDataIssue(int col, int row,WritableSheet sheet,RCA rca) throws RowsExceededException, WriteException {
	Label label;
	    
    label = new Label(++col,row, String.valueOf(rca.di_qa));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.di_uat));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.di_prod));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.di_product_backlog));
    sheet.addCell(label);
}

private void writeIntegrationSum(int col, int row,WritableSheet sheet,RCA rca) throws RowsExceededException, WriteException {
    Label label;
    
    label = new Label(++col,row, String.valueOf(rca.ffm_qa+rca.crmesb_qa+rca.otp_qa+rca.pmuu_qa+rca.io_qa));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.ffm_uat+rca.crmesb_uat+rca.otp_uat+rca.pmuu_uat+rca.io_uat));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.ffm_prod+rca.crmesb_prod+rca.otp_prod+rca.pmuu_prod+rca.io_prod));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.ffm_product_backlog+rca.crmesb_product_backlog+rca.otp_product_backlog+rca.pmuu_product_backlog+rca.io_product_backlog));
    sheet.addCell(label);
    
}

private void writeFfmIssue(int col, int row,WritableSheet sheet,RCA rca) throws RowsExceededException, WriteException {
	Label label;
    
    label = new Label(++col,row, String.valueOf(rca.ffm_qa));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.ffm_uat));
    sheet.addCell(label);
   
    label = new Label(++col,row, String.valueOf(rca.ffm_prod));
    sheet.addCell(label);	
    
	label = new Label(++col,row, String.valueOf(rca.ffm_product_backlog));
    sheet.addCell(label);
}

private void writeCrmEsbIssue(int col, int row,WritableSheet sheet,RCA rca) throws RowsExceededException, WriteException {
	Label label;
    
    label = new Label(++col,row, String.valueOf(rca.crmesb_qa));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.crmesb_uat));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.crmesb_prod));
    sheet.addCell(label);	
    
	label = new Label(++col,row, String.valueOf(rca.crmesb_product_backlog));
    sheet.addCell(label);
}

private void writeOtherThirdPartyIssue(int col, int row,WritableSheet sheet,RCA rca) throws RowsExceededException, WriteException {
	Label label;
    
    label = new Label(++col,row, String.valueOf(rca.otp_qa));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.otp_uat));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.otp_prod));
    sheet.addCell(label);	
    
	label = new Label(++col,row, String.valueOf(rca.otp_product_backlog));
    sheet.addCell(label);
}

private void writeProductMergeUpdateUpgradeIssue(int col, int row,WritableSheet sheet,RCA rca) throws RowsExceededException, WriteException {
	Label label;
    
    label = new Label(++col,row, String.valueOf(rca.pmuu_qa));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.pmuu_uat));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.pmuu_prod));
    sheet.addCell(label);	
    
	label = new Label(++col,row, String.valueOf(rca.pmuu_product_backlog));
    sheet.addCell(label);
}

private void writeIntegrationOthers(int col, int row,WritableSheet sheet,RCA rca) throws RowsExceededException, WriteException {
	Label label;
    
    label = new Label(++col,row, String.valueOf(rca.io_qa));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.io_uat));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.io_prod));
    sheet.addCell(label);	
    
	label = new Label(++col,row, String.valueOf(rca.io_product_backlog));
    sheet.addCell(label);
}

private void writeProductDefect(int col, int row,WritableSheet sheet,RCA rca) throws RowsExceededException, WriteException {
	Label label;

    
    label = new Label(++col,row, String.valueOf(rca.pd_qa));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.pd_uat));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.pd_prod));
    sheet.addCell(label);	
    
	label = new Label(++col,row, String.valueOf(rca.pd_product_backlog));
    sheet.addCell(label);
}

private void writeUnableToRep(int col, int row,WritableSheet sheet,RCA rca) throws RowsExceededException, WriteException {
	Label label;
    
    label = new Label(++col,row, String.valueOf(rca.utr_qa));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.utr_uat));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.utr_prod));
    sheet.addCell(label);	
    
	label = new Label(++col,row, String.valueOf(rca.utr_product_backlog));
    sheet.addCell(label);
}

private void writeBrowserSpecific(int col, int row,WritableSheet sheet,RCA rca) throws RowsExceededException, WriteException {
	Label label;
	    
    label = new Label(++col,row, String.valueOf(rca.bsi_qa));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.bsi_uat));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.bsi_prod));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.bsi_product_backlog));
    sheet.addCell(label);
}

private void writeNotDefect(int col, int row,WritableSheet sheet,RCA rca) throws RowsExceededException, WriteException {
	Label label;
	    
    label = new Label(++col,row, String.valueOf(rca.nad_qa));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.nad_uat));
    sheet.addCell(label);
  
    label = new Label(++col,row, String.valueOf(rca.nad_prod));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.nad_product_backlog));
    sheet.addCell(label);
}

private void writeDuplicate(int col, int row,WritableSheet sheet,RCA rca) throws RowsExceededException, WriteException {
	Label label;
	    
    label = new Label(++col,row, String.valueOf(rca.dup_qa));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.dup_uat));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.dup_prod));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.dup_product_backlog));
    sheet.addCell(label);
}

private void writeAsDesigned(int col, int row,WritableSheet sheet,RCA rca) throws RowsExceededException, WriteException {
	Label label;
    
    label = new Label(++col,row, String.valueOf(rca.ad_qa));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.ad_uat));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.ad_prod));
    sheet.addCell(label);
    
	label = new Label(++col,row, String.valueOf(rca.ad_product_backlog));
    sheet.addCell(label);
	
}

private void writeClientCodeBug(int col, int row,WritableSheet sheet,RCA rca) throws RowsExceededException, WriteException {
	Label label;
	    
    label = new Label(++col,row, String.valueOf(rca.ccb_qa));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.ccb_uat));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.ccb_prod));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.ccb_product_backlog));
    sheet.addCell(label);
	
}

private void writeConfigrationSum(int col, int row,WritableSheet sheet,RCA rca) throws RowsExceededException, WriteException {
    Label label;
    
    label = new Label(++col,row, String.valueOf(rca.plan_qa+rca.rate_qa+rca.rpa_qa+rca.ac_qa+rca.ti_qa+rca.dp_qa+rca.env_qa+rca.co_qa));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.plan_uat+rca.rate_uat+rca.rpa_uat+rca.ac_uat+rca.ti_uat+rca.dp_uat+rca.env_uat+rca.co_uat));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.plan_prod+rca.rate_prod+rca.rpa_prod+rca.ac_prod+rca.ti_prod+rca.dp_prod+rca.env_prod+rca.co_prod));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.plan_product_backlog+rca.rate_product_backlog+rca.rpa_product_backlog+rca.ac_product_backlog+rca.ti_product_backlog+rca.dp_product_backlog+rca.env_product_backlog+rca.co_product_backlog));
    sheet.addCell(label);
    
}

private void writePlanPackage(int col, int row,WritableSheet sheet,RCA rca) throws RowsExceededException, WriteException {
	Label label;
	    
    label = new Label(++col,row, String.valueOf(rca.plan_qa));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.plan_uat));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.plan_prod));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.plan_product_backlog));
    sheet.addCell(label);
	
}

private void writeRatePackage(int col, int row,WritableSheet sheet,RCA rca) throws RowsExceededException, WriteException {
	Label label;
	    
    label = new Label(++col,row, String.valueOf(rca.rate_qa));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.rate_uat));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.rate_prod));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.rate_product_backlog));
    sheet.addCell(label);
	
}

private void writeRulesPlanAdvisor(int col, int row,WritableSheet sheet,RCA rca) throws RowsExceededException, WriteException {
	Label label;
	    
    label = new Label(++col,row, String.valueOf(rca.rpa_qa));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.rpa_uat));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.rpa_prod));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.rpa_product_backlog));
    sheet.addCell(label);
	
}

private void writeAppConfig(int col, int row,WritableSheet sheet,RCA rca) throws RowsExceededException, WriteException {
	Label label;
	    
    label = new Label(++col,row, String.valueOf(rca.ac_qa));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.ac_uat));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.ac_prod));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.ac_product_backlog));
    sheet.addCell(label);
	
}

private void writeTemplateIssue(int col, int row,WritableSheet sheet,RCA rca) throws RowsExceededException, WriteException {
	Label label;
	    
    label = new Label(++col,row, String.valueOf(rca.ti_qa));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.ti_uat));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.ti_prod));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.ti_product_backlog));
    sheet.addCell(label);
	
}

private void writeDeploymentProperties(int col, int row,WritableSheet sheet,RCA rca) throws RowsExceededException, WriteException {
	Label label;
	    
    label = new Label(++col,row, String.valueOf(rca.dp_qa));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.dp_uat));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.dp_prod));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.dp_product_backlog));
    sheet.addCell(label);
	
}

private void writeEnvironment(int col, int row,WritableSheet sheet,RCA rca) throws RowsExceededException, WriteException {
	Label label;
	    
    label = new Label(++col,row, String.valueOf(rca.env_qa));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.env_uat));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.env_prod));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.env_product_backlog));
    sheet.addCell(label);
	
}

private void writeConfigOthers(int col, int row,WritableSheet sheet,RCA rca) throws RowsExceededException, WriteException {
	Label label;
	    
    label = new Label(++col,row, String.valueOf(rca.co_qa));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.co_uat));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.co_prod));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.co_product_backlog));
    sheet.addCell(label);
	
}

private void writeChangeReq(int col, int row,WritableSheet sheet,RCA rca) throws RowsExceededException, WriteException {
	Label label;
	    
    label = new Label(++col,row, String.valueOf(rca.cr_qa));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.cr_uat));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.cr_prod));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.cr_product_backlog));
    sheet.addCell(label);
	
}


private void writeMissedReq(int col, int row,WritableSheet sheet,RCA rca) throws RowsExceededException, WriteException {
	Label label;
	
	    
    label = new Label(++col,row, String.valueOf(rca.mr_qa));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.mr_uat));
    sheet.addCell(label);

    label = new Label(++col,row, String.valueOf(rca.mr_prod));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.mr_product_backlog));
    sheet.addCell(label);
	
}


private void getRcaTypeLabelsForExport(WritableSheet sheet, WritableCellFormat cellFormat, int row,int col) throws RowsExceededException, WriteException {

	Label label;	
	label = new Label(col,++row, "Missed requirement",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "Change Requirement",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "Configuration",cellFormat);
    sheet.addCell(label);    
    label = new Label(col,++row, "Plan Package",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "Rate Package",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "Rules Plan And Advisor",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "App Config",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "Template Issues",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "Deployment Properties",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "Environment",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "Config Others",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "Client Code Bug",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "As designed",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "Duplicate",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "Not a defect",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "Browser specific issue",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "Unable to reproduce",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "Product Defect",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "Integration",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row,"FFM Issue",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row,"CRM/ESB Issue",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row,"Other Third Party Issue",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row,"Product Merge/ Upgrade/ Update Issue",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row,"Integration Other Issue",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row,"Data Issue",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row,"Re Open",cellFormat);
    sheet.addCell(label);
    /* Changes for Non RCA field addition */
    label = new Label(col,++row,"Non RCA Bug",cellFormat);
    sheet.addCell(label);
    /* Changes for Open Ticket field addition */
    label = new Label(col,++row,"Close Ticket",cellFormat);
    sheet.addCell(label);
    
	
}

private void getRcaTypeLabelsForTemplateDownload(WritableSheet sheet, WritableCellFormat cellFormat, int row,int col) throws RowsExceededException, WriteException {

    Label label;    
    label = new Label(col,++row, "Missed requirement",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "Change Requirement",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "Plan Package",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "Rate Package",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "Rules Plan And Advisor",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "App Config",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "Template Issues",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "Deployment Properties",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "Environment",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "Config Others",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "Client Code Bug",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "As designed",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "Duplicate",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "Not a defect",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "Browser specific issue",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "Unable to reproduce",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row, "Product Defect",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row,"FFM Issue",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row,"CRM/ESB Issue",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row,"Other Third Party Issue",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row,"Product Merge/ Upgrade/ Update Issue",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row,"Integration Other Issue",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row,"Data Issue",cellFormat);
    sheet.addCell(label);
    label = new Label(col,++row,"Re Open",cellFormat);
    sheet.addCell(label);
    /* Changes for Non RCA field addition */
    label = new Label(col,++row,"Non RCA Bug",cellFormat);
    sheet.addCell(label);
    /* Changes for Close Ticket field addition */
    label = new Label(col,++row,"Close Ticket",cellFormat);
    sheet.addCell(label);
    
    
}


private void getProjectLabels(WritableSheet sheet, WritableCellFormat cellFormat, int rowNumber, List<ProjectDetails> projectList, String week)
		throws WriteException, RowsExceededException {
	Label label;
	int i=rowNumber-1;
	int mergeColStart=1;
	int mergeColEnd=1;
	
	
	
	 WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10);	    
	 WritableCellFormat cellFormatDate = new WritableCellFormat(cellFont);
	 cellFormatDate.setWrap(true);	
	 cellFormatDate.setVerticalAlignment(VerticalAlignment.CENTRE);
	 cellFormatDate.setBorder(Border.ALL,BorderLineStyle.MEDIUM);
	 cellFormatDate.setAlignment(Alignment.CENTRE);
	 
	 Iterator<ProjectDetails> projectIterator=projectList.iterator();
	 
	 	sheet.mergeCells(0, rowNumber, 0, rowNumber+27);   // Changes for Non RCA Bug field addition
	 	sheet.mergeCells(0, rowNumber, 0, rowNumber+28);   // Changes for Close Ticket field addition
		label = new Label(0, rowNumber, week,cellFormatDate);
	    sheet.addCell(label);
		
	    mergeColStart=mergeColEnd+1;
	    mergeColEnd=mergeColStart+3;
		sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
		label = new Label(mergeColStart,0, "TOTAL",cellFormat);
	    sheet.addCell(label);

	    
	    label = new Label(++i,1, "QA");
	    sheet.addCell(label);
	    label = new Label(++i,1, "UAT");
	    sheet.addCell(label);
//	    label = new Label(++i,1, "STAGE");
//	    sheet.addCell(label);
	    label = new Label(++i,1, "PROD");
	    sheet.addCell(label);
	    label = new Label(++i,1, "OPEN");
	    sheet.addCell(label);
	 
	 while(projectIterator.hasNext()){
		 
			
		    mergeColStart=mergeColEnd+1;
		    mergeColEnd=mergeColStart+3;
			sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
			label = new Label(mergeColStart,0, projectIterator.next().getProjectName(),cellFormat);
		    sheet.addCell(label);

		    
		    label = new Label(++i,1, "QA");
		    sheet.addCell(label);
		    label = new Label(++i,1, "UAT");
		    sheet.addCell(label);
//		    label = new Label(++i,1, "STAGE");
//		    sheet.addCell(label);
		    label = new Label(++i,1, "PROD");
		    sheet.addCell(label);
		    label = new Label(++i,1, "OPEN");
		    sheet.addCell(label);
		 
	 }
	    
	

//    mergeColStart=mergeColEnd+1;
//    mergeColEnd=mergeColStart+4;
//    
//    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
//    label = new Label(mergeColStart,0, "Centene",cellFormat);
//    sheet.addCell(label);
//    
//    label = new Label(++i,1, "QA");
//    sheet.addCell(label);
//    label = new Label(++i,1, "UAT");
//    sheet.addCell(label);
//    label = new Label(++i,1, "STAGE");
//    sheet.addCell(label);
//    label = new Label(++i,1, "PROD");
//    sheet.addCell(label);
//    label = new Label(++i,1, "OPEN");
//    sheet.addCell(label);
//    
//    
//    mergeColStart=mergeColEnd+1;
//    mergeColEnd=mergeColStart+4;
//    
//    sheet.mergeCells(mergeColStart, 0,mergeColEnd, 0);
//    label = new Label(mergeColStart,0, "IBC",cellFormat);
//    sheet.addCell(label);
//    
//    label = new Label(++i,1, "QA");
//    sheet.addCell(label);
//    label = new Label(++i,1, "UAT");
//    sheet.addCell(label);
//    label = new Label(++i,1, "STAGE");
//    sheet.addCell(label);
//    label = new Label(++i,1, "PROD");
//    sheet.addCell(label);
//    label = new Label(++i,1, "OPEN");
//    sheet.addCell(label);
//    
//    mergeColStart=mergeColEnd+1;
//    mergeColEnd=mergeColStart+4;
//    
//    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
//    label = new Label(mergeColStart,0, "WPS",cellFormat);
//    sheet.addCell(label);
//    
//    label = new Label(++i,1, "QA");
//    sheet.addCell(label);
//    label = new Label(++i,1, "UAT");
//    sheet.addCell(label);
//    label = new Label(++i,1, "STAGE");
//    sheet.addCell(label);
//    label = new Label(++i,1, "PROD");
//    sheet.addCell(label);
//    label = new Label(++i,1, "OPEN");
//    sheet.addCell(label);
//    
//    
//    mergeColStart=mergeColEnd+1;
//    mergeColEnd=mergeColStart+4;
//    
//    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
//    label = new Label(mergeColStart,0, "HFHP",cellFormat);
//    sheet.addCell(label);
//    
//    label = new Label(++i,1, "QA");
//    sheet.addCell(label);
//    label = new Label(++i,1, "UAT");
//    sheet.addCell(label);
//    label = new Label(++i,1, "STAGE");
//    sheet.addCell(label);
//    label = new Label(++i,1, "PROD");
//    sheet.addCell(label);
//    label = new Label(++i,1, "OPEN");
//    sheet.addCell(label);
//    
//    mergeColStart=mergeColEnd+1;
//    mergeColEnd=mergeColStart+4;
//    
//    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
//    label = new Label(mergeColStart,0, "HP",cellFormat);
//    sheet.addCell(label);
//    
//    label = new Label(++i,1, "QA");
//    sheet.addCell(label);
//    label = new Label(++i,1, "UAT");
//    sheet.addCell(label);
//    label = new Label(++i,1, "STAGE");
//    sheet.addCell(label);
//    label = new Label(++i,1, "PROD");
//    sheet.addCell(label);
//    label = new Label(++i,1, "OPEN");
//    sheet.addCell(label);
//    
//    mergeColStart=mergeColEnd+1;
//    mergeColEnd=mergeColStart+4;
//    
//    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
//    label = new Label(mergeColStart,0, "Kaiser",cellFormat);
//    sheet.addCell(label);
//    
//    label = new Label(++i,1, "QA");
//    sheet.addCell(label);
//    label = new Label(++i,1, "UAT");
//    sheet.addCell(label);
//    label = new Label(++i,1, "STAGE");
//    sheet.addCell(label);
//    label = new Label(++i,1, "PROD");
//    sheet.addCell(label);
//    label = new Label(++i,1, "OPEN");
//    sheet.addCell(label);
//    
//    mergeColStart=mergeColEnd+1;
//    mergeColEnd=mergeColStart+4;
//    
//    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
//    label = new Label(mergeColStart,0, "Federated",cellFormat);
//    sheet.addCell(label);
//    
//    label = new Label(++i,1, "QA");
//    sheet.addCell(label);
//    label = new Label(++i,1, "UAT");
//    sheet.addCell(label);
//    label = new Label(++i,1, "STAGE");
//    sheet.addCell(label);
//    label = new Label(++i,1, "PROD");
//    sheet.addCell(label);
//    label = new Label(++i,1, "OPEN");
//    sheet.addCell(label);
//    
//    mergeColStart=mergeColEnd+1;
//    mergeColEnd=mergeColStart+4;
//    
//    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
//    label = new Label(mergeColStart,0, "MI-IFP",cellFormat);
//    sheet.addCell(label);
//    
//    label = new Label(++i,1, "QA");
//    sheet.addCell(label);
//    label = new Label(++i,1, "UAT");
//    sheet.addCell(label);
//    label = new Label(++i,1, "STAGE");
//    sheet.addCell(label);
//    label = new Label(++i,1, "PROD");
//    sheet.addCell(label);
//    label = new Label(++i,1, "OPEN");
//    sheet.addCell(label);
//    
//    mergeColStart=mergeColEnd+1;
//    mergeColEnd=mergeColStart+4;
//    
//    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
//    label = new Label(mergeColStart,0, "MI-SG",cellFormat);
//    sheet.addCell(label);
//    
//    label = new Label(++i,1, "QA");
//    sheet.addCell(label);
//    label = new Label(++i,1, "UAT");
//    sheet.addCell(label);
//    label = new Label(++i,1, "STAGE");
//    sheet.addCell(label);
//    label = new Label(++i,1, "PROD");
//    sheet.addCell(label);
//    label = new Label(++i,1, "OPEN");
//    sheet.addCell(label);
//    
//    
//    mergeColStart=mergeColEnd+1;
//    mergeColEnd=mergeColStart+4;
//    
//    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
//    label = new Label(mergeColStart,0, "BCBSAL",cellFormat);
//    sheet.addCell(label);
//    
//    label = new Label(++i,1, "QA");
//    sheet.addCell(label);
//    label = new Label(++i,1, "UAT");
//    sheet.addCell(label);
//    label = new Label(++i,1, "STAGE");
//    sheet.addCell(label);
//    label = new Label(++i,1, "PROD");
//    sheet.addCell(label);
//    label = new Label(++i,1, "OPEN");
//    sheet.addCell(label);
//    
//    mergeColStart=mergeColEnd+1;
//    mergeColEnd=mergeColStart+4;
//    
//    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
//    label = new Label(mergeColStart,0, "BCBSMN",cellFormat);
//    sheet.addCell(label);
//    
//    label = new Label(++i,1, "QA");
//    sheet.addCell(label);
//    label = new Label(++i,1, "UAT");
//    sheet.addCell(label);
//    label = new Label(++i,1, "STAGE");
//    sheet.addCell(label);
//    label = new Label(++i,1, "PROD");
//    sheet.addCell(label);
//    label = new Label(++i,1, "OPEN");
//    sheet.addCell(label);
//    
//    mergeColStart=mergeColEnd+1;
//    mergeColEnd=mergeColStart+4;
//    
//    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
//    label = new Label(mergeColStart,0, "BCBSMA",cellFormat);
//    sheet.addCell(label);
//    
//    label = new Label(++i,1, "QA");
//    sheet.addCell(label);
//    label = new Label(++i,1, "UAT");
//    sheet.addCell(label);
//    label = new Label(++i,1, "STAGE");
//    sheet.addCell(label);
//    label = new Label(++i,1, "PROD");
//    sheet.addCell(label);
//    label = new Label(++i,1, "OPEN");
//    sheet.addCell(label);
//    
//    mergeColStart=mergeColEnd+1;
//    mergeColEnd=mergeColStart+4;
//    
//    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
//    label = new Label(mergeColStart,0, "BCBSNE",cellFormat);
//    sheet.addCell(label);
//    
//    label = new Label(++i,1, "QA");
//    sheet.addCell(label);
//    label = new Label(++i,1, "UAT");
//    sheet.addCell(label);
//    label = new Label(++i,1, "STAGE");
//    sheet.addCell(label);
//    label = new Label(++i,1, "PROD");
//    sheet.addCell(label);
//    label = new Label(++i,1, "OPEN");
//    sheet.addCell(label);
//    
//    mergeColStart=mergeColEnd+1;
//    mergeColEnd=mergeColStart+4;
//    
//    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
//    label = new Label(mergeColStart,0, "BCSBTN",cellFormat);
//    sheet.addCell(label);
//    
//    label = new Label(++i,1, "QA");
//    sheet.addCell(label);
//    label = new Label(++i,1, "UAT");
//    sheet.addCell(label);
//    label = new Label(++i,1, "STAGE");
//    sheet.addCell(label);
//    label = new Label(++i,1, "PROD");
//    sheet.addCell(label);
//    label = new Label(++i,1, "OPEN");
//    sheet.addCell(label);
//    
//    mergeColStart=mergeColEnd+1;
//    mergeColEnd=mergeColStart+4;
//    
//    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
//    label = new Label(mergeColStart,0, "Topaz",cellFormat);
//    sheet.addCell(label);
//    
//    label = new Label(++i,1, "QA");
//    sheet.addCell(label);
//    label = new Label(++i,1, "UAT");
//    sheet.addCell(label);
//    label = new Label(++i,1, "STAGE");
//    sheet.addCell(label);
//    label = new Label(++i,1, "PROD");
//    sheet.addCell(label);
//    label = new Label(++i,1, "OPEN");
//    sheet.addCell(label);
//    
//    mergeColStart=mergeColEnd+1;
//    mergeColEnd=mergeColStart+4;
//    
//    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
//    label = new Label(mergeColStart,0, "WM",cellFormat);
//    sheet.addCell(label);
//    
//    label = new Label(++i,1, "QA");
//    sheet.addCell(label);
//    label = new Label(++i,1, "UAT");
//    sheet.addCell(label);
//    label = new Label(++i,1, "STAGE");
//    sheet.addCell(label);
//    label = new Label(++i,1, "PROD");
//    sheet.addCell(label);
//    label = new Label(++i,1, "OPEN");
//    sheet.addCell(label);
//    
//    mergeColStart=mergeColEnd+1;
//    mergeColEnd=mergeColStart+4;
//    
//    sheet.mergeCells(mergeColStart, 0,mergeColEnd, 0);
//    label = new Label(mergeColStart,0, "TN-PX",cellFormat);
//    sheet.addCell(label);
//    
//    label = new Label(++i,1, "QA");
//    sheet.addCell(label);
//    label = new Label(++i,1, "UAT");
//    sheet.addCell(label);
//    label = new Label(++i,1, "STAGE");
//    sheet.addCell(label);
//    label = new Label(++i,1, "PROD");
//    sheet.addCell(label);
//    label = new Label(++i,1, "OPEN");
//    sheet.addCell(label);
//    
//    mergeColStart=mergeColEnd+1;
//    mergeColEnd=mergeColStart+4;
//    
//    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
//    label = new Label(mergeColStart,0, "HP-SG",cellFormat);
//    sheet.addCell(label);
//    
//    label = new Label(++i,1, "QA");
//    sheet.addCell(label);
//    label = new Label(++i,1, "UAT");
//    sheet.addCell(label);
//    label = new Label(++i,1, "STAGE");
//    sheet.addCell(label);
//    label = new Label(++i,1, "PROD");
//    sheet.addCell(label);
//    label = new Label(++i,1, "OPEN");
//    sheet.addCell(label);
//    
//    mergeColStart=mergeColEnd+1;
//    mergeColEnd=mergeColStart+4;
//    
//    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
//    label = new Label(mergeColStart,0, "UCD",cellFormat);
//    sheet.addCell(label);
//    
//    label = new Label(++i,1, "QA");
//    sheet.addCell(label);
//    label = new Label(++i,1, "UAT");
//    sheet.addCell(label);
//    label = new Label(++i,1, "STAGE");
//    sheet.addCell(label);
//    label = new Label(++i,1, "PROD");
//    sheet.addCell(label);
//    label = new Label(++i,1, "OPEN");
//    sheet.addCell(label);
//        
//    mergeColStart=mergeColEnd+1;
//    mergeColEnd=mergeColStart+4;
//    
//    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
//    label = new Label(mergeColStart,0, "UHG",cellFormat);
//    sheet.addCell(label);
//    
//    label = new Label(++i,1, "QA");
//    sheet.addCell(label);
//    label = new Label(++i,1, "UAT");
//    sheet.addCell(label);
//    label = new Label(++i,1, "STAGE");
//    sheet.addCell(label);
//    label = new Label(++i,1, "PROD");
//    sheet.addCell(label);
//    label = new Label(++i,1, "OPEN");
//    sheet.addCell(label);
//    
//    mergeColStart=mergeColEnd+1;
//    mergeColEnd=mergeColStart+4;
//    
//    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
//    label = new Label(mergeColStart,0, "Shopping",cellFormat);
//    sheet.addCell(label);
//    
//    label = new Label(++i,1, "QA");
//    sheet.addCell(label);
//    label = new Label(++i,1, "UAT");
//    sheet.addCell(label);
//    label = new Label(++i,1, "STAGE");
//    sheet.addCell(label);
//    label = new Label(++i,1, "PROD");
//    sheet.addCell(label);
//    label = new Label(++i,1, "OPEN");
//    sheet.addCell(label);
//    
//    mergeColStart=mergeColEnd+1;
//    mergeColEnd=mergeColStart+4;
//    
//    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
//    label = new Label(mergeColStart,0, "IA-PX",cellFormat);
//    sheet.addCell(label);    
//    
//    label = new Label(++i,1, "QA");
//    sheet.addCell(label);
//    label = new Label(++i,1, "UAT");
//    sheet.addCell(label);
//    label = new Label(++i,1, "STAGE");
//    sheet.addCell(label);
//    label = new Label(++i,1, "PROD");
//    sheet.addCell(label);
//    label = new Label(++i,1, "OPEN");
//    sheet.addCell(label);
//    
//    mergeColStart=mergeColEnd+1;
//    mergeColEnd=mergeColStart+4;
//    
//    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
//    label = new Label(mergeColStart,0, "DHP",cellFormat);
//    sheet.addCell(label);    
//    
//    label = new Label(++i,1, "QA");
//    sheet.addCell(label);
//    label = new Label(++i,1, "UAT");
//    sheet.addCell(label);
//    label = new Label(++i,1, "STAGE");
//    sheet.addCell(label);
//    label = new Label(++i,1, "PROD");
//    sheet.addCell(label);
//    label = new Label(++i,1, "OPEN");
//    sheet.addCell(label);
//    
//    mergeColStart=mergeColEnd+1;
//    mergeColEnd=mergeColStart+4;
//    
//    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
//    label = new Label(mergeColStart,0, "CRM",cellFormat);
//    sheet.addCell(label);    
//    
//    label = new Label(++i,1, "QA");
//    sheet.addCell(label);
//    label = new Label(++i,1, "UAT");
//    sheet.addCell(label);
//    label = new Label(++i,1, "STAGE");
//    sheet.addCell(label);
//    label = new Label(++i,1, "PROD");
//    sheet.addCell(label);
//    label = new Label(++i,1, "OPEN");
//    sheet.addCell(label);
//    
//    mergeColStart=mergeColEnd+1;
//    mergeColEnd=mergeColStart+4;
//    
//    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
//    label = new Label(mergeColStart,0, "Rating",cellFormat);
//    sheet.addCell(label);    
//    
//    label = new Label(++i,1, "QA");
//    sheet.addCell(label);
//    label = new Label(++i,1, "UAT");
//    sheet.addCell(label);
//    label = new Label(++i,1, "STAGE");
//    sheet.addCell(label);
//    label = new Label(++i,1, "PROD");
//    sheet.addCell(label);
//    label = new Label(++i,1, "OPEN");
//    sheet.addCell(label);
//    
//    mergeColStart=mergeColEnd+1;
//    mergeColEnd=mergeColStart+4;
//    
//    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
//    label = new Label(mergeColStart,0, "AppConfig",cellFormat);
//    sheet.addCell(label);    
//    
//    label = new Label(++i,1, "QA");
//    sheet.addCell(label);
//    label = new Label(++i,1, "UAT");
//    sheet.addCell(label);
//    label = new Label(++i,1, "STAGE");
//    sheet.addCell(label);
//    label = new Label(++i,1, "PROD");
//    sheet.addCell(label);
//    label = new Label(++i,1, "OPEN");
//    sheet.addCell(label);

}


private static WritableCellFormat getCellFormatProject(Colour colour, Pattern pattern) throws WriteException {
    WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 12);
    cellFont.setBoldStyle(cellFont.BOLD);
    WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
    cellFormat.setWrap(true);    
    cellFormat.setBackground(colour);
    cellFormat.setBorder(Border.ALL,BorderLineStyle.MEDIUM);
    cellFormat.setAlignment(Alignment.CENTRE);
    return cellFormat;
  }

private static WritableCellFormat getCellFormatRcaType(Colour colour, Pattern pattern) throws WriteException {
    WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 12);
  // cellFont.setBoldStyle(cellFont.BOLD);
    WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
    cellFormat.setWrap(true);    
    cellFormat.setBackground(colour);
    cellFormat.setBorder(Border.ALL,BorderLineStyle.THIN);
    cellFormat.setAlignment(Alignment.CENTRE);
    return cellFormat;
  }

	@Override
	public RCA getModel() {
		// TODO Auto-generated method stub
		return rca;
	}


	public RCA getRca() {
		return rca;
	}


	public void setRca(RCA rca) {
		this.rca = rca;
	}


	public boolean isIsdisabled() {
		return isdisabled;
	}


	public void setIsdisabled(boolean isdisabled) {
		this.isdisabled = isdisabled;
	}


	public String getWeekStr() {
		return weekStr;
	}
	public void setWeekStr(String weekStr) {
		this.weekStr = weekStr;
	}
	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public File getRcaFile() {
		return rcaFile;
	}

	public void setRcaFile(File rcaFile) {
		this.rcaFile = rcaFile;
	}

	public Map getProjectNameWithId() {
		
		projectNameWithId=(Map) session.get("projectNameWithId");
		return projectNameWithId;
	}

	public void setProjectNameWithId(Map projectNameWithId) {
		this.projectNameWithId = projectNameWithId;
	}

	public static Map getSession() {
		return session;
	}

	public void setSession(Map session) {
		RcaUtility.session = session;
	}

	public String getWeek2() {
		return week2;
	}

	public void setWeek2(String week2) {
		this.week2 =  week2;
	}


	

}
