package com.rca.controller;


import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

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
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.rca.dao.RcaUtilityDao;
import com.rca.entity.RCA;



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
	
		
	public String execute()
	{		
		RcaUtilityDao.getRcaDetail(rca);
		getWeekDates(rca.week);
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
		}
		else{
			addActionMessage("Project name already exist");
		}
		return SUCCESS;		
	}
	
	public String viewAddProject(){
		rca.setProjectStatus("Active");
		return SUCCESS;
	}

	
	/*public String reportRcaView() throws SQLException {
		rca.setWeekType("Weekly");
		List<String> weeks = findWeeks();
		rca.setWeeks(weeks);
		ArrayList<String> projectList = RcaUtilityDao.getProjectDetails(); 
		rca.setProjectList(projectList);
		return SUCCESS;
	}*/
	
	public String showProjectDetails() throws SQLException {
		   projectList = RcaUtilityDao.getProjectDetails();
		   rca.setProjectDetailList(projectList);
		   return SUCCESS;
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
			addActionMessage("Rca data submitted successfully");
		else if(result.equals("updated"))
			addActionMessage("Rca data udpated successfully");
		else if(result.equals("udpateFailure"))
			addActionMessage("Problem in udpating Rca data");
		else
			addActionMessage("Problem in submitting Rca data");
		
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

public String exportData() throws IOException, RowsExceededException, WriteException, BiffException 
{
	 List<RCA> rcaList=null;;
	if(rca.week==null)
	{
		addActionMessage("Week is Mandatory");
	}
	else
	{
		 rcaList=RcaUtilityDao.getRcaDetailList(rca);	
		 
	}	
	
	Label label=null;
	File file=new File("RCA.xlsx");
	WorkbookSettings wbSettings = new WorkbookSettings();

    wbSettings.setLocale(new Locale("en", "EN"));

    WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
    WritableSheet sheet= workbook.createSheet("RCA", 0);
    WritableCellFormat cellFormatProject=getCellFormatRcaType(Colour.GRAY_25, Pattern.GRAY_75); 
    WritableCellFormat cellFormatRcaType=getCellFormatRcaType(Colour.GRAY_25, Pattern.GRAY_75); 
    
    getProjectLabels(sheet, cellFormatProject);
    
    getRcaTypeLabelsForExport(sheet, cellFormatRcaType,1,1);
    
    if(rcaList !=null && rcaList.size()>0)
    {
     writeRcaData(sheet,rcaList);
    }
  
    
    
    workbook.write();
    fileInputStream = new FileInputStream(new File("RCA.xlsx")); 
    
    workbook.close();
	return "success";
	
}


private void writeRcaData(WritableSheet sheet, List<RCA> rcaList) throws RowsExceededException, WriteException {
	
	
	for (RCA rca :rcaList)
	{
		int row=2;
		int col=0;
		
				
		//SHP
		if(rca.project_id==16)
		{
			col=1;
			writeExceldata(col,row,sheet,rca);
		}
		else if(rca.project_id==6) // CENTENE
		{
			col=6;
			writeExceldata(col,row,sheet,rca);
		}
		else if(rca.project_id==11) // IBC	
		{
			col=11;
			writeExceldata(col,row,sheet,rca);
		}
		else if(rca.project_id==20) // WPS
		{
			col=16;
			writeExceldata(col,row,sheet,rca);
		}
		else if(rca.project_id==8) // HFHP
		{
			col=21;
			writeExceldata(col,row,sheet,rca);
		}
		else if(rca.project_id==9) // HP
		{
			col=26;
			writeExceldata(col,row,sheet,rca);
		}
		else if(rca.project_id==12) //Kaiser
		{
			col=31;
			writeExceldata(col,row,sheet,rca);
		}
		else if(rca.project_id==7) //Federated	
		{
			col=36;
			writeExceldata(col,row,sheet,rca);
		}
		else if(rca.project_id==13) //MI-IFP
		{
			col=41;
			writeExceldata(col,row,sheet,rca);
		}
		else if(rca.project_id==14) //MI-SG	
		{
			col=46;
			writeExceldata(col,row,sheet,rca);
		}
		else if(rca.project_id==1) //BCBSAL
		{
			col=51;
			writeExceldata(col,row,sheet,rca);
		}
		else if(rca.project_id==3) //BCBSMN
		{
			col=56;
			writeExceldata(col,row,sheet,rca);
		}
		else if(rca.project_id==2) //BCBSMA	
		{
			col=61;
			writeExceldata(col,row,sheet,rca);
		}
		else if(rca.project_id==4) //BCBSNE
		{
			col=66;
			writeExceldata(col,row,sheet,rca);
		}
		else if(rca.project_id==5) //BCSBTN	
		{
			col=71;
			writeExceldata(col,row,sheet,rca);
		}
		else if(rca.project_id==18)//Topaz
		{
			col=76;
			writeExceldata(col,row,sheet,rca);
		}
		else if(rca.project_id==19) //WM	
		{
			col=81;
			writeExceldata(col,row,sheet,rca);
		}
		else if(rca.project_id==17) //TN-PX		
		{
			col=86;
			writeExceldata(col,row,sheet,rca);
		}
		else if(rca.project_id==10) //HP-SG	
		{
			col=91;
			writeExceldata(col,row,sheet,rca);
		}
		else if(rca.project_id==21) //UCD	
		{
			col=96;
			writeExceldata(col,row,sheet,rca);
		}
		else if(rca.project_id==22)//UHG	
		{
			col=101;
			writeExceldata(col,row,sheet,rca);
		}
		else if(rca.project_id==15)//Shopping
		{
			col=106;
			writeExceldata(col,row,sheet,rca);
		}	
		else if(rca.project_id==23)//Shopping
		{
			col=111;
			writeExceldata(col,row,sheet,rca);
		}	
		else if(rca.project_id==24)//DHP
		{
			col=116;
			writeExceldata(col,row,sheet,rca);
		}
		else if(rca.project_id==25)//CRM
		{
			col=116;
			writeExceldata(col,row,sheet,rca);
		}
		else if(rca.project_id==26)//Rating
		{
			col=116;
			writeExceldata(col,row,sheet,rca);
		}
		else if(rca.project_id==27)//AppConfig
		{
			col=116;
			writeExceldata(col,row,sheet,rca);
		}

	}
		
}

private void writeExceldata(int col, int row,WritableSheet sheet,RCA rca) throws RowsExceededException, WriteException{
	
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
	
}
	
private void writeReOpen(int col, int row,WritableSheet sheet,RCA rca) throws RowsExceededException, WriteException {
	Label label;
	    
    label = new Label(++col,row, String.valueOf(rca.ro_qa));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.ro_uat));
    sheet.addCell(label);
    ++col;
    label = new Label(++col,row, String.valueOf(rca.ro_prod));
    sheet.addCell(label);    

}

private void writeDataIssue(int col, int row,WritableSheet sheet,RCA rca) throws RowsExceededException, WriteException {
	Label label;
	    
    label = new Label(++col,row, String.valueOf(rca.di_qa));
    sheet.addCell(label);
    
    label = new Label(++col,row, String.valueOf(rca.di_uat));
    sheet.addCell(label);
    ++col;
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
    ++col;
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
    ++col;
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
    ++col;
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
    ++col;
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
    ++col;
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
    ++col;
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
    ++col;
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
    ++col;
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
    ++col;
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
    ++col;
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
    ++col;
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
    ++col;
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
    ++col;
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
    ++col;
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
    ++col;
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
    ++col;
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
    ++col;
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
    ++col;
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
    ++col;
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
    ++col;
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
    ++col;
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
    ++col;
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
    ++col;
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
    ++col;
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
    
    
}


private void getProjectLabels(WritableSheet sheet, WritableCellFormat cellFormat)
		throws WriteException, RowsExceededException {
	Label label;
	int i=1;
	int mergeColStart=1;
	int mergeColEnd=1;
	
	 WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10);	    
	 WritableCellFormat cellFormatDate = new WritableCellFormat(cellFont);
	 cellFormatDate.setWrap(true);	
	 cellFormatDate.setVerticalAlignment(VerticalAlignment.CENTRE);
	 cellFormatDate.setBorder(Border.ALL,BorderLineStyle.MEDIUM);
	 cellFormatDate.setAlignment(Alignment.CENTRE);
	    
	sheet.mergeCells(0, 1, 0, 14);
	label = new Label(0,1, rca.week,cellFormatDate);
    sheet.addCell(label);
	
    mergeColStart=mergeColEnd+1;
    mergeColEnd=mergeColStart+4;
	sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
	label = new Label(mergeColStart,0, "SHP",cellFormat);
    sheet.addCell(label);

    
    label = new Label(++i,1, "QA");
    sheet.addCell(label);
    label = new Label(++i,1, "UAT");
    sheet.addCell(label);
    label = new Label(++i,1, "STAGE");
    sheet.addCell(label);
    label = new Label(++i,1, "PROD");
    sheet.addCell(label);
    label = new Label(++i,1, "OPEN");
    sheet.addCell(label);

    mergeColStart=mergeColEnd+1;
    mergeColEnd=mergeColStart+4;
    
    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
    label = new Label(mergeColStart,0, "Centene",cellFormat);
    sheet.addCell(label);
    
    label = new Label(++i,1, "QA");
    sheet.addCell(label);
    label = new Label(++i,1, "UAT");
    sheet.addCell(label);
    label = new Label(++i,1, "STAGE");
    sheet.addCell(label);
    label = new Label(++i,1, "PROD");
    sheet.addCell(label);
    label = new Label(++i,1, "OPEN");
    sheet.addCell(label);
    
    
    mergeColStart=mergeColEnd+1;
    mergeColEnd=mergeColStart+4;
    
    sheet.mergeCells(mergeColStart, 0,mergeColEnd, 0);
    label = new Label(mergeColStart,0, "IBC",cellFormat);
    sheet.addCell(label);
    
    label = new Label(++i,1, "QA");
    sheet.addCell(label);
    label = new Label(++i,1, "UAT");
    sheet.addCell(label);
    label = new Label(++i,1, "STAGE");
    sheet.addCell(label);
    label = new Label(++i,1, "PROD");
    sheet.addCell(label);
    label = new Label(++i,1, "OPEN");
    sheet.addCell(label);
    
    mergeColStart=mergeColEnd+1;
    mergeColEnd=mergeColStart+4;
    
    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
    label = new Label(mergeColStart,0, "WPS",cellFormat);
    sheet.addCell(label);
    
    label = new Label(++i,1, "QA");
    sheet.addCell(label);
    label = new Label(++i,1, "UAT");
    sheet.addCell(label);
    label = new Label(++i,1, "STAGE");
    sheet.addCell(label);
    label = new Label(++i,1, "PROD");
    sheet.addCell(label);
    label = new Label(++i,1, "OPEN");
    sheet.addCell(label);
    
    
    mergeColStart=mergeColEnd+1;
    mergeColEnd=mergeColStart+4;
    
    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
    label = new Label(mergeColStart,0, "HFHP",cellFormat);
    sheet.addCell(label);
    
    label = new Label(++i,1, "QA");
    sheet.addCell(label);
    label = new Label(++i,1, "UAT");
    sheet.addCell(label);
    label = new Label(++i,1, "STAGE");
    sheet.addCell(label);
    label = new Label(++i,1, "PROD");
    sheet.addCell(label);
    label = new Label(++i,1, "OPEN");
    sheet.addCell(label);
    
    mergeColStart=mergeColEnd+1;
    mergeColEnd=mergeColStart+4;
    
    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
    label = new Label(mergeColStart,0, "HP",cellFormat);
    sheet.addCell(label);
    
    label = new Label(++i,1, "QA");
    sheet.addCell(label);
    label = new Label(++i,1, "UAT");
    sheet.addCell(label);
    label = new Label(++i,1, "STAGE");
    sheet.addCell(label);
    label = new Label(++i,1, "PROD");
    sheet.addCell(label);
    label = new Label(++i,1, "OPEN");
    sheet.addCell(label);
    
    mergeColStart=mergeColEnd+1;
    mergeColEnd=mergeColStart+4;
    
    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
    label = new Label(mergeColStart,0, "Kaiser",cellFormat);
    sheet.addCell(label);
    
    label = new Label(++i,1, "QA");
    sheet.addCell(label);
    label = new Label(++i,1, "UAT");
    sheet.addCell(label);
    label = new Label(++i,1, "STAGE");
    sheet.addCell(label);
    label = new Label(++i,1, "PROD");
    sheet.addCell(label);
    label = new Label(++i,1, "OPEN");
    sheet.addCell(label);
    
    mergeColStart=mergeColEnd+1;
    mergeColEnd=mergeColStart+4;
    
    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
    label = new Label(mergeColStart,0, "Federated",cellFormat);
    sheet.addCell(label);
    
    label = new Label(++i,1, "QA");
    sheet.addCell(label);
    label = new Label(++i,1, "UAT");
    sheet.addCell(label);
    label = new Label(++i,1, "STAGE");
    sheet.addCell(label);
    label = new Label(++i,1, "PROD");
    sheet.addCell(label);
    label = new Label(++i,1, "OPEN");
    sheet.addCell(label);
    
    mergeColStart=mergeColEnd+1;
    mergeColEnd=mergeColStart+4;
    
    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
    label = new Label(mergeColStart,0, "MI-IFP",cellFormat);
    sheet.addCell(label);
    
    label = new Label(++i,1, "QA");
    sheet.addCell(label);
    label = new Label(++i,1, "UAT");
    sheet.addCell(label);
    label = new Label(++i,1, "STAGE");
    sheet.addCell(label);
    label = new Label(++i,1, "PROD");
    sheet.addCell(label);
    label = new Label(++i,1, "OPEN");
    sheet.addCell(label);
    
    mergeColStart=mergeColEnd+1;
    mergeColEnd=mergeColStart+4;
    
    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
    label = new Label(mergeColStart,0, "MI-SG",cellFormat);
    sheet.addCell(label);
    
    label = new Label(++i,1, "QA");
    sheet.addCell(label);
    label = new Label(++i,1, "UAT");
    sheet.addCell(label);
    label = new Label(++i,1, "STAGE");
    sheet.addCell(label);
    label = new Label(++i,1, "PROD");
    sheet.addCell(label);
    label = new Label(++i,1, "OPEN");
    sheet.addCell(label);
    
    
    mergeColStart=mergeColEnd+1;
    mergeColEnd=mergeColStart+4;
    
    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
    label = new Label(mergeColStart,0, "BCBSAL",cellFormat);
    sheet.addCell(label);
    
    label = new Label(++i,1, "QA");
    sheet.addCell(label);
    label = new Label(++i,1, "UAT");
    sheet.addCell(label);
    label = new Label(++i,1, "STAGE");
    sheet.addCell(label);
    label = new Label(++i,1, "PROD");
    sheet.addCell(label);
    label = new Label(++i,1, "OPEN");
    sheet.addCell(label);
    
    mergeColStart=mergeColEnd+1;
    mergeColEnd=mergeColStart+4;
    
    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
    label = new Label(mergeColStart,0, "BCBSMN",cellFormat);
    sheet.addCell(label);
    
    label = new Label(++i,1, "QA");
    sheet.addCell(label);
    label = new Label(++i,1, "UAT");
    sheet.addCell(label);
    label = new Label(++i,1, "STAGE");
    sheet.addCell(label);
    label = new Label(++i,1, "PROD");
    sheet.addCell(label);
    label = new Label(++i,1, "OPEN");
    sheet.addCell(label);
    
    mergeColStart=mergeColEnd+1;
    mergeColEnd=mergeColStart+4;
    
    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
    label = new Label(mergeColStart,0, "BCBSMA",cellFormat);
    sheet.addCell(label);
    
    label = new Label(++i,1, "QA");
    sheet.addCell(label);
    label = new Label(++i,1, "UAT");
    sheet.addCell(label);
    label = new Label(++i,1, "STAGE");
    sheet.addCell(label);
    label = new Label(++i,1, "PROD");
    sheet.addCell(label);
    label = new Label(++i,1, "OPEN");
    sheet.addCell(label);
    
    mergeColStart=mergeColEnd+1;
    mergeColEnd=mergeColStart+4;
    
    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
    label = new Label(mergeColStart,0, "BCBSNE",cellFormat);
    sheet.addCell(label);
    
    label = new Label(++i,1, "QA");
    sheet.addCell(label);
    label = new Label(++i,1, "UAT");
    sheet.addCell(label);
    label = new Label(++i,1, "STAGE");
    sheet.addCell(label);
    label = new Label(++i,1, "PROD");
    sheet.addCell(label);
    label = new Label(++i,1, "OPEN");
    sheet.addCell(label);
    
    mergeColStart=mergeColEnd+1;
    mergeColEnd=mergeColStart+4;
    
    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
    label = new Label(mergeColStart,0, "BCSBTN",cellFormat);
    sheet.addCell(label);
    
    label = new Label(++i,1, "QA");
    sheet.addCell(label);
    label = new Label(++i,1, "UAT");
    sheet.addCell(label);
    label = new Label(++i,1, "STAGE");
    sheet.addCell(label);
    label = new Label(++i,1, "PROD");
    sheet.addCell(label);
    label = new Label(++i,1, "OPEN");
    sheet.addCell(label);
    
    mergeColStart=mergeColEnd+1;
    mergeColEnd=mergeColStart+4;
    
    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
    label = new Label(mergeColStart,0, "Topaz",cellFormat);
    sheet.addCell(label);
    
    label = new Label(++i,1, "QA");
    sheet.addCell(label);
    label = new Label(++i,1, "UAT");
    sheet.addCell(label);
    label = new Label(++i,1, "STAGE");
    sheet.addCell(label);
    label = new Label(++i,1, "PROD");
    sheet.addCell(label);
    label = new Label(++i,1, "OPEN");
    sheet.addCell(label);
    
    mergeColStart=mergeColEnd+1;
    mergeColEnd=mergeColStart+4;
    
    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
    label = new Label(mergeColStart,0, "WM",cellFormat);
    sheet.addCell(label);
    
    label = new Label(++i,1, "QA");
    sheet.addCell(label);
    label = new Label(++i,1, "UAT");
    sheet.addCell(label);
    label = new Label(++i,1, "STAGE");
    sheet.addCell(label);
    label = new Label(++i,1, "PROD");
    sheet.addCell(label);
    label = new Label(++i,1, "OPEN");
    sheet.addCell(label);
    
    mergeColStart=mergeColEnd+1;
    mergeColEnd=mergeColStart+4;
    
    sheet.mergeCells(mergeColStart, 0,mergeColEnd, 0);
    label = new Label(mergeColStart,0, "TN-PX",cellFormat);
    sheet.addCell(label);
    
    label = new Label(++i,1, "QA");
    sheet.addCell(label);
    label = new Label(++i,1, "UAT");
    sheet.addCell(label);
    label = new Label(++i,1, "STAGE");
    sheet.addCell(label);
    label = new Label(++i,1, "PROD");
    sheet.addCell(label);
    label = new Label(++i,1, "OPEN");
    sheet.addCell(label);
    
    mergeColStart=mergeColEnd+1;
    mergeColEnd=mergeColStart+4;
    
    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
    label = new Label(mergeColStart,0, "HP-SG",cellFormat);
    sheet.addCell(label);
    
    label = new Label(++i,1, "QA");
    sheet.addCell(label);
    label = new Label(++i,1, "UAT");
    sheet.addCell(label);
    label = new Label(++i,1, "STAGE");
    sheet.addCell(label);
    label = new Label(++i,1, "PROD");
    sheet.addCell(label);
    label = new Label(++i,1, "OPEN");
    sheet.addCell(label);
    
    mergeColStart=mergeColEnd+1;
    mergeColEnd=mergeColStart+4;
    
    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
    label = new Label(mergeColStart,0, "UCD",cellFormat);
    sheet.addCell(label);
    
    label = new Label(++i,1, "QA");
    sheet.addCell(label);
    label = new Label(++i,1, "UAT");
    sheet.addCell(label);
    label = new Label(++i,1, "STAGE");
    sheet.addCell(label);
    label = new Label(++i,1, "PROD");
    sheet.addCell(label);
    label = new Label(++i,1, "OPEN");
    sheet.addCell(label);
        
    mergeColStart=mergeColEnd+1;
    mergeColEnd=mergeColStart+4;
    
    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
    label = new Label(mergeColStart,0, "UHG",cellFormat);
    sheet.addCell(label);
    
    label = new Label(++i,1, "QA");
    sheet.addCell(label);
    label = new Label(++i,1, "UAT");
    sheet.addCell(label);
    label = new Label(++i,1, "STAGE");
    sheet.addCell(label);
    label = new Label(++i,1, "PROD");
    sheet.addCell(label);
    label = new Label(++i,1, "OPEN");
    sheet.addCell(label);
    
    mergeColStart=mergeColEnd+1;
    mergeColEnd=mergeColStart+4;
    
    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
    label = new Label(mergeColStart,0, "Shopping",cellFormat);
    sheet.addCell(label);
    
    label = new Label(++i,1, "QA");
    sheet.addCell(label);
    label = new Label(++i,1, "UAT");
    sheet.addCell(label);
    label = new Label(++i,1, "STAGE");
    sheet.addCell(label);
    label = new Label(++i,1, "PROD");
    sheet.addCell(label);
    label = new Label(++i,1, "OPEN");
    sheet.addCell(label);
    
    mergeColStart=mergeColEnd+1;
    mergeColEnd=mergeColStart+4;
    
    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
    label = new Label(mergeColStart,0, "IA-PX",cellFormat);
    sheet.addCell(label);    
    
    label = new Label(++i,1, "QA");
    sheet.addCell(label);
    label = new Label(++i,1, "UAT");
    sheet.addCell(label);
    label = new Label(++i,1, "STAGE");
    sheet.addCell(label);
    label = new Label(++i,1, "PROD");
    sheet.addCell(label);
    label = new Label(++i,1, "OPEN");
    sheet.addCell(label);
    
    mergeColStart=mergeColEnd+1;
    mergeColEnd=mergeColStart+4;
    
    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
    label = new Label(mergeColStart,0, "DHP",cellFormat);
    sheet.addCell(label);    
    
    label = new Label(++i,1, "QA");
    sheet.addCell(label);
    label = new Label(++i,1, "UAT");
    sheet.addCell(label);
    label = new Label(++i,1, "STAGE");
    sheet.addCell(label);
    label = new Label(++i,1, "PROD");
    sheet.addCell(label);
    label = new Label(++i,1, "OPEN");
    sheet.addCell(label);
    
    mergeColStart=mergeColEnd+1;
    mergeColEnd=mergeColStart+4;
    
    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
    label = new Label(mergeColStart,0, "CRM",cellFormat);
    sheet.addCell(label);    
    
    label = new Label(++i,1, "QA");
    sheet.addCell(label);
    label = new Label(++i,1, "UAT");
    sheet.addCell(label);
    label = new Label(++i,1, "STAGE");
    sheet.addCell(label);
    label = new Label(++i,1, "PROD");
    sheet.addCell(label);
    label = new Label(++i,1, "OPEN");
    sheet.addCell(label);
    
    mergeColStart=mergeColEnd+1;
    mergeColEnd=mergeColStart+4;
    
    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
    label = new Label(mergeColStart,0, "Rating",cellFormat);
    sheet.addCell(label);    
    
    label = new Label(++i,1, "QA");
    sheet.addCell(label);
    label = new Label(++i,1, "UAT");
    sheet.addCell(label);
    label = new Label(++i,1, "STAGE");
    sheet.addCell(label);
    label = new Label(++i,1, "PROD");
    sheet.addCell(label);
    label = new Label(++i,1, "OPEN");
    sheet.addCell(label);
    
    mergeColStart=mergeColEnd+1;
    mergeColEnd=mergeColStart+4;
    
    sheet.mergeCells(mergeColStart, 0, mergeColEnd, 0);
    label = new Label(mergeColStart,0, "AppConfig",cellFormat);
    sheet.addCell(label);    
    
    label = new Label(++i,1, "QA");
    sheet.addCell(label);
    label = new Label(++i,1, "UAT");
    sheet.addCell(label);
    label = new Label(++i,1, "STAGE");
    sheet.addCell(label);
    label = new Label(++i,1, "PROD");
    sheet.addCell(label);
    label = new Label(++i,1, "OPEN");
    sheet.addCell(label);

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


	

}
