
<!-- Include require JavaScript and initialize menu -->
		<script src="prototype.js" type="text/javascript"></script>
		<script src="Menu.js" type="text/javascript"></script>
		<script type="text/javascript">
			Menu.init("menu");
		</script>

		<!-- Simple style sheet for horizontal menu -->

<script type="text/javascript">
function templateDownload()
{
	document.RCA_Form.action="templateDownload";
	document.RCA_Form.submit(); 
}

function generateReport()
{
	document.RCA_Form.action="rcaReportView";
	document.RCA_Form.submit(); 
}
function addProject()
{
	document.RCA_Form.action="viewAddProject";
	document.RCA_Form.submit(); 
}
function manageProject()
{
	document.RCA_Form.action="showProject";
	document.RCA_Form.submit(); 
}
function importData()
{
	document.RCA_Form.action="importData";
	document.RCA_Form.submit(); 
}
function displayRcaPage()
{
	document.RCA_Form.action="getRcaDetail";
	document.RCA_Form.submit(); 
}
/* function generateReportForSprint()
{
	document.RCA_Form.action="sprintReportView";
	document.RCA_Form.submit(); 
} */
function generateSummary()
{
	document.RCA_Form.action="generateSummarySlide";
	document.RCA_Form.submit(); 
}

</script>
     <div class="left-box">
		  <ul id="menu">
		  
		    <li><a href="javascript:displayRcaPage()">Enter Last Week Data </a></li>
	    	<li><a href="javascript:importData();">Import Last Week Data</a></li>
			<li><a href="javascript:templateDownload();">Download Template</a></li>
			<li><a href="sprintReportView" >Enter Sprint Data</a></li>
			  <s:if test="#session.role != null && #session.role.equals('manager')">
				   <li><a href="exportWeeklyData.jsp">Export Weekly Data</a></li>
				   <li><a href="javascript:generateReport()">Generate Reports</a></li>	
			       <li><a href="javascript:manageProject()">Manage Project</a> </li>
			          <li><a href="manageUsers">Manage User</a> </li>
			         <!--  <li><a href="projectWithTeam">Update Team Name</a></li> -->
			  </s:if>
			 <li><a href="javascript:generateSummary();" > Generate Summary Slide</a></li> 
			 <li><a href="updateTeamNames" >Update Team Name</a></li>
		  </ul>
	</div>


