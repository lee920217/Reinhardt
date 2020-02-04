package uk.co.cnoversea.common.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*
 * 转换关系
 *         SimpleDateForma.format      getTime          getTimeInMillis
 * String  <===================== Date <====== Calendar ==============> MillionSecond
 *         =====================>      ======>          <==============
 *         SimpleDateForma.parse       setTime          setTimeInMillis
 *
 *       +ZONE_OFFSET,+DST_OFFSET          -ZONE_OFFSET,-DST_OFFSET
 * LOCAL <======================= Calendar =======================> UTC
 */

public class DateConvertor {

    public static final String standDateFormatExp = "yyyy-MM-dd HH:mm:ss";

    public static final ThreadLocal<DateFormat> standDateFormat = new ThreadLocal<DateFormat>(){
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DateConvertor.standDateFormatExp);
        }
    };

    public static Date currentDate() {
        return Calendar.getInstance().getTime();
    }

    public static long currentMillis() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static String currentString() {
        return currentString(DateConvertor.standDateFormatExp);
    }

    public static String currentString(DateFormat dateFormat) {
        return toString(Calendar.getInstance().getTime(), dateFormat);
    }

    public static String formatTimeWithInterval(long tm, int interval, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(tm);
        int min = calendar.get(Calendar.MINUTE);
        min = (min / interval) * interval;
        calendar.set(Calendar.MINUTE, min);

        return new SimpleDateFormat(format).format(calendar.getTime());
    }

    public static String currentString(String dateFormatExp) {
        return toString(Calendar.getInstance().getTime(), dateFormatExp);
    }

//    public static Calendar currentUTCCalendar() {
//        // 1、取得本地时间：
//        Calendar calendar = Calendar.getInstance();
//        // 2、取得时间偏移量：
//        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
//        // 3、取得夏令时差：
//        int dstOffset = calendar.get(Calendar.DST_OFFSET);
//        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
//        calendar.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
//        return calendar;
//    }

    public static Date toDate(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    public static Date toDate(String dateString) throws ParseException {
        return toDate(dateString, DateConvertor.standDateFormat.get());
    }

    public static Date toDate(String dateString, DateFormat dateFormat) throws ParseException {
        return dateFormat.parse(dateString);
    }

    public static Date toDate(String dateString, String dateFormatExp) throws ParseException {
        DateFormat formator = new SimpleDateFormat(dateFormatExp);
        return toDate(dateString, formator);
    }

    public static long toMillis(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    public static long toMillis(String dateString) throws ParseException {
        Date date = toDate(dateString);
        return toMillis(date);
    }

    public static long toMillis(String dateString, DateFormat dateFormat) throws ParseException {
        Date date = toDate(dateString, dateFormat);
        return toMillis(date);
    }

    public static long toMillis(String dateString, String dateFormatExp) throws ParseException {
        Date date = toDate(dateString, dateFormatExp);
        return toMillis(date);
    }

    public static String toString(Date date) {
        return toString(date, DateConvertor.standDateFormat.get());
    }

    public static String toString(Date date, DateFormat dateFormat) {
        return dateFormat.format(date);
    }

    public static String toString(Date date, String dateFormatExp) {
        DateFormat formator = new SimpleDateFormat(dateFormatExp);
        return toString(date, formator);
    }

    public static String toString(long millis) {
        Date date = toDate(millis);
        return toString(date);
    }

    public static String toString(long millis, DateFormat dateFormat) {
        Date date = toDate(millis);
        return toString(date, dateFormat);
    }

    public static String toString(long millis, String dateFormatExp) {
        Date date = toDate(millis);
        return toString(date, dateFormatExp);
    }

    public static String toString(String dateString, DateFormat dateFormatFrom, DateFormat dateFormatTo)
            throws ParseException {
        Date dateFrom = toDate(dateString, dateFormatFrom);
        return toString(dateFrom, dateFormatTo);
    }

    public static String toString(String dateString, String dateFormatExpFrom, String dateFormatExpTo)
            throws ParseException {
        DateFormat formatorFrom = new SimpleDateFormat(dateFormatExpFrom);
        DateFormat formatorTo = new SimpleDateFormat(dateFormatExpTo);
        return toString(dateString, formatorFrom, formatorTo);
    }


}
