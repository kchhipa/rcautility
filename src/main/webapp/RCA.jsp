 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>RCA</title>
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
	   var lastWeekDay = null;
	   var dayDiff = null;
	   var weekValue = document.getElementById("week_id").value;
	   var weeks = new Array();
	   if(weekValue != "Select Week")
		   {
		 	  weeks = weekValue.split("-");		   
			  if(weeks[1] != "" || weeks[1] != null)  
			 	lastWeekDay = new Date(weeks[1]);
		
			  dayDiff = (todaysDate.getTime() - lastWeekDay.getTime())/(1000*60*60*24);
		   }
	   if(dayDiff != null && dayDiff>7)
		   {
			   document.getElementById("submitRcaId").disabled=true;
			  /*  document.getElementById("updateId").disabled=true; */
			   document.getElementById("resetId").disabled=true;
			   disableTextFields(true);
			   submitReset();
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
	   elementIdsArray[8] = document.getElementById("config_product_backlog");
	   elementIdsArray[9] = document.getElementById("config_qa");
	   elementIdsArray[10] = document.getElementById("config_uat");
	   elementIdsArray[11] = document.getElementById("config_prod");
	   elementIdsArray[12] = document.getElementById("ccb_product_backlog");
	   elementIdsArray[13] = document.getElementById("ccb_qa");
	   elementIdsArray[14] = document.getElementById("ccb_uat");
	   elementIdsArray[15] = document.getElementById("ccb_prod");
	   elementIdsArray[16] = document.getElementById("ad_product_backlog");
	   elementIdsArray[17] = document.getElementById("ad_qa");
	   elementIdsArray[18] = document.getElementById("ad_uat");
	   elementIdsArray[19] = document.getElementById("ad_prod");
	   elementIdsArray[20] = document.getElementById("dup_product_backlog");
	   elementIdsArray[21] = document.getElementById("dup_qa");
	   elementIdsArray[22] = document.getElementById("dup_uat");
	   elementIdsArray[23] = document.getElementById("dup_prod");
	   elementIdsArray[24] = document.getElementById("nad_product_backlog");
	   elementIdsArray[25] = document.getElementById("nad_qa");
	   elementIdsArray[26] = document.getElementById("nad_uat");
	   elementIdsArray[27] = document.getElementById("nad_prod");
	   elementIdsArray[28] = document.getElementById("bsi_product_backlog");
	   elementIdsArray[29] = document.getElementById("bsi_qa");
	   elementIdsArray[30] = document.getElementById("bsi_uat");
	   elementIdsArray[31] = document.getElementById("bsi_prod");
	   elementIdsArray[32] = document.getElementById("utr_product_backlog");
	   elementIdsArray[33] = document.getElementById("utr_qa");
	   elementIdsArray[34] = document.getElementById("utr_uat");
	   elementIdsArray[35] = document.getElementById("utr_prod");
	   elementIdsArray[36] = document.getElementById("pd_product_backlog");
	   elementIdsArray[37] = document.getElementById("pd_qa");
	   elementIdsArray[38] = document.getElementById("pd_uat");
	   elementIdsArray[39] = document.getElementById("pd_prod");
	   elementIdsArray[40] = document.getElementById("ii_product_backlog");
	   elementIdsArray[41] = document.getElementById("ii_qa");	   
	   elementIdsArray[42] = document.getElementById("ii_uat");
	   elementIdsArray[43] = document.getElementById("ii_prod");
	   elementIdsArray[44] = document.getElementById("di_product_backlog");
	   elementIdsArray[45] = document.getElementById("di_qa");
	   elementIdsArray[46] = document.getElementById("di_uat");
	   elementIdsArray[47] = document.getElementById("di_prod");
	   elementIdsArray[48] = document.getElementById("ro_qa");
	   elementIdsArray[49] = document.getElementById("ro_uat");
	   elementIdsArray[50] = document.getElementById("ro_prod");
	   
	  	return elementIdsArray;   
   }
   function generateReport()
   {
   	document.RCA_Form.action="rcaReportView";
   	document.RCA_Form.submit(); 
   }
</script>
</head>
<body onload="calculateWeek()">
     <div id="main">
        <div id="header" style="width: 100%; height:60px; background-color: #34495e; text-align: center; color: #fff;">
          <h1> RCA<br><span style="font-size: 16px; color:yellow;">Please submit the data for your project before 2 PM on every Monday.  <a href="logout" style="color:white; padding-left:30px;">Logout</a></span></h1>
        </div>	
		<div id="content"  style="background-color: #369044; width:100%; height:100%;">
		<form method="post" name="RCA_Form" id="RCA_Form" onsubmit="return false" enctype="multipart/form-data" >
		  <table cellspacing="12" style="font-weight:bold; padding-left:30%;  font-family:verdana;">
		     <ul>
			  <li><a href="importData.jsp" style="color:black; padding-left:30px;">Import Last Week Data</a></li>
			  <li><a href="javascript:templateDownload();" style="color:black; padding-left:30px;">Download Template</a></li>
			  <li><a href="javascript:generateReport()" style="color:black; padding-left:30px;">Generate Reports</a> </li>
			  <li><a href="exportWeeklyData.jsp" style="color:black; padding-left:30px;">Export Weekly Data</a> </li>
		   </ul>
		     <tr>
		        <td colspan="3"></td>
				<td><input type="submit" value="Submit" id="submitRcaId" onclick="submitForm()" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<!-- <td><input type=button value="Update" id="updateId" onclick="updateRca()" <s:if test="isdisabled==true"> disabled </s:if> /></td> -->
				<td><input type=button value="Reset" id="resetId" onclick="submitReset()" <s:if test="isdisabled==true"> disabled </s:if> /></td>
			
				</tr><tr>		     			 
				<td style="float:right;"><label for="project-name">Project Name</label></td> 
				<td colspan="2"><select name="project_id" id="project_id" style="width:120px;">
			    <option value="0">Select Project</option>
			    <s:iterator value="projectNameWithId" var="data">
        		  <option value='<s:property value="value"/>' <s:if test="rca.project_id==#data.value"> selected </s:if> ><s:property value="key" /></option>  
       			</s:iterator> 
				<!-- <option value="1" <s:if test="rca.project_id==1"> selected </s:if> >BCBSAL</option>
				<option value="2" <s:if test="rca.project_id==2"> selected </s:if> >BCBSMA</option>	
				<option value="3" <s:if test="rca.project_id==3"> selected </s:if> >BCBSMN</option>	
				<option value="4" <s:if test="rca.project_id==4"> selected </s:if> >BCBSNE</option>	
				<option value="5" <s:if test="rca.project_id==5"> selected </s:if> >BCBSTN</option>	
				<option value="6" <s:if test="rca.project_id==6"> selected </s:if> >Centene</option>	
				<option value="7" <s:if test="rca.project_id==7"> selected </s:if> >Federated</option>	
				<option value="8" <s:if test="rca.project_id==8"> selected </s:if> >HFHP</option>	
				<option value="9" <s:if test="rca.project_id==9"> selected </s:if> >HP</option>	
				<option value="10" <s:if test="rca.project_id==10"> selected </s:if> >HP-SG</option>
				<option value="11" <s:if test="rca.project_id==11"> selected </s:if> >IBC</option>	
				<option value="12" <s:if test="rca.project_id==12"> selected </s:if> >Kaiser</option>	
				<option value="13" <s:if test="rca.project_id==13"> selected </s:if> >MI-IFP</option>
				<option value="14" <s:if test="rca.project_id==14"> selected </s:if> >MI-SG</option>	
				<option value="15" <s:if test="rca.project_id==15"> selected </s:if> >Shopping</option>	
				<option value="16" <s:if test="rca.project_id==16"> selected </s:if> >SHP</option>	
				<option value="17" <s:if test="rca.project_id==17"> selected </s:if> >TN-PX</option>	
				<option value="18" <s:if test="rca.project_id==18"> selected </s:if> >Topaz</option>	
				<option value="19" <s:if test="rca.project_id==19"> selected </s:if> >WM</option>
				<option value="20" <s:if test="rca.project_id==20"> selected </s:if> >WPS</option>
				<option value="21" <s:if test="rca.project_id==21"> selected </s:if> >UCD</option>			
				<option value="22" <s:if test="rca.project_id==22"> selected </s:if> >UHG</option> -->
											
				</select></td> 
				<td colspan="2"></td>          
    			  
			</tr> <tr>
				<td style="float:right;"><label for="week">Week</label> </td>
				<td colspan="2"><select name="week" id="week_id"  style="width:120px;" onchange="disableSubmit();">	
				<s:if test="rca.week != null && !rca.week.equals('')">	
				<option value="<s:property value="rca.week" />"><s:property value="weekStr" /></option>				
				</s:if>	
							
				</select></td>
				<td ><input type="submit" value="Search" id="searchId" onclick="serchRcaData()"/></td>
				<!--<td ><input type="submit" value="export" id="export" onclick="exportData()"/></td> -->
				
			</tr> <tr>
			    <td style="padding-top: 40px;"></td>			   
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
				<td style="float:right;"><label for="configuration">Configuration</label></td>				
				<td><input type="text" value="<s:property value="rca.config_qa" />" name="config_qa" id="config_qa" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.config_uat" />" name="config_uat" id="config_uat" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.config_prod" />" name="config_prod" id="config_prod" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.config_product_backlog" />" name="config_product_backlog" id="config_product_backlog" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
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
			</tr> <tr>
				<td style="float:right;"><label for="integration_issue  ">Integration Issue</label></td>				
				<td><input type="text" value="<s:property value="rca.ii_qa" />" name="ii_qa" id="ii_qa" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.ii_uat" />" name="ii_uat" id="ii_uat" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.ii_prod" />" name="ii_prod" id="ii_prod" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.ii_product_backlog" />" name="ii_product_backlog" id="ii_product_backlog" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
			</tr> <tr>
				<td style="float:right;"><label for="data_issue  ">Data Issue</label></td>				
				<td><input type="text" value="<s:property value="rca.di_qa" />" name="di_qa" id="di_qa" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.di_uat" />" name="di_uat" id="di_uat" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.di_prod" />" name="di_prod" id="di_prod" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.di_product_backlog" />" name="di_product_backlog" id="di_product_backlog" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
			</tr>
			<tr>
			<td style="float:right;"><label for="data_issue">Re Open</label></td>				
				<td><input type="text" value="<s:property value="rca.ro_qa" />" name="ro_qa" id="ro_qa" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.ro_uat" />" name="ro_uat" id="ro_uat" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td><input type="text" value="<s:property value="rca.ro_prod" />" name="ro_prod" id="ro_prod" size="8" maxlength="4" onkeypress="return isNumberKey(event);" <s:if test="isdisabled==true"> disabled </s:if> /></td>
				<td></td>
			</tr>
			<tr> 		
				  <!-- <td colspan="5" style="padding-top:50px;"><input type="submit" value="Template Download" id="template" onclick="templateDownload()"/>
			&nbsp;&nbsp;&nbsp;&nbsp;<label for="data_issue  ">Select File</label> 
			<input  type="file" name="rcaFile" id="rcaFile"  /> 
				
			<input type="submit" value="Upload RCA" onclick="upload()" /> </td>
			 -->
				</tr> 
				
			  <tr align="center">
				<td colspan="5" style="padding-top:30px;">
				
				<s:if test="hasActionErrors()">
				   <div class="errors" style="color: red;">
				      <s:actionerror/>
				   </div>
				</s:if>
				<s:if test="hasActionMessages()">
				   <div class="success"  style="color: yellow;">
				      <b><s:actionmessage/></b>
				   </div>
				</s:if>				
				
				</td>
				<td colspan="3"></td>
				</tr>
				</table>
				
			</form>
		</div>
	</div>
</body>
</html>

