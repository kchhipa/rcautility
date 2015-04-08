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

 function submitForm()
   {	 
   	  if(validateUser())
   	  {
		 document.RCA_Form.action="addUser";
		 document.RCA_Form.submit();
		 return true;
	 }	  
		return false;
   } 

function validateUser()
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
	 	 var password = document.getElementById("passWord").value;	 
	 	 if(password==null || password=="")
	 		 {
	 		   alert("Password can not be blank");
	 		   return false;
	 		 }
	 	 var confirmPassword = document.getElementById("confirmPassword").value;	 
	 	 if(confirmPassword==null || confirmPassword=="")
	 		 {
	 		   alert("Confirm Password can not be blank");
	 		   return false;
	 		 }
	 	 else if(confirmPassword != password)
	 		 {
	 		 	alert("Password and confirm password must be same");
	 		 	return false;
	 		 }
	 	 var role = document.getElementById("role").value;	 
	 	 if(role==null || role=="" || role== "-1")
	 		 {
	 		   alert("Please select role");
	 		   return false;
	 		 }
	    return true;
   }
   
function changeToUpperCase() {
	 var userId = document.getElementById("userName").value;
   	 document.getElementById("userName").value = userId.toUpperCase();
}

</script>
</head>
<body>
     <div id="main">
		<%@ include file="common.jsp"%>
		<div id="content">
		<form method="post" name="RCA_Form" id="RCA_Form" onsubmit="javascript:return submitForm();" >
		  <table cellspacing="30" class="content-table">
		   <%@ include file="leftMenu.jsp"%>
		  <tr>
			  <td colspan="2"><h1>Manage User</h1> </td>
		  </tr>
		 
		  <tr>
				<td>
					<s:textfield name="userName" label="User ID" id="userName" placeholder="IIIPL-0000" onblur="changeToUpperCase()" />
					<s:label cssStyle="color:blue; font-size:10px; padding-top:0px;" value="Please use employee id as your user Id" />	
				</td>
		  </tr><tr>
		  	<td>
		  		<s:password name="passWord" label="Password" id="passWord" />
		  		<s:label cssStyle="color:blue; font-size:10px; padding-top:0px;" value="Please use first name as your password" />
			</td>
		  </tr>
		  <tr>
		  	<td>
		  		<s:password name="confirmPassword" label="Confirm Password" id="confirmPassword" />		  		
			</td>
		  </tr>
		  <tr>
		  	<td>
		  		<s:select name="role" label="Select Role" id="role"  headerKey="-1" headerValue="Select Role" list="#{'lead':'lead', 'manager':'manager'}" />	
		
		  		 
			</td>
		  </tr>
		  <tr></tr>

		  <tr></tr>
		 	<tr>
				<td colspan=3>
				<input type="submit" value="Submit"/> 
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
				
			</form>
		</div>
	</div>
</body>
</html>

