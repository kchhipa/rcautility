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

function disableSubmit()
{
	   var todaysDate = new Date();
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

function submitReset()
{
	   var elementIdArray = getElementIdArray();
	   for(var i=0;i<elementIdArray.length;i++)
		   {
		  	 elementIdArray[i].value="";
		   }
	   document.getElementById("tuesdayError").disabled=false;
}

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

</script>
</head>
<body onload="calculateWeek()">
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
				</tr>
				<tr>
				<td style="float:left;"><label for="week">Week:</label> </td>
				<td colspan="2"><select name="week" id="week_id"  style="width:120px;" onchange="disableSubmit();">	
				<s:if test="rca.week != null && !rca.week.equals('') && !rca.week.equals('Select Week')">	
				<option value="<s:property value="rca.week" />"><s:property value="weekStr" /></option>				
				</s:if>	

				</select>
				</td>
				</tr>
				
				<tr>
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
		<div id="tuesdayError" class="errors" style="color: red;"> </div>
	</div>
</body>
</html>

