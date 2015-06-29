 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Manage Users</title>
<link href="css/style.css" rel="stylesheet"/>
</head>
<body>
     <div id="main">
		<%@ include file="common.jsp"%>
		<div id="content">
			<form method="post" name="RCA_Form" id="RCA_Form">		
			  <div style="float:left; margin-top:-80px;">				
					  <%@ include file="leftMenu.jsp"%>				   
			  </div>
			  <div style="margin-left:280px;">
				<p style="align: center; margin-top:70px; font-size: 20px;">
					<a href="addUserView" >Add new user</a><br><br>
					<a href="assignProjectView" >Assign project to existing user</a><br><br>
					<a href="deleteUserView" >Delete user</a><br><br>
					<a href="deleteProjectFromUserView" >Delete project from existing user</a>
				</p>	
				</div> 
		   </form>	
		</div>
	</div>
</body>
</html>