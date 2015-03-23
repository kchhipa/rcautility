package com.rca.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rca.entity.RcaCount;

public class ReportUtility {
	
	public static final String PRODUCTION = "PROD";
	public static final String UAT = "UAT";
	public static final String QA = "QA";
	

	public Map<String, Map<String, Integer>> rcaCountForLastWeekForAllProjects(List<RcaCount> rcaWeeks){
	  Map<String, Map<String, Integer>> diffCategory = new HashMap<String, Map<String, Integer>>();;
    
    for(int x=0; x < rcaWeeks.size(); x++){
          
          String projName = rcaWeeks.get(x).getProjectDetails().getProjectName();
          
          RcaCount rcaCount = rcaWeeks.get(x);
          
          //diffCategory = new HashMap<String, Map<String, Integer>>();
          
          Map<String, Integer> differentRootCause = new HashMap<String, Integer>();
          differentRootCause.put("Duplicate/ Not a Defect/ Unable to reproduce/ Browse/ As designed", 
                                     mixCategoryWeeklyCountForAllProjects(rcaCount));
          differentRootCause.put("Data Issue", weeklyDataIssueForAllIssues(rcaCount));
          differentRootCause.put("Integration Issue", weeklyIntegrationIssueForAllIssues(rcaCount));
          differentRootCause.put("Configuration Issue", weeklyConfigurationIssueForAllIssues(rcaCount));
          differentRootCause.put("Missed/ Change Requirement", weeklyMissedAndCRCountForAllIssues(rcaCount));
          differentRootCause.put("Client Code Bug", weeklyClientCodeBugForAllIssues(rcaCount));
          
          /* To ensure that Projects with RCA count as zero do not show up in Graph */
          if((mixCategoryWeeklyCountForAllProjects(rcaCount) + weeklyDataIssueForAllIssues(rcaCount) + weeklyIntegrationIssueForAllIssues(rcaCount) + 
        		  weeklyConfigurationIssueForAllIssues(rcaCount) + weeklyMissedAndCRCountForAllIssues(rcaCount) + weeklyClientCodeBugForAllIssues(rcaCount))>0)
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
					weeklyMissedAndCRCountForAllIssuesInQA(rcaCount) + weeklyClientCodeBugForAllIssuesInQA(rcaCount);
			
			
			if(totalCount > 0){
				Map<String, Integer> differentRootCause = new LinkedHashMap<String, Integer>();
				
				differentRootCause.put("Duplicate/ Not a Defect/ Unable to reproduce/ Browse/ As designed", mixCategoryWeeklyCountForAllProjectsInQA(rcaCount));
	            differentRootCause.put("Data Issue", weeklyDataIssueForAllIssuesInQA(rcaCount));
	            differentRootCause.put("Integration Issue", weeklyIntegrationIssueForAllIssuesInQA(rcaCount));
	            differentRootCause.put("Configuration Issue", weeklyConfigurationIssueForAllIssuesInQA(rcaCount));
	            differentRootCause.put("Missed/ Change Requirement", weeklyMissedAndCRCountForAllIssuesInQA(rcaCount));
	            differentRootCause.put("Client Code Bug", weeklyClientCodeBugForAllIssuesInQA(rcaCount));
	
	            diffCategory.put(projName, differentRootCause);
				
			}
			
		}
		
		return diffCategory;
		
	}
	
	public Map<String, Map<String, Integer>> reportedQAAllWeeksGraphForIndividual(List<RcaCount> rcaCounts, List<String> allWeeks){
		
		Map<String, Map<String, Integer>> diffCategory = new LinkedHashMap<String, Map<String, Integer>>();
		
		for(int x=0; x < rcaCounts.size(); x++){
			
			RcaCount rcaCount = rcaCounts.get(x);
			int totalCount = mixCategoryWeeklyCountForAllProjectsInQA(rcaCount) + weeklyDataIssueForAllIssuesInQA(rcaCount) +
					weeklyIntegrationIssueForAllIssuesInQA(rcaCount) + weeklyConfigurationIssueForAllIssuesInQA(rcaCount) +
					weeklyMissedAndCRCountForAllIssuesInQA(rcaCount) + weeklyClientCodeBugForAllIssuesInQA(rcaCount);
			
			if(totalCount > 0){
				Map<String, Integer> differentRootCause = new LinkedHashMap<String, Integer>();
				
				differentRootCause.put("Duplicate/ Not a Defect/ Unable to reproduce/ Browse/ As designed", mixCategoryWeeklyCountForAllProjectsInQA(rcaCount));
	            differentRootCause.put("Data Issue", weeklyDataIssueForAllIssuesInQA(rcaCount));
	            differentRootCause.put("Integration Issue", weeklyIntegrationIssueForAllIssuesInQA(rcaCount));
	            differentRootCause.put("Configuration Issue", weeklyConfigurationIssueForAllIssuesInQA(rcaCount));
	            differentRootCause.put("Missed/ Change Requirement", weeklyMissedAndCRCountForAllIssuesInQA(rcaCount));
	            differentRootCause.put("Client Code Bug", weeklyClientCodeBugForAllIssuesInQA(rcaCount));
	
	            diffCategory.put(rcaCounts.get(x).getWeek(), differentRootCause);
				
			}
		}
		
		return diffCategory;
	}
	
	public Map<String, Map<String, Integer>> reportedUATRCAForAllProjects(List<RcaCount> rcaCounts){

		Map<String, Map<String, Integer>> diffCategory = new LinkedHashMap<String, Map<String, Integer>>();;
		
		for(int x=0; x < rcaCounts.size(); x++){
			
			String projName = rcaCounts.get(x).getProjectDetails().getProjectName();
			RcaCount rcaCount = rcaCounts.get(x);
			
			int totalCount = mixCategoryWeeklyCountForAllProjectsInUAT(rcaCount) + weeklyDataIssueForAllIssuesInUAT(rcaCount) +
					weeklyIntegrationIssueForAllIssuesInUAT(rcaCount) + weeklyConfigurationIssueForAllIssuesInUAT(rcaCount) +
					weeklyMissedAndCRCountForAllIssuesInUAT(rcaCount) + weeklyClientCodeBugForAllIssuesInUAT(rcaCount);
			
			
			if(totalCount > 0){
				Map<String, Integer> differentRootCause = new LinkedHashMap<String, Integer>();
				
				differentRootCause.put("Duplicate/ Not a Defect/ Unable to reproduce/ Browse/ As designed", mixCategoryWeeklyCountForAllProjectsInUAT(rcaCount));
	            differentRootCause.put("Data Issue", weeklyDataIssueForAllIssuesInUAT(rcaCount));
	            differentRootCause.put("Integration Issue", weeklyIntegrationIssueForAllIssuesInUAT(rcaCount));
	            differentRootCause.put("Configuration Issue", weeklyConfigurationIssueForAllIssuesInUAT(rcaCount));
	            differentRootCause.put("Missed/ Change Requirement", weeklyMissedAndCRCountForAllIssuesInUAT(rcaCount));
	            differentRootCause.put("Client Code Bug", weeklyClientCodeBugForAllIssuesInUAT(rcaCount));
	
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
					weeklyMissedAndCRCountForAllIssuesInProd(rcaCount) + weeklyClientCodeBugForAllIssuesInProd(rcaCount);
			
			
			if(totalCount > 0){
				Map<String, Integer> differentRootCause = new LinkedHashMap<String, Integer>();
				
				differentRootCause.put("Duplicate/ Not a Defect/ Unable to reproduce/ Browse/ As designed", mixCategoryWeeklyCountForAllProjectsInProd(rcaCount));
	            differentRootCause.put("Data Issue", weeklyDataIssueForAllIssuesInProd(rcaCount));
	            differentRootCause.put("Integration Issue", weeklyIntegrationIssueForAllIssuesInProd(rcaCount));
	            differentRootCause.put("Configuration Issue", weeklyConfigurationIssueForAllIssuesInProd(rcaCount));
	            differentRootCause.put("Missed/ Change Requirement", weeklyMissedAndCRCountForAllIssuesInProd(rcaCount));
	            differentRootCause.put("Client Code Bug", weeklyClientCodeBugForAllIssuesInProd(rcaCount));
	
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
			String projName = rcaCounts.get(x).getProjectDetails()
					.getProjectName();
			RcaCount rcaCount = rcaCounts.get(x);
			int totalCount = rcaCount.getRoQa() + rcaCount.getRoUat()
					+ rcaCount.getRoProd();

			if (totalCount > 0) {
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
	
	
	
	public List reportedQAAllWeeksGraphForAllProject(List<RcaCount> rcaCount, List<String> allWeeks){
		
		//Map<String, Integer> week_count = new LinkedHashMap<String, Integer>();
		
		List lst = new ArrayList();
		
		for(int i=0; i < allWeeks.size(); i++)
		{
			int total =0;

			Map<String, Integer> week_count = new LinkedHashMap<String, Integer>();
			String week = allWeeks.get(i);
			for(int x=0; x < rcaCount.size(); x++)
			{
				
				if(rcaCount.get(x).getWeek().equalsIgnoreCase(allWeeks.get(i)))
				{
					
					/*total = total + ( rcaCount.get(x).getAcQa() +
					         rcaCount.get(x).getAdQa() + rcaCount.get(x).getBsiQa() + rcaCount.get(x).getCcbQa() + rcaCount.get(x).getCoQa() + 
					         rcaCount.get(x).getCrmesbQa() + rcaCount.get(x).getCrQa() + rcaCount.get(x).getDiQa() + rcaCount.get(x).getDpQa() + 
					         rcaCount.get(x).getDupQa() + rcaCount.get(x).getEnvQa() + rcaCount.get(x).getFfmQa() + rcaCount.get(x).getIoQa() + 
					         rcaCount.get(x).getMrQa() + rcaCount.get(x).getNadQa() + rcaCount.get(x).getOtpQa() + rcaCount.get(x).getPdQa() + 
					         rcaCount.get(x).getPlanQa() + rcaCount.get(x).getPmuuQa() + rcaCount.get(x).getRateQa() + rcaCount.get(x).getRpaQa() + 
					         rcaCount.get(x).getTiQa() + rcaCount.get(x).getUtrQa());*/
					
					total = total + (
					         rcaCount.get(x).getAdQa() + rcaCount.get(x).getBsiQa() + rcaCount.get(x).getCcbQa() + 
					         rcaCount.get(x).getCrQa() + rcaCount.get(x).getDiQa() + 
					         rcaCount.get(x).getDupQa() +  
					         rcaCount.get(x).getMrQa() + rcaCount.get(x).getNadQa() +  rcaCount.get(x).getPdQa() + 
					          
					        rcaCount.get(x).getUtrQa());

				}
				
			}
			
			week_count.put(week, total);
			
			lst.add(week_count);
		}
		

		 
		 
		 return lst;
	}


	/**
	 * This API creates the list of Production client code bug per week
	 * 
	 * @param rcaCount
	 * @param allWeeks
	 * @return
	 */
	public List reportedAllWeeksCCBGraphForAllProject(
			List<RcaCount> rcaCount, List<String> allWeeks, String env) {
		List lst = new ArrayList();
		for (int i = 0; i < allWeeks.size(); i++) {
			int total = 0;
			Map<String, Integer> week_count = new LinkedHashMap<String, Integer>();
			String week = allWeeks.get(i);
			for (int x = 0; x < rcaCount.size(); x++) {
				if (rcaCount.get(x).getWeek().equalsIgnoreCase(allWeeks.get(i))) {
					if(PRODUCTION.equals(env))
						total = total + (rcaCount.get(x).getCcbProd());
					else if(QA.equals(env))
						total = total + (rcaCount.get(x).getCcbQa());
					else if(UAT.equals(env))
						total = total + (rcaCount.get(x).getCcbUat());
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
	public List reportedProdAllWeeksGraphForAllProject(List<RcaCount> rcaCount, List<String> allWeeks){
		
		List lst = new ArrayList();
		
		for(int i=0; i < allWeeks.size(); i++)
		{
			int total =0;

			Map<String, Integer> week_count = new LinkedHashMap<String, Integer>();
			String week = allWeeks.get(i);
			for(int x=0; x < rcaCount.size(); x++)
			{
				
				if(rcaCount.get(x).getWeek().equalsIgnoreCase(allWeeks.get(i)))
				{
					total = total + (
					         rcaCount.get(x).getAdProd() + rcaCount.get(x).getBsiProd() + rcaCount.get(x).getCcbProd() + 
					         rcaCount.get(x).getCrProd() + rcaCount.get(x).getDiProd() + 
					         rcaCount.get(x).getDupProd() +  
					         rcaCount.get(x).getMrProd() + rcaCount.get(x).getNadProd() +  rcaCount.get(x).getPdProd() + 
					        rcaCount.get(x).getUtrProd());
				}
			}
			
			week_count.put(week, total);
			lst.add(week_count);
		}
		
		 return lst;
	}
	
	/**
	 * Method to return UAT environment bug counts for all Projects and for given set of Weeks.
	 * @param rcaCount
	 * @param allWeeks
	 * @return
	 */
	public List reportedUATAllWeeksGraphForAllProject(List<RcaCount> rcaCount, List<String> allWeeks){
				
		List lst = new ArrayList();
		
		for(int i=0; i < allWeeks.size(); i++)
		{
			int total =0;

			Map<String, Integer> week_count = new LinkedHashMap<String, Integer>();
			String week = allWeeks.get(i);
			for(int x=0; x < rcaCount.size(); x++)
			{
				
				if(rcaCount.get(x).getWeek().equalsIgnoreCase(allWeeks.get(i)))
				{
					total = total + (
					         rcaCount.get(x).getAdUat() + rcaCount.get(x).getBsiUat() + rcaCount.get(x).getCcbUat() + 
					         rcaCount.get(x).getCrUat() + rcaCount.get(x).getDiUat() + 
					         rcaCount.get(x).getDupUat() +  
					         rcaCount.get(x).getMrUat() + rcaCount.get(x).getNadUat() +  rcaCount.get(x).getPdUat() + 
					        rcaCount.get(x).getUtrUat());
				}
			}
			
			week_count.put(week, total);
			lst.add(week_count);
		}
		
		 return lst;
	}
	
	/**
	 * Method to return total "Cumulative Open" bug counts for all Projects and for given set of Weeks.
	 * @param rcaCount
	 * @param allWeeks
	 * @return
	 */
	public List reportedCumulativeOpenAllWeeksGraphForAllProject(List<RcaCount> rcaCount, List<String> allWeeks){

		List lst = new ArrayList();

		for(int i=0; i < allWeeks.size(); i++)
		{
			int total =0;

			Map<String, Integer> week_count = new LinkedHashMap<String, Integer>();
			String week = allWeeks.get(i);
			for(int x=0; x < rcaCount.size(); x++)
			{

				if(rcaCount.get(x).getWeek().equalsIgnoreCase(allWeeks.get(i)))
				{
					total = total + (
							((rcaCount.get(x).getAcProductBacklog()==null)? 0 : rcaCount.get(x).getAcProductBacklog()) + 
							((rcaCount.get(x).getAdProductBacklog()==null)? 0 : rcaCount.get(x).getAdProductBacklog()) + 
							((rcaCount.get(x).getBsiProductBacklog()==null)? 0 : rcaCount.get(x).getBsiProductBacklog()) + 
							((rcaCount.get(x).getCcbProductBacklog()==null)? 0 : rcaCount.get(x).getCcbProductBacklog()) + 
							((rcaCount.get(x).getConfigProductBacklog()==null)? 0 : rcaCount.get(x).getConfigProductBacklog()) + 
							((rcaCount.get(x).getCoProductBacklog()==null)? 0 : rcaCount.get(x).getCoProductBacklog()) +  
							((rcaCount.get(x).getCrmesbProductBacklog()==null)? 0 : rcaCount.get(x).getCrmesbProductBacklog()) + 
							((rcaCount.get(x).getCrProductBacklog()==null)? 0 : rcaCount.get(x).getCrProductBacklog()) +  
							((rcaCount.get(x).getDiProductBacklog()==null)? 0 : rcaCount.get(x).getDiProductBacklog()) + 
							((rcaCount.get(x).getDpProductBacklog()==null)? 0 : rcaCount.get(x).getDpProductBacklog()) + 
							((rcaCount.get(x).getDupProductBacklog()==null)? 0 : rcaCount.get(x).getDupProductBacklog()) + 
							((rcaCount.get(x).getEnvProductBacklog()==null)? 0 : rcaCount.get(x).getEnvProductBacklog()) +  
							((rcaCount.get(x).getFfmProductBacklog()==null)? 0 : rcaCount.get(x).getFfmProductBacklog()) + 
							((rcaCount.get(x).getIiProductBacklog()==null)? 0 : rcaCount.get(x).getIiProductBacklog()) + 
							((rcaCount.get(x).getIoProductBacklog()==null)? 0 : rcaCount.get(x).getIoProductBacklog()) + 
							((rcaCount.get(x).getMrProductBacklog()==null)? 0 : rcaCount.get(x).getMrProductBacklog()) + 
							((rcaCount.get(x).getNadProductBacklog()==null)? 0 : rcaCount.get(x).getNadProductBacklog()) + 
							((rcaCount.get(x).getOtpProductBacklog()==null)? 0 : rcaCount.get(x).getOtpProductBacklog()) + 
							((rcaCount.get(x).getPdProductBacklog()==null)? 0 : rcaCount.get(x).getPdProductBacklog()) + 
							((rcaCount.get(x).getPlanProductBacklog()==null)? 0 : rcaCount.get(x).getPlanProductBacklog()) + 
							((rcaCount.get(x).getPmuuProductBacklog()==null)? 0 : rcaCount.get(x).getPmuuProductBacklog()) + 
							((rcaCount.get(x).getRateProductBacklog()==null)? 0 : rcaCount.get(x).getRateProductBacklog()) + 
							((rcaCount.get(x).getRpaProductBacklog()==null)? 0 : rcaCount.get(x).getRpaProductBacklog()) + 
							((rcaCount.get(x).getTiProductBacklog()==null)? 0 : rcaCount.get(x).getTiProductBacklog()) + 
							((rcaCount.get(x).getUtrProductBacklog()==null)? 0 : rcaCount.get(x).getUtrProductBacklog()));
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
	

}
