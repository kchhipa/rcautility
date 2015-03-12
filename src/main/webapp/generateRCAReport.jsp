 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>RCA Report</title>
<script type="text/javascript">
   function submitForm()
   {
	   if(valideateProjectAndWeek())
		   {
		   	document.RCA_Form.action="generateReport";
		    document.RCA_Form.submit();
		   }
	    
   }
</script>
</head>
<body >
     <div id="main">
        <div id="header" style="width: 100%; height:60px; background-color: #34495e; text-align: center; color: #fff;">
          <h1> RCA<br><span style="font-size: 16px; color:yellow;">Please submit the data for your project before 2 PM on every Monday.  <a href="logout" style="color:white; padding-left:30px;">Logout</a></span></h1>
        </div>	
		<div id="content"  style="background-color: #369044; width:100%; height:100%;">
		<s:form action="generateGraphPpt" namespace="/">
		  <h1>Generate PPT Report</h1>

		<!-- <input type="submit" value="Generate PPT" id="submitRcaId" onclick="submitForm()"/> -->
		<s:submit value="Generate PPT" name="submit" />
		</s:form>
		</div>
	</div>
</body>
</html>

