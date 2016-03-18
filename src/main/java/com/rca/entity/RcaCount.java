package com.rca.entity;

// default package
// Generated Mar 10, 2015 7:18:09 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * RcaCount generated by hbm2java
 */
@Entity
@Table(name = "rca_count", catalog = "rcautility")
public class RcaCount implements java.io.Serializable {

	private int rcaCountId;
	private ProjectDetails projectDetails;
	private int mrProductBacklog;
	private int mrQa;
	private int mrUat;
	private int mrProd;
	private int crProductBacklog;
	private int crQa;
	private int crUat;
	private int crProd;
	private int configProductBacklog;
	private int configQa;
	private int configUat;
	private int configProd;
	private int ccbProductBacklog;
	private int ccbQa;
	private int ccbUat;
	private int ccbProd;
	private int adProductBacklog;
	private int adQa;
	private int adUat;
	private int adProd;
	private int dupProductBacklog;
	private int dupQa;
	private int dupUat;
	private int dupProd;
	private int nadProductBacklog;
	private int nadQa;
	private int nadUat;
	private int nadProd;
	private int bsiProductBacklog;
	private int bsiQa;
	private int bsiUat;
	private int bsiProd;
	private int utrProductBacklog;
	private int utrQa;
	private int utrUat;
	private int utrProd;
	private int pdProductBacklog;
	private int pdQa;
	private int pdUat;
	private int pdProd;
	private int iiProductBacklog;
	private int iiQa;
	private int iiUat;
	private int iiProd;
	private int diProductBacklog;
	private int diQa;
	private int diUat;
	private int diProd;
	private int roQa;
	private int roUat;
	private int roProd;
	private String week;
	private int planProductBacklog;
	private int planQa;
	private int planUat;
	private int planProd;
	private int rateProductBacklog;
	private int rateQa;
	private int rateUat;
	private int rateProd;
	private int rpaProductBacklog;
	private int rpaQa;
	private int rpaUat;
	private int rpaProd;
	private int acProductBacklog;
	private int acQa;
	private int acUat;
	private int acProd;
	private int tiProductBacklog;
	private int tiQa;
	private int tiUat;
	private int tiProd;
	private int dpProductBacklog;
	private int dpQa;
	private int dpUat;
	private int dpProd;
	private int envProductBacklog;
	private int envQa;
	private int envUat;
	private int envProd;
	private int coProductBacklog;
	private int coQa;
	private int coUat;
	private int coProd;
	private int ffmProductBacklog;
	private int ffmQa;
	private int ffmUat;
	private int ffmProd;
	private int crmesbProductBacklog;
	private int crmesbQa;
	private int crmesbUat;
	private int crmesbProd;
	private int otpProductBacklog;
	private int otpQa;
	private int otpUat;
	private int otpProd;
	private int pmuuProductBacklog;
	private int pmuuQa;
	private int pmuuUat;
	private int pmuuProd;
	private int ioProductBacklog;
	private int ioQa;
	private int ioUat;
	private int ioProd;
	private String overviewMessage;
	private String risksIssues;
	
	/* Changes for Non RCA field addition */
	private int nrProductBacklog;
	private int nrQa;
	private int nrUat;
	private int nrProd;
	
	/* Changes for Close Ticket field addition */
	private int closeTicketProductBacklog;
	private int closeTicketQa;
	private int closeTicketUat;
	private int closeTicketProd;

	public RcaCount() {
	}

	public RcaCount(ProjectDetails projectDetails, int mrProductBacklog,
			int mrQa, int mrUat, int mrProd,
			int crProductBacklog, int crQa, int crUat,
			int crProd, int configProductBacklog, int configQa,
			int configUat, int configProd, int ccbProductBacklog,
			int ccbQa, int ccbUat, int ccbProd,
			int adProductBacklog, int adQa, int adUat,
			int adProd, int dupProductBacklog, int dupQa,
			int dupUat, int dupProd, int nadProductBacklog,
			int nadQa, int nadUat, int nadProd,
			int bsiProductBacklog, int bsiQa, int bsiUat,
			int bsiProd, int utrProductBacklog, int utrQa,
			int utrUat, int utrProd, int pdProductBacklog,
			int pdQa, int pdUat, int pdProd,
			int iiProductBacklog, int iiQa, int iiUat,
			int iiProd, int diProductBacklog, int diQa,
			int diUat, int diProd, int roQa, int roUat,
			int roProd, String week, int planProductBacklog,
			int planQa, int planUat, int planProd,
			int rateProductBacklog, int rateQa, int rateUat,
			int rateProd, int rpaProductBacklog, int rpaQa,
			int rpaUat, int rpaProd, int acProductBacklog,
			int acQa, int acUat, int acProd,
			int tiProductBacklog, int tiQa, int tiUat,
			int tiProd, int dpProductBacklog, int dpQa,
			int dpUat, int dpProd, int envProductBacklog,
			int envQa, int envUat, int envProd,
			int coProductBacklog, int coQa, int coUat,
			int coProd, int ffmProductBacklog, int ffmQa,
			int ffmUat, int ffmProd, int crmesbProductBacklog,
			int crmesbQa, int crmesbUat, int crmesbProd,
			int otpProductBacklog, int otpQa, int otpUat,
			int otpProd, int pmuuProductBacklog, int pmuuQa,
			int pmuuUat, int pmuuProd, int ioProductBacklog,
			int ioQa, int ioUat, int ioProd, String overviewMessage, String risksIssues,
			int nrProductBacklog, int nrQa, int nrUat, int nrProd, int closeTicketProductBacklog, int closeTicketQa, int closeTicketUat, int closeTicketProd) {
		this.projectDetails = projectDetails;
		this.mrProductBacklog = mrProductBacklog;
		this.mrQa = mrQa;
		this.mrUat = mrUat;
		this.mrProd = mrProd;
		this.crProductBacklog = crProductBacklog;
		this.crQa = crQa;
		this.crUat = crUat;
		this.crProd = crProd;
		this.configProductBacklog = configProductBacklog;
		this.configQa = configQa;
		this.configUat = configUat;
		this.configProd = configProd;
		this.ccbProductBacklog = ccbProductBacklog;
		this.ccbQa = ccbQa;
		this.ccbUat = ccbUat;
		this.ccbProd = ccbProd;
		this.adProductBacklog = adProductBacklog;
		this.adQa = adQa;
		this.adUat = adUat;
		this.adProd = adProd;
		this.dupProductBacklog = dupProductBacklog;
		this.dupQa = dupQa;
		this.dupUat = dupUat;
		this.dupProd = dupProd;
		this.nadProductBacklog = nadProductBacklog;
		this.nadQa = nadQa;
		this.nadUat = nadUat;
		this.nadProd = nadProd;
		this.bsiProductBacklog = bsiProductBacklog;
		this.bsiQa = bsiQa;
		this.bsiUat = bsiUat;
		this.bsiProd = bsiProd;
		this.utrProductBacklog = utrProductBacklog;
		this.utrQa = utrQa;
		this.utrUat = utrUat;
		this.utrProd = utrProd;
		this.pdProductBacklog = pdProductBacklog;
		this.pdQa = pdQa;
		this.pdUat = pdUat;
		this.pdProd = pdProd;
		this.iiProductBacklog = iiProductBacklog;
		this.iiQa = iiQa;
		this.iiUat = iiUat;
		this.iiProd = iiProd;
		this.diProductBacklog = diProductBacklog;
		this.diQa = diQa;
		this.diUat = diUat;
		this.diProd = diProd;
		this.roQa = roQa;
		this.roUat = roUat;
		this.roProd = roProd;
		this.week = week;
		this.planProductBacklog = planProductBacklog;
		this.planQa = planQa;
		this.planUat = planUat;
		this.planProd = planProd;
		this.rateProductBacklog = rateProductBacklog;
		this.rateQa = rateQa;
		this.rateUat = rateUat;
		this.rateProd = rateProd;
		this.rpaProductBacklog = rpaProductBacklog;
		this.rpaQa = rpaQa;
		this.rpaUat = rpaUat;
		this.rpaProd = rpaProd;
		this.acProductBacklog = acProductBacklog;
		this.acQa = acQa;
		this.acUat = acUat;
		this.acProd = acProd;
		this.tiProductBacklog = tiProductBacklog;
		this.tiQa = tiQa;
		this.tiUat = tiUat;
		this.tiProd = tiProd;
		this.dpProductBacklog = dpProductBacklog;
		this.dpQa = dpQa;
		this.dpUat = dpUat;
		this.dpProd = dpProd;
		this.envProductBacklog = envProductBacklog;
		this.envQa = envQa;
		this.envUat = envUat;
		this.envProd = envProd;
		this.coProductBacklog = coProductBacklog;
		this.coQa = coQa;
		this.coUat = coUat;
		this.coProd = coProd;
		this.ffmProductBacklog = ffmProductBacklog;
		this.ffmQa = ffmQa;
		this.ffmUat = ffmUat;
		this.ffmProd = ffmProd;
		this.crmesbProductBacklog = crmesbProductBacklog;
		this.crmesbQa = crmesbQa;
		this.crmesbUat = crmesbUat;
		this.crmesbProd = crmesbProd;
		this.otpProductBacklog = otpProductBacklog;
		this.otpQa = otpQa;
		this.otpUat = otpUat;
		this.otpProd = otpProd;
		this.pmuuProductBacklog = pmuuProductBacklog;
		this.pmuuQa = pmuuQa;
		this.pmuuUat = pmuuUat;
		this.pmuuProd = pmuuProd;
		this.ioProductBacklog = ioProductBacklog;
		this.ioQa = ioQa;
		this.ioUat = ioUat;
		this.ioProd = ioProd;
		this.overviewMessage = overviewMessage;
		this.risksIssues = risksIssues;
		/* Changes for Non RCA field addition */
		this.nrProductBacklog = nrProductBacklog;
		this.nrQa = nrQa;
		this.nrUat = nrUat;
		this.nrProd = nrProd;
		
		/* Changes for Close Ticket field addition */
		this.closeTicketProductBacklog = closeTicketProductBacklog;
		this.closeTicketQa = closeTicketQa;
		this.closeTicketUat = closeTicketUat;
		this.closeTicketProd = closeTicketProd;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "rca_count_id", unique = true, nullable = false)
	public int getRcaCountId() {
		return this.rcaCountId;
	}

	public void setRcaCountId(int rcaCountId) {
		this.rcaCountId = rcaCountId;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade= CascadeType.ALL)
	@JoinColumn(name = "project_id")
	public ProjectDetails getProjectDetails() {
		return this.projectDetails;
	}

	public void setProjectDetails(ProjectDetails projectDetails) {
		this.projectDetails = projectDetails;
	}

	@Column(name = "mr_product_backlog")
	public int getMrProductBacklog() {
		return this.mrProductBacklog;
	}

	public void setMrProductBacklog(int mrProductBacklog) {
		this.mrProductBacklog = mrProductBacklog;
	}

	@Column(name = "mr_qa")
	public int getMrQa() {
		return this.mrQa;
	}

	public void setMrQa(int mrQa) {
		this.mrQa = mrQa;
	}

	@Column(name = "mr_uat")
	public int getMrUat() {
		return this.mrUat;
	}

	public void setMrUat(int mrUat) {
		this.mrUat = mrUat;
	}

	@Column(name = "mr_prod")
	public int getMrProd() {
		return this.mrProd;
	}

	public void setMrProd(int mrProd) {
		this.mrProd = mrProd;
	}

	@Column(name = "cr_product_backlog")
	public int getCrProductBacklog() {
		return this.crProductBacklog;
	}

	public void setCrProductBacklog(int crProductBacklog) {
		this.crProductBacklog = crProductBacklog;
	}

	@Column(name = "cr_qa")
	public int getCrQa() {
		return this.crQa;
	}

	public void setCrQa(int crQa) {
		this.crQa = crQa;
	}

	@Column(name = "cr_uat")
	public int getCrUat() {
		return this.crUat;
	}

	public void setCrUat(int crUat) {
		this.crUat = crUat;
	}

	@Column(name = "cr_prod")
	public int getCrProd() {
		return this.crProd;
	}

	public void setCrProd(int crProd) {
		this.crProd = crProd;
	}

	@Column(name = "config_product_backlog")
	public int getConfigProductBacklog() {
		return this.configProductBacklog;
	}

	public void setConfigProductBacklog(int configProductBacklog) {
		this.configProductBacklog = configProductBacklog;
	}

	@Column(name = "config_qa")
	public int getConfigQa() {
		return this.configQa;
	}

	public void setConfigQa(int configQa) {
		this.configQa = configQa;
	}

	@Column(name = "config_uat")
	public int getConfigUat() {
		return this.configUat;
	}

	public void setConfigUat(int configUat) {
		this.configUat = configUat;
	}

	@Column(name = "config_prod")
	public int getConfigProd() {
		return this.configProd;
	}

	public void setConfigProd(int configProd) {
		this.configProd = configProd;
	}

	@Column(name = "ccb_product_backlog")
	public int getCcbProductBacklog() {
		return this.ccbProductBacklog;
	}

	public void setCcbProductBacklog(int ccbProductBacklog) {
		this.ccbProductBacklog = ccbProductBacklog;
	}

	@Column(name = "ccb_qa")
	public int getCcbQa() {
		return this.ccbQa;
	}

	public void setCcbQa(int ccbQa) {
		this.ccbQa = ccbQa;
	}

	@Column(name = "ccb_uat")
	public int getCcbUat() {
		return this.ccbUat;
	}

	public void setCcbUat(int ccbUat) {
		this.ccbUat = ccbUat;
	}

	@Column(name = "ccb_prod")
	public int getCcbProd() {
		return this.ccbProd;
	}

	public void setCcbProd(int ccbProd) {
		this.ccbProd = ccbProd;
	}

	@Column(name = "ad_product_backlog")
	public int getAdProductBacklog() {
		return this.adProductBacklog;
	}

	public void setAdProductBacklog(int adProductBacklog) {
		this.adProductBacklog = adProductBacklog;
	}

	@Column(name = "ad_qa")
	public int getAdQa() {
		return this.adQa;
	}

	public void setAdQa(int adQa) {
		this.adQa = adQa;
	}

	@Column(name = "ad_uat")
	public int getAdUat() {
		return this.adUat;
	}

	public void setAdUat(int adUat) {
		this.adUat = adUat;
	}

	@Column(name = "ad_prod")
	public int getAdProd() {
		return this.adProd;
	}

	public void setAdProd(int adProd) {
		this.adProd = adProd;
	}

	@Column(name = "dup_product_backlog")
	public int getDupProductBacklog() {
		return this.dupProductBacklog;
	}

	public void setDupProductBacklog(int dupProductBacklog) {
		this.dupProductBacklog = dupProductBacklog;
	}

	@Column(name = "dup_qa")
	public int getDupQa() {
		return this.dupQa;
	}

	public void setDupQa(int dupQa) {
		this.dupQa = dupQa;
	}

	@Column(name = "dup_uat")
	public int getDupUat() {
		return this.dupUat;
	}

	public void setDupUat(int dupUat) {
		this.dupUat = dupUat;
	}

	@Column(name = "dup_prod")
	public int getDupProd() {
		return this.dupProd;
	}

	public void setDupProd(int dupProd) {
		this.dupProd = dupProd;
	}

	@Column(name = "nad_product_backlog")
	public int getNadProductBacklog() {
		return this.nadProductBacklog;
	}

	public void setNadProductBacklog(int nadProductBacklog) {
		this.nadProductBacklog = nadProductBacklog;
	}

	@Column(name = "nad_qa")
	public int getNadQa() {
		return this.nadQa;
	}

	public void setNadQa(int nadQa) {
		this.nadQa = nadQa;
	}

	@Column(name = "nad_uat")
	public int getNadUat() {
		return this.nadUat;
	}

	public void setNadUat(int nadUat) {
		this.nadUat = nadUat;
	}

	@Column(name = "nad_prod")
	public int getNadProd() {
		return this.nadProd;
	}

	public void setNadProd(int nadProd) {
		this.nadProd = nadProd;
	}

	@Column(name = "bsi_product_backlog")
	public int getBsiProductBacklog() {
		return this.bsiProductBacklog;
	}

	public void setBsiProductBacklog(int bsiProductBacklog) {
		this.bsiProductBacklog = bsiProductBacklog;
	}

	@Column(name = "bsi_qa")
	public int getBsiQa() {
		return this.bsiQa;
	}

	public void setBsiQa(int bsiQa) {
		this.bsiQa = bsiQa;
	}

	@Column(name = "bsi_uat")
	public int getBsiUat() {
		return this.bsiUat;
	}

	public void setBsiUat(int bsiUat) {
		this.bsiUat = bsiUat;
	}

	@Column(name = "bsi_prod")
	public int getBsiProd() {
		return this.bsiProd;
	}

	public void setBsiProd(int bsiProd) {
		this.bsiProd = bsiProd;
	}

	@Column(name = "utr_product_backlog")
	public int getUtrProductBacklog() {
		return this.utrProductBacklog;
	}

	public void setUtrProductBacklog(int utrProductBacklog) {
		this.utrProductBacklog = utrProductBacklog;
	}

	@Column(name = "utr_qa")
	public int getUtrQa() {
		return this.utrQa;
	}

	public void setUtrQa(int utrQa) {
		this.utrQa = utrQa;
	}

	@Column(name = "utr_uat")
	public int getUtrUat() {
		return this.utrUat;
	}

	public void setUtrUat(int utrUat) {
		this.utrUat = utrUat;
	}

	@Column(name = "utr_prod")
	public int getUtrProd() {
		return this.utrProd;
	}

	public void setUtrProd(int utrProd) {
		this.utrProd = utrProd;
	}

	@Column(name = "pd_product_backlog")
	public int getPdProductBacklog() {
		return this.pdProductBacklog;
	}

	public void setPdProductBacklog(int pdProductBacklog) {
		this.pdProductBacklog = pdProductBacklog;
	}

	@Column(name = "pd_qa")
	public int getPdQa() {
		return this.pdQa;
	}

	public void setPdQa(int pdQa) {
		this.pdQa = pdQa;
	}

	@Column(name = "pd_uat")
	public int getPdUat() {
		return this.pdUat;
	}

	public void setPdUat(int pdUat) {
		this.pdUat = pdUat;
	}

	@Column(name = "pd_prod")
	public int getPdProd() {
		return this.pdProd;
	}

	public void setPdProd(int pdProd) {
		this.pdProd = pdProd;
	}

	@Column(name = "ii_product_backlog")
	public int getIiProductBacklog() {
		return this.iiProductBacklog;
	}

	public void setIiProductBacklog(int iiProductBacklog) {
		this.iiProductBacklog = iiProductBacklog;
	}

	@Column(name = "ii_qa")
	public int getIiQa() {
		return this.iiQa;
	}

	public void setIiQa(int iiQa) {
		this.iiQa = iiQa;
	}

	@Column(name = "ii_uat")
	public int getIiUat() {
		return this.iiUat;
	}

	public void setIiUat(int iiUat) {
		this.iiUat = iiUat;
	}

	@Column(name = "ii_prod")
	public int getIiProd() {
		return this.iiProd;
	}

	public void setIiProd(int iiProd) {
		this.iiProd = iiProd;
	}

	@Column(name = "di_product_backlog")
	public int getDiProductBacklog() {
		return this.diProductBacklog;
	}

	public void setDiProductBacklog(int diProductBacklog) {
		this.diProductBacklog = diProductBacklog;
	}

	@Column(name = "di_qa")
	public int getDiQa() {
		return this.diQa;
	}

	public void setDiQa(int diQa) {
		this.diQa = diQa;
	}

	@Column(name = "di_uat")
	public int getDiUat() {
		return this.diUat;
	}

	public void setDiUat(int diUat) {
		this.diUat = diUat;
	}

	@Column(name = "di_prod")
	public int getDiProd() {
		return this.diProd;
	}

	public void setDiProd(int diProd) {
		this.diProd = diProd;
	}

	@Column(name = "ro_qa")
	public int getRoQa() {
		return this.roQa;
	}

	public void setRoQa(int roQa) {
		this.roQa = roQa;
	}

	@Column(name = "ro_uat")
	public int getRoUat() {
		return this.roUat;
	}

	public void setRoUat(int roUat) {
		this.roUat = roUat;
	}

	@Column(name = "ro_prod")
	public int getRoProd() {
		return this.roProd;
	}

	public void setRoProd(int roProd) {
		this.roProd = roProd;
	}

	@Column(name = "week", length = 50)
	public String getWeek() {
		return this.week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	@Column(name = "plan_product_backlog")
	public int getPlanProductBacklog() {
		return this.planProductBacklog;
	}

	public void setPlanProductBacklog(int planProductBacklog) {
		this.planProductBacklog = planProductBacklog;
	}

	@Column(name = "plan_qa")
	public int getPlanQa() {
		return this.planQa;
	}

	public void setPlanQa(int planQa) {
		this.planQa = planQa;
	}

	@Column(name = "plan_uat")
	public int getPlanUat() {
		return this.planUat;
	}

	public void setPlanUat(int planUat) {
		this.planUat = planUat;
	}

	@Column(name = "plan_prod")
	public int getPlanProd() {
		return this.planProd;
	}

	public void setPlanProd(int planProd) {
		this.planProd = planProd;
	}

	@Column(name = "rate_product_backlog")
	public int getRateProductBacklog() {
		return this.rateProductBacklog;
	}

	public void setRateProductBacklog(int rateProductBacklog) {
		this.rateProductBacklog = rateProductBacklog;
	}

	@Column(name = "rate_qa")
	public int getRateQa() {
		return this.rateQa;
	}

	public void setRateQa(int rateQa) {
		this.rateQa = rateQa;
	}

	@Column(name = "rate_uat")
	public int getRateUat() {
		return this.rateUat;
	}

	public void setRateUat(int rateUat) {
		this.rateUat = rateUat;
	}

	@Column(name = "rate_prod")
	public int getRateProd() {
		return this.rateProd;
	}

	public void setRateProd(int rateProd) {
		this.rateProd = rateProd;
	}

	@Column(name = "rpa_product_backlog")
	public int getRpaProductBacklog() {
		return this.rpaProductBacklog;
	}

	public void setRpaProductBacklog(int rpaProductBacklog) {
		this.rpaProductBacklog = rpaProductBacklog;
	}

	@Column(name = "rpa_qa")
	public int getRpaQa() {
		return this.rpaQa;
	}

	public void setRpaQa(int rpaQa) {
		this.rpaQa = rpaQa;
	}

	@Column(name = "rpa_uat")
	public int getRpaUat() {
		return this.rpaUat;
	}

	public void setRpaUat(int rpaUat) {
		this.rpaUat = rpaUat;
	}

	@Column(name = "rpa_prod")
	public int getRpaProd() {
		return this.rpaProd;
	}

	public void setRpaProd(int rpaProd) {
		this.rpaProd = rpaProd;
	}

	@Column(name = "ac_product_backlog")
	public int getAcProductBacklog() {
		return this.acProductBacklog;
	}

	public void setAcProductBacklog(int acProductBacklog) {
		this.acProductBacklog = acProductBacklog;
	}

	@Column(name = "ac_qa")
	public int getAcQa() {
		return this.acQa;
	}

	public void setAcQa(int acQa) {
		this.acQa = acQa;
	}

	@Column(name = "ac_uat")
	public int getAcUat() {
		return this.acUat;
	}

	public void setAcUat(int acUat) {
		this.acUat = acUat;
	}

	@Column(name = "ac_prod")
	public int getAcProd() {
		return this.acProd;
	}

	public void setAcProd(int acProd) {
		this.acProd = acProd;
	}

	@Column(name = "ti_product_backlog")
	public int getTiProductBacklog() {
		return this.tiProductBacklog;
	}

	public void setTiProductBacklog(int tiProductBacklog) {
		this.tiProductBacklog = tiProductBacklog;
	}

	@Column(name = "ti_qa")
	public int getTiQa() {
		return this.tiQa;
	}

	public void setTiQa(int tiQa) {
		this.tiQa = tiQa;
	}

	@Column(name = "ti_uat")
	public int getTiUat() {
		return this.tiUat;
	}

	public void setTiUat(int tiUat) {
		this.tiUat = tiUat;
	}

	@Column(name = "ti_prod")
	public int getTiProd() {
		return this.tiProd;
	}

	public void setTiProd(int tiProd) {
		this.tiProd = tiProd;
	}

	@Column(name = "dp_product_backlog")
	public int getDpProductBacklog() {
		return this.dpProductBacklog;
	}

	public void setDpProductBacklog(int dpProductBacklog) {
		this.dpProductBacklog = dpProductBacklog;
	}

	@Column(name = "dp_qa")
	public int getDpQa() {
		return this.dpQa;
	}

	public void setDpQa(int dpQa) {
		this.dpQa = dpQa;
	}

	@Column(name = "dp_uat")
	public int getDpUat() {
		return this.dpUat;
	}

	public void setDpUat(int dpUat) {
		this.dpUat = dpUat;
	}

	@Column(name = "dp_prod")
	public int getDpProd() {
		return this.dpProd;
	}

	public void setDpProd(int dpProd) {
		this.dpProd = dpProd;
	}

	@Column(name = "env_product_backlog")
	public int getEnvProductBacklog() {
		return this.envProductBacklog;
	}

	public void setEnvProductBacklog(int envProductBacklog) {
		this.envProductBacklog = envProductBacklog;
	}

	@Column(name = "env_qa")
	public int getEnvQa() {
		return this.envQa;
	}

	public void setEnvQa(int envQa) {
		this.envQa = envQa;
	}

	@Column(name = "env_uat")
	public int getEnvUat() {
		return this.envUat;
	}

	public void setEnvUat(int envUat) {
		this.envUat = envUat;
	}

	@Column(name = "env_prod")
	public int getEnvProd() {
		return this.envProd;
	}

	public void setEnvProd(int envProd) {
		this.envProd = envProd;
	}

	@Column(name = "co_product_backlog")
	public int getCoProductBacklog() {
		return this.coProductBacklog;
	}

	public void setCoProductBacklog(int coProductBacklog) {
		this.coProductBacklog = coProductBacklog;
	}

	@Column(name = "co_qa")
	public int getCoQa() {
		return this.coQa;
	}

	public void setCoQa(int coQa) {
		this.coQa = coQa;
	}

	@Column(name = "co_uat")
	public int getCoUat() {
		return this.coUat;
	}

	public void setCoUat(int coUat) {
		this.coUat = coUat;
	}

	@Column(name = "co_prod")
	public int getCoProd() {
		return this.coProd;
	}

	public void setCoProd(int coProd) {
		this.coProd = coProd;
	}

	@Column(name = "ffm_product_backlog")
	public int getFfmProductBacklog() {
		return this.ffmProductBacklog;
	}

	public void setFfmProductBacklog(int ffmProductBacklog) {
		this.ffmProductBacklog = ffmProductBacklog;
	}

	@Column(name = "ffm_qa")
	public int getFfmQa() {
		return this.ffmQa;
	}

	public void setFfmQa(int ffmQa) {
		this.ffmQa = ffmQa;
	}

	@Column(name = "ffm_uat")
	public int getFfmUat() {
		return this.ffmUat;
	}

	public void setFfmUat(int ffmUat) {
		this.ffmUat = ffmUat;
	}

	@Column(name = "ffm_prod")
	public int getFfmProd() {
		return this.ffmProd;
	}

	public void setFfmProd(int ffmProd) {
		this.ffmProd = ffmProd;
	}

	@Column(name = "crmesb_product_backlog")
	public int getCrmesbProductBacklog() {
		return this.crmesbProductBacklog;
	}

	public void setCrmesbProductBacklog(int crmesbProductBacklog) {
		this.crmesbProductBacklog = crmesbProductBacklog;
	}

	@Column(name = "crmesb_qa")
	public int getCrmesbQa() {
		return this.crmesbQa;
	}

	public void setCrmesbQa(int crmesbQa) {
		this.crmesbQa = crmesbQa;
	}

	@Column(name = "crmesb_uat")
	public int getCrmesbUat() {
		return this.crmesbUat;
	}

	public void setCrmesbUat(int crmesbUat) {
		this.crmesbUat = crmesbUat;
	}

	@Column(name = "crmesb_prod")
	public int getCrmesbProd() {
		return this.crmesbProd;
	}

	public void setCrmesbProd(int crmesbProd) {
		this.crmesbProd = crmesbProd;
	}

	@Column(name = "otp_product_backlog")
	public int getOtpProductBacklog() {
		return this.otpProductBacklog;
	}

	public void setOtpProductBacklog(int otpProductBacklog) {
		this.otpProductBacklog = otpProductBacklog;
	}

	@Column(name = "otp_qa")
	public int getOtpQa() {
		return this.otpQa;
	}

	public void setOtpQa(int otpQa) {
		this.otpQa = otpQa;
	}

	@Column(name = "otp_uat")
	public int getOtpUat() {
		return this.otpUat;
	}

	public void setOtpUat(int otpUat) {
		this.otpUat = otpUat;
	}

	@Column(name = "otp_prod")
	public int getOtpProd() {
		return this.otpProd;
	}

	public void setOtpProd(int otpProd) {
		this.otpProd = otpProd;
	}

	@Column(name = "pmuu_product_backlog")
	public int getPmuuProductBacklog() {
		return this.pmuuProductBacklog;
	}

	public void setPmuuProductBacklog(int pmuuProductBacklog) {
		this.pmuuProductBacklog = pmuuProductBacklog;
	}

	@Column(name = "pmuu_qa")
	public int getPmuuQa() {
		return this.pmuuQa;
	}

	public void setPmuuQa(int pmuuQa) {
		this.pmuuQa = pmuuQa;
	}

	@Column(name = "pmuu_uat")
	public int getPmuuUat() {
		return this.pmuuUat;
	}

	public void setPmuuUat(int pmuuUat) {
		this.pmuuUat = pmuuUat;
	}

	@Column(name = "pmuu_prod")
	public int getPmuuProd() {
		return this.pmuuProd;
	}

	public void setPmuuProd(int pmuuProd) {
		this.pmuuProd = pmuuProd;
	}

	@Column(name = "io_product_backlog")
	public int getIoProductBacklog() {
		return this.ioProductBacklog;
	}

	public void setIoProductBacklog(int ioProductBacklog) {
		this.ioProductBacklog = ioProductBacklog;
	}

	@Column(name = "io_qa")
	public int getIoQa() {
		return this.ioQa;
	}

	public void setIoQa(int ioQa) {
		this.ioQa = ioQa;
	}

	@Column(name = "io_uat")
	public int getIoUat() {
		return this.ioUat;
	}

	public void setIoUat(int ioUat) {
		this.ioUat = ioUat;
	}

	@Column(name = "io_prod")
	public int getIoProd() {
		return this.ioProd;
	}

	public void setIoProd(int ioProd) {
		this.ioProd = ioProd;
	}
	
	@Column(name = "overview_message")
	public String getOverviewMessage() {
		return overviewMessage;
	}

	public void setOverviewMessage(String overviewMessage) {
		this.overviewMessage = overviewMessage;
	}

	@Column(name = "risks_issues")
	public String getRisksIssues() {
		return risksIssues;
	}

	public void setRisksIssues(String risksIssues) {
		this.risksIssues = risksIssues;
	}

	/* Changes for Non RCA field addition */
	@Column(name = "nr_product_backlog")
	public int getNrProductBacklog() {
		return nrProductBacklog;
	}

	public void setNrProductBacklog(int nrProductBacklog) {
		this.nrProductBacklog = nrProductBacklog;
	}

	@Column(name = "nr_qa")
	public int getNrQa() {
		return nrQa;
	}

	public void setNrQa(int nrQa) {
		this.nrQa = nrQa;
	}

	@Column(name = "nr_uat")
	public int getNrUat() {
		return nrUat;
	}

	public void setNrUat(int nrUat) {
		this.nrUat = nrUat;
	}

	@Column(name = "nr_prod")
	public int getNrProd() {
		return nrProd;
	}

	public void setNrProd(int nrProd) {
		this.nrProd = nrProd;
	}
	
	@Column(name = "close_product_backlog")
	public int getCloseTicketProductBacklog() {
		return closeTicketProductBacklog;
	}

	public void setCloseTicketProductBacklog(int closeTicketProductBacklog) {
		this.closeTicketProductBacklog = closeTicketProductBacklog;
	}

	@Column(name = "close_qa")
	public int getCloseTicketQa() {
		return closeTicketQa;
	}

	public void setCloseTicketQa(int closeTicketQa) {
		this.closeTicketQa = closeTicketQa;
	}

	@Column(name = "close_uat")
	public int getCloseTicketUat() {
		return closeTicketUat;
	}

	public void setCloseTicketUat(int closeTicketUat) {
		this.closeTicketUat = closeTicketUat;
	}

	@Column(name = "close_prod")
	public int getCloseTicketProd() {
		return closeTicketProd;
	}

	public void setCloseTicketProd(int closeTicketProd) {
		this.closeTicketProd = closeTicketProd;
	}

}
