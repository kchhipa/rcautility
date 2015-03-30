 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>RCA Report</title>
<link href="css/style.css" rel="stylesheet"/>
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
<body>
     <div id="main">
		<%@ include file="common.jsp"%>
		<div id="content">
		<form method="post" name="RCA_Form" id="RCA_Form" onsubmit="return false" enctype="multipart/form-data" >
		  <table cellspacing="30" class="content-table">

		   <%@ include file="leftMenu.jsp"%>
		  
		  <tr>
			  <td colspan="2"><h1>Generate Report </h1> </td>
		  </tr>
		  <tr></tr>
 
		  <tr>
		  	 <td>
			<s:radio name="rca.weekType"  label ="Report Type" list="{'Weekly','Monthly'}"/></td>
		  </tr>
		  <tr>
				<td colspan="2">
					<s:select name="rca.week" list="weeks" headerKey="Select Week" headerValue="Select Week" label="Week" id ="week_id" />			
				</td>
		  
		  <tr></tr>

		 	<tr>
				<td colspan=3>
				<input type="submit" value="Submit" id="export" onclick="exportData()"/> 
				<input type="submit" value="Cancel" onclick="homePage()"/> 
				</td>
		  </tr>	  
	  </table>
				
			</form>
		</div>
	</div>
</body>
</html>

