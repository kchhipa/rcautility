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

 function exportData()
   {
   	 if(validate()){
	 document.RCA_Form.action="generateGraphPpt";
	 document.RCA_Form.submit();
	 }	   
   }
 
 function exportDataForMonthlyReport() {
		var week = document.getElementById("week_id1").value;
		if (week == "Select Week" || week == "") {
			alert("Please select week");
			return false;
		} else {
			document.RCA_Form2.action = "exportData";
			document.RCA_Form2.submit();
		}
	}

 function validate()
   {
	   var week = document.getElementById("week_id").value;
	 	if(week=="Select Week" || week=="")
	    	{
	    	alert("Please select week");
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
  
  function Change(value){
		 if(value == 'Monthly'){
			 document.getElementById("monthlyDiv").style.display = "block";
			 document.getElementById("weeklyDiv").style.display = "none";
		 }else{
			 document.getElementById("monthlyDiv").style.display = "none";
			 document.getElementById("weeklyDiv").style.display = "block";
		 }
	}
 
  function calculateWeek(dateString, weekId) {
		var d = new Date();
		d.setMonth(d.getMonth() - 3);
		var dateStartFrom = new Date(dateString);
		if (d < dateStartFrom)
		  d = dateStartFrom;

		var weekday = ["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"];
		var n = weekday[d.getDay()];
		if (n == "Tuesday")
			d.setDate(d.getDate() - 1);
		if (n == "Wednesday")
			d.setDate(d.getDate() - 2);
		if (n == "Thursday")
			d.setDate(d.getDate() - 3);
		if (n == "Friday")
			d.setDate(d.getDate() - 4);
		if (n == "Saturday")
			d.setDate(d.getDate() - 5);
		if (n == "Sunday")
			d.setDate(d.getDate() - 6);

		var date2 = new Date();
		date2.setMonth(d.getMonth());
		date2.setFullYear(d.getFullYear());
		date2.setDate(d.getDate() + 6);

		var mondays = new Array();
		var sundays = new Array();
		var months = new Array();
		var years = new Array();

		var d2 = new Date();

		for ( var i = 0; d <= d2; i++) {
			mondays[i] = d.getDate();
			months[i] = d.getMonth() + 1;
			years[i] = d.getFullYear();
			d.setDate(d.getDate() + 7);
		}
		for ( var i = 0; date2 <= d2; i++) {
			sundays[i] = date2.getDate();
			date2.setDate(date2.getDate() + 7);
		}

		var x = document.getElementById(weekId);
		var weekValue = x.value;

		var option = document.createElement("option");

		option.text = "Select Week";
		option.value = "Select Week";
		x.add(option, x[0]);
		var dateArray = [];
		for ( var i = 0; i < mondays.length - 1; i++) {
			var option = document.createElement("option");
			var text = months[i] + "/" + mondays[i] + "-" + months[i + 1] + "/"
					+ sundays[i];
			var value = months[i] + "/" + mondays[i] + "/" + years[i] + "-"
					+ months[i + 1] + "/" + sundays[i] + "/" + years[i + 1];
			if (weekValue == value)
				continue;
			option.text = text;
			option.value = value;
			x.add(option, x[i + 1]);
			dateArray.push(value);
		}
	}

	function futureWeek() {
		var selectedDate = document.getElementById("week_id1").value;
		var firstWeek = selectedDate.split("-");
		var x = document.getElementById('week_id2');
		x.options.length = 0;
		calculateWeek(firstWeek[0], 'week_id2');
	}
	
	function interval(){
		var interval = []; 
		var selected = document.getElementById("week_id2").value;
		var options = document.getElementById("week_id2").options;
		for (var i=0; i<= options.length; i++){
			if(options[i].value == selected){
				break;
			}
			interval.push(options[i+1].value);
		}
		document.getElementById("weekInterval").value = interval ;
	}

</script>
</head>
<body onload="calculateWeek('09/22/2014','week_id1',null)">
     <div id="main">
		<%@ include file="common.jsp"%>
		<div id="content">
		<table cellspacing="30" class="content-table">
		<%@ include file="leftMenu.jsp"%>
		  <tr>
			  <td colspan="2"><h1>Generate Report </h1> </td>
		  </tr>
		  <tr>
		  	 <td>
			<s:radio name="rca.weekType"  label ="Report Type" list="{'Weekly','Monthly'}" onChange="Change(this.value)"/></td>
		  </tr>
		
		</table>
		
		
		<div id="weeklyDiv" style="display: block;"> 
		<form method="post" name="RCA_Form" id="RCA_Form" onsubmit="return false" enctype="multipart/form-data" >
		
		  <table cellspacing="30" class="content-table">

		   
		  <tr>
				<td colspan="2">
					<s:select name="rca.week" list="weeks" headerKey="Select Week" headerValue="Select Week" label="Week" id ="week_id" />			
				</td>
		  </tr>
		  <tr></tr>

		 	<tr>
				<td colspan=3>
				<input type="submit" value="Submit" id="export" onclick="exportData()"/> 
				<input type="submit" value="Cancel" onclick="homePage()"/> 
				</td>
		  </tr>	  
	  </table>
	  </form>
	  </div>
	  
	  <div id="monthlyDiv" style="display: none;">
	  
	  <form method="post" name="RCA_Form2" id="RCA_Form2" onsubmit="return false" enctype="multipart/form-data">
				<table cellspacing="80" class="content-table">
					<tr>
						<td colspan="2"><label for="week">Week: &nbsp;</label>
						 <select name="week" id="week_id1" style="width: 120px;" onchange="futureWeek()">
								<s:if test="rca.week != null && !rca.week.equals('')">
									<option value="<s:property value="rca.week" />">
										<s:property value="weekStr" />
									</option>
								</s:if>
						</select>
					  </td>
					</tr>
					<tr>
						<td colspan="2"><label for="week">Week: &nbsp;</label> 
						<select name="week2" id="week_id2" style="width: 120px;" onchange="interval()">
									<option value="<s:property value="rca.week" />">
										<s:property value="weekStr" />
									</option>
						</select>
						<input type="hidden" name="weekInterval" id="weekInterval"/>
						</td>
					</tr>
					<tr>
						<td style="float: right;">
						<input type="submit" value="Submit" id="export" onclick="exportDataForMonthlyReport()" /> 
						<input type="submit" value="Cancel" onclick="homePage()" /></td>
					</tr>

				</table>

			</form>
	  
	  </div>
				
			
		</div>
	</div>
</body>
</html>

