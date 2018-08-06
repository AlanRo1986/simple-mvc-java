package com.lanxinbase.system.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by alan.luo on 2017/8/4.
 */
public class DateUtils {
    public static final String YEAR = "yyyy";
    public static final String MONTH = "MM";
    public static final String DAY = "dd";
    public static final String FULL_YEAR_MONTH = "yyyy-MM";
    public static final String FULL_DATE = "yyyy-MM-dd";
    public static final String FULL_DATE_Q = "yyyyMMdd";
    public static final String FULL_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FULL_DATE_TIME_Q = "yyyyMMddHHmmss";
    public static final String WEEK = "week";

    private static SimpleDateFormat sdf;

    public static long getTime(){
        return System.currentTimeMillis();
    }

    public static int getTimeInt(){
        return (int)(System.currentTimeMillis()/1000);
    }

    public static Date getDate() {
        return new Date();
    }

    public static String getFullDate(Date date){
        if (date == null){
            date = getDate();
        }
        return format(date,FULL_DATE);
    }

    public static String getFullDate(long time){
        if (time <= 0){
            return "";
        }
        return format(new Date(time),FULL_DATE);
    }

    public static String getFullDateQ(Date date){
        if (date == null){
            date = getDate();
        }
        return format(date,FULL_DATE_Q);
    }

    public static String getFullDateQ(long time){
        if (time <= 0){
            return "";
        }
        return format(new Date(time),FULL_DATE_Q);
    }

    public static String getFullDateTime(Date date) {
        if (date == null){
            date = getDate();
        }
        return format(date,FULL_DATE_TIME);
    }

    public static String getFullDateTimeQ(Date date) {
        if (date == null){
            date = getDate();
        }
        return format(date,FULL_DATE_TIME_Q);
    }

    public static String getFullDateTime(long time){
        if (time <= 0){
            return "";
        }
        return format(new Date(time),FULL_DATE_TIME);
    }

    public static String getFullDateTimeQ(long time){
        if (time <= 0){
            return "";
        }
        return format(new Date(time),FULL_DATE_TIME_Q);
    }

    public static int get(int type){
        Calendar calendar = new GregorianCalendar();
        return calendar.get(type);
    }

    /**
     * 增减日期
     * @param date
     * @param less 要减去或者增加的数量,加使用整数
     * @param lessType 0.day,1.month,2.year
     * @return
     */
    public static Date dateLess(Date date, int less, int lessType){
        if (date == null){
            date = getDate();
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        switch (lessType){
            case 0:
                calendar.add(Calendar.DATE,less);
                break;
            case 1:
                calendar.add(Calendar.MONTH,less);
                break;
            case 2:
                calendar.add(Calendar.YEAR,less);
                break;
        }

        return calendar.getTime();
    }


    /**
     * 日期时间增减
     * @param dateStr
     * @param less 要减去或者增加的数量,加使用整数
     * @param lessType 0.day,1.month,2.year
     * @return
     */
    public static String dateLessForString(String dateStr,int less,int lessType){
        Date date = strToDate(dateStr,FULL_DATE);
        return getFullDate(dateLess(date,less,lessType));
    }

    public static String dateLessToString(Date date,int less,int lessType,String patten){
        if (patten == null){
            return getFullDate(dateLess(date,less,lessType));
        }else{
            return format(dateLess(date,less,lessType),patten);
        }
    }

    /**
     * 字符串转成Date对象
     * @param dateStr
     * @param pattern 字符串的格式
     * @return
     */
    public static Date strToDate(String dateStr,String pattern){
        if (pattern == null){
            pattern = FULL_DATE;
        }

        SimpleDateFormat sdf = new SimpleDateFormat (pattern);

        try {

            return sdf.parse(dateStr);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * format date time.
     * @param date
     * @param formats
     * @return
     */
    public static String format(Date date, String formats){
        Calendar calendar = new GregorianCalendar();
        TimeZone timeZone = calendar.getTimeZone();
        sdf = new SimpleDateFormat(formats);
        sdf.setTimeZone(timeZone);
        return sdf.format(date);
    }
}
