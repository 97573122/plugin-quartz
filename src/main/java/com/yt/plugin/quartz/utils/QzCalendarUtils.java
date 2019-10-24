package com.yt.plugin.quartz.utils;

import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static java.util.Calendar.*;


public class QzCalendarUtils implements QzDateFormator {

	private QzCalendarUtils() {

	}

	/**
	 * get the time string of current time
	 * 
	 * @param pattern
	 * @return
	 */
	public static String now(String pattern) {

		Assert.notNull(pattern);

		GregorianCalendar gerCalendar = new GregorianCalendar();
		Date date = gerCalendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * get default time String of current time,"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @return
	 */
	public static String now() {
		return QzCalendarUtils.now(YEAR_MONTH_DAY_HH_MM_SS);
	}

	/**
	 * 
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T now(Class<T> clazz) {

		Assert.notNull(clazz);

		if (GregorianCalendar.class.equals(clazz)) {
			return (T) new GregorianCalendar();
		} else if (Calendar.class.equals(clazz)) {
			return (T) new GregorianCalendar();
		} else if (Date.class.equals(clazz)) {
			return (T) new GregorianCalendar().getTime();
		} else if (String.class.equals(clazz)) {
			return (T) QzCalendarUtils.now(YEAR_MONTH_DAY_HH_MM_SS);
		} else {
			throw new IllegalArgumentException(
					"argument must be in [java.util.Calendar,java.util.GregorianCalendar,java.util.Date,String]");
		}
	}

	public static GregorianCalendar toCalendar(String time, String pattern) {

		Assert.notNull(time);
		Assert.notNull(pattern);

		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = format.parse(time);
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			return calendar;
		} catch (ParseException e) {
			throw new RuntimeException(
					"[Pase Exception]: the time string doesn't match for pattern.");
		}
	}

	public static GregorianCalendar toCalendar(String time) {
		Assert.notNull(time);
		return toCalendar(time, YEAR_MONTH_DAY_HH_MM_SS);
	}

	public static long getDiffMillis(Calendar c1, Calendar c2) {
		Assert.notNull(c1);
		Assert.notNull(c2);

		long diff = c1.getTimeInMillis() - c2.getTimeInMillis();

		return diff;
	}

	public static long getDiffDays(Calendar c1, Calendar c2) {
		Assert.notNull(c1);
		Assert.notNull(c2);

		Calendar c1Copy = new GregorianCalendar(c1.get(YEAR), c1.get(MONTH), c1
				.get(DATE));
		Calendar c2Copy = new GregorianCalendar(c2.get(YEAR), c2.get(MONTH), c2
				.get(DATE));

		long diffMillis = getDiffMillis(c1Copy, c2Copy);

		long dayMills = 24L * 60L * 60L * 1000L;
		long diffDays = diffMillis / dayMills;

		return diffDays;
	}

	public static int compare(Calendar c1, Calendar c2) {
		Assert.notNull(c1);
		Assert.notNull(c2);

		return c1.compareTo(c2);
	}

	public static String toString(Calendar c, String pattern) {
		Assert.notNull(c);
		Assert.notNull(pattern);

		Date date = c.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	public static String toString(Calendar c) {
		Assert.notNull(c);
		return toString(c, YEAR_MONTH_DAY_HH_MM_SS);
	}

	public static GregorianCalendar calculator(Calendar c, int diffDays) {
		GregorianCalendar cloneCalendar = (GregorianCalendar) c.clone();
		cloneCalendar.add(Calendar.DATE, diffDays);
		return cloneCalendar;
	}

	public static GregorianCalendar calculator(int diffDays) {
		return calculator(new GregorianCalendar(), diffDays);
	}

	public static Calendar beginTimeOfMonth(int year, int month) {
		Calendar first = new GregorianCalendar(year, month - 1, 1, 0, 0, 0);
		return first;
	}

	public static Calendar endTimeOfMonth(int year, int month) {
		Calendar first = new GregorianCalendar(year, month, 1, 0, 0, 0);
		first.add(Calendar.MILLISECOND, -1);
		return first;
	}

	public static GregorianCalendar preDaysCalendar(int preDays) {
		return QzCalendarUtils.preDaysCalendar(new GregorianCalendar(), preDays);
	}

	public static GregorianCalendar preDaysCalendar(Calendar c1, int preDays) {
		GregorianCalendar cloneCalendar = (GregorianCalendar) c1.clone();
		cloneCalendar.add(Calendar.DATE, -preDays);
		return cloneCalendar;
	}

	public static GregorianCalendar parseToCalendar(String dateStringToParse) {
		return QzCalendarUtils.parseToCalendar(dateStringToParse,
				YEAR_MONTH_DAY_HH_MM_SS);
	}

	public static GregorianCalendar parseToCalendar(String dateStringToParse,
			String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = format.parse(dateStringToParse);
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			return calendar;
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(
					"[Pase Exception]: please check the input date string format.");
		}
	}

	public static GregorianCalendar getStartDate(String date) {
		int week = getWeekByDate(date);
		GregorianCalendar beginGC = QzCalendarUtils.parseToCalendar(date
				+ " 00:00:00");
		GregorianCalendar beginDate = QzCalendarUtils.preDaysCalendar(beginGC,
				week + 7 - 0);
		return beginDate;
	}

	public static GregorianCalendar getEndDate(String date) {
		int week = getWeekByDate(date);
		GregorianCalendar endGC = QzCalendarUtils.parseToCalendar(date
				+ " 23:59:59");
		GregorianCalendar endDate = QzCalendarUtils.preDaysCalendar(endGC,
				week + 6 - 6);
		return endDate;
	}

	public static int getWeekByDate(String date) {
		GregorianCalendar c = QzCalendarUtils.toCalendar(date,
				QzCalendarUtils.YEAR_MONTH_DAY);
		int weekDay = c.get(Calendar.DAY_OF_WEEK);
		// 因为通过c.get(Calendar.DAY_OF_WEEK)等到的周日为1，周六为6，所以需要转换一�?
		if (weekDay == 1) {
			weekDay = 7;
		} else {
			weekDay--;
		}
		return weekDay;
	}

	public static int getNowWeekDay() {
		GregorianCalendar c = new GregorianCalendar();
		int weekDay = c.get(Calendar.DAY_OF_WEEK);
		if (weekDay == 1) {
			weekDay = 7;
		} else {
			weekDay--;
		}
		return weekDay;
	}
	
	public static int getNowMonthDay() {
		GregorianCalendar c = new GregorianCalendar();
		int monthDay = c.get(Calendar.DAY_OF_MONTH);
		return monthDay;
	}
	
	public static int getNowMaxMonthDay() {
		GregorianCalendar c = new GregorianCalendar();
		int monthDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		return monthDay;
	}

	public static boolean juageDate(int i) {
		if (((i % 4 == 0) && (i % 100 != 0)) || (i % 4 == 0 && i % 400 == 0)) {
			return true;
		}
		return false;
	}

}
