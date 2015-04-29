/**
 * 
 */
package com.rca.entity;

/**
 * @author govind.gupta
 * 
 */
public class RankingFramework implements Comparable<RankingFramework> {
	/**
	 * A
	 */
	private String teamName;

	/**
	 * B = project name
	 */
	private String client;

	/**
	 * C = Defaulted to 100%
	 */
	private int agileCompliance;

	/**
	 * D = IF(C=100%,10,IF(C=75%,5,IF(C=50%,2,IF(C=25%,0,0))))
	 */
	private String agileComplianceScore;

	/**
	 * E = Client Code Bug UAT+PROD
	 */
	private int ccb;

	/**
	 * F = IF(E<=5,10,IF(E<=10,5,IF(E<=15,2,IF(E<=20,0,0))))
	 */
	private String ccbScore;

	/**
	 * G = Defaulted to 100%
	 */
	private int toolCompliance;

	/**
	 * H = IF(G=100%,5,IF(G=75%,,IF(G=50%,1,IF(G=25%,0,0))))
	 */
	private String toolComplianceScore;

	/**
	 * I = Defaulted to 100%
	 */
	private int jUnitCov;

	/**
	 * J = IF(I=100%,5,IF(I=75%,,IF(I=50%,1,IF(I=25%,0,0))))
	 */
	private String jUnitCovScore;

	/**
	 * K = Reported QA Defects previous week
	 */
	private int prevWeek;

	/**
	 * L = Reported QA Defects current week.
	 */
	private int currWeek;

	/**
	 * M = IF(AND(K=0,L>=0),1,(L-K)/K)
	 */
	private String perChange;

	/**
	 * N =L3-K3
	 */
	private String absoluteChange;

	/**
	 * O =
	 * IF(AND(M>20%,N>5),0,IF(AND(0%<M<20%,N<5),10,IF(AND(-20%<M<0%,N<5),10,IF
	 * (AND(M<-20%,N<5),20,10))))
	 */
	private String qaDefectScore;

	/**
	 * P = Reopen defect.
	 */
	private int reopen;

	/**
	 * Q = IF(P=0,10,IF(P=1,5,IF(P > 1,0,0)))
	 */
	private String reopenScore;

	/**
	 * R = Defaulted to 0
	 */
	private int collaboration;

	/**
	 * S = IF(R3=1,10,0)
	 */
	private String collaborationScore;

	/**
	 * T = cumulativeBacklog
	 */
	private int cumulativeBacklog;

	/**
	 * U = IF(T5 <=-5,10,IF(T5<=0,5,IF(T5<=5,3,0)))
	 */
	private String cumulativeBacklogScore;

	/**
	 * V = Miss Requirement
	 */
	private int missReq;

	/**
	 * W = IF(V=0,10,IF(V<=2,5,IF(V > 2,0,0)))
	 */
	private String missReqScore;

	/**
	 * X Defaulted to A
	 */
	private String risk;

	/**
	 * Y = IF(X="A",10,IF(X="B",7,IF(X="C",5,IF(X="D",3,0))))
	 */
	private String riskScore;

	/**
	 * Z = SUM(D,F,H,J,O,Q,S,U,W,Y)
	 */
	private String totalScore;

	/**
	 * AA = IF(Z>=85,"A",IF(Z>=75,"B",IF(Z>=60,"C","D")))
	 */
	private String ranking;

	/**
	 * AB - Old Ranking
	 */
	private char actualUsed;

	/**
	 * AC
	 */
	private String rankingComment;
	
	private static final int cent = 100;

	/**
	 * @return the client
	 */
	public String getClient() {
		return client;
	}

	/**
	 * B = project name
	 * @param client the client to set
	 */
	public void setClient(String client) {
		this.client = client;
	}

	/**
	 * @return the agileComplianceScore
	 */
	public String getAgileComplianceScore() {
		return agileComplianceScore;
	}

	/**
	 * D = IF(C=100%,10,IF(C=75%,5,IF(C=50%,2,IF(C=25%,0,0))))
	 * @param agileComplianceScore the agileComplianceScore to set
	 */
	public void setAgileComplianceScore(String agileComplianceScore) {
		this.agileComplianceScore = agileComplianceScore;
	}

	/**
	 * @return the ccb
	 */
	public int getCcb() {
		return ccb;
	}

	/**
	 * E = Client Code Bug UAT+PROD
	 * @param ccb the ccb to set
	 */
	public void setCcb(int ccb) {
		this.ccb = ccb;
	}

	/**
	 * @return the ccbScore
	 */
	public String getCcbScore() {
		return ccbScore;
	}

	/**
	 * F = IF(E<=5,10,IF(E<=10,5,IF(E<=15,2,IF(E<=20,0,0))))
	 * @param ccbScore the ccbScore to set
	 */
	public void setCcbScore(String ccbScore) {
		this.ccbScore = ccbScore;
	}

	/**
	 * @return the toolCompliance
	 */
	public int getToolCompliance() {
		this.toolCompliance = cent;
		return toolCompliance;
	}

	/**
	 * @return the toolComplianceScore
	 */
	public String getToolComplianceScore() {
		return toolComplianceScore;
	}

	/**
	 * H = IF(G=100%,5,IF(G=75%,,IF(G=50%,1,IF(G=25%,0,0))))
	 * @param toolComplianceScore the toolComplianceScore to set
	 */
	public void setToolComplianceScore(String toolComplianceScore) {
		this.toolComplianceScore = toolComplianceScore;
	}

	/**
	 * @return the jUnitCov
	 */
	public int getjUnitCov() {
		this.jUnitCov = cent;
		return jUnitCov;
	}

	/**
	 * @return the jUnitCovScore
	 */
	public String getjUnitCovScore() {
		return jUnitCovScore;
	}

	/**
	 * J = IF(I=100%,5,IF(I=75%,,IF(I=50%,1,IF(I=25%,0,0))))
	 * @param jUnitCovScore the jUnitCovScore to set
	 */
	public void setjUnitCovScore(String jUnitCovScore) {
		this.jUnitCovScore = jUnitCovScore;
	}

	/**
	 * @return the prevWeek
	 */
	public int getPrevWeek() {
		return prevWeek;
	}

	/**
	 * K = Reported QA Defects previous week
	 * @param prevWeek the prevWeek to set
	 */
	public void setPrevWeek(int prevWeek) {
		this.prevWeek = prevWeek;
	}

	/**
	 * @return the currWeek
	 */
	public int getCurrWeek() {
		return currWeek;
	}

	/**
	 * L = Reported QA Defects current week
	 * @param currWeek the currWeek to set
	 */
	public void setCurrWeek(int currWeek) {
		this.currWeek = currWeek;
	}

	/**
	 * @return the perChange
	 */
	public String getPerChange() {
		return perChange;
	}

	/**
	 * M = IF(AND(K=0,L>=0),1,(L-K)/K)
	 * @param perChange the perChange to set
	 */
	public void setPerChange(String perChange) {
		this.perChange = perChange;
	}

	/**
	 * @return the absoluteChange
	 */
	public String getAbsoluteChange() {
		return absoluteChange;
	}

	/**
	 * N =L3-K3
	 * @param absoluteChange the absoluteChange to set
	 */
	public void setAbsoluteChange(String absoluteChange) {
		this.absoluteChange = absoluteChange;
	}

	/**
	 * @return the qaDefectScore
	 */
	public String getQaDefectScore() {
		return qaDefectScore;
	}

	/**
	 * O = IF(AND(M>20%,N>5),0,IF(AND(0%<M<20%,N<5),10,IF(AND(-20%<M<0%,N<5),10,IF(AND(M<-20%,N<5),20,10))))
	 * @param qaDefectScore the qaDefectScore to set
	 */
	public void setQaDefectScore(String qaDefectScore) {
		this.qaDefectScore = qaDefectScore;
	}

	/**
	 * @return the reopen
	 */
	public int getReopen() {
		return reopen;
	}

	/**
	 * P = Reopen defect.
	 * @param reopen the reopen to set
	 */
	public void setReopen(int reopen) {
		this.reopen = reopen;
	}

	/**
	 * @return the reopenScore
	 */
	public String getReopenScore() {
		return reopenScore;
	}

	/**
	 * Q = IF(P=0,10,IF(P=1,5,IF(P > 1,0,0)))
	 * @param reopenScore the reopenScore to set
	 */
	public void setReopenScore(String reopenScore) {
		this.reopenScore = reopenScore;
	}

	/**
	 * @return the collaboration
	 */
	public int getCollaboration() {
		this.collaboration = 0;
		return collaboration;
	}

	/**
	 * @return the collaborationScore
	 */
	public String getCollaborationScore() {
		return collaborationScore;
	}

	/**
	 * S = IF(R3=1,10,0)
	 * @param collaborationScore the collaborationScore to set
	 */
	public void setCollaborationScore(String collaborationScore) {
		this.collaborationScore = collaborationScore;
	}

	/**
	 * @return the cumulativeBacklog
	 */
	public int getCumulativeBacklog() {
		return cumulativeBacklog;
	}

	/**
	 * @param cumulativeBacklog the cumulativeBacklog to set
	 */
	public void setCumulativeBacklog(int cumulativeBacklog) {
		this.cumulativeBacklog = cumulativeBacklog;
	}

	/**
	 * @return the cumulativeBacklogScore
	 */
	public String getCumulativeBacklogScore() {
		return cumulativeBacklogScore;
	}

	/**
	 * U = IF(T5 <=-5,10,IF(T5<=0,5,IF(T5<=5,3,0)))
	 * @param cumulativeBacklogScore the cumulativeBacklogScore to set
	 */
	public void setCumulativeBacklogScore(String cumulativeBacklogScore) {
		this.cumulativeBacklogScore = cumulativeBacklogScore;
	}

	/**
	 * @return the missReq
	 */
	public int getMissReq() {
		return missReq;
	}

	/**
	 * V = Miss Requirement
	 * @param missReq the missReq to set
	 */
	public void setMissReq(int missReq) {
		this.missReq = missReq;
	}

	/**
	 * @return the missReqScore
	 */
	public String getMissReqScore() {
		return missReqScore;
	}

	/**
	 * W = IF(V=0,10,IF(V<=2,5,IF(V > 2,0,0)))
	 * @param missReqScore the missReqScore to set
	 */
	public void setMissReqScore(String missReqScore) {
		this.missReqScore = missReqScore;
	}

	/**
	 * @return the risk
	 */
	public String getRisk() {
		this.risk = "A";
		return risk;
	}

	/**
	 * @return the riskScore
	 */
	public String getRiskScore() {
		return riskScore;
	}

	/**
	 * Y = IF(X="A",10,IF(X="B",7,IF(X="C",5,IF(X="D",3,0))))
	 * @param riskScore the riskScore to set
	 */
	public void setRiskScore(String riskScore) {
		this.riskScore = riskScore;
	}

	/**
	 * @return the totalScore
	 */
	public String getTotalScore() {
		return totalScore;
	}

	/**
	 * Z = SUM(D,F,H,J,O,Q,S,U,W,Y)
	 * @param totalScore the totalScore to set
	 */
	public void setTotalScore(String totalScore) {
		this.totalScore = totalScore;
	}

	/**
	 * @return the ranking
	 */
	public String getRanking() {
		return ranking;
	}

	/**
	 * AA = IF(Z>=85,"A",IF(Z>=75,"B",IF(Z>=60,"C","D")))
	 * @param ranking the ranking to set
	 */
	public void setRanking(String ranking) {
		this.ranking = ranking;
	}

	/**
	 * @return the actualUsed
	 */
	public char getActualUsed() {
		return actualUsed;
	}

	/**
	 * AB - Old Ranking
	 * @param actualUsed the actualUsed to set
	 */
	public void setActualUsed(char actualUsed) {
		this.actualUsed = actualUsed;
	}

	/**
	 * @return the rankingComment
	 */
	public String getRankingComment() {
		return rankingComment;
	}

	/**
	 * @param rankingComment the rankingComment to set
	 */
	public void setRankingComment(String rankingComment) {
		this.rankingComment = rankingComment;
	}

	/**
	 * @return the agileCompliance
	 */
	public int getAgileCompliance() {
		this.agileCompliance = cent;
		return agileCompliance;
	}

	/**
	 * @return the teamName
	 */
	public String getTeamName() {
		return teamName;
	}

	/**
	 * @param teamName the teamName to set
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	@Override
	public int compareTo(RankingFramework o) {
		return this.client.compareTo(o.client);
	}
}
