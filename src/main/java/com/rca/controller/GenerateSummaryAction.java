package com.rca.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
		//Write the workbook in file system
		FileOutputStream out = new FileOutputStream(new File("D:\\Ranking Framework.xlsx"));
		toolSetMatrix.write(out);
		out.close();
		toolSetMatrix.close();
	}

	void createRFSheet()
	{
		XSSFSheet rankingFrameworkSheet = toolSetMatrix.createSheet("Ranking Framework");
		rU.createRFHeaderRows(rankingFrameworkSheet, toolSetMatrix);
		List<RankingFramework> rankingRows = populateRFData();
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
