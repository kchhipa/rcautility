 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>RCA</title>
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


</script>
</head>
<body>
     <div id="main">
        <div id="header" style="width: 100%; height:60px; background-color: #34495e; text-align: center; color: #fff;">
          <h1> RCA<br><span style="font-size: 16px; color:yellow;">Please submit the data for your project before 2 PM on every Monday.  <a href="logout" style="color:white; padding-left:30px;">Logout</a></span></h1>
        </div>	
		<div id="content"  style="background-color: #369044; width:100%; height:600px">
		 <form method="post" name="RCA_Form" id="RCA_Form" onsubmit="return false" enctype="multipart/form-data" > 
		  <table cellspacing="12" style="font-weight:bold; padding-left:30%;  font-family:verdana;">
		  
		  <tr>
			    <td style="padding-top: 40px;"></td>			   
			    <td style="font-size: 30px;"> <label for="rca">RCA Utility</label></td>	
		 </tr>    
		  <tr>  <td style="padding-top: 20px;"><a href="javascript:displayRcaPage();" style="color:black; padding-left:30px;">Enter Last Week Data</a> </td></tr>
		  <tr>  <td style="padding-top: 20px;"><a href="importData.jsp" style="color:black; padding-left:30px;">Import Last Week Data</a> </td></tr>
		  <tr>  <td style="padding-top: 20px;"><a href="javascript:templateDownload();" style="color:black; padding-left:30px;">Download Template</a> </td></tr>
		  <s:if test="#session.role != null && #session.role.equals('manager')">	
		  <tr>  <td style="padding-top: 20px;"><a href="javascript:generateReport()" style="color:black; padding-left:30px;">Generate Reports</a> </td></tr>
		  <tr>  <td style="padding-top: 20px;"><a href="exportWeeklyData.jsp" style="color:black; padding-left:30px;">Export Weekly Data</a> </td></tr>
		  <tr>  <td style="padding-top: 20px;"><a href="javascript:addProject();" style="color:black; padding-left:30px;">Add Project</a> </td></tr>
		  </s:if>

		  </table>
				
			 </form> 
		</div>
	</div>
</body>
</html>

