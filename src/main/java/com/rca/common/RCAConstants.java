package com.rca.common;

public final class RCAConstants
{

  public static final String BAR = "Bar";
  public static final String NORMAL_BAR = "Normal_Bar";
  public static final String LINE = "Line";
  
  
  public final class DefectSource
  {
    private static final String PRODUCTION = "Prod";
    private static final String UAT = "UAT";
    private static final String QA = "qa";
    private static final String COM_OPEN = "commulative_open";
    
    private String defectSource;

    /**
     * @return the defectSource
     */
    public String getDefectSource()
    {
      return defectSource;
    }

    /**
     * @param defectSource the defectSource to set
     */
    public void setDefectSource(String defectSource)
    {
      this.defectSource = defectSource;
    }
  }
  
  public final class GraphMode
  {
    private static final String PROJECTLY = "Projectly";
    private static final String WEEKLY = "Weekly";
    private static final String CLIENT_CODE = "client_code";
    private static final String SPRINTLY = "sprintly";
    
    private String graphMode;

    /**
     * @return the graphMode
     */
    public String getGraphMode()
    {
      return graphMode;
    }

    /**
     * @param graphMode the graphMode to set
     */
    public void setGraphMode(String graphMode)
    {
      this.graphMode = graphMode;
    }
    
  }
  public final class BugType
  {
    private static final String DATA_ISSUE = "Data Issue";
    private static final String INTEGRATION_ISSUE  = "Integration Issue";
    private static final String PRODUCT_DEFECT = "Product Defect";
    private static final String NOT_DEFECT = "Duplicate/Not a Defect/Unable to Reproduce/Browser/As Designed";
    private static final String CLIENT_CODE = "Client Code Bug";
    private static final String CONFIGURATION = "Configuration";
    private static final String MISSED_REQUIREMENT = "Missed Requirement/Change Request";
  }
}
