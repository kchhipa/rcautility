package com.rca.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hslf.model.Picture;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextBox;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;
import org.jfree.chart.plot.PlotOrientation;

import com.opensymphony.xwork2.ActionSupport;
import com.rca.common.RCAConstants;
import com.rca.common.ReportUtility;
import com.rca.entity.RCA;
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
	private RCA rca;
	
	//Employee manager injected by spring context
			private RcaManager rcaManager;
	
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
	  
	  SlideShow ppt = new SlideShow();
    Slide slide = ppt.createSlide();
    int pageWidth = ppt.getPageSize().width/2;
    int pageheight = ppt.getPageSize().height/2;
    GenerateGraph generateGraph = new GenerateGraph();
    // add a new picture to this slideshow and insert it in a  new slide

    List<RcaCount> allWeeksrcaCounts = rcaManager.findRCAReportForMultipleWeek();
    
    ReportUtility rU = new ReportUtility();
    List<String> allWeeks = rU.findWeeks();
    int idx = ppt.addPicture(generateGraph.createGraph( rU.reportedQARCAForAllProjects(rcaCounts), "", "", "", 
    		PlotOrientation.VERTICAL, false, 450, 450,RCAConstants.BAR) , XSLFPictureData.PICTURE_TYPE_PNG);
    int lCx = ppt.addPicture(generateGraph.createLineGraph(rU.reportedQAAllWeeksCCBGraphForAllProject(allWeeksrcaCounts, allWeeks), "Client Code Trend", "", "", 
    		PlotOrientation.VERTICAL, true, 650, 450,RCAConstants.LINE) , XSLFPictureData.PICTURE_TYPE_PNG);
    int bWCx = ppt.addPicture(generateGraph.createWeeklyGraph(rU.reportedQAAllWeeksGraphForAllProject(allWeeksrcaCounts, allWeeks), "Weekly Trend", "", "", 
      		PlotOrientation.VERTICAL, true, 650, 450,RCAConstants.BAR) , XSLFPictureData.PICTURE_TYPE_PNG);
    Picture pict = new Picture(idx);
    //set image position in the slide
    pict.setAnchor(new java.awt.Rectangle(20, 20, pageWidth-50, pageheight-50));
    slide.addShape(pict);

    
    TextBox txt1 = new TextBox();
    txt1.setText("Reported Prod");
    txt1.setAnchor(new java.awt.Rectangle(pageWidth+20, 20, pageWidth-50, pageheight-50));
    slide.addShape(txt1);
    
         Picture pict1 = new Picture(bWCx);
          //set image position in the slide
          pict1.setAnchor(new java.awt.Rectangle(20, pageheight+20, pageWidth-50, pageheight-50));
          slide.addShape(pict1);
          
          Picture ccbLineGraph = new Picture(lCx);
          //set image position in the slide
          ccbLineGraph.setAnchor(new java.awt.Rectangle(pageWidth+20,  pageheight+20, pageWidth-50, pageheight-50));
          slide.addShape(ccbLineGraph);
          
          RcaCount rcaCount = null;
    		for (int x = 0; x < rcaCounts.size(); x++) {
    			String projName = rcaCounts.get(x).getProjectDetails().getProjectName();
    			if(projName.equals("MI-SG")){
    				rcaCount = rcaCounts.get(x);
    				break;
    			}
    		}
            createGraphIndividualPpt(rcaCount, ppt);

   /* FileOutputStream out = new FileOutputStream(
        "D:\\project.ppt");
    ppt.write(out);
    out.close();*/
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
	
	public  void createGraphIndividualPpt(RcaCount rcaCount , SlideShow ppt) throws IOException{
		  
	    Slide slide = ppt.createSlide();
	    int pageWidth = ppt.getPageSize().width/4;
	    int pageheight = ppt.getPageSize().height/3;
	    
	    int totalPageHeight = ppt.getPageSize().height;
	    GenerateGraph generateGraph = new GenerateGraph();
	    // add a new picture to this slideshow and insert it in a  new slide
	   
	    //List<RcaCount> allWeeksrcaCounts = rcaManager.findRCAReportForMultipleWeek();
	    ReportUtility rU = new ReportUtility();
	    List<String> allWeeks = rU.findWeeks();
	    
	    int idx1 = ppt.addPicture(generateGraph.createIndividualWeeklyGraph( rU.reportedQAAllWeeksGraphForIndividual(getAllWeekRCACountsListforIndividual(allWeeks), allWeeks), "Cumulative Open", "", "", 
	    		PlotOrientation.VERTICAL, true, 500, 500,RCAConstants.BAR) , XSLFPictureData.PICTURE_TYPE_PNG);
	    int idx2 = ppt.addPicture(generateGraph.createIndividualWeeklyGraph( rU.reportedQAAllWeeksGraphForIndividual(getAllWeekRCACountsListforIndividual(allWeeks), allWeeks), "Weekly PROD", "", "", 
	    		PlotOrientation.VERTICAL, true, 500, 500,RCAConstants.BAR) , XSLFPictureData.PICTURE_TYPE_PNG);
	    int idx3 = ppt.addPicture(generateGraph.createIndividualWeeklyGraph( rU.reportedQAAllWeeksGraphForIndividual(getAllWeekRCACountsListforIndividual(allWeeks), allWeeks), "Weekly UAT", "", "", 
	    		PlotOrientation.VERTICAL, true, 500, 500,RCAConstants.BAR) , XSLFPictureData.PICTURE_TYPE_PNG);
	    int idx4 = ppt.addPicture(generateGraph.createIndividualWeeklyGraph( rU.reportedQAAllWeeksGraphForIndividual(getAllWeekRCACountsListforIndividual(allWeeks), allWeeks), "Weekly QA", "", "", 
	    		PlotOrientation.VERTICAL, true, 500, 500,RCAConstants.BAR) , XSLFPictureData.PICTURE_TYPE_PNG);
	    
		TextBox txt1 = new TextBox();
		txt1.setText("MI-SG Dashboard");
		txt1.setAnchor(new java.awt.Rectangle(pageWidth+20, 20, pageWidth-10, pageheight-50));
		slide.addShape(txt1);
		
	    Picture pict2 = new Picture(idx1);
	    pict2.setAnchor(new java.awt.Rectangle(0, totalPageHeight-160, pageWidth-50, pageheight-50));
	    slide.addShape(pict2);
	    
	    Picture pict3 = new Picture(idx2);
	    pict3.setAnchor(new java.awt.Rectangle(180, totalPageHeight-160, pageWidth-50, pageheight-50));
	    slide.addShape(pict3);
	    
	    Picture pict4 = new Picture(idx3);
	    pict4.setAnchor(new java.awt.Rectangle(360, totalPageHeight-160, pageWidth-50, pageheight-50));
	    slide.addShape(pict4);
	    
	    Picture pict5 = new Picture(idx4);
	    pict5.setAnchor(new java.awt.Rectangle(540, totalPageHeight-160, pageWidth-50, pageheight-50));
	    slide.addShape(pict5);
	    
	    FileOutputStream out = new FileOutputStream(
	        "D:\\project.ppt");
	    ppt.write(out);
	    out.close();
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
