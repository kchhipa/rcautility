package com.rca.service.graph.creation;

import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import com.rca.common.RCAConstants;

/**
 * Interface which creates different types of graphs
 * Following factory pattern.
 * @author naina.singhal
 *
 */
public class DifferentTypeGraphCreationFactory
{
   
  public static DifferentTypeGraphAbstractCreation createGraphCreationObject(String graphType, String graphHeader, String xAxis, String yAxis, PlotOrientation plotOrientation, DefaultCategoryDataset dataSet, boolean legend, boolean tooltips)
  {
	  if(RCAConstants.BAR.equalsIgnoreCase(graphType))
	    {
	      return new BarGraphCreation(graphHeader, xAxis, yAxis, plotOrientation, dataSet, legend, tooltips);
	    }
	    else if(RCAConstants.NORMAL_BAR.equalsIgnoreCase(graphType))
	    {
	      return new NormalBarGraphCreation(graphHeader, xAxis, yAxis, plotOrientation, dataSet, legend, tooltips);
	    }
	    else
	    {
	      return new LineGraphCreation(graphHeader, xAxis, yAxis, plotOrientation, dataSet, legend, tooltips);
	    }
    
  }
  
}


