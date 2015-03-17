package com.rca.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> weeks = new ArrayList<String>();
		SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");

		Calendar c1 = Calendar.getInstance();
		// c1.add(Calendar.MONTH, -3);
		c1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		c1.add(Calendar.WEEK_OF_MONTH, -1);
		for (int i = 0; i < 13; i++) {
			// System.out.print("Start Date : " + formatter.format(c1.getTime())
			// + ", ");
			String startDate = formatter.format(c1.getTime());
			c1.add(Calendar.DAY_OF_WEEK, +6);
			// System.out.println("End Date : " +
			// formatter.format(c1.getTime()));

			String endDate = formatter.format(c1.getTime());
			String finalRange = startDate + "-" + endDate;
			// System.out.println("Date range :" + startDate +"-"+endDate);
			weeks.add(finalRange);
			c1.add(Calendar.DAY_OF_WEEK, -14);
			// System.out.println("End Date : " + c1.getTime());
			c1.add(Calendar.DAY_OF_WEEK, 1);
		}

	}
	
	public List<String> findWeeks(){
		List<String> weeks = new ArrayList<String>();
		SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");

		Calendar c1 = Calendar.getInstance();
		// c1.add(Calendar.MONTH, -3);
		c1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		c1.add(Calendar.WEEK_OF_MONTH, -1);
		for (int i = 0; i < 12; i++) {
			// System.out.print("Start Date : " + formatter.format(c1.getTime())
			// + ", ");
			String startDate = formatter.format(c1.getTime());
			c1.add(Calendar.DAY_OF_WEEK, +6);
			// System.out.println("End Date : " +
			// formatter.format(c1.getTime()));

			String endDate = formatter.format(c1.getTime());
			String finalRange = startDate + "-" + endDate;
			// System.out.println("Date range :" + startDate +"-"+endDate);
			weeks.add(finalRange);
			c1.add(Calendar.DAY_OF_WEEK, -14);
			// System.out.println("End Date : " + c1.getTime());
			c1.add(Calendar.DAY_OF_WEEK, 1);
		}

		return weeks;
	}

}
