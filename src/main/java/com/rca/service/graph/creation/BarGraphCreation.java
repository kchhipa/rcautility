package com.rca.service.graph.creation;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarGraphCreation extends DifferentTypeGraphAbstractCreation
{

  /**
   * Parameterized Constructor.
   */
  public BarGraphCreation(String graphHeader, String xAxis, String yAxis, PlotOrientation plotOrientation, DefaultCategoryDataset dataSet)
  {
    this.graphHeader = graphHeader;
    this.xAxis = graphHeader;
    this.yAxis = yAxis;
    this.plotOrientation = plotOrientation;
    this.dataSet = dataSet;
  }
  
  @Override
  public JFreeChart createGraph()
  {
    return ChartFactory.createStackedBarChart3D(graphHeader, xAxis , yAxis, dataSet, plotOrientation, true, true, false);
  }

}
