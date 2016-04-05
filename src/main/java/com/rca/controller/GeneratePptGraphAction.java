package com.rca.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.hslf.model.Picture;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextBox;
import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.usermodel.RichTextRun;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.util.Log;

import com.opensymphony.xwork2.ActionSupport;
import com.rca.common.RCAConstants;
import com.rca.common.ReportUtility;
import com.rca.entity.ProjectDetails;
import com.rca.entity.RCA;
import com.rca.entity.RcaCount;
import com.rca.entity.SprintReport;
import com.rca.service.GenerateGraph;
import com.rca.service.ProjectDetailsManager;
import com.rca.service.RcaManager;
import com.rca.service.SprintReportManager;
import com.rca.workbookutility.LoggedDefectsVsOpen;

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
	private SprintReportManager sprintReportManager;
	SlideShow ppt = null;
	GenerateGraph generateGraph = new GenerateGraph();
	ReportUtility rU = new ReportUtility();
	Map<String, Integer> projectNameWithCcbCount;
	
	
	int idx = 0 , idxCloseVsOpen=0;
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
	public static String MISSED_REQUIREMENT = "MorR";
	public static String CHANGE_REQUIREMENT = "CR";
	public static String CLIENT_CODE_BUG = "cCB";
	public static String PRODUCT_DEFECT = "productDefect";
	 /* Changes for Non RCA field addition */
	public static String NON_RCA_BUG = "nrb";
	
	/* Changes for Non RCA field addition */
	public static String CLOSEICKETS = "close";
	
	private final Logger LOG = Logger.getLogger(GeneratePptGraphAction.class);
	
	public String execute() throws Exception {
		
		List<RcaCount> rcaCounts = rcaManager.findRCAByWeekPeriod(rca.getWeek());
		createGraphPpt(rcaCounts,rca.getWeek());
	    fileInputStream = new FileInputStream(new File("D:\\Weekly review.ppt"));
	    return SUCCESS;
	}
	
	public String generateReport(){
		return SUCCESS;
	}
	
	public  void createGraphPpt(List<RcaCount> rcaCounts, String week) throws IOException{

		ppt = new SlideShow();
		
		//Adding Reported Prod Slide
		addPptSlides(rcaCounts, PRODUCTION);
		
		//Adding Reported UAT Slide
		addPptSlides(rcaCounts, UAT);
		
		//Adding Reported QA Slide
		addPptSlides(rcaCounts, QA);
		
		//Adding Reported Cumulative Open Slide
		addPptSlides(rcaCounts, CUMULATIVE_OPEN);
		
		//Adding  Close Tickets
//		addPptSlides(rcaCounts, CLOSEICKETS);
		
		closeVsOpenDefectSlide(rcaCounts);
		
		// Adding Reopen Slide to PPT
		createReopenSlide(ppt, rcaCounts);
		
		
		//Adding Project 
		createProjectSpecificGraphs(week);//Note : Temporary comment
		
		//Adding Comments Slide 
		addPptSlidesComments(rcaCounts);
		
		//Adding Sprint Graph Slide
		//createProjectSpecificGraphs();
		
		FileOutputStream out = new FileOutputStream(
				"D:\\Weekly review.ppt");
		ppt.write(out);
		out.close();
	}
	
	
	
	
	/**
	 * Generic Method to add comments slide in PPT as per requirements.
	 * @param rcaCounts
	 * @throws IOException
	 */
	public void addPptSlidesComments(List<RcaCount> rcaCounts)
			throws IOException {

		Slide slide = ppt.createSlide();
		int pageWidth = ppt.getPageSize().width;
		int pageheight = ppt.getPageSize().height;
		// add a new picture to this slideshow and insert it in a new slide
		List<RcaCount> allWeeksrcaCounts = rcaManager
				.findRCAReportForMultipleWeek(rca.getWeek());

		TextBox txt2 = new TextBox();
		txt2.setText("UAT");

		TextRun tr = txt2.createTextRun();
		
		tr.appendText(addUatComments(rcaCounts));
		
		tr.appendText("\n\n");
		
		tr.appendText("PROD");
		
		tr.appendText(addProdComments(rcaCounts));
		
		tr.appendText("\n\n");
		
		tr.appendText("QA");
		
		tr.appendText(addQAComments(rcaCounts));
		
		tr.appendText("\n\n");
		
		tr.appendText("OPEN");
		
		tr.appendText("\n\n");
		
		tr.appendText(addOpenComments(rcaCounts));
		
		txt2.setAnchor(new java.awt.Rectangle(0, 0, pageWidth,
				pageheight));
		RichTextRun rt2Heading = tr.getRichTextRuns()[0];
		rt2Heading.setFontSize(12);
		rt2Heading.setFontName("Franklin Gothic Medium");
		for(int i=1; i<tr.getRichTextRuns().length;i++){
			RichTextRun rt2 = tr.getRichTextRuns()[i];
			if(rt2.getText().equalsIgnoreCase("PROD")||rt2.getText().equalsIgnoreCase("QA")||rt2.getText().equalsIgnoreCase("OPEN")){
				rt2.setFontSize(11);
				rt2.setFontName("Franklin Gothic Medium");
			}else{
				rt2.setFontSize(10);
				rt2.setFontName("Franklin Gothic Body");
				rt2.setAlignment(TextBox.AlignLeft);
			}
		}
		slide.addShape(txt2);

	}
	
public String addUatComments(List<RcaCount> rcaCounts){
		
		String uatText="";
		RcaCount rcaCount = null;
		int count=0;

		Iterator<RcaCount> it = rcaCounts.iterator();
		while (it.hasNext()) {
			count=0;
			rcaCount = (RcaCount) it.next();
			int total = calculateBugTypeCountForUATPerProject(rcaCount,
					CLIENT_CODE_BUG)
					+ calculateBugTypeCountForUATPerProject(rcaCount,
							MISSED_REQUIREMENT)
					+ calculateBugTypeCountForUATPerProject(rcaCount,
							CHANGE_REQUIREMENT)
					+ calculateBugTypeCountForUATPerProject(rcaCount,
							INTEGRATION_ISSUE)
					+ calculateBugTypeCountForUATPerProject(rcaCount,
							CONFIGURATION_ISSUE)
					+ calculateBugTypeCountForUATPerProject(rcaCount,
							DATA_ISSUE)
					+ calculateBugTypeCountForUATPerProject(rcaCount,
							MIX_CATEGORY)
					+ calculateBugTypeCountForUATPerProject(rcaCount,
							PRODUCT_DEFECT)
					+ calculateBugTypeCountForUATPerProject(rcaCount,   /* Changes for Non RCA field addition */
							NON_RCA_BUG);
			if (total > 0) {
				uatText+="\n"
						+ rcaCount.getProjectDetails().getProjectName() + "("+total+") : ";
				if (calculateBugTypeCountForUATPerProject(rcaCount,
						CLIENT_CODE_BUG) != 0) {
					uatText+=" "+calculateBugTypeCountForUATPerProject(
							rcaCount, CLIENT_CODE_BUG) + " Client Code Defect#";
					count++;
				}
				if (calculateBugTypeCountForUATPerProject(rcaCount,
						MISSED_REQUIREMENT) != 0) {
					uatText+=" "+calculateBugTypeCountForUATPerProject(
							rcaCount, MISSED_REQUIREMENT)
							+ " Missed Requirement#";
					count++;
				}
				if (calculateBugTypeCountForUATPerProject(rcaCount,
						CHANGE_REQUIREMENT) != 0) {
					uatText+=" "+calculateBugTypeCountForUATPerProject(
							rcaCount, CHANGE_REQUIREMENT)
							+ " Change Requirement#";
					count++;
				}
				if (calculateBugTypeCountForUATPerProject(rcaCount,
						INTEGRATION_ISSUE) != 0) {
					uatText+=" "+calculateBugTypeCountForUATPerProject(
							rcaCount, INTEGRATION_ISSUE) + " Integration Issue#";
					count++;
				}
				if (calculateBugTypeCountForUATPerProject(rcaCount,
						CONFIGURATION_ISSUE) != 0) {
					uatText+=" "+calculateBugTypeCountForUATPerProject(
							rcaCount, CONFIGURATION_ISSUE)
							+ " Configuration Issue#";
					count++;
				}
				if (calculateBugTypeCountForUATPerProject(rcaCount, DATA_ISSUE) != 0) {
					uatText+=" "+calculateBugTypeCountForUATPerProject(
							rcaCount, DATA_ISSUE) + " Data Issue#";
					count++;
				}
				if (calculateBugTypeCountForUATPerProject(rcaCount,
						MIX_CATEGORY) != 0) {
					uatText+=" "+calculateBugTypeCountForUATPerProject(
							rcaCount, MIX_CATEGORY) + " Others#";
					count++;
				}
				if (calculateBugTypeCountForUATPerProject(rcaCount,
						PRODUCT_DEFECT) != 0) {
					uatText+=" "+calculateBugTypeCountForUATPerProject(
							rcaCount, PRODUCT_DEFECT) + " Product Defect#";
					count++;
				}
				 /* Changes for Non RCA field addition */
				int nonRcaBugCount = calculateBugTypeCountForUATPerProject(rcaCount,NON_RCA_BUG);
				if (nonRcaBugCount != 0) {
					uatText+=" "+nonRcaBugCount + " Non RCA Bug#";
					count++;
				}
				
			}
			if(count==1){
				uatText=uatText.replace("#", "");
			}
			if(count>1){
				uatText=uatText.replaceAll("#", ",");
			}
			if(uatText.endsWith(",")){
				uatText = uatText.substring(0,uatText.length() - 1);
			}

		}
		return uatText;
		
	}
	
public String addProdComments(List<RcaCount> rcaCounts){
		
		String prodText="";
		RcaCount rcaCount = null;
		int count=0;

		Iterator<RcaCount> it = rcaCounts.iterator();
		while (it.hasNext()) {
			count=0;
			rcaCount = (RcaCount) it.next();
			int total = calculateBugTypeCountForProdPerProject(rcaCount,
					CLIENT_CODE_BUG)
					+ calculateBugTypeCountForProdPerProject(rcaCount,
							MISSED_REQUIREMENT)
					+ calculateBugTypeCountForProdPerProject(rcaCount,
							CHANGE_REQUIREMENT)
					+ calculateBugTypeCountForProdPerProject(rcaCount,
							INTEGRATION_ISSUE)
					+ calculateBugTypeCountForProdPerProject(rcaCount,
							CONFIGURATION_ISSUE)
					+ calculateBugTypeCountForProdPerProject(rcaCount,
							DATA_ISSUE)
					+ calculateBugTypeCountForProdPerProject(rcaCount,
							MIX_CATEGORY)
					+ calculateBugTypeCountForProdPerProject(rcaCount,
							PRODUCT_DEFECT)
					+ calculateBugTypeCountForProdPerProject(rcaCount,   /* Changes for Non RCA field addition */
							NON_RCA_BUG);
			if (total > 0) {
				prodText+="\n"
						+ rcaCount.getProjectDetails().getProjectName() + "("+total+") : ";
				if (calculateBugTypeCountForProdPerProject(rcaCount,
						CLIENT_CODE_BUG) != 0) {
					prodText+=" "+calculateBugTypeCountForProdPerProject(
							rcaCount, CLIENT_CODE_BUG) + " Client Code Defect#";
					count++;
				}
				if (calculateBugTypeCountForProdPerProject(rcaCount,
						MISSED_REQUIREMENT) != 0) {
					prodText+=" "+calculateBugTypeCountForProdPerProject(
							rcaCount, MISSED_REQUIREMENT)
							+ " Missed Requirement#";
					count++;
				}
				if (calculateBugTypeCountForProdPerProject(rcaCount,
						CHANGE_REQUIREMENT) != 0) {
					prodText+=" "+calculateBugTypeCountForProdPerProject(
							rcaCount, CHANGE_REQUIREMENT)
							+ " Change Requirement#";
					count++;
				}
				if (calculateBugTypeCountForProdPerProject(rcaCount,
						INTEGRATION_ISSUE) != 0) {
					prodText+=" "+calculateBugTypeCountForProdPerProject(
							rcaCount, INTEGRATION_ISSUE) + " Integration Issue#";
					count++;
				}
				if (calculateBugTypeCountForProdPerProject(rcaCount,
						CONFIGURATION_ISSUE) != 0) {
					prodText+=" "+calculateBugTypeCountForProdPerProject(
							rcaCount, CONFIGURATION_ISSUE)
							+ " Configuration Issue#";
					count++;
				}
				if (calculateBugTypeCountForProdPerProject(rcaCount, DATA_ISSUE) != 0) {
					prodText+=" "+calculateBugTypeCountForProdPerProject(
							rcaCount, DATA_ISSUE) + " Data Issue#";
					count++;
				}
				if (calculateBugTypeCountForProdPerProject(rcaCount,
						MIX_CATEGORY) != 0) {
					prodText+=" "+calculateBugTypeCountForProdPerProject(
							rcaCount, MIX_CATEGORY) + " Others#";
					count++;
				}
				if (calculateBugTypeCountForProdPerProject(rcaCount,
						PRODUCT_DEFECT) != 0) {
					prodText+=" "+calculateBugTypeCountForProdPerProject(
							rcaCount, PRODUCT_DEFECT) + " Product Defect#";
					count++;
				}
				 /* Changes for Non RCA field addition */
				int nonRcaBugProdCount = calculateBugTypeCountForProdPerProject(rcaCount,NON_RCA_BUG);
				if (nonRcaBugProdCount !=0) {
					prodText+=" "+nonRcaBugProdCount + " Non RCA Bug#";
					count++;
				}
				
			}
			
			if(count==1){
				prodText=prodText.replace("#", "");
			}
			if(count>1){
				prodText=prodText.replaceAll("#", ",");
			}
			if(prodText.endsWith(",")){
				prodText = prodText.substring(0,prodText.length() - 1);
			}

		}
		return prodText;
		
	}

public String addQAComments(List<RcaCount> rcaCounts){
	
	String qaText="";
	RcaCount rcaCount = null;
	int count=0;

	Iterator<RcaCount> it = rcaCounts.iterator();
	while (it.hasNext()) {
		count=0;
		rcaCount = (RcaCount) it.next();
		int total = calculateBugTypeCountForQAPerProject(rcaCount,
				CLIENT_CODE_BUG)
				+ calculateBugTypeCountForQAPerProject(rcaCount,
						MISSED_REQUIREMENT)
				+ calculateBugTypeCountForQAPerProject(rcaCount,
						CHANGE_REQUIREMENT)
				+ calculateBugTypeCountForQAPerProject(rcaCount,
						INTEGRATION_ISSUE)
				+ calculateBugTypeCountForQAPerProject(rcaCount,
						CONFIGURATION_ISSUE)
				+ calculateBugTypeCountForQAPerProject(rcaCount,
						DATA_ISSUE)
				+ calculateBugTypeCountForQAPerProject(rcaCount,
						MIX_CATEGORY)
				+ calculateBugTypeCountForQAPerProject(rcaCount,
						PRODUCT_DEFECT)
				+ calculateBugTypeCountForQAPerProject(rcaCount,   /* Changes for Non RCA field addition */
						NON_RCA_BUG);
		if (total > 0) {
			qaText+="\n"
					+ rcaCount.getProjectDetails().getProjectName() + "("+total+") : ";
			if (calculateBugTypeCountForQAPerProject(rcaCount,
					CLIENT_CODE_BUG) != 0) {
				qaText+=" "+calculateBugTypeCountForQAPerProject(
						rcaCount, CLIENT_CODE_BUG) + " Client Code Defect#";
				count++;
			}
			if (calculateBugTypeCountForQAPerProject(rcaCount,
					MISSED_REQUIREMENT) != 0) {
				qaText+=" "+calculateBugTypeCountForQAPerProject(
						rcaCount, MISSED_REQUIREMENT)
						+ " Missed Requirement#";
				count++;
			}
			if (calculateBugTypeCountForQAPerProject(rcaCount,
					CHANGE_REQUIREMENT) != 0) {
				qaText+=" "+calculateBugTypeCountForQAPerProject(
						rcaCount, CHANGE_REQUIREMENT)
						+ " Change Requirement#";
				count++;
			}
			if (calculateBugTypeCountForQAPerProject(rcaCount,
					INTEGRATION_ISSUE) != 0) {
				qaText+=" "+calculateBugTypeCountForQAPerProject(
						rcaCount, INTEGRATION_ISSUE) + " Integration Issue#";
				count++;
			}
			if (calculateBugTypeCountForQAPerProject(rcaCount,
					CONFIGURATION_ISSUE) != 0) {
				qaText+=" "+calculateBugTypeCountForQAPerProject(
						rcaCount, CONFIGURATION_ISSUE)
						+ " Configuration Issue#";
				count++;
			}
			if (calculateBugTypeCountForQAPerProject(rcaCount, DATA_ISSUE) != 0) {
				qaText+=" "+calculateBugTypeCountForQAPerProject(
						rcaCount, DATA_ISSUE) + " Data Issue#";
				count++;
			}
			if (calculateBugTypeCountForQAPerProject(rcaCount,
					MIX_CATEGORY) != 0) {
				qaText+=" "+calculateBugTypeCountForQAPerProject(
						rcaCount, MIX_CATEGORY) + " Others#";
				count++;
			}
			if (calculateBugTypeCountForQAPerProject(rcaCount,
					PRODUCT_DEFECT) != 0) {
				qaText+=" "+calculateBugTypeCountForQAPerProject(
						rcaCount, PRODUCT_DEFECT) + " Product Defect#";
				count++;
			}
			 /* Changes for Non RCA field addition */
			int nonRcaBugQaCount = calculateBugTypeCountForQAPerProject(rcaCount,NON_RCA_BUG);
			if (nonRcaBugQaCount != 0) {
				qaText+=" "+nonRcaBugQaCount + " Non RCA Bug#";
				count++;
			}
		}
		if(count==1){
			qaText=qaText.replace("#", "");
		}
		if(count>1){
			qaText=qaText.replaceAll("#", ",");
		}
		if(qaText.endsWith(",")){
			qaText = qaText.substring(0,qaText.length() - 1);
		}

	}
	return qaText;
	
}

public String addOpenComments(List<RcaCount> rcaCounts){
	
	String openText="";
	RcaCount rcaCount = null;
	int count=0;

	Iterator<RcaCount> it = rcaCounts.iterator();
	while (it.hasNext()) {
		count=0;
		rcaCount = (RcaCount) it.next();
		int total = calculateBugTypeCountForOpenPerProject(rcaCount,
				CLIENT_CODE_BUG)
				+ calculateBugTypeCountForOpenPerProject(rcaCount,
						MISSED_REQUIREMENT)
				+ calculateBugTypeCountForOpenPerProject(rcaCount,
						CHANGE_REQUIREMENT)
				+ calculateBugTypeCountForOpenPerProject(rcaCount,
						INTEGRATION_ISSUE)
				+ calculateBugTypeCountForOpenPerProject(rcaCount,
						CONFIGURATION_ISSUE)
				+ calculateBugTypeCountForOpenPerProject(rcaCount,
						DATA_ISSUE)
				+ calculateBugTypeCountForOpenPerProject(rcaCount,
						MIX_CATEGORY)
				+ calculateBugTypeCountForOpenPerProject(rcaCount,
						PRODUCT_DEFECT)
				+ calculateBugTypeCountForOpenPerProject(rcaCount,   /* Changes for Non RCA field addition */
						NON_RCA_BUG);
		if (total > 0) {
			openText+="\n"
					+ rcaCount.getProjectDetails().getProjectName() + "("+total+") : ";
			if (calculateBugTypeCountForOpenPerProject(rcaCount,
					MISSED_REQUIREMENT) != 0) {
				openText+=" "+calculateBugTypeCountForOpenPerProject(
						rcaCount, MISSED_REQUIREMENT) + " Missed Requirement#";
				count++;
			}
			if (calculateBugTypeCountForOpenPerProject(rcaCount,
					CHANGE_REQUIREMENT) != 0) {
				openText+=" "+calculateBugTypeCountForOpenPerProject(
						rcaCount, CHANGE_REQUIREMENT) + " Change Requirement#";
				count++;
			}
		
			if (calculateBugTypeCountForOpenPerProject(rcaCount,
					CLIENT_CODE_BUG) != 0) {
				openText+=" "+calculateBugTypeCountForOpenPerProject(
						rcaCount, CLIENT_CODE_BUG) + " Client Code Defect#";
				count++;
			}
			if (calculateBugTypeCountForOpenPerProject(rcaCount,
					INTEGRATION_ISSUE) != 0) {
				openText+=" "+calculateBugTypeCountForOpenPerProject(
						rcaCount, INTEGRATION_ISSUE) + " Integration Issue#";
				count++;
			}
			if (calculateBugTypeCountForOpenPerProject(rcaCount,
					CONFIGURATION_ISSUE) != 0) {
				openText+=" "+calculateBugTypeCountForOpenPerProject(
						rcaCount, CONFIGURATION_ISSUE)
						+ " Configuration Issue#";
				count++;
			}
			if (calculateBugTypeCountForOpenPerProject(rcaCount, DATA_ISSUE) != 0) {
				openText+=" "+calculateBugTypeCountForOpenPerProject(
						rcaCount, DATA_ISSUE) + " Data Issue#";
				count++;
			}
			if (calculateBugTypeCountForOpenPerProject(rcaCount,
					MIX_CATEGORY) != 0) {
				openText+=" "+calculateBugTypeCountForOpenPerProject(
						rcaCount, MIX_CATEGORY) + " Others#";
				count++;
			}
			if (calculateBugTypeCountForOpenPerProject(rcaCount,
					PRODUCT_DEFECT) != 0) {
				openText+=" "+calculateBugTypeCountForOpenPerProject(
						rcaCount, PRODUCT_DEFECT) + " Product Defect#";
				count++;
			}
			 /* Changes for Non RCA field addition */
			int nonRcaBugOpenCount = calculateBugTypeCountForOpenPerProject(rcaCount,NON_RCA_BUG);
			if (nonRcaBugOpenCount != 0) {
				openText+=" "+nonRcaBugOpenCount+ " Non RCA Bug#";
				count++;
			}
			
		}
		if(count==1){
			openText=openText.replace("#", "");
		}
		if(count>1){
			openText=openText.replaceAll("#", ",");
		}
		if(openText.endsWith(",")){
			openText = openText.substring(0,openText.length() - 1);
		}

	}
	return openText;
	
}


private int calculateBugTypeCountForUATPerProject(RcaCount rcaCount, String bugType)
	{
		int totalBugTypeCount = 0;
		
			
			if(bugType.equals(MIX_CATEGORY))
				totalBugTypeCount = rU.mixCategoryWeeklyCountForAllProjectsInUAT(rcaCount);
			else if(bugType.equals(DATA_ISSUE))
				totalBugTypeCount = rU.weeklyDataIssueForAllIssuesInUAT(rcaCount);
			else if(bugType.equals(INTEGRATION_ISSUE))
				totalBugTypeCount = rU.weeklyIntegrationIssueForAllIssuesInUAT(rcaCount);
			else if(bugType.equals(CONFIGURATION_ISSUE))
				totalBugTypeCount = rU.weeklyConfigurationIssueForAllIssuesInUAT(rcaCount);
			else if(bugType.equals(MISSED_REQUIREMENT))
				totalBugTypeCount = rU.weeklyMissedCountForAllIssuesInUAT(rcaCount);
			else if(bugType.equals(CHANGE_REQUIREMENT))
				totalBugTypeCount = rU.weeklyCRCountForAllIssuesInUAT(rcaCount);
			else if(bugType.equals(CLIENT_CODE_BUG))
				totalBugTypeCount = rU.weeklyClientCodeBugForAllIssuesInUAT(rcaCount);
			else if(bugType.equals(PRODUCT_DEFECT))
				totalBugTypeCount = rU.weeklyProductDefectForAllIssuesInUAT(rcaCount);
			else if(bugType.equals(NON_RCA_BUG))  /* Changes for Non RCA field addition */
				totalBugTypeCount = rU.weeklyNonRcaBugForAllIssuesInUAT(rcaCount);
	
		
		return totalBugTypeCount;
	}
	
	private int calculateBugTypeCountForProdPerProject(RcaCount rcaCount, String bugType)
	{
		int totalBugTypeCount = 0;
		
			
			if(bugType.equals(MIX_CATEGORY))
				totalBugTypeCount = rU.mixCategoryWeeklyCountForAllProjectsInProd(rcaCount);
			else if(bugType.equals(DATA_ISSUE))
				totalBugTypeCount = rU.weeklyDataIssueForAllIssuesInProd(rcaCount);
			else if(bugType.equals(INTEGRATION_ISSUE))
				totalBugTypeCount = rU.weeklyIntegrationIssueForAllIssuesInProd(rcaCount);
			else if(bugType.equals(CONFIGURATION_ISSUE))
				totalBugTypeCount = rU.weeklyConfigurationIssueForAllIssuesInProd(rcaCount);
			else if(bugType.equals(MISSED_REQUIREMENT))
				totalBugTypeCount = rU.weeklyMissedCountForAllIssuesInProd(rcaCount);
			else if(bugType.equals(CHANGE_REQUIREMENT))
				totalBugTypeCount = rU.weeklyCRCountForAllIssuesInProd(rcaCount);
			else if(bugType.equals(CLIENT_CODE_BUG))
				totalBugTypeCount = rU.weeklyClientCodeBugForAllIssuesInProd(rcaCount);
			else if(bugType.equals(PRODUCT_DEFECT))
				totalBugTypeCount = rU.weeklyProductDefectForAllIssuesInProd(rcaCount);
			else if(bugType.equals(NON_RCA_BUG))   /* Changes for Non RCA field addition */
				totalBugTypeCount = rU.weeklyNonRcaBugForAllIssuesInProd(rcaCount);
	
		
		return totalBugTypeCount;
	}
	
	private int calculateBugTypeCountForQAPerProject(RcaCount rcaCount, String bugType)
	{
		int totalBugTypeCount = 0;
		
			
			if(bugType.equals(MIX_CATEGORY))
				totalBugTypeCount = rU.mixCategoryWeeklyCountForAllProjectsInQA(rcaCount);
			else if(bugType.equals(DATA_ISSUE))
				totalBugTypeCount = rU.weeklyDataIssueForAllIssuesInQA(rcaCount);
			else if(bugType.equals(INTEGRATION_ISSUE))
				totalBugTypeCount = rU.weeklyIntegrationIssueForAllIssuesInQA(rcaCount);
			else if(bugType.equals(CONFIGURATION_ISSUE))
				totalBugTypeCount = rU.weeklyConfigurationIssueForAllIssuesInQA(rcaCount);
			else if(bugType.equals(MISSED_REQUIREMENT))
				totalBugTypeCount = rU.weeklyMissedCountForAllIssuesInQA(rcaCount);
			else if(bugType.equals(CHANGE_REQUIREMENT))
				totalBugTypeCount = rU.weeklyCRCountForAllIssuesInQA(rcaCount);
			else if(bugType.equals(CLIENT_CODE_BUG))
				totalBugTypeCount = rU.weeklyClientCodeBugForAllIssuesInQA(rcaCount);
			else if(bugType.equals(PRODUCT_DEFECT))
				totalBugTypeCount = rU.weeklyProductDefectForAllIssuesInQA(rcaCount);
			else if(bugType.equals(NON_RCA_BUG))   /* Changes for Non RCA field addition */
				totalBugTypeCount = rU.weeklyNonRcaBugForAllIssuesInQA(rcaCount);

	
		
		return totalBugTypeCount;
	}
	
	private int calculateBugTypeCountForOpenPerProject(RcaCount rcaCount, String bugType)
	{
		int totalBugTypeCount = 0;
		
			
			if(bugType.equals(MIX_CATEGORY))
				totalBugTypeCount = rU.mixCategoryWeeklyCountForAllProjectsInOpen(rcaCount);
			else if(bugType.equals(MISSED_REQUIREMENT))
				totalBugTypeCount = rU.weeklyMissedRequirementsForAllIssuesInOpen(rcaCount);
			else if(bugType.equals(CHANGE_REQUIREMENT))
				totalBugTypeCount = rU.weeklyChangeRequirementsForAllIssuesInOpen(rcaCount);
			else if(bugType.equals(DATA_ISSUE))
				totalBugTypeCount = rU.weeklyDataIssueForAllIssuesInOpen(rcaCount);
			else if(bugType.equals(INTEGRATION_ISSUE))
				totalBugTypeCount = rU.weeklyIntegrationIssueForAllIssuesInOpen(rcaCount);
			else if(bugType.equals(CONFIGURATION_ISSUE))
				totalBugTypeCount = rU.weeklyConfigurationIssueForAllIssuesInOpen(rcaCount);
			else if(bugType.equals(CLIENT_CODE_BUG))
				totalBugTypeCount = rU.weeklyClientCodeBugForAllIssuesInOpen(rcaCount);
			else if(bugType.equals(PRODUCT_DEFECT))
				totalBugTypeCount = rU.weeklyProductDefectForAllIssuesInOpen(rcaCount);
			else if(bugType.equals(NON_RCA_BUG))   /* Changes for Non RCA field addition */
				totalBugTypeCount = rU.weeklyNonRcaBugForAllIssuesInOpen(rcaCount);
		return totalBugTypeCount;
	}
	
	/**
	 * This method get active project list and create PPT for all active projects.
	 * @throws IOException
	 */
	private void createProjectSpecificGraphs(String week) throws IOException{
		
		List<ProjectDetails> activeProjectList = projectDetailsManager.getAllActiveProjects();
		Calendar calobj = Calendar.getInstance();
		calobj.add(Calendar.DATE, 1);
		SimpleDateFormat sdfmt1 = new SimpleDateFormat("yyyy/MM/dd");
		System.out.println("date is : " + calobj.getTime());
				
		if(activeProjectList != null && activeProjectList.size()!=0)
		{
			for(int index =0; index < activeProjectList.size(); index++)
			{
				List<RcaCount> rcaCounts = rcaManager.findRCAReportForMultipleWeekForProject(activeProjectList.get(index).getProjectId() );
				//SprintReport sprintReport  = sprintReportManager.findWeeklySprintReportByProjectId(week,activeProjectList.get(index).getProjectId() );
				ArrayList<SprintReport> sprintReport = sprintReportManager.findExistingSprintReportByProjectId(
						sdfmt1.format(calobj.getTime()), activeProjectList.get(index).getProjectId());
				if(sprintReport != null && sprintReport.size() >0)
				Collections.reverse(sprintReport);
				
				//if(sprintReport!=null)
					//comment by satish
					//sprintReport.setWeek(rU.removeYearFromWeek(sprintReport.getWeek()));
				  
				if(rcaCounts !=null && rcaCounts.size() >0)
  
				{  
					createGraphIndividualPpt(rcaCounts,sprintReport, ppt);
				}
			}
		}
		
	}
	
	/**
	 * This method get active project list and create PPT for all active projects.
	 * @throws IOException
	 */
	private void createProjectSpecificGraphs() throws IOException {
		List<ProjectDetails> activeProjectList = projectDetailsManager.getAllActiveProjects();
		if (activeProjectList != null && activeProjectList.size() != 0) {
			for (int index = 0; index < activeProjectList.size(); index++) {
				Calendar calobj = Calendar.getInstance();
				calobj.add(Calendar.DATE, 1);
				SimpleDateFormat sdfmt1 = new SimpleDateFormat("yyyy/MM/dd");
				System.out.println("date is : " + calobj.getTime());
				ArrayList<SprintReport> sprintReport = sprintReportManager.findExistingSprintReportByProjectId(
						sdfmt1.format(calobj.getTime()), activeProjectList.get(index).getProjectId());
				
				if (sprintReport != null)
					createSprintGraphIndividualPpt(sprintReport, ppt);
			}
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
			txt1.setAnchor(new java.awt.Rectangle(0, 0, pageWidth+30, pageheight/10));
			RichTextRun rt1 = txt1.getTextRun().getRichTextRuns()[0];
			rt1.setFontSize(25);
			rt1.setFontName("Franklin Gothic Medium");
			rt1.setAlignment(TextBox.AlignLeft);
			slide.addShape(txt1);
			
			TextBox txt2 = new TextBox();
			txt2.setText("Total " + totalWeeklyBugCount + "\n" + "\n");
			
			TextRun tr = txt2.createTextRun();
			if(calculateTotalBugTypeCountForQA(rcaCounts, CLIENT_CODE_BUG) == 0){
				tr.appendText(0 +" Client Code Defect" + "\n");
			}
			else
			{
				tr.appendText(calculateTotalBugTypeCountForQA(rcaCounts, CLIENT_CODE_BUG) +" Client Code Defect" + "\n");
				fillProjectNameWithCcbCount(projectNameWithCcbCount,tr);		
			}
			if(calculateTotalBugTypeCountForQA(rcaCounts, MISSED_REQUIREMENT) != 0){
				tr.appendText(calculateTotalBugTypeCountForQA(rcaCounts, MISSED_REQUIREMENT) + " Missed Requirement" +  "\n");
			}
			if(calculateTotalBugTypeCountForQA(rcaCounts, CHANGE_REQUIREMENT) != 0){
				tr.appendText(calculateTotalBugTypeCountForQA(rcaCounts, CHANGE_REQUIREMENT) + " Change Requirement" +  "\n");
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
			 /* Changes for Non RCA field addition */
			int nonRcaBugCountQa = calculateTotalBugTypeCountForQA(rcaCounts, NON_RCA_BUG);
			if(nonRcaBugCountQa != 0){
				tr.appendText(nonRcaBugCountQa + " Non RCA Bug" + "\n");
			}
			if(calculateTotalBugTypeCountForQA(rcaCounts, MIX_CATEGORY) != 0){
				tr.appendText(calculateTotalBugTypeCountForQA(rcaCounts, MIX_CATEGORY) + " Others " + "\n");
			}
			if(calculateTotalBugTypeCountForQA(rcaCounts, PRODUCT_DEFECT) != 0){
				tr.appendText(calculateTotalBugTypeCountForQA(rcaCounts, PRODUCT_DEFECT) + " Product Defect "+ "\n");
			}
			
			/* Changes for Close Ticket field addition */
			int openTicketCountQa = calculateTotalBugTypeCountForQA(rcaCounts, CLOSEICKETS);
			if(openTicketCountQa != 0){
				tr.appendText(openTicketCountQa + " Close Ticket" + "\n");
			}
			
			txt2.setAnchor(new java.awt.Rectangle(pageWidth+40, 20, pageWidth-50, pageheight-50));
			RichTextRun rt2Heading = tr.getRichTextRuns()[0];
			rt2Heading.setFontSize(18);
			rt2Heading.setFontName("Franklin Gothic Medium");
			for(int i=1; i<tr.getRichTextRuns().length;i++){
				RichTextRun rt2 = tr.getRichTextRuns()[i];
				rt2.setFontSize(12);
				rt2.setFontName("Franklin Gothic Body");
				rt2.setAlignment(TextBox.AlignLeft);
			}
			slide.addShape(txt2);

		}
		
		//Calling Prod Slide Data method.
		if(PRODUCTION.equals(slideType))
		{
			fillProdDataInSlide(allWeeksrcaCounts, rcaCounts);
			TextBox txt1 = new TextBox();
			txt1.setText("Reported Prod");
			txt1.setAnchor(new java.awt.Rectangle(0, 0, pageWidth+30, pageheight/10));
			RichTextRun rt1 = txt1.getTextRun().getRichTextRuns()[0];
			rt1.setFontSize(25);
			rt1.setFontName("Franklin Gothic Medium");
			rt1.setAlignment(TextBox.AlignLeft);
			slide.addShape(txt1);
			    
			TextBox txt2 = new TextBox();
			txt2.setText("Total " + totalWeeklyBugCount + "\n" + "\n");
			
			TextRun tr = txt2.createTextRun();
			if(calculateTotalBugTypeCountForProd(rcaCounts, CLIENT_CODE_BUG) == 0){
				tr.appendText(0 +" Client Code Defect" + "\n");
			}
			else
			{
				tr.appendText(calculateTotalBugTypeCountForProd(rcaCounts, CLIENT_CODE_BUG) +" Client Code Defect" + "\n");
				fillProjectNameWithCcbCount(projectNameWithCcbCount, tr);
			}
			if(calculateTotalBugTypeCountForProd(rcaCounts, MISSED_REQUIREMENT) != 0){
				tr.appendText(calculateTotalBugTypeCountForProd(rcaCounts, MISSED_REQUIREMENT) + " Missed Requirement" +  "\n");
			}
			if(calculateTotalBugTypeCountForProd(rcaCounts, CHANGE_REQUIREMENT) != 0){
				tr.appendText(calculateTotalBugTypeCountForProd(rcaCounts, CHANGE_REQUIREMENT) + " Changed Requirement" +  "\n");
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
			 /* Changes for Non RCA field addition */
			int nonRcaBugCount = calculateTotalBugTypeCountForProd(rcaCounts, NON_RCA_BUG);
			if(nonRcaBugCount != 0){
				tr.appendText(nonRcaBugCount + " Non RCA Bug" + "\n");
			}
			if(calculateTotalBugTypeCountForProd(rcaCounts, MIX_CATEGORY) != 0){
				tr.appendText(calculateTotalBugTypeCountForProd(rcaCounts, MIX_CATEGORY) + " Others " + "\n");
			}
			if(calculateTotalBugTypeCountForProd(rcaCounts, PRODUCT_DEFECT) != 0){
				tr.appendText(calculateTotalBugTypeCountForProd(rcaCounts, PRODUCT_DEFECT) + " Product Defect "+ "\n");
			}
			
			/* Changes for Close Ticket field addition */
			int openTicketCount = calculateTotalBugTypeCountForProd(rcaCounts, CLOSEICKETS);
			if(openTicketCount != 0){
				tr.appendText(openTicketCount + " Close Ticket" + "\n");
			}

			txt2.setAnchor(new java.awt.Rectangle(pageWidth+40, 20, pageWidth-50, pageheight-50));
			RichTextRun rt2Heading = tr.getRichTextRuns()[0];
			rt2Heading.setFontSize(18);
			rt2Heading.setFontName("Franklin Gothic Medium");
			for(int i=1; i<tr.getRichTextRuns().length;i++){
				RichTextRun rt2 = tr.getRichTextRuns()[i];
				rt2.setFontSize(14);
				rt2.setFontName("Franklin Gothic Body");
				rt2.setAlignment(TextBox.AlignLeft);
			}
			slide.addShape(txt2);
		}
		
		//Calling UAT Slide Data method.
		if(UAT.equals(slideType))
		{
			fillUATDataInSlide(allWeeksrcaCounts, rcaCounts);
			TextBox txt1 = new TextBox();
			txt1.setText("Reported UAT");
			txt1.setAnchor(new java.awt.Rectangle(0, 0, pageWidth+30, pageheight/10));
			RichTextRun rt1 = txt1.getTextRun().getRichTextRuns()[0];
			rt1.setFontSize(25);
			rt1.setFontName("Franklin Gothic Medium");
			rt1.setAlignment(TextBox.AlignLeft);
			slide.addShape(txt1);
			
			TextBox txt2 = new TextBox();
			txt2.setText("Total " + totalWeeklyBugCount + "\n" + "\n");
			
			TextRun tr = txt2.createTextRun();
			if(calculateTotalBugTypeCountForUAT(rcaCounts, CLIENT_CODE_BUG) == 0){
				tr.appendText(0 +" Client Code Defect" + "\n");
			}
			else
			{
				tr.appendText(calculateTotalBugTypeCountForUAT(rcaCounts, CLIENT_CODE_BUG) +" Client Code Defect" + "\n");
				fillProjectNameWithCcbCount(projectNameWithCcbCount, tr);
			}
			if(calculateTotalBugTypeCountForUAT(rcaCounts, MISSED_REQUIREMENT) != 0){
				tr.appendText(calculateTotalBugTypeCountForUAT(rcaCounts, MISSED_REQUIREMENT) + " Missed Requirement" +  "\n");
			}
			if(calculateTotalBugTypeCountForUAT(rcaCounts, CHANGE_REQUIREMENT) != 0){
				tr.appendText(calculateTotalBugTypeCountForUAT(rcaCounts, CHANGE_REQUIREMENT) + " Change Requirement" +  "\n");
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
			 /* Changes for Non RCA field addition */
			int nonRcaBugCountUat = calculateTotalBugTypeCountForUAT(rcaCounts, NON_RCA_BUG);
			if(nonRcaBugCountUat != 0){
				tr.appendText(nonRcaBugCountUat + " Non RCA Bug" + "\n");
			}
			if(calculateTotalBugTypeCountForUAT(rcaCounts, MIX_CATEGORY) != 0){
				tr.appendText(calculateTotalBugTypeCountForUAT(rcaCounts, MIX_CATEGORY) + " Others " + "\n");
			}
			if(calculateTotalBugTypeCountForUAT(rcaCounts, PRODUCT_DEFECT) != 0){
				tr.appendText(calculateTotalBugTypeCountForUAT(rcaCounts, PRODUCT_DEFECT) + " Product Defect "+ "\n");
			}
			
			 /* Changes for Close Ticket field addition */
			int openTicketCountUat = calculateTotalBugTypeCountForUAT(rcaCounts, CLOSEICKETS);
			if(openTicketCountUat != 0){
				tr.appendText(openTicketCountUat + " Close Ticket" + "\n");
			}
									
			txt2.setAnchor(new java.awt.Rectangle(pageWidth+40, 20, pageWidth-50, pageheight-50));
			RichTextRun rt2Heading = tr.getRichTextRuns()[0];
			rt2Heading.setFontSize(18);
			rt2Heading.setFontName("Franklin Gothic Medium");
			for(int i=1; i<tr.getRichTextRuns().length;i++){
				RichTextRun rt2 = tr.getRichTextRuns()[i];
				rt2.setFontSize(14);
				rt2.setFontName("Franklin Gothic Body");
				rt2.setAlignment(TextBox.AlignLeft);
			}
			slide.addShape(txt2);
		}
		
		//Calling Cumulative Open Slide Data method.
		if(CUMULATIVE_OPEN.equals(slideType))
		{
			fillCumulativeOpenDataInSlide(allWeeksrcaCounts, rcaCounts);
			TextBox txt1 = new TextBox();
			txt1.setText("Cumulative Open");
			txt1.setAnchor(new java.awt.Rectangle(0, 0, pageWidth+30, pageheight/10));
			RichTextRun rt1 = txt1.getTextRun().getRichTextRuns()[0];
			rt1.setFontSize(25);
			rt1.setFontName("Franklin Gothic Medium");
			rt1.setAlignment(TextBox.AlignLeft);
			slide.addShape(txt1);

			TextBox txt2 = new TextBox();
			txt2.setText("Backlog prioritized as per the business direction");

			txt2.setAnchor(new java.awt.Rectangle(pageWidth+40, 20, pageWidth-50, pageheight-50));
			RichTextRun rt2 = txt2.getTextRun().getRichTextRuns()[0];
			rt2.setFontSize(18);
			rt2.setFontName("Franklin Gothic Medium");
			rt2.setAlignment(TextBox.AlignLeft);
			slide.addShape(txt2);
		}
		
		
		  
		Picture pict = new Picture(idx);
		//set image position in the slide
		pict.setAnchor(new java.awt.Rectangle(5, 30, pageWidth+30, pageheight-50));
		slide.addShape(pict);

		Picture pict1 = new Picture(bWCx);
		//set image position in the slide
		pict1.setAnchor(new java.awt.Rectangle(20, pageheight+30, pageWidth-40, pageheight-50));
		slide.addShape(pict1);

		Picture ccbLineGraph = new Picture(lCx);
		// set image position in the slide
		ccbLineGraph.setAnchor(new java.awt.Rectangle(pageWidth + 20,
				pageheight + 30, pageWidth - 40, pageheight - 50));
		slide.addShape(ccbLineGraph);
		
	}
	
	public void closeVsOpenDefectSlide(List<RcaCount> rcaCounts) throws IOException
	{
		Slide slide = ppt.createSlide();
		int pageWidth = ppt.getPageSize().width/2;
		int pageheight = ppt.getPageSize().height/2;
		// add a new picture to this slideshow and insert it in a  new slide
		List<RcaCount> allWeeksrcaCounts = rcaManager.findRCAReportForMultipleWeek(rca.getWeek());
		
			
			fillCloseOpenDataInSlide(allWeeksrcaCounts, rcaCounts);
			TextBox txt3 = new TextBox();
			
			txt3.setText("Logged Vs Resolved Defects");
			txt3.setAnchor(new java.awt.Rectangle(0, 0, pageWidth+30, pageheight/10));
			RichTextRun rt1 = txt3.getTextRun().getRichTextRuns()[0];
			rt1.setFontSize(25);
			rt1.setFontName("Franklin Gothic Medium");
			rt1.setAlignment(TextBox.AlignLeft);
			
			
			
			Picture pic = new Picture(idx);
			pic.setAnchor(new java.awt.Rectangle(5, 50, pageWidth+30, pageheight-50));
			slide.addShape(txt3);
			slide.addShape(pic);
			
			Picture pict1 = new Picture(bWCx);
			//set image position in the slide
			pict1.setAnchor(new java.awt.Rectangle(20, pageheight+30, pageWidth+30, pageheight-50));
			slide.addShape(pict1);
			
	}
	
	/**
	 * 
	 * @param sprintReport
	 * @param ppt
	 * @throws IOException
	 */
	public void createSprintGraphIndividualPpt(ArrayList<SprintReport> sprintReport , SlideShow ppt) throws IOException{
		Slide slide = ppt.createSlide();
		int pageWidth = ppt.getPageSize().width/2;
		int pageheight = ppt.getPageSize().height/2;
		
		TextBox txt1 = new TextBox();
		txt1.setText("Spartan(Dev:3, QA:2)");

		txt1.setAnchor(new java.awt.Rectangle(0, 0, pageWidth+30, pageheight/10));
		RichTextRun rt1 = txt1.getTextRun().getRichTextRuns()[0];
		rt1.setFontSize(25);
		rt1.setFontName("Franklin Gothic Medium");
		rt1.setAlignment(TextBox.AlignLeft);
		slide.addShape(txt1);
		
		int sidx=0;
		if(sprintReport!= null){
			sidx = ppt.addPicture(generateGraph.createSprintGraph( rU.reportedSprintReportGraph(sprintReport), "", "", "", 
					PlotOrientation.VERTICAL, false, 850, 450,RCAConstants.BAR) , XSLFPictureData.PICTURE_TYPE_PNG);
		}
		
		Picture pict1 = new Picture(sidx);
		pict1.setAnchor(new java.awt.Rectangle(5, 30, pageWidth+30, pageheight-50));
		slide.addShape(pict1);
		
		Log.debug("Exit createSprintGraphIndividualPpt");
		
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
				PlotOrientation.VERTICAL, false, 850, 450,RCAConstants.BAR) , XSLFPictureData.PICTURE_TYPE_PNG);
		
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
				PlotOrientation.VERTICAL, true, 850, 450,RCAConstants.BAR) , XSLFPictureData.PICTURE_TYPE_PNG);
		
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
				PlotOrientation.VERTICAL, true, 850, 450,RCAConstants.BAR) , XSLFPictureData.PICTURE_TYPE_PNG);
		
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
				
		idx = ppt.addPicture(generateGraph.createGraph( rU.rcaCountForLastWeekForAllProjects(rcaCounts), "", "", "", 
				PlotOrientation.VERTICAL, true, 850, 550,RCAConstants.BAR) , XSLFPictureData.PICTURE_TYPE_PNG);
		
		lCx = ppt.addPicture(generateGraph.createLineGraph(rU.reportedAllWeeksCCBGraphForAllProject(allWeeksrcaCounts, allWeeks, CUMULATIVE_OPEN), "Client Code Trend", "", "",
				PlotOrientation.VERTICAL, true, 650, 450,RCAConstants.LINE) , XSLFPictureData.PICTURE_TYPE_PNG);
		
		bWCx = ppt.addPicture(generateGraph.createWeeklyGraph(rU.reportedCumulativeOpenAllWeeksGraphForAllProject(allWeeksrcaCounts, allWeeks), "Weekly Trend", "", "", 
				PlotOrientation.VERTICAL, true, 650, 450,RCAConstants.BAR) , XSLFPictureData.PICTURE_TYPE_PNG);
	}
	
	/**
	 * Method to fill relevant data for Cumulative Open slide in PPT.
	 * @param allWeeksrcaCounts
	 * @param rcaCounts
	 * @throws IOException
	 */
	public void fillCloseOpenDataInSlide(List<RcaCount> allWeeksrcaCounts, List<RcaCount> rcaCounts) throws IOException{
		
		ReportUtility rU = new ReportUtility();
		List<String> allWeeks = rU.findWeeks(rca.getWeek());
		LoggedDefectsVsOpen logDefOpen = new LoggedDefectsVsOpen();
		idx = ppt.addPicture(generateGraph.createGraphForOpenCloseDefects( rU.rcaCountForLastWeekForOpenVsCloseProjects(rcaCounts), "Logged Vs Resolved Defects", "", "", 
				PlotOrientation.VERTICAL, true, 850, 550,RCAConstants.BAR) , XSLFPictureData.PICTURE_TYPE_PNG);
		
		bWCx = ppt.addPicture(generateGraph.createWeeklyGraphCloseVsOpen( logDefOpen.reportedWeeklyTrendLoggedVsOpen(allWeeksrcaCounts, allWeeks), "Weekly Trend", "", "", 
				PlotOrientation.VERTICAL, true, 850, 550,RCAConstants.BAR, true, false) , XSLFPictureData.PICTURE_TYPE_PNG);
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
		projectNameWithCcbCount = new HashMap<String, Integer>();
		
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
			else if(bugType.equals(MISSED_REQUIREMENT))
				totalBugTypeCount = totalBugTypeCount + rU.weeklyMissedCountForAllIssuesInProd(rcaCount);
			else if(bugType.equals(CHANGE_REQUIREMENT))
				totalBugTypeCount = totalBugTypeCount + rU.weeklyCRCountForAllIssuesInProd(rcaCount);
			else if(bugType.equals(CLIENT_CODE_BUG))
			{
				totalBugTypeCount = totalBugTypeCount + rU.weeklyClientCodeBugForAllIssuesInProd(rcaCount);
				if(projectNameWithCcbCount!=null && rcaCount.getCcbProd()!=0)
					projectNameWithCcbCount.put(rcaCount.getProjectDetails().getProjectName(), rcaCount.getCcbProd());
			}
			else if(bugType.equals(PRODUCT_DEFECT))
				totalBugTypeCount = totalBugTypeCount + rU.weeklyProductDefectForAllIssuesInProd(rcaCount);
			else if(bugType.equals(NON_RCA_BUG))    /* Changes for Non RCA field addition */
				totalBugTypeCount = totalBugTypeCount + rU.weeklyNonRcaBugForAllIssuesInProd(rcaCount);

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
		projectNameWithCcbCount = new HashMap<String, Integer>();
		
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
			else if(bugType.equals(MISSED_REQUIREMENT))
				totalBugTypeCount = totalBugTypeCount + rU.weeklyMissedCountForAllIssuesInQA(rcaCount);
			else if(bugType.equals(CHANGE_REQUIREMENT))
				totalBugTypeCount = totalBugTypeCount + rU.weeklyCRCountForAllIssuesInQA(rcaCount);
			else if(bugType.equals(CLIENT_CODE_BUG))
			{
				totalBugTypeCount = totalBugTypeCount + rU.weeklyClientCodeBugForAllIssuesInQA(rcaCount);
				if(projectNameWithCcbCount!=null && rcaCount.getCcbQa() !=0)
					projectNameWithCcbCount.put(rcaCount.getProjectDetails().getProjectName(), rcaCount.getCcbQa());
			}			
			else if(bugType.equals(PRODUCT_DEFECT))
				totalBugTypeCount = totalBugTypeCount + rU.weeklyProductDefectForAllIssuesInQA(rcaCount);
			else if(bugType.equals(NON_RCA_BUG))   /* Changes for Non RCA field addition */
				totalBugTypeCount = totalBugTypeCount + rU.weeklyNonRcaBugForAllIssuesInQA(rcaCount);

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
		projectNameWithCcbCount = new HashMap<String, Integer>();
		
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
			else if(bugType.equals(MISSED_REQUIREMENT))
				totalBugTypeCount = totalBugTypeCount + rU.weeklyMissedCountForAllIssuesInUAT(rcaCount);
			else if(bugType.equals(CHANGE_REQUIREMENT))
				totalBugTypeCount = totalBugTypeCount + rU.weeklyCRCountForAllIssuesInUAT(rcaCount);
			else if(bugType.equals(CLIENT_CODE_BUG))
			{
				totalBugTypeCount = totalBugTypeCount + rU.weeklyClientCodeBugForAllIssuesInUAT(rcaCount);
				if(projectNameWithCcbCount!=null && rcaCount.getCcbUat()!=0)
					projectNameWithCcbCount.put(rcaCount.getProjectDetails().getProjectName(), rcaCount.getCcbUat());
			}
			else if(bugType.equals(PRODUCT_DEFECT))
				totalBugTypeCount = totalBugTypeCount + rU.weeklyProductDefectForAllIssuesInUAT(rcaCount);
			else if(bugType.equals(NON_RCA_BUG))   /* Changes for Non RCA field addition */
				totalBugTypeCount = totalBugTypeCount + rU.weeklyNonRcaBugForAllIssuesInUAT(rcaCount);

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
		txt1.setAnchor(new java.awt.Rectangle(0, 0, pageWidth+30,
				pageheight / 10));
		RichTextRun rt1 = txt1.getTextRun().getRichTextRuns()[0];
		rt1.setFontSize(25);
		rt1.setFontName("Franklin Gothic Medium");
		rt1.setAlignment(TextBox.AlignLeft);
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
		tr.setText("\n");
		addDetailReopenCount(rcaCounts, tr, PRODUCTION);
		addDetailReopenCount(rcaCounts, tr, UAT);
		addDetailReopenCount(rcaCounts, tr, QA);

		txt2.setAnchor(new java.awt.Rectangle(x, y, pageWidth * 2 - 50,
				pageheight - 80));
		for(int i=0; i<tr.getRichTextRuns().length;i++){
			RichTextRun rt2 = tr.getRichTextRuns()[i];
			rt2.setFontSize(18);
			rt2.setFontName("Franklin Gothic Medium");
			rt2.setAlignment(TextBox.AlignLeft);
		}
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
	/**
	 * 
	 * @param rcaCount
	 * @param ppt
	 * @throws IOException
	 */
	public void createGraphIndividualPpt(List<RcaCount> rcaCount, ArrayList<SprintReport> sprintReport , SlideShow ppt) throws IOException{
		Log.debug("Enter createGraphIndividualPpt");
		Slide slide = ppt.createSlide();
		int pageWidth = ppt.getPageSize().width/4;
		int pageheight = ppt.getPageSize().height/3;
		GenerateGraph generateGraph = new GenerateGraph(); 
		LoggedDefectsVsOpen logDefOpen = new LoggedDefectsVsOpen();
		// add a new picture to this slideshow and insert it in a new slide
		//List<RcaCount> allWeeksrcaCounts = rcaManager.findRCAReportForMultipleWeek();
		ReportUtility rU = new ReportUtility();
		List<String> allWeeks = rU.findWeeks(rca.getWeek());
		int idx1 = ppt.addPicture(generateGraph.createGraph( rU.reportedOpenAllWeeksGraphForIndividualProject(rcaCount, allWeeks), "Cumulative Open", "", "",
				PlotOrientation.VERTICAL, true, 650, 950,RCAConstants.BAR, false, true) , XSLFPictureData.PICTURE_TYPE_PNG);
		int idx2 = ppt.addPicture(generateGraph.createGraph( rU.reportedProdAllWeeksGraphForIndividualProject(rcaCount, allWeeks), "Weekly PROD", "", "",
				PlotOrientation.VERTICAL, true, 650, 950,RCAConstants.BAR, false, true) , XSLFPictureData.PICTURE_TYPE_PNG);
		int idx3 = ppt.addPicture(generateGraph.createGraph( rU.reportedUATAllWeeksGraphForIndividualProject(rcaCount, allWeeks), "Weekly UAT", "", "",
				PlotOrientation.VERTICAL, true, 650, 950,RCAConstants.BAR, false, true) , XSLFPictureData.PICTURE_TYPE_PNG);
		int idx4 = ppt.addPicture(generateGraph.createGraph( rU.reportedQAAllWeeksGraphForIndividualProject(rcaCount, allWeeks), "Weekly QA", "", "",
				PlotOrientation.VERTICAL, true, 650, 950,RCAConstants.BAR, false, true) , XSLFPictureData.PICTURE_TYPE_PNG);
//		int idxOpenClose = ppt.addPicture(generateGraph.createOpenCloseVsDefectsGraph( rU.reportedQAAllWeeksGraphForIndividualProject(rcaCount, allWeeks), "Open Vs Close Defects", "", "",
//				PlotOrientation.VERTICAL, true, 700, 950,RCAConstants.BAR, false, true) , XSLFPictureData.PICTURE_TYPE_PNG);
		
		
		
		int idx5 = 0,idxOpenClose=0;
		if(sprintReport!= null){
		    /*idx5 = ppt.addPicture(generateGraph.createWeeklyBarGraph( rU.reportedSprintGraph(sprintReport), rcaCount.get(0).getProjectDetails().getProjectName()+"( Dev: "+sprintReport.getDevMembers()+", QA: "+sprintReport.getQaMembers()+")", "", "",
				PlotOrientation.VERTICAL, false, 700, 450,RCAConstants.NORMAL_BAR,true,false) , XSLFPictureData.PICTURE_TYPE_PNG);*/
			/*idx5 = ppt.addPicture(generateGraph.createWeeklyBarGraph( rU.reportedSprintReportGraph(sprintReport), rcaCount.get(0).getProjectDetails().getProjectName()+"( Dev: "+sprintReport.getDevMembers()+", QA: "+sprintReport.getQaMembers()+")", "", "",
					PlotOrientation.VERTICAL, false, 700, 450,RCAConstants.NORMAL_BAR,true,false) , XSLFPictureData.PICTURE_TYPE_PNG);*/
			idx5= ppt.addPicture(generateGraph.createSprintGraph( rU.reportedSprintReportGraph(sprintReport),rcaCount.get(0).getProjectDetails().getProjectName()+"(Dev:"+sprintReport.get(sprintReport.size() - 1).getDevMembers()+" , QA:"+sprintReport.get(sprintReport.size() - 1).getQaMembers()+" )", "", "", 
					PlotOrientation.VERTICAL, false, 700, 450,RCAConstants.BAR) , XSLFPictureData.PICTURE_TYPE_PNG);
			
		}
		
		/* Correcting the Project Dashboard name location & Adding Overview/Risk Issues section in Individual Project PPTs - Begins */
		/*TextBox txt1 = new TextBox();
		txt1.setText(rcaCount.get(0).getProjectDetails().getProjectName() + " Dashboard");*/
		TextBox txt1 = new TextBox();
		//if (null != rcaCount.get(0).getProjectDetails() && null != rcaCount.get(0).getProjectDetails().getActionTeam()){
		StringBuilder actionTeam = new StringBuilder();
		actionTeam.append(rcaCount.get(0).getProjectDetails().getProjectName())
		.append(null != rcaCount.get(0).getProjectDetails().getActionTeam()? " ("+rcaCount.get(0).getProjectDetails().getActionTeam().replaceAll("\\s", "").replace('+', '/')+')':"")
		.append(" Dashboard");
		//}
		txt1.setText(actionTeam.toString());
		
		txt1.setAnchor(new java.awt.Rectangle(0, 0, (ppt.getPageSize().width)+30, (ppt.getPageSize().height/2)/10));
		RichTextRun rt1 = txt1.getTextRun().getRichTextRuns()[0];
		rt1.setFontSize(25);
		rt1.setFontName("Franklin Gothic Medium");
		rt1.setAlignment(TextBox.AlignLeft);
		slide.addShape(txt1);
		
		TextBox txt2 = new TextBox();
		
		TextRun tr = txt2.createTextRun();
		if(rcaCount.size() > 0){
			
			RcaCount rcaList = null;
			Iterator<RcaCount> itOM = rcaCount.iterator();
			
			while(itOM.hasNext())
			{
				rcaList = itOM.next();
				if(rcaList.getWeek().equals(rca.getWeek()))
				{
					txt2.setText("Overview: " + "\n");

					if(rcaList.getOverviewMessage() != null && !rcaList.getOverviewMessage().isEmpty())
						tr.appendText(rcaList.getOverviewMessage() + "\n");
					else
						tr.appendText("N/A" + "\n");
				}
			}
			
			Iterator<RcaCount> itRI = rcaCount.iterator();
			
			while(itRI.hasNext())
			{
				rcaList = itRI.next();
				if(rcaList.getWeek().equals(rca.getWeek()))
				{
					tr.appendText("\n");
					tr.appendText("Risks/Issues:");
					tr.appendText("\n");

					if(rcaList.getRisksIssues() != null && !rcaList.getRisksIssues().isEmpty())
						tr.appendText(rcaList.getRisksIssues() + "\n");
					else
						tr.appendText("N/A" + "\n");
				}
			}
			
		}
		txt2.setAnchor(new java.awt.Rectangle(ppt.getPageSize().width/2+50, 20, ppt.getPageSize().width/2-50, ppt.getPageSize().height/2-30));
		RichTextRun rt2Heading = tr.getRichTextRuns()[0];
		rt2Heading.setFontSize(18);
		rt2Heading.setFontName("Franklin Gothic Medium");
		for(int i=1; i<tr.getRichTextRuns().length;i++){
			RichTextRun rt2 = tr.getRichTextRuns()[i];
			if(rt2.getText().equalsIgnoreCase("Risks/Issues:")){
				rt2.setFontSize(18);
				rt2.setFontName("Franklin Gothic Medium");
			}else{
				rt2.setFontSize(14);
				rt2.setFontName("Franklin Gothic Body");
				rt2.setAlignment(TextBox.AlignLeft);
			}
		}
		slide.addShape(txt2);
		/* Adding Overview/Risk Issues section in Individual Project PPTs - Ends */
		
		//Adding 4 RCA slide
		Picture pict2 = new Picture(idx1);
		pict2.setAnchor(new java.awt.Rectangle(0, pageheight+160, pageWidth-5, pageheight-10));
		slide.addShape(pict2);
		Picture pict3 = new Picture(idx2);
		pict3.setAnchor(new java.awt.Rectangle(180, pageheight+160, pageWidth-5, pageheight-10));
		slide.addShape(pict3);
		Picture pict4 = new Picture(idx3);
		pict4.setAnchor(new java.awt.Rectangle(360, pageheight+160, pageWidth-5, pageheight-10));
		slide.addShape(pict4);
		Picture pict5 = new Picture(idx4);
		pict5.setAnchor(new java.awt.Rectangle(540, pageheight+160, pageWidth-20, pageheight-20));
		slide.addShape(pict5);
		/*Adding for open/close defects*/
		
		if(idx5!=0){
			Picture pict6 = new Picture(idx5);
			//pict6.setAnchor(new java.awt.Rectangle(2, 40, (pageheight*4)/2, pageheight)); //Size change of graph
			pict6.setAnchor(new java.awt.Rectangle(2, 40, pageWidth+150, pageheight-40));
			slide.addShape(pict6);
		}
		idxOpenClose = ppt.addPicture(generateGraph.createWeeklyGraphCloseVsOpen( logDefOpen.reportedWeeklyTrendLoggedVsOpen(rcaCount, allWeeks), "Logged Vs Resolved Defects", "", "", 
				PlotOrientation.VERTICAL, true, 750, 1000,RCAConstants.BAR, true, true) , XSLFPictureData.PICTURE_TYPE_PNG);
		Picture pictOpenClose = new Picture(idxOpenClose);
		//pictOpenClose.setAnchor(new java.awt.Rectangle(2, pageheight+40, pageWidth+120, pageheight-60));   // Size Change of graph
		pictOpenClose.setAnchor(new java.awt.Rectangle(2, pageheight+15 , pageWidth+150, pageheight-40));
		slide.addShape(pictOpenClose);
		
		// reading an image
		InputStream stream = getClass().getResourceAsStream("ColorCategory.jpg");
		// converting it into a byte array
		byte[] picture = IOUtils.toByteArray(stream);
		// adding the image to the presentation
		int imgx = ppt.addPicture(picture, XSLFPictureData.PICTURE_TYPE_JPEG);
		Picture pictImage = new Picture(imgx);
		pictImage.setAnchor(new java.awt.Rectangle(0, pageheight+335,
				pageWidth * 4, pageheight / 7));
		slide.addShape(pictImage);
		Log.debug("Exit createGraphIndividualPpt");
	}
	
	private void fillProjectNameWithCcbCount(Map<String, Integer> projectNameWithCCb , TextRun tr)
	{
		if(projectNameWithCCb!=null && projectNameWithCCb.size()!=0)
		{
			for(Map.Entry<String, Integer> entry : projectNameWithCCb.entrySet())
			{
				tr.appendText("       "+entry.getKey() + " (" +entry.getValue()+"):\n");
			}
		}
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

	public ProjectDetailsManager getProjectDetailsManager() {
		return projectDetailsManager;
	}

	public void setProjectDetailsManager(ProjectDetailsManager projectDetailsManager) {
		this.projectDetailsManager = projectDetailsManager;
	}
	
	public RCA getRca() {
		return rca;
	}

	public void setRca(RCA rca) {
		this.rca = rca;
	}
	public SprintReportManager getSprintReportManager() {
		return sprintReportManager;
	}

	public void setSprintReportManager(SprintReportManager sprintReportManager) {
		this.sprintReportManager = sprintReportManager;
	}

	public Map<String, Integer> getProjectNameWithCcbCount() {
		return projectNameWithCcbCount;
	}

	public void setProjectNameWithCcbCount(
			Map<String, Integer> projectNameWithCcbCount) {
		this.projectNameWithCcbCount = projectNameWithCcbCount;
	}
	
	
}
