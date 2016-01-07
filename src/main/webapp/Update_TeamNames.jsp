
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta HTTP-EQUIV="Pragma" content="no-cache">
<meta HTTP-EQUIV="Expires" content="-1">

<title>RCA</title>
<link href="css/style.css" rel="stylesheet" />
<script src="<%=request.getContextPath()%>/js/jquery-2.1.4.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/jquery-ui.css" />
<script type="text/javascript">
$(function() {//************ start ready event ************
	$(":text").attr("autocomplete", "off"); 
	});//End ready event
	
 

	function validateForm()
	{
		    var errorMessage="";
		    var flag=true;
			var projectName = document.getElementById("project_id").value;
			 //validation for projectName is not selected
		   	if(projectName=="Select Project" || projectName=="0" || projectName=="")
		   	{
		   		errorMessage=errorMessage+"\u2022 Please select project name\n";
		    	//alert("Please select project name");
		    	flag=false;
		    }
			if(!flag){
				alert(errorMessage);
				return flag;
			}
			
		    return flag;
	}
   
   
 
   var xmlhttp2;
   function  getTeamNameForProject()
   {
	 var projectId = document.getElementById("project_id").value;
	 if(projectId=="0")
		 {
		 	alert("Please select project first");
		 	return false;
		 }
	 var url = "getTeamNameByProjectId?projectId="+projectId;  
	 if(window.XMLHttpRequest)
		 {	// code for IE7+, Firefox, Chrome, Opera, Safari
		 	xmlhttp2 = new XMLHttpRequest();
		 }
	 else
		 {	// code for IE6, IE5
		 	xmlhttp2 = new ActiveXObject("Microsoft.XMLHTTP");
		 }
	 xmlhttp2.onreadystatechange = setTeamName;     
     xmlhttp2.open("GET", url, true);
     xmlhttp2.send(null);	  	
	 
   }
   function setTeamName()
   {
	   if(xmlhttp2.readyState==4)
       {   		   
		   var response = xmlhttp2.responseText;       
           if(response != null && response.indexOf("_")!=-1)
        	   {
	        	   var teamNameWithAutomation = response.split("_");
	        	   if(teamNameWithAutomation[0] == "null")
	        		   teamNameWithAutomation[0] = "";	           			
	        	   if(teamNameWithAutomation[1] == "null")
	        		   teamNameWithAutomation[1] = "";
	        	   
	        	   document.getElementById("teamName").value = teamNameWithAutomation[0];
	               document.getElementById("automation").value = teamNameWithAutomation[1];
	        	    
        	   }
           document.getElementById("teamDetail").style.display = "table-row";
           document.getElementById("automationDetail").style.display = "table-row";

       }
   }
   
   var xmlhttp3;
   function updateTeam()
   {
	  	 var projectId = document.getElementById("project_id").value;
	  	 var automation = document.getElementById("automation").value;
		 if(projectId=="0")
			 {
			 	alert("Please select project first");
			 	return false;
			 }
		 var actionTeam = document.getElementById("teamName").value;		
		 actionTeam = actionTeam.replace(/[+]/g,"_");
		 var url = "updateTeamName?projectId="+projectId+"&actionTeam="+actionTeam+"&automation="+automation;  
		 if(window.XMLHttpRequest)
			 {	// code for IE7+, Firefox, Chrome, Opera, Safari
			 xmlhttp3 = new XMLHttpRequest();
			 }
		 else
			 {	// code for IE6, IE5
			 xmlhttp3 = new ActiveXObject("Microsoft.XMLHTTP");
			 }
		 xmlhttp3.onreadystatechange = setUpdateMessage;     
		 xmlhttp3.open("POST", url, true);
		 xmlhttp3.send(null);	
		 
   }
   function setUpdateMessage()
   {
	   if(xmlhttp3.readyState==4)
       {  
		   var response = xmlhttp3.responseText;          
           if(response == "success")
		   	alert("Updated Successfully");
           else
        		alert("Problem in updation. Please try again");
       }

   }
   function submitReset()
   {
	   var elementIdArray = getElementIdArray();
	   elementIdArray[0].value="0";
	   for(var i=1;i<elementIdArray.length;i++)
		   {
		  	 elementIdArray[i].value="";
		   }
   }
   
   function getElementIdArray()
   {	
	   var elementIdsArray = new Array();
	   elementIdsArray[0] = document.getElementById("project_id");
	  	return elementIdsArray;   
   }
</script>

</head>
<body>
	<div id="main">
		<%@ include file="common.jsp"%>
		<div id="content" style="overflow: auto;height:400px;">
			<div id="tuesdayError" class="errors" style="color: red;"
				align="center"></div>
			<form method="post" name="RCA_Form" id="RCA_Form"
				onsubmit="return false" enctype="multipart/form-data">
				<table cellspacing="15" class="content-table">
					<%@ include file="leftMenu.jsp"%>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td class="label"><label for="project-name">Project
								Name</label></td>
						<td><select name="project_id" id="project_id"
							style="width: 138px;">
								<option value="0">Select Project</option>
								<s:iterator value="projectNameWithId" var="data">
									<option value='<s:property value="value"/>'
										<s:if test="project_id==#data.value"> selected </s:if>>
										<s:property value="key" />
									</option>
								</s:iterator>

						</select></td>
						<td colspan="2"><input type="submit" value="Update Team Name"
							onclick="getTeamNameForProject();"></td>

					</tr>
					<tr id="automationDetail" style="display: none;">
						<td class="label"><label class="label" for="Automation">Automation</label></td>
						<td colspan="3"><input type="text" id="automation" size="37"
							name="automation" value="" /></td>
					</tr>
					<tr id="teamDetail" style="display: none;">
						<td class="label"><label for="Action Team">Action
								Team</label></td>
						<td colspan="2"><input type="text" id="teamName" size="37"
							name="teamName" value="" /></td>
						<td><input type="submit" value="Submit"
							onclick="updateTeam()"> 
					</tr>

					<tr>
						<td colspan="4">
							<s:if test="hasActionErrors()">
								<div class="errors" style="color: red; text-align: center;">
									<s:actionerror />
								</div>
							</s:if>
							<s:if test="hasActionMessages()">
								<div class="success" style="color: yellow; text-align: center;">
									<b><s:actionmessage /></b>
								</div>
							</s:if>
						</td>
					</tr>
				</table>

				
			</form>
		</div>
	</div>
</body>
</html>