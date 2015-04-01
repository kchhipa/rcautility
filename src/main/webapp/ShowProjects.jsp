 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>RCA</title>
<link href="css/style.css" rel="stylesheet"/>
<style type="text/css" media="screen">
TD { 
 margin: 0;
 padding: 4px;
 border-width: 1px 1px 0 1px;
 border-style: solid dotted; 
}
</style>
<script type="text/javascript">
function addProject()
{
	document.RCA_Form.action="viewAddProject";
	document.RCA_Form.submit(); 
}
</script>
</head>
<body>
     <div id="main">
		<%@ include file="common.jsp"%>
		<div style="border:2px;width:510;" id="content">
		 <form method="post" name="RCA_Form" id="RCA_Form" onsubmit="return false" enctype="multipart/form-data" > 
		 <table cellspacing="12" class="content-table">
	
    <tr>
        <th><u>Project Name</u></th>
		
        <th><u>Status</u></th>
  
    </tr>
    <s:iterator  value ="rca.projectDetailList">
        <tr>
			<td><s:property value="projectName"/></td>
			
            <td><s:property value="projectStatus"/></td>
        </tr>
	</s:iterator>
		
		
		
	</table>
	<div>
			<p style="text-align: center;"><input type="submit" value="Add Project" id="addProjectId"  width="48" height="48" onclick="addProject()"/></p>	
	</div>
			 </form> 
		</div>
	</div>
</body>
</html>