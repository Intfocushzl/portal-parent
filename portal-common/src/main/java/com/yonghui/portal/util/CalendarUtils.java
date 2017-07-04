package com.yonghui.portal.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间工具类
 * 
 * 
 */
public final class CalendarUtils {
	public static final String YMDHMS_LONG = "yyyyMMddHHmmss";
	public static final String YMDHMS_SHORT = "yyMMddHHmmss";
	public static final String YMD_LONG = "yyyyMMdd";
	public static final String YMD_SHORT = "yyMMdd";
	public static final String Y_M_DHMS_LONG = "yyyy-MM-dd HH:mm:ss";
	public static final String Y_M_DHMS_SHORT = "yy-MM-dd HH:mm:ss";
	public static final String Y_M_D_LONG = "yyyy-MM-dd";
	public static final String Y_M_D_SHORT = "yy-MM-dd";
	public static final String YBMBD_LONG = "yyyy/MM/dd";
	public static final String YBMBD_SHORT = "yy/MM/dd";
	public static final String Y_M_D_H_M = "yyyy-MM-dd HH:mm";
	/**
	 * 得到格式化后的日期
	 * 
	 * @param fmt
	 * @param dtstr
	 * @return
	 */
	public static Date getParseDate(String fmt, String dtstr) {
		if (fmt != null && fmt.trim().equals("")) {
			throw new RuntimeException("fmt is not null");
		}
		try {
			return new SimpleDateFormat(fmt).parse(dtstr);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 得到格式化后的日期字符串
	 * 
	 * @param fmt
	 * @param dt
	 * @return
	 */
	public static String getFormatDate(String fmt, Date dt) {
		if (fmt != null && fmt.trim().equals("")) {
			throw new RuntimeException("fmt is not null");
		}
		if (dt == null) {
			return new SimpleDateFormat(fmt).format(new Date());
		}
		return new SimpleDateFormat(fmt).format(dt);
	}

	/**
	 * 根据指定日期修改年份
	 * 
	 * @param dt
	 * @param amount
	 *            正数代表增加多少年，负数代表减少多少年
	 * @return
	 */
	public static Date mergeYear(Date dt, int amount) {
		Calendar cl = Calendar.getInstance();
		if (dt != null) {
			cl.setTime(dt);
		}
		cl.add(Calendar.YEAR, amount);
		return cl.getTime();
	}

	/**
	 * 在指定日期上面对月份进行增减
	 * 
	 * @param dt
	 * @param amount
	 *            正数代表增加，负数代表减少
	 * @return
	 */
	public static Date mergeMonth(Date dt, int amount) {
		Calendar cl = Calendar.getInstance();
		if (dt != null) {
			cl.setTime(dt);
		}
		cl.add(Calendar.MONTH, amount);
		return cl.getTime();
	}

	/**
	 * 在指定日期上面对天数进行增减
	 * 
	 * @param dt
	 * @param amount
	 *            正数代表增加 ，负数代表减少
	 * @return
	 */
	public static Date mergeDate(Date dt, int amount) {
		Calendar cl = Calendar.getInstance();
		if (dt != null) {
			cl.setTime(dt);
		}
		cl.add(Calendar.DATE, amount);
		return cl.getTime();
	}

	/**
	 * 在指定日期上面对小时进行增减
	 * 
	 * @param dt
	 * @param amount
	 *            正数代表增加 ，负数代表减少
	 * @return
	 */
	public static Date mergeHour(Date dt, int amount) {
		Calendar cl = Calendar.getInstance();
		if (dt != null) {
			cl.setTime(dt);
		}
		cl.add(Calendar.HOUR, amount);
		return cl.getTime();
	}

	/**
	 * 在指定日期上面对秒进行增减
	 * 
	 * @param dt
	 * @param amount
	 *            正数代表增加 ，负数代表减少
	 * @return
	 */
	public static Date mergeSecond(Date dt, int amount) {
		Calendar cl = Calendar.getInstance();
		if (dt != null) {
			cl.setTime(dt);
		}
		cl.add(Calendar.SECOND, amount);
		return cl.getTime();
	}

	/**
	 * 在指定日期上面对分钟进行增减
	 * 
	 * @param dt
	 * @param amount
	 *            正数代表增加 ，负数代表减少
	 * @return
	 */
	public static Date mergeMinute(Date dt, int amount) {
		Calendar cl = Calendar.getInstance();
		if (dt != null) {
			cl.setTime(dt);
		}
		cl.add(Calendar.MINUTE, amount);
		return cl.getTime();
	}
}
