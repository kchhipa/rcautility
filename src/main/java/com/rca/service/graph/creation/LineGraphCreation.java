package com.rca.service.graph.creation;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class LineGraphCreation extends DifferentTypeGraphAbstractCreation
{
  /**
   * Parameterized Constructor.
   */
  public LineGraphCreation(String graphHeader, String xAxis, String yAxis, PlotOrientation plotOrientation, DefaultCategoryDataset dataSet, boolean legend, boolean tooltips)
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
    return ChartFactory.createLineChart(graphHeader, xAxis , yAxis, dataSet, plotOrientation, legend, tooltips, false);
  }

}
