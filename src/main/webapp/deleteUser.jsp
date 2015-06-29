 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Delete user</title>
<link href="css/style.css" rel="stylesheet"/>
<script type="text/javascript">
function deleteUser()
{
	var userId = document.getElementById("userName").value;
 	if(userId=="" || userId==null)
    	{
    	alert("User Id can not be blank");
    	return false;
    	}
 	else if(userId.indexOf("IIIPL-"))
 		{
 			alert("User Id must contain IIIPL-");
 			return false;
 		}
 	if(confirm("Are you sure you want to delete"))
 		{
		document.RCA_Form.action = "deleteUser";
		document.RCA_Form.submit();
		}
 	return false;
}
</script>
</head>
<body>
      <div id="main">
		<%@ include file="common.jsp"%>
		<div id="content">
			<form method="post" name="RCA_Form" id="RCA_Form">		
			  <div style="float:left;">				
					  <%@ include file="leftMenu.jsp"%>				   
			  </div>
			  <div style="margin-left:280px;">
				 <table cellspacing="30" class="content-table">
		
		  <tr>
			  <td colspan="2"><h1>Delete User</h1> </td>
		  </tr>
		 <tr></tr>
		  <tr>
				<td>
					<label style="margin-top:50px; margin-right:50px;">User ID : </label>
					<input type="text"  name="userName" id="userName" placeholder="IIIPL-0000" onblur="changeToUpperCase()" /></br></br>
					<label style="color:blue; font-size:11px; margin-left:140px;"/>Please use employee id as your user Id </label>	
				</td>
		  </tr>
		  <tr></tr>
		

		  <tr></tr>
		 	<tr>
				<td colspan=3>
				<input type="submit" value="Delete" onClick="return deleteUser()" /> 
				<input type="button" value="Reset" onClick="this.form.reset();"/> 
				</td>
				</tr>
				
				 <tr>
				 <s:if test="hasActionMessages()">
			      <td style="color: yellow;" colspan=3>		    								
				      <s:actionmessage/>				  					
			      </td>
			      </s:if>	
			      <s:if test="hasActionErrors()">
				   <td style="color: red;" colspan=3>		 
				      <s:actionerror/>
				   </td>
				</s:if>
			    </tr>
	  </table>
					
				</div> 
		   </form>	
		</div>
	</div>
</body>
</html>