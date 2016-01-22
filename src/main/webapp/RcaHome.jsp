 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>RCA</title>
<link href="css/style.css" rel="stylesheet"/>
<script type="text/javascript">
function templateDownload()
{
	document.RCA_Form.action="templateDownload";
	document.RCA_Form.submit(); 
}

function displayRcaPage()
{
	document.RCA_Form.action="getRcaDetail";
	document.RCA_Form.submit(); 
}

function generateReport()
{
	document.RCA_Form.action="rcaReportView";
	document.RCA_Form.submit(); 
}
function addProject()
{
	document.RCA_Form.action="viewAddProject";
	document.RCA_Form.submit(); 
}
function manageProject()
{
	document.RCA_Form.action="showProject";
	document.RCA_Form.submit(); 
}

function manageUsers()
{
	document.RCA_Form.action="manageUsers";  // previously it was showUsers
	document.RCA_Form.method="get";
	document.RCA_Form.submit(); 
}

function importData()
{
	document.RCA_Form.action="importData";
	document.RCA_Form.submit(); 
}

function generateReportForSprint()
{
	document.RCA_Form.action="sprintReportView";
	document.RCA_Form.submit(); 
}
function generateSummary()
{
	document.RCA_Form.action="generateSummarySlide";
	document.RCA_Form.submit(); 
}
function EditSprint()
{
	document.RCA_Form.action="EditSprintView";
	document.RCA_Form.submit(); 
}

</script>
</head>
<body>
     <div id="main">
		<%@ include file="common.jsp"%>
		<div id="content">
		 <form method="post" name="RCA_Form" id="RCA_Form" onsubmit="return false" enctype="multipart/form-data" > 
		  <table cellspacing="12" class="content-table">
		  
		  <tr>
			    <td style="padding-top: 40px;"></td>			   
			    <td style="font-size: 30px;"> <label for="rca">RCA Utility</label></td>	
		 </tr>    
		  <tr>  <td style="padding-top: 20px;"><a href="javascript:displayRcaPage();" style="color:black; padding-left:30px;">Enter Last Week Data</a> </td></tr>
		  <tr>  <td style="padding-top: 20px;"><a href="javascript:importData();" style="color:black; padding-left:30px;">Import Last Week Data</a> </td></tr>
		  <tr>  <td style="padding-top: 20px;"><a href="javascript:templateDownload();" style="color:black; padding-left:30px;">Download Template</a> </td></tr>
		  <tr>  <td style="padding-top: 20px;"><a href="javascript:generateReportForSprint();" style="color:black; padding-left:30px;">Enter Sprint Data</a> </td></tr>
		  <tr>  <td style="padding-top: 20px;"><a href="javascript:EditSprint();" style="color:black; padding-left:30px;">Edit Sprint Data</a> </td></tr>
		  <s:if test="#session.role != null && #session.role.equals('manager')">	
		  <tr>  <td style="padding-top: 20px;"><a href="javascript:generateReport()" style="color:black; padding-left:30px;">Generate Reports</a> </td></tr>
		  <tr>  <td style="padding-top: 20px;"><a href="exportWeeklyData.jsp" style="color:black; padding-left:30px;">Export Weekly Data</a> </td></tr>
		 <!--  <tr>  <td style="padding-top: 20px;"><a href="javascript:addProject();" style="color:black; padding-left:30px;">Manage Project</a> </td></tr> -->
		  <tr>  <td style="padding-top: 20px;"><a href="javascript:manageProject();" style="color:black; padding-left:30px;">Manage Project</a> </td></tr>
		  <tr>  <td style="padding-top: 20px;"><a href="javascript:manageUsers();" style="color:black; padding-left:30px;">Manage Users</a> </td></tr> 
		   <!-- <tr>  <td style="padding-top: 20px;"><a href="projectWithTeam" style="color:black; padding-left:30px;">Update Team Name</a> </td></tr> -->
		  
		  </s:if>
		 <tr>  <td style="padding-top: 20px;"><a href="javascript:generateSummary();" style="color:black; padding-left:30px;">Generate Summary Slide</a> </td></tr>

		  </table>
				
			 </form> 
		</div>
	</div>
</body>
</html>

