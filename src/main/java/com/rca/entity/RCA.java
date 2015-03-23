package com.rca.entity;

import java.util.ArrayList;
import java.util.List;


public class RCA {
	
	public int project_id;
	public String week;	
	public int mr_product_backlog;
	public int mr_qa;
	public int mr_uat;
	public int mr_prod;
	public int cr_product_backlog;
	public int cr_qa;
	public int cr_uat;
	public int cr_prod;
	public int config_product_backlog;
	public int config_qa;
	public int config_uat;
	public int config_prod;
	public int ccb_product_backlog;
	public int ccb_qa;
	public int ccb_uat;
	public int ccb_prod;
	public int ad_product_backlog;
	public int ad_qa;
	public int ad_uat;
	public int ad_prod;
	public int dup_product_backlog;
	public int dup_qa;
	public int dup_uat;
	public int dup_prod;
	public int nad_product_backlog;
	public int nad_qa;
	public int nad_uat;
	public int nad_prod;
	public int bsi_product_backlog;
	public int bsi_qa;
	public int bsi_uat;
	public int bsi_prod;
	public int utr_product_backlog;
	public int utr_qa;
	public int utr_uat;
	public int utr_prod;
	public int pd_product_backlog;
	public int pd_qa;
	public int pd_uat;
	public int pd_prod;
	public int ii_product_backlog;
	public int ii_qa;
	public int ii_uat;
	public int ii_prod;
	public int di_product_backlog;
	public int di_qa;
	public int di_uat;
	public int di_prod;			
	public int ro_qa;
	public int ro_uat;
	public int ro_prod;
	
	private List<String> weeks;
	private String startDate;
	private String endDate;
	private String weekType;
	private String weekCount;
	private ArrayList<String> projectList;
	private String[] project;
	private String projectStatus;
	private String projectName;
	
	/**
	 * @return the project
	 */
	public String[] getProject() {
		return project;
	}
	/**
	 * @param project the project to set
	 */
	public void setProject(String[] project) {
		this.project = project;
	}
	public int getProject_id() {
		return project_id;
	}
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public int getMr_product_backlog() {
		return mr_product_backlog;
	}
	public void setMr_product_backlog(int mr_product_backlog) {
		this.mr_product_backlog = mr_product_backlog;
	}
	public int getMr_qa() {
		return mr_qa;
	}
	public void setMr_qa(int mr_qa) {
		this.mr_qa = mr_qa;
	}
	public int getMr_uat() {
		return mr_uat;
	}
	public void setMr_uat(int mr_uat) {
		this.mr_uat = mr_uat;
	}
	public int getMr_prod() {
		return mr_prod;
	}
	public void setMr_prod(int mr_prod) {
		this.mr_prod = mr_prod;
	}
	public int getCr_product_backlog() {
		return cr_product_backlog;
	}
	public void setCr_product_backlog(int cr_product_backlog) {
		this.cr_product_backlog = cr_product_backlog;
	}
	public int getCr_qa() {
		return cr_qa;
	}
	public void setCr_qa(int cr_qa) {
		this.cr_qa = cr_qa;
	}
	public int getCr_uat() {
		return cr_uat;
	}
	public void setCr_uat(int cr_uat) {
		this.cr_uat = cr_uat;
	}
	public int getCr_prod() {
		return cr_prod;
	}
	public void setCr_prod(int cr_prod) {
		this.cr_prod = cr_prod;
	}
	public int getConfig_product_backlog() {
		return config_product_backlog;
	}
	public void setConfig_product_backlog(int config_product_backlog) {
		this.config_product_backlog = config_product_backlog;
	}
	public int getConfig_qa() {
		return config_qa;
	}
	public void setConfig_qa(int config_qa) {
		this.config_qa = config_qa;
	}
	public int getConfig_uat() {
		return config_uat;
	}
	public void setConfig_uat(int config_uat) {
		this.config_uat = config_uat;
	}
	public int getConfig_prod() {
		return config_prod;
	}
	public void setConfig_prod(int config_prod) {
		this.config_prod = config_prod;
	}
	public int getCcb_product_backlog() {
		return ccb_product_backlog;
	}
	public void setCcb_product_backlog(int ccb_product_backlog) {
		this.ccb_product_backlog = ccb_product_backlog;
	}
	public int getCcb_qa() {
		return ccb_qa;
	}
	public void setCcb_qa(int ccb_qa) {
		this.ccb_qa = ccb_qa;
	}
	public int getCcb_uat() {
		return ccb_uat;
	}
	public void setCcb_uat(int ccb_uat) {
		this.ccb_uat = ccb_uat;
	}
	public int getCcb_prod() {
		return ccb_prod;
	}
	public void setCcb_prod(int ccb_prod) {
		this.ccb_prod = ccb_prod;
	}
	public int getAd_product_backlog() {
		return ad_product_backlog;
	}
	public void setAd_product_backlog(int ad_product_backlog) {
		this.ad_product_backlog = ad_product_backlog;
	}
	public int getAd_qa() {
		return ad_qa;
	}
	public void setAd_qa(int ad_qa) {
		this.ad_qa = ad_qa;
	}
	public int getAd_uat() {
		return ad_uat;
	}
	public void setAd_uat(int ad_uat) {
		this.ad_uat = ad_uat;
	}
	public int getAd_prod() {
		return ad_prod;
	}
	public void setAd_prod(int ad_prod) {
		this.ad_prod = ad_prod;
	}
	public int getDup_product_backlog() {
		return dup_product_backlog;
	}
	public void setDup_product_backlog(int dup_product_backlog) {
		this.dup_product_backlog = dup_product_backlog;
	}
	public int getDup_qa() {
		return dup_qa;
	}
	public void setDup_qa(int dup_qa) {
		this.dup_qa = dup_qa;
	}
	public int getDup_uat() {
		return dup_uat;
	}
	public void setDup_uat(int dup_uat) {
		this.dup_uat = dup_uat;
	}
	public int getDup_prod() {
		return dup_prod;
	}
	public void setDup_prod(int dup_prod) {
		this.dup_prod = dup_prod;
	}
	public int getNad_product_backlog() {
		return nad_product_backlog;
	}
	public void setNad_product_backlog(int nad_product_backlog) {
		this.nad_product_backlog = nad_product_backlog;
	}
	public int getNad_qa() {
		return nad_qa;
	}
	public void setNad_qa(int nad_qa) {
		this.nad_qa = nad_qa;
	}
	public int getNad_uat() {
		return nad_uat;
	}
	public void setNad_uat(int nad_uat) {
		this.nad_uat = nad_uat;
	}
	public int getNad_prod() {
		return nad_prod;
	}
	public void setNad_prod(int nad_prod) {
		this.nad_prod = nad_prod;
	}
	public int getBsi_product_backlog() {
		return bsi_product_backlog;
	}
	public void setBsi_product_backlog(int bsi_product_backlog) {
		this.bsi_product_backlog = bsi_product_backlog;
	}
	public int getBsi_qa() {
		return bsi_qa;
	}
	public void setBsi_qa(int bsi_qa) {
		this.bsi_qa = bsi_qa;
	}
	public int getBsi_uat() {
		return bsi_uat;
	}
	public void setBsi_uat(int bsi_uat) {
		this.bsi_uat = bsi_uat;
	}
	public int getBsi_prod() {
		return bsi_prod;
	}
	public void setBsi_prod(int bsi_prod) {
		this.bsi_prod = bsi_prod;
	}
	public int getUtr_product_backlog() {
		return utr_product_backlog;
	}
	public void setUtr_product_backlog(int utr_product_backlog) {
		this.utr_product_backlog = utr_product_backlog;
	}
	public int getUtr_qa() {
		return utr_qa;
	}
	public void setUtr_qa(int utr_qa) {
		this.utr_qa = utr_qa;
	}
	public int getUtr_uat() {
		return utr_uat;
	}
	public void setUtr_uat(int utr_uat) {
		this.utr_uat = utr_uat;
	}
	public int getUtr_prod() {
		return utr_prod;
	}
	public void setUtr_prod(int utr_prod) {
		this.utr_prod = utr_prod;
	}
	public int getPd_product_backlog() {
		return pd_product_backlog;
	}
	public void setPd_product_backlog(int pd_product_backlog) {
		this.pd_product_backlog = pd_product_backlog;
	}
	public int getPd_qa() {
		return pd_qa;
	}
	public void setPd_qa(int pd_qa) {
		this.pd_qa = pd_qa;
	}
	public int getPd_uat() {
		return pd_uat;
	}
	public void setPd_uat(int pd_uat) {
		this.pd_uat = pd_uat;
	}
	public int getPd_prod() {
		return pd_prod;
	}
	public void setPd_prod(int pd_prod) {
		this.pd_prod = pd_prod;
	}
	public int getIi_product_backlog() {
		return ii_product_backlog;
	}
	public void setIi_product_backlog(int ii_product_backlog) {
		this.ii_product_backlog = ii_product_backlog;
	}
	public int getIi_qa() {
		return ii_qa;
	}
	public void setIi_qa(int ii_qa) {
		this.ii_qa = ii_qa;
	}
	public int getIi_uat() {
		return ii_uat;
	}
	public void setIi_uat(int ii_uat) {
		this.ii_uat = ii_uat;
	}
	public int getIi_prod() {
		return ii_prod;
	}
	public void setIi_prod(int ii_prod) {
		this.ii_prod = ii_prod;
	}
	public int getDi_product_backlog() {
		return di_product_backlog;
	}
	public void setDi_product_backlog(int di_product_backlog) {
		this.di_product_backlog = di_product_backlog;
	}
	public int getDi_qa() {
		return di_qa;
	}
	public void setDi_qa(int di_qa) {
		this.di_qa = di_qa;
	}
	public int getDi_uat() {
		return di_uat;
	}
	public void setDi_uat(int di_uat) {
		this.di_uat = di_uat;
	}
	public int getDi_prod() {
		return di_prod;
	}
	public void setDi_prod(int di_prod) {
		this.di_prod = di_prod;
	}
	public int getRo_qa() {
		return ro_qa;
	}
	public void setRo_qa(int ro_qa) {
		this.ro_qa = ro_qa;
	}
	public int getRo_uat() {
		return ro_uat;
	}
	public void setRo_uat(int ro_uat) {
		this.ro_uat = ro_uat;
	}
	public int getRo_prod() {
		return ro_prod;
	}
	public void setRo_prod(int ro_prod) {
		this.ro_prod = ro_prod;
	}
	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the weekType
	 */
	public String getWeekType() {
		return weekType;
	}
	/**
	 * @param weekType the weekType to set
	 */
	public void setWeekType(String weekType) {
		this.weekType = weekType;
	}
	/**
	 * @return the weekCount
	 */
	public String getWeekCount() {
		return weekCount;
	}
	/**
	 * @param weekCount the weekCount to set
	 */
	public void setWeekCount(String weekCount) {
		this.weekCount = weekCount;
	}
	/**
	 * @return the weeks
	 */
	public List<String> getWeeks() {
		return weeks;
	}
	/**
	 * @return the projectList
	 */
	public ArrayList<String> getProjectList() {
		return projectList;
	}
	/**
	 * @param projectList the projectList to set
	 */
	public void setProjectList(ArrayList<String> projectList) {
		this.projectList = projectList;
	}
	/**
	 * @param weeks the weeks to set
	 */
	public void setWeeks(List<String> weeks) {
		this.weeks = weeks;
	}
	/**
	 * @return the projectStatus
	 */
	public String getProjectStatus() {
		return projectStatus;
	}
	/**
	 * @param projectStatus the projectStatus to set
	 */
	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}
	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}
	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	
	
	
}
