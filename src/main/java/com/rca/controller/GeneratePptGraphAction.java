package com.rca.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.hslf.model.Picture;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextBox;
import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;
import org.jfree.chart.plot.PlotOrientation;

import com.opensymphony.xwork2.ActionSupport;
import com.rca.common.RCAConstants;
import com.rca.common.ReportUtility;
import com.rca.entity.RcaCount;
import com.rca.service.GenerateGraph;
import com.rca.service.RcaManager;

/**
 * Main action class to generate RCA graph charts and generating PPT Slides.
 * @author prashant.singh
 *
 */
public class GeneratePptGraphAction extends ActionSupport implements SessionAware{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1269358124476669565L;
	public SessionMap session;
	private InputStream fileInputStream;
	
	//Employee manager injected by spring context
	private RcaManager rcaManager;
	SlideShow ppt = null;
	GenerateGraph generateGraph = new GenerateGraph();
	ReportUtility rU = new ReportUtility();
	
	
	int idx = 0;
	int bWCx = 0;
	int totalWeeklyBugCount = 0;
	private static String QA = "QA";
	private static String PRODUCTION = "Prod";
	private static String UAT = "UAT";
	private static String CUMULATIVE_OPEN = "Cumulative Open";
	
	public static String MIX_CATEGORY = "mixCategory";
	public static String DATA_ISSUE = "dataIssue";
	public static String INTEGRATION_ISSUE = "integrationIssue";
	public static String CONFIGURATION_ISSUE = "configurationIssue";
	public static String MISSED_CHANGE_REQUIREMENT = "MorCR";
	public static String CLIENT_CODE_BUG = "cCB";
	
	public String execute() throws Exception {
		
		List<RcaCount> rcaCounts = rcaManager.findRCAByWeekPeriod("2/23/2015-3/1/2015");
		//rcaManager.findRCAReportForMultipleWeek();
		createGraphPpt(rcaCounts);
	    fileInputStream = new FileInputStream(new File("D:\\project.ppt"));
	    return SUCCESS;
	}
	
	public String generateReport(){
		return SUCCESS;
	}
	
	public  void createGraphPpt(List<RcaCount> rcaCounts) throws IOException{

		ppt = new SlideShow();
		
		//Adding Reported Prod Slide
		addPptSlides(rcaCounts, PRODUCTION);
		
		//Adding Reported UAT Slide
		addPptSlides(rcaCounts, UAT);
		
		//Adding Reported QA Slide
		addPptSlides(rcaCounts, QA);
		
		//Adding Reported Cumulative Open Slide
		addPptSlides(rcaCounts, CUMULATIVE_OPEN);
		
		FileOutputStream out = new FileOutputStream(
				"D:\\project.ppt");
		ppt.write(out);
		out.close();
	}
	
	/**
	 * Generic Method to add slides in PPT as per requirements.
	 * @param rcaCounts
	 * @param slideType
	 * @throws IOException
	 */
	public void addPptSlides(List<RcaCount> rcaCounts, String slideType) throws IOException{
		
		Slide slide = ppt.createSlide();
		int pageWidth = ppt.getPageSize().width/2;
		int pageheight = ppt.getPageSize().height/2;
		// add a new picture to this slideshow and insert it in a  new slide
		List<RcaCount> allWeeksrcaCounts = rcaManager.findRCAReportForMultipleWeek();

		
		//Calling QA Slide Data method.
		if(QA.equals(slideType))
		{
			fillQADataInSlide(allWeeksrcaCounts, rcaCounts);
			TextBox txt1 = new TextBox();
			txt1.setText("Reported QA");
			txt1.setAnchor(new java.awt.Rectangle(0, 0, pageWidth/2, pageheight/10));
			slide.addShape(txt1);
			
			TextBox txt2 = new TextBox();
			txt2.setText("Total " + totalWeeklyBugCount + "\n" + "\n");
			
			TextRun tr = txt2.createTextRun();
			tr.appendText("Duplicate/ Not a Defect/ Unable to reproduce/ Browse/ As designed: " + calculateTotalBugTypeCountForQA(rcaCounts, MIX_CATEGORY) + "\n");
			tr.appendText("Data Issue: " + calculateTotalBugTypeCountForQA(rcaCounts, DATA_ISSUE) + "\n");
			tr.appendText("Integration Issue: " + calculateTotalBugTypeCountForQA(rcaCounts, INTEGRATION_ISSUE) + "\n");
			tr.appendText("Configuration Issue: " + calculateTotalBugTypeCountForQA(rcaCounts, CONFIGURATION_ISSUE) + "\n");
			tr.appendText("Missed/ Change Requirement: " + calculateTotalBugTypeCountForQA(rcaCounts, MISSED_CHANGE_REQUIREMENT) + "\n");
			tr.appendText("Client Code Bug: " + calculateTotalBugTypeCountForQA(rcaCounts, CLIENT_CODE_BUG));
			
			txt2.setAnchor(new java.awt.Rectangle(pageWidth+20, 20, pageWidth-50, pageheight-50));
			slide.addShape(txt2);

		}
		
		//Calling Prod Slide Data method.
		if(PRODUCTION.equals(slideType))
		{
			fillProdDataInSlide(allWeeksrcaCounts, rcaCounts);
			TextBox txt1 = new TextBox();
			txt1.setText("Reported Prod");
			txt1.setAnchor(new java.awt.Rectangle(0, 0, pageWidth/2, pageheight/10));
			slide.addShape(txt1);
			    
			TextBox txt2 = new TextBox();
			txt2.setText("Total " + totalWeeklyBugCount + "\n" + "\n");
			
			TextRun tr = txt2.createTextRun();
			tr.appendText("Duplicate/ Not a Defect/ Unable to reproduce/ Browse/ As designed: " + calculateTotalBugTypeCountForProd(rcaCounts, MIX_CATEGORY) + "\n");
			tr.appendText("Data Issue: " + calculateTotalBugTypeCountForProd(rcaCounts, DATA_ISSUE) + "\n");
			tr.appendText("Integration Issue: " + calculateTotalBugTypeCountForProd(rcaCounts, INTEGRATION_ISSUE) + "\n");
			tr.appendText("Configuration Issue: " + calculateTotalBugTypeCountForProd(rcaCounts, CONFIGURATION_ISSUE) + "\n");
			tr.appendText("Missed/ Change Requirement: " + calculateTotalBugTypeCountForProd(rcaCounts, MISSED_CHANGE_REQUIREMENT) + "\n");
			tr.appendText("Client Code Bug: " + calculateTotalBugTypeCountForProd(rcaCounts, CLIENT_CODE_BUG));

			txt2.setAnchor(new java.awt.Rectangle(pageWidth+20, 20, pageWidth-50, pageheight-50));
			slide.addShape(txt2);
		}
		
		//Calling UAT Slide Data method.
		if(UAT.equals(slideType))
		{
			fillUATDataInSlide(allWeeksrcaCounts, rcaCounts);
			TextBox txt1 = new TextBox();
			txt1.setText("Reported UAT");
			txt1.setAnchor(new java.awt.Rectangle(0, 0, pageWidth/2, pageheight/10));
			slide.addShape(txt1);
			
			TextBox txt2 = new TextBox();
			txt2.setText("Total " + totalWeeklyBugCount + "\n" + "\n");
			
			TextRun tr = txt2.createTextRun();
			tr.appendText("Duplicate/ Not a Defect/ Unable to reproduce/ Browse/ As designed: " + calculateTotalBugTypeCountForUAT(rcaCounts, MIX_CATEGORY) + "\n");
			tr.appendText("Data Issue: " + calculateTotalBugTypeCountForUAT(rcaCounts, DATA_ISSUE) + "\n");
			tr.appendText("Integration Issue: " + calculateTotalBugTypeCountForUAT(rcaCounts, INTEGRATION_ISSUE) + "\n");
			tr.appendText("Configuration Issue: " + calculateTotalBugTypeCountForUAT(rcaCounts, CONFIGURATION_ISSUE) + "\n");
			tr.appendText("Missed/ Change Requirement: " + calculateTotalBugTypeCountForUAT(rcaCounts, MISSED_CHANGE_REQUIREMENT) + "\n");
			tr.appendText("Client Code Bug: " + calculateTotalBugTypeCountForUAT(rcaCounts, CLIENT_CODE_BUG));
			
			txt2.setAnchor(new java.awt.Rectangle(pageWidth+20, 20, pageWidth-50, pageheight-50));
			slide.addShape(txt2);
		}
		
		//Calling Cumulative Open Slide Data method.
		if(CUMULATIVE_OPEN.equals(slideType))
		{
			fillCumulativeOpenDataInSlide(allWeeksrcaCounts, rcaCounts);
			TextBox txt1 = new TextBox();
			txt1.setText("Cumulative Open");
			txt1.setAnchor(new java.awt.Rectangle(0, 0, pageWidth/2, pageheight/10));
			slide.addShape(txt1);

			TextBox txt2 = new TextBox();
			txt2.setText("Backlog prioritized as per the business direction");

			txt2.setAnchor(new java.awt.Rectangle(pageWidth+20, 20, pageWidth-50, pageheight-50));
			slide.addShape(txt2);
		}
		
		Picture pict = new Picture(idx);
		//set image position in the slide
		pict.setAnchor(new java.awt.Rectangle(20, 30, pageWidth-50, pageheight-50));
		slide.addShape(pict);

		Picture pict1 = new Picture(bWCx);
		//set image position in the slide
		pict1.setAnchor(new java.awt.Rectangle(20, pageheight+30, pageWidth-50, pageheight-50));
		slide.addShape(pict1);
	}
	
	/**
	 * Method to fill relevant data for QA slide in PPT.
	 * @param allWeeksrcaCounts
	 * @param rcaCounts
	 * @throws IOException
	 */
	public void fillQADataInSlide(List<RcaCount> allWeeksrcaCounts, List<RcaCount> rcaCounts) throws IOException{
		
		List<String> allWeeks = rU.findWeeks();
		
		RcaCount rcaCount = null;
		
		totalWeeklyBugCount = 0;
		
		Iterator<RcaCount> it = rcaCounts.iterator();
		while(it.hasNext()){
			
			rcaCount = (RcaCount) it.next();
			totalWeeklyBugCount = totalWeeklyBugCount + rU.weeklyBugCountForAllProjectsInQA(rcaCount);

		}
		
		idx = ppt.addPicture(generateGraph.createGraph( rU.reportedQARCAForAllProjects(rcaCounts), "", "", "", 
				PlotOrientation.VERTICAL, false, 450, 450,RCAConstants.BAR) , XSLFPictureData.PICTURE_TYPE_PNG);
		
		//int lCx = ppt.addPicture(createLineChart() , XSLFPictureData.PICTURE_TYPE_PNG);
		
		bWCx = ppt.addPicture(generateGraph.createWeeklyGraph(rU.reportedQAAllWeeksGraphForAllProject(allWeeksrcaCounts, allWeeks), "Weekly Trend", "", "", 
				PlotOrientation.VERTICAL, true, 650, 450,RCAConstants.BAR) , XSLFPictureData.PICTURE_TYPE_PNG);
	}
	
	/**
	 * Method to fill relevant data for Production slide in PPT.
	 * @param allWeeksrcaCounts
	 * @param rcaCounts
	 * @throws IOException
	 */
	public void fillProdDataInSlide(List<RcaCount> allWeeksrcaCounts, List<RcaCount> rcaCounts) throws IOException{
		
		ReportUtility rU = new ReportUtility();
		List<String> allWeeks = rU.findWeeks();
				
		RcaCount rcaCount = null;
		
		totalWeeklyBugCount = 0;
		
		Iterator<RcaCount> it = rcaCounts.iterator();
		while(it.hasNext()){
			
			rcaCount = (RcaCount) it.next();
			totalWeeklyBugCount = totalWeeklyBugCount + rU.weeklyBugCountForAllProjectsInProduction(rcaCount);

		}
				
		idx = ppt.addPicture(generateGraph.createGraph( rU.reportedProdRCAForAllProjects(rcaCounts), "", "", "", 
				PlotOrientation.VERTICAL, false, 450, 450,RCAConstants.BAR) , XSLFPictureData.PICTURE_TYPE_PNG);
		
		//int lCx = ppt.addPicture(createLineChart() , XSLFPictureData.PICTURE_TYPE_PNG);
		
		bWCx = ppt.addPicture(generateGraph.createWeeklyGraph(rU.reportedProdAllWeeksGraphForAllProject(allWeeksrcaCounts, allWeeks), "Weekly Trend", "", "", 
				PlotOrientation.VERTICAL, true, 650, 450,RCAConstants.BAR) , XSLFPictureData.PICTURE_TYPE_PNG);
	}
	
	/**
	 * Method to fill relevant data for UAT slide in PPT.
	 * @param allWeeksrcaCounts
	 * @param rcaCounts
	 * @throws IOException
	 */
	public void fillUATDataInSlide(List<RcaCount> allWeeksrcaCounts, List<RcaCount> rcaCounts) throws IOException{
		
		ReportUtility rU = new ReportUtility();
		List<String> allWeeks = rU.findWeeks();
		
		RcaCount rcaCount = null;
		
		totalWeeklyBugCount = 0;
		
		Iterator<RcaCount> it = rcaCounts.iterator();
		while(it.hasNext()){
			
			rcaCount = (RcaCount) it.next();
			totalWeeklyBugCount = totalWeeklyBugCount + rU.weeklyBugCountForAllProjectsInUAT(rcaCount);

		}
		
		idx = ppt.addPicture(generateGraph.createGraph( rU.reportedUATRCAForAllProjects(rcaCounts), "", "", "", 
				PlotOrientation.VERTICAL, false, 450, 450,RCAConstants.BAR) , XSLFPictureData.PICTURE_TYPE_PNG);
		
		//int lCx = ppt.addPicture(createLineChart() , XSLFPictureData.PICTURE_TYPE_PNG);
		
		bWCx = ppt.addPicture(generateGraph.createWeeklyGraph(rU.reportedUATAllWeeksGraphForAllProject(allWeeksrcaCounts, allWeeks), "Weekly Trend", "", "", 
				PlotOrientation.VERTICAL, true, 650, 450,RCAConstants.BAR) , XSLFPictureData.PICTURE_TYPE_PNG);
	}
	
	/**
	 * Method to fill relevant data for Cumulative Open slide in PPT.
	 * @param allWeeksrcaCounts
	 * @param rcaCounts
	 * @throws IOException
	 */
	public void fillCumulativeOpenDataInSlide(List<RcaCount> allWeeksrcaCounts, List<RcaCount> rcaCounts) throws IOException{
		
		ReportUtility rU = new ReportUtility();
		List<String> allWeeks = rU.findWeeks();
				
		idx = ppt.addPicture(generateGraph.createGraph( rU.rcaCountForLastWeekForAllProjects(allWeeksrcaCounts), "", "", "", 
				PlotOrientation.VERTICAL, true, 950, 550,RCAConstants.BAR) , XSLFPictureData.PICTURE_TYPE_PNG);
		
		//int lCx = ppt.addPicture(createLineChart() , XSLFPictureData.PICTURE_TYPE_PNG);
		
		bWCx = ppt.addPicture(generateGraph.createWeeklyGraph(rU.reportedCumulativeOpenAllWeeksGraphForAllProject(allWeeksrcaCounts, allWeeks), "Weekly Trend", "", "", 
				PlotOrientation.VERTICAL, true, 650, 450,RCAConstants.BAR) , XSLFPictureData.PICTURE_TYPE_PNG);
	}
	
	/**
	 * Calculate the total bug count in Production environment as per bug type.
	 * @param rcaCounts
	 * @param bugType
	 * @return
	 */
	private String calculateTotalBugTypeCountForProd(List<RcaCount> rcaCounts, String bugType)
	{
		int totalBugTypeCount = 0;
		
		RcaCount rcaCount = null;
		
		Iterator<RcaCount> it = rcaCounts.iterator();
		while(it.hasNext()){
			
			rcaCount = (RcaCount) it.next();
			if(bugType.equals(MIX_CATEGORY))
				totalBugTypeCount = totalBugTypeCount + rU.mixCategoryWeeklyCountForAllProjectsInProd(rcaCount);
			else if(bugType.equals(DATA_ISSUE))
				totalBugTypeCount = totalBugTypeCount + rU.weeklyDataIssueForAllIssuesInProd(rcaCount);
			else if(bugType.equals(INTEGRATION_ISSUE))
				totalBugTypeCount = totalBugTypeCount + rU.weeklyIntegrationIssueForAllIssuesInProd(rcaCount);
			else if(bugType.equals(CONFIGURATION_ISSUE))
				totalBugTypeCount = totalBugTypeCount + rU.weeklyConfigurationIssueForAllIssuesInProd(rcaCount);
			else if(bugType.equals(MISSED_CHANGE_REQUIREMENT))
				totalBugTypeCount = totalBugTypeCount + rU.weeklyMissedAndCRCountForAllIssuesInProd(rcaCount);
			else if(bugType.equals(CLIENT_CODE_BUG))
				totalBugTypeCount = totalBugTypeCount + rU.weeklyClientCodeBugForAllIssuesInProd(rcaCount);

		}
		
		return Integer.toString(totalBugTypeCount);
	}
	
	/**
	 * Calculate the total bug count in QA environment as per bug type.
	 * @param rcaCounts
	 * @param bugType
	 * @return
	 */
	private String calculateTotalBugTypeCountForQA(List<RcaCount> rcaCounts, String bugType)
	{
		int totalBugTypeCount = 0;
		
		RcaCount rcaCount = null;
		
		Iterator<RcaCount> it = rcaCounts.iterator();
		while(it.hasNext()){
			
			rcaCount = (RcaCount) it.next();
			if(bugType.equals(MIX_CATEGORY))
				totalBugTypeCount = totalBugTypeCount + rU.mixCategoryWeeklyCountForAllProjectsInQA(rcaCount);
			else if(bugType.equals(DATA_ISSUE))
				totalBugTypeCount = totalBugTypeCount + rU.weeklyDataIssueForAllIssuesInQA(rcaCount);
			else if(bugType.equals(INTEGRATION_ISSUE))
				totalBugTypeCount = totalBugTypeCount + rU.weeklyIntegrationIssueForAllIssuesInQA(rcaCount);
			else if(bugType.equals(CONFIGURATION_ISSUE))
				totalBugTypeCount = totalBugTypeCount + rU.weeklyConfigurationIssueForAllIssuesInQA(rcaCount);
			else if(bugType.equals(MISSED_CHANGE_REQUIREMENT))
				totalBugTypeCount = totalBugTypeCount + rU.weeklyMissedAndCRCountForAllIssuesInQA(rcaCount);
			else if(bugType.equals(CLIENT_CODE_BUG))
				totalBugTypeCount = totalBugTypeCount + rU.weeklyClientCodeBugForAllIssuesInQA(rcaCount);

		}
		
		return Integer.toString(totalBugTypeCount);
	}
	
	/**
	 * Calculate the total bug count in UAT environment as per bug type.
	 * @param rcaCounts
	 * @param bugType
	 * @return
	 */
	private String calculateTotalBugTypeCountForUAT(List<RcaCount> rcaCounts, String bugType)
	{
		int totalBugTypeCount = 0;
		
		RcaCount rcaCount = null;
		
		Iterator<RcaCount> it = rcaCounts.iterator();
		while(it.hasNext()){
			
			rcaCount = (RcaCount) it.next();
			if(bugType.equals(MIX_CATEGORY))
				totalBugTypeCount = totalBugTypeCount + rU.mixCategoryWeeklyCountForAllProjectsInUAT(rcaCount);
			else if(bugType.equals(DATA_ISSUE))
				totalBugTypeCount = totalBugTypeCount + rU.weeklyDataIssueForAllIssuesInUAT(rcaCount);
			else if(bugType.equals(INTEGRATION_ISSUE))
				totalBugTypeCount = totalBugTypeCount + rU.weeklyIntegrationIssueForAllIssuesInUAT(rcaCount);
			else if(bugType.equals(CONFIGURATION_ISSUE))
				totalBugTypeCount = totalBugTypeCount + rU.weeklyConfigurationIssueForAllIssuesInUAT(rcaCount);
			else if(bugType.equals(MISSED_CHANGE_REQUIREMENT))
				totalBugTypeCount = totalBugTypeCount + rU.weeklyMissedAndCRCountForAllIssuesInUAT(rcaCount);
			else if(bugType.equals(CLIENT_CODE_BUG))
				totalBugTypeCount = totalBugTypeCount + rU.weeklyClientCodeBugForAllIssuesInUAT(rcaCount);

		}
		
		return Integer.toString(totalBugTypeCount);
	}
	
	
	public Map getSession() {
		return session;
	}

	public void setSession(Map session) {
		this.session = (SessionMap)session;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	public RcaManager getRcaManager() {
		return rcaManager;
	}

	public void setRcaManager(RcaManager rcaManager) {
		this.rcaManager = rcaManager;
	}
	
	
}
