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
function generateReport()
{
	document.RCA_Form.action="rcaReportView";
	document.RCA_Form.submit(); 
}
function upload()
{	
	   var rcaFile = document.getElementById("rcaFile").value;
	   if(rcaFile=="")
	   	{
	   	alert("Please browse RCA file");	
	   	return false;
	   	}
	    
	   var fileExt = rcaFile.substring(rcaFile.lastIndexOf('.') + 1).toLowerCase();
	 
	  if(fileExt == "csv" || fileExt == "xls" || fileExt == "xlsx")
	   {


		  var projectName = document.getElementById("project_id").value;
		   var week = document.getElementById("week_id").value;
		    if(projectName=="Select Project" || projectName=="0" || projectName=="")
		    	{
		    	alert("Please select project name");
		    	return false;
		    	}	    
		    else if(week=="Select Week" || week=="")
		    	{
		    	alert("Please select week");
		    	return false;
		    	}
		    else
		    {
		   
			   document.RCA_Form.action="templateUpload";
		       document.RCA_Form.submit();
		    }	 
	   }      
	   else
		   {  
			alert("Only csv, xls, xlsx file extensions are allowed");	
		   	return false;
		   }	  
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
		 
		   <ul>
			  <li><a href="RCA.jsp" style="color:black; padding-left:30px;">Enter Last Week Data</a></li>
			  <li><a href="javascript:templateDownload();" style="color:black; padding-left:30px;">Download Template</a></li>
			  <li><a href="javascript:generateReport()" style="color:black; padding-left:30px;">Generate Reports</a> </li>
			  <li><a href="exportWeeklyData.jsp" style="color:black; padding-left:30px;">Export Weekly Data</a> </li>
			  
		  </ul>
		 
			   	<tr>   
			   	   <td>		
				  <label for="data_issue  ">Select File</label> 
			      <input  type="file" name="rcaFile" id="rcaFile"  /> 
			      <input type="submit" value="Upload RCA" onclick="upload()" /> 
			       </td>
				</tr>
		  </table>
				
			 </form> 
		</div>
	</div>
</body>
</html>

