package com.rca.service.graph.creation;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class NormalBarGraphCreation extends DifferentTypeGraphAbstractCreation
{

  /**
   * Parameterized Constructor.
   */
  public NormalBarGraphCreation(String graphHeader, String xAxis, String yAxis, PlotOrientation plotOrientation, DefaultCategoryDataset dataSet, boolean legend, boolean tooltips)
  {
    this.graphHeader = graphHeader;
    this.xAxis = xAxis;
    this.yAxis = yAxis;
    this.plotOrientation = plotOrientation;
    this.dataSet = dataSet;
    this.legend = legend;
    this.tooltips = tooltips;
  }
  
  @Override
  public JFreeChart createGraph()
  {
    return ChartFactory.createBarChart(graphHeader, xAxis , yAxis, dataSet, plotOrientation, legend, tooltips, false);
  }

}
