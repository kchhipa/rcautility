package com.rca.service;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.GroupedStackedBarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;

import com.rca.service.graph.creation.DifferentTypeGraphAbstractCreation;
import com.rca.service.graph.creation.DifferentTypeGraphCreationFactory;


public class GenerateGraph
{

  public void createGraph(Map<String, Map<String,Integer>> data, String graphHeader, String xAxis, String yAxis, PlotOrientation plotOrientation, boolean rotatedLabel, int graphWidth, int graphHeight, String graphType)
  {
    DefaultCategoryDataset chartDataSet = dataSetObjectCreation(data);
    DifferentTypeGraphAbstractCreation graphCreationObject = DifferentTypeGraphCreationFactory.createGraphCreationObject(graphType, graphHeader, xAxis, yAxis, plotOrientation, chartDataSet);
    JFreeChart jFreeChart = graphCreationObject.createGraph();
 // set the background color for the chart...
    jFreeChart.setBackgroundPaint(Color.white);
    CategoryPlot plot = createPlot(jFreeChart);
    plot.setRenderer(createRender());
    createGraphImage(jFreeChart, graphWidth, graphHeight);
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
    return renderer;
  }
  
  public CategoryPlot createPlot(JFreeChart jFreeChart)
  {
 // get a reference to the plot for further customization...
    CategoryPlot plot = jFreeChart.getCategoryPlot();
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
    return plot;
  }
  
  public void createGraphImage(JFreeChart jFreeChart, int graphWidth, int graphHeight)
  {
    // Step -3 : Write line chart to a file */
    File barChart = new File("D:\\bar_Chart_example.png");
    try
    {
      ChartUtilities.saveChartAsPNG(barChart, jFreeChart, graphWidth,
        graphHeight);
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
