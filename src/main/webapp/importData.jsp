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
		<%@ include file="common.jsp"%>
		<div id="content">
		 <form method="post" name="RCA_Form" id="RCA_Form" onsubmit="return false" enctype="multipart/form-data" > 
		  <table cellspacing="80" class="content-table">
		 
			<%@ include file="leftMenu.jsp"%>		 
			
			<tr>		     			 
				<td style="float:left;"><label for="project-name">Project Name:</label></td> 
				<td><select name="project_id" id="project_id" style="width:120px;">
					<option value="0">Select Project</option>
					<s:iterator value="projectNameWithId" var="data">
					  <option value='<s:property value="value"/>' <s:if test="rca.project_id==#data.value"> selected </s:if> ><s:property value="key" /></option>  
					</s:iterator> 
					</select>
				</td> 
				</tr><tr>
				  <td style="float:left;"><label for="data_issue  ">Select File:</label> </td>
			      <td><input  type="file" name="rcaFile" id="rcaFile"  /> </td>        
    			</tr>  
			</tr>
			<tr>
			  <td>
			      <input type="submit" value="Upload RCA" onclick="upload();" /> 
			  </td>
			</tr>
		  </table>
				
			 </form> 
		</div>
	</div>
</body>
</html>

