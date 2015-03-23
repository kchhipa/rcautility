package com.rca.service;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Paint;
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
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.GroupedStackedBarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.LegendTitle;
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
    DefaultCategoryDataset chartDataSet = dataSetObjectCreation(data);
    DifferentTypeGraphAbstractCreation graphCreationObject = DifferentTypeGraphCreationFactory.createGraphCreationObject(graphType, graphHeader, xAxis, yAxis, plotOrientation, chartDataSet, true, true);
    JFreeChart jFreeChart = graphCreationObject.createGraph();
    
    // set the background color for the chart...
    jFreeChart.setBackgroundPaint(Color.white);
    
    LegendTitle legend = (LegendTitle) jFreeChart.getSubtitle(0);
    legend.setItemLabelPadding(new RectangleInsets(2, 2, 2, 30));
    //legend.setPosition(RectangleEdge.RIGHT);
    legend.setLegendItemGraphicLocation(RectangleAnchor.CENTER);
    legend.setFrame(BlockBorder.NONE);
    
    CategoryPlot plot = createPlot(jFreeChart);
	CategoryAxis domainAxis = plot.getDomainAxis();

	/* If inclined label is required on domain axis */
	if(rotatedLabel)
	{
		domainAxis.setCategoryLabelPositions(
				CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));
	}
    
    plot.setRenderer(createRender());
    return createGraphImage(jFreeChart, graphWidth, graphHeight);
  }
  
  @SuppressWarnings("deprecation")
  public File createWeeklyGraph(List data, String graphHeader, String xAxis, String yAxis, PlotOrientation plotOrientation, boolean rotatedLabel, int graphWidth, int graphHeight, String graphType)
  {
	  DefaultCategoryDataset chartDataSet = dataSetObjectCreation(data);
	  DifferentTypeGraphAbstractCreation graphCreationObject = DifferentTypeGraphCreationFactory.createGraphCreationObject(graphType, graphHeader, xAxis, yAxis, plotOrientation, chartDataSet, false, false);
	  JFreeChart jFreeChart = graphCreationObject.createGraph();

	  // set the background color for the chart...
	  jFreeChart.setBackgroundPaint(Color.white);
	  CategoryPlot plot = createPlot(jFreeChart);
	  CategoryAxis domainAxis = plot.getDomainAxis();

	  /* If inclined label is required on domain axis */
	  if(rotatedLabel)
	  {
		  domainAxis.setCategoryLabelPositions(
				  CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));
	  }

	  CategoryItemRenderer renderer = new CustomRenderer();

	  /* This Generator enables the labels to be placed on top of bars */
	  CategoryItemLabelGenerator itemLabelGenerator = new StandardCategoryItemLabelGenerator();
	  renderer.setBaseItemLabelGenerator(itemLabelGenerator);
	  renderer.setPositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,TextAnchor.BASELINE_CENTER));
	  renderer.setBaseItemLabelsVisible(true);
	  
	  ((BarRenderer) renderer).setBarPainter(new StandardBarPainter());
	  ((BarRenderer) renderer).setShadowVisible(false);

	  plot.setRenderer(renderer);
	  	  
	  //plot.setRenderer(createWeeklyGraphRender(plot));   
	  return createGraphImage(jFreeChart, graphWidth, graphHeight);
	}
  
  
  public File createIndividualWeeklyGraph(Map<String, Map<String,Integer>> data, String graphHeader, String xAxis, String yAxis, PlotOrientation plotOrientation, boolean rotatedLabel, int graphWidth, int graphHeight, String graphType)
  {
	    DefaultCategoryDataset chartDataSet = dataSetObjectCreation(data);
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
	    CategoryPlot plot = createPlot(jFreeChart);
	    CategoryAxis domainAxis = plot.getDomainAxis();
	    if(rotatedLabel)
		  {
			  domainAxis.setCategoryLabelPositions(
					  CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 2.0));
		  }
	    plot.setRenderer(createRender());
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
  
  public GroupedStackedBarRenderer createRender()
  {
    GroupedStackedBarRenderer renderer = new GroupedStackedBarRenderer();
    
    renderer.setItemMargin(0.0);
    Paint p1 = new GradientPaint(
        0.0f, 0.0f, new Color(0x22, 0x22, 0xFF), 0.0f, 0.0f, new Color(0x88, 0x88, 0xFF)
    );
    renderer.setSeriesPaint(0, p1);
    renderer.setSeriesPaint(4, p1);
    renderer.setSeriesPaint(8, p1);
     
    Paint p2 = new GradientPaint(
        0.0f, 0.0f, new Color(0x22, 0xFF, 0x22), 0.0f, 0.0f, new Color(0x88, 0xFF, 0x88)
    );
    renderer.setSeriesPaint(1, p2); 
    renderer.setSeriesPaint(5, p2); 
    renderer.setSeriesPaint(9, p2); 
    
    Paint p3 = new GradientPaint(
        0.0f, 0.0f, new Color(0xFF, 0x22, 0x22), 0.0f, 0.0f, new Color(0xFF, 0x88, 0x88)
    );
    renderer.setSeriesPaint(2, p3);
    renderer.setSeriesPaint(6, p3);
    renderer.setSeriesPaint(10, p3);
        
    Paint p4 = new GradientPaint(
        0.0f, 0.0f, new Color(0xFF, 0xFF, 0x22), 0.0f, 0.0f, new Color(0xFF, 0xFF, 0x88)
    );
    renderer.setSeriesPaint(3, p4);
    renderer.setSeriesPaint(7, p4);
    renderer.setSeriesPaint(11, p4);
    renderer.setGradientPaintTransformer(
        new StandardGradientPaintTransformer(GradientPaintTransformType.HORIZONTAL)
    );
    
    /* Setting the Bar paint to be plain & not glossy. */
    ((BarRenderer) renderer).setBarPainter(new StandardBarPainter());
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
		  Paint p1 = new GradientPaint(0.0f, 0.0f, new Color(57, 126, 186), 0.0f, 0.0f, new Color(57, 126, 186));
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
