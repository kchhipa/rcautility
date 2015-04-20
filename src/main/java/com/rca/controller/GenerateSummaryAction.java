package com.rca.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opensymphony.xwork2.ActionSupport;
import com.rca.common.ReportUtility;
import com.rca.entity.RankingFramework;
import com.rca.entity.RcaCount;
import com.rca.service.LoginDetailService;
import com.rca.service.RcaManager;
import com.rca.workbookutility.WorkBookCell;
import com.rca.workbookutility.WorkBookRow;

public class GenerateSummaryAction extends ActionSupport{
	private RcaManager rcaManager;
	private LoginDetailService loginDetailService;
	private FileInputStream fileInputStream;
	private XSSFCellStyle percStyle;
	private XSSFWorkbook toolSetMatrix;
	private ReportUtility rU;


	public String execute() throws Exception {
		createWorkBook();
		fileInputStream = new FileInputStream(new File("D:\\Ranking Framework.xlsx"));
		return SUCCESS;
	}

	void createWorkBook() throws IOException
	{
		toolSetMatrix = new XSSFWorkbook();
		rU = new ReportUtility();
		createRFSheet();
		createSummarySheet();
		//Write the workbook in file system
		FileOutputStream out = new FileOutputStream(new File("D:\\Ranking Framework.xlsx"));
		toolSetMatrix.write(out);
		out.close();
		toolSetMatrix.close();
	}

	public static final Comparator<RcaCount> byProjectName = new Comparator<RcaCount>() {
		@Override
		public int compare(RcaCount o1, RcaCount o2) {
			return o1.getProjectDetails().getProjectName().compareTo(o2.getProjectDetails().getProjectName()); 
		}
	};
	
	private void createSummarySheet() {
		XSSFSheet summarySheet = toolSetMatrix.createSheet("Summary Sheet");
		rU.createSummaryHeaderRows(summarySheet, toolSetMatrix);
		List<WorkBookRow> wbRows = populateSummarySheetData();
		int counter = summarySheet.getPhysicalNumberOfRows();
		for(WorkBookRow wbRow : wbRows)
		{
			XSSFRow row = summarySheet.createRow(counter);
			buildSummaryData(wbRow, summarySheet, row);
			counter++;
		}
	}
	
	void buildSummaryData(WorkBookRow wbRow, XSSFSheet summarySheet, XSSFRow row)
	{
		List<WorkBookCell> wbCells = wbRow.getRowCells();
		int columnIndex = row.getPhysicalNumberOfCells();
		for(WorkBookCell wbCell : wbCells)
		{
			XSSFCell cell = row.createCell(columnIndex);
			if (wbCell.isFormula())
				cell.setCellFormula(wbCell.getValue());
			else
				cell.setCellValue(wbCell.getValue());
			columnIndex++;
		}
	}
	
	List<WorkBookRow> populateSummarySheetData()
	{
		List<String> prevTwoWeek = rU.findPreviousTwoWeek();
		List<WorkBookRow> wbRows = new ArrayList<WorkBookRow>();
		WorkBookRow wbRow = null;
		String headers = "S.No,Action Team Name,Client,Geb/Spock,Prod,UAT,QA,Open,Team Ranking";
		String[] headerCells = headers.split(",");

		List<RcaCount> rcaCounts = rcaManager.findRCAByWeekPeriod(prevTwoWeek.get(0));
		Collections.sort(rcaCounts, byProjectName);
		int rowCount = 1;
		for (RcaCount rca : rcaCounts)
		{
			RcaCount prevRCACount = rcaManager.findWeeklyRCAReportByProjectId(prevTwoWeek.get(1), rca.getProjectDetails().getProjectId());
			
			List<RcaCount> prevTwoWeekRCAList = new ArrayList<RcaCount>();
			prevTwoWeekRCAList.add(rca);
			prevTwoWeekRCAList.add(prevRCACount);
			
			wbRow = new WorkBookRow();
			wbRow.setRowName(rca.getProjectDetails().getProjectName());
			List<WorkBookCell> wbCells = new ArrayList<WorkBookCell>();
			for(String header : headerCells)
			{
				WorkBookCell wbCell = new WorkBookCell();
				wbCell.setName(rca.getProjectDetails().getProjectName());
				wbCell.setColumnHeader(header);
//				wbCell.setColor();
				if ("S.No".equals(header))
				{
					wbCell.setValue(String.valueOf(rowCount));
				}
				else if ("Action Team Name".equals(header))
				{
					wbCell.setValue(rca.getProjectDetails().getActionTeam());
				}
				else if ("Client".equals(header))
				{
					wbCell.setValue(rca.getProjectDetails().getProjectName());
				}
				else if ("Geb/Spock".equals(header))
				{
					wbCell.setValue("Geb/Spock Comment");
				}
				else if ("Prod".equals(header))
				{
					int newCount = rU.weeklyBugCountForAllProjectsInProduction(rca);
					int oldCount = 0;
					if (null != prevRCACount)
						oldCount = rU.weeklyBugCountForAllProjectsInProduction(prevRCACount);
					String diff = " ";
					if(newCount-oldCount != 0)
						diff = (newCount-oldCount) >= 0 ? "(+" + (newCount-oldCount)+")":"(" + (newCount-oldCount)+")";
					wbCell.setValue(newCount + diff);
				}
				else if ("UAT".equals(header))
				{
					int newCount = rU.weeklyBugCountForAllProjectsInUAT(rca);
					int oldCount = 0;
					if (null != prevRCACount)
						oldCount = rU.weeklyBugCountForAllProjectsInUAT(prevRCACount);
					String diff = " ";
					if(newCount-oldCount != 0)
						diff = (newCount-oldCount) >= 0 ? "(+" + (newCount-oldCount)+")":"(" + (newCount-oldCount)+")";
					wbCell.setValue(newCount + diff);
				}
				else if ("QA".equals(header))
				{
					int newCount = rU.weeklyBugCountForAllProjectsInQA(rca);
					int oldCount = 0;
					if (null != prevRCACount)
						oldCount = rU.weeklyBugCountForAllProjectsInQA(prevRCACount);
					String diff = " ";
					if(newCount-oldCount != 0)
						diff = (newCount-oldCount) >= 0 ? "(+" + (newCount-oldCount)+")":"(" + (newCount-oldCount)+")";
					wbCell.setValue(newCount + diff);
				}
				else if ("Open".equals(header))
				{
					List<Map<String, Integer>> prevTwoWeekCumu = rU.reportedCumulativeOpenAllWeeksGraphForAllProject(prevTwoWeekRCAList, prevTwoWeek);
					int newCount = prevTwoWeekCumu.get(0).get(rU.removeYearFromWeek(prevTwoWeek.get(0)));
					int oldCount = 0;
					if (null != prevRCACount)
						oldCount = prevTwoWeekCumu.get(1).get(rU.removeYearFromWeek(prevTwoWeek.get(1)));
					String diff = " ";
					if(newCount-oldCount != 0)
						diff = (newCount-oldCount) >= 0 ? "(+" + (newCount-oldCount)+")":"(" + (newCount-oldCount)+")";
					wbCell.setValue(newCount + diff);
				}
				else if ("Team Ranking".equals(header))
				{
					wbCell.setValue("\'Ranking Framework\'!AA" + (rowCount + 1));
					wbCell.setFormula(true);
				}
				wbCells.add(wbCell);
			}
			wbRow.setRowCells(wbCells);
			wbRows.add(wbRow);
			rowCount++;
		}
		return wbRows;
	}
	
	void createRFSheet()
	{
		XSSFSheet rankingFrameworkSheet = toolSetMatrix.createSheet("Ranking Framework");
		rU.createRFHeaderRows(rankingFrameworkSheet, toolSetMatrix);
		List<RankingFramework> rankingRows = populateRFData();
		Collections.sort(rankingRows);
		percStyle = toolSetMatrix.createCellStyle();
		percStyle.setDataFormat(toolSetMatrix.createDataFormat().getFormat("0%"));

		int counter = rankingFrameworkSheet.getPhysicalNumberOfRows();
		for (RankingFramework rankingRow : rankingRows)
		{
			XSSFRow row = rankingFrameworkSheet.createRow(counter);
			rU.buildRFColumns(rankingRow, row, rankingFrameworkSheet, percStyle);
			counter++;
		}
	}

	public List<RankingFramework> populateRFData()
	{
		List<RankingFramework> rankingRows = new ArrayList<RankingFramework>();

		List<String> prevTwoWeek = rU.findPreviousTwoWeek();

		List<RcaCount> rcaCounts = rcaManager.findRCAByWeekPeriod(prevTwoWeek.get(0));
		int rowCount = 2;
		for (RcaCount rca : rcaCounts)
		{
			RcaCount prevRCACount = rcaManager.findWeeklyRCAReportByProjectId(prevTwoWeek.get(1), rca.getProjectDetails().getProjectId());
			RankingFramework rankingRow = new RankingFramework();
			
			String actionTeams = rca.getProjectDetails().getActionTeam();
			if (null != actionTeams && !"".equalsIgnoreCase(actionTeams))
			{
				rankingRow.setTeamName(actionTeams);
			}
			rankingRow.setAbsoluteChange("L"+rowCount+"-K"+rowCount);
			rankingRow.setAgileComplianceScore("IF(C"+rowCount+"=100%,10,IF(C"+rowCount+"=75%,5,IF(C"+rowCount+"=50%,2,IF(C"+rowCount+"=25%,0,0))))");
			rankingRow.setCcb(rca.getCcbUat()+rca.getCcbProd());
			rankingRow.setCcbScore("IF(E"+rowCount+"<=5,10,IF(E"+rowCount+"<=10,5,IF(E"+rowCount+"<=15,2,IF(E"+rowCount+"<=20,0,0))))");
			rankingRow.setClient(rca.getProjectDetails().getProjectName());
			rankingRow.setCollaborationScore("IF(R"+rowCount+"=1,10,0)");

			List<RcaCount> prevTwoWeekRCAList = new ArrayList<RcaCount>();
			prevTwoWeekRCAList.add(rca);
			prevTwoWeekRCAList.add(prevRCACount);
			List<Map<String, Integer>> prevTwoWeekCumu = rU.reportedCumulativeOpenAllWeeksGraphForAllProject(prevTwoWeekRCAList, prevTwoWeek);
			rankingRow.setCumulativeBacklog(prevTwoWeekCumu.get(1).get(rU.removeYearFromWeek(prevTwoWeek.get(1))) - prevTwoWeekCumu.get(0).get(rU.removeYearFromWeek(prevTwoWeek.get(0))));
			rankingRow.setCumulativeBacklogScore("IF(T"+rowCount+"<=-5,10,IF(T"+rowCount+"<=0,5,IF(T"+rowCount+"<=5,3,0)))");

			rankingRow.setCurrWeek(rU.weeklyBugCountForAllProjectsInQA(rca));
			rankingRow.setjUnitCovScore("IF(I"+rowCount+"=100%,5,IF(I"+rowCount+"=75%,,IF(I"+rowCount+"=50%,1,IF(I"+rowCount+"=25%,0,0))))");

			rankingRow.setMissReq(rca.getMrQa()+rca.getMrUat()+rca.getMrProd());
			rankingRow.setMissReqScore("IF(V"+rowCount+"=0,10,IF(V"+rowCount+"<=2,5,IF(V"+rowCount+" > 2,0,0)))");
			rankingRow.setPerChange("IF(AND(K"+rowCount+"=0,L"+rowCount+">=0),1,(L"+rowCount+"-K"+rowCount+")/K"+rowCount+")");

			rankingRow.setPrevWeek((prevRCACount == null)? 0:rU.weeklyBugCountForAllProjectsInQA(prevRCACount));
			rankingRow.setQaDefectScore("IF(AND(M"+rowCount+">20%,N"+rowCount+">5),0,IF(AND(0%<M"+rowCount+"<20%,N"+rowCount+"<5),10,IF(AND(-20%<M"+rowCount+"<0%,N"+rowCount+"<5),10,IF(AND(M"+rowCount+"<-20%,N"+rowCount+"<5),20,10))))");
			rankingRow.setRanking("IF(Z"+rowCount+">=85,\"A\",IF(Z"+rowCount+">=75,\"B\",IF(Z"+rowCount+">=60,\"C\",\"D\")))");
			rankingRow.setRankingComment("Dummy Comment");

			rankingRow.setReopen(rca.getRoProd()+rca.getRoQa()+rca.getRoUat());
			rankingRow.setReopenScore("IF(P"+rowCount+"=0,10,IF(P"+rowCount+"=1,5,IF(P"+rowCount+" > 1,0,0)))");
			rankingRow.setRiskScore("IF(X"+rowCount+"=\"A\",10,IF(X"+rowCount+"=\"B\",7,IF(X"+rowCount+"=\"C\",5,IF(X"+rowCount+"=\"D\",3,0))))");
			rankingRow.setToolComplianceScore("IF(G"+rowCount+"=100%,5,IF(G"+rowCount+"=75%,,IF(G"+rowCount+"=50%,1,IF(G"+rowCount+"=25%,0,0))))");
			rankingRow.setTotalScore("SUM(D"+rowCount+",F"+rowCount+",H"+rowCount+",J"+rowCount+",O"+rowCount+",Q"+rowCount+",S"+rowCount+",U"+rowCount+",W"+rowCount+",Y"+rowCount+")");
			rankingRows.add(rankingRow);
			rowCount++;
		}
		return rankingRows;
	}

	public RcaManager getRcaManager() {
		return rcaManager;
	}

	public void setRcaManager(RcaManager rcaManager) {
		this.rcaManager = rcaManager;
	}

	public LoginDetailService getLoginDetailService() {
		return loginDetailService;
	}

	public void setLoginDetailService(LoginDetailService loginDetailService) {
		this.loginDetailService = loginDetailService;
	}
	public FileInputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(FileInputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

}
