
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>RCA</title>
<link href="css/style.css" rel="stylesheet" />
<script type="text/javascript">

function calculateWeek()
{
	var d = new Date();
	d.setMonth(d.getMonth()-3);
	var dateStartFrom = new Date("09/22/2014");
	if(d<dateStartFrom)
		d = dateStartFrom;
	
	var weekday = new Array(7);
	weekday[0]=  "Sunday";
	weekday[1] = "Monday";
	weekday[2] = "Tuesday";
	weekday[3] = "Wednesday";
	weekday[4] = "Thursday";
	weekday[5] = "Friday";
	weekday[6] = "Saturday";
	var n = weekday[d.getDay()];
		if(n=="Tuesday")
		d.setDate(d.getDate()-1);
		if(n=="Wednesday")
		d.setDate(d.getDate()-2);
		if(n=="Thursday")
		d.setDate(d.getDate()-3);
		if(n=="Friday")
		d.setDate(d.getDate()-4);
		if(n=="Saturday")
		d.setDate(d.getDate()-5);
		if(n=="Sunday")
		d.setDate(d.getDate()-6);

		var date2 = new Date();
		date2.setMonth(d.getMonth());
		date2.setFullYear(d.getFullYear());
		date2.setDate(d.getDate()+6);
		
		var mondays = new Array();
		var sundays = new Array();
		var months = new Array();
		var years = new Array();
		var sundaysMonth = new Array();
		
		var d2 = new Date();
		// d2.setDate(d2.getDate()+3);
		
		for(var i=0;d<=d2;i++)
			{
			 mondays[i]= d.getDate();
			 months[i]= d.getMonth()+1;
			 years[i]= d.getFullYear();
			 d.setDate(d.getDate()+7);
			}
		for(var i=0;date2<=d2;i++)
			{
			 sundays[i]= date2.getDate();
			 sundaysMonth[i]= date2.getMonth()+1;
			 date2.setDate(date2.getDate()+7);
			}
		
		   var x = document.getElementById("week_id");
		   var weekValue = x.value;
		   
		   var option = document.createElement("option");
		   option.text = "Select Week";
		   option.value = "Select Week";
		   x.add(option,x[0]);		   		   
		   
		   for(var i = 0; i < mondays.length-1; i++) {
			   var option = document.createElement("option");
			   var text = months[i]+"/"+mondays[i]+"-"+sundaysMonth[i]+"/"+sundays[i];
			   var value = months[i]+"/"+mondays[i]+"/"+years[i]+"-"+sundaysMonth[i]+"/"+sundays[i]+"/"+years[i+1];
			   if(weekValue==value)
				   continue;
			   option.text = text;
			   option.value = value;
			   x.add(option,x[i+1]);
		   }  
}
   
	function submitForm()
	{
		   if(validateForm())
			   {
			   	document.RCA_Form.action="saveSprintDetail";
			    document.RCA_Form.submit();
			   }
		    
	}
	function serchRcaData()
	{
		   var result=validateProjectAndWeek();
		    if(result)
		    	{
				document.RCA_Form.action="getSprintDetail";
		    	document.RCA_Form.submit();
		    	}
	}
	
	
	function validateProjectAndWeek()
	{
		   
			var projectName = document.getElementById("project_id").value;
		   
		   	if(projectName=="Select Project" || projectName=="0" || projectName=="")
		   	{
		    	alert("Please select project name");
		    	return false;
		    }	    
			var week = document.getElementById("week_id").value;
		    if(week=="Select Week" || week=="")
		    {
		    	alert("Please select week");
		    	return false;
		    }
			
		    return true;
	}
	
	
	
	function validateForm()
	{
		   
			var projectName = document.getElementById("project_id").value;
		   
		   	if(projectName=="Select Project" || projectName=="0" || projectName=="")
		   	{
		    	alert("Please select project name");
		    	return false;
		    }	    
			var week = document.getElementById("week_id").value;
		    if(week=="Select Week" || week=="")
		    {
		    	alert("Please select week");
		    	return false;
		    }
		    var sprint1Name = document.getElementById("sprint1Name").value;
		 	if(sprint1Name=="" || sprint1Name==null)
		    {
		    	alert("Sprint 1 Name can not be blank");
		    	return false;
		    }
			var sprint1UserStory = document.getElementById("sprint1UserStory").value;
			if(sprint1UserStory=="" || sprint1UserStory==null)
			{
				alert("Sprint 1 User Story can not be blank");
				return false;
			}
			var sprint1BugCount = document.getElementById("sprint1BugCount").value;
			if(sprint1BugCount=="" || sprint1BugCount==null)
			{
			   	alert("Sprint 1 Bug Count can not be blank");
			    return false;
			}
// 			var sprint2Name = document.getElementById("sprint2Name").value;
// 			if(sprint2Name=="" || sprint2Name==null)
// 			{
// 			  	alert("Sprint 2 Name can not be blank");
// 			    	return false;
// 			}
// 			var sprint2UserStory = document.getElementById("sprint2UserStory").value;
// 			if(sprint2UserStory=="" || sprint2UserStory==null)
// 			{
// 				alert("Sprint 2 User Story can not be blank");
// 				return false;
// 			}
// 			var sprint2BugCount = document.getElementById("sprint2BugCount").value;
// 			if(sprint2BugCount=="" || sprint2BugCount==null)
// 			{
// 				alert("Sprint 2 Bug Count can not be blank");
// 				return false;
// 			}
			var devMembers = document.getElementById("devMembers").value;
			if(devMembers=="" || devMembers==null)
			{
				alert("Dev Members can not be blank");
				return false;
			}
			
			var qaMembers = document.getElementById("qaMembers").value;
			if(qaMembers=="" || qaMembers==null)
			{
				alert("QA Members can not be blank");
				return false;
			}
			
		    return true;
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
   
  
   function disableSubmit()
   {
	   var todaysDate = new Date();
	   // todaysDate.setDate(todaysDate.getDate()+2);
	   var lastWeekDay = null;
	   var dayDiff = null;
	   var weekValue = document.getElementById("week_id").value;
	   var weeks = new Array();
	   var role = "<%=(String) session.getAttribute("role")%>";	  
	   //alert("Role is: " + role);
	   if(weekValue != "Select Week")
		   {
		 	  weeks = weekValue.split("-");		   
			  if(weeks[1] != "" || weeks[1] != null)  
			 	lastWeekDay = new Date(weeks[1]);
			  
			  getDevQAMember();
			 
			  dayDiff = (todaysDate.getTime() - lastWeekDay.getTime())/(1000*60*60*24);
		   }
	   if(dayDiff != null && dayDiff>2 && role != "manager")
		   {
			   document.getElementById("submitRcaId").disabled=true;
			  /*  document.getElementById("updateId").disabled=true; */
			   document.getElementById("resetId").disabled=true;
			   disableTextFields(true);
			   submitReset();
			   document.getElementById("tuesdayError").innerHTML = "You cannot fill RCA data after due date i.e. every Monday.";
		   }
	   else
		   {
			   document.getElementById("submitRcaId").disabled=false;
			  /*  document.getElementById("updateId").disabled=false; */
			   document.getElementById("resetId").disabled=false;
			   disableTextFields(false);
			   submitReset();
		   }
   }
   
   var xmlhttp;
   function  getDevQAMember()
   {
	   var projectId = document.getElementById("project_id").value;
	   if(projectId=="0")
		   return false;
	   var weekObj = document.getElementById("week_id");
	   if(weekObj.value=="Select Week")
		   return false;
	   var preWeek = weekObj.options[weekObj.selectedIndex-1].value;	 
	   var URL = "getSprintDevQAMembers?weekStr="+preWeek+"&project_id="+projectId;
	   if (window.XMLHttpRequest)
	     {// code for IE7+, Firefox, Chrome, Opera, Safari
	     	xmlhttp=new XMLHttpRequest();
	     }
	   else
	     {// code for IE6, IE5
	    	 xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	     }
	   xmlhttp.onreadystatechange = setDevQAMember;     
       xmlhttp.open("GET", URL, true);
       xmlhttp.send(null);	  	
   }
   
   function setDevQAMember() {       
       if(xmlhttp.readyState==4)
       {                 
           var response = xmlhttp.responseText;
           var devQa = response.split("_");
           if(devQa[0] |= "null")
           		document.getElementById("devMembers").value = devQa[0];
           else
        	   document.getElementById("devMembers").value = "";
           if(devQa[1] |= "null")
           		document.getElementById("qaMembers").value = devQa[1];
           else
        	   document.getElementById("qaMembers").value = "";
       }
   }  
   function disableTextFields(isDisabled)
   {
	   var elementIdArray = getElementIdArray();
	   for(var i=0;i<elementIdArray.length;i++)
	   {
	  	 elementIdArray[i].disabled=isDisabled;
	   }
   }
   function submitReset()
   {
	   var elementIdArray = getElementIdArray();
	   for(var i=0;i<elementIdArray.length;i++)
		   {
		  	 elementIdArray[i].value="";
		   }
	   document.getElementById("tuesdayError").disabled=false;
   }
   function getElementIdArray()
   {	
	   var elementIdsArray = new Array();	 	   
	   
	   elementIdsArray[0] = document.getElementById("sprint1Name");
	   elementIdsArray[1] = document.getElementById("sprint2Name");
	   elementIdsArray[2] = document.getElementById("sprint1UserStory");
	   elementIdsArray[3] = document.getElementById("sprint2UserStory");
	   elementIdsArray[4] = document.getElementById("sprint1BugCount");
	   elementIdsArray[5] = document.getElementById("sprint2BugCount");
	   elementIdsArray[6] = document.getElementById("devMembers");
	   elementIdsArray[7] = document.getElementById("qaMembers");
	 
	   
	  	return elementIdsArray;   
   }
 
</script>
</head>
<body onload="calculateWeek()">
	<div id="main">
		<%@ include file="common.jsp"%>
		<div id="content">
			<div id="tuesdayError" class="errors" style="color: red;"
				align="center"></div>
			<form method="post" name="RCA_Form" id="RCA_Form"
				onsubmit="return false" enctype="multipart/form-data">
				<table cellspacing="12" class="content-table">
					<%@ include file="leftMenu.jsp"%>

					<tr>
						<td>&nbsp;</td>
					</tr>

					<tr>
						<td style="float: right;"><label for="project-name">Project
								Name</label></td>
						<td colspan="2"><select name="project_id" id="project_id"
							style="width: 120px;" onchange="getDevQAMember();">
								<option value="0">Select Project</option>
								<s:iterator value="projectNameWithId" var="data">
									<option value='<s:property value="value"/>'
										<s:if test="project_id==#data.value"> selected </s:if>>
										<s:property value="key" />
									</option>
								</s:iterator>

						</select></td>
						<td colspan="2"></td>

					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td style="float: right;"><label for="week">Week</label></td>
						<td colspan="2"><select name="week" id="week_id"
							style="width: 120px;" onchange="disableSubmit();">
								<s:if
									test="sprintReport.week != null && !sprintReport.week.equals('') && !sprintReport.week.equals('Select Week')">
									<option value="<s:property value="sprintReport.week" />">
										<s:property value="weekStr" />
									</option>
								</s:if>

						</select></td>
						<td><input type="submit" value="Search" id="searchId"
							onclick="serchRcaData()" /></td>
					</tr>

					<tr>
						<td>&nbsp;</td>
					</tr>

					<tr>
						<td></td>
						<td style="text-align: center;">Sprint Name</td>
						<td style="text-align: center;">User Story</td>
						<td style="text-align: center;">Bug Count</td>
					</tr>
					<tr>
						<td style="float: right;">Sprint 1</td>
						<td><input
							value="<s:property value="sprintReport.sprint1Name" />"
							name="sprint1Name" id="sprint1Name" type="text" size="12"
							maxlength="50" /></td>
						<td><input
							value="<s:property value="sprintReport.sprint1UserStory" />"
							name="sprint1UserStory" id="sprint1UserStory" type="text"
							size="12" maxlength="4" onkeypress="return isNumberKey(event);" /></td>
						<td><input
							value="<s:property value="sprintReport.sprint1BugCount" />"
							name="sprint1BugCount" id="sprint1BugCount" type="text" size="12"
							maxlength="4" onkeypress="return isNumberKey(event);" /></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td></td>
						<td>Sprint Name</td>
						<td>User Story</td>
						<td>Bug Count</td>
					</tr>
					<tr>
						<td style="float: right;">Sprint 2</td>
						<td><input
							value="<s:property value="sprintReport.sprint2Name" />"
							name="sprint2Name" id="sprint2Name" type="text" size="12"
							maxlength="50" /></td>
						<td><input
							value="<s:property value="sprintReport.sprint2UserStory" />"
							name="sprint2UserStory" id="sprint2UserStory" type="text"
							size="12" maxlength="4" onkeypress="return isNumberKey(event);" /></td>
						<td><input
							value="<s:property value="sprintReport.sprint2BugCount" />"
							name="sprint2BugCount" id="sprint2BugCount" type="text" size="12"
							maxlength="4" onkeypress="return isNumberKey(event);" /></td>
					</tr>

					<tr>
						<td>&nbsp;</td>
					</tr>

					<tr>
						<td style="float: right;"><label for="project-name">Dev
								Members</label></td>
						<td colspan="2"><input
							value="<s:property value="sprintReport.devMembers" />"
							name="devMembers" id="devMembers" type="text" size="12"
							maxlength="4" onkeypress="return isNumberKey(event);" /></td>
						<td colspan="2"></td>

					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td style="float: right;"><label for="week">QA
								Members</label></td>
						<td colspan="2"><input
							value="<s:property value="sprintReport.qaMembers" />"
							name="qaMembers" id="qaMembers" type="text" size="12"
							maxlength="4" onkeypress="return isNumberKey(event);" /></td>
						<td colspan="2"></td>
					</tr>

					<tr>
						<td>&nbsp;</td>
					</tr>

					<tr>
						<td colspan="1"></td>
						<td><input type="submit" value="Submit" id="submitRcaId"
							onclick="submitForm()"
							<s:if test="isdisabled==true"> disabled </s:if> /></td>
						<!-- <td><input type=button value="Update" id="updateId" onclick="updateRca()" <s:if test="isdisabled==true"> disabled </s:if> /></td> -->
						<td><input type=button value="Reset" id="resetId"
							onclick="submitReset()"
							<s:if test="isdisabled==true"> disabled </s:if> /></td>
					</tr>


				</table>

				<table cellspacing="12" class="content-table">
				

					<tr align="center">
						<td colspan="5" style="padding-top: 30px;"><s:if
								test="hasActionErrors()">
								<div class="errors" style="color: red;">
									<s:actionerror />
								</div>
							</s:if> <s:if test="hasActionMessages()">
								<div class="success" style="color: yellow;">
									<b><s:actionmessage /></b>
								</div>
							</s:if></td>
						<td colspan="3"></td>
					</tr>
				</table>               
			</form>
		</div>
	</div>
</body>
</html>

