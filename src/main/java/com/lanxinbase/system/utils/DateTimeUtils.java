package com.lanxinbase.system.utils;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

/**
 * Created by alan on 2019/4/19.
 * <p>
 * ********************************
 * getTime():1555776871585
 * getTimeInt():1555776871
 * getLocalDate():2019-04-21
 * parseLocalDate("2019-04-20",1):2019-04-20
 * parseLocalDate(2019,04,20):2019-04-20
 * plus(getLocalDate(),2,ChronoUnit.DAYS):2019-04-23
 * --------------------------------
 * getLocalTime():00:14:31.585
 * parseLocalTime("23:56:00"):23:56
 * parseLocalTime(23,56,12):23:56:12
 * plus(getLocalTime(),-3,ChronoUnit.HOURS):21:14:31.585
 * --------------------------------
 * getLocalDateTime():2019-04-21T00:14:31.585
 * parseLocalDateTime("2019-04-20 21:05:56",1):2019-04-20T21:05:56
 * parseLocalDateTime(2019,4,20,21,05,56):2019-04-20T21:05:56
 * plus(getLocalDateTime(),-1,ChronoUnit.MONTHS):2019-03-21T00:14:31.585
 * --------------------------------
 * format(int t):2019-04-21T00:14:31
 * format(long t):2019-04-21T00:14:31.585
 * format(int t,1):2019-4-21 0:14:31
 * format(long t,1):2019-4-21 0:14:31
 * format(LocalDate d,1):2019-4-21
 * format(LocalTime t):0:14:31
 * format(LocalDateTime t,1):2019-4-21 0:14:31
 * format(ZonedDateTime.now(),1):2019-4-21 0:14:31
 * format(LocalDate d,DateTimeFormatter.ISO_DATE):2019-04-21
 * format(LocalTime t,DateTimeFormatter.ISO_TIME):00:14:31.585
 * format(LocalDateTime t,DateTimeFormatter.ISO_DATE_TIME):2019-04-21T00:14:31.585
 * format(ZonedDateTime t,DateTimeFormatter.ISO_DATE_TIME):2019-04-21T00:14:31.585+08:00[Asia/Shanghai]
 * format(LocalDateTime t,DATE_TIME_FULL):2019-04-21 00:14:31
 * format(int i,DATE_TIME_FULL):2019-04-21 00:14:31
 * format(long l,DATE_TIME_FULL):2019-04-21 00:14:31
 * --------------------------------
 * getYear():2019
 * getMonthI():4
 * getMonth():APRIL
 * getMonthS():四月
 * getDayOfMonth():21
 * getDayOfYear():111
 * getDayOfMonthMax():30
 * getWeek():星期日
 * getWeekI():7
 * getDate():Sun Apr 21 00:14:31 CST 2019
 * getTimestamp():2019-04-21 00:14:31.587
 * getTimestampL():2019-04-21 00:14:31.587
 * getHour():0
 * getMinute():14
 * getSecond():31
 */
public class DateTimeUtils extends OutPut {

    public static final String YEAR = "yyyy";
    public static final String MONTH = "MM";
    public static final String DAY = "dd";
    public static final String DATE_FULL = "yyyy-MM-dd";
    public static final String DATE_FULL2 = "yyyyMMdd";
    public static final String DATE_TIME_FULL = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_FULL2 = "yyyyMMddHHmmss";
    public static final String WEEK = "EEEE";

    public static final int FORMAT_EX0 = -1;
    public static final int FORMAT_EX = 1;
    public static final int FORMAT_EX2 = 2;

    private static final int ZONE_HOUR = 8;

    public DateTimeUtils() {

    }

    /**
     * 取时间戳
     *
     * @return long
     */
    public static long getTime() {
        return System.currentTimeMillis();
    }

    /**
     * 取时间戳
     *
     * @return int
     */
    public static int getTimeInt() {
        return (int) (getTime() / 1000);
    }

    /**
     * 取本地日期对象
     *
     * @return LocalDate
     */
    public static LocalDate getLocalDate() {
        return LocalDate.now();
    }

    /**
     * 字符串日期转本地日期对象
     *
     * @param str    2012/01/01|2012-01-01
     * @param format 1|2
     * @return LocalDate
     */
    public static LocalDate parseLocalDate(String str, int format) {
        /**
         * format:1=-,2=/
         */
        String regx = "/";
        if (1 == format) {
            regx = "-";
        }
        String[] s = str.split(regx);
        return parseLocalDate(Integer.valueOf(s[0]), Integer.valueOf(s[1]), Integer.valueOf(s[2]));
    }

    /**
     * 整数日期转本地日期对象
     *
     * @param y 年
     * @param m 月
     * @param d 日
     * @return LocalDate
     */
    public static LocalDate parseLocalDate(int y, int m, int d) {
        return LocalDate.of(y, m, d);
    }

    /**
     * 本地日期增减
     *
     * @param date 本地日期
     * @param n    +正数则增加；-负数则减去
     * @param unit ChronoUnit.DAYS|ChronoUnit.YEARS|ChronoUnit.MONTHS
     * @return LocalDate
     */
    public static LocalDate plus(LocalDate date, int n, ChronoUnit unit) {
        return date.plus(n, unit);
    }

    /**
     * 取本地时间对象
     *
     * @return LocalTime
     */
    public static LocalTime getLocalTime() {
        return LocalTime.now();
    }

    /**
     * 本地时间增减
     *
     * @param time 本地时间
     * @param n    +正数则增加；-负数则减去
     * @param unit ChronoUnit.HOURS|ChronoUnit.MINUTES|ChronoUnit.SECONDS
     * @return LocalTime
     */
    public static LocalTime plus(LocalTime time, int n, ChronoUnit unit) {
        return time.plus(n, unit);
    }

    /**
     * 字符串时间转本地时间对象
     *
     * @param time 00:00:00
     * @return LocalTime
     */
    public static LocalTime parseLocalTime(String time) {
        String[] s = time.split(":");
        return parseLocalTime(Integer.valueOf(s[0]), Integer.valueOf(s[1]), Integer.valueOf(s[2]));
    }

    /**
     * 整数时间转本地时间对象
     *
     * @param h 小时
     * @param m 分钟
     * @param s 秒
     * @return LocalTime
     */
    public static LocalTime parseLocalTime(int h, int m, int s) {
        return LocalTime.of(h, m, s);
    }

    /**
     * 获取本地日期时间对象
     *
     * @return
     */
    public static LocalDateTime getLocalDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 本地日期时间增减
     *
     * @param time 本地日期时间
     * @param n    +正数则增加；-负数则减去
     * @param unit ChronoUnit.DAYS|ChronoUnit.YEARS|ChronoUnit.MONTHS|ChronoUnit.HOURS|ChronoUnit.MINUTES|ChronoUnit.SECONDS
     * @return LocalDateTime
     */
    public static LocalDateTime plus(LocalDateTime time, int n, ChronoUnit unit) {
        return time.plus(n, unit);
    }

    /**
     * 本地日期增减
     *
     * @param time 本地时间
     * @param n    +正数则增加；-负数则减去
     * @param unit ChronoUnit.DAYS|ChronoUnit.YEARS|ChronoUnit.MONTHS
     * @return LocalDate
     */
    public static LocalDate plusDate(LocalDate time, int n, ChronoUnit unit) {
        return time.plus(n, unit);
    }

    /**
     * 本地时间增减
     *
     * @param time 本地时间
     * @param n    +正数则增加；-负数则减去
     * @param unit ChronoUnit.HOURS|ChronoUnit.MINUTES|ChronoUnit.SECONDS
     * @return LocalTime
     */
    public static LocalTime plusTime(LocalTime time, int n, ChronoUnit unit) {
        return time.plus(n, unit);
    }

    /**
     * 字符串日期时间转本地日期时间对象
     *
     * @param datetime string like it:2019-01-01 00:00:00 other is 2019/01/01 00:00:00
     * @param format   if == 1 then 2019-01-01 00:00:00 other is 2019/01/01 00:00:00
     * @return null|LocalDateTime
     */
    public static LocalDateTime parseLocalDateTime(String datetime, int format) {
        String[] x = datetime.split(" ");
        if (x.length != 2) {
            return null;
        }
        String regx = "/";
        if (1 == format) {
            regx = "-";
        }
        String[] d = x[0].split(regx);
        String[] s = x[1].split(":");
        return parseLocalDateTime(Integer.valueOf(d[0]), Integer.valueOf(d[1]), Integer.valueOf(d[2]),
                Integer.valueOf(s[0]), Integer.valueOf(s[1]), Integer.valueOf(s[2]));
    }

    /**
     * 整数日期时间转本地日期时间对象
     *
     * @param y 年
     * @param m 月
     * @param d 日
     * @param h 小时
     * @param i 分钟
     * @param s 秒
     * @return LocalDateTime
     */
    public static LocalDateTime parseLocalDateTime(int y, int m, int d, int h, int i, int s) {
        return LocalDateTime.of(y, m, d, h, i, s);
    }

    /**
     * 格式化当前时间
     *
     * @return
     */
    public static String format() {
        return format(getTime(), 1);
    }

    /**
     * 秒时间戳转本地日期时间对象
     *
     * @param datetime 秒时间戳
     * @return LocalDateTime
     */
    public static LocalDateTime format(int datetime) {
        return format((long) datetime * 1000L);
    }

    /**
     * 微秒时间戳转本地日期时间对象
     *
     * @param datetime 微秒时间戳
     * @return LocalDateTime
     */
    public static LocalDateTime format(long datetime) {
        LocalDateTime localDateTime = Instant.ofEpochMilli(datetime).atZone(ZoneOffset.systemDefault()).toLocalDateTime();
        return localDateTime;
    }

    /**
     * 秒时间戳格式化字符串文本
     *
     * @param datetime 秒时间戳
     * @param format   格式化字符串
     * @return String
     */
    public static String format(int datetime, int format) {
        return format((long) datetime * 1000L, format);
    }

    /**
     * 微秒时间戳格式化字符串文本
     *
     * @param datetime 微秒时间戳
     * @param format   格式化字符串
     * @return String
     */
    public static String format(long datetime, int format) {
        return format(format(datetime), format);
    }

    /**
     * 本地日期对象格式化文本
     *
     * @param date   本地日期对象
     * @param format 1~2
     * @return String
     */
    public static String format(LocalDate date, int format) {
        String regx = "/";
        if (FORMAT_EX == format) {
            regx = "-";
        } else if (FORMAT_EX0 == format) {
            regx = "";
        }
        StringBuffer st = new StringBuffer();
        st.append(date.getYear()).append(regx)
                .append(date.getMonthValue()).append(regx)
                .append(date.getDayOfMonth());
        return st.toString();
    }


    /**
     * 本地时间对象格式化文本
     *
     * @param time 本地时间对象
     * @return String
     */
    public static String format(LocalTime time) {
        StringBuffer st = new StringBuffer();
        st.append(time.getHour()).append(":")
                .append(time.getMinute()).append(":")
                .append(time.getSecond());
        return st.toString();
    }

    /**
     * 本地日期时间对象格式化文本
     *
     * @param dateTime 本地日期时间对象
     * @param format   1~2
     * @return String
     */
    public static String format(LocalDateTime dateTime, int format) {
        String regx = "/";
        if (1 == format) {
            regx = "-";
        }
        StringBuffer st = new StringBuffer();
        st.append(dateTime.getYear()).append(regx)
                .append(dateTime.getMonthValue()).append(regx)
                .append(dateTime.getDayOfMonth()).append(" ")
                .append(dateTime.getHour()).append(":")
                .append(dateTime.getMinute()).append(":")
                .append(dateTime.getSecond());
        return st.toString();
    }

    /**
     * 时区时间格式化文本
     *
     * @param dateTime 时区时间对象
     * @param format   1~2
     * @return String
     */
    public static String format(ZonedDateTime dateTime, int format) {
        String regx = "/";
        if (1 == format) {
            regx = "-";
        }
        StringBuffer st = new StringBuffer();
        st.append(dateTime.getYear()).append(regx)
                .append(dateTime.getMonthValue()).append(regx)
                .append(dateTime.getDayOfMonth()).append(" ")
                .append(dateTime.getHour()).append(":")
                .append(dateTime.getMinute()).append(":")
                .append(dateTime.getSecond());
        return st.toString();
    }

    /**
     * 格式化本地日期
     *
     * @param date   本地日期对象
     * @param format 日期时间格式化对象:DateTimeFormatter.ISO_DATE
     * @return String
     */
    public static String format(LocalDate date, DateTimeFormatter format) {
        return format.format(date);
    }

    /**
     * 格式化本地时间
     *
     * @param time   本地时间对象
     * @param format 日期时间格式化对象:DateTimeFormatter.ISO_TIME
     * @return String
     */
    public static String format(LocalTime time, DateTimeFormatter format) {
        return format.format(time);
    }

    /**
     * 格式化本地日期时间
     *
     * @param dateTime 本地日期时间对象
     * @param format   日期时间格式化对象:DateTimeFormatter.ISO_LOCAL_DATE_TIME|ISO_DATE_TIME
     * @return String
     */
    public static String format(LocalDateTime dateTime, DateTimeFormatter format) {
        return format.format(dateTime);
    }

    /**
     * 格式化时区日期时间
     *
     * @param dateTime 时区日期时间对象
     * @param format   日期格式化对象:DateTimeFormatter.ISO_ZONED_DATE_TIME|ISO_DATE_TIME
     * @return String
     */
    public static String format(ZonedDateTime dateTime, DateTimeFormatter format) {
        return format.format(dateTime);
    }

    /**
     * 取Date对象
     *
     * @return Date
     */
    public static Date getDate() {
        return Date.from(Instant.now());
    }

    /**
     * 取Timestamp对象
     *
     * @return Timestamp
     */
    public static Timestamp getTimestamp() {
        return Timestamp.from(Instant.now());
    }

    /**
     * 取Timestamp对象
     *
     * @return Timestamp
     */
    public static Timestamp getTimestampL() {
        return Timestamp.valueOf(getLocalDateTime());
    }

    /**
     * 获取星期
     *
     * @return like 星期六|?
     */
    public static String getWeek() {
        return getLocalDate().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
    }

    /**
     * 获取星期
     *
     * @return like 1~7
     */
    public static int getWeekI() {
        return getLocalDate().getDayOfWeek().getValue();
    }

    /**
     * 获取当天日（按月）
     *
     * @return int
     */
    public static int getDayOfMonth() {
        return getLocalDate().getDayOfMonth();
    }

    /**
     * 获取当天日（按年）
     *
     * @return int
     */
    public static int getDayOfYear() {
        return getLocalDate().getDayOfYear();
    }

    /**
     * 获取当月最后一天
     *
     * @return int
     */
    public static int getDayOfMonthMax() {
        return getMonth().maxLength();
    }

    /**
     * 获取Month对象
     *
     * @return Month
     */
    public static Month getMonth() {
        return getLocalDate().getMonth();
    }

    /**
     * 获取月份（当地格式）
     *
     * @return String|六月
     */
    public static String getMonthS() {
        return getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
    }

    /**
     * 获取月份
     *
     * @return int
     */
    public static int getMonthI() {
        return getMonth().getValue();
    }

    /**
     * 取年份
     *
     * @return int|2019
     */
    public static int getYear() {
        return getLocalDate().getYear();
    }

    /**
     * 取小时
     *
     * @return int|0~23
     */
    public static int getHour() {
        return getLocalTime().getHour();
    }

    /**
     * 取分钟
     *
     * @return int|0~59
     */
    public static int getMinute() {
        return getLocalTime().getMinute();
    }

    /**
     * 取秒
     *
     * @return int|0~59
     */
    public static int getSecond() {
        return getLocalTime().getSecond();
    }

    /**
     * 格式化本地日期时间对象
     *
     * @param dateTime 本地日期时间对象
     * @param pattern  public static final String YEAR = "yyyy";
     *                 public static final String MONTH = "MM";
     *                 public static final String DAY = "dd";
     *                 public static final String FULL_DATE = "yyyy-MM-dd";
     *                 public static final String FULL_DATE_Q = "yyyyMMdd";
     *                 public static final String FULL_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
     *                 public static final String FULL_DATE_TIME_Q = "yyyyMMddHHmmss";
     *                 public static final String WEEK = "EEEE";
     * @return String
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(Date.from(dateTime.atZone(ZoneOffset.systemDefault()).toInstant()));
    }

    /**
     * 格式化日期时间（整数）
     *
     * @param dateTime 时间戳
     * @param pattern  格式化文本
     * @return String
     */
    public static String format(int dateTime, String pattern) {
        return format((long) dateTime * 1000L, pattern);
    }

    /**
     * 格式化日期时间（长整数）
     *
     * @param dateTime 微秒时间戳
     * @param pattern  格式化文本
     * @return String
     */
    public static String format(long dateTime, String pattern) {
        return format(format(dateTime), pattern);
    }

    public static void main(String[] args) {
        out("********************************");
        out("getTime():" + getTime());
        out("getTimeInt():" + getTimeInt());
        out("getLocalDate():" + getLocalDate());
        out("parseLocalDate(\"2019-04-20\",1):" + parseLocalDate("2019-04-20", 1));
        out("parseLocalDate(2019,04,20):" + parseLocalDate(2019, 04, 20));
        out("plus(getLocalDate(),2,ChronoUnit.DAYS):" + plus(getLocalDate(), 2, ChronoUnit.DAYS));
        out("--------------------------------");
        out("getLocalTime():" + getLocalTime());
        out("parseLocalTime(\"23:56:00\"):" + parseLocalTime("23:56:00"));
        out("parseLocalTime(23,56,12):" + parseLocalTime(23, 56, 12));
        out("plus(getLocalTime(),-3,ChronoUnit.HOURS):" + plus(getLocalTime(), -3, ChronoUnit.HOURS));
        out("--------------------------------");
        out("getLocalDateTime():" + getLocalDateTime());
        out("parseLocalDateTime(\"2019-04-20 21:05:56\",1):" + parseLocalDateTime("2019-04-20 21:05:56", 1));
        out("parseLocalDateTime(2019,4,20,21,05,56):" + parseLocalDateTime(2019, 4, 20, 21, 05, 56));
        out("plus(getLocalDateTime(),-1,ChronoUnit.MONTHS):" + plus(getLocalDateTime(), -1, ChronoUnit.MONTHS));
        out("--------------------------------");
        out("format(int t):" + format(getTimeInt()));
        out("format(long t):" + format(getTime()));
        out("format(int t,1):" + format(getTimeInt(), 1));
        out("format(long t,1):" + format(getTime(), 1));
        out("format(LocalDate d,1):" + format(getLocalDate(), 1));
        out("format(LocalTime t):" + format(getLocalTime()));
        out("format(LocalDateTime t,1):" + format(getLocalDateTime(), 1));
        out("format(ZonedDateTime.now(),1):" + format(ZonedDateTime.now(), 1));
        out("format(LocalDate d,DateTimeFormatter.ISO_DATE):" + format(getLocalDate(), DateTimeFormatter.ISO_DATE));
        out("format(LocalTime t,DateTimeFormatter.ISO_TIME):" + format(getLocalTime(), DateTimeFormatter.ISO_TIME));
        out("format(LocalDateTime t,DateTimeFormatter.ISO_DATE_TIME):" + format(getLocalDateTime(), DateTimeFormatter.ISO_DATE_TIME));
        out("format(ZonedDateTime t,DateTimeFormatter.ISO_DATE_TIME):" + format(ZonedDateTime.now(), DateTimeFormatter.ISO_DATE_TIME));
        out("format(LocalDateTime t,DATE_TIME_FULL):" + format(getLocalDateTime(), DATE_TIME_FULL));
        out("format(int i,DATE_TIME_FULL):" + format(getTimeInt(), DATE_TIME_FULL));
        out("format(long l,DATE_TIME_FULL):" + format(getTime(), DATE_TIME_FULL));
        out("--------------------------------");
        out("getYear():" + getYear());
        out("getMonthI():" + getMonthI());
        out("getMonth():" + getMonth());
        out("getMonthS():" + getMonthS());
        out("getDayOfMonth():" + getDayOfMonth());
        out("getDayOfYear():" + getDayOfYear());
        out("getDayOfMonthMax():" + getDayOfMonthMax());
        out("getWeek():" + getWeek());
        out("getWeekI():" + getWeekI());
        out("getDate():" + getDate());
        out("getTimestamp():" + getTimestamp());
        out("getTimestampL():" + getTimestampL());
        out("getHour():" + getHour());
        out("getMinute():" + getMinute());
        out("getSecond():" + getSecond());


    }
}
