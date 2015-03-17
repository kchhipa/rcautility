import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rca.common.ReportUtility;
import com.rca.controller.CalTest;
import com.rca.dao.ProjectDetailsDAO;
import com.rca.dao.RcaCountDAO;
import com.rca.entity.ProjectDetails;
import com.rca.entity.RcaCount;


public class TestDao {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ConfigurableApplicationContext cac = new ClassPathXmlApplicationContext("beans.xml");
		RcaCountDAO rcd = (RcaCountDAO) cac.getBean("rcaCountDAO");
		
		ProjectDetailsDAO pdd = (ProjectDetailsDAO) cac.getBean("projectDetailsDAO");
		
		rcd.getRCACounts();
		System.out.println("Now Fin by ID ");
		RcaCount rCount = rcd.findById(31);
		
		System.out.println("My Project " + rCount.getProjectDetails().getProjectName());
		rCount.getProjectDetails().getProjectName();
		
		System.out.println("Find RCA by given week period");
		
		
		//String projName = count.get(0).getProjectDetails().getProjectName();
		
		//System.out.println(projName);
		
		System.out.println("Find weekly RCA report by Project Id");

		rcd.findWeeklyRCAReportByProjectId("9/29/2014-10/5/2014", 1);
		
		ReportUtility ru = new ReportUtility();
		
		List<RcaCount> count = rcd.findRCAByWeekPeriod("2/23/2015-3/1/2015");
		
		Map<String, Map<String, Integer>> dataSets = ru.reportedQARCAForAllProjects(count);
		
		ru.findWeeks();
		rcd.findRCAReportForMultipleWeek(ru.findWeeks());
		List<Map<String, Integer>> week_count = ru.reportedQAAllWeeksGraphForAllProject(rcd.findRCAReportForMultipleWeek(ru.findWeeks()), ru.findWeeks());
		
		CalTest ct = new CalTest();
		List<String> weeks = ct.findWeeks();
		
		/*for (String week : weeks) {
			List<RcaCount> count = rcd.findRCAByWeekPeriod(week);
			ru.weeklyBugCountForAllProjectsInQA(count, week);
			
			
		}*/
		
		
		List<RcaCount> aa = rcd.findRCAReportForMultipleWeek(weeks);
		
		System.out.println("AA Size: " + aa.size());
		/*for (RcaCount rcaCount : aa) {
			System.out.println(rcaCount.getWeek());
		}
*/		ArrayList<String> week = new ArrayList<String>();
		week.add("9/29/2014-10/5/2014");
		
		List<RcaCount> bb = rcd.findRCAReportForMultipleWeek(week);
		
		
		
		Map<String, Map<String, Integer>> lastWeekData = ru.rcaCountForLastWeekForAllProjects(bb);
		
		
		
		
		System.out.println("Project Details by ID: ");
		
		ProjectDetails pd = pdd.findProjectDetailsByIdWithRcaCount(1);
		
		System.out.println(pd.getProjectName());
		
		
		Set<RcaCount> rr = pd.getRcaCounts();
		
		System.out.println(rr.size());
		
	}

}
