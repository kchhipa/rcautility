function calculateWeek()
{
	var d = new Date();
	var d = new Date("10/31/2016");
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
		var d2 = new Date("10/31/2016");
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
		   	document.RCA_Form.action="saveSprintReport";
		    document.RCA_Form.submit();
		   }
   }
   
  /* function serchRcaData()
   {
	   var result = valideateProjectAndWeek();
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
   }*/
   
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
	   //var role = "<%= (String) session.getAttribute("role")%>";
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
			   document.getElementById("resetId").disabled=true;
			   disableTextFields(true);
			   submitReset();
			   document.getElementById("tuesdayError").innerHTML = "You cannot fill RCA data after due date i.e. every Monday.";
		   }
	   else
		   {
			   document.getElementById("submitRcaId").disabled=false;
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
	   document.getElementById("tuesdayError").disabled=false;
   }