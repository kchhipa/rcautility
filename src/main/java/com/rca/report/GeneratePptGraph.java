package com.rca.report;

import java.awt.Color;
import java.awt.GradientPaint;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hslf.model.Picture;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextBox;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
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

public class GeneratePptGraph {
	public static void main(String arg[]){
		try {
			SlideShow ppt = new SlideShow();
			Slide slide = ppt.createSlide();
			int pageWidth = ppt.getPageSize().width/2;
			int pageheight = ppt.getPageSize().height/2;
			
			// add a new picture to this slideshow and insert it in a  new slide
            int idx = ppt.addPicture(createBarChart() , XSLFPictureData.PICTURE_TYPE_PNG);
            int lCx = ppt.addPicture(createLineChart() , XSLFPictureData.PICTURE_TYPE_PNG);
            int bWCx = ppt.addPicture(createWeeklyBarChart() , XSLFPictureData.PICTURE_TYPE_PNG);
            Picture pict = new Picture(idx);
            //set image position in the slide
            pict.setAnchor(new java.awt.Rectangle(20, 20, pageWidth-50, pageheight-50));
            slide.addShape(pict);

			
			TextBox txt1 = new TextBox();
			txt1.setText("This is tex area");
			txt1.setAnchor(new java.awt.Rectangle(pageWidth+20, 20, pageWidth-50, pageheight-50));
			slide.addShape(txt1);
			
			Picture pict1 = new Picture(bWCx);
            //set image position in the slide
            pict1.setAnchor(new java.awt.Rectangle(20, pageheight+20, pageWidth-50, pageheight-50));
            slide.addShape(pict1);
            
            Picture pict2 = new Picture(lCx);
            //set image position in the slide
            pict2.setAnchor(new java.awt.Rectangle(pageWidth+20,  pageheight+20, pageWidth-50, pageheight-50));
            slide.addShape(pict2);
			 						
			FileOutputStream out = new FileOutputStream(
					"D:\\project.ppt");
			ppt.write(out);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates Bugs Count vS Project Bar Chart.
	 */
	private static File createBarChart() {
		File barChart = null;
		try {
			// chart preparation
			DefaultCategoryDataset bar_chart_dataset = new DefaultCategoryDataset();
			bar_chart_dataset.addValue(2, "Bugs", "MI-SG");
			bar_chart_dataset.addValue(4, "Bugs", "BCBSAL");
			bar_chart_dataset.addValue(2, "Bugs", "BCBSNE");
			bar_chart_dataset.addValue(3, "Bugs", "Centene");
			bar_chart_dataset.addValue(8, "Bugs", "Topaz");

			/* Step -2:Define the JFreeChart object to create line chart */
			/*JFreeChart lineChartObject = ChartFactory.createLineChart(
					"Schools Vs Years", "Year", "Schools Count",
					line_chart_dataset, PlotOrientation.VERTICAL, true, true,
					false);*/
			
			JFreeChart barChartObject = ChartFactory.createBarChart("Bug Count Vs Project", "Project", "Bug Counts",
					bar_chart_dataset, PlotOrientation.VERTICAL, true, true,
					false);
			
			// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

	        // set the background color for the chart...
	        barChartObject.setBackgroundPaint(Color.white);

	        // get a reference to the plot for further customization...
	        CategoryPlot plot = barChartObject.getCategoryPlot();
	        plot.setBackgroundPaint(Color.lightGray);
	        plot.setDomainGridlinePaint(Color.white);
	        plot.setDomainGridlinesVisible(true);
	        plot.setRangeGridlinePaint(Color.white);
	        
	        // set the range axis to display integers only...
	        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

	        // disable bar outlines...
	        BarRenderer renderer = (BarRenderer) plot.getRenderer();
	        renderer.setDrawBarOutline(false);
	        
	        Color color = null;

	        renderer.setSeriesPaint(0, color.ORANGE);

	        renderer.setIncludeBaseInRange(true);
	        renderer.setAutoPopulateSeriesPaint(true);


	        /*CategoryAxis domainAxis = plot.getDomainAxis();
	        domainAxis.setCategoryLabelPositions(
	            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
	        );*/

			/* Step -3 : Write line chart to a file */
			int width = 450; /* Width of the image */
			int height = 450; /* Height of the image */
			barChart = new File("D:\\bar_Chart_example.png");
			ChartUtilities.saveChartAsPNG(barChart, barChartObject, width,
					height);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return barChart;
	}
	
	/**
	 * Creates Bugs Count vS Project Bar Chart.
	 */
	private static File createWeeklyBarChart() {
		File barChart = null;
		try {
			// chart preparation
			DefaultCategoryDataset bar_chart_dataset = new DefaultCategoryDataset();
			bar_chart_dataset.addValue(27, "Bugs", "12/15/2014-12/21/2014");
			bar_chart_dataset.addValue(44, "Bugs", "12/22/2014-12/28/2014");
			bar_chart_dataset.addValue(18, "Bugs", "12/29/2014-1/4/2015");
			bar_chart_dataset.addValue(17, "Bugs", "1/5/2015-1/11/2015");
			bar_chart_dataset.addValue(14, "Bugs", "1/12/2015-1/18/2015");
			bar_chart_dataset.addValue(8, "Bugs", "1/19/2015-1/25/2015");
			bar_chart_dataset.addValue(8, "Bugs", "1/26/2015-2/1/2015");
			bar_chart_dataset.addValue(12, "Bugs", "2/2/2015-2/8/2015");
			bar_chart_dataset.addValue(18, "Bugs", "2/9/2015-2/15/2015");
			bar_chart_dataset.addValue(12, "Bugs", "2/16/2015-2/22/2015");
			bar_chart_dataset.addValue(6, "Bugs", "2/23/2015-3/1/2015");
			bar_chart_dataset.addValue(8, "Bugs", "2/23/2015-3/1/2015");

			/* Step -2:Define the JFreeChart object to create line chart */
			/*JFreeChart lineChartObject = ChartFactory.createLineChart(
					"Schools Vs Years", "Year", "Schools Count",
					line_chart_dataset, PlotOrientation.VERTICAL, true, true,
					false);*/
			
			JFreeChart barChartObject = ChartFactory.createBarChart("Weekly Trend", "Week", "Bug Counts",
					bar_chart_dataset, PlotOrientation.VERTICAL, true, true,
					false);
			
			// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

	        // set the background color for the chart...
	        barChartObject.setBackgroundPaint(Color.white);

	        // get a reference to the plot for further customization...
	        CategoryPlot plot = barChartObject.getCategoryPlot();
	        plot.setBackgroundPaint(Color.lightGray);
	        plot.setDomainGridlinePaint(Color.white);
	        plot.setDomainGridlinesVisible(true);
	        plot.setRangeGridlinePaint(Color.white);
	        
	        // set the range axis to display integers only...
	        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

	        // disable bar outlines...
	        BarRenderer renderer = (BarRenderer) plot.getRenderer();
	        renderer.setDrawBarOutline(false);
	        
	        Color color = null;

	        renderer.setSeriesPaint(0, color.BLUE.brighter());

	        renderer.setIncludeBaseInRange(true);
	        renderer.setAutoPopulateSeriesPaint(true);


	        CategoryAxis domainAxis = plot.getDomainAxis();
	        domainAxis.setCategoryLabelPositions(
	            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
	        );

			/* Step -3 : Write line chart to a file */
			int width = 650; /* Width of the image */
			int height = 750; /* Height of the image */
			barChart = new File("D:\\bar_Chart_example.png");
			ChartUtilities.saveChartAsPNG(barChart, barChartObject, width,
					height);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return barChart;
	}
	
	/**
	 * Creates Bugs Count vS Project Line Chart.
	 */
	private static File createLineChart() {
		File lineChart = null;
		try {
			// chart preparation
			DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
			line_chart_dataset.addValue(1, "Bugs", "12/15/2014-12/21/2014");
			line_chart_dataset.addValue(2, "Bugs", "12/22/2014-12/28/2014");
			line_chart_dataset.addValue(0, "Bugs", "12/29/2014-1/4/2015");
			line_chart_dataset.addValue(0, "Bugs", "1/5/2015-1/11/2015");
			line_chart_dataset.addValue(1, "Bugs", "1/12/2015-1/18/2015");
			line_chart_dataset.addValue(0, "Bugs", "1/19/2015-1/25/2015");
			line_chart_dataset.addValue(0, "Bugs", "1/26/2015-2/1/2015");
			line_chart_dataset.addValue(0, "Bugs", "2/2/2015-2/8/2015");
			line_chart_dataset.addValue(2, "Bugs", "2/9/2015-2/15/2015");
			line_chart_dataset.addValue(0, "Bugs", "2/16/2015-2/22/2015");
			line_chart_dataset.addValue(0, "Bugs", "2/23/2015-3/1/2015");
			line_chart_dataset.addValue(1, "Bugs", "2/23/2015-3/1/2015");


			/* Step -2:Define the JFreeChart object to create line chart */
			JFreeChart lineChartObject = ChartFactory.createLineChart(
					"Client Code Trend", "Week", "Bug Counts",
					line_chart_dataset, PlotOrientation.VERTICAL, true, true,
					false);
			
			// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

	        // set the background color for the chart...
			lineChartObject.setBackgroundPaint(Color.white);

	        // get a reference to the plot for further customization...
	        CategoryPlot plot = lineChartObject.getCategoryPlot();
	        plot.setBackgroundPaint(Color.lightGray);
	        plot.setDomainGridlinePaint(Color.white);
	        plot.setDomainGridlinesVisible(true);
	        plot.setRangeGridlinePaint(Color.white);
	        
	        // set the range axis to display integers only...
	        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	        
	        CategoryAxis domainAxis = plot.getDomainAxis();
	        domainAxis.setCategoryLabelPositions(
	            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
	        );

			/* Step -3 : Write line chart to a file */
			int width = 650; /* Width of the image */
			int height = 650; /* Height of the image */
			lineChart = new File("D:\\line_Chart_example.png");
			ChartUtilities.saveChartAsPNG(lineChart, lineChartObject, width,
					height);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lineChart;
	}
	
}

