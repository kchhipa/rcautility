 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>RCA</title>
<style>
ul {
    list-style-type: none;
    margin: 0;
    padding: 0;
}

li {
    display: inline;
}
</style>
<script type="text/javascript">
function templateDownload()
{
	document.RCA_Form.action="templateDownload";
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

</script>
</head>
<body >
     <div id="main">
		  <ul>
			  <li><a href="RCA.jsp" style="color:black; padding-left:30px;">Enter Last Week Data</a></li>
			  <li><a href="importData.jsp" style="color:black; padding-left:30px;">Import Last Week Data</a></li>
			  <li><a href="javascript:templateDownload();" style="color:black; padding-left:30px;">Download Template</a></li>
			   <s:if test="#session.role != null && #session.role.equals('manager')">
			  <li><a href="javascript:generateReport()" style="color:black; padding-left:30px;">Generate Reports</a> </li>
			  <li><a href="exportWeeklyData.jsp" style="color:black; padding-left:30px;">Export Weekly Data</a> </li>
			  <li><a href="javascript:addProject()" style="color:black; padding-left:30px;">Add Project</a> </li>
			  </s:if>
		  </ul>
	</div>
</body>
</html>

