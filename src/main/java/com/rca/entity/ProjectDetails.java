package com.rca.entity;

// default package
// Generated Mar 10, 2015 7:18:09 AM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * ProjectDetails generated by hbm2java
 */
@Entity
@Table(name = "project_details", catalog = "rcautility")
public class ProjectDetails implements java.io.Serializable {

	private int projectId;
	private String projectName;
	private Set<RcaCount> rcaCounts = new HashSet<RcaCount>(0);

	public ProjectDetails() {
	}

	public ProjectDetails(int projectId, String projectName) {
		this.projectId = projectId;
		this.projectName = projectName;
	}

	public ProjectDetails(int projectId, String projectName,
			Set<RcaCount> rcaCounts) {
		this.projectId = projectId;
		this.projectName = projectName;
		this.rcaCounts = rcaCounts;
	}

	@Id
	@Column(name = "project_id", unique = true, nullable = false)
	public int getProjectId() {
		return this.projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	@Column(name = "project_name", nullable = false, length = 100)
	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "projectDetails", cascade= CascadeType.ALL)
	public Set<RcaCount> getRcaCounts() {
		return this.rcaCounts;
	}

	public void setRcaCounts(Set<RcaCount> rcaCounts) {
		this.rcaCounts = rcaCounts;
	}

}
