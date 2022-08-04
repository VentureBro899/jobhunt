package venture.dev.venturejobhunt.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtils {
    public static final String DAFAULT_DATE_FORMAT = "yyyy-M-d";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DAFAULT_DATETIME_FORMAT = "yyyy-M-d HH:mm:ss";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DAFAULT_TIME_FORMAT = "HH:mm:ss";

    /**
     * 默认构造方法
     */
    private DateUtils() {

    }

    /**
     * 当前时间
     *
     * @return date
     */
    public static Date currentDate() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 得到年
     *
     * @param c
     * @return
     */
    public static int getYear(Calendar c) {
        if (c != null) {
            return c.get(Calendar.YEAR);
        } else {
            return Calendar.getInstance().get(Calendar.YEAR);
        }
    }

    /**
     * 得到月份
     * 注意，这里的月份依然是从0开始的
     *
     * @param c
     * @return
     */
    public static int getMonth(Calendar c) {
        if (c != null) {
            return c.get(Calendar.MONTH) + 1;
        } else {
            return Calendar.getInstance().get(Calendar.MONTH) + 1;
        }
    }

    /**
     * 得到日期
     *
     * @param c
     * @return
     */
    public static int getDate(Calendar c) {
        if (c != null) {
            return c.get(Calendar.DATE);
        } else {
            return Calendar.getInstance().get(Calendar.DATE);
        }
    }

    /**
     * 得到星期
     *
     * @param c
     * @return
     */
    public static int getDay(Calendar c) {
        if (c != null) {
            return c.get(Calendar.DAY_OF_WEEK);
        } else {
            return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        }
    }

    /**
     * 得到小时
     *
     * @param c
     * @return
     */
    public static int getHour(Calendar c) {
        if (c != null) {
            return c.get(Calendar.HOUR_OF_DAY);
        } else {
            return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        }
    }

    /**
     * 得到分钟
     *
     * @param c
     * @return
     */
    public static int getMinute(Calendar c) {
        if (c != null) {
            return c.get(Calendar.MINUTE);
        } else {
            return Calendar.getInstance().get(Calendar.MINUTE);
        }
    }

    /**
     * 得到秒
     *
     * @param c
     * @return
     */
    public static int getSecond(Calendar c) {
        if (c != null) {
            return c.get(Calendar.SECOND);
        } else {
            return Calendar.getInstance().get(Calendar.SECOND);
        }
    }

    /**
     * 得到指定或者当前时间前n天的Calendar
     *
     * @param c
     * @param n
     * @return
     */
    public static Calendar beforeNDays(Calendar c, int n) {
        //偏移量，给定n天的毫秒数
        long offset = n * 24 * 60 * 60 * 1000;
        Calendar calendar = null;
        if (c != null) {
            calendar = c;
        } else {
            calendar = Calendar.getInstance();
        }

        calendar.setTimeInMillis(calendar.getTimeInMillis() - offset);
        return calendar;
    }

    /**
     * 得到指定或者当前时间后n天的Calendar
     *
     * @param c
     * @param n
     * @return
     */
    public static Calendar afterNDays(Calendar c, int n) {
        //偏移量，给定n天的毫秒数
        long offset = n * 24 * 60 * 60 * 1000;
        Calendar calendar = null;
        if (c != null) {
            calendar = c;
        } else {
            calendar = Calendar.getInstance();
        }

        calendar.setTimeInMillis(calendar.getTimeInMillis() + offset);
        return calendar;
    }

    /**
     * 指定日期加月份
     *
     * @param date      指定日期
     * @param addMonths 要加的月份数
     * @return Date 计算后的日期
     */
    public static Date addMonths(Date date, int addMonths) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, addMonths);
        return calendar.getTime();
    }

    /**
     * 当前日期加月份
     *
     * @param addMonths 要加的月份数
     * @return Date 计算后的日期
     */
    public static Date addMonths(int addMonths) {
        return addMonths(new Date(), addMonths);
    }

    /**
     * 指定日期加天
     *
     * @param date    指定日期
     * @param addDays 要加的天数
     * @return Date 计算后的日期
     */
    public static Date addDays(Date date, int addDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, addDays);
        return calendar.getTime();
    }

    /**
     * 当前日期加天
     *
     * @param addDays 要加的天数
     * @return Date 计算后的日期
     */
    public static Date addDays(int addDays) {
        return addDays(new Date(), addDays);
    }

    /**
     * 指定日期加小时
     *
     * @param date     指定日期
     * @param addHours 要加的小时数
     * @return Date 计算后的日期
     */
    public static Date addHours(Date date, int addHours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, addHours);
        return calendar.getTime();
    }

    /**
     * 当前日期加小时
     *
     * @param addHours 要加的小时数
     * @return Date 计算后的日期
     */
    public static Date addHours(int addHours) {
        return addHours(new Date(), addHours);
    }

    /**
     * 指定日期加分
     *
     * @param date       指定日期
     * @param addMinutes 要加的分数
     * @return Date 计算后的日期
     */
    public static Date addMinutes(Date date, int addMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, addMinutes);
        return calendar.getTime();
    }

    /**
     * 当前日期加分
     *
     * @param addMinutes 要加的分数
     * @return Date 计算后的日期
     */
    public static Date addMinutes(int addMinutes) {
        return addMinutes(new Date(), addMinutes);
    }

    /**
     * 指定日期加秒
     *
     * @param date       指定日期
     * @param addSeconds 要加的秒数
     * @return Date 计算后的日期
     */
    public static Date addSeconds(Date date, int addSeconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, addSeconds);
        return calendar.getTime();
    }

    /**
     * 当前日期加秒
     *
     * @param addSeconds 要加的秒数
     * @return Date 计算后的日期
     */
    public static Date addSeconds(int addSeconds) {
        return addSeconds(new Date(), addSeconds);
    }

    /**
     * 昨天
     *
     * @param c
     * @return
     */
    public static Calendar yesterday(Calendar c) {
        long offset = 1 * 24 * 60 * 60 * 1000;
        Calendar calendar = null;
        if (c != null) {
            calendar = c;
        } else {
            calendar = Calendar.getInstance();
        }
        calendar.setTimeInMillis(calendar.getTimeInMillis() - offset);
        return calendar;
    }

    /**
     * 明天
     *
     * @param c
     * @return
     */
    public static Calendar tomorrow(Calendar c) {
        long offset = 1 * 24 * 60 * 60 * 1000;
        Calendar calendar = null;
        if (c != null) {
            calendar = c;
        } else {
            calendar = Calendar.getInstance();
        }

        calendar.setTimeInMillis(calendar.getTimeInMillis() + offset);
        return calendar;
    }

    /**
     * 得到指定或者当前时间前offset毫秒的Calendar
     *
     * @param c
     * @param offset
     * @return
     */
    public static Calendar before(Calendar c, long offset) {
        Calendar calendar = null;
        if (c != null) {
            calendar = c;
        } else {
            calendar = Calendar.getInstance();
        }

        calendar.setTimeInMillis(calendar.getTimeInMillis() - offset);
        return calendar;
    }

    /**
     * 得到指定或者当前时间前offset毫秒的Calendar
     *
     * @param c
     * @param offset
     * @return
     */
    public static Calendar after(Calendar c, long offset) {
        Calendar calendar = null;
        if (c != null) {
            calendar = c;
        } else {
            calendar = Calendar.getInstance();
        }

        calendar.setTimeInMillis(calendar.getTimeInMillis() - offset);
        return calendar;
    }

    /**
     * 日期格式化
     *
     * @param c
     * @param pattern
     * @return
     */
    public static String format(Calendar c, String pattern) {
        Calendar calendar = null;
        if (c != null) {
            calendar = c;
        } else {
            calendar = Calendar.getInstance();
        }
        if (pattern == null || pattern.equals("")) {
            pattern = DATETIME_FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        return sdf.format(calendar.getTime());
    }

    /**
     * 日期格式化
     *
     * @param date    日期
     * @param pattern 格式
     * @return String 格式化字符串
     */
    public static String format(Date date, String pattern) {
        Date tempDate = null;
        if (date != null) {
            tempDate = date;
        } else {
            tempDate = Calendar.getInstance().getTime();
        }
        if (pattern == null || pattern.equals("")) {
            pattern = DATETIME_FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        return sdf.format(tempDate);
    }

    /**
     * Date类型转换到Calendar类型
     *
     * @param d
     * @return
     */
    public static Calendar date2Calendar(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c;
    }

    /**
     * Calendar类型转换到Date类型
     *
     * @param c
     * @return
     */
    public static Date calendar2Date(Calendar c) {
        return c.getTime();
    }

    /**
     * Date类型转换到Timestamp类型
     *
     * @param d
     * @return
     */
    public static Timestamp date2Timestamp(Date d) {
        return new Timestamp(d.getTime());
    }

    /**
     * Calendar类型转换到Timestamp类型
     *
     * @param c
     * @return
     */
    public static Timestamp calendar2Timestamp(Calendar c) {
        return new Timestamp(c.getTimeInMillis());
    }

    /**
     * Timestamp类型转换到Calendar类型
     *
     * @param ts
     * @return
     */
    public static Calendar timestamp2Calendar(Timestamp ts) {
        Calendar c = Calendar.getInstance();
        c.setTime(ts);
        return c;
    }

    /**
     * 得到当前时间的字符串表示
     * 格式2010-02-02 12:12:12
     *
     * @return
     */
    public static String getTimeString() {
        return format(Calendar.getInstance(), DATETIME_FORMAT);
    }

    /**
     * 标准日期格式字符串解析成Calendar对象
     *
     * @param s
     * @return
     */
    public static Calendar pars2Calender(String s) {
        Timestamp ts = Timestamp.valueOf(s);
        return timestamp2Calendar(ts);
    }

}
