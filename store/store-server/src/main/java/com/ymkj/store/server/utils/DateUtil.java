package com.ymkj.store.server.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 通用日期工具类
 * 
 * @author longjw@yuminsoft.com
 * @date 2017年5月8日 上午11:33:11
 * @version 1.0
 */
public class DateUtil {

    public final static String CEN_YYYYMMDD = "yyyy/MM/dd";
    public final static String DATAFORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public final static String CN_YYYY_MM_DD = "yyyy年MM月dd日";
    public final static String DATAFORMAT_YYYYMMDD = "yyyyMMdd";
    public final static String DATAFORMAT_YYYY_MM_DDHHmm = "yyyy-MM-dd HH:mm";
    public final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public final static String DATE_TIME_SSS_FORMAT = "yyyyMMddHHmmSSS";
    public final static String DATEFORMAT_yyyy = "yyyy/MM/dd HH:mm:ss";
    public final static String DATE_TIME_HH_FORMAT = "HH";
    public final static String CN_MM_DD = "MM月dd日";

    /**
     * 日期字符串转日期
     *
     * @param dateString
     * @return
     */
    public static Date strToDate(String dateString, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        try {
            Date date = df.parse(dateString);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date getTime() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 将传入日期格式化成指定格式。
     *
     * @param d       要格式化的日期
     * @param pattern 格式化的样式
     * @return 日期的字符串形式
     */
    public static String format(Date d, String pattern) {
        if (d == null)
            return null;
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(d);
    }

    public static Date getCommStyleTime(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        try {
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getCommStyleTime(String date, String format) {
        DateFormat df = new SimpleDateFormat(format);

        try {
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 两个日期相减得天数
     *
     * @param enddate   结束日期 date类型
     * @param begindate 开始日期 date类型
     * @return 天数
     */
    public static int getIntervalDays(Date enddate, Date begindate) {
        long millisecond = enddate.getTime() - begindate.getTime();
        int day = (int) (millisecond / 24L / 60L / 60L / 1000L);
        return day;
    }

    /**
     * 取得在指定时间上加减days天后的时间
     *
     * @param date 指定的时间
     * @param days 天数,正为加，负为减
     * @return 在指定时间上加减days天后的时间
     */
    public static Date addDays(Date date, int days) {
        Date time = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        time = cal.getTime();
        return time;
    }

    /**
     * 获取指定时间的月份的第一天  返回指定日期格式的字符串
     *
     * @param date
     * @return
     */
	public static String getMonthFirstDay(Date date, String pattern) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH,
				cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		Date startDate = cal.getTime();
		return DateUtil.format(startDate, pattern);
	}

    /**
     * 获取指定月份的天数
     *
     * @param date
     * @return
     */
    public static int getMonthDays(Date date) {
        int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
        int month = Integer.parseInt(new SimpleDateFormat("MM").format(date));
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }
   /**
    * 日期转int
    * @param date
    * @return
    */
    public static int DateToInt(Date date) {  
		int time = 0;  
        String strTime = date.getTime() + "";  
        strTime = strTime.substring(0, 10);  
        time = Integer.parseInt(strTime);  
        return time;  
    }  
    

}
