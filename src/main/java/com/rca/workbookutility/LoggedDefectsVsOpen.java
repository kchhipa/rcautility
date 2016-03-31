package com.rca.workbookutility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.rca.common.ReportUtility;
import com.rca.entity.RcaCount;

public class LoggedDefectsVsOpen {
	
	
	
	private String defectypeClose;
	private int count;
	private String defectTypeOpen;
	
	Logger LOG = Logger.getLogger(LoggedDefectsVsOpen.class);
	
	public String getCloseDefectType() {
		return defectypeClose;
	}
	public void setCloseDefectType1(String defectypeClose) {
		this.defectypeClose = defectypeClose;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getOpenDefectType() {
		return defectTypeOpen;
	}
	public void setOpenDefectType(String defectType) {
		this.defectTypeOpen = defectType;
	}
	
	public LoggedDefectsVsOpen(){
		
	}
	
	public LoggedDefectsVsOpen(String category, int count, String type){
		
	}
	
	public Map<String, Map<String, Integer>> reportedWeeklyTrendLoggedVsOpen(List<RcaCount> allWeeksrcaCounts, List<String> allWeeks){

		int sizeWeek = allWeeks.size();
		String status="";
		
		Map<String, Map<String, Integer>> defVsOpenList = new LinkedHashMap<String, Map<String, Integer>>();
		
		
		ReportUtility ru = new ReportUtility();
		
    	for(int i=0 ; i < sizeWeek ; i++){
			String key="",key1="";
			String week = allWeeks.get(i),week1="";
			week = ru.removeYearFromWeek(week);
			LOG.info("week =============== " + week);
			LoggedDefectsVsOpen loggedDefOpen = new LoggedDefectsVsOpen();
			Map<String, Integer> map = new HashMap<String, Integer>();
		for(int j=0 ; j < allWeeksrcaCounts.size() ; j++){
			
			
			week1 =ru.removeYearFromWeek(allWeeksrcaCounts.get(j).getWeek());
			
		 if(null != allWeeksrcaCounts.get(j) && (!week.equals("") || week != null))
			 
			{
			 LOG.info("Number of closed defects " + ru.weeklyCloseTicketForAllIssuesInClose(allWeeksrcaCounts.get(j)));
			if(week1.equalsIgnoreCase(week))
				{
				RcaCount rcaCount = allWeeksrcaCounts.get(j);
				  LOG.info("week matched ===============" + week);
				if( ru.weeklyCloseTicketForAllIssuesInClose(rcaCount) > 0 ){
					status = "Resolved";
					LOG.info("Status is " + status);
					loggedDefOpen.setCloseDefectType1(status);
					loggedDefOpen.setCount(ru.weeklyCloseTicketForAllIssuesInClose(rcaCount));
					 map.put(status, ru.weeklyCloseTicketForAllIssuesInClose(rcaCount));
				}
				
				if(ru.weeklyTotalOpenCountIssues(rcaCount) > 0){
					status = "Logged";
					loggedDefOpen.setOpenDefectType(status);
					loggedDefOpen.setCount(ru.weeklyTotalOpenCountIssues(rcaCount));
					LOG.info("Status is " + status);
					map.put(status, ru.weeklyTotalOpenCountIssues(rcaCount));
					 
				}
				key = week;
				}
			
			}
		 if(!key.equals("") && key != null){
			 defVsOpenList.put(key, map); 
			 
		 }
		  }
//		      defVsOpenList.put(key, lst);
		}
		
		return defVsOpenList;
	}
	
}
