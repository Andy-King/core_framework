package org.jee.framework.core.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理相关方法
 * @author AK
 * 
 */
public abstract class DateUtils {

	/**
	 * DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	 */
	public static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	/**
	 * DEFAULT_DATE_PATTERN = "yyyy-MM-dd"
	 */
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

	/**
	 * One day millis
	 */
	public final static long ONE_DAY_MILLS = 1000L * 60 * 60 * 24;
	
	/**
	 * 格式化日期字符串
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static String format(long time, String pattern) {
		final SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(time);
	}
	
	/**
	 * 格式化日期字符串
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static String format(Date date, String pattern) {
		return format(date.getTime(), pattern);
	}

	/**
	 * <pre>
	 * 自动判断格式化类型
	 * 注: DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	 * </pre>
	 * 
	 * @see org.jee.framework.core.utils.DateUtils#DEFAULT_DATE_TIME_PATTERN
	 * @param date
	 *            需格式化的日期
	 * @return 格式化后的字符串
	 */
	public static String format(Date date) {
		return format(date, DEFAULT_DATE_TIME_PATTERN);
	}

	/**
	 * 解析日期
	 * 
	 * @param date 日期字符串
	 * @param pattern 日期格式
	 * @return Date
	 */
	public static Date parse(String date, String pattern) {
		final SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(date);
		} catch (ParseException e) {
			ExceptionUtils.throwRuntimeException(e);
		}
		return null;
	}

	/**
	 * <pre>
	 * 解析字符串到日期型
	 * 注: DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	 * </pre>
	 * 
	 * @see org.jee.framework.core.utils.DateUtils#DEFAULT_DATE_TIME_PATTERN
	 * @param date 日期字符串
	 * @return 返回日期型对象
	 */
	public static Date parse(String date) {
		return parse(date, DEFAULT_DATE_TIME_PATTERN);
	}

	/**
	 * 取得当前日期
	 * 
	 * @return
	 */
	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	/**
	 * 取得当前日期
	 * 
	 * @return
	 */
	public static Timestamp getCurrentTimestamp(long time) {
		return new Timestamp(time);
	}


	/**
	 * <pre>
	 * 字符串转换成日期
	 * invoke:
	 * System.out.println(string2Date("2014-06-05 09:30:20", "yyyy-MM-dd HH:mm:ss"));
	 * result:
	 * 11:33
	 * </pre>
	 * 注:
	 *   此方法调用的是 parse(date, datePattern);
	 * @see org.jee.framework.core.utils.DateUtils#parse(String date, String pattern)
	 */
	public static Date string2Date(String date, String datePattern) {
		return parse(date, datePattern);
	}
	
	/**
	 * 字符串转换成日期
	 * 
	 * @see org.jee.framework.core.utils.DateUtils#DEFAULT_DATE_TIME_PATTERN
	 */
	public static Date string2Date(String date) {
		return string2Date(date, DEFAULT_DATE_TIME_PATTERN);
	}

	/**
	 * 字符串转换成时间
	 */
	public static long string2Time(String date, String datePattern) {
		return string2Date(date, datePattern).getTime();
	}

	/**
	 * 字符串转换成时间
	 * @see org.jee.framework.core.utils.DateUtils#string2Date(String date)
	 */
	public static long string2Time(String date) {
		return string2Date(date).getTime();
	}

	/**
	 * 在当前日期上加天数
	 * 
	 * @param startDate
	 * @param incrDays
	 * @return
	 */
	public static Date addDays(Date startDate, int incrDays) {
		final long addDayAfterMillis = ONE_DAY_MILLS * incrDays + startDate.getTime();
		return new Date(addDayAfterMillis);
	}

	/**
	 * 
	 * 日期转换成millis
	 * 
	 * <pre>
	 * e.g:
	 * 活动开始结束时间
	 * 活动时间     2013, 12, 1, 0, 0, 0~2013, 12, 15, 23, 59, 59
	 *     private final static long COUPONS_ACTIVE_START_TIME = getDate2Millis(2013, 12, 1, 0, 0, 0);
	 *     private final static long COUPONS_ACTIVE_END_TIME   = getDate2Millis(2013, 12, 15, 23, 59, 59);
	 * </pre>
	 */
	public static long getDate2Millis(int year, int month, int day, int hour,
			int minute, int second) {
		Calendar c = Calendar.getInstance();
		c.set(year, month - 1, day, hour, minute, second);
		// @see java.util.GregorianCalendar#get()
		c.get(Calendar.MILLISECOND); // 先调用 get()，强制 Calendar 刷新// Sync the time
									 // and calendar fields.// complete();
		return c.getTimeInMillis();
	}

	/**
	 * 得到两个时间之间的秒钟数
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static int getSeconds(Date start, Date end) {
		return (int) ((end.getTime() - start.getTime()) / 1000);
	}

	/**
	 * 按自然日计算到当天秒数 根据当前时间得到离23:59:59的秒数
	 */
	public static int getCurrentNaturalDaySeconds() {
		Calendar calendar = Calendar.getInstance();
		long currentTime = calendar.getTimeInMillis();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		// @see java.util.GregorianCalendar#get()
		calendar.get(Calendar.MILLISECOND); // 先调用 get()，强制 Calendar 刷新// Sync
											// the time and calendar fields.//
											// complete();
		currentTime = calendar.getTimeInMillis() - currentTime;
		return (int) (currentTime / 1000);
	}

	/**
	 * 防止返回结果为0,时候导致memcached为一个月
	 * 
	 * @see org.jee.framework.core.utils.DateUtils#getCurrentNaturalDaySeconds()
	 */
	public static int getCurrentNaturalDaySecondsForMemcached() {
		final int value = getCurrentNaturalDaySeconds();
		return value <= 0 ? 1 : value;
	}
	
	public static void main(String[] args) {
		System.out.println(addDays(new Date(System.currentTimeMillis()), 1));
		System.out.println(string2Date("2014-06-05 09:30:20", "yyyy-MM-dd HH:mm:ss"));
	}
}
