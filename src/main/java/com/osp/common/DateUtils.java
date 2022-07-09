package com.osp.common;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Admin on 1/6/2018.
 */
public class DateUtils {

    static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

    /**
     * add days to date
     *
     * @param date
     * @param days
     * @return
     * @author:manhpt
     */
    public static Date addDays(Date date, int days) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);

        return cal.getTime();
    }

    /**
     * subtract days to date
     *
     * @param date
     * @param days
     * @return
     */
    public static Date subtractDays(Date date, int days) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, -days);

        return cal.getTime();
    }

    //Convert String to Date
    public static Date strToDate(String input, String format) throws ParseException {
        Date result = null;
        if (!input.isEmpty()) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat(format);
                result = formatter.parse(input);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


    //Convert Date to String
    public static String dateToStr(Date input, String oFormat) {
        String result = "";
        if (input != null) {
            try {
                DateFormat df = new SimpleDateFormat(oFormat);
                result = df.format(input);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


    public static Calendar lastDayOfWeek(Calendar calendar) {
        Calendar cal = (Calendar) calendar.clone();
        int day = cal.get(Calendar.DAY_OF_YEAR);
        while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
            cal.set(Calendar.DAY_OF_YEAR, ++day);
        }
        return cal;
    }

    public int getCurrentDay() {
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        return cal.get(Calendar.DATE);
    }

    public String getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        return month > 9 ? "" + month : "0" + month;
    }

    public int getCurrentYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    public String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        return (day > 9 ? day : "0" + day) + "/" + (month > 9 ? month : "0" + month) + "/" + year;
    }

    public String getCurrentDate(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        return (day > 9 ? day : "0" + day) + "/" + (month > 9 ? month : "0" + month) + "/" + year;
    }

    public String getLastDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);

        Date lastDayOfMonth = calendar.getTime();
        return formatter.format(lastDayOfMonth);
    }

    public static String getFirstDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return formatter.format(calendar.getTime());
    }

    public String getLastDayOfWeek() {
        // Get calendar set to current date and time
        Calendar c = Calendar.getInstance();

        // Set the calendar to monday of the current week
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        // Print dates of the current week starting on Monday
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        c.add(Calendar.DATE, 6);
        return df.format(c.getTime());
    }

    public String getFirstDayOfWeek() {
        // Get calendar set to current date and time
        Calendar c = Calendar.getInstance();

        // Set the calendar to monday of the current week
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        // Print dates of the current week starting on Monday
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(c.getTime());
    }

    public boolean isDate(Object obj) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            format.format(obj);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Date addMinute(Date dateInput, int mins) {
        Calendar date = Calendar.getInstance();
        date.setTime(dateInput);
        long timeInSecs = date.getTimeInMillis();
        return new Date(timeInSecs + (mins * 60 * 1000));
    }

    public static String formatDate(String inDate, String inSDFs, String outSDFs) {
        SimpleDateFormat inSDF = new SimpleDateFormat(inSDFs);
        SimpleDateFormat outSDF = new SimpleDateFormat(outSDFs);
        String outDate = null;
        if (inDate != null) {
            try {
                Date date = inSDF.parse(inDate);
                outDate = outSDF.format(date);
            } catch (ParseException ex) {
                System.out.println("Unable to format date: " + inDate + ex.getMessage());
            }
        }
        return outDate;
    }
}
