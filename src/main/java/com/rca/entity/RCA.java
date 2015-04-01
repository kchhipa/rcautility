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
	public int plan_product_backlog;
	public int plan_qa;
	public int plan_uat;
	public int plan_prod;
	public int rate_product_backlog;
	public int rate_qa;
	public int rate_uat;
	public int rate_prod;
	public int rpa_product_backlog;
	public int rpa_qa;
	public int rpa_uat;
	public int rpa_prod;
	public int ac_product_backlog;
	public int ac_qa;
	public int ac_uat;
	public int ac_prod;
	public int ti_product_backlog;
	public int ti_qa;
	public int ti_uat;
	public int ti_prod;
	public int dp_product_backlog;
	public int dp_qa;
	public int dp_uat;
	public int dp_prod;
	public int env_product_backlog;
	public int env_qa;
	public int env_uat;
	public int env_prod;
	public int co_product_backlog;
	public int co_qa;
	public int co_uat;
	public int co_prod;
	public int ffm_product_backlog;
	public int ffm_qa;
	public int ffm_uat;
	public int ffm_prod;
	public int crmesb_product_backlog;
	public int crmesb_qa;
	public int crmesb_uat;
	public int crmesb_prod;
	public int otp_product_backlog;
	public int otp_qa;
	public int otp_uat;
	public int otp_prod;
	public int pmuu_product_backlog;
	public int pmuu_qa;
	public int pmuu_uat;
	public int pmuu_prod;
	public int io_product_backlog;
	public int io_qa;
	public int io_uat;
	public int io_prod;
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
	public int di_product_backlog;
	public int di_qa;
	public int di_uat;
	public int di_prod;			
	public int ro_qa;
	public int ro_uat;
	public int ro_prod;
	public String overview_message;
	
	private List<String> weeks;
	private String startDate;
	private String endDate;
	private String weekType;
	private String weekCount;
	private ArrayList<String> projectList;
	private String[] project;
	private String projectStatus;
	private String projectName;
	private ArrayList<RCA> projectDetailList;
	
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
	public int getPlan_product_backlog() {
		return plan_product_backlog;
	}
	public void setPlan_product_backlog(int plan_product_backlog) {
		this.plan_product_backlog = plan_product_backlog;
	}
	public int getPlan_qa() {
		return plan_qa;
	}
	public void setPlan_qa(int plan_qa) {
		this.plan_qa = plan_qa;
	}
	public int getPlan_uat() {
		return plan_uat;
	}
	public void setPlan_uat(int plan_uat) {
		this.plan_uat = plan_uat;
	}
	public int getPlan_prod() {
		return plan_prod;
	}
	public void setPlan_prod(int plan_prod) {
		this.plan_prod = plan_prod;
	}
	public int getRate_product_backlog() {
		return rate_product_backlog;
	}
	public void setRate_product_backlog(int rate_product_backlog) {
		this.rate_product_backlog = rate_product_backlog;
	}
	public int getRate_qa() {
		return rate_qa;
	}
	public void setRate_qa(int rate_qa) {
		this.rate_qa = rate_qa;
	}
	public int getRate_uat() {
		return rate_uat;
	}
	public void setRate_uat(int rate_uat) {
		this.rate_uat = rate_uat;
	}
	public int getRate_prod() {
		return rate_prod;
	}
	public void setRate_prod(int rate_prod) {
		this.rate_prod = rate_prod;
	}
	public int getRpa_product_backlog() {
		return rpa_product_backlog;
	}
	public void setRpa_product_backlog(int rpa_product_backlog) {
		this.rpa_product_backlog = rpa_product_backlog;
	}
	public int getRpa_qa() {
		return rpa_qa;
	}
	public void setRpa_qa(int rpa_qa) {
		this.rpa_qa = rpa_qa;
	}
	public int getRpa_uat() {
		return rpa_uat;
	}
	public void setRpa_uat(int rpa_uat) {
		this.rpa_uat = rpa_uat;
	}
	public int getRpa_prod() {
		return rpa_prod;
	}
	public void setRpa_prod(int rpa_prod) {
		this.rpa_prod = rpa_prod;
	}
	public int getAc_product_backlog() {
		return ac_product_backlog;
	}
	public void setAc_product_backlog(int ac_product_backlog) {
		this.ac_product_backlog = ac_product_backlog;
	}
	public int getAc_qa() {
		return ac_qa;
	}
	public void setAc_qa(int ac_qa) {
		this.ac_qa = ac_qa;
	}
	public int getAc_uat() {
		return ac_uat;
	}
	public void setAc_uat(int ac_uat) {
		this.ac_uat = ac_uat;
	}
	public int getAc_prod() {
		return ac_prod;
	}
	public void setAc_prod(int ac_prod) {
		this.ac_prod = ac_prod;
	}
	public int getTi_product_backlog() {
		return ti_product_backlog;
	}
	public void setTi_product_backlog(int ti_product_backlog) {
		this.ti_product_backlog = ti_product_backlog;
	}
	public int getTi_qa() {
		return ti_qa;
	}
	public void setTi_qa(int ti_qa) {
		this.ti_qa = ti_qa;
	}
	public int getTi_uat() {
		return ti_uat;
	}
	public void setTi_uat(int ti_uat) {
		this.ti_uat = ti_uat;
	}
	public int getTi_prod() {
		return ti_prod;
	}
	public void setTi_prod(int ti_prod) {
		this.ti_prod = ti_prod;
	}
	public int getDp_product_backlog() {
		return dp_product_backlog;
	}
	public void setDp_product_backlog(int dp_product_backlog) {
		this.dp_product_backlog = dp_product_backlog;
	}
	public int getDp_qa() {
		return dp_qa;
	}
	public void setDp_qa(int dp_qa) {
		this.dp_qa = dp_qa;
	}
	public int getDp_uat() {
		return dp_uat;
	}
	public void setDp_uat(int dp_uat) {
		this.dp_uat = dp_uat;
	}
	public int getDp_prod() {
		return dp_prod;
	}
	public void setDp_prod(int dp_prod) {
		this.dp_prod = dp_prod;
	}
	public int getEnv_product_backlog() {
		return env_product_backlog;
	}
	public void setEnv_product_backlog(int env_product_backlog) {
		this.env_product_backlog = env_product_backlog;
	}
	public int getEnv_qa() {
		return env_qa;
	}
	public void setEnv_qa(int env_qa) {
		this.env_qa = env_qa;
	}
	public int getEnv_uat() {
		return env_uat;
	}
	public void setEnv_uat(int env_uat) {
		this.env_uat = env_uat;
	}
	public int getEnv_prod() {
		return env_prod;
	}
	public void setEnv_prod(int env_prod) {
		this.env_prod = env_prod;
	}
	public int getCo_product_backlog() {
		return co_product_backlog;
	}
	public void setCo_product_backlog(int co_product_backlog) {
		this.co_product_backlog = co_product_backlog;
	}
	public int getCo_qa() {
		return co_qa;
	}
	public void setCo_qa(int co_qa) {
		this.co_qa = co_qa;
	}
	public int getCo_uat() {
		return co_uat;
	}
	public void setCo_uat(int co_uat) {
		this.co_uat = co_uat;
	}
	public int getCo_prod() {
		return co_prod;
	}
	public void setCo_prod(int co_prod) {
		this.co_prod = co_prod;
	}
	public int getFfm_product_backlog() {
		return ffm_product_backlog;
	}
	public void setFfm_product_backlog(int ffm_product_backlog) {
		this.ffm_product_backlog = ffm_product_backlog;
	}
	public int getFfm_qa() {
		return ffm_qa;
	}
	public void setFfm_qa(int ffm_qa) {
		this.ffm_qa = ffm_qa;
	}
	public int getFfm_uat() {
		return ffm_uat;
	}
	public void setFfm_uat(int ffm_uat) {
		this.ffm_uat = ffm_uat;
	}
	public int getFfm_prod() {
		return ffm_prod;
	}
	public void setFfm_prod(int ffm_prod) {
		this.ffm_prod = ffm_prod;
	}
	public int getCrmesb_product_backlog() {
		return crmesb_product_backlog;
	}
	public void setCrmesb_product_backlog(int crmesb_product_backlog) {
		this.crmesb_product_backlog = crmesb_product_backlog;
	}
	public int getCrmesb_qa() {
		return crmesb_qa;
	}
	public void setCrmesb_qa(int crmesb_qa) {
		this.crmesb_qa = crmesb_qa;
	}
	public int getCrmesb_uat() {
		return crmesb_uat;
	}
	public void setCrmesb_uat(int crmesb_uat) {
		this.crmesb_uat = crmesb_uat;
	}
	public int getCrmesb_prod() {
		return crmesb_prod;
	}
	public void setCrmesb_prod(int crmesb_prod) {
		this.crmesb_prod = crmesb_prod;
	}
	public int getOtp_product_backlog() {
		return otp_product_backlog;
	}
	public void setOtp_product_backlog(int otp_product_backlog) {
		this.otp_product_backlog = otp_product_backlog;
	}
	public int getOtp_qa() {
		return otp_qa;
	}
	public void setOtp_qa(int otp_qa) {
		this.otp_qa = otp_qa;
	}
	public int getOtp_uat() {
		return otp_uat;
	}
	public void setOtp_uat(int otp_uat) {
		this.otp_uat = otp_uat;
	}
	public int getOtp_prod() {
		return otp_prod;
	}
	public void setOtp_prod(int otp_prod) {
		this.otp_prod = otp_prod;
	}
	public int getPmuu_product_backlog() {
		return pmuu_product_backlog;
	}
	public void setPmuu_product_backlog(int pmuu_product_backlog) {
		this.pmuu_product_backlog = pmuu_product_backlog;
	}
	public int getPmuu_qa() {
		return pmuu_qa;
	}
	public void setPmuu_qa(int pmuu_qa) {
		this.pmuu_qa = pmuu_qa;
	}
	public int getPmuu_uat() {
		return pmuu_uat;
	}
	public void setPmuu_uat(int pmuu_uat) {
		this.pmuu_uat = pmuu_uat;
	}
	public int getPmuu_prod() {
		return pmuu_prod;
	}
	public void setPmuu_prod(int pmuu_prod) {
		this.pmuu_prod = pmuu_prod;
	}
	public int getIo_product_backlog() {
		return io_product_backlog;
	}
	public void setIo_product_backlog(int io_product_backlog) {
		this.io_product_backlog = io_product_backlog;
	}
	public int getIo_qa() {
		return io_qa;
	}
	public void setIo_qa(int io_qa) {
		this.io_qa = io_qa;
	}
	public int getIo_uat() {
		return io_uat;
	}
	public void setIo_uat(int io_uat) {
		this.io_uat = io_uat;
	}
	public int getIo_prod() {
		return io_prod;
	}
	public void setIo_prod(int io_prod) {
		this.io_prod = io_prod;
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
	public ArrayList<RCA> getProjectDetailList() {
		return projectDetailList;
	}
	public void setProjectDetailList(ArrayList<RCA> projectDetailList) {
		this.projectDetailList = projectDetailList;
	}
	public String getOverview_message() {
		return overview_message;
	}
	public void setOverview_message(String overview_message) {
		this.overview_message = overview_message;
	}

}
