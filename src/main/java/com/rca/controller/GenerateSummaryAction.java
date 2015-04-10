package com.rca.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
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

public class GenerateSummaryAction extends ActionSupport{
	private RcaManager rcaManager;
	private LoginDetailService loginDetailService;
	private FileInputStream fileInputStream;
	private XSSFCellStyle style;


	public String execute() throws Exception {

		createWorkBook();
		fileInputStream = new FileInputStream(new File("D:\\Ranking Framework.xlsx"));
		return SUCCESS;
	}

	void createWorkBook() throws IOException
	{

		XSSFWorkbook toolSetMatrix = new XSSFWorkbook();
		XSSFSheet rankingFrameworkSheet = toolSetMatrix.createSheet("Ranking Framework");
		createHeaderRow(rankingFrameworkSheet, toolSetMatrix);
		List<RankingFramework> rankingRows = populateRankingRows();
		style = toolSetMatrix.createCellStyle();
		style.setDataFormat(toolSetMatrix.createDataFormat().getFormat("0%"));

		int counter = rankingFrameworkSheet.getPhysicalNumberOfRows();
		for (RankingFramework rankingRow : rankingRows)
		{
			XSSFRow row = rankingFrameworkSheet.createRow(counter);
			buildColumn(rankingRow, row, rankingFrameworkSheet);
			counter++;
		}
		//Write the workbook in file system
		FileOutputStream out = new FileOutputStream(new File("D:\\Ranking Framework.xlsx"));
		toolSetMatrix.write(out);
		out.close();
		toolSetMatrix.close();
	}

	void createHeaderRow(XSSFSheet rankingFrameworkSheet, XSSFWorkbook toolSetMatrix)
	{
		XSSFRow headerRow = rankingFrameworkSheet.createRow(0);
		String header = "Action Team Name, Client, Agile Compliance, Score,	Client code defects(PROD+UAT), Score, Tool Compliance, Score, Junit Test coverage, Score, Previous Week, Current Week, % Change, Absolute Change, Score, Re-open, Score, Collaboration, Score, Cumulative Backlog, Score, Missed requirements, Score, Risk, Score, Total score, Ranking, Actual Used, Ranking Comment";
		String[] headerCells = header.split(",");
		int columnIndex = 0;
		XSSFCellStyle headerStyle = toolSetMatrix.createCellStyle();;
		headerStyle.setRotation((short) 90);
		headerStyle.setShrinkToFit(true);
		headerStyle.setWrapText(true);
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

	public List<RankingFramework> populateRankingRows()
	{
		ReportUtility rU = new ReportUtility();

		List<RankingFramework> rankingRows = new ArrayList<RankingFramework>();

		List<String> prevTwoWeek = findPreviousTwoWeek();

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
			rankingRow.setCumulativeBacklog(prevTwoWeekCumu.get(1).get(prevTwoWeek.get(1)) - prevTwoWeekCumu.get(0).get(prevTwoWeek.get(0)));
			rankingRow.setCumulativeBacklogScore("IF(T"+rowCount+"<=-5,10,IF(T"+rowCount+"<=0,5,IF(T"+rowCount+"<=5,3,0)))");

			rankingRow.setCurrWeek(rU.weeklyBugCountForAllProjectsInQA(rca));
			rankingRow.setjUnitCovScore("IF(I"+rowCount+"=100%,5,IF(I"+rowCount+"=75%,,IF(I"+rowCount+"=50%,1,IF(I"+rowCount+"=25%,0,0))))");

			rankingRow.setMissReq(rca.getMrQa()+rca.getMrUat()+rca.getMrProd());
			rankingRow.setMissReqScore("IF(V"+rowCount+"=0,10,IF(V"+rowCount+"<=2,5,IF(V"+rowCount+" > 2,0,0)))");
			rankingRow.setPerChange("IF(AND(K"+rowCount+"=0,L"+rowCount+">=0),1,(L"+rowCount+"-K"+rowCount+")/K"+rowCount+")");

			rankingRow.setPrevWeek((prevRCACount == null)? 0:rU.weeklyBugCountForAllProjectsInQA(prevRCACount));
			rankingRow.setQaDefectScore("IF(AND(M"+rowCount+">20%,N"+rowCount+">5),0,IF(AND(0%<M"+rowCount+"<20%,N"+rowCount+"<5),10,IF(AND(-20%<M"+rowCount+"<0%,N"+rowCount+"<5),10,IF(AND(M"+rowCount+"<-20%,N"+rowCount+"<5),20,10))))");
			rankingRow.setRanking("IF(Z"+rowCount+">=85,\"A\",IF(Z"+rowCount+">=75,\"B\",IF(Z"+rowCount+">=60,\"C\",\"D\")))");
			rankingRow.setRankingComment("Tesst");

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

	void buildColumn(RankingFramework rankingRow, XSSFRow row, XSSFSheet rankingFrameworkSheet)
	{
		rankingFrameworkSheet.setFitToPage(true);
		boolean hideColumn = true;

		int cellCounter=0;
		//	A
		Cell cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellValue(rankingRow.getTeamName());

		//	B
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellValue(rankingRow.getClient());

		//		C
		rankingFrameworkSheet.setColumnHidden(cellCounter, hideColumn);
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellStyle(style);
		cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		cell.setCellValue(rankingRow.getAgileCompliance()/100);

		//		D
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellFormula(rankingRow.getAgileComplianceScore());

		//		E
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellValue(rankingRow.getCcb());

		//		F
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellFormula(rankingRow.getCcbScore());

		//		G
		rankingFrameworkSheet.setColumnHidden(cellCounter, hideColumn);
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellStyle(style);
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
		cell.setCellStyle(style);
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
		cell.setCellStyle(style);
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

		//		P
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellValue(rankingRow.getReopen());

		//		Q
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellFormula(rankingRow.getReopenScore());

		//		R
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellValue(rankingRow.getCollaboration());

		//		S
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellFormula(rankingRow.getCollaborationScore());

		//		T
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellValue(rankingRow.getCumulativeBacklog());

		//		U
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellFormula(rankingRow.getCumulativeBacklogScore());

		//		V
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellValue(rankingRow.getMissReq());

		//		W
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellFormula(rankingRow.getMissReqScore());

		//		X
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellValue(rankingRow.getRisk());

		//		Y
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellFormula(rankingRow.getRiskScore());

		//		Z
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellFormula(rankingRow.getTotalScore());

		//		AA
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellFormula(rankingRow.getRanking());

		//		AB
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cellCounter++;
		cell.setCellValue(rankingRow.getActualUsed());

		//		AC
		cell = createCell(row, cellCounter, rankingFrameworkSheet);
		cell.setCellValue(rankingRow.getRankingComment());
	}

	private List<String> findPreviousTwoWeek() {
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
