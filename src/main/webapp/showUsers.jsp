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
function addUser()
{	
	document.location="addUserView";
}
</script>
</head>
<body>
     <div id="main">
		<%@ include file="common.jsp"%>
		<div style="border:2px;width:510;" id="content">		
		 <table width="100%" class="project-table">
	
    <tr>
        <th><u>User Id</u></th>
		
        <th><u>User Name</u></th>
  
    </tr>
    <tr><td colspan="2" style="border:none"></td></tr>
    <s:iterator  value ="loginDetailsList">
        <tr>
			<td><s:property value="loginId"/></td>
			
            <td><s:property value="password"/></td>
            
           <%--  <td>
                     <s:if test="projectStatus.equals('active')">
                     <a href="changeProjectStatus?projectName=<s:property value="projectName" />&projectStatus=inactive">Inactivate</a>
                     </s:if>
                     <s:else>
                     <a href="changeProjectStatus?projectName=<s:property value="projectName" />&projectStatus=active">Activate&nbsp;&nbsp;&nbsp;</a>
                     </s:else>
            </td> --%>
        </tr>
	</s:iterator>
		
		
		
	</table>
	<div>
			<p style="text-align: center;"><input type="submit" value="Add User" id="addUserId"  width="48" height="48" onclick="addUser()"/></p>	
	</div>			 
		</div>
	</div>
</body>
</html>