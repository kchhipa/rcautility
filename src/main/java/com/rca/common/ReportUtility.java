package com.rca.common;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.rca.dao.RcaCountDAOImpl;
import com.rca.entity.RankingFramework;
import com.rca.entity.RcaCount;
import com.rca.entity.SprintReport;

public class ReportUtility {
	
	public static final String PRODUCTION = "PROD";
	public static final String UAT = "UAT";
	public static final String QA = "QA";
	private static String CUMULATIVE_OPEN = "Cumulative Open";
	private static final Log log = LogFactory.getLog(RcaCountDAOImpl.class);
	private static final XSSFColor BLUE = new XSSFColor(new Color(197,217,241));
	private static final XSSFColor YELLOW = new XSSFColor(new Color(255,255,0));
	private static final XSSFColor GREY = new XSSFColor(new Color(191, 190, 154));

	public Map<String, Map<String, Integer>> rcaCountForLastWeekForAllProjects(List<RcaCount> rcaWeeks){
	  Map<String, Map<String, Integer>> diffCategory = new HashMap<String, Map<String, Integer>>();;
    
    for(int x=0; x < rcaWeeks.size(); x++){
          
          String projName = rcaWeeks.get(x).getProjectDetails().getProjectName();
          
          RcaCount rcaCount = rcaWeeks.get(x);
          
          //diffCategory = new HashMap<String, Map<String, Integer>>();
          
          Map<String, Integer> differentRootCause = new LinkedHashMap<String, Integer>();
          differentRootCause.put("Duplicate/ Not a Defect/ Unable to reproduce/ Browse/ As designed", 
        		  mixCategoryWeeklyCountForAllProjectsInOpen(rcaCount));
          differentRootCause.put("Data Issue", weeklyDataIssueForAllIssuesInOpen(rcaCount));
          differentRootCause.put("Integration Issue", weeklyIntegrationIssueForAllIssuesInOpen(rcaCount));
          differentRootCause.put("Configuration Issue", weeklyConfigurationIssueForAllIssuesInOpen(rcaCount));
          differentRootCause.put("Missed/ Change Requirement", weeklyMissedAndCRCountForAllIssuesInOpen(rcaCount));
          differentRootCause.put("Client Code Bug", weeklyClientCodeBugForAllIssuesInOpen(rcaCount));
          differentRootCause.put("Product Defect", weeklyProductDefectForAllIssuesInOpen(rcaCount));
          
          /* To ensure that Projects with RCA count as zero do not show up in Graph */
          if((mixCategoryWeeklyCountForAllProjectsInOpen(rcaCount) + weeklyDataIssueForAllIssuesInOpen(rcaCount) + weeklyDataIssueForAllIssuesInOpen(rcaCount) + 
        		  weeklyConfigurationIssueForAllIssuesInOpen(rcaCount) + weeklyMissedAndCRCountForAllIssuesInOpen(rcaCount) + weeklyClientCodeBugForAllIssuesInOpen(rcaCount) + 
        		  weeklyProductDefectForAllIssuesInOpen(rcaCount)) > 0)
        	  	diffCategory.put(projName, differentRootCause);
          
          //projCount.put(projName, diffCategory);
    }
    
    return diffCategory;

		
	}

	
	public Map<String, Map<String, Integer>> reportedQARCAForAllProjects(List<RcaCount> rcaCounts){

		Map<String, Map<String, Integer>> diffCategory = new LinkedHashMap<String, Map<String, Integer>>();;
		
		for(int x=0; x < rcaCounts.size(); x++){
			
			String projName = rcaCounts.get(x).getProjectDetails().getProjectName();
			RcaCount rcaCount = rcaCounts.get(x);
			
			int totalCount = mixCategoryWeeklyCountForAllProjectsInQA(rcaCount) + weeklyDataIssueForAllIssuesInQA(rcaCount) +
					weeklyIntegrationIssueForAllIssuesInQA(rcaCount) + weeklyConfigurationIssueForAllIssuesInQA(rcaCount) +
					weeklyMissedAndCRCountForAllIssuesInQA(rcaCount) + weeklyClientCodeBugForAllIssuesInQA(rcaCount) + weeklyProductDefectForAllIssuesInQA(rcaCount);
			
			
			if(totalCount > 0){
				Map<String, Integer> differentRootCause = new LinkedHashMap<String, Integer>();
				
				differentRootCause.put("Duplicate/ Not a Defect/ Unable to reproduce/ Browse/ As designed", mixCategoryWeeklyCountForAllProjectsInQA(rcaCount));
	            differentRootCause.put("Data Issue", weeklyDataIssueForAllIssuesInQA(rcaCount));
	            differentRootCause.put("Integration Issue", weeklyIntegrationIssueForAllIssuesInQA(rcaCount));
	            differentRootCause.put("Configuration Issue", weeklyConfigurationIssueForAllIssuesInQA(rcaCount));
	            differentRootCause.put("Missed/ Change Requirement", weeklyMissedAndCRCountForAllIssuesInQA(rcaCount));
	            differentRootCause.put("Client Code Bug", weeklyClientCodeBugForAllIssuesInQA(rcaCount));
	            differentRootCause.put("Product Defect", weeklyProductDefectForAllIssuesInQA(rcaCount));
	
	            diffCategory.put(projName, differentRootCause);
				
			}
			
		}
		
		return diffCategory;
		
	}
	
	public Map<String, Map<String, Integer>> reportedSprintGraph(SprintReport sprintReport) {

		Map<String, Map<String, Integer>> diffCategory = new LinkedHashMap<String, Map<String, Integer>>();;
		Map<String, Integer> differentRootCause = new LinkedHashMap<String, Integer>();
		Map<String, Integer> differentRootCause1 = new LinkedHashMap<String, Integer>();
		if(sprintReport!=null){
			differentRootCause.put("User Story Points", sprintReport.getSprint1UserStory());
			differentRootCause.put("No. of Defects", sprintReport.getSprint1BugCount());
			diffCategory.put(sprintReport.getSprint1Name(), differentRootCause);

			differentRootCause1.put("User Story Points", sprintReport.getSprint2UserStory());
			differentRootCause1.put("No. of Defects", sprintReport.getSprint2BugCount());
			diffCategory.put(sprintReport.getSprint2Name(), differentRootCause1);
		}
		return diffCategory;


	}
	
	/**
	 * This method return map of all QA defect category wise for A project
	 * @param rcaCounts
	 * @param allWeeks
	 * @return
	 */
	public Map<String, Map<String, Integer>> reportedQAAllWeeksGraphForIndividualProject(List<RcaCount> rcaCounts, List<String> allWeeks){
		
		Map<String, Map<String, Integer>> diffCategory = new LinkedHashMap<String, Map<String, Integer>>();
		
		LinkedHashMap<String, RcaCount> rcaCountMap = getRcaCountMap(rcaCounts);
		
		for(int x=0; x < allWeeks.size(); x++){
			String week = removeYearFromWeek(allWeeks.get(x))+"  ";
			RcaCount rcaCount = rcaCountMap.get(allWeeks.get(x));
			Map<String, Integer> differentRootCause = new LinkedHashMap<String, Integer>();
			if(rcaCount !=null )
			{
				differentRootCause.put("Duplicate/ Not a Defect/ Unable to reproduce/ Browse/ As designed", mixCategoryWeeklyCountForAllProjectsInQA(rcaCount));
	            differentRootCause.put("Data Issue", weeklyDataIssueForAllIssuesInQA(rcaCount));
	            differentRootCause.put("Integration Issue", weeklyIntegrationIssueForAllIssuesInQA(rcaCount));
	            differentRootCause.put("Configuration Issue", weeklyConfigurationIssueForAllIssuesInQA(rcaCount));
	            differentRootCause.put("Missed/ Change Requirement", weeklyMissedAndCRCountForAllIssuesInQA(rcaCount));
	            differentRootCause.put("Client Code Bug", weeklyClientCodeBugForAllIssuesInQA(rcaCount));
	            differentRootCause.put("Product Defect", weeklyProductDefectForAllIssuesInQA(rcaCount));
	            diffCategory.put(week, differentRootCause);
			}
			else
			{
				differentRootCause.put("Duplicate/ Not a Defect/ Unable to reproduce/ Browse/ As designed", 0);
	            differentRootCause.put("Data Issue", 0);
	            differentRootCause.put("Integration Issue", 0);
	            differentRootCause.put("Configuration Issue", 0);
	            differentRootCause.put("Missed/ Change Requirement", 0);
	            differentRootCause.put("Client Code Bug", 0);
	            differentRootCause.put("Product Defect", 0);
				diffCategory.put(week, differentRootCause);
			}
		}			
		
		return diffCategory;
	}
	
	

	/**
	 * This method return map of all UAT defect category wise for A project
	 * @param rcaCounts
	 * @param allWeeks
	 * @return
	 */
	public Map<String, Map<String, Integer>> reportedUATAllWeeksGraphForIndividualProject(List<RcaCount> rcaCounts, List<String> allWeeks){
		
		Map<String, Map<String, Integer>> diffCategory = new LinkedHashMap<String, Map<String, Integer>>();
		
		LinkedHashMap<String, RcaCount> rcaCountMap = getRcaCountMap(rcaCounts);
		
		for(int x=0; x < allWeeks.size(); x++){
			String week = removeYearFromWeek(allWeeks.get(x))+"  ";
			RcaCount rcaCount = rcaCountMap.get(allWeeks.get(x));
			Map<String, Integer> differentRootCause = new LinkedHashMap<String, Integer>();
			if(rcaCount !=null )
			{
				differentRootCause.put("Duplicate/ Not a Defect/ Unable to reproduce/ Browse/ As designed", mixCategoryWeeklyCountForAllProjectsInUAT(rcaCount));
	            differentRootCause.put("Data Issue", weeklyDataIssueForAllIssuesInUAT(rcaCount));
	            differentRootCause.put("Integration Issue", weeklyIntegrationIssueForAllIssuesInUAT(rcaCount));
	            differentRootCause.put("Configuration Issue", weeklyConfigurationIssueForAllIssuesInUAT(rcaCount));
	            differentRootCause.put("Missed/ Change Requirement", weeklyMissedAndCRCountForAllIssuesInUAT(rcaCount));
	            differentRootCause.put("Client Code Bug", weeklyClientCodeBugForAllIssuesInUAT(rcaCount));
	            differentRootCause.put("Product Defect", weeklyProductDefectForAllIssuesInUAT(rcaCount));
	            diffCategory.put(week, differentRootCause);
			}
			else
			{
				differentRootCause.put("Duplicate/ Not a Defect/ Unable to reproduce/ Browse/ As designed", 0);
	            differentRootCause.put("Data Issue", 0);
	            differentRootCause.put("Integration Issue", 0);
	            differentRootCause.put("Configuration Issue", 0);
	            differentRootCause.put("Missed/ Change Requirement", 0);
	            differentRootCause.put("Client Code Bug", 0);
	            differentRootCause.put("Product Defect", 0);
				diffCategory.put(week, differentRootCause);
			}
		}			
		
		return diffCategory;
	}	
	
	/**
	 * This method return map of all prod defect category wise for A project
	 * @param rcaCounts
	 * @param allWeeks
	 * @return
	 */
	public Map<String, Map<String, Integer>> reportedProdAllWeeksGraphForIndividualProject(List<RcaCount> rcaCounts, List<String> allWeeks){
		
		Map<String, Map<String, Integer>> diffCategory = new LinkedHashMap<String, Map<String, Integer>>();
		
		LinkedHashMap<String, RcaCount> rcaCountMap = getRcaCountMap(rcaCounts);
		
		for(int x=0; x < allWeeks.size(); x++){
			String week = removeYearFromWeek(allWeeks.get(x))+"  ";
			RcaCount rcaCount = rcaCountMap.get(allWeeks.get(x));
			Map<String, Integer> differentRootCause = new LinkedHashMap<String, Integer>();
			if(rcaCount !=null )
			{
				differentRootCause.put("Duplicate/ Not a Defect/ Unable to reproduce/ Browse/ As designed", mixCategoryWeeklyCountForAllProjectsInProd(rcaCount));
	            differentRootCause.put("Data Issue", weeklyDataIssueForAllIssuesInProd(rcaCount));
	            differentRootCause.put("Integration Issue", weeklyIntegrationIssueForAllIssuesInProd(rcaCount));
	            differentRootCause.put("Configuration Issue", weeklyConfigurationIssueForAllIssuesInProd(rcaCount));
	            differentRootCause.put("Missed/ Change Requirement", weeklyMissedAndCRCountForAllIssuesInProd(rcaCount));
	            differentRootCause.put("Client Code Bug", weeklyClientCodeBugForAllIssuesInProd(rcaCount));
	            differentRootCause.put("Product Defect", weeklyProductDefectForAllIssuesInProd(rcaCount));
	            diffCategory.put(week, differentRootCause);
			}
			else
			{
				differentRootCause.put("Duplicate/ Not a Defect/ Unable to reproduce/ Browse/ As designed", 0);
	            differentRootCause.put("Data Issue", 0);
	            differentRootCause.put("Integration Issue", 0);
	            differentRootCause.put("Configuration Issue", 0);
	            differentRootCause.put("Missed/ Change Requirement", 0);
	            differentRootCause.put("Client Code Bug", 0);
	            differentRootCause.put("Product Defect", 0);
				diffCategory.put(week, differentRootCause);
			}
		}		
		
		return diffCategory;
	}	

	/**
	 * This method return map of all open defect category wise for A project
	 * @param rcaCounts
	 * @param allWeeks
	 * @return
	 */
	public Map<String, Map<String, Integer>> reportedOpenAllWeeksGraphForIndividualProject(List<RcaCount> rcaCounts, List<String> allWeeks){
		
		Map<String, Map<String, Integer>> diffCategory = new LinkedHashMap<String, Map<String, Integer>>();
		
		LinkedHashMap<String, RcaCount> rcaCountMap = getRcaCountMap(rcaCounts);
		
		for(int x=0; x < allWeeks.size(); x++){
			String week = removeYearFromWeek(allWeeks.get(x))+"  ";
			RcaCount rcaCount = rcaCountMap.get(allWeeks.get(x));
			Map<String, Integer> differentRootCause = new LinkedHashMap<String, Integer>();
			if(rcaCount !=null )
			{
				differentRootCause.put("Duplicate/ Not a Defect/ Unable to reproduce/ Browse/ As designed", mixCategoryWeeklyCountForAllProjectsInOpen(rcaCount));
	            differentRootCause.put("Data Issue", weeklyDataIssueForAllIssuesInOpen(rcaCount));
	            differentRootCause.put("Integration Issue", weeklyIntegrationIssueForAllIssuesInOpen(rcaCount));
	            differentRootCause.put("Configuration Issue", weeklyConfigurationIssueForAllIssuesInOpen(rcaCount));
	            differentRootCause.put("Missed/ Change Requirement", weeklyMissedAndCRCountForAllIssuesInOpen(rcaCount));
	            differentRootCause.put("Client Code Bug", weeklyClientCodeBugForAllIssuesInOpen(rcaCount));
	            differentRootCause.put("Product Defect", weeklyProductDefectForAllIssuesInOpen(rcaCount));
	            diffCategory.put(week, differentRootCause);
			}
			else
			{
				differentRootCause.put("Duplicate/ Not a Defect/ Unable to reproduce/ Browse/ As designed", 0);
	            differentRootCause.put("Data Issue", 0);
	            differentRootCause.put("Integration Issue", 0);
	            differentRootCause.put("Configuration Issue", 0);
	            differentRootCause.put("Missed/ Change Requirement", 0);
	            differentRootCause.put("Client Code Bug", 0);
	            differentRootCause.put("Product Defect", 0);
				diffCategory.put(week, differentRootCause);
			}
		}
		
		return diffCategory;
	}	
	
	/**
	 * This mehotd returning map of rca count
	 * @param rcaCounts
	 * @return
	 */

	public LinkedHashMap<String, RcaCount> getRcaCountMap(List<RcaCount> rcaCounts){
		
		LinkedHashMap<String, RcaCount> rcaCountMap = new LinkedHashMap<String, RcaCount>();
		
		for(int x=0; x < rcaCounts.size(); x++){
		
			rcaCountMap.put(rcaCounts.get(x).getWeek(), rcaCounts.get(x));
		}
		
		return rcaCountMap;
	}
	
	/**
	 * This mehotd returning map of rca count
	 * @param rcaCounts
	 * @return
	 */

	public LinkedHashMap<String, RcaCount> getRcaCountMapForPrjWeek(List<RcaCount> rcaCounts){
		
		LinkedHashMap<String, RcaCount> rcaCountMap = new LinkedHashMap<String, RcaCount>();
		
		for(int x=0; x < rcaCounts.size(); x++){
		
			rcaCountMap.put(rcaCounts.get(x).getWeek()+"*"+rcaCounts.get(x).getProjectDetails().getProjectId() , rcaCounts.get(x));
		}
		
		return rcaCountMap;
	}	
	/**
	 * 
	 * @param rcaCounts
	 * @return
	 */
	public Map<String, Map<String, Integer>> reportedUATRCAForAllProjects(List<RcaCount> rcaCounts){

		Map<String, Map<String, Integer>> diffCategory = new LinkedHashMap<String, Map<String, Integer>>();;
		
		for(int x=0; x < rcaCounts.size(); x++){
			
			String projName = rcaCounts.get(x).getProjectDetails().getProjectName();
			RcaCount rcaCount = rcaCounts.get(x);
			
			int totalCount = mixCategoryWeeklyCountForAllProjectsInUAT(rcaCount) + weeklyDataIssueForAllIssuesInUAT(rcaCount) +
					weeklyIntegrationIssueForAllIssuesInUAT(rcaCount) + weeklyConfigurationIssueForAllIssuesInUAT(rcaCount) +
					weeklyMissedAndCRCountForAllIssuesInUAT(rcaCount) + weeklyClientCodeBugForAllIssuesInUAT(rcaCount) + weeklyProductDefectForAllIssuesInUAT(rcaCount);
			
			
			if(totalCount > 0){
				Map<String, Integer> differentRootCause = new LinkedHashMap<String, Integer>();
				
				differentRootCause.put("Duplicate/ Not a Defect/ Unable to reproduce/ Browse/ As designed", mixCategoryWeeklyCountForAllProjectsInUAT(rcaCount));
	            differentRootCause.put("Data Issue", weeklyDataIssueForAllIssuesInUAT(rcaCount));
	            differentRootCause.put("Integration Issue", weeklyIntegrationIssueForAllIssuesInUAT(rcaCount));
	            differentRootCause.put("Configuration Issue", weeklyConfigurationIssueForAllIssuesInUAT(rcaCount));
	            differentRootCause.put("Missed/ Change Requirement", weeklyMissedAndCRCountForAllIssuesInUAT(rcaCount));
	            differentRootCause.put("Client Code Bug", weeklyClientCodeBugForAllIssuesInUAT(rcaCount));
	            differentRootCause.put("Product Defect", weeklyProductDefectForAllIssuesInUAT(rcaCount));
	
	            diffCategory.put(projName, differentRootCause);
				
			}
			
		}
		
		return diffCategory;
		
	}
	
	public Map<String, Map<String, Integer>> reportedProdRCAForAllProjects(List<RcaCount> rcaCounts){

		Map<String, Map<String, Integer>> diffCategory = new LinkedHashMap<String, Map<String, Integer>>();;
		
		for(int x=0; x < rcaCounts.size(); x++){
			
			String projName = rcaCounts.get(x).getProjectDetails().getProjectName();
			RcaCount rcaCount = rcaCounts.get(x);
			
			int totalCount = mixCategoryWeeklyCountForAllProjectsInProd(rcaCount) + weeklyDataIssueForAllIssuesInProd(rcaCount) +
					weeklyIntegrationIssueForAllIssuesInProd(rcaCount) + weeklyConfigurationIssueForAllIssuesInProd(rcaCount) +
					weeklyMissedAndCRCountForAllIssuesInProd(rcaCount) + weeklyClientCodeBugForAllIssuesInProd(rcaCount) +  weeklyProductDefectForAllIssuesInProd(rcaCount);
			
			
			if(totalCount > 0){
				Map<String, Integer> differentRootCause = new LinkedHashMap<String, Integer>();
				
				differentRootCause.put("Duplicate/ Not a Defect/ Unable to reproduce/ Browse/ As designed", mixCategoryWeeklyCountForAllProjectsInProd(rcaCount));
	            differentRootCause.put("Data Issue", weeklyDataIssueForAllIssuesInProd(rcaCount));
	            differentRootCause.put("Integration Issue", weeklyIntegrationIssueForAllIssuesInProd(rcaCount));
	            differentRootCause.put("Configuration Issue", weeklyConfigurationIssueForAllIssuesInProd(rcaCount));
	            differentRootCause.put("Missed/ Change Requirement", weeklyMissedAndCRCountForAllIssuesInProd(rcaCount));
	            differentRootCause.put("Client Code Bug", weeklyClientCodeBugForAllIssuesInProd(rcaCount));
	            differentRootCause.put("Product Defect", weeklyProductDefectForAllIssuesInProd(rcaCount));
	
	            diffCategory.put(projName, differentRootCause);
				
			}
			
		}
		
		return diffCategory;
		
	}

	/**
	 * Generate a map of project and it's corresponding reopen bug count by
	 * environment type. This method will return map of Project Name as key and
	 * Map of Environment type & reopen bug count as value
	 * 
	 * @param rcaCounts
	 * @return
	 */
	public Map<String, Map<String, Integer>> reportedReopenRCAForAllProjects(
			List<RcaCount> rcaCounts) {
		Map<String, Map<String, Integer>> diffCategory = new LinkedHashMap<String, Map<String, Integer>>();
		for (int x = 0; x < rcaCounts.size(); x++) {
			RcaCount rcaCount = rcaCounts.get(x);
			int totalCount = rcaCount.getRoQa() + rcaCount.getRoUat()
					+ rcaCount.getRoProd();

			if (totalCount > 0) {
				String projName = rcaCounts.get(x).getProjectDetails()
						.getProjectName();
				Map<String, Integer> differentReopenEnvironment = new LinkedHashMap<String, Integer>();
				differentReopenEnvironment.put(QA, rcaCount.getRoQa());
				differentReopenEnvironment.put(UAT, rcaCount.getRoUat());
				differentReopenEnvironment
						.put(PRODUCTION, rcaCount.getRoProd());

				diffCategory.put(projName, differentReopenEnvironment);
			}
		}
		return diffCategory;
	}
	
	public Map<String, Integer> rcaCountForMultipleWeeksForAllProjects(List<RcaCount> rcaCounts, String week){
		Map<String, Integer> projCount = new HashMap<String, Integer>(); 
		
		for(int x=0; x < rcaCounts.size(); x++){
			
			int totalCount = weeklyTotalBugCountForAllProjects(rcaCounts.get(x));
			projCount.put(week, totalCount);
			
		}
		
		return projCount;
		
	}

	
	public int mixCategoryWeeklyCountForAllProjects(RcaCount rcaWeeks){
		int total=0;
			total = total + (rcaWeeks.getDupQa() + rcaWeeks.getDupUat() + rcaWeeks.getDupProd()+ rcaWeeks.getNadQa() +
					rcaWeeks.getNadUat() + rcaWeeks.getNadProd() + rcaWeeks.getUtrQa() + rcaWeeks.getUtrUat() +
					rcaWeeks.getUtrProd() + rcaWeeks.getBsiQa() + rcaWeeks.getBsiUat() + rcaWeeks.getBsiProd() +
					rcaWeeks.getAdQa() + rcaWeeks.getAdUat() + rcaWeeks.getAdProd());
		
			return total;
	}
	
	public int mixCategoryWeeklyCountForAllProjectsInQA(RcaCount rcaWeeks){
		int total=0;
			total = total + (rcaWeeks.getDupQa() + rcaWeeks.getNadQa() + rcaWeeks.getUtrQa() +  rcaWeeks.getBsiQa() + 
			rcaWeeks.getAdQa());
		
			return total;
	}
	
	public int mixCategoryWeeklyCountForAllProjectsInUAT(RcaCount rcaWeeks){
		int total=0;
			total = total + (rcaWeeks.getDupUat() + rcaWeeks.getNadUat() + rcaWeeks.getUtrUat() + rcaWeeks.getBsiUat() + 
			rcaWeeks.getAdUat());

			return total;
	}
	
	public int mixCategoryWeeklyCountForAllProjectsInProd(RcaCount rcaWeeks){
		int total=0;
			total = total + ( rcaWeeks.getDupProd()+ rcaWeeks.getNadProd() + rcaWeeks.getUtrProd() +  
					rcaWeeks.getBsiProd() + rcaWeeks.getAdProd());
		return total;
	}
	
	/**
	 * Method to return open Others Defect count for a selected week.
	 * @param rcaWeeks
	 * @return
	 */
	public int mixCategoryWeeklyCountForAllProjectsInOpen(RcaCount rcaWeeks){
		int total=0;
			total = total + ( rcaWeeks.getDupProductBacklog()+ rcaWeeks.getNadProductBacklog() + rcaWeeks.getUtrProductBacklog() +  
					rcaWeeks.getBsiProductBacklog() + rcaWeeks.getAdProductBacklog());
		return total;
	}
	
	public int weeklyDataIssueForAllIssues(RcaCount rcaWeeks){
		int total =0;
		
			total = total + (((rcaWeeks.getDiQa()==null)? 0 : rcaWeeks.getDiQa()) + ((rcaWeeks.getDiUat() == null)? 0 : rcaWeeks.getDiUat()) + 
					((rcaWeeks.getDiProd()==null)? 0 : rcaWeeks.getDiProd()));
		return total;
		
	}
	
	public int weeklyDataIssueForAllIssuesInQA(RcaCount rcaWeeks){
		int total =0;
		
			total = total + (rcaWeeks.getDiQa());
		return total;
		
	}
	
	public int weeklyDataIssueForAllIssuesInUAT(RcaCount rcaWeeks){
		int total =0;
		
			total = total + (rcaWeeks.getDiUat());
		return total;
		
	}
	
	public int weeklyDataIssueForAllIssuesInProd(RcaCount rcaWeeks){
		int total =0;
		
			total = total + (rcaWeeks.getDiProd());
		return total;
		
	}
	/**
	 * Method to return open data Defect count for a selected week.
	 * @param rcaWeeks
	 * @return
	 */
	
	public int weeklyDataIssueForAllIssuesInOpen(RcaCount rcaWeeks){
		int total =0;
		
			total = total + (rcaWeeks.getDiProductBacklog());
		return total;
		
	}
	public int weeklyIntegrationIssueForAllIssues(RcaCount rcaWeeks){
		int total =0;
		
			total = total + (((rcaWeeks.getIiQa()==null)? 0 : rcaWeeks.getIiQa()) + ((rcaWeeks.getIiUat() == null)? 0 : rcaWeeks.getIiUat()) + ((rcaWeeks.getIiProd()==null)? 0 : rcaWeeks.getIiProd()));
		return total;
		
	}
	
	public int weeklyIntegrationIssueForAllIssuesInQA(RcaCount rcaWeeks){
		int total =0;
		
			total = total + (rcaWeeks.getFfmQa() + rcaWeeks.getCrmesbQa() + rcaWeeks.getPmuuQa() + 
					rcaWeeks.getOtpQa() + rcaWeeks.getIoQa());
		return total;
		
	}
	
	public int weeklyIntegrationIssueForAllIssuesInUAT(RcaCount rcaWeeks){
		int total =0;
		
			total = total + (rcaWeeks.getFfmUat() + rcaWeeks.getCrmesbUat() + rcaWeeks.getPmuuUat() + 
					rcaWeeks.getOtpUat() + rcaWeeks.getIoUat());
		return total;
		
	}
	
	public int weeklyIntegrationIssueForAllIssuesInProd(RcaCount rcaWeeks){
		int total =0;
		
			total = total + (rcaWeeks.getFfmProd() + rcaWeeks.getCrmesbProd() + rcaWeeks.getPmuuProd() + 
					rcaWeeks.getOtpProd() + rcaWeeks.getIoProd());
		return total;
		
	}
	/**
	 * Method to return open Integration Defect count for a selected week.
	 * @param rcaWeeks
	 * @return
	 */
	public int weeklyIntegrationIssueForAllIssuesInOpen(RcaCount rcaWeeks){
		int total =0;
		
			total = total + (rcaWeeks.getFfmProductBacklog() + rcaWeeks.getCrmesbProductBacklog() + rcaWeeks.getPmuuProductBacklog() + 
					rcaWeeks.getOtpProductBacklog() + rcaWeeks.getIoProductBacklog());
		return total;
		
	}
	public int weeklyConfigurationIssueForAllIssues(RcaCount rcaWeeks){
		int total =0;
		
			total = total + (((rcaWeeks.getConfigQa()==null)? 0 : rcaWeeks.getConfigQa()) + ((rcaWeeks.getConfigUat() == null)? 0 : rcaWeeks.getConfigUat()) + 
					((rcaWeeks.getConfigProd()==null)? 0 : rcaWeeks.getConfigProd()));
		return total;
		
	}
	
	public int weeklyConfigurationIssueForAllIssuesInQA(RcaCount rcaWeeks){
		int total =0;
		
			total = total + (rcaWeeks.getPlanQa() + rcaWeeks.getRateQa() + rcaWeeks.getRpaQa() + rcaWeeks.getAcQa() +
					rcaWeeks.getTiQa() + rcaWeeks.getDpQa() + rcaWeeks.getEnvQa() + rcaWeeks.getCoQa());
		return total;
		
	}
	
	public int weeklyConfigurationIssueForAllIssuesInUAT(RcaCount rcaWeeks){
		int total =0;
		
			total = total + (rcaWeeks.getPlanUat() + rcaWeeks.getRateUat() + rcaWeeks.getRpaUat() + rcaWeeks.getAcUat() +
				rcaWeeks.getTiUat() + rcaWeeks.getDpUat() + rcaWeeks.getEnvUat() + rcaWeeks.getCoUat());
			
		return total;
		
	}
	
	public int weeklyConfigurationIssueForAllIssuesInProd(RcaCount rcaWeeks){
		int total =0;
		
			
			total = total + (rcaWeeks.getPlanProd() + rcaWeeks.getRateProd() + rcaWeeks.getRpaProd() + rcaWeeks.getAcProd() +
				rcaWeeks.getTiProd() + rcaWeeks.getDpProd() + rcaWeeks.getEnvProd() + rcaWeeks.getCoProd());
		
		return total;
		
	}
	/**
	 * Sum of all configuration issue in open for a project and a week
	 * @param rcaWeeks
	 * @return
	 */
	public int weeklyConfigurationIssueForAllIssuesInOpen(RcaCount rcaWeeks){
		int total =0;
		
			
			total = total + (rcaWeeks.getPlanProductBacklog() + rcaWeeks.getRateProductBacklog() + rcaWeeks.getRpaProductBacklog() + rcaWeeks.getAcProductBacklog() +
				rcaWeeks.getTiProductBacklog() + rcaWeeks.getDpProductBacklog() + rcaWeeks.getEnvProductBacklog() + rcaWeeks.getCoProductBacklog());
		
		return total;
		
	}
	public int weeklyMissedAndCRCountForAllIssues(RcaCount rcaWeeks){
		
		int total =0;
		
			total = total + rcaWeeks.getMrQa() + rcaWeeks.getMrUat() + rcaWeeks.getMrProd() +
					rcaWeeks.getCrQa() + rcaWeeks.getCrUat() + rcaWeeks.getCrProd();
		return total;
	}
	
	public int weeklyMissedAndCRCountForAllIssuesInQA(RcaCount rcaWeeks){
			
			int total =0;
			
				total = total + (rcaWeeks.getMrQa() + rcaWeeks.getCrQa());
			return total;
		}
	
	public int weeklyMissedAndCRCountForAllIssuesInUAT(RcaCount rcaWeeks){
		
		int total =0;
		
			total = total + (rcaWeeks.getMrUat() + rcaWeeks.getCrUat());
		return total;
	}
	
	public int weeklyMissedAndCRCountForAllIssuesInProd(RcaCount rcaWeeks){
		
		int total =0;
		
			total = total + (rcaWeeks.getMrProd() + rcaWeeks.getCrProd());
		return total;
	}
	
public int weeklyMissedCountForAllIssuesInQA(RcaCount rcaWeeks){
		
		int total =0;
		
			total = total + (rcaWeeks.getMrQa());
		return total;
	}

public int weeklyMissedCountForAllIssuesInUAT(RcaCount rcaWeeks){
	
	int total =0;
	
		total = total + (rcaWeeks.getMrUat());
	return total;
}

public int weeklyMissedCountForAllIssuesInProd(RcaCount rcaWeeks){
	
	int total =0;
	
		total = total + (rcaWeeks.getMrProd());
	return total;
}


public int weeklyCRCountForAllIssuesInQA(RcaCount rcaWeeks){
	
	int total =0;
	
		total = total + (rcaWeeks.getCrQa());
	return total;
}

public int weeklyCRCountForAllIssuesInUAT(RcaCount rcaWeeks){

int total =0;

	total = total + ( rcaWeeks.getCrUat());
return total;
}

public int weeklyCRCountForAllIssuesInProd(RcaCount rcaWeeks){

int total =0;

	total = total + ( rcaWeeks.getCrProd());
return total;
}
	
	
	
	
	/**
	 * This function sum all changed and missed require for a project and a week
	 * @param rcaWeeks
	 * @return
	 */
	public int weeklyMissedAndCRCountForAllIssuesInOpen(RcaCount rcaWeeks){
		
		int total =0;
		
			total = total + (rcaWeeks.getMrProductBacklog() + rcaWeeks.getCrProductBacklog());
		return total;
	}

	
	public int weeklyClientCodeBugForAllIssues(RcaCount rcaWeeks){
       
		int total =0;
		
			total = total + (((rcaWeeks.getCcbQa()==null)? 0 : rcaWeeks.getCcbQa()) + ((rcaWeeks.getCcbUat() == null)? 0 : rcaWeeks.getCcbUat()) + 
					((rcaWeeks.getCcbProd()==null)? 0 : rcaWeeks.getCcbProd()));
		
		return total;
	}
	
	public int weeklyClientCodeBugForAllIssuesInQA(RcaCount rcaWeeks){
	       
		int total =0;
		
			total = total + rcaWeeks.getCcbQa();
		
		return total;
	}
	
	public int weeklyClientCodeBugForAllIssuesInUAT(RcaCount rcaWeeks){
	       
		int total =0;
		
			total = total + rcaWeeks.getCcbUat();
		
		return total;
	}
	
	public int weeklyClientCodeBugForAllIssuesInProd(RcaCount rcaWeeks){
	       
		int total =0;
		
			total = total + rcaWeeks.getCcbProd();
		
		return total;
	}
	/**
	 * This method rerurn sum of client code defect for a week and a project from open category
	 * @param rcaWeeks
	 * @return
	 */
	public int weeklyClientCodeBugForAllIssuesInOpen(RcaCount rcaWeeks){
	       
		int total =0;
		
			total = total + rcaWeeks.getCcbProductBacklog();
		
		return total;
	}
		
	
	/**
	 * Method to return all environments Product Defect count for a selected week.
	 * @param rcaWeeks
	 * @return
	 */
	public int weeklyProductDefectForAllIssues(RcaCount rcaWeeks){
	       
		int total =0;
		
			total = total + (rcaWeeks.getPdQa() + rcaWeeks.getPdUat() + rcaWeeks.getPdProd());
		
		return total;
	}
	
	/**
	 * Method to return QA Product Defect count for a selected week.
	 * @param rcaWeeks
	 * @return
	 */
	public int weeklyProductDefectForAllIssuesInQA(RcaCount rcaWeeks){
	       
		int total =0;
		
			total = total + rcaWeeks.getPdQa();
		
		return total;
	}
	
	/**
	 * Method to return UAT Product Defect count for a selected week.
	 * @param rcaWeeks
	 * @return
	 */
	public int weeklyProductDefectForAllIssuesInUAT(RcaCount rcaWeeks){
	       
		int total =0;
		
			total = total + rcaWeeks.getPdUat();
		
		return total;
	}
	
	/**
	 * Method to return Production environment Product Defect count for a selected week.
	 * @param rcaWeeks
	 * @return
	 */
	public int weeklyProductDefectForAllIssuesInProd(RcaCount rcaWeeks){
	       
		int total =0;
		
			total = total + rcaWeeks.getPdProd();
		
		return total;
	}
	
	/**
	 * Method to return open Product Defect count for a selected week.
	 * @param rcaWeeks
	 * @return
	 */
	public int weeklyProductDefectForAllIssuesInOpen(RcaCount rcaWeeks){
	       
		int total =0;
		
			total = total + rcaWeeks.getPdProductBacklog();
		
		return total;
	}
	
	public int weeklyTotalBugCountForAllProjects(RcaCount rcaCount){
		int total =0;
		
		total = total + (        rcaCount.getAcProd() + rcaCount.getAcQa() + rcaCount.getAcUat() +
		         rcaCount.getAdProd() + rcaCount.getAdQa() + rcaCount.getAdUat() +
		         rcaCount.getBsiProd() + rcaCount.getBsiQa() + rcaCount.getBsiUat() +
		         rcaCount.getCcbProd() + rcaCount.getCcbQa() + rcaCount.getCcbUat() +
		        // rcaCount.getConfigProd() + rcaCount.getConfigQa() + rcaCount.getConfigUat() +
		         rcaCount.getCoProd() + rcaCount.getCoQa() + rcaCount.getCoUat() +
		         rcaCount.getCrmesbProd() + rcaCount.getCrmesbQa() + rcaCount.getCrmesbUat() +
		         rcaCount.getCrProd() + rcaCount.getCrQa() + rcaCount.getCrUat() +
		         rcaCount.getDiProd() + rcaCount.getDiQa() + rcaCount.getDiUat() +
		         rcaCount.getDpProd() + rcaCount.getDpQa() + rcaCount.getDpUat() +
		         rcaCount.getDupProd() + rcaCount.getDupQa() + rcaCount.getDupUat() +
		         rcaCount.getEnvProd() + rcaCount.getEnvQa() + rcaCount.getEnvUat() +
		         rcaCount.getFfmProd() + rcaCount.getFfmQa() + rcaCount.getFfmUat() +
		        // rcaCount.getIiProd() + rcaCount.getIiQa() + rcaCount.getIiUat() +
		         rcaCount.getIoProd() + rcaCount.getIoQa() + rcaCount.getIoUat() +
		         rcaCount.getMrProd() + rcaCount.getMrQa() + rcaCount.getMrUat() +
		         rcaCount.getNadProd() + rcaCount.getNadQa() + rcaCount.getNadUat() +
		         rcaCount.getOtpProd() + rcaCount.getOtpQa() + rcaCount.getOtpUat() +
		         rcaCount.getPdProd() + rcaCount.getPdQa() + rcaCount.getPdUat() +
		         rcaCount.getPlanProd() + rcaCount.getPlanQa() + rcaCount.getPlanUat() +
		         rcaCount.getPmuuProd() + rcaCount.getPmuuQa() + rcaCount.getPmuuUat() +
		         rcaCount.getRateProd() + rcaCount.getRateQa() + rcaCount.getRateUat() +
		        // rcaCount.getRoProd() + rcaCount.getRoQa() + rcaCount.getRoUat() +
		         rcaCount.getRpaProd() + rcaCount.getRpaQa() + rcaCount.getRpaUat() +
		         rcaCount.getTiProd() + rcaCount.getTiQa() + rcaCount.getTiUat() +
		         rcaCount.getUtrProd() + rcaCount.getUtrQa() + rcaCount.getUtrUat());
		
		return total;
	}
	
	public int weeklyBugCountForAllProjectsInProduction(RcaCount rcaCount){
		int total =0;
		
		total = total + ( rcaCount.getAcProd() + rcaCount.getAdProd() + rcaCount.getBsiProd() + rcaCount.getCcbProd() + rcaCount.getCoProd() + 
				          rcaCount.getCrmesbProd() + rcaCount.getCrProd() + rcaCount.getDiProd() + rcaCount.getDpProd() + rcaCount.getDupProd() + 
				          rcaCount.getEnvProd() + rcaCount.getFfmProd() + rcaCount.getIoProd() + rcaCount.getMrProd() + rcaCount.getNadProd() + 
				          rcaCount.getOtpProd() + rcaCount.getPdProd() + rcaCount.getPlanProd() + rcaCount.getPmuuProd() + rcaCount.getRateProd() + 
				          rcaCount.getRpaProd() + rcaCount.getTiProd() + rcaCount.getUtrProd());
		
		return total;
	}
	
	public int weeklyBugCountForAllProjectsInUAT(RcaCount rcaCount){
		int total =0;
		
		total = total + ( rcaCount.getAcUat() + rcaCount.getAdUat() + rcaCount.getBsiUat() + rcaCount.getCcbUat() + rcaCount.getCoUat() + 
				          rcaCount.getCrmesbUat() + rcaCount.getCrUat() + rcaCount.getDiUat() + rcaCount.getDpUat() + rcaCount.getDupUat() + 
				          rcaCount.getEnvUat() + rcaCount.getFfmUat() + rcaCount.getIoUat() + rcaCount.getMrUat() + rcaCount.getNadUat() + 
				          rcaCount.getOtpUat() + rcaCount.getPdUat() + rcaCount.getPlanUat() + rcaCount.getPmuuUat() + rcaCount.getRateUat() + 
				          rcaCount.getRpaUat() + rcaCount.getTiUat() + rcaCount.getUtrUat());
		
		return total;
	}
	
	/**
	 * Method which returns the total bug count for all projects in QA environment.
	 * @param rcaCount
	 * @return
	 */
	public int weeklyBugCountForAllProjectsInQA(RcaCount rcaCount){
		int total =0;
		
		total = total + ( rcaCount.getAcQa() + rcaCount.getAdQa() + rcaCount.getBsiQa() + rcaCount.getCcbQa() + rcaCount.getCoQa() + 
				          rcaCount.getCrmesbQa() + rcaCount.getCrQa() + rcaCount.getDiQa() + rcaCount.getDpQa() + rcaCount.getDupQa() + 
				          rcaCount.getEnvQa() + rcaCount.getFfmQa() + rcaCount.getIoQa() + rcaCount.getMrQa() + rcaCount.getNadQa() + 
				          rcaCount.getOtpQa() + rcaCount.getPdQa() + rcaCount.getPlanQa() + rcaCount.getPmuuQa() + rcaCount.getRateQa() + 
				          rcaCount.getRpaQa() + rcaCount.getTiQa() + rcaCount.getUtrQa());
		
		return total;
	}
	
	
	
	public List<Map<String, Integer>> reportedQAAllWeeksGraphForAllProject(List<RcaCount> rcaCount, List<String> allWeeks){
		
		log.info("Enter reportedQAAllWeeksGraphForAllProject");
		List<Map<String, Integer>> lst = new ArrayList<Map<String, Integer>>();
		
		for(int i=0; i < allWeeks.size(); i++)
		{
			int total =0;
			Map<String, Integer> week_count = new LinkedHashMap<String, Integer>();
			String week = allWeeks.get(i);
			week = removeYearFromWeek(week);
			for(int x=0; x < rcaCount.size(); x++)
			{
				if(rcaCount.get(x).getWeek().equalsIgnoreCase(allWeeks.get(i)))
				{
					RcaCount rcaCountData = rcaCount.get(x);
					total = total + (
							rcaCountData.getAdQa() + // As Desgin 
							rcaCountData.getBsiQa() + // Browser Specific
							rcaCountData.getCcbQa() + //Client Code
							rcaCountData.getCrQa() + //Change Req
							rcaCountData.getDiQa() + //Data Issue
							rcaCountData.getDupQa() +  //Duplicate
							rcaCountData.getMrQa() + //Missed Requirement
							rcaCountData.getNadQa() +  //Not a defect
							rcaCountData.getPdQa() + //Product defec
							rcaCountData.getUtrQa()); //Unable to reproduce
							//Integration Issues
							if (rcaCountData.getIiQa() != null && rcaCountData.getIiQa() == 0){
								total = total + rcaCountData.getCrmesbQa() + rcaCountData.getFfmQa() +
										rcaCountData.getIoQa() + rcaCountData.getOtpQa() +  rcaCountData.getPmuuQa() ;
							}
							else{
								total = total + rcaCountData.getIiQa();
							}
							// Configuration Issues
							if (rcaCountData.getConfigProd() != null && rcaCountData.getConfigProd() == 0){
								total = total + rcaCountData.getPlanQa() + rcaCountData.getAcQa() +  rcaCountData.getRateQa() +
										rcaCountData.getRpaQa() + rcaCountData.getEnvQa() + rcaCountData.getDpQa() +
										rcaCountData.getCoQa() + rcaCountData.getTiQa();
							}else {
								total = total + rcaCountData.getConfigQa();
							}	
							log.info(" QA Defect Count Project Name: "+ rcaCountData.getProjectDetails().getProjectName() + " Week: " + week + " Week Count: " + total);
				}
				
			}
			
			week_count.put(week, total);
			
			lst.add(week_count);
		}
		log.info("Exit reportedProdAllWeeksGraphForAllProject");
		 return lst;
	}


	/**
	 * This API creates the list of client code bug per week for given environment
	 * 
	 * @param rcaCount
	 * @param allWeeks
	 * @param env
	 * @return
	 */
	public List<Map<String, Integer>> reportedAllWeeksCCBGraphForAllProject(
			List<RcaCount> rcaCount, List<String> allWeeks, String env) {
		List<Map<String, Integer>> lst = new ArrayList<Map<String, Integer>>();
		for (int i = 0; i < allWeeks.size(); i++) {
			int total = 0;
			Map<String, Integer> week_count = new LinkedHashMap<String, Integer>();
			String week = allWeeks.get(i);
			week = removeYearFromWeek(week);
			for (int x = 0; x < rcaCount.size(); x++) {
				if (rcaCount.get(x).getWeek().equalsIgnoreCase(allWeeks.get(i))) {
					if(PRODUCTION.equals(env))
						total = total + (rcaCount.get(x).getCcbProd());
					else if(QA.equals(env))
						total = total + (rcaCount.get(x).getCcbQa());
					else if(UAT.equals(env))
						total = total + (rcaCount.get(x).getCcbUat());
					else if(CUMULATIVE_OPEN.equals(env))
						total = total + (rcaCount.get(x).getCcbProductBacklog());
				}
			}
			week_count.put(week, total);
			lst.add(week_count);
		}	
		return lst;
	}

	/**
	 * Method to return Production environment bug counts for all Projects and for given set of Weeks.
	 * @param rcaCount
	 * @param allWeeks
	 * @return
	 */
	public List<Map<String, Integer>> reportedProdAllWeeksGraphForAllProject(List<RcaCount> rcaCount, List<String> allWeeks){
		
		log.info("Enter reportedProdAllWeeksGraphForAllProject");
		List<Map<String, Integer>> lst = new ArrayList<Map<String, Integer>>();
		
		for(int i=0; i < allWeeks.size(); i++)
		{
			int total =0;

			Map<String, Integer> week_count = new LinkedHashMap<String, Integer>();
			String week = allWeeks.get(i);
			week = removeYearFromWeek(week);
			for(int x=0; x < rcaCount.size(); x++)
			{
				if(rcaCount.get(x).getWeek().equalsIgnoreCase(allWeeks.get(i)))
				{
					RcaCount rcaCountData = rcaCount.get(x);

					total = total + rcaCountData.getAdProd() + rcaCountData.getBsiProd() + rcaCountData.getCcbProd() + 
					rcaCountData.getCrProd() + rcaCountData.getDiProd() + rcaCountData.getDupProd() +  
					rcaCountData.getMrProd() + rcaCountData.getNadProd() +  rcaCountData.getPdProd() + 
					rcaCountData.getUtrProd() ;

					//Integration Issues
					if (rcaCountData.getIiProd() != null && rcaCountData.getIiProd() == 0){
						total = total + rcaCountData.getCrmesbProd() + rcaCountData.getFfmProd() +
								rcaCountData.getIoProd() + rcaCountData.getOtpProd() +  rcaCountData.getPmuuProd() ;
					}
					else{
						total = total + rcaCountData.getIiProd();
					}
					// Configuration Issues
					if (rcaCountData.getConfigProd() != null && rcaCountData.getConfigProd() == 0){
						total = total + rcaCountData.getPlanProd() + rcaCountData.getAcProd() +  rcaCountData.getRateProd() +
								rcaCountData.getRpaProd() + rcaCountData.getEnvProd() + rcaCountData.getDpProd() +
								rcaCountData.getCoProd() + rcaCountData.getTiProd();
					}else {
						total = total + rcaCountData.getConfigProd();
					}
					log.info(" Prod defect Count Project Name: "+ rcaCountData.getProjectDetails().getProjectName() + " Week: " + week + " Week Count: " + total);
				}
			}
			week_count.put(week, total);
			lst.add(week_count);			
		}
		log.info("Exit reportedProdAllWeeksGraphForAllProject");
		return lst;
	}
	
	/**
	 * Method to return UAT environment bug counts for all Projects and for given set of Weeks.
	 * @param rcaCount
	 * @param allWeeks
	 * @return
	 */
	public List<Map<String, Integer>> reportedUATAllWeeksGraphForAllProject(List<RcaCount> rcaCount, List<String> allWeeks){
		log.info("Enter reportedUATAllWeeksGraphForAllProject");		
		List<Map<String, Integer>> lst = new ArrayList<Map<String, Integer>>();
		
		for(int i=0; i < allWeeks.size(); i++)
		{
			int total =0;

			Map<String, Integer> week_count = new LinkedHashMap<String, Integer>();
			String week = allWeeks.get(i);
			week = removeYearFromWeek(week);
			for(int x=0; x < rcaCount.size(); x++)
			{
				
				if(rcaCount.get(x).getWeek().equalsIgnoreCase(allWeeks.get(i)))
				{
					RcaCount rcaCountData = rcaCount.get(x);
					total = total + (
							rcaCountData.getAdUat() + // As Desgin 
							rcaCountData.getBsiUat() + // Browser Specific
							rcaCountData.getCcbUat() + //Client Code
							rcaCountData.getCrUat() + //Change Req
							rcaCountData.getDiUat() + //Data Issue
							rcaCountData.getDupUat() +  //Duplicate
							rcaCountData.getMrUat() + //Missed Requirement
							rcaCountData.getNadUat() +  //Not a defect
							rcaCountData.getPdUat() + //Product defec
							rcaCountData.getUtrUat()); //Unable to reproduce
							//Integration Issues
							if (rcaCountData.getIiUat() != null && rcaCountData.getIiUat() == 0){
								total = total + rcaCountData.getCrmesbUat() + rcaCountData.getFfmUat() +
										rcaCountData.getIoUat() + rcaCountData.getOtpUat() +  rcaCountData.getPmuuUat() ;
							}
							else{
								total = total + rcaCountData.getIiUat();
							}
							// Configuration Issues
							if (rcaCountData.getConfigProd() != null && rcaCountData.getConfigProd() == 0){
								total = total + rcaCountData.getPlanUat() + rcaCountData.getAcUat() +  rcaCountData.getRateUat() +
										rcaCountData.getRpaUat() + rcaCountData.getEnvUat() + rcaCountData.getDpUat() +
										rcaCountData.getCoUat() + rcaCountData.getTiUat();
							}else {
								total = total + rcaCountData.getConfigUat();
							}	
							log.info(" UAT defect Count Project Name: "+ rcaCountData.getProjectDetails().getProjectName() + " Week: " + week + " Week Count: " + total);
				}
			}
			
			week_count.put(week, total);
			lst.add(week_count);
		}
		log.info("Exit reportedUATAllWeeksGraphForAllProject");
		 return lst;
	}
	
	/**
	 * Method to return total "Cumulative Open" bug counts for all Projects and for given set of Weeks.
	 * @param rcaCount
	 * @param allWeeks
	 * @return
	 */
	public List<Map<String, Integer>> reportedCumulativeOpenAllWeeksGraphForAllProject(List<RcaCount> rcaCount, List<String> allWeeks){

		List<Map<String, Integer>> lst = new ArrayList<Map<String, Integer>>();

		for(int i=0; i < allWeeks.size(); i++)
		{
			int total =0;

			Map<String, Integer> week_count = new LinkedHashMap<String, Integer>();
			String week = allWeeks.get(i);
			week = removeYearFromWeek(week);
			for(int x=0; x < rcaCount.size(); x++)
			{
				if(null != rcaCount.get(x))
				{
					if(rcaCount.get(x).getWeek().equalsIgnoreCase(allWeeks.get(i)))
					{
						RcaCount rcaCountData = rcaCount.get(x);
						total = total + (
								rcaCountData.getAdProductBacklog() + // As Desgin 
								rcaCountData.getBsiProductBacklog() + // Browser Specific
								rcaCountData.getCcbProductBacklog() + //Client Code
								rcaCountData.getCrProductBacklog() + //Change Req
								rcaCountData.getDiProductBacklog() + //Data Issue
								rcaCountData.getDupProductBacklog() +  //Duplicate
								rcaCountData.getMrProductBacklog() + //Missed Requirement
								rcaCountData.getNadProductBacklog() +  //Not a defect
								rcaCountData.getPdProductBacklog() + //Product defect
								rcaCountData.getUtrProductBacklog()); //Unable to reproduce
								//Integration Issues
								if (rcaCountData.getIiProductBacklog() != null && rcaCountData.getIiProductBacklog() == 0){
									total = total + rcaCountData.getCrmesbProductBacklog() + rcaCountData.getFfmProductBacklog() +
											rcaCountData.getIoProductBacklog() + rcaCountData.getOtpProductBacklog() +  rcaCountData.getPmuuProductBacklog() ;
								}
								else{
									total = total + rcaCountData.getIiProductBacklog();
								}
								// Configuration Issues
								if (rcaCountData.getConfigProd() != null && rcaCountData.getConfigProd() == 0){
									total = total + rcaCountData.getPlanProductBacklog() + rcaCountData.getAcProductBacklog() +  rcaCountData.getRateProductBacklog() +
											rcaCountData.getRpaProductBacklog() + rcaCountData.getEnvProductBacklog() + rcaCountData.getDpProductBacklog() +
											rcaCountData.getCoProductBacklog() + rcaCountData.getTiProductBacklog();
								}else {
									total = total + rcaCountData.getConfigProductBacklog();
								}	
								log.info(" Open defect Count Project Name: "+ rcaCountData.getProjectDetails().getProjectName() + " Week: " + week + " Week Count: " + total);
					}
				}
			}

			week_count.put(week, total);
			lst.add(week_count);
		}

		return lst;
	}
	
	public List<String> findWeeks(){
		List<String> weeks = new ArrayList<String>();
		SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");
	    
	    Calendar c1 = Calendar.getInstance();
	  
	    c1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	    c1.add(Calendar.WEEK_OF_MONTH, -1);
	    for(int i=0; i<12; i++)
	    {
	        String startDate = formatter.format(c1.getTime());
	        c1.add(Calendar.DAY_OF_WEEK, +6);
	        
	        String endDate = formatter.format(c1.getTime());
	        String finalRange = startDate +"-"+endDate;
	       
	        weeks.add(finalRange);
	        c1.add(Calendar.DAY_OF_WEEK, -14);
	        
	        c1.add(Calendar.DAY_OF_WEEK, 1);
	    }
	    
	    Collections.reverse(weeks); // This sets the weeks set sequence in chronological order.
	    
	    return weeks;
	}

	/**
	 * This method will generate the reopen bug count by environment type.
	 * 
	 * @param rcaCounts
	 * @return
	 */
	public Map<String, Integer> reopenRCACountByEnvironment(
			List<RcaCount> rcaCounts) {
		Map<String, Integer> reopenCount = new HashMap<String, Integer>();
		int qaCount = 0;
		int uatCount = 0;
		int prodCount = 0;
		for (RcaCount rcaCount : rcaCounts) {
			qaCount += rcaCount.getRoQa();
			uatCount += rcaCount.getRoUat();
			prodCount += rcaCount.getRoProd();
		}
		reopenCount.put(QA, qaCount);
		reopenCount.put(UAT, uatCount);
		reopenCount.put(PRODUCTION, prodCount);
		return reopenCount;
	}

	/**
	 * Generates the last 12 week range list from the given week end range
	 * 
	 * @param week
	 * @return
	 */
	public List<String> findWeeks(String week) {
		List<String> weeks = new ArrayList<String>();
		SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");
		Calendar c1 = Calendar.getInstance();
		try {
			c1.setTime(formatter.parse(week.split("-")[1]));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		c1.add(Calendar.WEEK_OF_MONTH, -1);
		for (int i = 0; i < 12; i++) {
			String startDate = formatter.format(c1.getTime());
			c1.add(Calendar.DAY_OF_WEEK, +6);
			String endDate = formatter.format(c1.getTime());
			String finalRange = startDate + "-" + endDate;
			weeks.add(finalRange);
			c1.add(Calendar.DAY_OF_WEEK, -14);
			c1.add(Calendar.DAY_OF_WEEK, 1);
		}
		Collections.reverse(weeks); // This sets the weeks set sequence in
									// chronological order.
		return weeks;
	}
	
	public String removeYearFromWeek(String week){
		String dates[] = null;
		String dayMonths1[] = null;
		String dayMonths2[] = null;
		dates = week.split("-");	
		String y1=dates[0].substring(0,dates[0].lastIndexOf('/'));
		dayMonths1=y1.split("/");
		if(dayMonths1[0].length()==1){
			dayMonths1[0]="0"+dayMonths1[0];
		}
		if(dayMonths1[1].length()==1){
			dayMonths1[1]="0"+dayMonths1[1];
		}
		y1=dayMonths1[0]+"/"+dayMonths1[1];
	    String y2=dates[1].substring(0,dates[1].lastIndexOf('/'));
	    dayMonths2=y2.split("/");
		if(dayMonths2[0].length()==1){
			dayMonths2[0]="0"+dayMonths2[0];
		}
		if(dayMonths2[1].length()==1){
			dayMonths2[1]="0"+dayMonths2[1];
		}
		y2=dayMonths2[0]+"/"+dayMonths2[1];
		return y1+"-"+y2;
	}
	
	// Below methods are utility methods to generate Summary & Ranking Framework excel sheet
	
	
	public void buildRFColumns(RankingFramework rankingRow, XSSFRow row, XSSFSheet rankingFrameworkSheet, XSSFCellStyle percStyle, XSSFCellStyle styleYellow, XSSFCellStyle styleGreen)
	{
		rankingFrameworkSheet.setFitToPage(true);
		boolean hideColumn = false;

		int cellCounter=0;
		
		
		//	A
		Cell cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellValue(rankingRow.getTeamName());
		cell.setCellStyle(styleGreen);

		//	B
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellValue(rankingRow.getClient());

		//		C
		rankingFrameworkSheet.setColumnHidden(cellCounter, hideColumn);
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellStyle(percStyle);
		cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		cell.setCellValue(rankingRow.getAgileCompliance()/100);

		//		D
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellFormula(rankingRow.getAgileComplianceScore());
		cell.setCellStyle(styleYellow);

		//		E
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellValue(rankingRow.getCcb());

		//		F
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellFormula(rankingRow.getCcbScore());
		cell.setCellStyle(styleYellow);

		//		G
		rankingFrameworkSheet.setColumnHidden(cellCounter, hideColumn);
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellStyle(percStyle);
		cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		cell.setCellValue(rankingRow.getToolCompliance()/100);

		//		H
		rankingFrameworkSheet.setColumnHidden(cellCounter, hideColumn);
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellFormula(rankingRow.getToolComplianceScore());

		//		I
		rankingFrameworkSheet.setColumnHidden(cellCounter, hideColumn);
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellStyle(percStyle);
		cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		cell.setCellValue(rankingRow.getjUnitCov()/100);

		//		J
		rankingFrameworkSheet.setColumnHidden(cellCounter, hideColumn);
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellFormula(rankingRow.getjUnitCovScore());

		//		K
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellValue(rankingRow.getPrevWeek());

		//		L
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellValue(rankingRow.getCurrWeek());

		//		M
		rankingFrameworkSheet.setColumnHidden(cellCounter, hideColumn);
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		rankingFrameworkSheet.setColumnWidth(cellCounter, 6);
		cellCounter++;
		cell.setCellStyle(percStyle);
		cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		cell.setCellFormula(rankingRow.getPerChange());

		//		N
		rankingFrameworkSheet.setColumnHidden(cellCounter, hideColumn);
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellFormula(rankingRow.getAbsoluteChange());

		//		O
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellFormula(rankingRow.getQaDefectScore());
		cell.setCellStyle(styleYellow);

		//		P
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellValue(rankingRow.getReopen());

		//		Q
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellFormula(rankingRow.getReopenScore());
		cell.setCellStyle(styleYellow);

		//		R
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellValue(rankingRow.getCollaboration());

		//		S
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellFormula(rankingRow.getCollaborationScore());
		cell.setCellStyle(styleYellow);

		//		T
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellValue(rankingRow.getCumulativeBacklog());

		//		U
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellFormula(rankingRow.getCumulativeBacklogScore());
		cell.setCellStyle(styleYellow);

		//		V
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellValue(rankingRow.getMissReq());

		//		W
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellFormula(rankingRow.getMissReqScore());
		cell.setCellStyle(styleYellow);

		//		X
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellValue(rankingRow.getRisk());

		//		Y
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellFormula(rankingRow.getRiskScore());
		cell.setCellStyle(styleYellow);

		//		Z
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellFormula(rankingRow.getTotalScore());

		//		AA
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellFormula(rankingRow.getRanking());
		cell.setCellStyle(styleGreen);

		//		AB
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellValue(rankingRow.getActualUsed());
		cell.setCellStyle(styleGreen);

		//		AC
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cell.setCellValue(rankingRow.getRankingComment());
	}

	public List<String> findPreviousTwoWeek() {
		List<String> weeks = new ArrayList<String>();
		SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");

		Calendar c1 = Calendar.getInstance();
		c1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		c1.add(Calendar.WEEK_OF_MONTH, -1);
		for (int i = 0; i < 2; i++) {
			String startDate = formatter.format(c1.getTime());
			c1.add(Calendar.DAY_OF_WEEK, +6);
			String endDate = formatter.format(c1.getTime());
			String finalRange = startDate + "-" + endDate;
			weeks.add(finalRange);
			c1.add(Calendar.DAY_OF_WEEK, -14);
			c1.add(Calendar.DAY_OF_WEEK, 1);
		}
		return weeks;
	}
	
	public void createRFHeaderRows(XSSFSheet rankingFrameworkSheet, XSSFWorkbook toolSetMatrix)
	{
		XSSFRow headerRow = rankingFrameworkSheet.createRow(0);
		String header = "Action Team Name, Client, Agile Compliance, Score,	Client code defects(PROD+UAT), Score, Tool Compliance, Score, Junit Test coverage, Score, Previous Week, Current Week, % Change, Absolute Change, Score, Re-open, Score, Collaboration, Score, Cumulative Backlog, Score, Missed requirements, Score, Risk, Score, Total score, Ranking, Actual Used, Ranking Comment";
		String[] headerCells = header.split(",");
		int columnIndex = 0;
		XSSFCellStyle headerStyleBlue = toolSetMatrix.createCellStyle();
		XSSFCellStyle headerStyleYellow = toolSetMatrix.createCellStyle();
		buildHeaderStyle(headerStyleBlue, CellStyle.BORDER_THIN, GREY, BLUE, (short) 90);
		buildHeaderStyle(headerStyleYellow, CellStyle.BORDER_THIN, GREY, YELLOW, (short) 90);
		for (String headerCell : headerCells)
		{
			Cell cell = headerRow.createCell(columnIndex++);
			cell.setCellValue(headerCell);
			if(headerCell.equalsIgnoreCase(" Collaboration") || headerCell.equalsIgnoreCase(" Risk")){
				cell.setCellStyle(headerStyleYellow);
			}else{
				cell.setCellStyle(headerStyleBlue);
			}
		}
	}

	private void buildHeaderStyle(XSSFCellStyle headerStyle, short borderThickness, XSSFColor borderColor, XSSFColor foreGroundColor, short rotation){
		headerStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		headerStyle.setFillForegroundColor(foreGroundColor);
		headerStyle.setRotation(rotation);
		headerStyle.setShrinkToFit(true);
		headerStyle.setWrapText(true);
		headerStyle.setBorderBottom(borderThickness);
        headerStyle.setBottomBorderColor(borderColor);
        headerStyle.setBorderLeft(borderThickness);
        headerStyle.setLeftBorderColor(borderColor);
        headerStyle.setBorderRight(borderThickness);
        headerStyle.setRightBorderColor(borderColor);
        headerStyle.setBorderTop(borderThickness);
        headerStyle.setTopBorderColor(borderColor);
	}
	
	public void createSummaryHeaderRows(XSSFSheet rankingFrameworkSheet, XSSFWorkbook toolSetMatrix)
	{
		XSSFRow headerRow = rankingFrameworkSheet.createRow(0);
		String header = "S.No, Action Team Name, Client, Geb/Spock, Prod, UAT, QA, Open, Team Ranking";
		String[] headerCells = header.split(",");
		XSSFCellStyle headerStyle = toolSetMatrix.createCellStyle();
		buildHeaderStyle(headerStyle, CellStyle.BORDER_THIN, GREY, BLUE, (short) 0);
		int columnIndex = 0;
		for (String headerCell : headerCells)
		{
			Cell cell = headerRow.createCell(columnIndex++);
			cell.setCellValue(headerCell);
			cell.setCellStyle(headerStyle);
		}
	}

	private Cell createCell(XSSFRow row, int cellCounter, XSSFSheet rankingFrameworkSheet)
	{
		rankingFrameworkSheet.autoSizeColumn(cellCounter);
		Cell cell = row.createCell(cellCounter);
		return cell;
	}
}
