
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
				
			
				
				 var e = document.getElementById("sprintName");
				   var sprintName = e.options[e.selectedIndex].text;
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
		 	xmlhttp7 = new XMLHttpRequest();
		 }
	 else
		 {	// code for IE6, IE5
		 	xmlhttp7 = new ActiveXObject("Microsoft.XMLHTTP");
		 }
	 xmlhttp7.onreadystatechange = setTeamNameList;     
     xmlhttp7.open("GET", url, true);
     xmlhttp7.send(null);	  	
	 
   }
   function setTeamNameList()
   {
	   if(xmlhttp7.readyState==4)
       {   		
			   
		   var response = xmlhttp7.responseText; 
		  
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
	   document.getElementById("sprintName").value="";
	   document.getElementById("sprintStartDt").value="";
	   document.getElementById("sprintEndDt").value="";
	   document.getElementById("spCommitted").value="";
	   document.getElementById("spDelivered").value="";
	   document.getElementById("spAddedInMid").value="";
	   document.getElementById("teamCapacity").value="";
	   document.getElementById("devMembers").value="";
	   document.getElementById("qaMembers").value="";
   }
   
   function resetOnUpdate()
   {
	   var elementIdArray = getElementIdArray();
	   elementIdArray[0].value="0";
	   document.getElementById("sprintName").value="";
	   clearData();
	  /*  document.getElementById('no').checked=false;
	   document.getElementById('yes').checked=false; */
	  /*  document.getElementById("ProjectTeamNames").value="";
	 
	   document.getElementById("sprintStartDt").value="";
	   document.getElementById("sprintEndDt").value="";
	   document.getElementById("spCommitted").value="";
	   document.getElementById("spDelivered").value="";
	   document.getElementById("spAddedInMid").value="";
	   document.getElementById("teamCapacity").value="";
	   document.getElementById("devMembers").value="";
	   document.getElementById("qaMembers").value=""; */
   }
   function clearData()
   
   {
	   document.getElementById("ProjectTeamNames").value="";
	   document.getElementById('no').checked=false;
   document.getElementById('yes').checked=false;
	   document.getElementById("sprintStartDt").value="";
	   document.getElementById("sprintEndDt").value="";
	   document.getElementById("spCommitted").value="";
	   document.getElementById("spDelivered").value="";
	   document.getElementById("spAddedInMid").value="";
	   document.getElementById("teamCapacity").value="";
	   document.getElementById("devMembers").value="";
	   document.getElementById("qaMembers").value="";
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
   
   
   function getSprintName()
   {

	   var project_id = document.getElementById("project_id").value; 
	   
	   var url = "getSprintNameByProjectId?project_id="+project_id;    
	   if(window.XMLHttpRequest)
		 {	// code for IE7+, Firefox, Chrome, Opera, Safari
		 	xmlhttp2 = new XMLHttpRequest();
		 }
	 else
		 {	// code for IE6, IE5
		 	xmlhttp2 = new ActiveXObject("Microsoft.XMLHTTP");
		 }
	 xmlhttp2.onreadystatechange = setSprintName;     
   xmlhttp2.open("GET", url, true);
   xmlhttp2.send(null);	  	
	 
   }
   function setSprintName()
   {
	   if(xmlhttp2.readyState==4)
       {  var response = xmlhttp2.responseText;
       if(response!='')
    	   { 
       //console.log(response.replace(/"/g,""));
   		
       console.log(response.substring(1,response.length-1)); 
       var arrString = [];
       arrString =  response.substring(1,response.length-1).split(',');
       for (var i=0; i<arrString.length; i++) {
    	   
	       document.getElementById("sprintName").options[i] = new Option(arrString[i].replace(/"/g,""), arrString[i].replace(/"/g,""));
         } }
       else
    	   {
    	   alert("Sprint details not found for this project");
    	   document.getElementById("sprintName").value="0";
    	   }
		  
		   //$('#ProjectSprintNames').val(response);
		   
       }
	   
   }
   
   
   var xmlhttp4;
   function getSprintDetails()
   {
		
	   var project_id = document.getElementById("project_id").value;
	   if(project_id=="0") {
		 	alert("Please select project first");
		 	return false;
	 	}
	   var e = document.getElementById("sprintName");
	   var sprintName = e.options[e.selectedIndex].text;
	
	  	
	  	if(sprintName=='')		 {
		 	alert("Please specify sprint name");
		 	return false;
		}
	 var url = "getSprintDetails?project_id="+project_id+"&sprintName="+sprintName; 
	 if(window.XMLHttpRequest){	// code for IE7+, Firefox, Chrome, Opera, Safari
		 xmlhttp4 = new XMLHttpRequest();
	 }else{	// code for IE6, IE5
		 xmlhttp4 = new ActiveXObject("Microsoft.XMLHTTP");
	 }
	 xmlhttp4.onreadystatechange = setMessage;     
 	var response = xmlhttp4.responseText;  
 	xmlhttp4.open("POST", url, true);
 	xmlhttp4.send(null);	
}
   


function setMessage()
{
if(xmlhttp4.readyState==4)
{  
  
	
	
	var response = xmlhttp4.responseText; 

	/* if(response == "success")
	   {	alert("Details");
	  
	
	   }
	   else
			alert("Record not found");  */
   var sr2=JSON.parse(response);

   <fmt:formatDate value="${sprintStartDt}" pattern="MM/dd/yyyy"/>
	   <fmt:formatDate value="${sprintEndDt}" pattern="MM/dd/yyyy"/>
   $('#spAddedInMid').val(sr2.spAddedInMid);
   
   var dateFormatStart = $.datepicker.formatDate('mm/dd/yy', new Date(sr2.startDate));
   $('#sprintStartDt').val(dateFormatStart);
   var dateFormatEnd = $.datepicker.formatDate('mm/dd/yy', new Date(sr2.endDate));
   $('#sprintEndDt').val(dateFormatEnd);
   
   $('#spCommitted').val(sr2.spCommitted);
   $('#spDelivered').val(sr2.spDelivered);
   $('#teamCapacity').val(sr2.teamCapacity);
   $('#devMembers').val(sr2.devMembers);
   $('#qaMembers').val(sr2.qaMembers);
  
   
   if(sr2.isKanbanFollowed=="No"||sr2.isKanbanFollowed=="no")
	   { 
	
	 document.getElementById("no").checked = true;
	   }
   else if(sr2.isKanbanFollowed=="Yes"||sr2.isKanbanFollowed=="yes")
   {

 document.getElementById("yes").checked = true;
   }
 
		
		
   /* if(response == "success")
   {	alert("Details");
   alert(response);
 // $('#subtotal').val(srb);
   }
   else
		alert("Record not found"); */
}

}
var xmlhttp5;
function updateForm()
{
	if(validateForm())
	   {
		   	
 var project_id = document.getElementById("project_id").value;
 var e = document.getElementById("sprintName");
 var sprintName = e.options[e.selectedIndex].text;
  	var sprintStartdt = document.getElementById("sprintStartDt").value;
  	var sprintEnddt = document.getElementById("sprintEndDt").value;
  	var spCommitted = document.getElementById("spCommitted").value;
  	var spDelivered = document.getElementById("spDelivered").value;
  	var spAddedInMid = document.getElementById("spAddedInMid").value;
  	 var teamCapacity = document.getElementById("teamCapacity").value;
  	 var devMembers = document.getElementById("devMembers").value;
  	 var qaMembers = document.getElementById("qaMembers").value; 
  	 
  	if (document.getElementById('no').checked)
  	 var isKanbanFollowed = "no";
  	else if(document.getElementById('yes').checked)
  		 var isKanbanFollowed = "yes";
  
  	 //var url = "getSprintDetails?projectId="+projectId+"&sprintName="+sprintName+"&sprintStartdt="+sprintStartdt+"&sprintEnddt="+sprintEnddt+"&spCommitted="+spCommitted+"&spDelivered="+spDelivered+"&spAddedInMid="+spAddedInMid+"&teamCapacity="+teamCapacity+"&devMembers="+devMembers+"&qaMembers="+qaMembers; 
 var url ="updateSprintDetail?project_id="+project_id+"&sprintName="+sprintName+"&sprintStartdt="+sprintStartdt+"&sprintEnddt="+sprintEnddt+"&spCommitted="+spCommitted+"&spDelivered="+spDelivered+"&spAddedInMid="+spAddedInMid+"&teamCapacity="+teamCapacity+"&devMembers="+devMembers+"&qaMembers="+qaMembers+"&isKanbanFollowed="+isKanbanFollowed; 

 if(window.XMLHttpRequest)
 {	// code for IE7+, Firefox, Chrome, Opera, Safari
	 xmlhttp5 = new XMLHttpRequest();
 }
else
 {	// code for IE6, IE5
	xmlhttp5 = new ActiveXObject("Microsoft.XMLHTTP");
 }
 xmlhttp5.onreadystatechange = setUpdatingMessage;     
var response = xmlhttp5.responseText;  
xmlhttp5.open("POST", url, true);
xmlhttp5.send(null);	
	   }
		
}
function setUpdatingMessage()
{ if(xmlhttp5.readyState==4)
	{
	
	alert("Updated");
	resetOnUpdate();
	}}
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
						<td><select name="project_id" id="project_id" onchange="clearData();getTeamNameListForSelectedProject();getSprintName();"
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
					    	<td class="label"><label for="Sprint-name">Sprint
								Name</label></td>
								
									<td><select name="sprintName" id="sprintName"
							style="width: 138px;">
							<option value="0">Select Sprint</option>
						</select></td>
							
								<!-- <td><input value="" name="sprintReportBean.sprintName"
							id="sprintName" type="text" size="15" maxlength="50" /></td>  -->
						
						<td><input type="button" value="Get Details"
							onclick="getSprintDetails();" /></td>
							
						
					    </tr>
					    
					<tr>
					    <td></td>
					<!-- 	<td class="label">Sprint Name<label class="mandatory">*</label></td> -->
						<td class="label">Sprint /Week Start Date<label class="mandatory">*</label></td>
						<td class="label">Sprint /Week End Date<label class="mandatory">*</label></td>
					</tr>
					<tr>
						<td class="label"></td>
						<!-- <td><input value="" name="sprintReportBean.sprintName"
							id="sprintName" type="text" size="15" maxlength="50" /></td> -->
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
					
							<td><input type="submit" value="Update" id="updateRcaId"
							onclick="updateForm()" /></td>
							
						<td><input type=button value="Reset" id="resetId"
							onclick="resetOnUpdate()" /></td>
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