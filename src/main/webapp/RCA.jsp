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
			   var text = months[i]+"/"+mondays[i]+"-"+months[i+1]+"/"+sundays[i];
			   var value = months[i]+"/"+mondays[i]+"/"+years[i]+"-"+months[i+1]+"/"+sundays[i]+"/"+years[i+1];
			   if(weekValue==value)
				   continue;
			   option.text = text;
			   option.value = value;
			   x.add(option,x[i+1]);
		   } 
		   
		   checkManagerSectionVisibility();
}
   
   function checkManagerSectionVisibility()
   {
	   var role = "<%= (String) session.getAttribute("role")%>";
	   if(role != "manager")
	   {
		   document.getElementById("manager_sections").style.display = "none";
	   }
   }
   
   function submitForm()
   {
	   if(valideateProjectAndWeek())
		   {
		   	document.RCA_Form.action="saveRcaDetail";
		    document.RCA_Form.submit();
		   }
	    
   }
  /*  function updateRca()
   {
	   if(valideateProjectAndWeek())
		   {
		   	document.RCA_Form.action="updateRcaDetail";
		    document.RCA_Form.submit();
		   }
	    
   }  */  
   function serchRcaData()
   {
	   var result=valideateProjectAndWeek();
	    if(result)
	    	{
			document.RCA_Form.action="getRcaDetail";
	    	document.RCA_Form.submit();
	    	}
   }
   function valideateProjectAndWeek()
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
   
   function exportData()
   {
	   var week = document.getElementById("week_id").value;
	   if(week=="Select Week" || week=="")
	   	{
	   	alert("Please select week");	
	   	return false;
	   	}
	   else
		   {
			document.RCA_Form.action="exportData";
	    	document.RCA_Form.submit();
		   }
   }
   function templateDownload()
   {
	document.RCA_Form.action="templateDownload";
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
  
   function disableSubmit()
   {
	   var todaysDate = new Date();
	   // todaysDate.setDate(todaysDate.getDate()+2);
	   var lastWeekDay = null;
	   var dayDiff = null;
	   var weekValue = document.getElementById("week_id").value;
	   var weeks = new Array();
	   var role = "<%= (String) session.getAttribute("role")%>";
	   //alert("Role is: " + role);
	   if(weekValue != "Select Week")
		   {
		 	  weeks = weekValue.split("-");		   
			  if(weeks[1] != "" || weeks[1] != null)  
			 	lastWeekDay = new Date(weeks[1]);
		
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
			   document.getElementById("overview_message").style.display = "none";
			   document.getElementById("overview_label").style.display = "none";
			   document.getElementById("risks_issues").style.display = "none";
			   document.getElementById("risks_label").style.display = "none";
		   }
	   else if(dayDiff != null && dayDiff>3 && role == "manager")
		   {

			   document.getElementById("submitRcaId").disabled=true;
			  /*  document.getElementById("updateId").disabled=true; */
			   document.getElementById("resetId").disabled=true;
			   disableTextFields(true);
			   submitReset();
			   document.getElementById("tuesdayError").innerHTML = "You cannot fill RCA data after due date i.e. every Tuesday.";
		   }
	   else
		   {
			   document.getElementById("submitRcaId").disabled=false;
			  /*  document.getElementById("updateId").disabled=false; */
			   document.getElementById("resetId").disabled=false;
			   disableTextFields(false);
			   submitReset();
			   document.getElementById("tuesdayError").innerHTML = "";
			   if(role != "manager")
				   {
					   document.getElementById("overview_message").style.display = "none";
					   document.getElementById("overview_label").style.display = "none";
					   document.getElementById("risks_issues").style.display = "none";
					   document.getElementById("risks_label").style.display = "none";
				   }

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
	   
	   elementIdsArray[0] = document.getElementById("mr_product_backlog");
	   elementIdsArray[1] = document.getElementById("mr_qa");
	   elementIdsArray[2] = document.getElementById("mr_uat");
	   elementIdsArray[3] = document.getElementById("mr_prod");
	   elementIdsArray[4] = document.getElementById("cr_product_backlog");
	   elementIdsArray[5] = document.getElementById("cr_qa");
	   elementIdsArray[6] = document.getElementById("cr_uat");
	   elementIdsArray[7] = document.getElementById("cr_prod");
	   elementIdsArray[8] = document.getElementById("ccb_product_backlog");
	   elementIdsArray[9] = document.getElementById("ccb_qa");
	   elementIdsArray[10] = document.getElementById("ccb_uat");
	   elementIdsArray[11] = document.getElementById("ccb_prod");
	   elementIdsArray[12] = document.getElementById("ad_product_backlog");
	   elementIdsArray[13] = document.getElementById("ad_qa");
	   elementIdsArray[14] = document.getElementById("ad_uat");
	   elementIdsArray[15] = document.getElementById("ad_prod");
	   elementIdsArray[16] = document.getElementById("dup_product_backlog");
	   elementIdsArray[17] = document.getElementById("dup_qa");
	   elementIdsArray[18] = document.getElementById("dup_uat");
	   elementIdsArray[19] = document.getElementById("dup_prod");
	   elementIdsArray[20] = document.getElementById("nad_product_backlog");
	   elementIdsArray[21] = document.getElementById("nad_qa");
	   elementIdsArray[22] = document.getElementById("nad_uat");
	   elementIdsArray[23] = document.getElementById("nad_prod");
	   elementIdsArray[24] = document.getElementById("bsi_product_backlog");
	   elementIdsArray[25] = document.getElementById("bsi_qa");
	   elementIdsArray[26] = document.getElementById("bsi_uat");
	   elementIdsArray[27] = document.getElementById("bsi_prod");
	   elementIdsArray[28] = document.getElementById("utr_product_backlog");
	   elementIdsArray[29] = document.getElementById("utr_qa");
	   elementIdsArray[30] = document.getElementById("utr_uat");
	   elementIdsArray[31] = document.getElementById("utr_prod");
	   elementIdsArray[32] = document.getElementById("pd_product_backlog");
	   elementIdsArray[33] = document.getElementById("pd_qa");
	   elementIdsArray[34] = document.getElementById("pd_uat");
	   elementIdsArray[35] = document.getElementById("pd_prod");
	   elementIdsArray[36] = document.getElementById("di_product_backlog");
	   elementIdsArray[37] = document.getElementById("di_qa");
	   elementIdsArray[38] = document.getElementById("di_uat");
	   elementIdsArray[39] = document.getElementById("di_prod");
	   elementIdsArray[40] = document.getElementById("plan_product_backlog");
	   elementIdsArray[41] = document.getElementById("plan_qa");
	   elementIdsArray[42] = document.getElementById("plan_uat");
	   elementIdsArray[43] = document.getElementById("plan_prod");
	   elementIdsArray[44] = document.getElementById("rate_product_backlog");
	   elementIdsArray[45] = document.getElementById("rate_qa");
	   elementIdsArray[46] = document.getElementById("rate_uat");
	   elementIdsArray[47] = document.getElementById("rate_prod");
	   elementIdsArray[48] = document.getElementById("rpa_product_backlog");
	   elementIdsArray[49] = document.getElementById("rpa_qa");
	   elementIdsArray[50] = document.getElementById("rpa_uat");
	   elementIdsArray[51] = document.getElementById("rpa_prod");
	   elementIdsArray[52] = document.getElementById("ac_product_backlog");
	   elementIdsArray[53] = document.getElementById("ac_qa");
	   elementIdsArray[54] = document.getElementById("ac_uat");
	   elementIdsArray[55] = document.getElementById("ac_prod");
	   elementIdsArray[56] = document.getElementById("ti_product_backlog");
	   elementIdsArray[57] = document.getElementById("ti_qa");
	   elementIdsArray[58] = document.getElementById("ti_uat");
	   elementIdsArray[59] = document.getElementById("ti_prod");
	   elementIdsArray[60] = document.getElementById("dp_product_backlog");
	   elementIdsArray[61] = document.getElementById("dp_qa");
	   elementIdsArray[62] = document.getElementById("dp_uat");
	   elementIdsArray[63] = document.getElementById("dp_prod");
	   elementIdsArray[64] = document.getElementById("env_product_backlog");
	   elementIdsArray[65] = document.getElementById("env_qa");
	   elementIdsArray[66] = document.getElementById("env_uat");
	   elementIdsArray[67] = document.getElementById("env_prod");
	   elementIdsArray[68] = document.getElementById("co_product_backlog");
	   elementIdsArray[69] = document.getElementById("co_qa");
	   elementIdsArray[70] = document.getElementById("co_uat");
	   elementIdsArray[71] = document.getElementById("co_prod");
	   elementIdsArray[72] = document.getElementById("ffm_product_backlog");
	   elementIdsArray[73] = document.getElementById("ffm_qa");
	   elementIdsArray[74] = document.getElementById("ffm_uat");
	   elementIdsArray[75] = document.getElementById("ffm_prod");
	   elementIdsArray[76] = document.getElementById("crmesb_product_backlog");
	   elementIdsArray[77] = document.getElementById("crmesb_qa");
	   elementIdsArray[78] = document.getElementById("crmesb_uat");
	   elementIdsArray[79] = document.getElementById("crmesb_prod");
	   elementIdsArray[80] = document.getElementById("otp_product_backlog");
	   elementIdsArray[81] = document.getElementById("otp_qa");
	   elementIdsArray[82] = document.getElementById("otp_uat");
	   elementIdsArray[83] = document.getElementById("otp_prod");
	   elementIdsArray[84] = document.getElementById("pmuu_product_backlog");
	   elementIdsArray[85] = document.getElementById("pmuu_qa");
	   elementIdsArray[86] = document.getElementById("pmuu_uat");
	   elementIdsArray[87] = document.getElementById("pmuu_prod");
	   elementIdsArray[88] = document.getElementById("io_product_backlog");
	   elementIdsArray[89] = document.getElementById("io_qa");
	   elementIdsArray[90] = document.getElementById("io_uat");
	   elementIdsArray[91] = document.getElementById("io_prod");
	   elementIdsArray[92] = document.getElementById("ro_qa");
	   elementIdsArray[93] = document.getElementById("ro_uat");
	   elementIdsArray[94] = document.getElementById("ro_prod");
	   elementIdsArray[95] = document.getElementById("overview_message");
	   elementIdsArray[96] = document.getElementById("risks_issues");
	   
	  	return elementIdsArray;   
   }
 
</script>
</head>
<body onload="calculateWeek()">
     <div id="main">
		<%@ include file="common.jsp"%>
		<div id="content">
		<div id="tuesdayError" class="errors" style="color: red;" align="center"> </div>
		<form method="post" name="RCA_Form" id="RCA_Form" onsubmit="return false" enctype="multipart/form-data" >
		  <table cellspacing="12" class="content-table">
		 <%@ include file="leftMenu.jsp"%>
			<tr>		     			 
				<td style="float:left;"><label for="project-name">Project Name: </label> 
				<select name="project_id" id="project_id" style="width:120px;" onchange="disableSubmit();">
			    <option value="0">Select Project</option>
			    <s:iterator value="projectNameWithId" var="data">
        		  <option value='<s:property value="value"/>' <s:if test="rca.project_id==#data.value"> selected </s:if> ><s:property value="key" /></option>  
       			</s:iterator> 
									
				</select></td> 
				<td style="float:left; padding-left: 20px;"><label for="week">Week: </label>
				<select name="week" id="week_id"  style="width:120px;" onchange="disableSubmit();">	
				<s:if test="rca.week != null && !rca.week.equals('') && !rca.week.equals('Select Week')">	
				<option value="<s:property value="rca.week" />"><s:property value="weekStr" /></option>				
				</s:if>	

				</select>
				<input type="submit" value="Search" id="searchId" onclick="serchRcaData()"/></td>
				<!-- <td colspan="2"></td> -->          
    			  
			</tr> <tr>
<%-- 				<td style="float:right;"><label for="week">Week</label> </td>
				<td colspan="2"><select name="week" id="week_id"  style="width:120px;" onchange="disableSubmit();">	
				<s:if test="rca.week != null && !rca.week.equals('') && !rca.week.equals('Select Week')">	
				<option value="<s:property value="rca.week" />"><s:property value="weekStr" /></option>				
				</s:if>	

				</select></td> --%>
				<!-- <td ><input type="submit" value="Search" id="searchId" onclick="serchRcaData()"/></td> -->
				 <s:if test="hasActionErrors()">
                  <td style="color: red; font-size: 13px;">                				 
				      <s:actionerror/>	
				      </td>			  
				</s:if>
				<s:if test="hasActionMessages()">
				 <td style="color: blue; font-size: 13px;">
				      <b><s:actionmessage/></b>	
				 </td>			   
				</s:if>	
				</tr>
				</table>
				<table cellspacing="12" class="content-table">
				<tr>
			    <td style="padding-top: 20px;"></td>			   
			    <td> <label for="QA">QA</label></td>				
				<td> <label for="Uat">UAT</label></td>
				<td> <label for="Prod">PROD</label></td>
				 <td> <label for="Open_backlog">OPEN</label></td>
				
			</tr> <tr>
				<td style="float:right;"><label for="missed_requirement">Missed Requirement</label></td>				
				<td><input type="text" value="<s:property value="rca.mr_qa" />" name="mr_qa" id="mr_qa" size="8" maxlength="4" onkeypress="return isNumberKey(event);"  <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.mr_uat" />" name="mr_uat" id="mr_uat" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.mr_prod" />" name="mr_prod" id="mr_prod" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.mr_product_backlog" />" name="mr_product_backlog" id="mr_product_backlog" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
			</tr> <tr>
				<td style="float:right;"><label for="change_requirement ">Change Requirement</label></td>				
				<td><input type="text" value="<s:property value="rca.cr_qa" />" name="cr_qa" id="cr_qa" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.cr_uat" />" name="cr_uat" id="cr_uat" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.cr_prod" />" name="cr_prod" id="cr_prod" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.cr_product_backlog" />" name="cr_product_backlog" id="cr_product_backlog" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
			</tr> <tr>
				<td style="float:right;"><label for="client_code_bug  ">Client Code Bug</label></td>				
				<td><input type="text" value="<s:property value="rca.ccb_qa" />" name="ccb_qa" id="ccb_qa" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if>/></td>
				<td><input type="text" value="<s:property value="rca.ccb_uat" />" name="ccb_uat" id="ccb_uat" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if>/></td>
				<td><input type="text" value="<s:property value="rca.ccb_prod" />" name="ccb_prod" id="ccb_prod" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if>/></td>
				<td><input type="text" value="<s:property value="rca.ccb_product_backlog" />" name="ccb_product_backlog" id="ccb_product_backlog" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if>/></td>
			</tr> <tr>
				<td style="float:right;"><label for="as_designed ">As Designed</label></td>				
				<td><input type="text" value="<s:property value="rca.ad_qa" />" name="ad_qa" id="ad_qa" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.ad_uat" />" name="ad_uat" id="ad_uat" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.ad_prod" />" name="ad_prod" id="ad_prod" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.ad_product_backlog" />" name="ad_product_backlog" id="ad_product_backlog" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
			</tr> <tr>
				<td style="float:right;"><label for="duplicate">Duplicate</label></td>				
				<td><input type="text" value="<s:property value="rca.dup_qa" />" name="dup_qa" id="dup_qa" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.dup_uat" />" name="dup_uat" id="dup_uat" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.dup_prod" />" name="dup_prod" id="dup_prod" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.dup_product_backlog" />" name="dup_product_backlog" id="dup_product_backlog" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
			</tr> <tr>
				<td style="float:right;"><label for="not_a_defect ">Not A Defect</label></td>				
				<td><input type="text" value="<s:property value="rca.nad_qa" />" name="nad_qa" id="nad_qa" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.nad_uat" />" name="nad_uat" id="nad_uat" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.nad_prod" />" name="nad_prod" id="nad_prod" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.nad_product_backlog" />" name="nad_product_backlog" id="nad_product_backlog" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
			</tr> <tr>
				<td style="float:right;"><label for="browser_specific_issue ">Browser Specific Issue</label></td>				
				<td><input type="text" value="<s:property value="rca.bsi_qa" />" name="bsi_qa" id="bsi_qa" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.bsi_uat" />" name="bsi_uat" id="bsi_uat" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.bsi_prod" />" name="bsi_prod" id="bsi_prod" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.bsi_product_backlog" />" name="bsi_product_backlog" id="bsi_product_backlog" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
			</tr> <tr>
				<td style="float:right;"><label for="unable_to_reproduce">Unable To Reproduce</label></td>				
				<td><input type="text" value="<s:property value="rca.utr_qa" />" name="utr_qa" id="utr_qa" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.utr_uat" />" name="utr_uat" id="utr_uat" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.utr_prod" />" name="utr_prod" id="utr_prod" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.utr_product_backlog" />" name="utr_product_backlog" id="utr_product_backlog" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
			</tr> <tr>
				<td style="float:right;"><label for="product_defect ">Product Defect</label></td>				
				<td><input type="text" value="<s:property value="rca.pd_qa" />" name="pd_qa" id="pd_qa" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.pd_uat" />" name="pd_uat" id="pd_uat" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.pd_prod" />" name="pd_prod" id="pd_prod" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.pd_product_backlog" />" name="pd_product_backlog" id="pd_product_backlog" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
			</tr>   <tr>
				<td style="float:right;"><label for="data_issue  ">Data Issue</label></td>				
				<td><input type="text" value="<s:property value="rca.di_qa" />" name="di_qa" id="di_qa" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.di_uat" />" name="di_uat" id="di_uat" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.di_prod" />" name="di_prod" id="di_prod" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.di_product_backlog" />" name="di_product_backlog" id="di_product_backlog" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
			</tr>
			<tr>
				<td style="float:right;"><label for="configuration">Configuration Issue: </label></td>				
				<td colspan="5">Configuration Sub Categories</td>
			</tr> <tr>
				<td style="float:right; color: white;"><label for="plan_package">Plan Package</label></td>				
				<td><input type="text" value="<s:property value="rca.plan_qa" />" name="plan_qa" id="plan_qa" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.plan_uat" />" name="plan_uat" id="plan_uat" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.plan_prod" />" name="plan_prod" id="plan_prod" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.plan_product_backlog" />" name="plan_product_backlog" id="plan_product_backlog" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
			</tr> <tr>
				<td style="float:right; color: white;"><label for="rate_package">Rate Package</label></td>				
				<td><input type="text" value="<s:property value="rca.rate_qa" />" name="rate_qa" id="rate_qa" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.rate_uat" />" name="rate_uat" id="rate_uat" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.rate_prod" />" name="rate_prod" id="rate_prod" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.rate_product_backlog" />" name="rate_product_backlog" id="rate_product_backlog" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
			</tr> <tr>
				<td style="float:right; color: white;"><label for="rules_plan_advisor">Rules And Plan Advisor</label></td>				
				<td><input type="text" value="<s:property value="rca.rpa_qa" />" name="rpa_qa" id="rpa_qa" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.rpa_uat" />" name="rpa_uat" id="rpa_uat" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.rpa_prod" />" name="rpa_prod" id="rpa_prod" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.rpa_product_backlog" />" name="rpa_product_backlog" id="rpa_product_backlog" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
			</tr> <tr>
				<td style="float:right; color: white;"><label for="app_config">App Config</label></td>				
				<td><input type="text" value="<s:property value="rca.ac_qa" />" name="ac_qa" id="ac_qa" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.ac_uat" />" name="ac_uat" id="ac_uat" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.ac_prod" />" name="ac_prod" id="ac_prod" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.ac_product_backlog" />" name="ac_product_backlog" id="ac_product_backlog" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
			</tr>  <tr>
				<td style="float:right; color: white;"><label for="template_issues">Template Issues</label></td>				
				<td><input type="text" value="<s:property value="rca.ti_qa" />" name="ti_qa" id="ti_qa" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.ti_uat" />" name="ti_uat" id="ti_uat" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.ti_prod" />" name="ti_prod" id="ti_prod" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.ti_product_backlog" />" name="ti_product_backlog" id="ti_product_backlog" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
			</tr>  <tr>
				<td style="float:right; color: white;"><label for="deployment_properties">Deployment Properties</label></td>				
				<td><input type="text" value="<s:property value="rca.dp_qa" />" name="dp_qa" id="dp_qa" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.dp_uat" />" name="dp_uat" id="dp_uat" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.dp_prod" />" name="dp_prod" id="dp_prod" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.dp_product_backlog" />" name="dp_product_backlog" id="dp_product_backlog" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
			</tr>  <tr>
				<td style="float:right; color: white;"><label for="environment">Environment</label></td>				
				<td><input type="text" value="<s:property value="rca.env_qa" />" name="env_qa" id="env_qa" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.env_uat" />" name="env_uat" id="env_uat" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.env_prod" />" name="env_prod" id="env_prod" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.env_product_backlog" />" name="env_product_backlog" id="env_product_backlog" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
			</tr>  <tr>
				<td style="float:right; color: white;"><label for="config_others">Others</label></td>				
				<td><input type="text" value="<s:property value="rca.co_qa" />" name="co_qa" id="co_qa" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.co_uat" />" name="co_uat" id="co_uat" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.co_prod" />" name="co_prod" id="co_prod" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.co_product_backlog" />" name="co_product_backlog" id="co_product_backlog" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
			</tr> 
			<tr>
				<td style="float:right;"><label for="integration_issue  ">Integration Issue: </label></td>
				<td colspan="5">Integration Sub Categories</td>
			</tr> <tr>
				<td style="float:right; color: white;"><label for="ffm">FFM</label></td>				
				<td><input type="text" value="<s:property value="rca.ffm_qa" />" name="ffm_qa" id="ffm_qa" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.ffm_uat" />" name="ffm_uat" id="ffm_uat" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.ffm_prod" />" name="ffm_prod" id="ffm_prod" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.ffm_product_backlog" />" name="ffm_product_backlog" id="ffm_product_backlog" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
			</tr> <tr>
			<td style="float:right; color: white;"><label for="crm/esb">CRM/ESB</label></td>				
				<td><input type="text" value="<s:property value="rca.crmesb_qa" />" name="crmesb_qa" id="crmesb_qa" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.crmesb_uat" />" name="crmesb_uat" id="crmesb_uat" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.crmesb_prod" />" name="crmesb_prod" id="crmesb_prod" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.crmesb_product_backlog" />" name="crmesb_product_backlog" id="crmesb_product_backlog" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
			</tr> <tr>
				<td style="float:right; color: white;"><label for="other_third_party">Other Third Party</label></td>				
				<td><input type="text" value="<s:property value="rca.otp_qa" />" name="otp_qa" id="otp_qa" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.otp_uat" />" name="otp_uat" id="otp_uat" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.otp_prod" />" name="otp_prod" id="otp_prod" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.otp_product_backlog" />" name="otp_product_backlog" id="otp_product_backlog" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
			</tr> <tr>
				<td style="float:right; color: white;"><label for="product_merge/upgrade/update">Product Merge/ upgrade/ update</label></td>				
				<td><input type="text" value="<s:property value="rca.pmuu_qa" />" name="pmuu_qa" id="pmuu_qa" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.pmuu_uat" />" name="pmuu_uat" id="pmuu_uat" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.pmuu_prod" />" name="pmuu_prod" id="pmuu_prod" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.pmuu_product_backlog" />" name="pmuu_product_backlog" id="pmuu_product_backlog" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
			</tr>  <tr>
				<td style="float:right; color: white; "><label for="integration_others">Others</label></td>				
				<td><input type="text" value="<s:property value="rca.io_qa" />" name="io_qa" id="io_qa" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.io_uat" />" name="io_uat" id="io_uat" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.io_prod" />" name="io_prod" id="io_prod" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.io_product_backlog" />" name="io_product_backlog" id="io_product_backlog" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
			</tr>
			<tr>
			<td style="float:right;"><label for="data_issue">Re Open</label></td>				
				<td><input type="text" value="<s:property value="rca.ro_qa" />" name="ro_qa" id="ro_qa" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.ro_uat" />" name="ro_uat" id="ro_uat" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.ro_prod" />" name="ro_prod" id="ro_prod" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td></td>
			</tr>
			
			<tr><td colspan="6" style="text-align: left;"><div id="manager_sections">
				<table cellspacing="12">
				<tr>
				<td style="float:left;"><label for="data_issue" id="overview_label">Overview Message: </label>
				<textarea rows="2" cols="66" name="overview_message" maxlength="1500" id="overview_message" <s:if test="isdisabled==true"> disabled </s:if> ><s:property value="rca.overview_message" /></textarea></td>
				</tr>
				<tr>
				<td style="float:left;"><label for="data_issue" id="risks_label">Risks/Issues: </label>
				<textarea rows="2" cols="66" name="risks_issues" maxlength="1500" id="risks_issues" <s:if test="isdisabled==true"> disabled </s:if> ><s:property value="rca.risks_issues" /></textarea></td>
				</tr>
				</table>
				</div></td></tr>
			<%-- </table>
			
			<table cellspacing="12" class="content-table">
			<tr>
			<td colspan="1"></td>
			<td colspan="1"><label for="data_issue" id="overview_label">Overview Message: </label>
			<textarea rows="2" cols="60" name="overview_message" id="overview_message" <s:if test="isdisabled==true"> disabled </s:if> ><s:property value="rca.overview_message" /></textarea></td>
			</tr>
			<tr>
			<td colspan="1"></td>
			<td colspan="1"><label for="data_issue" id="risks_label">Risks/Issues: </label>
			<textarea rows="2" cols="60" name="risks_issues" id="risks_issues" <s:if test="isdisabled==true"> disabled </s:if> ><s:property value="rca.risks_issues" /></textarea></td>
			</tr> --%> 
		     <tr>
		        <td colspan="3"></td>
				<td><input type="submit" value="Submit" id="submitRcaId" onclick="submitForm()" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<!-- <td><input type=button value="Update" id="updateId" onclick="updateRca()" <s:if test="isdisabled==true"> disabled </s:if> /></td> -->
				<td><input type=button value="Reset" id="resetId" onclick="submitReset()" <s:if test="isdisabled==true"> disabled </s:if> /></td>
			</tr>			
			<tr>
				<!-- <div id="tuesdayError" class="errors" style="color: red;"> </div> -->
		
				  <!-- <td colspan="5" style="padding-top:50px;"><input type="submit" value="Template Download" id="template" onclick="templateDownload()"/>
			&nbsp;&nbsp;&nbsp;&nbsp;<label for="data_issue  ">Select File</label> 
			<input  type="file" name="rcaFile" id="rcaFile"  /> 
				
			<input type="submit" value="Upload RCA" onclick="upload()" /> </td>
			 -->
				</tr> 
				
			  <tr align="center">
				<td colspan="5" style="padding-top:30px;">													
				</td>
				<td colspan="3"></td>
				</tr>
				</table>
				
			</form>
		</div>
	</div>
</body>
</html>

