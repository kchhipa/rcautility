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
		<div id="content" style="min-height: 400px;">
		 <form method="post" name="RCA_Form" id="RCA_Form" onsubmit="return false" enctype="multipart/form-data" > 
		  <table cellspacing="80" class="content-table">
		  <%@ include file="leftMenu.jsp"%>
		  
		   <tr>
				<td colspan="2"><label for="week">Week: &nbsp;</label> 
					<select name="week" id="week_id"  style="width:120px;">	
					<s:if test="rca.week != null && !rca.week.equals('')">	
					<option value="<s:property value="rca.week" />"><s:property value="weekStr" /></option>				
					</s:if>	
					</select>
				</td>
	 		 </tr>
			 <tr>
					<td style="float:right;">
						<input type="submit" value="Submit" id="export" onclick="exportData()"/>
						<input type="submit" value="Cancel" onclick="homePage()"/>
					</td>
			</tr>
		  
		  </table>
				
			 </form> 
		</div>
	</div>
</body>
</html>

