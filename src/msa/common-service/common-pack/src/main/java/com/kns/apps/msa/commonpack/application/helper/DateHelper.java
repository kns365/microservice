package com.kns.apps.msa.commonpack.application.helper;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateHelper {

    private static final String sDateMilis = "yyyyMMddHHmmssSSS";
    private static final String sDateSeconds = "yyyyMMddHHmmss";

    public DateHelper() {

    }

    public static String getSystemDateMilis() {
        return getDateStr(sDateMilis, null);
    }

    public static String getDateStr(String format, Date date) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = StringUtils.isBlank(format) ? new SimpleDateFormat(sDateSeconds) : new SimpleDateFormat(format);
        return sdf.format(date != null ? date.getTime() : calendar.getTime());
    }

    public static Date getSystemDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }


    public static Date readDateByFormat(String formatTypeInput, String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatTypeInput);
        Date output = null;
        try {
            output = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output;
    }

    public static Date writeDateByFormat(String formatTypeInput, String dateStr, String formatTypeOutput) {//chua test
        SimpleDateFormat sdf = new SimpleDateFormat(formatTypeInput);
        Date output = null;
        try {
            output = sdf.parse(sdf.format(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output;
    }

    public static String convertDateStr(String formatType, String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatType);
        return sdf.format(dateStr);
    }

    public static Date getPastDate(int num) {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -num);
        return cal.getTime();
    }

    public static Date getMinDay(String formatTypeInput, String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatTypeInput);
        Date output = null;
        try {
            Date temp = sdf.parse(dateStr);
            output = getMinDay(temp);
        } catch (Exception ignore) {
        }
        return output;
    }

    public static Date getMaxDay(String formatTypeInput, String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatTypeInput);
        Date output = null;
        try {
            Date temp = sdf.parse(dateStr);
            output = getMaxDay(temp);
        } catch (Exception ignore) {
        }
        return output;
    }

    public static Date getMinDay(Date input) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(input);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date minDay = cal.getTime();
        return minDay;
    }

    public static Date getMaxDay(Date input) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(input);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        Date maxDay = cal.getTime();
        return maxDay;
    }

    public static Date getMinDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date minDay = cal.getTime();
        return minDay;
    }

    public static Date getMaxDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        Date maxDay = cal.getTime();
        return maxDay;
    }

    public static Date toDate(int year, int month, int date, long time) {
        int hour = (int) TimeUnit.MILLISECONDS.toHours(time) % 24;
        int minute = (int) TimeUnit.MILLISECONDS.toMinutes(time) % 60;
        int second = (int) TimeUnit.MILLISECONDS.toSeconds(time) % 60;
        int milli = (int) TimeUnit.MILLISECONDS.toMillis(time);
        return toDate(year, month, date, hour, minute, second, milli);
    }

    public static Date toDate(int year, int month, int date) {
        return toDate(year, month, date, 0);
    }

    public static Date toDate(int year, int month, int date, int hour) {
        return toDate(year, month, date, hour, 0);
    }

    public static Date toDate(int year, int month, int date, int hour, int minute) {
        return toDate(year, month, date, hour, minute, 0);
    }

    public static Date toDate(int year, int month, int date, int hour, int minute, int second) {
        return toDate(year, month, date, hour, minute, second, 0);
    }

    public static Date toDate(int year, int month, int date, int hour, int minute, int second, int milli) {
        return toDate(LocalDateTime.of(year, month, date, hour, minute, second, milli));
    }

    public static Date toDate(LocalDateTime timestamp) {
        return Date.from(timestamp.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static long getDuration(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}
