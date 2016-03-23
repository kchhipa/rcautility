package com.rca.workbookutility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
		
		Map<String, Map<String, Integer>> defVsOpenList = new HashMap<String, Map<String, Integer>>();
		
		List<LoggedDefectsVsOpen> lst = null;
		
		ReportUtility ru = new ReportUtility();
		
    	for(int i=0 ; i < sizeWeek ; i++){
			String key="",key1="";
			String week = allWeeks.get(i),week1="";
			week = ru.removeYearFromWeek(week);
			LOG.info("week =============== " + week);
			lst = new ArrayList<LoggedDefectsVsOpen>();
			LoggedDefectsVsOpen loggedDefOpen = new LoggedDefectsVsOpen();
			Map<String, Integer> map = new HashMap<String, Integer>();
		for(int j=0 ; j < allWeeksrcaCounts.size() ; j++){
			
			
			week1 =ru.removeYearFromWeek(allWeeksrcaCounts.get(j).getWeek());
			
		 if(null != allWeeksrcaCounts.get(j))
			 
			{
			 LOG.info("Number of closed defects " + ru.weeklyCloseTicketForAllIssuesInClose(allWeeksrcaCounts.get(j)));
			if(week1.equalsIgnoreCase(week))
				{
				RcaCount rcaCount = allWeeksrcaCounts.get(j);
				  LOG.info("week matched ===============" + week);
				if( ru.weeklyCloseTicketForAllIssuesInClose(rcaCount) > 0 ){
					status = "close";
					LOG.info("Status is " + status);
					loggedDefOpen.setCloseDefectType1(status);
					loggedDefOpen.setCount(ru.weeklyCloseTicketForAllIssuesInClose(rcaCount));
					LOG.info("size of list and week is " + lst.size() + " key " + key);
					 lst.add(loggedDefOpen);
					 map.put(status, ru.weeklyCloseTicketForAllIssuesInClose(rcaCount));
				}
				
				if(ru.weeklyTotalOpenCountIssues(rcaCount) > 0){
					status = "open";
					loggedDefOpen.setOpenDefectType(status);
					loggedDefOpen.setCount(ru.weeklyTotalOpenCountIssues(rcaCount));
					LOG.info("Status is " + status);
					 LOG.info("size of list and week is " + lst.size() + " key " + key);
					 lst.add(loggedDefOpen);
					 map.put(status, ru.weeklyTotalOpenCountIssues(rcaCount));
					 
				}
				key = week1;
				}
			
			}
		 defVsOpenList.put(key, map);
		  }
//		      defVsOpenList.put(key, lst);
		}
		
		return defVsOpenList;
	}
	
}
