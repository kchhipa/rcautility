<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>RCA Login</title>
<script type="text/javascript">

 function submitForm()
 {
	 if(validateCredential())
	   {
	   	document.RCA_login.action="home";
	    document.RCA_login.submit();
	   }
  
 }
function validateCredential()
{

	   var userName = document.getElementById("userNameId").value;
	   var passWord = document.getElementById("passwordId").value;
	    if(userName=="" || userName==null)
	    	{
	    	alert("username can not be blank");
	    	return false;
	    	}	    
	    else if(passWord=="" || passWord==null)
	    	{
	    	alert("password can not be blank");
	    	return false;
	    	}
	    return true;
}
</script>

</head>
<body>
     <div id="main">
        <div id="header" style="width: 100%; height:60px; background-color: #34495e; text-align: center; color: #fff;">
          <h1> RCA<br></h1>
        </div>	
		<div id="content"  style="background-color: #3483c5; height:600px;">
			<div id="loginDiv" style="margin-left:40%; font-family:verdana; margin-top:100px; padding-left:70px; width:370px; height:210px; position:absolute; background-color: #95b31d;">
			<form method="post" name="RCA_login" id="RCA_login">
			<table>
			<tr>
			   <td style="padding-top: 30px;"><label for="userName">Username</label>
			   <input type="text" id= "userNameId" name="userName" style="margin-left:10px;"/></td>
			   </tr><tr>
			    <td style="padding-top: 20px;"><label for="passWord">Password</label>&nbsp;
			    <input type="password" id="passwordId" name="passWord" style="margin-left:10px;" /></td>
			    </tr><tr>
			      <td align="center" style="padding-top:20px; padding-left:40px;"> <button value="Submit" onclick="submitForm();">Submit</button> </td>
			    </tr>
			    <tr>
			      <td style="color: red;">		    
				<s:if test="hasActionMessages()">				
				      <s:actionmessage/>				  
				</s:if>		
			      </td>
			    </tr>
			    </table>
				</form>
			</div>
		</div>
	</div>
</body>
</html>