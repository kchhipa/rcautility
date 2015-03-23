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

 function submitForm()
   {
   	 if(validate()){
	 document.RCA_Form.action="submitAddProject";
	 document.RCA_Form.submit();
	 return true;
	 }	  
		return false;
   }

function validate()
   {
	   var week = document.getElementById("projectName").value;
	 	if(week=="")
	    	{
	    	alert("Please enter project");
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
 function reset(){
	document.getElementById("projectName").value = "";
 }
 

</script>
</head>
<body>
     <div id="main">
        <div id="header" style="width: 100%; height:60px; background-color: #34495e; text-align: center; color: #fff;">
          <h1> RCA<br><span style="font-size: 16px; color:yellow;">Please submit the data for your project before 2 PM on every Monday. <a href="logout" class="button logout">Logout</a></span></h1>
        </div>	
		<div id="content">
		<form method="post" name="RCA_Form" id="RCA_Form" onsubmit="javascript:return submitForm();" enctype="multipart/form-data" >
		  <table class="content-table">
		   <%@ include file="leftMenu.jsp"%>
		  <tr>
			  <td colspan="2"><h1>Add Project</h1> </td>
		  </tr>
		  
		  <tr>
		  	<td>
		  	<s:radio name="rca.projectStatus" list="{'Active','Inactive'}"/>
			</td>
		  </tr>
		  <tr></tr>
		  <tr>
				<td colspan="2">
				<s:textfield name="rca.projectName" label="Project Name" id="projectName" />	
				</td>
		  <tr></tr><tr></tr><tr></tr><tr></tr>
		  <tr></tr><tr></tr><tr></tr><tr></tr>
		  <tr></tr><tr></tr><tr></tr><tr></tr>
		  
		  
		  <tr></tr>
		 	<tr>
				<td colspan=3>
				<input type="submit" value="Submit"/> 
				<input type="button" value="Reset" onClick="resetForm()"/> 
				<input type="button" value="Home" id="home" onClick="homePage()"/> 
				</td>
				</tr>
				
				 <tr>
			      <td style="color: red;" colspan=3>		    
				<s:if test="hasActionMessages()">				
				      <s:actionmessage/>				  
				</s:if>		
			      </td>
			    </tr>
	  </table>
				
			</form>
		</div>
	</div>
</body>
</html>

