package com.rca.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.rca.entity.ProjectDetails;
import com.rca.entity.RCA;
import com.rca.entity.RcaCount;
import com.rca.service.GenerateGraph;
import com.rca.service.ProjectDetailsManager;
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
	private RCA rca;
	
	//Employee manager injected by spring context
	private RcaManager rcaManager;
	private ProjectDetailsManager projectDetailsManager;
	SlideShow ppt = null;
	GenerateGraph generateGraph = new GenerateGraph();
	ReportUtility rU = new ReportUtility();
	
	
	int idx = 0;
	int bWCx = 0;
	int lCx = 0;
	int totalWeeklyBugCount = 0;
	private static String QA = "QA";
	private static String PRODUCTION = "PROD";
	private static String UAT = "UAT";
	private static String CUMULATIVE_OPEN = "Cumulative Open";
	
	public static String MIX_CATEGORY = "mixCategory";
	public static String DATA_ISSUE = "dataIssue";
	public static String INTEGRATION_ISSUE = "integrationIssue";
	public static String CONFIGURATION_ISSUE = "configurationIssue";
	public static String MISSED_CHANGE_REQUIREMENT = "MorCR";
	public static String CLIENT_CODE_BUG = "cCB";
	public static String PRODUCT_DEFECT = "productDefect";
	
	public String execute() throws Exception {
		
		List<RcaCount> rcaCounts = rcaManager.findRCAByWeekPeriod(rca.getWeek());
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
		
		// Adding Reopen Slide to PPT
		createReopenSlide(ppt, rcaCounts);
		
		
		//Adding Project 
		createProjectSpecificGraphs();
		
		FileOutputStream out = new FileOutputStream(
				"D:\\project.ppt");
		ppt.write(out);
		out.close();
	}
	
	private void createProjectSpecificGraphs() throws IOException{
		
		//List<ProjectDetails> activeProjectList = projectDetailsManager.getAllActiveProjects();
				
		List<String> allWeeks = rU.findWeeks(rca.getWeek());
		//for(int index =0; index < activeProjectList.size(); index++)
		{
		//	List<RcaCount> rcaCounts = rcaManager.findRCAReportForMultipleWeekForProject(allWeeks, activeProjectList.get(index).getProjectId() );
			//To do nteed to udpate
			createGraphIndividualPpt(rcaManager.findRCAReportForMultipleWeekForProject(22), ppt);
			createGraphIndividualPpt(rcaManager.findRCAReportForMultipleWeekForProject(18), ppt);
			createGraphIndividualPpt(rcaManager.findRCAReportForMultipleWeekForProject(1), ppt);
		}
		
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
		List<RcaCount> allWeeksrcaCounts = rcaManager.findRCAReportForMultipleWeek(rca.getWeek());

		
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
			if(calculateTotalBugTypeCountForQA(rcaCounts, CLIENT_CODE_BUG) != 0){
				tr.appendText(calculateTotalBugTypeCountForQA(rcaCounts, CLIENT_CODE_BUG) +" Client Code Defect" + "\n");
			}
			if(calculateTotalBugTypeCountForQA(rcaCounts, MISSED_CHANGE_REQUIREMENT) != 0){
				tr.appendText(calculateTotalBugTypeCountForQA(rcaCounts, MISSED_CHANGE_REQUIREMENT) + " Missed/ Change Requirement" +  "\n");
			}
			if(calculateTotalBugTypeCountForQA(rcaCounts, INTEGRATION_ISSUE) != 0){
				tr.appendText(calculateTotalBugTypeCountForQA(rcaCounts, INTEGRATION_ISSUE) + " Integration Issue" + "\n");
			}
			if(calculateTotalBugTypeCountForQA(rcaCounts, CONFIGURATION_ISSUE) != 0){
				tr.appendText(calculateTotalBugTypeCountForQA(rcaCounts, CONFIGURATION_ISSUE) + " Configuration Issue" + "\n");
			}
			if(calculateTotalBugTypeCountForQA(rcaCounts, DATA_ISSUE) != 0){
				tr.appendText(calculateTotalBugTypeCountForQA(rcaCounts, DATA_ISSUE) + " Data Issue" + "\n");
			}
			if(calculateTotalBugTypeCountForQA(rcaCounts, MIX_CATEGORY) != 0){
				tr.appendText(calculateTotalBugTypeCountForQA(rcaCounts, MIX_CATEGORY) + " Others " + "\n");
			}
			if(calculateTotalBugTypeCountForQA(rcaCounts, PRODUCT_DEFECT) != 0){
				tr.appendText(calculateTotalBugTypeCountForQA(rcaCounts, PRODUCT_DEFECT) + " Product Defect ");
			}
			
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
			if(calculateTotalBugTypeCountForProd(rcaCounts, CLIENT_CODE_BUG) != 0){
				tr.appendText(calculateTotalBugTypeCountForProd(rcaCounts, CLIENT_CODE_BUG) +" Client Code Defect" + "\n");
			}
			if(calculateTotalBugTypeCountForProd(rcaCounts, MISSED_CHANGE_REQUIREMENT) != 0){
				tr.appendText(calculateTotalBugTypeCountForProd(rcaCounts, MISSED_CHANGE_REQUIREMENT) + " Missed/ Change Requirement" +  "\n");
			}
			if(calculateTotalBugTypeCountForProd(rcaCounts, INTEGRATION_ISSUE) != 0){
				tr.appendText(calculateTotalBugTypeCountForProd(rcaCounts, INTEGRATION_ISSUE) + " Integration Issue" + "\n");
			}
			if(calculateTotalBugTypeCountForProd(rcaCounts, CONFIGURATION_ISSUE) != 0){
				tr.appendText(calculateTotalBugTypeCountForProd(rcaCounts, CONFIGURATION_ISSUE) + " Configuration Issue" + "\n");
			}
			if(calculateTotalBugTypeCountForProd(rcaCounts, DATA_ISSUE) != 0){
				tr.appendText(calculateTotalBugTypeCountForProd(rcaCounts, DATA_ISSUE) + " Data Issue" + "\n");
			}
			if(calculateTotalBugTypeCountForProd(rcaCounts, MIX_CATEGORY) != 0){
				tr.appendText(calculateTotalBugTypeCountForProd(rcaCounts, MIX_CATEGORY) + " Others " + "\n");
			}
			if(calculateTotalBugTypeCountForProd(rcaCounts, PRODUCT_DEFECT) != 0){
				tr.appendText(calculateTotalBugTypeCountForProd(rcaCounts, PRODUCT_DEFECT) + " Product Defect ");
			}

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
			if(calculateTotalBugTypeCountForUAT(rcaCounts, CLIENT_CODE_BUG) != 0){
				tr.appendText(calculateTotalBugTypeCountForUAT(rcaCounts, CLIENT_CODE_BUG) +" Client Code Defect" + "\n");
			}
			if(calculateTotalBugTypeCountForUAT(rcaCounts, MISSED_CHANGE_REQUIREMENT) != 0){
				tr.appendText(calculateTotalBugTypeCountForUAT(rcaCounts, MISSED_CHANGE_REQUIREMENT) + " Missed/ Change Requirement" +  "\n");
			}
			if(calculateTotalBugTypeCountForUAT(rcaCounts, INTEGRATION_ISSUE) != 0){
				tr.appendText(calculateTotalBugTypeCountForUAT(rcaCounts, INTEGRATION_ISSUE) + " Integration Issue" + "\n");
			}
			if(calculateTotalBugTypeCountForUAT(rcaCounts, CONFIGURATION_ISSUE) != 0){
				tr.appendText(calculateTotalBugTypeCountForUAT(rcaCounts, CONFIGURATION_ISSUE) + " Configuration Issue" + "\n");
			}
			if(calculateTotalBugTypeCountForUAT(rcaCounts, DATA_ISSUE) != 0){
				tr.appendText(calculateTotalBugTypeCountForUAT(rcaCounts, DATA_ISSUE) + " Data Issue" + "\n");
			}
			if(calculateTotalBugTypeCountForUAT(rcaCounts, MIX_CATEGORY) != 0){
				tr.appendText(calculateTotalBugTypeCountForUAT(rcaCounts, MIX_CATEGORY) + " Others " + "\n");
			}
			if(calculateTotalBugTypeCountForUAT(rcaCounts, PRODUCT_DEFECT) != 0){
				tr.appendText(calculateTotalBugTypeCountForUAT(rcaCounts, PRODUCT_DEFECT) + " Product Defect ");
			}
									
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

		Picture ccbLineGraph = new Picture(lCx);
		// set image position in the slide
		ccbLineGraph.setAnchor(new java.awt.Rectangle(pageWidth + 20,
				pageheight + 20, pageWidth - 50, pageheight - 50));
		slide.addShape(ccbLineGraph);
	}
	
	/**
	 * Method to fill relevant data for QA slide in PPT.
	 * @param allWeeksrcaCounts
	 * @param rcaCounts
	 * @throws IOException
	 */
	public void fillQADataInSlide(List<RcaCount> allWeeksrcaCounts, List<RcaCount> rcaCounts) throws IOException{
		
		List<String> allWeeks = rU.findWeeks(rca.getWeek());
		
		RcaCount rcaCount = null;
		
		totalWeeklyBugCount = 0;
		
		Iterator<RcaCount> it = rcaCounts.iterator();
		while(it.hasNext()){
			
			rcaCount = (RcaCount) it.next();
			totalWeeklyBugCount = totalWeeklyBugCount + rU.weeklyBugCountForAllProjectsInQA(rcaCount);

		}
		
		idx = ppt.addPicture(generateGraph.createGraph( rU.reportedQARCAForAllProjects(rcaCounts), "", "", "", 
				PlotOrientation.VERTICAL, false, 450, 450,RCAConstants.BAR) , XSLFPictureData.PICTURE_TYPE_PNG);
		
		lCx = ppt.addPicture(generateGraph.createLineGraph(rU.reportedAllWeeksCCBGraphForAllProject(allWeeksrcaCounts, allWeeks, QA), "Client Code Trend", "", "",
				PlotOrientation.VERTICAL, true, 650, 450,RCAConstants.LINE) , XSLFPictureData.PICTURE_TYPE_PNG);
		
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
		List<String> allWeeks = rU.findWeeks(rca.getWeek());
				
		RcaCount rcaCount = null;
		
		totalWeeklyBugCount = 0;
		
		Iterator<RcaCount> it = rcaCounts.iterator();
		while(it.hasNext()){
			
			rcaCount = (RcaCount) it.next();
			totalWeeklyBugCount = totalWeeklyBugCount + rU.weeklyBugCountForAllProjectsInProduction(rcaCount);

		}
				
		idx = ppt.addPicture(generateGraph.createGraph( rU.reportedProdRCAForAllProjects(rcaCounts), "", "", "", 
				PlotOrientation.VERTICAL, false, 450, 450,RCAConstants.BAR) , XSLFPictureData.PICTURE_TYPE_PNG);
		
		lCx = ppt.addPicture(generateGraph.createLineGraph(rU.reportedAllWeeksCCBGraphForAllProject(allWeeksrcaCounts, allWeeks, PRODUCTION), "Client Code Trend", "", "",
				PlotOrientation.VERTICAL, true, 650, 450,RCAConstants.LINE) , XSLFPictureData.PICTURE_TYPE_PNG);
		
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
		List<String> allWeeks = rU.findWeeks(rca.getWeek());
		
		RcaCount rcaCount = null;
		
		totalWeeklyBugCount = 0;
		
		Iterator<RcaCount> it = rcaCounts.iterator();
		while(it.hasNext()){
			
			rcaCount = (RcaCount) it.next();
			totalWeeklyBugCount = totalWeeklyBugCount + rU.weeklyBugCountForAllProjectsInUAT(rcaCount);

		}
		
		idx = ppt.addPicture(generateGraph.createGraph( rU.reportedUATRCAForAllProjects(rcaCounts), "", "", "", 
				PlotOrientation.VERTICAL, false, 450, 450,RCAConstants.BAR) , XSLFPictureData.PICTURE_TYPE_PNG);
		
		lCx = ppt.addPicture(generateGraph.createLineGraph(rU.reportedAllWeeksCCBGraphForAllProject(allWeeksrcaCounts, allWeeks, UAT), "Client Code Trend", "", "",
				PlotOrientation.VERTICAL, true, 650, 450,RCAConstants.LINE) , XSLFPictureData.PICTURE_TYPE_PNG);
		
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
		List<String> allWeeks = rU.findWeeks(rca.getWeek());
				
		idx = ppt.addPicture(generateGraph.createGraph( rU.rcaCountForLastWeekForAllProjects(allWeeksrcaCounts), "", "", "", 
				PlotOrientation.VERTICAL, true, 950, 550,RCAConstants.BAR) , XSLFPictureData.PICTURE_TYPE_PNG);
		
		lCx = ppt.addPicture(generateGraph.createLineGraph(rU.reportedAllWeeksCCBGraphForAllProject(allWeeksrcaCounts, allWeeks, CUMULATIVE_OPEN), "Client Code Trend", "", "",
				PlotOrientation.VERTICAL, true, 650, 450,RCAConstants.LINE) , XSLFPictureData.PICTURE_TYPE_PNG);
		
		bWCx = ppt.addPicture(generateGraph.createWeeklyGraph(rU.reportedCumulativeOpenAllWeeksGraphForAllProject(allWeeksrcaCounts, allWeeks), "Weekly Trend", "", "", 
				PlotOrientation.VERTICAL, true, 650, 450,RCAConstants.BAR) , XSLFPictureData.PICTURE_TYPE_PNG);
	}
	
	/**
	 * Calculate the total bug count in Production environment as per bug type.
	 * @param rcaCounts
	 * @param bugType
	 * @return
	 */
	private int calculateTotalBugTypeCountForProd(List<RcaCount> rcaCounts, String bugType)
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
			else if(bugType.equals(PRODUCT_DEFECT))
				totalBugTypeCount = totalBugTypeCount + rU.weeklyProductDefectForAllIssuesInProd(rcaCount);

		}
		
		return totalBugTypeCount;
	}
	
	/**
	 * Calculate the total bug count in QA environment as per bug type.
	 * @param rcaCounts
	 * @param bugType
	 * @return
	 */
	private int calculateTotalBugTypeCountForQA(List<RcaCount> rcaCounts, String bugType)
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
			else if(bugType.equals(PRODUCT_DEFECT))
				totalBugTypeCount = totalBugTypeCount + rU.weeklyProductDefectForAllIssuesInQA(rcaCount);

		}
		
		return totalBugTypeCount;
	}
	
	/**
	 * Calculate the total bug count in UAT environment as per bug type.
	 * @param rcaCounts
	 * @param bugType
	 * @return
	 */
	private int calculateTotalBugTypeCountForUAT(List<RcaCount> rcaCounts, String bugType)
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
			else if(bugType.equals(PRODUCT_DEFECT))
				totalBugTypeCount = totalBugTypeCount + rU.weeklyProductDefectForAllIssuesInUAT(rcaCount);

		}
		
		return totalBugTypeCount;
	}

	/**
	 * Generate Reopen Bug Count Slide
	 * 
	 * @param ppt
	 * @param rcaCounts
	 * @throws IOException
	 */
	public void createReopenSlide(SlideShow ppt, List<RcaCount> rcaCounts)
			throws IOException {
		ReportUtility rU = new ReportUtility();
		// Create Slide
		Slide reopenSlide = ppt.createSlide();
		// Setting Title
		int pageWidth = ppt.getPageSize().width / 2;
		int pageheight = ppt.getPageSize().height / 2;

		TextBox txt1 = new TextBox();
		txt1.setText("Reopen");
		txt1.setAnchor(new java.awt.Rectangle(0, 0, pageWidth / 2,
				pageheight / 10));
		reopenSlide.addShape(txt1);
		// Generating Graph and adding the graph in picture format to slide
		GenerateGraph generateGraph = new GenerateGraph();
		int idx = ppt.addPicture(generateGraph.createGraph(
				rU.reportedReopenRCAForAllProjects(rcaCounts), "Reopen Count",
				"Projects", "Bug Count", PlotOrientation.VERTICAL, false, 450,
				450, RCAConstants.BAR), XSLFPictureData.PICTURE_TYPE_PNG);
		Picture pict = new Picture(idx);
		// set image position in the slide
		int x = (pageWidth / 2), y = pageheight / 4, width = pageWidth - 50, height = pageheight - 50;
		pict.setAnchor(new java.awt.Rectangle(x, y, width, height));
		reopenSlide.addShape(pict);
		y = y + height + 20;
		x = 50;
		TextBox txt2 = new TextBox();
		TextRun tr = txt2.createTextRun();
		addDetailReopenCount(rcaCounts, tr, PRODUCTION);
		addDetailReopenCount(rcaCounts, tr, UAT);
		addDetailReopenCount(rcaCounts, tr, QA);

		txt2.setAnchor(new java.awt.Rectangle(x, y, pageWidth * 2 - 50,
				pageheight - 80));
		reopenSlide.addShape(txt2);
	}

	/**
	 * Add detail of reopen bugs in Reopen slide
	 * 
	 * @param rcaCounts
	 * @param tr
	 * @param enviType
	 */
	public void addDetailReopenCount(List<RcaCount> rcaCounts, TextRun tr,
			String enviType) {
		Map<String, Map<String, Integer>> count = rU
				.reportedReopenRCAForAllProjects(rcaCounts);
		Map<String, Integer> reopenCount = rU
				.reopenRCACountByEnvironment(rcaCounts);
		tr.appendText(enviType + " (" + reopenCount.get(enviType) + ")" + "\n");
		if (reopenCount.get(enviType) > 0) {
			for (Entry<String, Map<String, Integer>> temp : count.entrySet()) {
				if (temp.getValue().get(enviType) > 0)
					tr.appendText("\t" + temp.getKey() + " - "
							+ temp.getValue().get(enviType) + "\n");
			}
		}
	}

	@SuppressWarnings("unchecked")
	List<RcaCount> getAllWeekRCACountsListforIndividual(List<String> allWeeks){
		List<RcaCount> allWeeksrcaCountsforIndividual = new ArrayList<RcaCount>();
		for (int i = 0; i < allWeeks.size(); i++) {
			RcaCount rca = rcaManager.findWeeklyRCAReportByProjectId(allWeeks.get(i), 14);
			if(rca!= null){
				allWeeksrcaCountsforIndividual.add(rca);
			}
		}
		return allWeeksrcaCountsforIndividual;
	}
	public void createGraphIndividualPpt(List<RcaCount> rcaCount , SlideShow ppt) throws IOException{
		Slide slide = ppt.createSlide();
		int pageWidth = ppt.getPageSize().width/4;
		int pageheight = ppt.getPageSize().height/3;
		int totalPageHeight = ppt.getPageSize().height;
		GenerateGraph generateGraph = new GenerateGraph();
		// add a new picture to this slideshow and insert it in a new slide
		//List<RcaCount> allWeeksrcaCounts = rcaManager.findRCAReportForMultipleWeek();
		ReportUtility rU = new ReportUtility();
		List<String> allWeeks = rU.findWeeks(rca.getWeek());
		int idx1 = ppt.addPicture(generateGraph.createGraph( rU.reportedOpenllWeeksGraphForIndividualProject(rcaCount, allWeeks), "Cumulative Open", "", "",
				PlotOrientation.VERTICAL, true, 850, 550,RCAConstants.BAR, false) , XSLFPictureData.PICTURE_TYPE_PNG);
		int idx2 = ppt.addPicture(generateGraph.createGraph( rU.reportedProdllWeeksGraphForIndividualProject(rcaCount, allWeeks), "Weekly PROD", "", "",
				PlotOrientation.VERTICAL, true, 850, 550,RCAConstants.BAR, false) , XSLFPictureData.PICTURE_TYPE_PNG);
		int idx3 = ppt.addPicture(generateGraph.createGraph( rU.reportedUATAllWeeksGraphForIndividualProject(rcaCount, allWeeks), "Weekly UAT", "", "",
				PlotOrientation.VERTICAL, true, 850, 550,RCAConstants.BAR, false) , XSLFPictureData.PICTURE_TYPE_PNG);
		int idx4 = ppt.addPicture(generateGraph.createGraph( rU.reportedQAAllWeeksGraphForIndividualProject(rcaCount, allWeeks), "Weekly QA", "", "",
				PlotOrientation.VERTICAL, true, 850, 550,RCAConstants.BAR, false) , XSLFPictureData.PICTURE_TYPE_PNG);
		TextBox txt1 = new TextBox();
		txt1.setText(rcaCount.get(0).getProjectDetails().getProjectName() + " Dashboard");
		txt1.setAnchor(new java.awt.Rectangle(pageWidth+20, 20, pageWidth-10, pageheight-50));
		slide.addShape(txt1);
		Picture pict2 = new Picture(idx1);
		pict2.setAnchor(new java.awt.Rectangle(0, totalPageHeight-160, pageWidth-5, pageheight-50));
		slide.addShape(pict2);
		Picture pict3 = new Picture(idx2);
		pict3.setAnchor(new java.awt.Rectangle(180, totalPageHeight-160, pageWidth-5, pageheight-50));
		slide.addShape(pict3);
		Picture pict4 = new Picture(idx3);
		pict4.setAnchor(new java.awt.Rectangle(360, totalPageHeight-160, pageWidth-5, pageheight-50));
		slide.addShape(pict4);
		Picture pict5 = new Picture(idx4);
		pict5.setAnchor(new java.awt.Rectangle(540, totalPageHeight-160, pageWidth-5, pageheight-50));
		slide.addShape(pict5);

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

	public RCA getRca() {
		return rca;
	}

	public void setRca(RCA rca) {
		this.rca = rca;
	}
	
	
}
