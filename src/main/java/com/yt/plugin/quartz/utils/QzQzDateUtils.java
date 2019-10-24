package com.yt.plugin.quartz.utils;

import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class QzQzDateUtils implements QzDateFormator {

	private QzQzDateUtils() {

	}

	public static Date now() {
		return new GregorianCalendar().getTime();
	}

	public static Date now(String pattern) {
		Date date = QzQzDateUtils.now();
		String str = QzQzDateUtils.toString(date, pattern);
		return QzQzDateUtils.toDate(str, pattern);
	}

	public static Date toDate(String time, String pattern) {
		Assert.notNull(time);
		Assert.notNull(pattern);
		return QzCalendarUtils.toCalendar(time, pattern).getTime();
	}

	public static Date toDate(String time) {
		Assert.notNull(time);
		for (String key : defaultDateFormatMap.keySet()) {
			if (isDateFormat(time, key)) {
				return QzQzDateUtils.toDate(time, defaultDateFormatMap.get(key));
			}
		}
		throw new RuntimeException("just support format : "
				+ QzStringUtils.collectionToDelimitedString(defaultDateFormatMap.values(), ",") + " - " + time);
	}

	public static String toString(Date date, String pattern) {
		Assert.notNull(date);
		Assert.notNull(pattern);
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	public static String toString(Date date) {
		Assert.notNull(date);
		return toString(date, YEAR_MONTH_DAY_HH_MM_SS);
	}

	public static int compare(Date d1, Date d2) {
		Assert.notNull(d1);
		Assert.notNull(d2);

		Calendar c1 = new GregorianCalendar();
		Calendar c2 = new GregorianCalendar();
		c1.setTime(d1);
		c2.setTime(d2);

		return c1.compareTo(c2);
	}

	public static int compareDate(Date d1, Date d2) {
		Assert.notNull(d1);
		Assert.notNull(d2);

		d1 = QzQzDateUtils.toDate(QzQzDateUtils.toString(d1, QzQzDateUtils.YEAR_MONTH_DAY), QzQzDateUtils.YEAR_MONTH_DAY);
		d2 = QzQzDateUtils.toDate(QzQzDateUtils.toString(d2, QzQzDateUtils.YEAR_MONTH_DAY), QzQzDateUtils.YEAR_MONTH_DAY);
		Calendar c1 = new GregorianCalendar();
		Calendar c2 = new GregorianCalendar();
		c1.setTime(d1);
		c2.setTime(d2);

		return c1.compareTo(c2);
	}

	public static Date beginTimeOfMonth(int year, int month) {
		Calendar first = new GregorianCalendar(year, month - 1, 1, 0, 0, 0);
		return first.getTime();
	}

	public static Date endTimeOfMonth(int year, int month) {
		Calendar first = new GregorianCalendar(year, month, 1, 0, 0, 0);
		first.add(Calendar.MILLISECOND, -1);
		return first.getTime();
	}

	public static Date preDays(Date date, int preDays) {
		GregorianCalendar c1 = new GregorianCalendar();
		c1.setTime(date);
		GregorianCalendar cloneCalendar = (GregorianCalendar) c1.clone();
		cloneCalendar.add(Calendar.DATE, -preDays);
		return cloneCalendar.getTime();
	}

	public static Date nextDays(Date date, int nextDays) {
		GregorianCalendar c1 = new GregorianCalendar();
		c1.setTime(date);
		GregorianCalendar cloneCalendar = (GregorianCalendar) c1.clone();
		cloneCalendar.add(Calendar.DATE, nextDays);
		return cloneCalendar.getTime();
	}

	public static Date nextYears(Date date, int nextYear) {
		GregorianCalendar c1 = new GregorianCalendar();
		c1.setTime(date);
		GregorianCalendar cloneCalendar = (GregorianCalendar) c1.clone();
		cloneCalendar.add(Calendar.YEAR, nextYear);
		return cloneCalendar.getTime();
	}

	public static Date nextMonths(Date date, int nextMonth) {
		GregorianCalendar c1 = new GregorianCalendar();
		c1.setTime(date);
		GregorianCalendar cloneCalendar = (GregorianCalendar) c1.clone();
		cloneCalendar.add(Calendar.MONTH, nextMonth);
		return cloneCalendar.getTime();
	}

    public static Date nextMinute(Date date, int nextMinute) {
        GregorianCalendar c1 = new GregorianCalendar();
        c1.setTime(date);
        GregorianCalendar cloneCalendar = (GregorianCalendar) c1.clone();
        cloneCalendar.add(Calendar.MINUTE, nextMinute);
        return cloneCalendar.getTime();
    }

	public static Date preYears(Date date, int nextYear) {
		GregorianCalendar c1 = new GregorianCalendar();
		c1.setTime(date);
		GregorianCalendar cloneCalendar = (GregorianCalendar) c1.clone();
		cloneCalendar.add(Calendar.YEAR, -nextYear);
		return cloneCalendar.getTime();
	}

    public static Date preMinute(Date date, int nextMinute) {
        GregorianCalendar c1 = new GregorianCalendar();
        c1.setTime(date);
        GregorianCalendar cloneCalendar = (GregorianCalendar) c1.clone();
        cloneCalendar.add(Calendar.MINUTE, -nextMinute);
        return cloneCalendar.getTime();
    }

	public static Date preMonths(Date date, int preMonth) {
		GregorianCalendar c1 = new GregorianCalendar();
		c1.setTime(date);
		GregorianCalendar cloneCalendar = (GregorianCalendar) c1.clone();
		cloneCalendar.add(Calendar.MONTH, -preMonth);
		return cloneCalendar.getTime();
	}

	public static long getDiffMillis(Date d1, Date d2) {
		Assert.notNull(d1);
		Assert.notNull(d2);
		long diff = d1.getTime() - d2.getTime();
		return diff;
	}

	public static int yearDiff(Date beginDate, Date endDate) {
		Assert.notNull(beginDate);
		Assert.notNull(endDate);
		int year = 0;
		while (compareDate(endDate, beginDate) > 0) {
			year++;
			beginDate = nextYears(beginDate, 1);
		}
		return year;
	}

	public static int getDiffYear(Date beginDate, Date endDate) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(beginDate);
		c2.setTime(endDate);
		int year1 = c1.get(Calendar.YEAR);
		int year2 = c2.get(Calendar.YEAR);
		return Math.abs(year2 - year1);
	}

	/**
	 * 返回两个日期之间相差的月数(不足1月以1月记)
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int monthDiff(Date beginDate, Date endDate) {
		Assert.notNull(beginDate);
		Assert.notNull(endDate);
		int month = 0;
		while (compareDate(endDate, beginDate) > 0) {
			month++;
			beginDate = nextMonths(beginDate, 1);
		}
		return month;
	}

	public static long dayDiff(Date d1, Date d2) {
		Assert.notNull(d1);
		Assert.notNull(d2);
		Calendar c1 = new GregorianCalendar();
		Calendar c2 = new GregorianCalendar();
		c1.setTime(d1);
		c2.setTime(d2);
		long diffDays = QzCalendarUtils.getDiffDays(c1, c2);
		return diffDays;
	}

	public static String getDiffs(Date d1, Date d2) {
		long diffMillis = QzQzDateUtils.getDiffMillis(d1, d2);
		long diffHours = diffMillis / (60L * 60L * 1000L);
		long diffMinutes = diffMillis / (60L * 1000L) % 60;
		long diffSeconds = (diffMillis / 1000L) % 60;
		diffHours = Math.abs(diffHours);
		diffMinutes = Math.abs(diffMinutes);
		diffSeconds = Math.abs(diffSeconds);
		StringBuffer temp = new StringBuffer();
		temp.append(diffHours < 10 ? "0" + diffHours : diffHours);
		temp.append(":");
		temp.append(diffMinutes < 10 ? "0" + diffMinutes : diffMinutes);
		temp.append(":");
		temp.append(diffSeconds < 10 ? "0" + diffSeconds : diffSeconds);
		return temp.toString();
	}

	public static boolean isDateFormat(String date) {
		Assert.notNull(date);
		for (String key : defaultDateFormatMap.keySet()) {
			if (isDateFormat(date, key)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isDateFormat(String date, String format) {
		Assert.notNull(date);
		return QzStringUtils.isDefinedPattern(date, format);
	}

	public static Date getNowDate() {
		Date now = new Date();
		return toDate(toString(now, YEAR_MONTH_DAY), YEAR_MONTH_DAY);
	}

	public static int getYear(Date d) {
		Assert.notNull(d);
		String dateStr = QzQzDateUtils.toString(d, QzQzDateUtils.YEAR_MONTH); // yyyy-MM
		return Integer.parseInt(dateStr.split(QzQzDateUtils.SPLIT_CHAR)[0]);
	}

	public static int getMonth(Date d) {
		Assert.notNull(d);
		String dateStr = QzQzDateUtils.toString(d, QzQzDateUtils.YEAR_MONTH); // yyyy-MM
		return Integer.parseInt(dateStr.split(QzQzDateUtils.SPLIT_CHAR)[1]);
	}

	public static int getDay(Date d) {
		Assert.notNull(d);
		String dateStr = QzQzDateUtils.toString(d, QzQzDateUtils.YEAR_MONTH_DAY); // yyyy-MM-dd
		return Integer.parseInt(dateStr.split(QzQzDateUtils.SPLIT_CHAR)[2]);
	}

	private static Map<String, String> defaultDateFormatMap = new HashMap<String, String>();

	static {
		defaultDateFormatMap.put("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}", QzQzDateUtils.YEAR_MONTH_DAY);
		defaultDateFormatMap.put("[0-9]{4}/[0-9]{1,2}/[0-9]{1,2}", "yyyy/MM/dd");
		defaultDateFormatMap.put("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}",
				QzQzDateUtils.YEAR_MONTH_DAY_HH_MM_SS);
		defaultDateFormatMap.put("[0-9]{4}/[0-9]{1,2}/[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}",
				"yyyy/MM/dd HH:mm:ss");
		defaultDateFormatMap.put("[0-9]{4}-[0-9]{1,2}", QzQzDateUtils.YEAR_MONTH);
		defaultDateFormatMap.put("[0-9]{4}/[0-9]{1,2}", "yyyy/MM");
	}

	public static boolean isSameYearMonth(Date d1, Date d2) {
		if (null == d1 || null == d2)
			return false;
		GregorianCalendar g1 = new GregorianCalendar();
		g1.setTime(d1);
		GregorianCalendar cloneCalendar = (GregorianCalendar) g1.clone();
		cloneCalendar.setTime(d2);
		if (g1.get(Calendar.YEAR) != cloneCalendar.get(Calendar.YEAR)) {
			return false;
		}
		if (g1.get(Calendar.MONTH) != cloneCalendar.get(Calendar.MONTH)) {
			return false;
		}
		return true;
	}

	public static int getWeekDay(Date d) {
		Assert.notNull(d);
		Calendar c = new GregorianCalendar();
		c.setTime(d);
		int weekDay = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (weekDay == 0)
			weekDay = 7;
		return weekDay;
	}

	/**
	 * 返回此日期的零点整，如2007-3-14 19:00:35 返回 2007-3-14 00:00:00
	 *
	 * @param date
	 */
	public static Date getTheDayZero(Date date) {
		Calendar result = new GregorianCalendar();
		result.setTime(date);
		result.set(Calendar.HOUR_OF_DAY, 0);
		result.set(Calendar.MINUTE, 0);
		result.set(Calendar.SECOND, 0);
		result.set(Calendar.MILLISECOND, 0);
		return result.getTime();
	}

	/**
	 * 返回此日期的午夜，如2007-3-14 19:00:35 返回 2007-3-14 23:59:59
	 *
	 * @param date
	 */
	public static Date getTheDayMidnight(Date date) {
		Calendar result = new GregorianCalendar();
		result.setTime(date);
		result.set(Calendar.HOUR_OF_DAY, 23);
		result.set(Calendar.MINUTE, 59);
		result.set(Calendar.SECOND, 59);
		result.set(Calendar.MILLISECOND, 999);
		return result.getTime();
	}

	/**
	 * 获取一个时间段的String集合(数据eg:2015-12,2015-11)
	 * 
	 * @param preMonth      起始时间往前推多少个月(若为负值则往后推)
	 * @param preMonthCount 往前推多少个月,决定返回结果的条数
	 * @param pattern       返回值List中每个对象的日期格式
	 * @return
	 */
	public static List<String> getPreMonthList(int preMonth, int preMonthCount, String pattern) {
		Assert.notNull(pattern);
		List<String> monthList = new ArrayList<String>();
		Date now = preMonths(now(), preMonth);
		Date preDate = preMonths(now, 12);
		while (compare(now, preDate) > 0) {
			monthList.add(toString(now, pattern));
			now = preMonths(now, 1);
		}
		return monthList;
	}

	/**
	 * 获取一个时间段的String集合(数据eg:2015-12,2015-11)
	 * 
	 * @param day           取值范围[1-28] 若当前时间为2015-02-19,day传值大于19则结果中包含2015-02,否则不包含
	 * @param preMonth      起始时间往前推多少个月(若为负值则往后推)
	 * @param preMonthCount 往前推多少个月,决定返回结果的条数
	 * @param pattern       返回值List中每个对象的日期格式
	 * @return
	 */
	public static List<String> getPreMonthList(int day, int preMonth, int preMonthCount, String pattern) {
		Assert.notNull(pattern);
		if (day < 1) {
			throw new RuntimeException("The value of the parameter [day] must greater than 0.");
		}
		List<String> monthList = new ArrayList<String>();
		Date now = QzQzDateUtils.now(YEAR_MONTH_DAY);
		// 获取当月最后一天
		Date date = endTimeOfMonth(getYear(now), getMonth(now));
		int tempDay = getDay(date);
		// 如果当月最后一天比参数day还小,则取当月最后一天赋值给day去计算日期.
		if (getDay(date) < day)
			day = tempDay;
		// 获取当前月份+day组合成日期
		String dateStr = toString(now, YEAR_MONTH);
		dateStr += "-" + day;
		Date tempDate = toDate(dateStr, YEAR_MONTH_DAY);
		// 若组合的日期大于等于当前日期,则返回结果不包含当月-preMonth+1得到的月份
		if (compare(now, tempDate) <= 0) {
			now = preMonths(now(), preMonth);
		} else {
			now = preMonths(now(), preMonth - 1);
		}

		Date preDate = preMonths(now, preMonthCount);
		while (compare(now, preDate) > 0) {
			monthList.add(toString(now, pattern));
			now = preMonths(now, 1);
		}
		return monthList;
	}

	/**
	 * 获取两个时间相差的秒/分钟/小时
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static String getFormatDiffTime(Date beginDate, Date endDate) {
		// 获取间隔的秒
		long temp = QzQzDateUtils.getDiffMillis(endDate, beginDate) / 1000;
		if (temp < 0)
			temp = temp * (-1);// 容错
		// 使用中间变量,保持初始变量用于后面计算.
		long time = temp;
		// 计算秒
		if (time < 60) {// 小于60秒直接返回单位为秒
			return time + "秒";
		}
		// 计算分钟
		time = time / 60;// 转换成间隔的分钟
		if (time < 60) {// 小于60分钟直接返回单位为分钟
			long tempTime = temp - (time * 60);
			return time + "分钟" + tempTime + "秒";
		}
		// 计算小时
		time = time / 60;// 转换成间隔的小时
		long tempTime = (temp - time * 60 * 60) / 60;

		return time + "小时" + tempTime + "分钟";
	}

	/**
	 * 获取当年的第一天
	 *
	 * @return
	 */
	public static Date getCurrYearFirst() {
		Calendar currCal = Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearFirst(currentYear);
	}

	/**
	 * 获取当年的最后一天
	 *
	 * @return
	 */
	public static Date getCurrYearLast() {
		Calendar currCal = Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearLast(currentYear);
	}

	/**
	 * 获取某年第一天日期
	 * 
	 * @param year 年份
	 * @return Date
	 */
	public static Date getYearFirst(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}

	/**
	 * 获取某年最后一天日期
	 * 
	 * @param year 年份
	 * @return Date
	 */
	public static Date getYearLast(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();

		return currYearLast;
	}

	// String httpUrl = "http://apis.baidu.com/xiaogg/holiday/holiday";
	// String httpArg = "d=20151001";
	// String jsonResult = request(httpUrl, httpArg);
	// System.out.println(jsonResult);
	/**
	 * @see //http://apistore.baidu.com/apiworks/servicedetail/1116.html
	 * 
	 * @param httpUrl  :请求接口
	 * @param httpArg :参数
	 * @return 返回结果
	 */
	public static String getHolidayFromBaidu(String httpUrl, String httpArg, String apikey) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + "?" + httpArg;

		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			// 填入apikey到HTTP header
			connection.setRequestProperty("apikey", apikey);
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	public static int getDaysOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 两个日期相差多少个月，
	 * 
	 * @param startDate 小
	 * @param endDate   大
	 * @return
	 */
	public static int calDiffMonth(String startDate, String endDate) {
		int result = 0;
		try {
			SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd");
			Date start = sfd.parse(startDate);
			Date end = sfd.parse(endDate);
			int startYear = getYear(start);
			int startMonth = getMonth(start);
			int startDay = getDay(start);
			int endYear = getYear(end);
			int endMonth = getMonth(end);
			int endDay = getDay(end);
			if (startDay > endDay) { // 1月17 大于 2月28
				if (endDay == getDaysOfMonth(getYear(new Date()), 2)) { // 也满足一月
					result = (endYear - startYear) * 12 + endMonth - startMonth;
				} else {
					result = (endYear - startYear) * 12 + endMonth - startMonth - 1;
				}
			} else {
				result = (endYear - startYear) * 12 + endMonth - startMonth;
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	/*
	 * 本周一0点时间
	 */
	public static Date getTimeMonday() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal.getTime();
	}
	
	/*
	 * 本周日0点时间
	 */
	public static Date getTimeSunday() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.add(Calendar.DAY_OF_WEEK, 6);
		return cal.getTime();
	}
	
	/*
	 * 本月第一天0点时间
	 */
	public static Date getTimeMonthFirst() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), 1, 0, 0, 0);
		return cal.getTime();
	}
	
	/*
	 * 本月最后一天0点时间
	 */
	public static Date getTimeMonthLast() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY)+1, 1, 0, 0, 0);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}
	
	/*
	 * 本年第一天0点时间
	 */
	public static Date getTimeYearFirst() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), 0, 1, 0, 0, 0);
		return cal.getTime();
	}
	
	/*
	 * 本年最后一天0点时间
	 */
	public static Date getTimeYearLast() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR)-1, 11, 31, 0, 0, 0);
		return cal.getTime();
	}
	public static Date getFirstMonthByTime(String time){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(QzQzDateUtils.toDate(time));
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}


	public static void main(String[] args) {
		Date date = QzQzDateUtils.nextMinute(QzQzDateUtils.now(),1);
		System.out.println(toString(date,YEAR_MONTH_DAY_HH_MM_SS));

	}




}
