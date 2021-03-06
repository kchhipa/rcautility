
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
	//initialize datepicker for spring start date
	$( "#sprintStartDt").datepicker({
	     showOn: "button",
	     buttonImage: "css/images/calendar.gif",
	     buttonImageOnly: true,
	     buttonText: "Select date",
	     //dateFormat:"dd/mm/yy",
		 maxDate: 'today',
		 changeMonth: true,
	     changeYear: true,
	     yearRange: '-5:'
	   }).keypress(function(event) {event.preventDefault();});
	
	//initialize datepicker for spring end date
	$( "#sprintEndDt").datepicker({
	     showOn: "button",
	     buttonImage: "css/images/calendar.gif",
	     buttonImageOnly: true,
	     buttonText: "Select date",
	     //dateFormat:"dd/mm/yy",
		// maxDate: 'today',
		 changeMonth: true,
	     changeYear: true,
	     yearRange: '-1:+10'
	   }).keypress(function(event) {event.preventDefault();});
	 
	//To show sprint start date datepicker on text box focus
	 $("#sprintStartDt").focus(function() {
	        $(this).datepicker("show");
	 });
	 
	 //To show sprint end date datepicker on text box focus
	 $("#sprintEndDt").focus(function() {
	        $(this).datepicker("show");
	 });
	 
	 // if kanban methodology followed
	 $('input[type="radio"]').click(function(){
	        if($(this).attr("value")=="Yes"){
	   		$("#spCommitted").attr("readonly", true).addClass('input-disabled');; 
			 $("#sprintName").attr("readonly", true).addClass('input-disabled');; 
	        }
	        if($(this).attr("value")=="No"){
	        	
				$("#spCommitted").attr("readonly",false).removeClass('input-disabled'); 
				$("#spAddedInMid").attr("readonly",false).removeClass('input-disabled'); 
				$("#sprintName").attr("readonly",false).removeClass('input-disabled'); 
	        }
	       });
	 
	});//End ready event
	
 
	function submitForm()
	{
		   if(validateForm())
			   {
			   	document.RCA_Form.action="saveSprintDetail";
			    document.RCA_Form.submit();
			   }
		    
	}
	
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
			 
			if (document.getElementById('no').checked) {
				
			
				var sprintName = document.getElementById("sprintName").value;
			  	//validation for sprintName is empty
				if(sprintName=="" || sprintName==null){
					errorMessage=errorMessage+"\u2022 Sprint Name can not be empty\n";	
					//alert("Sprint Name can not be empty");
					flag=false;
		     }
				var spCommitted = document.getElementById("spCommitted").value;
				if(spCommitted=="" || spCommitted==null)
				{
					errorMessage=errorMessage+"\u2022 SP Committed can not be empty\n";
					//alert("SP Committed can not be empty");
					flag=false;
				}
			  	}
			
			if (!(document.getElementById('no').checked ||document.getElementById('yes').checked )) {
				errorMessage=errorMessage+"\u2022 Please select if Kanban is followed or not\n";
				flag=false;
			}
			
			var sprintStartDt = document.getElementById("sprintStartDt").value;
			//validation for sprintStartDt is empty
			if(sprintStartDt=="" || sprintStartDt==null)
			{
				errorMessage=errorMessage+"\u2022 Sprint start Date can not be empty\n";
				//alert("Sprint start Date can not be empty");
				flag=false;
			}
			var sprintEndDt = document.getElementById("sprintEndDt").value;
			//validation for sprintEndDt is empty
			if(sprintEndDt=="" || sprintEndDt==null)
			{
				errorMessage=errorMessage+"\u2022 Sprint End Date can not be empty\n";
				//alert("Sprint End Date can not be empty");
				flag=false;
			}
			
			var startDtTime=new Date(sprintStartDt).getTime();
			var endDtTime=new Date(sprintEndDt).getTime();
			// validate sprint start date is before end date 
			if(startDtTime>endDtTime)
			{
				errorMessage=errorMessage+"\u2022 Sprint start Date must be before End Date\n";
				//alert("Sprint start Date must be before End Date");
				flag=false;
			}
		
			var teamCapacity = document.getElementById("teamCapacity").value;
			//validation for teamCapacity is empty
			if(teamCapacity=="" || teamCapacity==null)
			{
				errorMessage=errorMessage+"\u2022 Team capacity can not be empty\n";
				//alert("Team capacity can not be empty");
				flag=false;
			}
			var devMembers = document.getElementById("devMembers").value;
			if(devMembers=="" || devMembers==null)
			{
				errorMessage=errorMessage+"\u2022 Dev Members can not be empty\n";
				//alert("Dev Members can not be empty");
				flag=false;
			}
			/* if(devMembers==0)
			{
				errorMessage=errorMessage+"\u2022 Dev Members can not be zero\n";
				//alert("Dev Members can not be zero");
				flag=false;
			} */
			var qaMembers = document.getElementById("qaMembers").value;
			//validation for QAMembers is empty
			if(qaMembers=="" || qaMembers==null)
			{
				errorMessage=errorMessage+"\u2022 QA Members can not be empty\n";
				//alert("QA Members can not be empty");
				flag=false;
			}
			//validation for QAMembers is zeor
			/* if(qaMembers==0)
			{
				errorMessage=errorMessage+"\u2022 QA Members can not be zero\n";
				//alert("QA Members can not be zero");
				flag=false;
			} */
			if(!flag){
				alert(errorMessage);
				return flag;
			}
			
		    return flag;
	}
   
   function isNumberKey(evt)
   {
      var charCode = (evt.which) ? evt.which : evt.keyCode;
      if (charCode > 31 && (charCode < 48 || charCode > 57))
    	  {
    	    alert("Please enter numbers only");
         	return false;
    	  }

      return true;
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
   function  getTeamNameListForSelectedProject()
   {
	 var projectId = document.getElementById("project_id").value;
	 var url = "getTeamNameByProjectId?projectId="+projectId;  
	 if(window.XMLHttpRequest)
		 {	// code for IE7+, Firefox, Chrome, Opera, Safari
		 	xmlhttp2 = new XMLHttpRequest();
		 }
	 else
		 {	// code for IE6, IE5
		 	xmlhttp2 = new ActiveXObject("Microsoft.XMLHTTP");
		 }
	 xmlhttp2.onreadystatechange = setTeamNameList;     
     xmlhttp2.open("GET", url, true);
     xmlhttp2.send(null);	  	
	 
   }
   function setTeamNameList()
   {
	   if(xmlhttp2.readyState==4)
       {   		   
		   var response = xmlhttp2.responseText; 
           if(response != null && response.indexOf("_")!=-1)
        	   {
	        	   var teamNameWithAutomation = response.split("_");
	        	   if(teamNameWithAutomation[0] == "null")
	        		   teamNameWithAutomation[0] = "";	           			        	    
        	   }
           if(teamNameWithAutomation[0] != null && teamNameWithAutomation[0].indexOf("+")!=1)
        	   {
        	   var individualTeamName = teamNameWithAutomation[0].split("+");
        		   $("#ProjectTeamNames").empty();
        		   for (var i=0; i<individualTeamName.length; i++) {
        		       document.getElementById("ProjectTeamNames").options[i] = new Option(individualTeamName[i], individualTeamName[i]);
                     } 
        	   
        	   }
       }
	   
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
   function hideTeam()
   {
	   document.getElementById("automationDetail").style.display = "none";
	   document.getElementById("teamDetail").style.display = "none";
   }
   var xmlhttp3;
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
	   elementIdsArray[1] = document.getElementById("sprintName");
	   elementIdsArray[2] = document.getElementById("sprintStartDt");
	   elementIdsArray[3] = document.getElementById("sprintEndDt");
	   elementIdsArray[4] = document.getElementById("spCommitted");
	   elementIdsArray[5] = document.getElementById("spDelivered");
	   elementIdsArray[6] = document.getElementById("spAddedInMid");
	   elementIdsArray[7] = document.getElementById("teamCapacity");
	   elementIdsArray[8] = document.getElementById("devMembers");
	   elementIdsArray[9] = document.getElementById("qaMembers");
	   return elementIdsArray;   
   }
   
   function getTeamCapacity(){
		 var projectId = document.getElementById("project_id").value;
		 if(projectId=="0")
		 {
		 	alert("Please select project first");
		 	return false;
		 }
		 var url = "getTeamCapacityByProject?projectId="+projectId;  
		 if(window.XMLHttpRequest)
			 {	// code for IE7+, Firefox, Chrome, Opera, Safari
			 	xmlhttp3 = new XMLHttpRequest();
			 }
		 else
			 {	// code for IE6, IE5
			 	xmlhttp3 = new ActiveXObject("Microsoft.XMLHTTP");
			 }
		 xmlhttp3.onreadystatechange = setTeamCapacity;     
	     xmlhttp3.open("GET", url, true);
	     xmlhttp3.send(null);	  	
   }
   function setTeamCapacity()
   {
	   if(xmlhttp3.readyState==4)
       {   		   
		   var response = xmlhttp3.responseText;
		   if(teamCapacity != null)
			   {
		   		document.getElementById("teamCapacity").value = response;
			   }
       }
	   
   }
   
</script>

</head>
<body>
	<div id="main">
		<%@ include file="common.jsp"%>
		<div id="content">
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
						<td><select name="project_id" id="project_id" onchange="getTeamNameListForSelectedProject();getTeamCapacity();" 
							style="width: 138px;">
								<option value="0">Select Project</option>
								<s:iterator value="projectNameWithId" var="data">
									<option value='<s:property value="value"/>'
										<s:if test="project_id==#data.value"> selected </s:if>>
										<s:property value="key" />
									</option>
								</s:iterator>
                            </select></td>
					</tr>
					
					<tr>
						<td class="label"><label for="Team-name">Team
								Name</label></td>
								<td><select name="ProjectTeamNames" id="ProjectTeamNames"
							style="width: 138px;">
						</select></td>
					</tr>
					
                    <tr>
                    <td class="label">Is Kanban Followed<label class="mandatory">*</label></td>
                    <td><label><input type="radio" name="sprintReportBean.isKanbanFollowed" value="Yes" id="yes"> Yes</label></td>

                       <td><label><input type="radio" name="sprintReportBean.isKanbanFollowed" value="No" id="no"> No</label></td>
                    </tr>
					<tr>
					    <td class="label">Sprint Detail</td>
						<td class="label">Sprint Name<label class="mandatory">*</label></td>
						<td class="label">Sprint /Week Start Date<label class="mandatory">*</label></td>
						<td class="label">Sprint /Week End Date<label class="mandatory">*</label></td>
					</tr>
					<tr>
						<td class="label"></td>
						<td><input value="" name="sprintReportBean.sprintName"
							id="sprintName" type="text" size="15" maxlength="50" /></td>
						<td class="label"><input value=""
							name="sprintReportBean.startDate" id="sprintStartDt" type="text"
							size="12" maxlength="50" /></td>
						<td class="label"><input value=""
							name="sprintReportBean.endDate" id="sprintEndDt" type="text"
							size="12" maxlength="50" /></td>
					</tr>	
					<tr>
						<td></td>
						<td class="label">SP Commited<label class="mandatory">*</label></td>
						<td class="label">SP Delivered</td>
						<td class="label">SP Added in Mid Sprint/Work</td>
					</tr>
					<tr>
						<td class="label"></td>
						<td><input value="" name="sprintReportBean.spCommitted"
							id="spCommitted" type="text" size="15" maxlength="4"
							onkeypress="return isNumberKey(event);" /></td>
						<td><input value="" name="sprintReportBean.spDelivered"
							id="spDelivered" type="text" size="15" maxlength="4"
							onkeypress="return isNumberKey(event);" /></td>
						<td><input
							value="<s:property value="sprintReport.spAddedInMid" />"
							name="sprintReportBean.spAddedInMid" id="spAddedInMid"
							type="text" size="15" maxlength="4"
							onkeypress="return isNumberKey(event);" /></td>
					</tr>
					<tr>
						<td></td>
						<td class="label">Team Capacity/Velocity<label class="mandatory">*</label></td>
						<td class="label">Dev Members<label class="mandatory">*</label></td>
						<td class="label">QA Members<label class="mandatory">*</label></td>
					</tr>
					<tr>
						<td></td>
						<td><input value="" name="sprintReportBean.teamCapacity"
							id="teamCapacity" type="text" size="15" maxlength="4"
							onkeypress="return isNumberKey(event);" /></td>
						<td><input value="" name="sprintReportBean.devMembers"
							id="devMembers" type="text" size="15" maxlength="4"
							onkeypress="return isNumberKey(event);" /></td>
						<td><input value="" name="sprintReportBean.qaMembers"
							id="qaMembers" type="text" size="15" maxlength="4"
							onkeypress="return isNumberKey(event);" /></td>
					</tr>

					<tr>
						<td colspan="1"></td>
						<td><input type="submit" value="Submit" id="submitRcaId"
							onclick="submitForm()" /></td>
						<td><input type=button value="Reset" id="resetId"
							onclick="submitReset()" /></td>
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