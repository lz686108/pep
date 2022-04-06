package com.parseweb.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class TimeKit4DD {
	public static final String TIME_FORMAT_1 = "yyyyMMddHHmmss.SSS";

	public static final String TIME_FORMAT_2 = "yyyy-MM-dd HH:mm:ss";

	public static final String TIME_FORMAT_3 = "yyyyMMddHHmmss";

	/** 
	 * 专门为ERP质检数据增加的。
	 */
	public static final String TIME_FORMAT_4 = "yyyyMMdd HH:mm:ss";

	public static final String TIME_FORMAT_5 = "HH:mm";

	public static final String TIME_FORMAT_6 = "HH:mm:ss";

	public static final String TIME_FORMAT_7 = "mm:ss";

	public static final String TIME_FORMAT_8 = "yyyyMMddHH";

	public static final String TIME_FORMAT_9 = "yyyyMMdd";

	static {
		System.setProperty("user.timezone", "Asia/Shanghai");
		TimeZone e8 = TimeZone.getTimeZone("GMT+8");
		TimeZone.setDefault(e8);
	}

	/**
	 * @param timeID
	 *            TIME_FORMAT_1格式的TimeID
	 * @param format
	 *            要转成的时间格式
	 * @return String 时间格式
	 */
	public static String getFormatFromTimeID(String timeID, String format) {
		SimpleDateFormat sd1 = new SimpleDateFormat(TIME_FORMAT_1);
		Date date1 = null;
		try {
			date1 = sd1.parse(timeID);
		} catch (ParseException e) {
		}
		SimpleDateFormat sd2 = new SimpleDateFormat(format);
		return sd2.format(date1);
	}

	/**
	 * 时间格式为yyyyMMddHH的过去24小时,如果在11分钟以内则取前一小时当结束。
	 * 
	 * @return
	 */
	public static List<String> getLastHours(int hours) {
		List<String> h24 = new ArrayList<>();
		Date date = new Date();
		int min = TimeKit4DD.getCurrentMinute();
		if (min < 11) {
			date = TimeKit4DD.getNexHour(date, -1);
		}
		Date date1 = TimeKit4DD.getNexHour(date, -hours);
		for (int i = 0; i <= hours; i++) {
			String strDate = TimeKit4DD.getFormatByDate(TimeKit4DD.getNexHour(date1, i), TimeKit4DD.TIME_FORMAT_8);
			h24.add(strDate);
		}
		return h24;
	}

	/**
	 * @param date
	 *            YYYYMMDD
	 * @return
	 */
	public static List<String> get24HoursByDate(String date) {
		List<String> h24 = new ArrayList<>();
		for (int i = 1; i <= 24; i++) {
			String hour = String.format("%02d", i);
			h24.add(date + hour);
		}
		return h24;
	}

	public static List<String> getLastDates(Date date, int num) {
		List<String> dates = new ArrayList<>();
		Date date1 = TimeKit4DD.getNextDate(date, -num);
		for (int i = 0; i <= num; i++) {
			String strDate = TimeKit4DD.getFormatByDate(TimeKit4DD.getNextDate(date1, i), TimeKit4DD.TIME_FORMAT_9);
			dates.add(strDate);
		}
		return dates;
	}

	/**
	 * @param date
	 *            YYYYMMDD格式
	 * @return 1-24小时格式的区间
	 */
	public static List<String> getHoursByStrDate(String date) {
		List<String> h24 = new ArrayList<>();
		for (int i = 0; i <= 23; i++) {
			String strHour = String.format("%02d", i);
			String strDate = date + strHour;
			h24.add(strDate);
		}
		return h24;
	}

	public static String getFormatByDate(Date date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	public static String getTodayDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(currentTime);
	}

	public static String getYYYYMMddWithDash(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}

	public static String getHHmm(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		return formatter.format(date);
	}

	public static String getTodayDateYYYYMMDD() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		return formatter.format(currentTime);
	}

	public static String getYYYYMMDD(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		return formatter.format(date);
	}

	public static String getHHmmSS(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		return formatter.format(date);
	}

	public static String getStringFormatFromDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}

	public static String getChnDescYMD(Date date) {
		String desc = "";
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		c.setTime(date);
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH) + 1;
		int d = c.get(Calendar.DAY_OF_MONTH);

		desc = y + "年" + m + "月" + d + "日";

		return desc;
	}

	public static String getChnDescYMDHMS(Date date) {
		String desc = "";
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		c.setTime(date);
		int m = c.get(Calendar.MONTH) + 1;
		int d = c.get(Calendar.DAY_OF_MONTH);
		int h = c.get(Calendar.HOUR_OF_DAY);
		int mm = c.get(Calendar.MINUTE);
		int s = c.get(Calendar.SECOND);

		desc = m + "月" + d + "日" + h + "时" + mm + "分" + s + "秒";

		return desc;
	}

	public static String getChnDescMDHM(Date date) {
		String desc = "";
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		c.setTime(date);
		int m = c.get(Calendar.MONTH) + 1;
		int d = c.get(Calendar.DAY_OF_MONTH);
		int h = c.get(Calendar.HOUR_OF_DAY);
		int mm = c.get(Calendar.MINUTE);

		desc = m + "月" + d + "日" + h + "时" + mm + "分";

		return desc;
	}

	public static String getChnDescYMD(String timeID) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = sdf.parse(timeID);
		} catch (ParseException e) {
		}
		String desc = "";
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		c.setTime(date);
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH) + 1;
		int d = c.get(Calendar.DAY_OF_MONTH);

		desc = y + "年" + m + "月" + d + "日";

		return desc;
	}

	public static String getChnDescYMD4TimeID(String timeID) {
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_1);
		Date date = null;
		try {
			date = sdf.parse(timeID);
		} catch (ParseException e) {
		}
		String desc = "";
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		c.setTime(date);
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH) + 1;
		int d = c.get(Calendar.DAY_OF_MONTH);

		desc = y + "年" + m + "月" + d + "日";

		return desc;
	}

	// 20170415013654.688
	public static List<String> getTimeIDs(String timeID1, String timeID2) {
		List<String> times = new ArrayList<>();
		times.add(timeID1);
		String next = timeID1;
		while (true) {
			next = getNexSecondsByF1(next, 1);
			times.add(next);
			if (next.substring(0, 14).equals(timeID2.substring(0, 14))) {
				break;
			}
		}
		return times;
	}

	public static String getChnDescMD(Date date) {
		String desc = "";
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		c.setTime(date);
		int m = c.get(Calendar.MONTH) + 1;
		int d = c.get(Calendar.DAY_OF_MONTH);

		desc = m + "月" + d + "日";

		return desc;

	}

	public static String getMMdd(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
		return formatter.format(date);
	}

	public static Date getNextDate(Date date, int num) {
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, num);
		return c.getTime();
	}

	public static Date getNexHour(Date date, int num) {
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		c.setTime(date);
		c.add(Calendar.HOUR_OF_DAY, num);
		return c.getTime();
	}

	/**
	 * @param strDate
	 *            格式为yyyyMMddHH
	 * @param num
	 * @return
	 */
	public static String getNexHour(String strDate, int num) {
		Date date = getDateByYYYYMMDDHH(strDate);
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		c.setTime(date);
		c.add(Calendar.HOUR_OF_DAY, num);
		Date nextDate = c.getTime();
		return getFormatByDate(nextDate, TIME_FORMAT_8);
	}

	public static String change2MMSS(String format1) {
		SimpleDateFormat sd1 = new SimpleDateFormat(TIME_FORMAT_1);
		Date date1 = null;
		try {
			date1 = sd1.parse(format1);
		} catch (ParseException e) {
		}
		SimpleDateFormat sd4 = new SimpleDateFormat(TIME_FORMAT_7);
		return sd4.format(date1);
	}

	public static String change2HHMMSS(String format1) {
		SimpleDateFormat sd1 = new SimpleDateFormat(TIME_FORMAT_1);
		Date date1 = null;
		try {
			date1 = sd1.parse(format1);
		} catch (ParseException e) {
		}
		SimpleDateFormat sd4 = new SimpleDateFormat(TIME_FORMAT_6);
		return sd4.format(date1);
	}

	/**
	 * 20170101000000.884
	 * 
	 * @param date
	 * @param num
	 * @return
	 */
	public static String getNexMinutesByF1(String f1StrDate, int num) {
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_1);
		Date date = new Date();
		try {
			date = sdf.parse(f1StrDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		c.setTime(date);
		c.add(Calendar.MINUTE, num);
		date = c.getTime();
		return sdf.format(date);
	}

	public static String getNexSecondsByF1(String f1StrDate, int num) {
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_1);
		Date date = new Date();
		try {
			date = sdf.parse(f1StrDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		c.setTime(date);
		c.add(Calendar.SECOND, num);
		date = c.getTime();
		return sdf.format(date);
	}

	public static Date getDateByString(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			date = sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date getDateByYYYYMMDD(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		try {
			date = sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date getDateByYYYYMMDDHH(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
		Date date = new Date();
		try {
			date = sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date getDate4Match(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = new Date();
		try {
			date = sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date getDate4QA(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_4);
		Date date = new Date();
		try {
			date = sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String getNextDateByYYYYMMDD(String date, int num) {
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(new SimpleDateFormat("yyyyMMdd").parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.add(Calendar.DAY_OF_YEAR, num);
		return getYYYYMMDD(c.getTime());
	}

	public static String getNextDateString(Date date, int num) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, num);
		return getStringFormatFromDate(c.getTime());
	}

	public static String getTimeFormat(long timemills) {
		Date date = new Date(timemills);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(date);
	}

	public static String genTimeID() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(currentTime);
	}

	public static String genTimeID(String format) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(currentTime);
	}

	public static String getTimeFormat1(long timemills) {
		Date date = new Date(timemills);
		SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT_1);
		return formatter.format(date);
	}

	public static double getTimeMillsByTF1(String strTF1) {
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_1);
		Date date = new Date();
		try {
			date = sdf.parse(strTF1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return getTimeInMills(date);
	}

	public static String getTimeFormat2(long timemills) {
		Date date = new Date(timemills);
		SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT_2);
		return formatter.format(date);
	}

	public static String getTimeFormat3(long timemills) {
		Date date = new Date(timemills);
		SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT_3);
		return formatter.format(date);
	}

	public static int getRuningMins(long lastTime) {
		long now = System.currentTimeMillis();
		int mins = (int) ((now - lastTime) / 1000 / 60);
		return mins;
	}

	public static Double getTimeInMills(Date date) {
		System.setProperty("user.timezone", "Asia/Shanghai");
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		cal.setTime(date);
		return (double) cal.getTimeInMillis();
	}

	public static int getSpanToSecond(String timespan) {
		String span = timespan;
		String[] spans = span.split(":");
		int seconds = 0;
		if (spans.length == 2) {
			seconds += Long.valueOf(spans[0]) * 60;
			seconds += Long.valueOf(spans[1]);
		}
		return seconds;
	}

	public static String change2Format1(String fromRunningTime) {
		String returnFormat = "";
		SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date d1 = dd.parse(fromRunningTime);
			SimpleDateFormat dd2 = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
			returnFormat = dd2.format(d1);
		} catch (ParseException e) {
			// do nothing
		}
		return returnFormat;
	}

	public static String change2Format2(String timeFormat1) {
		String returnFormat = "";
		SimpleDateFormat dd = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			Date d1 = dd.parse(timeFormat1);
			SimpleDateFormat dd2 = new SimpleDateFormat(TIME_FORMAT_2);
			returnFormat = dd2.format(d1);
		} catch (ParseException e) {
			// do nothing
		}
		return returnFormat;
	}

	public static int getCurrentHour() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.HOUR_OF_DAY);
	}

	public static int getCurrentMinute() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.MINUTE);
	}

	public static long getUnixTimeSeconds() {
		return (System.currentTimeMillis() / 1000L);
	}

	public static int getBreakMins(String startTime, String endTime) {
		int mins = 0;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMAT_1);
			Date date1 = simpleDateFormat.parse(startTime);
			Date date2 = simpleDateFormat.parse(endTime);
			long time1 = date1.getTime();
			long time2 = date2.getTime();
			mins = (int) ((time2 - time1) / 1000 / 60);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return mins;
	}

	public static int getBreakHours(String hour1, String hour2) {
		int mins = 0;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMAT_8);
			Date date1 = simpleDateFormat.parse(hour1);
			Date date2 = simpleDateFormat.parse(hour2);
			long time1 = date1.getTime();
			long time2 = date2.getTime();
			mins = (int) ((time2 - time1) / 1000 / 60 / 60);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return mins;
	}

	public static int getBreakSecs(String startTime, String endTime) {
		int secs = 0;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMAT_1);
			Date date1 = simpleDateFormat.parse(startTime);
			Date date2 = simpleDateFormat.parse(endTime);
			long time1 = date1.getTime();
			long time2 = date2.getTime();
			secs = (int) ((time2 - time1) / 1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return secs;
	}

	public static void main(String[] args) throws ParseException {
		List<String> l = getLastDates(new Date(), 1);

		for (String s : l) {
			System.out.println(s);
		}

	}

}
