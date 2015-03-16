package com.rca.controller;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hslf.model.Picture;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextBox;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import com.opensymphony.xwork2.ActionSupport;
import com.rca.common.RCAConstants;
import com.rca.common.ReportUtility;
import com.rca.entity.RcaCount;
import com.rca.service.GenerateGraph;
import com.rca.service.RcaManager;
import com.rca.service.RcaManagerImpl;

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
	
	public String execute() throws Exception {
		
		List<RcaCount> rcaWeeks = rcaManager.findRCAByWeekPeriod("9/29/2014-10/5/2014");
		
		createGraphPpt(rcaWeeks);
	    fileInputStream = new FileInputStream(new File("D:\\project.ppt"));
	    return SUCCESS;
	}
	
	public String generateReport(){
		return SUCCESS;
	}
	
	public static void createGraphPpt(List<RcaCount> rcaWeeks) throws IOException{
	  
	  SlideShow ppt = new SlideShow();
    Slide slide = ppt.createSlide();
    int pageWidth = ppt.getPageSize().width/2;
    int pageheight = ppt.getPageSize().height/2;
    GenerateGraph generateGraph = new GenerateGraph();
    // add a new picture to this slideshow and insert it in a  new slide
    ReportUtility rU = new ReportUtility();
    int idx = ppt.addPicture(generateGraph.createGraph( rU.rcaCountForLastWeekForAllProjects(rcaWeeks), "graphHeader", "", "", PlotOrientation.VERTICAL, false, 450, 450,RCAConstants.BAR) , XSLFPictureData.PICTURE_TYPE_PNG);
    //int lCx = ppt.addPicture(createLineChart() , XSLFPictureData.PICTURE_TYPE_PNG);
    //int bWCx = ppt.addPicture(createWeeklyBarChart() , XSLFPictureData.PICTURE_TYPE_PNG);
    Picture pict = new Picture(idx);
    //set image position in the slide
    pict.setAnchor(new java.awt.Rectangle(20, 20, pageWidth-50, pageheight-50));
    slide.addShape(pict);

    
    TextBox txt1 = new TextBox();
    txt1.setText("Hello");
    txt1.setAnchor(new java.awt.Rectangle(pageWidth+20, 20, pageWidth-50, pageheight-50));
    slide.addShape(txt1);
    
    /*Picture pict1 = new Picture(bWCx);
          //set image position in the slide
          pict1.setAnchor(new java.awt.Rectangle(20, pageheight+20, pageWidth-50, pageheight-50));
          slide.addShape(pict1);
          
          Picture pict2 = new Picture(lCx);
          //set image position in the slide
          pict2.setAnchor(new java.awt.Rectangle(pageWidth+20,  pageheight+20, pageWidth-50, pageheight-50));
          slide.addShape(pict2);
      */          
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
	
	
}
