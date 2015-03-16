package com.rca.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.rca.entity.RcaCount;

public class ReportUtility {
	
	

	public Map<String, Map<String, Integer>> rcaCountForLastWeekForAllProjects(List<RcaCount> rcaWeeks){
	  Map<String, Map<String, Integer>> diffCategory = new HashMap<String, Map<String, Integer>>();;
    
    for(int x=0; x < rcaWeeks.size(); x++){
          
          String projName = rcaWeeks.get(x).getProjectDetails().getProjectName();
          
          //diffCategory = new HashMap<String, Map<String, Integer>>();
          
          Map<String, Integer> differentRootCause = new HashMap<String, Integer>();
          differentRootCause.put("Duplicate/ Not a Defect/ Unable to reproduce/ Browse/ As designed", 
                                     mixCategoryWeeklyCountForAllProjects(rcaWeeks));
          differentRootCause.put("Data Issue", weeklyDataIssueForAllIssues(rcaWeeks));
          differentRootCause.put("Integration Issue", weeklyIntegrationIssueForAllIssues(rcaWeeks));
          differentRootCause.put("Configuration Issue", weeklyConfigurationIssueForAllIssues(rcaWeeks));
          differentRootCause.put("Missed/ Change Requirement", weeklyMissedAndCRCountForAllIssues(rcaWeeks));
          differentRootCause.put("Client Code Bug", weeklyClientCodeBugForAllIssues(rcaWeeks));
          
          diffCategory.put(projName, differentRootCause);
          
          //projCount.put(projName, diffCategory);
    }
    
    return diffCategory;

	}
	
	public Map<String, Map<String, List>> rcaCountForMultipleWeeksForAllProjects(List<RcaCount> rcaWeeks){
		Map<String, Map<String, List>> projCount = new HashMap<String, Map<String, List>>(); 
		Map<String, List> diffCategory = null;
		
		for(int x=0; x < rcaWeeks.size(); x++){
			
			String projName = rcaWeeks.get(x).getProjectDetails().getProjectName();
			
			// projCount = new HashMap<String, Map<String, List>>();
			
			diffCategory = new HashMap<String, List>();
			
			List<Integer> differentRootCause = new ArrayList<Integer>();
			differentRootCause.add(mixCategoryWeeklyCountForAllProjects(rcaWeeks));
			differentRootCause.add(weeklyDataIssueForAllIssues(rcaWeeks));
			differentRootCause.add(weeklyIntegrationIssueForAllIssues(rcaWeeks));
			differentRootCause.add(weeklyConfigurationIssueForAllIssues(rcaWeeks));
			differentRootCause.add(weeklyMissedAndCRCountForAllIssues(rcaWeeks));
			differentRootCause.add(weeklyClientCodeBugForAllIssues(rcaWeeks));
			
			diffCategory.put(projName, differentRootCause);
			
			/*totalQA = totalQA + rcaWeeks.get(x).getCcbQa();
			totalUAT = totalUAT + rcaWeeks.get(x).getCcbUat();
			totalPROD = totalPROD + rcaWeeks.get(x).getCcbProd();*/
			
			
			projCount.put(projName, diffCategory);
		}
		
		
		return projCount;
		
	}

	
	public int mixCategoryWeeklyCountForAllProjects(List<RcaCount> rcaWeeks){
		int total=0;
		for(int x=0; x < rcaWeeks.size(); x++){
			total = total + (rcaWeeks.get(x).getDupQa() + rcaWeeks.get(x).getDupUat() + rcaWeeks.get(x).getDupProd()+ rcaWeeks.get(x).getNadQa() +
			rcaWeeks.get(x).getNadUat() + rcaWeeks.get(x).getNadProd() + rcaWeeks.get(x).getUtrQa() + rcaWeeks.get(x).getUtrUat() +
			rcaWeeks.get(x).getUtrProd() + rcaWeeks.get(x).getBsiQa() + rcaWeeks.get(x).getBsiUat() + rcaWeeks.get(x).getBsiProd() +
			rcaWeeks.get(x).getAdQa() + rcaWeeks.get(x).getAdUat() + rcaWeeks.get(x).getAdProd());
		}
		return total;
	}
	
	public int weeklyDataIssueForAllIssues(List<RcaCount> rcaWeeks){
		int total =0;
		
		for(int x=0; x < rcaWeeks.size(); x++){
			total = total + (rcaWeeks.get(x).getDiQa() + rcaWeeks.get(x).getDiUat() + rcaWeeks.get(x).getDiProd());
		}
		return total;
		
	}
	
	public int weeklyIntegrationIssueForAllIssues(List<RcaCount> rcaWeeks){
		int total =0;
		
		for(int x=0; x < rcaWeeks.size(); x++){
			total = total + (rcaWeeks.get(x).getIiQa() + rcaWeeks.get(x).getIiUat() + rcaWeeks.get(x).getIiProd());
		}
		return total;
		
	}
	
	public int weeklyConfigurationIssueForAllIssues(List<RcaCount> rcaWeeks){
		int total =0;
		
		for(int x=0; x < rcaWeeks.size(); x++){
			total = total + (rcaWeeks.get(x).getConfigQa() + rcaWeeks.get(x).getConfigUat() + rcaWeeks.get(x).getConfigProd());
		}
		return total;
		
	}
	
	public int weeklyMissedAndCRCountForAllIssues(List<RcaCount> rcaWeeks){
		
		int total =0;
		
		for(int x=0; x < rcaWeeks.size(); x++){
			total = total + rcaWeeks.get(x).getMrQa() + rcaWeeks.get(x).getMrUat() + rcaWeeks.get(x).getMrProd() +
					rcaWeeks.get(x).getCrQa() + rcaWeeks.get(x).getCrUat() + rcaWeeks.get(x).getCrProd();
		}
		return total;
	}
	
	public int weeklyClientCodeBugForAllIssues(List<RcaCount> rcaWeeks){
       
		int total =0;
		
		for(int x=0; x < rcaWeeks.size(); x++){
			total = total + rcaWeeks.get(x).getCcbQa() + rcaWeeks.get(x).getCcbUat() + rcaWeeks.get(x).getCcbProd();
		}
		
		return total;
	}

}
