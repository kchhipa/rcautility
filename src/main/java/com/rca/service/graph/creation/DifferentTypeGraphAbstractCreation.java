package com.rca.service.graph.creation;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public abstract class DifferentTypeGraphAbstractCreation
{
  String graphHeader;
  String xAxis;
  String yAxis;
  PlotOrientation plotOrientation;
  DefaultCategoryDataset dataSet;
  boolean legend; 
  boolean tooltips;
  
  public abstract JFreeChart createGraph();
  
  /**
   * @return the graphHeader
   */
  public String getGraphHeader()
  {
    return graphHeader;
  }
  /**
   * @param graphHeader the graphHeader to set
   */
  public void setGraphHeader(String graphHeader)
  {
    this.graphHeader = graphHeader;
  }
  /**
   * @return the xAxis
   */
  public String getxAxis()
  {
    return xAxis;
  }
  /**
   * @param xAxis the xAxis to set
   */
  public void setxAxis(String xAxis)
  {
    this.xAxis = xAxis;
  }
  /**
   * @return the yAxis
   */
  public String getyAxis()
  {
    return yAxis;
  }
  /**
   * @param yAxis the yAxis to set
   */
  public void setyAxis(String yAxis)
  {
    this.yAxis = yAxis;
  }
  /**
   * @return the plotOrientation
   */
  public PlotOrientation getPlotOrientation()
  {
    return plotOrientation;
  }
  /**
   * @param plotOrientation the plotOrientation to set
   */
  public void setPlotOrientation(PlotOrientation plotOrientation)
  {
    this.plotOrientation = plotOrientation;
  }
  /**
   * @return dataSet
   */
  public DefaultCategoryDataset getDataSet()
  {
    return dataSet;
  }
  /**
   * @param dataSet the dataSet to set
   */
  public void setBar_chart_dataset(DefaultCategoryDataset dataSet)
  {
    this.dataSet = dataSet;
  }

public boolean isLegend() {
	return legend;
}

public void setLegend(boolean legend) {
	this.legend = legend;
}

public boolean isTooltips() {
	return tooltips;
}

public void setTooltips(boolean tooltips) {
	this.tooltips = tooltips;
}
  
}
