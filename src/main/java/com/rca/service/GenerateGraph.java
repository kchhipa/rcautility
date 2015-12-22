package com.rca.service;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.Stroke;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.GroupedStackedBarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.StandardGradientPaintTransformer;
import org.jfree.ui.TextAnchor;

import com.rca.service.graph.creation.DifferentTypeGraphAbstractCreation;
import com.rca.service.graph.creation.DifferentTypeGraphCreationFactory;


public class GenerateGraph
{

	public File createGraph(Map<String, Map<String,Integer>> data, String graphHeader, String xAxis, String yAxis, PlotOrientation plotOrientation, boolean rotatedLabel, int graphWidth, int graphHeight, String graphType)
	{
		return createGraph(data, graphHeader, xAxis, yAxis, plotOrientation, rotatedLabel, graphWidth, graphHeight, graphType, true, false);
	}
	
	public File createSprintGraph(Map<String, Map<String,Integer>> data, String graphHeader, String xAxis, String yAxis, PlotOrientation plotOrientation, boolean rotatedLabel, int graphWidth, int graphHeight, String graphType)
	{
		return createSprintGraph(data, graphHeader, xAxis, yAxis, plotOrientation, rotatedLabel, graphWidth, graphHeight, graphType, true, false);
	}
 
  public File createGraph(Map<String, Map<String,Integer>> data, String graphHeader, String xAxis, String yAxis, PlotOrientation plotOrientation, boolean rotatedLabel, int graphWidth, int graphHeight, String graphType, boolean showLegend, boolean projectFontSize)
  {
	
    DefaultCategoryDataset chartDataSet = dataSetObjectCreation(data);
	Set<String> keyset=data.keySet();
    DifferentTypeGraphAbstractCreation graphCreationObject = DifferentTypeGraphCreationFactory.createGraphCreationObject(graphType, graphHeader, xAxis, yAxis, plotOrientation, chartDataSet, true, true);
    JFreeChart jFreeChart = graphCreationObject.createGraph();
    
    if(graphHeader.equalsIgnoreCase("Cumulative Open")|| graphHeader.equalsIgnoreCase("Weekly PROD")||graphHeader.equalsIgnoreCase("Weekly UAT")|| graphHeader.equalsIgnoreCase("Weekly QA"))
        jFreeChart.setTitle(new org.jfree.chart.title.TextTitle(graphHeader,new java.awt.Font("Calibri", java.awt.Font.BOLD, 48)));
    
    // set the background color for the chart...
    jFreeChart.setBackgroundPaint(Color.WHITE);
    
    LegendTitle legend = (LegendTitle) jFreeChart.getSubtitle(0);
    legend.setItemLabelPadding(new RectangleInsets(2, 2, 2, 30));
    //legend.setPosition(RectangleEdge.RIGHT);
    legend.setLegendItemGraphicLocation(RectangleAnchor.CENTER);
    legend.setFrame(BlockBorder.NONE);
    legend.setVisible(showLegend);
    Font legendFont = new Font("Franklin Gothic Book Medium", Font.BOLD, 14);
    legend.setItemFont(legendFont);
    legend.setItemPaint(Color.BLACK);
    
    CategoryPlot plot = createPlot(jFreeChart);
	CategoryAxis domainAxis = plot.getDomainAxis();
	ValueAxis rangeAxis = plot.getRangeAxis();	
	Stroke s = new BasicStroke(1.0f);
	Stroke s1 = new BasicStroke(1.0f);
	plot.setRangeGridlineStroke(s);
	plot.setRangeGridlinePaint(Color.BLACK);
	plot.setRangeZeroBaselineVisible(true);
	plot.setOutlineVisible(false);
	domainAxis.setAxisLineVisible(true);
	domainAxis.setAxisLineStroke(s1);
	domainAxis.setAxisLinePaint(Color.BLACK);
	domainAxis.setTickMarksVisible(false);
	rangeAxis.setAxisLineStroke(s1);
	rangeAxis.setAxisLinePaint(Color.BLACK);
	
	if(projectFontSize){
	  	Font font = new Font("Franklin Gothic Book Heavy", Font.BOLD, 35);
	  	domainAxis.setTickLabelFont(font);
	  	plot.getRangeAxis().setTickLabelFont(font);
	  	domainAxis.setTickLabelPaint(Color.BLACK);
		plot.getRangeAxis().setTickLabelPaint(Color.BLACK);
	  	domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
	}else{
		Font font = new Font("Franklin Gothic Book Heavy", Font.BOLD, 17);
		domainAxis.setTickLabelFont(font);
		plot.getRangeAxis().setTickLabelFont(font);
		domainAxis.setTickLabelPaint(Color.BLACK);
		plot.getRangeAxis().setTickLabelPaint(Color.BLACK);
		domainAxis.setLabelFont(font);
		domainAxis.setLabelPaint(Color.BLACK);
		plot.getRangeAxis().setLabelFont(font);
		plot.getRangeAxis().setLabelPaint(Color.BLACK);
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions
					.createUpRotationLabelPositions(Math.PI / 7.0));
	}
		
    
    plot.setRenderer(createRender(keyset.size()));
    
    return createGraphImage(jFreeChart, graphWidth, graphHeight);
  }
  
  public File createSprintGraph(Map<String, Map<String,Integer>> data, String graphHeader, String xAxis, String yAxis, PlotOrientation plotOrientation, boolean rotatedLabel, int graphWidth, int graphHeight, String graphType, boolean showLegend, boolean projectFontSize)
  {
		DefaultCategoryDataset chartDataSet = dataSetObjectCreation(data);
		Set<String> keyset = data.keySet();
		DifferentTypeGraphAbstractCreation graphCreationObject = DifferentTypeGraphCreationFactory
				.createGraphCreationObject(graphType, graphHeader, xAxis, yAxis, plotOrientation, chartDataSet, true,
						true);
		JFreeChart jFreeChart = graphCreationObject.createGraph();

		// set the background color for the chart...
		jFreeChart.setBackgroundPaint(Color.white);
		CategoryPlot plot = createPlot(jFreeChart);
		CategoryAxis domainAxis = plot.getDomainAxis();
		ValueAxis rangeAxis = plot.getRangeAxis();
		Stroke s = new BasicStroke(1.0f);
		Stroke s1 = new BasicStroke(1.0f);
		plot.setRangeGridlinePaint(Color.BLACK);
		plot.setRangeGridlineStroke(s);
		plot.setRangeZeroBaselineVisible(true);
		plot.setOutlineVisible(false);
		domainAxis.setAxisLineVisible(true);
		domainAxis.setAxisLineStroke(s1);
		domainAxis.setAxisLinePaint(Color.BLACK);
		domainAxis.setTickMarksVisible(false);
		rangeAxis.setAxisLineStroke(s1);
		rangeAxis.setAxisLinePaint(Color.BLACK);

		Font font = new Font("Franklin Gothic Book Heavy", Font.BOLD, 17);
		domainAxis.setTickLabelFont(font);
		plot.getRangeAxis().setTickLabelFont(font);
		domainAxis.setTickLabelPaint(Color.BLACK);
		plot.getRangeAxis().setTickLabelPaint(Color.BLACK);
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);

		CategoryItemRenderer renderer = new CustomRenderer();

		// Enabling the tool tip generator
		renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());

		// This Generator enables the labels to be placed on top of bars
		CategoryItemLabelGenerator itemLabelGenerator = new StandardCategoryItemLabelGenerator();
		renderer.setBaseItemLabelGenerator(itemLabelGenerator);
		renderer.setBaseItemLabelFont(font);
		renderer.setPositiveItemLabelPosition(
				new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
		renderer.setBaseItemLabelsVisible(true);

		((BarRenderer) renderer).setBarPainter(new StandardBarPainter());
		((BarRenderer) renderer).setShadowVisible(false);
		((BarRenderer) renderer).setItemMargin(0.50);
		((BarRenderer) renderer).setMaximumBarWidth(0.03);

		plot.setRenderer(renderer);

		plot.setRenderer(createSprintRender());
		return createGraphImage(jFreeChart, graphWidth, graphHeight);
     }
  
 
  
  @SuppressWarnings("deprecation")
  public File createWeeklyGraph(List data, String graphHeader, String xAxis, String yAxis, PlotOrientation plotOrientation, boolean rotatedLabel, int graphWidth, int graphHeight, String graphType)
  {
	  DefaultCategoryDataset chartDataSet = dataSetObjectCreation(data);
	  DifferentTypeGraphAbstractCreation graphCreationObject = DifferentTypeGraphCreationFactory.createGraphCreationObject(graphType, graphHeader, xAxis, yAxis, plotOrientation, chartDataSet, false, true);
	  JFreeChart jFreeChart = graphCreationObject.createGraph();

	  // set the background color for the chart...
	  jFreeChart.setBackgroundPaint(Color.white);
	  CategoryPlot plot = createPlot(jFreeChart);
	  CategoryAxis domainAxis = plot.getDomainAxis();
	  ValueAxis rangeAxis = plot.getRangeAxis();	
	  Stroke s = new BasicStroke(1.0f);
	  Stroke s1 = new BasicStroke(1.0f);
	  plot.setRangeGridlinePaint(Color.BLACK);
	  plot.setRangeGridlineStroke(s);
	  plot.setRangeZeroBaselineVisible(true);
	  plot.setOutlineVisible(false);
	  domainAxis.setAxisLineVisible(true);
	  domainAxis.setAxisLineStroke(s1);
	  domainAxis.setAxisLinePaint(Color.BLACK);
	  domainAxis.setTickMarksVisible(false);
	  rangeAxis.setAxisLineStroke(s1);
	  rangeAxis.setAxisLinePaint(Color.BLACK);

	  Font font = new Font("Franklin Gothic Book Heavy", Font.BOLD, 17);

	  domainAxis.setTickLabelFont(font);
	  plot.getRangeAxis().setTickLabelFont(font);
	  domainAxis.setTickLabelPaint(Color.BLACK);
	  plot.getRangeAxis().setTickLabelPaint(Color.BLACK);
	  domainAxis.setCategoryLabelPositions(CategoryLabelPositions
			  .createUpRotationLabelPositions(Math.PI / 7.0));

	  CategoryItemRenderer renderer = new CustomRenderer();
	  
	  /* Enabling the tool tip generator */
	  renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());

	  /* This Generator enables the labels to be placed on top of bars */
	  CategoryItemLabelGenerator itemLabelGenerator = new StandardCategoryItemLabelGenerator();
	  renderer.setBaseItemLabelGenerator(itemLabelGenerator);
	  renderer.setBaseItemLabelFont(font);
	  renderer.setPositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,TextAnchor.BASELINE_CENTER));
	  renderer.setBaseItemLabelsVisible(true);

	  ((BarRenderer) renderer).setBarPainter(new StandardBarPainter());
	  ((BarRenderer) renderer).setShadowVisible(false);
	  ((BarRenderer) renderer).setItemMargin(0.50);
	  ((BarRenderer) renderer).setMaximumBarWidth(0.03);

	  plot.setRenderer(renderer);
	  	  
	  //plot.setRenderer(createWeeklyGraphRender(plot));   +
	  return createGraphImage(jFreeChart, graphWidth, graphHeight);
	}
  
  
  public File createIndividualWeeklyGraph(Map<String, Map<String,Integer>> data, String graphHeader, String xAxis, String yAxis, PlotOrientation plotOrientation, boolean rotatedLabel, int graphWidth, int graphHeight, String graphType)
  {
	    DefaultCategoryDataset chartDataSet = dataSetObjectCreation(data);
		Set<String> keyset=data.keySet();
	    DifferentTypeGraphAbstractCreation graphCreationObject = DifferentTypeGraphCreationFactory.createGraphCreationObject(graphType, graphHeader, xAxis, yAxis, plotOrientation, chartDataSet, true, true);
	    JFreeChart jFreeChart = graphCreationObject.createGraph();
	 // set the background color for the chart...
	    jFreeChart.setBackgroundPaint(Color.white);
	    LegendTitle legend = (LegendTitle) jFreeChart.getSubtitle(0);
	    legend.setItemLabelPadding(new RectangleInsets(2, 2, 2, 30));
	    //legend.setPosition(RectangleEdge.RIGHT);
	    legend.setLegendItemGraphicLocation(RectangleAnchor.CENTER);
	    legend.setFrame(BlockBorder.NONE);
	    legend.setVisible(false);
	    Font legendFont = new Font("Franklin Gothic Book Heavy", Font.BOLD, 18);
	    legend.setItemFont(legendFont);
	    legend.setItemPaint(Color.BLACK);
	    CategoryPlot plot = createPlot(jFreeChart);
	    CategoryAxis domainAxis = plot.getDomainAxis();
		ValueAxis rangeAxis = plot.getRangeAxis();	
		Stroke s = new BasicStroke(1.0f);
		Stroke s1 = new BasicStroke(1.0f);
		plot.setRangeGridlinePaint(Color.BLACK);
		plot.setRangeGridlineStroke(s);
		plot.setRangeZeroBaselineVisible(true);
		plot.setOutlineVisible(false);
		domainAxis.setAxisLineVisible(true);
		domainAxis.setAxisLineStroke(s1);
		domainAxis.setAxisLinePaint(Color.BLACK);
		domainAxis.setTickMarksVisible(false);
		rangeAxis.setAxisLineStroke(s1);
		rangeAxis.setAxisLinePaint(Color.BLACK);
		Font font = new Font("Franklin Gothic Book Heavy", Font.BOLD, 17);
		domainAxis.setTickLabelFont(font);
		plot.getRangeAxis().setTickLabelFont(font);
		domainAxis.setTickLabelPaint(Color.BLACK);
		jFreeChart.getLegend().setItemPaint(Color.BLACK);
		plot.getRangeAxis().setTickLabelPaint(Color.BLACK);
	    if(rotatedLabel)
		  {
			  domainAxis.setCategoryLabelPositions(
					  CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 2.0));
		  }
	    plot.setRenderer(createRender(keyset.size()));
	    return createGraphImage(jFreeChart, graphWidth, graphHeight);
  }

  
  @SuppressWarnings("deprecation")
  public File createWeeklyBarGraph(Map<String, Map<String,Integer>> data, String graphHeader, String xAxis, String yAxis, PlotOrientation plotOrientation, boolean rotatedLabel, int graphWidth, int graphHeight, String graphType, boolean showLegend, boolean projectFontSize)
  {
	  DefaultCategoryDataset chartDataSet = dataSetObjectCreation(data);
	    DifferentTypeGraphAbstractCreation graphCreationObject = DifferentTypeGraphCreationFactory.createGraphCreationObject(graphType, graphHeader, xAxis, yAxis, plotOrientation, chartDataSet, true, true);
	    JFreeChart jFreeChart = graphCreationObject.createGraph();
	    
	    jFreeChart.setTitle(new org.jfree.chart.title.TextTitle(graphHeader,new java.awt.Font("Franklin Gothic Book Heavy", java.awt.Font.BOLD, 17)));
	    
	    // set the background color for the chart...
	    jFreeChart.setBackgroundPaint(Color.white);
	    
	    LegendTitle legend = (LegendTitle) jFreeChart.getSubtitle(0);
	    legend.setItemLabelPadding(new RectangleInsets(2, 2, 12, 30));
	    Font legendFont = new Font("Franklin Gothic Book Heavy", Font.BOLD, 18);
	    legend.setItemFont(legendFont);
	    legend.setItemPaint(Color.BLACK);
	    jFreeChart.getLegend().setItemPaint(Color.BLACK);
	    //legend.setPosition(RectangleEdge.RIGHT);
	    legend.setLegendItemGraphicLocation(RectangleAnchor.CENTER);
	    legend.setFrame(BlockBorder.NONE);
	    legend.setVisible(showLegend);
	    
	    
	    
	    CategoryPlot plot = createPlot(jFreeChart);
	    CategoryAxis domainAxis = plot.getDomainAxis();
		ValueAxis rangeAxis = plot.getRangeAxis();	
		Stroke s = new BasicStroke(1.0f);
		Stroke s1 = new BasicStroke(1.0f);
		plot.setRangeGridlinePaint(Color.BLACK);
		plot.setRangeGridlineStroke(s);
		plot.setRangeZeroBaselineVisible(true);
		plot.setOutlineVisible(false);
		domainAxis.setAxisLineVisible(true);
		domainAxis.setAxisLineStroke(s1);
		domainAxis.setAxisLinePaint(Color.BLACK);
		domainAxis.setTickMarksVisible(false);
		rangeAxis.setAxisLineStroke(s1);
		rangeAxis.setAxisLinePaint(Color.BLACK);
		Font font = new Font("Franklin Gothic Book Heavy", Font.BOLD, 15);
	  	jFreeChart.getLegend().setItemFont(font);
	  	domainAxis.setTickLabelFont(font);
	  	plot.getRangeAxis().setTickLabelFont(font);
	  	domainAxis.setTickLabelInsets(new RectangleInsets(2, 2, 12, 30));
	  	plot.getRangeAxis().setTickLabelFont(font);
	  	domainAxis.setTickLabelPaint(Color.BLACK);
		plot.getRangeAxis().setTickLabelPaint(Color.BLACK);
	  	
	  	if(rotatedLabel)
		  {
			  domainAxis.setCategoryLabelPositions(
					  CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));
		  }
		
	    
	    plot.setRenderer(createBarRender());
	    
	    return createGraphImage(jFreeChart, graphWidth, graphHeight);
	}
  
  
	/**
	 * This API will create Line graph using the data parameter
	 * 
	 * @param data
	 * @param graphHeader
	 * @param xAxis
	 * @param yAxis
	 * @param plotOrientation
	 * @param rotatedLabel
	 * @param graphWidth
	 * @param graphHeight
	 * @param graphType
	 * @return Line graph
	 */
	public File createLineGraph(List data, String graphHeader, String xAxis,
			String yAxis, PlotOrientation plotOrientation,
			boolean rotatedLabel, int graphWidth, int graphHeight,
			String graphType) {
		DefaultCategoryDataset chartDataSet = dataSetObjectCreation(data,
				"Bugs");
		DifferentTypeGraphAbstractCreation graphCreationObject = DifferentTypeGraphCreationFactory
				.createGraphCreationObject(graphType, graphHeader, xAxis,
						yAxis, plotOrientation, chartDataSet, false, false);
		JFreeChart jFreeChart = graphCreationObject.createGraph();

		// set the background color for the chart...
		jFreeChart.setBackgroundPaint(Color.white);
		CategoryPlot plot = createPlot(jFreeChart);
		CategoryAxis domainAxis = plot.getDomainAxis();
		ValueAxis rangeAxis = plot.getRangeAxis();	
		Stroke s = new BasicStroke(1.0f);
		Stroke s1 = new BasicStroke(1.0f);
		plot.setRangeGridlineStroke(s);
		plot.setRangeGridlinePaint(Color.BLACK);
		plot.setRangeZeroBaselineVisible(true);
		plot.setOutlineVisible(false);
		domainAxis.setAxisLineVisible(true);
		domainAxis.setAxisLineStroke(s1);
		domainAxis.setAxisLinePaint(Color.BLACK);
		domainAxis.setTickMarksVisible(false);
		rangeAxis.setAxisLineStroke(s1);
		rangeAxis.setAxisLinePaint(Color.BLACK);
		
		Font font = new Font("Franklin Gothic Book Heavy", Font.BOLD, 17);
		domainAxis.setTickLabelPaint(Color.BLACK);
		plot.getRangeAxis().setTickLabelPaint(Color.BLACK);
	  	domainAxis.setTickLabelFont(font);
	  	plot.getRangeAxis().setTickLabelFont(font);

		/* If inclined label is required on domain axis */
		if (rotatedLabel) {
			domainAxis.setCategoryLabelPositions(CategoryLabelPositions
					.createUpRotationLabelPositions(Math.PI / 6.0));
		}

		// Line Renderer for customizing the line properties
		LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) plot
				.getRenderer();
		lineandshaperenderer.setBaseItemLabelPaint(Color.BLACK); // Label Color
		lineandshaperenderer.setPaint(Color.BLUE); // Line Color

		// Item label generator
		CategoryItemLabelGenerator itemLabelGenerator = new StandardCategoryItemLabelGenerator();
		lineandshaperenderer.setBaseItemLabelFont(font);
		lineandshaperenderer.setBaseItemLabelGenerator(itemLabelGenerator);
		lineandshaperenderer
				.setPositiveItemLabelPosition(new ItemLabelPosition(
						ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
		lineandshaperenderer.setBaseItemLabelsVisible(true);
		plot.setRenderer(lineandshaperenderer);
		return createGraphImage(jFreeChart, graphWidth, graphHeight);
	}

	/**
	 * This API will create the data set for line graph
	 * 
	 * @param data
	 * @param bug
	 * @return
	 */
	public DefaultCategoryDataset dataSetObjectCreation(
			List<Map<String, Integer>> data, String bug) {
		DefaultCategoryDataset chartDataSet = new DefaultCategoryDataset();
		for (Map<String, Integer> map : data) {
			Set<Entry<String, Integer>> dataSet = map.entrySet();
			for (Entry<String, Integer> entry : dataSet) {
				chartDataSet.addValue(entry.getValue(), bug, entry.getKey());
			}
		}
		return chartDataSet;
	}
  
  /**
   * Data key will contain week/ project name (Y-axis entry)
   * Data value <string, Integer> - String --BugType
   * Integer -- bug count
   * @param data
   */
  
  public DefaultCategoryDataset dataSetObjectCreation(Map<String, Map<String,Integer>> data)
  {
    DefaultCategoryDataset chartDataSet = new DefaultCategoryDataset();
    for (Map.Entry<String, Map<String,Integer>> dataSet : data.entrySet())
    {
      Map<String, Integer> dataDivision = dataSet.getValue();
      for (Map.Entry<String,Integer> dataSetDivision : dataDivision.entrySet())
      {
        chartDataSet.addValue(dataSetDivision.getValue(), dataSetDivision.getKey(),dataSet.getKey());
      }
    }
    return chartDataSet;
  }
  
  public DefaultCategoryDataset dataSetObjectCreation(List<Map<String, Integer>> data)
  {
	  DefaultCategoryDataset chartDataSet = new DefaultCategoryDataset();
	
	  for (Map<String, Integer> map : data) {
	
		  Set<Entry<String, Integer>> dataSet =  map.entrySet();
		  for (Entry<String, Integer> entry : dataSet) {
			  //entry.getKey();
			 // entry.getValue();
			  chartDataSet.addValue(entry.getValue(), entry.getValue(), entry.getKey())  ;
		}
	}
	
	return chartDataSet;
  }
  
  public GroupedStackedBarRenderer createRender(int keySetSize)
  {
    GroupedStackedBarRenderer renderer = new GroupedStackedBarRenderer();
    
    renderer.setItemMargin(0.0);
    Paint darkLilac = new GradientPaint(0.0f, 0.0f, new Color(113, 88, 143), 0.0f, 0.0f, new Color(113, 88, 143));
    renderer.setSeriesPaint(0, darkLilac);
    /*renderer.setSeriesPaint(4, p1);
    renderer.setSeriesPaint(8, p1);*/
     
    Paint steelGray = new GradientPaint(0.0f, 0.0f, new Color(147, 169, 207), 0.0f, 0.0f, new Color(153, 190, 215));
    renderer.setSeriesPaint(1, steelGray); 
    /*renderer.setSeriesPaint(5, p2); 
    renderer.setSeriesPaint(9, p2); */
    
    Paint orange = new GradientPaint(0.0f, 0.0f, new Color(219, 132, 61), 0.0f, 0.0f, new Color(219, 132, 61));
    renderer.setSeriesPaint(2, orange);
    /*renderer.setSeriesPaint(6, p3);
    renderer.setSeriesPaint(10, p3);*/
        
	Paint darkBrown = new GradientPaint(0.0f, 0.0f, new Color(170, 70, 67), 0.0f, 0.0f, new Color(170, 70, 67));
    renderer.setSeriesPaint(3, darkBrown);
    /*renderer.setSeriesPaint(7, p4);
    renderer.setSeriesPaint(11, p4);*/
    
    Paint bluishGray = new GradientPaint(0.0f, 0.0f, new Color(69, 114, 167), 0.0f, 0.0f, new Color(69, 114, 167));
    renderer.setSeriesPaint(4, bluishGray);
    
    Paint red = new GradientPaint(0.0f, 0.0f, new Color(255, 0, 0), 0.0f, 0.0f, new Color(255, 0, 0));
    renderer.setSeriesPaint(5, red);
    
    Paint seaGreen = new GradientPaint(0.0f, 0.0f, new Color(65, 152, 175), 0.0f, 0.0f, new Color(65, 152, 175));
    renderer.setSeriesPaint(6, seaGreen);
    
    /* Changes for Non RCA field addition */
    Paint lightGrey = new GradientPaint(0.0f, 0.0f, new Color(155, 155, 155), 0.0f, 0.0f, new Color(155, 155, 155));
    renderer.setSeriesPaint(7, lightGrey);
    
    renderer.setGradientPaintTransformer(
        new StandardGradientPaintTransformer(GradientPaintTransformType.HORIZONTAL)
    );
    
    /* Enabling the tool tip generator */
    renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
    
    /* Setting the Bar paint to be plain & not glossy. */
    ((BarRenderer) renderer).setBarPainter(new StandardBarPainter());
	
	// Set bars maximum width
    if(keySetSize<6){
    	
    	((BarRenderer) renderer).setItemMargin(0.40);
    	((BarRenderer) renderer).setMaximumBarWidth(0.10);
    	
    }else{
    	((BarRenderer) renderer).setItemMargin(0.50);
    	((BarRenderer) renderer).setMaximumBarWidth(0.05);
    }
    
    return renderer;
  }
  
  
  public BarRenderer createSprintRender()
  {
		BarRenderer renderer = new BarRenderer();

		Paint lightBlue = new GradientPaint(0.0f, 0.0f, new Color(30, 144, 255), 0.0f, 0.0f, new Color(70,130,180));
		renderer.setSeriesPaint(0, lightBlue);

		Paint orange = new GradientPaint(0.0f, 0.0f, new Color(255, 127, 80), 0.0f, 0.0f, new Color(255, 127, 80));
		renderer.setSeriesPaint(1, orange);

		Paint green = new GradientPaint(0.0f, 0.0f, new Color(60, 179, 113), 0.0f, 0.0f, new Color(34,139,34));
		renderer.setSeriesPaint(2, green);

		Paint red = new GradientPaint(0.0f, 0.0f, new Color(255, 0, 0), 0.0f, 0.0f, new Color(255, 0, 0));
		renderer.setSeriesPaint(3, red);

		renderer.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.VERTICAL));

		renderer.setGradientPaintTransformer(
				new StandardGradientPaintTransformer(GradientPaintTransformType.HORIZONTAL));

		/* Enabling the tool tip generator */
		renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());

		CategoryItemLabelGenerator itemLabelGenerator = new StandardCategoryItemLabelGenerator();
		renderer.setBaseItemLabelGenerator(itemLabelGenerator);
		Font font1 = new Font("Franklin Gothic Book Heavy", Font.BOLD, 17);
		renderer.setBaseItemLabelFont(font1);
		renderer.setPositiveItemLabelPosition(
				new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
		renderer.setBaseItemLabelsVisible(true);

		/* Setting the Bar paint to be plain & not glossy. */
		((BarRenderer) renderer).setBarPainter(new StandardBarPainter());

		((BarRenderer) renderer).setShadowVisible(false);
		// Set bars maximum width
		((BarRenderer) renderer).setMaximumBarWidth(0.15);

		return renderer;
  }
  
  public BarRenderer createBarRender()
  {
    BarRenderer renderer = new BarRenderer();
    
    renderer.setItemMargin(0.0);
    Paint bluishGray = new GradientPaint(0.0f, 0.0f, new Color(69, 114, 167), 0.0f, 0.0f, new Color(69, 114, 167));
    renderer.setSeriesPaint(0, bluishGray);
    /*renderer.setSeriesPaint(4, p1);
    renderer.setSeriesPaint(8, p1);*/
     
    Paint orange = new GradientPaint(0.0f, 0.0f, new Color(219, 132, 61), 0.0f, 0.0f, new Color(219, 132, 61));
    renderer.setSeriesPaint(1, orange); 
    /*renderer.setSeriesPaint(5, p2); 
    renderer.setSeriesPaint(9, p2); */
    
    renderer.setGradientPaintTransformer(
        new StandardGradientPaintTransformer(GradientPaintTransformType.HORIZONTAL)
    );
    
    /* Enabling the tool tip generator */
    renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
    
      CategoryItemLabelGenerator itemLabelGenerator = new StandardCategoryItemLabelGenerator();
	  renderer.setBaseItemLabelGenerator(itemLabelGenerator);
	  Font font1 = new Font("Franklin Gothic Book Heavy", Font.BOLD, 17);
	  renderer.setBaseItemLabelFont(font1);
	  renderer.setPositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,TextAnchor.BASELINE_CENTER));
	  renderer.setBaseItemLabelsVisible(true);
	  
	 
    
    /* Setting the Bar paint to be plain & not glossy. */
    ((BarRenderer) renderer).setBarPainter(new StandardBarPainter());
    
    ((BarRenderer) renderer).setShadowVisible(false);
    // Set bars maximum width
    ((BarRenderer) renderer).setMaximumBarWidth(0.15);
    
    return renderer;
  }
  
  
  public CategoryPlot createPlot(JFreeChart jFreeChart)
  {
 // get a reference to the plot for further customization...
    CategoryPlot plot = jFreeChart.getCategoryPlot();
    plot.setBackgroundPaint(Color.white);
    plot.setDomainGridlinePaint(Color.white);
    plot.setDomainGridlinesVisible(true);
    plot.setRangeGridlinePaint(Color.black);
    
    // set the range axis to display integers only...
    final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    ValueAxis yAxis = plot.getRangeAxis();
    double x = yAxis.getUpperBound();
    if(x<25)
    	yAxis.setUpperBound(x+1);
    else 
    	yAxis.setUpperMargin(0.15);
    rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    
    /*CategoryAxis domainAxis = plot.getDomainAxis();
    domainAxis.setCategoryLabelPositions(
        CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
    );*/
    return plot;
  }
  
  class CustomRenderer extends StackedBarRenderer
  {
	  /**
	   * Custom BarRenderer for common color throughout Weekly bar chart.
	   * @author prashant.singh
	   */
	  private static final long serialVersionUID = 4985191864600514284L;

	  public CustomRenderer()
	  {
	  }

	  public Paint getItemPaint(final int row, final int column)
	  {
		  Paint p1 = new GradientPaint(0.0f, 0.0f, new Color(78, 115, 168), 0.0f, 0.0f, new Color(78, 115, 168));
		  // returns color depending on y coordinate.
		  return (row > 0) ? p1 : p1 ;
	  }
  }
  
  public File createGraphImage(JFreeChart jFreeChart, int graphWidth, int graphHeight)
  {
    // Step -3 : Write line chart to a file */
    File chart = new File("D:\\bar_Chart_example1.png");
    try
    {
      ChartUtilities.saveChartAsPNG(chart, jFreeChart, graphWidth,
        graphHeight);
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return chart;
  }
}
