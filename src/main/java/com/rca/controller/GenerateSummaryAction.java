package com.rca.controller;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.xssf.usermodel.XSSFBorderFormatting;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opensymphony.xwork2.ActionSupport;
import com.rca.common.ReportUtility;
import com.rca.entity.ProjectDetails;
import com.rca.entity.RankingFramework;
import com.rca.entity.RcaCount;
import com.rca.service.LoginDetailService;
import com.rca.service.ProjectDetailsManager;
import com.rca.service.RcaManager;
import com.rca.workbookutility.WorkBookCell;
import com.rca.workbookutility.WorkBookRow;

public class GenerateSummaryAction extends ActionSupport{
	private RcaManager rcaManager;
	private ProjectDetailsManager projectDetailsManager;
	private LoginDetailService loginDetailService;
	private FileInputStream fileInputStream;
	private XSSFCellStyle percStyle;
	private XSSFWorkbook toolSetMatrix;
	private ReportUtility rU;
	private XSSFFont defaultFont;
	
	private static final Color GREEN = new Color(145, 208, 80);
	private static final Color RED = new Color(255, 0, 0);
	private static final Color YELLOW = new Color(255, 255, 0);
	private static final Color WHITE = new Color(255, 255, 255);


	public String execute() throws Exception {
		createWorkBook();
		fileInputStream = new FileInputStream(new File("D:\\Ranking Framework.xlsx"));
		return SUCCESS;
	}

	void createWorkBook() throws IOException {
		toolSetMatrix = new XSSFWorkbook();
		defaultFont = toolSetMatrix.createFont();
		defaultFont.setFontName(HSSFFont.FONT_ARIAL);
		rU = new ReportUtility();
		createRFSheet();
		createSummarySheet();
		// Write the workbook in file system
		FileOutputStream out = new FileOutputStream(new File(
				"D:\\Ranking Framework.xlsx"));
		toolSetMatrix.write(out);
		out.close();
		toolSetMatrix.close();
	}

	public static final Comparator<RcaCount> byProjectName = new Comparator<RcaCount>() {
		@Override
		public int compare(RcaCount o1, RcaCount o2) {
			return o1.getProjectDetails().getProjectName()
					.compareTo(o2.getProjectDetails().getProjectName());
		}
	};
	
	public static final Comparator<RcaCount> byTeamName = new Comparator<RcaCount>() {
		@Override
		public int compare(RcaCount o1, RcaCount o2) {
			String s1 = o1.getProjectDetails().getActionTeam();
			String s2 = o2.getProjectDetails().getActionTeam();
			if(null == s1)
				s1="";
			if(null == s2)
				s2="";
			return s1.compareTo(s2);
		}
	};

	public static final Comparator<RankingFramework> byTeamNameWithRankingFramework = new Comparator<RankingFramework>() {
		@Override
		public int compare(RankingFramework o1, RankingFramework o2) {
			String s1 = o1.getTeamName();
			String s2 = o2.getTeamName();
			if(null == s1)
				s1="";
			if(null == s2)
				s2="";
			return s1.compareTo(s2);
		}
	};
	private void createSummarySheet() {
		XSSFSheet summarySheet = toolSetMatrix.createSheet("Summary Sheet");
		rU.createSummaryHeaderRows(summarySheet, toolSetMatrix, defaultFont);
		List<WorkBookRow> wbRows = populateSummarySheetData();
		int counter = summarySheet.getPhysicalNumberOfRows();
		for (WorkBookRow wbRow : wbRows) {
			XSSFRow row = summarySheet.createRow(counter);
			buildSummaryData(wbRow, summarySheet, row);
			counter++;
		}
	}

	private void buildSummaryData(WorkBookRow wbRow, XSSFSheet summarySheet, XSSFRow row) {
		List<WorkBookCell> wbCells = wbRow.getRowCells();
		int columnIndex = row.getPhysicalNumberOfCells();
		XSSFCellStyle style = toolSetMatrix.createCellStyle();
		for (WorkBookCell wbCell : wbCells) {
			XSSFCell cell = row.createCell(columnIndex);
			style = (XSSFCellStyle) cell.getCellStyle().clone();
			style.setFillForegroundColor(new XSSFColor(wbCell.getColor()));
			style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
			style.setFont(defaultFont);
			setBorderStyle(XSSFBorderFormatting.BORDER_THIN, style);
			if (wbCell.isFormula())
				cell.setCellFormula(wbCell.getValue());
			else
				cell.setCellValue(wbCell.getValue());
			cell.setCellStyle(style);
			columnIndex++;
		}
	}

	// This method set the border style
	private static void setBorderStyle(short borderThickness, XSSFCellStyle style) {
		style.setBorderBottom(borderThickness);
		style.setBorderLeft(borderThickness);
		style.setBorderRight(borderThickness);
		style.setBorderTop(borderThickness);
	}

	private List<WorkBookRow> populateSummarySheetData()
	{
		List<String> prevTwoWeek = rU.findPreviousTwoWeek();
		List<WorkBookRow> wbRows = new ArrayList<WorkBookRow>();
		WorkBookRow wbRow = null;
		String headers = "S.No,Action Team Name,Client,Geb/Spock,Prod,UAT,QA,Open,Team Ranking";
		String[] headerCells = headers.split(",");

		List<RcaCount> rcaCounts = rcaManager.findRCAByWeekPeriod(prevTwoWeek.get(0));
		
		int rowCount = 1;
		boolean found = false;
		List<ProjectDetails> projectDetailsList=projectDetailsManager.getAllActiveProjects();
        
		for (ProjectDetails projectDetails : projectDetailsList) {
			found = false;
			for (RcaCount rca : rcaCounts) {
				if (null != rca.getProjectDetails().getActionTeam()
						&& !"".equalsIgnoreCase(rca.getProjectDetails()
								.getActionTeam())
						&& null != projectDetails.getActionTeam()
						&& !"".equalsIgnoreCase(projectDetails.getActionTeam())) {
					if (projectDetails.getActionTeam().equals(
							rca.getProjectDetails().getActionTeam())) {
						found = true;
						break;
					}
				}
			}
			if (!found) {
				
				RcaCount rcaCount = new RcaCount();
				rcaCount.setWeek(prevTwoWeek.get(0));
				rcaCount.setProjectDetails(projectDetails);
				rcaCounts.add(rcaCount);
				
			}

		}
		Collections.sort(rcaCounts, byTeamName);
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
				wbCell.setColor(WHITE);
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
					wbCell.setValue(rca.getProjectDetails().getAutomation());
					if ("Automation in progress".equalsIgnoreCase(rca.getProjectDetails().getAutomation()))
					{
						wbCell.setColor(GREEN);
					}
				}
				else if ("Prod".equals(header))
				{
					int newCount = rU.weeklyBugCountForAllProjectsInProduction(rca);
					int oldCount = 0;
					if (null != prevRCACount)
						oldCount = rU.weeklyBugCountForAllProjectsInProduction(prevRCACount);
					String diff = " ";
					if(newCount-oldCount != 0)
						diff = (newCount-oldCount) >= 0 ? " (+" + (newCount-oldCount)+")":" (" + (newCount-oldCount)+")";
					wbCell.setValue(newCount + diff);
					doColor(wbCell, oldCount, newCount);
				}
				else if ("UAT".equals(header))
				{
					int newCount = rU.weeklyBugCountForAllProjectsInUAT(rca);
					int oldCount = 0;
					if (null != prevRCACount)
						oldCount = rU.weeklyBugCountForAllProjectsInUAT(prevRCACount);
					String diff = " ";
					if(newCount-oldCount != 0)
						diff = (newCount-oldCount) >= 0 ? " (+" + (newCount-oldCount)+")":" (" + (newCount-oldCount)+")";
					wbCell.setValue(newCount + diff);
					doColor(wbCell, oldCount, newCount);
				}
				else if ("QA".equals(header))
				{
					int newCount = rU.weeklyBugCountForAllProjectsInQA(rca);
					int oldCount = 0;
					if (null != prevRCACount)
						oldCount = rU.weeklyBugCountForAllProjectsInQA(prevRCACount);
					String diff = " ";
					if(newCount-oldCount != 0)
						diff = (newCount-oldCount) >= 0 ? " (+" + (newCount-oldCount)+")":" (" + (newCount-oldCount)+")";
					wbCell.setValue(newCount + diff);
					doColor(wbCell, oldCount, newCount);
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
						diff = (newCount-oldCount) >= 0 ? " (+" + (newCount-oldCount)+")":" (" + (newCount-oldCount)+")";
					wbCell.setValue(newCount + diff);
					doColor(wbCell, oldCount, newCount);
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

	private void doColor(WorkBookCell wbCell, int oldCount, int newCount) {
		int divisionFact = oldCount > 0 ? oldCount : 1;
		int percIncrease = (newCount - oldCount) * 100 / divisionFact;
		if (percIncrease <= -20)
			wbCell.setColor(GREEN);
		else if (percIncrease >= 20)
			wbCell.setColor(RED);
		else
			wbCell.setColor(YELLOW);
	}

	void createRFSheet() {
		XSSFSheet rankingFrameworkSheet = toolSetMatrix
				.createSheet("Ranking Framework");
		rU.createRFHeaderRows(rankingFrameworkSheet, toolSetMatrix, defaultFont);
		List<RankingFramework> rankingRows = populateRFData();
		percStyle = toolSetMatrix.createCellStyle();
		percStyle.setDataFormat(toolSetMatrix.createDataFormat()
				.getFormat("0%"));
		XSSFCellStyle styleYellow = toolSetMatrix.createCellStyle();
		XSSFCellStyle styleGreen = toolSetMatrix.createCellStyle();
		XSSFCellStyle defaultStyle = toolSetMatrix.createCellStyle();
		XSSFColor lightYellow = new XSSFColor(YELLOW);
		XSSFColor lightGreen = new XSSFColor(GREEN);
		styleYellow.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		styleYellow.setFillForegroundColor(lightYellow);
		styleGreen.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		styleGreen.setFillForegroundColor(lightGreen);
		

		int counter = rankingFrameworkSheet.getPhysicalNumberOfRows();
		for (RankingFramework rankingRow : rankingRows)
		{
			XSSFRow row = rankingFrameworkSheet.createRow(counter);
			rU.buildRFColumns(rankingRow, row, rankingFrameworkSheet, percStyle, styleYellow, styleGreen, defaultStyle, defaultFont);
			counter++;
		}
	}

	public List<RankingFramework> populateRFData()
	{
		List<RankingFramework> rankingRows = new ArrayList<RankingFramework>();

		List<String> prevTwoWeek = rU.findPreviousTwoWeek();
		boolean found = false;

		List<RcaCount> rcaCounts = rcaManager.findRCAByWeekPeriod(prevTwoWeek.get(0));
		Collections.sort(rcaCounts, byTeamName);
		int rowCount = 2;
		
		List<ProjectDetails> projectDetailsList=projectDetailsManager.getAllActiveProjects();
        
		for (ProjectDetails projectDetails : projectDetailsList) {
			found = false;
			for (RcaCount rca : rcaCounts) {
				if (null != rca.getProjectDetails().getActionTeam()
						&& !"".equalsIgnoreCase(rca.getProjectDetails()
								.getActionTeam())
						&& null != projectDetails.getActionTeam()
						&& !"".equalsIgnoreCase(projectDetails.getActionTeam())) {
					if (projectDetails.getActionTeam().equals(
							rca.getProjectDetails().getActionTeam())) {
						found = true;
						break;
					}
				}
			}
			if (!found) {

				RankingFramework rankingRow = new RankingFramework();

				String actionTeams = projectDetails.getActionTeam();
				if (null != actionTeams && !"".equalsIgnoreCase(actionTeams)) {
					rankingRow.setAgileComplianceScore("IF(C" + rowCount
							+ "=100%,10,IF(C" + rowCount + "=75%,5,IF(C"
							+ rowCount + "=50%,2,IF(C" + rowCount
							+ "=25%,0,0))))");
					rankingRow.setCcbScore("IF(E" + rowCount + "<=5,10,IF(E"
							+ rowCount + "<=10,5,IF(E" + rowCount
							+ "<=15,2,IF(E" + rowCount + "<=20,0,0))))");
					rankingRow.setCollaborationScore("IF(R" + rowCount
							+ "=1,10,0)");
					rankingRow.setCumulativeBacklogScore("IF(T" + rowCount
							+ "<=-5,10,IF(T" + rowCount + "<=0,5,IF(T"
							+ rowCount + "<=5,3,0)))");
					rankingRow.setjUnitCovScore("IF(I" + rowCount
							+ "=100%,5,IF(I" + rowCount + "=75%,,IF(I"
							+ rowCount + "=50%,1,IF(I" + rowCount
							+ "=25%,0,0))))");
					rankingRow.setMissReqScore("IF(V" + rowCount + "=0,10,IF(V"
							+ rowCount + "<=2,5,IF(V" + rowCount
							+ " > 2,0,0)))");
					rankingRow.setQaDefectScore("IF(AND(M" + rowCount
							+ ">20%,N" + rowCount + ">5),0,IF(AND(0%<M"
							+ rowCount + "<20%,N" + rowCount
							+ "<5),10,IF(AND(-20%<M" + rowCount + "<0%,N"
							+ rowCount + "<5),10,IF(AND(M" + rowCount
							+ "<-20%,N" + rowCount + "<5),20,10))))");
					rankingRow
							.setReopenScore("IF(P" + rowCount + "=0,10,IF(P"
									+ rowCount + "=1,5,IF(P" + rowCount
									+ " > 1,0,0)))");
					rankingRow.setRiskScore("IF(X" + rowCount
							+ "=\"A\",10,IF(X" + rowCount + "=\"B\",7,IF(X"
							+ rowCount + "=\"C\",5,IF(X" + rowCount
							+ "=\"D\",3,0))))");
					rankingRow.setToolComplianceScore("IF(G" + rowCount
							+ "=100%,5,IF(G" + rowCount + "=75%,,IF(G"
							+ rowCount + "=50%,1,IF(G" + rowCount
							+ "=25%,0,0))))");
					rankingRow.setTotalScore("SUM(D" + rowCount + ",F"
							+ rowCount + ",H" + rowCount + ",J" + rowCount
							+ ",O" + rowCount + ",Q" + rowCount + ",S"
							+ rowCount + ",U" + rowCount + ",W" + rowCount
							+ ",Y" + rowCount + ")");
					rankingRow.setAbsoluteChange("L" + rowCount + "-K"
							+ rowCount);
					rankingRow.setRanking("IF(Z" + rowCount + ">=85,\"A\",IF(Z"
							+ rowCount + ">=75,\"B\",IF(Z" + rowCount
							+ ">=60,\"C\",\"D\")))");
					rankingRow.setClient(projectDetails.getProjectName());
					rankingRow.setTeamName(actionTeams);
					rankingRows.add(rankingRow);
					rowCount++;
				}

			}

		}

		for (RcaCount rca : rcaCounts) {
			RcaCount prevRCACount = rcaManager.findWeeklyRCAReportByProjectId(
					prevTwoWeek.get(1), rca.getProjectDetails().getProjectId());
			RankingFramework rankingRow = new RankingFramework();

			String actionTeams = rca.getProjectDetails().getActionTeam();
			if (null != actionTeams && !"".equalsIgnoreCase(actionTeams)) {
				rankingRow.setTeamName(actionTeams);
			}
			rankingRow.setAbsoluteChange("L" + rowCount + "-K" + rowCount);
			rankingRow.setAgileComplianceScore("IF(C" + rowCount
					+ "=100%,10,IF(C" + rowCount + "=75%,5,IF(C" + rowCount
					+ "=50%,2,IF(C" + rowCount + "=25%,0,0))))");
			rankingRow.setCcb(rca.getCcbUat() + rca.getCcbProd());
			rankingRow.setCcbScore("IF(E" + rowCount + "<=5,10,IF(E" + rowCount
					+ "<=10,5,IF(E" + rowCount + "<=15,2,IF(E" + rowCount
					+ "<=20,0,0))))");
			rankingRow.setClient(rca.getProjectDetails().getProjectName());
			rankingRow.setCollaborationScore("IF(R" + rowCount + "=1,10,0)");

			List<RcaCount> prevTwoWeekRCAList = new ArrayList<RcaCount>();
			prevTwoWeekRCAList.add(rca);
			prevTwoWeekRCAList.add(prevRCACount);
			List<Map<String, Integer>> prevTwoWeekCumu = rU
					.reportedCumulativeOpenAllWeeksGraphForAllProject(
							prevTwoWeekRCAList, prevTwoWeek);
			rankingRow.setCumulativeBacklog(prevTwoWeekCumu.get(0).get(
					rU.removeYearFromWeek(prevTwoWeek.get(0)))
					- prevTwoWeekCumu.get(1).get(
							rU.removeYearFromWeek(prevTwoWeek.get(1))));
			rankingRow.setCumulativeBacklogScore("IF(T" + rowCount
					+ "<=-5,10,IF(T" + rowCount + "<=0,5,IF(T" + rowCount
					+ "<=5,3,0)))");

			rankingRow.setCurrWeek(rU.weeklyBugCountForAllProjectsInQA(rca));
			rankingRow.setjUnitCovScore("IF(I" + rowCount + "=100%,5,IF(I"
					+ rowCount + "=75%,,IF(I" + rowCount + "=50%,1,IF(I"
					+ rowCount + "=25%,0,0))))");

			rankingRow.setMissReq(rca.getMrQa() + rca.getMrUat()
					+ rca.getMrProd());
			rankingRow.setMissReqScore("IF(V" + rowCount + "=0,10,IF(V"
					+ rowCount + "<=2,5,IF(V" + rowCount + " > 2,0,0)))");
			rankingRow.setPerChange("IF(AND(K" + rowCount + "=0,L" + rowCount
					+ ">=0),1,(L" + rowCount + "-K" + rowCount + ")/K"
					+ rowCount + ")");

			rankingRow.setPrevWeek((prevRCACount == null) ? 0 : rU
					.weeklyBugCountForAllProjectsInQA(prevRCACount));
			rankingRow.setQaDefectScore("IF(AND(M" + rowCount + ">20%,N"
					+ rowCount + ">5),0,IF(AND(0%<M" + rowCount + "<20%,N"
					+ rowCount + "<5),10,IF(AND(-20%<M" + rowCount + "<0%,N"
					+ rowCount + "<5),10,IF(AND(M" + rowCount + "<-20%,N"
					+ rowCount + "<5),20,10))))");
			rankingRow.setRanking("IF(Z" + rowCount + ">=85,\"A\",IF(Z"
					+ rowCount + ">=75,\"B\",IF(Z" + rowCount
					+ ">=60,\"C\",\"D\")))");
			rankingRow.setRankingComment(" ");

			rankingRow.setReopen(rca.getRoProd() + rca.getRoQa()
					+ rca.getRoUat());
			rankingRow.setReopenScore("IF(P" + rowCount + "=0,10,IF(P"
					+ rowCount + "=1,5,IF(P" + rowCount + " > 1,0,0)))");
			rankingRow.setRiskScore("IF(X" + rowCount + "=\"A\",10,IF(X"
					+ rowCount + "=\"B\",7,IF(X" + rowCount + "=\"C\",5,IF(X"
					+ rowCount + "=\"D\",3,0))))");
			rankingRow.setToolComplianceScore("IF(G" + rowCount
					+ "=100%,5,IF(G" + rowCount + "=75%,,IF(G" + rowCount
					+ "=50%,1,IF(G" + rowCount + "=25%,0,0))))");
			rankingRow.setTotalScore("SUM(D" + rowCount + ",F" + rowCount
					+ ",H" + rowCount + ",J" + rowCount + ",O" + rowCount
					+ ",Q" + rowCount + ",S" + rowCount + ",U" + rowCount
					+ ",W" + rowCount + ",Y" + rowCount + ")");
			rankingRows.add(rankingRow);
			rowCount++;
		}
		Collections.sort(rankingRows, byTeamNameWithRankingFramework);
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

	public ProjectDetailsManager getProjectDetailsManager() {
		return projectDetailsManager;
	}

	public void setProjectDetailsManager(ProjectDetailsManager projectDetailsManager) {
		this.projectDetailsManager = projectDetailsManager;
	}
	
	

}
