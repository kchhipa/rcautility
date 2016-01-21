package com.rca.controller;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opensymphony.xwork2.ActionSupport;
import com.rca.common.ReportUtility;
import com.rca.entity.ProjectDetails;
import com.rca.entity.RcaCount;
import com.rca.entity.SprintReport;
import com.rca.entity.SprintReportRanking;
import com.rca.service.LoginDetailService;
import com.rca.service.ProjectDetailsManager;
import com.rca.service.RcaManager;
import com.rca.service.SprintReportManager;

public class GenerateSprintReport extends ActionSupport {
	private RcaManager rcaManager;
	private ProjectDetailsManager projectDetailsManager;
	private LoginDetailService loginDetailService;
	private FileInputStream fileInputStream;
	private XSSFCellStyle percStyle;
	private XSSFWorkbook toolSetMatrix;
	private ReportUtility rU;
	private XSSFFont defaultFont;
	private SprintReportManager sprintReportManager;
	private static final XSSFColor WHITE = new XSSFColor(new Color(255, 255,
			255));
	private static final XSSFColor BLACK = new XSSFColor(new Color(0, 0, 0));

	public String execute() throws Exception {
		createWorkBook();
		fileInputStream = new FileInputStream(new File("D:\\Sprint Data.xlsx"));
		return SUCCESS;
	}

	void createWorkBook() throws IOException {
		toolSetMatrix = new XSSFWorkbook();
		defaultFont = toolSetMatrix.createFont();
		defaultFont.setFontName(HSSFFont.FONT_ARIAL);
		rU = new ReportUtility();
		createSprintSheet();
		// Write the workbook in file system
		FileOutputStream out = new FileOutputStream(new File(
				"D:\\Sprint Data.xlsx"));
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
			if (null == s1)
				s1 = "";
			if (null == s2)
				s2 = "";
			return s1.compareTo(s2);
		}
	};

	public static final Comparator<ProjectDetails> byTeamNameWithProjectDetails = new Comparator<ProjectDetails>() {
		@Override
		public int compare(ProjectDetails o1, ProjectDetails o2) {
			String s1 = o1.getActionTeam();
			String s2 = o2.getActionTeam();
			if (null == s1)
				s1 = "";
			if (null == s2)
				s2 = "";
			return s1.compareTo(s2);
		}
	};

	void createSprintSheet() {
		XSSFSheet summarySheet = toolSetMatrix.createSheet("Summary Sheet");

		rU.createSprintReportHeaderRows(summarySheet, toolSetMatrix,
				defaultFont, 0);
		List<SprintReportRanking> rankingRows = populateSprintData();
		percStyle = toolSetMatrix.createCellStyle();
		percStyle.setDataFormat(toolSetMatrix.createDataFormat()
				.getFormat("0%"));
		rU.buildHeaderStyle(percStyle, CellStyle.BORDER_THIN, BLACK, WHITE,
				(short) 0, defaultFont);
		XSSFCellStyle mergedCellStyle = toolSetMatrix.createCellStyle();
		mergedCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		rU.buildHeaderStyle(mergedCellStyle, CellStyle.BORDER_THIN, BLACK,
				WHITE, (short) 0, defaultFont);
		XSSFCellStyle styleGreen = toolSetMatrix.createCellStyle();
		XSSFCellStyle defaultStyle = toolSetMatrix.createCellStyle();

		rU.buildHeaderStyle(defaultStyle, CellStyle.BORDER_THIN, BLACK, WHITE,
				(short) 0, defaultFont);
		int counter = summarySheet.getPhysicalNumberOfRows();

		int kanbanCount = 0;
		for (SprintReportRanking rankingRow : rankingRows) {
			if ("NO".equalsIgnoreCase(rankingRow.getIsKanbanFollowed())) {
				XSSFRow row = summarySheet.createRow(counter);
				rU.buildSprintSummaryColumns(rankingRow, row, summarySheet,
						percStyle, mergedCellStyle, styleGreen, defaultStyle,
						defaultFont, counter + 1, false);
				counter++;
			}

		}
		kanbanCount = counter;

		for (SprintReportRanking rankingRow : rankingRows) {
			if ("Yes".equalsIgnoreCase(rankingRow.getIsKanbanFollowed())) {
				if (kanbanCount == counter) {
					rU.createSprintReportHeaderRowsKanban(summarySheet,
							toolSetMatrix, defaultFont, counter);
				}
				XSSFRow row = summarySheet.createRow(kanbanCount + 2);
				rankingRow.setsNo(kanbanCount + 1);
				rU.buildSprintSummaryColumns(rankingRow, row, summarySheet,
						percStyle, mergedCellStyle, styleGreen, defaultStyle,
						defaultFont, kanbanCount + 3, true);
				kanbanCount++;
			}

		}
		
	}

	public List<SprintReportRanking> populateSprintData() {
		List<SprintReportRanking> sprintDataRows = new ArrayList<SprintReportRanking>();
		List<String> prevTwoWeek = rU.findPreviousTwoWeek();
		List<RcaCount> rcaCounts = rcaManager.findRCAByWeekPeriod(prevTwoWeek
				.get(0));
		Collections.sort(rcaCounts, byTeamName);
		int rowCount = 3;
		List<ProjectDetails> projectDetailsList = projectDetailsManager
				.getAllActiveProjects();
		Collections.sort(projectDetailsList, byTeamNameWithProjectDetails);
		Calendar calobj = Calendar.getInstance();
		for (ProjectDetails projectDetails : projectDetailsList) {
			SprintReportRanking sprintReportRanking = new SprintReportRanking();
			sprintReportRanking.setsNo(rowCount - 2);

			ArrayList<SprintReport> sprintReport = sprintReportManager
					.findLatestClosedSprintDataByProjectId(calobj.getTime(),
							projectDetails.getProjectId());
			String actionTeams = projectDetails.getActionTeam();
			if (sprintReport != null && sprintReport.size() > 0) {
				String teamSize = "";
				sprintReportRanking.setCommittedSP(sprintReport.get(0)
						.getSpCommitted());
				sprintReportRanking.setCompletedSP(sprintReport.get(0)
						.getSpDelivered());
				sprintReportRanking.setSpAddedMidSprint(sprintReport.get(0)
						.getSpAddedInMid());
				sprintReportRanking.setSprintEndDate(rU
						.editDateFormat(sprintReport.get(0).getEndDate()));
				sprintReportRanking.setSprintStartDate(rU
						.editDateFormat(sprintReport.get(0).getStartDate()));
				sprintReportRanking.setSprintName(sprintReport.get(0)
						.getSprintName().indexOf("(") != -1 ? sprintReport
						.get(0)
						.getSprintName()
						.substring(
								0,
								sprintReport.get(0).getSprintName()
										.indexOf("(")) : sprintReport.get(0)
						.getSprintName());
				teamSize = sprintReport.get(0).getDevMembers()
						+ sprintReport.get(0).getQaMembers() + "("
						+ sprintReport.get(0).getDevMembers() + "+"
						+ sprintReport.get(0).getQaMembers() + ")";
				sprintReportRanking.setTeamSize(teamSize);
				sprintReportRanking.setTeamCapacity(sprintReport.get(0)
						.getTeamCapacity());
				for (RcaCount rca : rcaCounts) {
					if (projectDetails.getProjectId() == rca
							.getProjectDetails().getProjectId()) {
						sprintReportRanking.setReopenCount(rca.getRoProd()
								+ rca.getRoQa() + rca.getRoUat());
						sprintReportRanking.setClientCodeDefect(rca.getCcbQa()
								+ rca.getCcbUat());
						break;
					}
				}
				sprintReportRanking.setRanking("IF(M" + rowCount
						+ ">80,\"A\",IF(P" + rowCount + ">=59,\"B\",\"C\"))");
				sprintReportRanking.setRealizedPercentage("(I" + rowCount
						+ "-J" + rowCount + "-K" + rowCount + ")/h" + rowCount);
				sprintReportRanking.setClient(projectDetails.getProjectName());
				sprintReportRanking
						.setTeamName(actionTeams != null ? actionTeams : "");
				sprintReportRanking.setIsKanbanFollowed(sprintReport.get(0)
						.getIsKanbanFollowed());
				sprintDataRows.add(sprintReportRanking);
				rowCount++;
			}
		}

		return sprintDataRows;

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

	public void setProjectDetailsManager(
			ProjectDetailsManager projectDetailsManager) {
		this.projectDetailsManager = projectDetailsManager;
	}

	public SprintReportManager getSprintReportManager() {
		return sprintReportManager;
	}

	public void setSprintReportManager(SprintReportManager sprintReportManager) {
		this.sprintReportManager = sprintReportManager;
	}

}
