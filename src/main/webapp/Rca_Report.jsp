 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>RCA Report</title>
<script type="text/javascript">

 function exportData()
   {
   
   	 if(validate()){
	 document.RCA_Form.action="generateGraphPpt";
	 document.RCA_Form.submit();
	 }	   
   }

 function validate()
   {
	   var week = document.getElementById("week_id").value;
	 	if(week=="Select Week" || week=="")
	    	{
	    	alert("Please select week");
	    	return false;
	    	}
	    	    	
	    return true;
   }
 function templateDownload()
 {
 	document.RCA_Form.action="templateDownload";
 	document.RCA_Form.submit(); 
 }
 
  function homePage()
 {
 	document.RCA_Form.action="homeRca";
 	document.RCA_Form.submit(); 
 }
 

</script>
</head>
<body style="background: none repeat scroll 0% 0% rgb(54, 144, 68);">
     <div id="main">
        <div id="header" style="width: 100%; height:60px; background-color: #34495e; text-align: center; color: #fff;">
          <h1> RCA<br><span style="font-size: 16px; color:yellow;">Please submit the data for your project before 2 PM on every Monday.  <a href="logout" style="color:white; padding-left:30px;">Logout</a></span></h1>
        </div>	
		<div id="content"  style="background-color: #369044; width:100%; height:100%;">
		<form method="post" name="RCA_Form" id="RCA_Form" onsubmit="return false" enctype="multipart/form-data" >
		  <table style="font-weight:bold; padding-left:30%;  font-family:verdana;">
		   <%@ include file="leftMenu.jsp"%>
		  
		  <tr>
			  <td colspan="2"><h1>Report</h1> </td>
		  </tr>
		  
		  <tr>
		  	<td>
		  	<s:radio name="rca.weekType" list="{'Weekly','Monthly'}"/>
			</td>
		  </tr>
		  <tr></tr>
		  <tr>
				<td colspan="2">
				<s:select name="rca.week" list="weeks" headerKey="Select Week" headerValue="Select Week" label="Week" id ="week_id" />			
				</select></td>
		  
			<!-- <td>
			 <s:textfield name="rca.startDate" label="Start Date" />			
			 <s:textfield name="rca.endDate" label="End Date" />
			</td> 
		  </tr>
		  <tr></tr>
		  <tr>
			<td> 
			<s:textfield name="rca.weekCount" label="Week Count" id="week_id" />
			</td>
		  </tr>
		  <tr></tr>
		   <tr>
			  <td>
			  <s:checkboxlist list="rca.projectList" name="rca.project" label="Projects"/>
			 </td>
		  </tr> -->
		  <tr></tr><tr></tr><tr></tr><tr></tr>
		  <tr></tr><tr></tr><tr></tr><tr></tr>
		  <tr></tr><tr></tr><tr></tr><tr></tr>
		  
		  
		  <tr></tr>
		 	<tr>
				<td colspan=3>
				<input type="submit" value="Submit" id="export" onclick="exportData()"/> 
				<input type="submit" value="Cancel" onclick="homePage()"/> 
				<input type="submit" value="Home" id="home" onclick="homePage()"/> 
				</td>
				</tr>
	  </table>
				
			</form>
		</div>
	</div>
</body>
</html>
