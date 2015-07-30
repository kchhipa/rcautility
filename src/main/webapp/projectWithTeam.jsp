<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Project With Team</title>
<link href="css/style.css" rel="stylesheet"/>

<script type="text/javascript">

function updateTeamName(projectId)
		{
			var updatedActionTeam = document.getElementById(projectId).value;
			document.getElementById("projectId").value = projectId;
			document.getElementById("actionTeam").value = updatedActionTeam;			
			document.RCA_Form.action = "updateTeamName";
			document.RCA_Form.submit();
		}
		
</script>
</head>
<body>

 <div id="main">
		<%@ include file="common.jsp"%>
		<div style="border:2px;width:510; padding-bottom: 30px;" id="content">
		 <form method="post" name="RCA_Form" id="RCA_Form" onsubmit="return false" enctype="multipart/form-data" > 
		  <%@ include file="leftMenu.jsp"%>
		  
		  	<input type = "hidden" id="actionTeam" name="actionTeam" />
		    <input type = "hidden" id="projectId" name="projectId" />
		  </form> 
		  <div style="margin-left: 250px;"><br>
		    <s:if test="hasActionMessages()">
			      <span style="color: yellow; font-size:20px; ">		    								
				      <s:actionmessage/>				  					
			      </span>
			      </s:if>	
			      <s:if test="hasActionErrors()">
				   <span style="color: red; font-size:20px; ">		 
				      <s:actionerror/>
				   </span>
				</s:if>
			   
		 <table width="90%" class="project-table" style="margin-left: 230; margin-top: 30px; margin-bottom: 50px;">
		  	
    <tr >
        <th height="40px;">Project Name</th>
		
        <th>Team Name</th>
  
    </tr>
    <s:iterator  value ="projectDetailsList">
        <tr>
			<td><s:property value="projectName"/></td>
			
            <td ><input type="text" id="<s:property value="projectId"/>" size="40px;" value="<s:property value="actionTeam"/>"/> </td>
            
            <td >
               <input type="submit" value="Update" id="addProjectId"  width="48" height="48" onclick="updateTeamName('<s:property value="projectId"/>')"/>   
            </td>
        </tr>
	</s:iterator>
	
	</table>	
	</div>
		
		</div>
	</div>
</body>
</html>