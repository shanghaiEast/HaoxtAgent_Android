package com.haoxt.agent.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
/**
 * 日期处理工具函数包,包括日期对象、日期字符串相关转换函数
 *
 * @version 1.0 <br>
 */
public class DateUtil {

    private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

    private final static SimpleDateFormat sdfDay = new SimpleDateFormat(
            "yyyy-MM-dd");

    private final static SimpleDateFormat sdfDays = new SimpleDateFormat(
            "yyyyMMdd");

    private final static SimpleDateFormat sdfTime = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    /**
     * 获取YYYY格式
     *
     * @return
     */
    public static String getYear() {
        return sdfYear.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD格式
     *
     * @return
     */
    public static String getDay() {
        return sdfDay.format(new Date());
    }

    /**
     * 获取YYYYMMDD格式
     *
     * @return
     */
    public static String getDays() {
        return sdfDays.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     *
     * @return
     */
    public static String getTime() {
        return sdfTime.format(new Date());
    }

    /**
     * @Title: compareDate
     * @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
     * @param s
     * @param e
     * @return boolean
     * @throws
     * @author luguosui
     */
    public static boolean compareDate(String s, String e) {
        if (fomatDate(s) == null || fomatDate(e) == null) {
            return false;
        }
        return fomatDate(s).getTime() >= fomatDate(e).getTime();
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public static Date fomatDate(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 校验日期是否合法
     *
     * @return
     */
    public static boolean isValidDate(String s) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fmt.parse(s);
            return true;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }
    }

    /**
     *
     * @Description 获得时间年差
     * @param startTime
     * @param endTime
     * @return
     */
    public static int getDiffYear(String startTime, String endTime) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(
                    startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
            return years;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return 0;
        }
    }
    /**
     *
     * @Description 获得时间分钟差
     * @param startTime
     * @param endTime
     * @return
     */
    public static int getDiffMinute(String startTime, String endTime) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            int minute = (int) ((fmt.parse(endTime).getTime() - fmt.parse(
                    startTime).getTime()) / 1000);
            return minute;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return 0;
        }
    }

    /**
     * <li>功能描述：时间相减得到天数
     *
     * @param beginDateStr
     * @param endDateStr
     * @return long
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr, String endDateStr) {
        long day = 0;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
                "yyyy-MM-dd");
        java.util.Date beginDate = null;
        java.util.Date endDate = null;

        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        // System.out.println("相隔的天数="+day);

        return day;
    }

    /**
     * 得到n天之后的日期
     *
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);

        return dateStr;
    }

    /**
     * 得到n天之后是周几
     *
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);

        return dateStr;
    }

    /**
     * 定义常见的时间格式
     */
    private static String[] dateFormat = { "yyyy-MM-dd HH:mm:ss",
            "yyyy/MM/dd HH:mm:ss", "yyyy年MM月dd日HH时mm分ss秒", "yyyy-MM-dd",
            "yyyy/MM/dd", "yy-MM-dd", "yy/MM/dd", "yyyy年MM月dd日", "HH:mm:ss",
            "yyyyMMddHHmmss", "yyyyMMdd", "yyyy.MM.dd", "yy.MM.dd",
            "MM月dd日HH时mm分", "yyyy年MM月dd日 HH:mm:ss", "yyyy-MM-dd HH:mm" };

    /**
     * <b>方法描述：</b> 将日期格式从 java.util.Calendar 转到 java.sql.Timestamp 格式
     *
     * @param date
     *            格式表示的日期
     * @return <p>
     *         <b>使用说明：</b>
     *         </p>
     */
    public static Timestamp convUtilCalendarToSqlTimestamp(Calendar date) {
        if (date == null)
            return null;
        else
            return new Timestamp(date.getTimeInMillis());
    }

    /**
     * <b>方法描述：</b> 将日期格式从 java.util.Timestamp 转到 java.util.Calendar 格式
     * @param date
     *            格式表示的日期
     * @return <p>
     *         <b>使用说明：</b>
     *         </p>
     */
    public static Calendar convSqlTimestampToUtilCalendar(Timestamp date) {
        if (date == null)
            return null;
        else {
            java.util.GregorianCalendar gc = new java.util.GregorianCalendar();
            gc.setTimeInMillis(date.getTime());
            return gc;
        }
    }

    /**
     * <b>方法描述：</b> 解析一个字符串，形成一个Calendar对象，适应各种不同的日期表示法
     * @param dateStr
     *            期望解析的字符串，注意，不能传null进去，否则出错
     * @return <p>
     *         <b>使用说明：</b> <br>
     *         <br>
     *         可输入的日期字串格式如下： <br>
     *         "yyyy-MM-dd HH:mm:ss", <br>
     *         "yyyy/MM/dd HH:mm:ss", <br>
     *         "yyyy年MM月dd日HH时mm分ss秒", <br>
     *         "yyyy-MM-dd", <br>
     *         "yyyy/MM/dd", <br>
     *         "yy-MM-dd", <br>
     *         "yy/MM/dd", <br>
     *         "yyyy年MM月dd日", <br>
     *         "HH:mm:ss", <br>
     *         "yyyyMMddHHmmss", <br>
     *         "yyyyMMdd", <br>
     *         "yyyy.MM.dd", <br>
     *         "yy.MM.dd"
     *         </p>
     */
    public static Calendar parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().length() == 0)
            return null;

        Date result = parseDate(dateStr, 0);

        Calendar cal = Calendar.getInstance();
        cal.setTime(result);

        return cal;
    }

    /**
     * <b>方法描述：</b> 将一个日期转成日期时间格式，格式这样 2002-08-05 21:25:21
     * @param date
     *            期望格式化的日期对象
     * @return 返回格式化后的字符串
     *         <p>
     *         <b>使用说明：</b> <br>
     *         Calendar date = new GregorianCalendar(); <br>
     *         String ret = DateUtils.toDateTimeStr(date); <br>
     *         返回： <br>
     *         ret = "2002-12-04 09:13:16";
     *         </p>
     */
    public static String toDateTimeStr(Calendar date) {
        if (date == null)
            return null;
        return new SimpleDateFormat(dateFormat[0]).format(date.getTime());
    }

    /**
     * <b>方法描述：</b> 将一个日期转成日期时间格式
     * @param format
     *            日志格式序号
     * @param date
     *            期望格式化的日期对象
     * @return <p>
     *         <b>使用说明：</b>
     *         </p>
     */
    public static String toDateTimeStr(int format, Calendar date) {
        if (date == null)
            return null;

        return new SimpleDateFormat(dateFormat[format]).format(date.getTime());
    }

    /**
     * <b>方法描述：</b> 将一个日期转成日期格式， 格式：yyyy-MM-dd
     * @param date
     *            期望格式化的日期对象
     * @return 返回格式化后的字符串
     *         <p>
     *         <b>使用说明：</b>
     *         </p>
     */
    public static String toDateStr(Calendar date) {
        if (date == null)
            return null;
        return new SimpleDateFormat(dateFormat[3]).format(date.getTime());
    }

    /**
     * <b>方法描述：</b> 根据format数组的序号，将Calendar转换为对应格式的String
     * @param date
     *            要转换的日期对象
     * @param formatIndex
     *            format数组中的索引
     * @return 返回格式化后的字符串
     *         <p>
     *         <b>使用说明：</b>
     *         </p>
     */
    public static String toDateStrByFormatIndex(Calendar date, int formatIndex) {
        if (date == null)
            return null;
        return new SimpleDateFormat(dateFormat[formatIndex]).format(date
                .getTime());
    }

    /**
     * @param d1
     *            第一个日期
     * @param d2
     *            第二个日期
     * @return 两个日期之间相差的天数
     *         <p>
     *         <b>使用说明：</b>
     *         </p>
     */
    public static int calendarMinus(Calendar d1, Calendar d2) {
        if (d1 == null || d2 == null) {
            return 0;
        }

        d1.set(Calendar.HOUR_OF_DAY, 0);
        d1.set(Calendar.MINUTE, 0);
        d1.set(Calendar.SECOND, 0);

        d2.set(Calendar.HOUR_OF_DAY, 0);
        d2.set(Calendar.MINUTE, 0);
        d2.set(Calendar.SECOND, 0);

        long t1 = d1.getTimeInMillis();
        long t2 = d2.getTimeInMillis();
        long daylong = 3600 * 24 * 1000;
        t1 = t1 - t1 % (daylong);
        t2 = t2 - t2 % (daylong);

        long t = t1 - t2;
        int value = (int) (t / (daylong));
        return value;
    }

    /**
     * @param d1
     *            第一个日期
     * @param d2
     *            第二个日期
     * @return 两个日期之间相差的天数
     *         <p>
     *         <b>使用说明：</b>
     *         </p>
     */
    public static long calendarminus(Calendar d1, Calendar d2) {
        if (d1 == null || d2 == null) {
            return 0;
        }
        return (d1.getTimeInMillis() - d2.getTimeInMillis()) / (3600 * 24000);
    }

    /**
     * @param d1
     *            第一个日期
     * @param d2
     *            第二个日期
     * @return 两个日期之间相差的秒数
     *         <p>
     *         <b>使用说明：</b>
     *         </p>
     */
    public static long calendarTime(Calendar d1, Calendar d2) {
        if (d1 == null || d2 == null) {
            return 0;
        }
        return (d1.getTimeInMillis() - d2.getTimeInMillis()) / 1000;
    }

    /**
     * @param dateStr
     *            日期的字符串形式
     * @param index
     *            日期格式索引
     * @return 符合日期字符串的日期对象
     *         <p>
     *         <b>使用说明：</b>
     *         </p>
     */
    public static Date parseDate(String dateStr, int index) {
        DateFormat df = null;
        try {
            df = new SimpleDateFormat(dateFormat[index]);
            return df.parse(dateStr);
        } catch (Exception aioe) {
            return null;
        }
    }

    /**
     * @param dateStr
     *            日期的字符串形式
     * @return 符合日期字符串的日期对象
     *         <p>
     *         <b>使用说明：</b>
     *         </p>
     */
    public static Date StringToDate(String dateStr) {
        if (dateStr == null || dateStr.trim().length() == 0) {
            return null;
        }
        return parseDate(dateStr, 3);
    }

    /**
     * 方法描述：</b> 将日期转换为格式化后的日期字符串
     * @param date
     *            日期
     * @param index
     *            日期格式索引
     * @return <p>
     *         <b>使用说明：</b>
     *         </p>
     */
    public static String dateToString(Date date, int index) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(dateFormat[index]).format(date);
    }

    /**

     * <b>方法描述：</b> 返回固定格式的日期字符串。转换结果格式为："yyyy-MM-dd"
     *
     * @param date
     *            待转换的日期对象
     * @return <p>
     *         <b>使用说明：</b>
     *         </p>
     */
    public static String dateToString(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(dateFormat[3]).format(date);
    }

    /**
     * @Title: getStartDate
     * @Description: 获取当前日期，"yyyy-MM-dd 05:00:00"
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    public static String getStartDate() {
        String startTime = new SimpleDateFormat(dateFormat[3])
                .format(new Date());
        startTime = startTime + " 05:00:00";
        return startTime;
    }

    /**
     * @Title: getDate
     * @Description: 获取当前日期，"yyyy-MM-dd"
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    public static String getDate() {

        return new SimpleDateFormat(dateFormat[3]).format(new Date());
    }

    /**
     * @Title: getDateTime
     * @Description: 获取当前日期，"yyyy-MM-dd HH:mm:ss:SSS"
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    public static String getDateTime() {

        return new SimpleDateFormat(dateFormat[0]).format(new Date());
    }

    public static String getDateTimeHHMM(String datetime) throws ParseException {
        return new SimpleDateFormat(dateFormat[15])
                .format(new SimpleDateFormat(dateFormat[15]).parse(datetime));
    }

    /**
     * <p>
     * <b>作者：</b>zy
     * </p>
     * <p>
     *
     * </p>
     * <p>
     * <b>方法描述：</b> 将日期格式从 java.util.Date 转到 java.sql.Timestamp 格式
     * </p>
     *
     * @param date
     * @return <p>
     *         <b>使用说明：</b>
     *         </p>
     */
    public static Timestamp convUtilDateToSqlTimestamp(Date date) {
        if (date == null)
            return null;
        else
            return new Timestamp(date.getTime());
    }

    /**
     * <p>
     * <b>作者：</b>zy
     * </p>
     * <p>
     *
     * </p>
     * <p>
     * <b>方法描述：</b> 将Date对象转换成Calendar对象
     * </p>
     *
     * @param date
     * @return <p>
     *         <b>使用说明：</b>
     *         </p>
     */
    public static Calendar convUtilDateToUtilCalendar(Date date) {
        if (date == null)
            return null;
        else {
            java.util.GregorianCalendar gc = new java.util.GregorianCalendar();
            gc.setTimeInMillis(date.getTime());
            return gc;
        }
    }

    /**
     * <p>
     * <b>作者：</b>zy
     * </p>
     * <p>
     *
     * </p>
     * <p>
     * <b>方法描述：</b> 根据某个索引中的日期格式解析日期
     * </p>
     *
     * @param dateStr
     *            日期
     * @param index
     *            日期格式的索引
     * @return <p>
     *         <b>使用说明：</b>
     *         </p>
     */
    public static Timestamp parseTimestamp(String dateStr, int index) {
        DateFormat df = null;
        try {
            df = new SimpleDateFormat(dateFormat[index]);
            return new Timestamp(df.parse(dateStr).getTime());
        } catch (ParseException pe) {
            return new Timestamp(parseDate(dateStr, index + 1).getTime());
        } catch (ArrayIndexOutOfBoundsException aioe) {
            return null;
        }
    }

    /**

     * <b>方法描述：</b> 返回固定格式的日期字符串。转换结果格式为："yyyy-MM-dd"
     * </p>
     *
     *            待转换的日期对象
     * @return <p>
     *         <b>使用说明：</b>
     *         </p>
     */
    public static Timestamp parseTimestamp(String dateStr) {
        DateFormat df = null;
        try {
            df = new SimpleDateFormat(dateFormat[3]);
            return new Timestamp(df.parse(dateStr).getTime());
        } catch (ParseException pe) {
            return null;
        } catch (ArrayIndexOutOfBoundsException aioe) {
            return null;
        }
    }

    /**
     * @Title: calculateCalendarByday
     * @Description: 日期加减天数
     * @param calendar
     * @param days
     * @return Calendar 返回类型
     */
    public static Calendar calculateCalendarByday(Calendar calendar, int days) {
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar;
    }

    /**
     * 获取倒计时
     *
     * @param countdownTime
     *            倒计时的时间，格式：yyyy-MM-dd HH:mm:ss
     * @return String 为null时转换失败 倒计时够天数显示天数，不够显示小时，不够小时显示分钟
     *         2天后面没有1天，而是24小时，2小时同理
     */
    public static String getCountdownTime(String countdownTime) {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .parse(countdownTime));// 将字符串转换为可获取毫秒数的日期类型
            long nowDate = System.currentTimeMillis();
            long countdown = (c.getTimeInMillis() - nowDate) / (1000 * 60);// 获取倒计时（分钟）
            long days = countdown / (60 * 24);// 天
            long hours = countdown / 60 % 24;// 小时
            long mins = countdown % 60;// 分钟
            if (days > 0) {
                return (days + 1) + "天";
            }
            if (hours > 0) {
                return (hours + 1) + "小时";
            }
            if (mins > 0) {
                return mins + "分钟";
            }
            return "0分钟";
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取时间节点是在当前时间节点多长时间以前
     *
     * @param oldTime
     *            之前的时间，格式：yyyy-MM-dd HH:mm:ss
     * @return String 为null时转换失败 倒计时够天数显示天数，不够显示小时，不够小时显示分钟
     */
    public static String getDiffTimeLessNow(String oldTime) {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .parse(oldTime));// 将字符串转换为可获取毫秒数的日期类型
            long nowDate = System.currentTimeMillis();
            long countdown = (nowDate - c.getTimeInMillis()) / (1000 * 60);// 获取倒计时（分钟）
            long days = countdown / (60 * 24);// 天
            long hours = countdown / 60 % 24;// 小时
            long mins = countdown % 60;// 分钟
            if (days > 0) {
                return days + "天";
            }
            if (hours > 0) {
                return hours + "小时";
            }
            if (mins > 0) {
                return mins + "分钟";
            }
            return "0分钟";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 是否已经过时
     *
     * @param time
     *            判断过时的时间对象，格式：yyyy-MM-dd HH:mm:ss
     * @return boolean 为null时判断失败 true表示改时间是当前时间节点之前的时间，false表示改时间是当前时间节点之后的时间
     */
    public static boolean isOldTime(String time) {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(time));// 将字符串转换为可获取毫秒数的日期类型
            long nowDate = System.currentTimeMillis();
            long dateDiff = (c.getTimeInMillis() - nowDate);// 获取时间差
            if (dateDiff < 0) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @Description: 获得两个日期之间的所有日期
     * @author zy
     * @date 2015-9-21 下午03:32:21
     * @param startDate
     *            2015-09-21
     * @param endDate
     *            2015-11-13
     * @return List<String> String日期格式"yyyy-MM-dd"的集合
     */
    public static List<String> getDates(String startDate, String endDate) {
        List<String> result = new ArrayList<String>();
        if (startDate.equals(endDate)) {
            return result;
        }
        Calendar startDay = Calendar.getInstance();
        Calendar endDay = Calendar.getInstance();
        startDay.setTime(DateUtil.parseDate(startDate, 3));
        endDay.setTime(DateUtil.parseDate(endDate, 3));
        Calendar temp = (Calendar) startDay.clone();
        temp.add(Calendar.DAY_OF_YEAR, 1);
        result.add(startDate.substring(0, 10));
        while (temp.before(endDay)) {
            result.add(DateUtil.dateToString(temp.getTime()));
            temp.add(Calendar.DAY_OF_YEAR, 1);
        }
        result.add(endDate.substring(0, 10));
        return result;
    }

    /**
     * @Description: 获得两个月之间的所有月
     * @author zy
     * @date 2015-9-22 上午10:28:02
     * @param minDate
     *            2015-01
     * @param maxDate
     *            2016-07
     * @return List<String>
     * @throws ParseException
     */
    public static List<String> getMonthBetween(String minDate, String maxDate)
            throws ParseException {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(sdf.parse(minDate));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(sdf.parse(maxDate));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }

    /**
     * @Description: 获取某年某月最后一天
     * @author zy
     * @date 2015-9-22 下午02:10:25
     * @param date
     *            2015-02
     * @return
     */
    public static String getLastDayOfMonth(String date) {
        int year = Integer.parseInt(date.split("-")[0]);
        int month = Integer.parseInt(date.split("-")[1]);
        Calendar cal = Calendar.getInstance();
        // 设置年份
        cal.set(Calendar.YEAR, year);
        // 设置月份
        cal.set(Calendar.MONTH, month - 1);
        // 获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        // 设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());

        return lastDayOfMonth;
    }

    /**
     * @Description: 获取某年某月第一天
     * @author zy
     * @date 2015-9-22 下午02:11:56
     * @return
     */
    public static String getFirstDayOfMonth(String date) {
        int year = Integer.parseInt(date.split("-")[0]);
        int month = Integer.parseInt(date.split("-")[1]);
        Calendar cal = Calendar.getInstance();
        // 设置年份
        cal.set(Calendar.YEAR, year);
        // 设置月份
        cal.set(Calendar.MONTH, month - 1);
        // 获取某月最小天数
        int lastDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        // 设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());
        return lastDayOfMonth;
    }

    /**
     * 得到年月日时分秒
     */
    public static String getNowtime() {
        Calendar calendar = new GregorianCalendar();
        return calendar.get(Calendar.YEAR) + ""
                + (calendar.get(Calendar.MONTH) + 1) + ""
                + calendar.get(Calendar.DAY_OF_MONTH) + ""
                + calendar.get(Calendar.HOUR_OF_DAY) + ""
                + calendar.get(Calendar.MINUTE) + ""
                + calendar.get(Calendar.SECOND) + ""
                + calendar.get(Calendar.MILLISECOND);
    }

    /**
     * 获得昨天5点
     *
     * @return
     * @throws ParseException
     */
    public static String getYesterdayFive() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 05:00:00");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        return sdf.format(calendar.getTime());
    }

    /**
     * 获得今天5点
     *
     * @return
     * @throws ParseException
     */
    public static String getDateFive() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 05:00:00");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return sdf.format(calendar.getTime());
    }

    /**
     * 获得明天天5点
     *
     * @return
     * @throws ParseException
     */
    public static String getTomorrowFive() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 05:00:00");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);
        return sdf.format(calendar.getTime());
    }

    /**
     * 获得上周起始时间
     *
     * @return
     * @throws ParseException
     */
    public static String getLastWeekStartTime() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 05:00:00");
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int offset = 1 - dayOfWeek;
        calendar.add(Calendar.DATE, offset - 7);
        return sdf.format(calendar.getTime());
    }

    /**
     * 获得上周结束时间
     *
     * @return
     * @throws ParseException
     */
    public static String getLastWeekEndTime() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 05:00:00");
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int offset = 7 - dayOfWeek;
        calendar.add(Calendar.DATE, offset - 7);
        return sdf.format(calendar.getTime());
    }

    /**
     * 获得当周起始时间
     *
     * @return
     * @throws ParseException
     */
    public static String getStartWeek(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.DAY_OF_WEEK,
                calendar.getActualMinimum(Calendar.DAY_OF_WEEK));
        return sdf.format(calendar.getTime());
    }

    /**
     * 获得当周结束时间
     *
     * @return
     * @throws ParseException
     */
    public static String getEndWeek(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.DAY_OF_WEEK,
                calendar.getActualMaximum(calendar.DAY_OF_WEEK));
        return sdf.format(calendar.getTime());
    }

    /**
     * 获得当月起始时间
     *
     * @return
     * @throws ParseException
     */
    public static String getStartMounth(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return sdf.format(calendar.getTime());
    }

    /**
     * 获得当月结束时间
     *
     * @return
     * @throws ParseException
     */
    public static String getEndMounth(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(calendar.DAY_OF_MONTH));
        return sdf.format(calendar.getTime());
    }

    /**
     * 获得当月起始时间
     *
     * @return
     * @throws ParseException
     */
    public static String getStartLastMounth(String dateStr)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return sdf.format(calendar.getTime());
    }

    /**
     * 获得当月结束时间
     *
     * @return
     * @throws ParseException
     */
    public static String getEndLastMounth(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(calendar.DAY_OF_MONTH));
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取当前季度 起始时间
     *
     * @return
     * @throws ParseException
     */
    public static String getStartQuarter(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                calendar.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                calendar.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                calendar.set(Calendar.MONTH, 4);
            else if (currentMonth >= 10 && currentMonth <= 12)
                calendar.set(Calendar.MONTH, 9);
        } catch (Exception e) {
            e.printStackTrace();
        }
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取当季的结束时间
     *
     * @throws ParseException
     */
    public static String getEndQuarter(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int currentMonth = calendar.get(calendar.MONTH) + 1;
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                calendar.set(Calendar.MONTH, 2);
                calendar.set(Calendar.DATE, 29);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                calendar.set(Calendar.MONTH, 5);
                calendar.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                calendar.set(Calendar.MONTH, 8);
                calendar.set(Calendar.DATE, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                calendar.set(Calendar.MONTH, 11);
                calendar.set(Calendar.DATE, 31);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取当年起始时间
     *
     * @throws ParseException
     */
    public static String getStartYear(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        try {
            calendar.set(Calendar.MONTH, 0);
            calendar.set(Calendar.DAY_OF_MONTH,
                    calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        } catch (Exception e) {
            e.printStackTrace();
        }
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取当年结束时间
     *
     * @throws ParseException
     */
    public static String getEndYear(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        try {
            calendar.set(Calendar.MONTH, 11);
            calendar.set(Calendar.DAY_OF_MONTH,
                    calendar.getMaximum(Calendar.DAY_OF_MONTH));
        } catch (Exception e) {
            e.printStackTrace();
        }
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        return sdf.format(calendar.getTime());

    }

    public static String DateConvert(String dateStr){

        //2019 08 29 15 04 26
        String s_nd = dateStr.substring(0, 4); // 年份
        String s_yf = dateStr.substring(4, 6); // 月份
        String s_rq = dateStr.substring(6, 8); // 日期

        String s_hour = dateStr.substring(8, 10);
        String s_minute = dateStr.substring(10, 12);
        String s_second = dateStr.substring(12, 14);

//        Date date = new Date(dateStr);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String aa = simpleDateFormat.format(dateStr);
        return s_nd+"-"+s_yf+"-"+s_rq+" "+s_hour+":"+s_minute+":"+s_second;

    }

}