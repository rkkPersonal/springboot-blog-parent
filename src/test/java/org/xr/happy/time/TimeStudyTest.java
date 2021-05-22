package org.xr.happy.time;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeStudyTest {


    public static void main(String[] args) {

        long currentTimestamp = System.currentTimeMillis();

        System.out.println(currentTimestamp);

        Date date = new Date();
        System.out.println(date);

        date = new Date(currentTimestamp);
        System.out.println(date);

        date.setTime(currentTimestamp);
        long time = date.getTime();
        System.out.println(time);
        System.out.println(date.getYear() + "," + date.getMonth() + "," + date.getDate() + "," + date.getDay() + "," + date.getHours() + "," + date.getMinutes() + "," + date.getSeconds() + "," +
                date.getTimezoneOffset());


        Calendar calendar = Calendar.getInstance();

        Date time1 = calendar.getTime();
        System.out.println(time1);
        calendar.setTime(date);

        System.out.println(calendar.toString());

        int i = calendar.get(Calendar.YEAR);

        System.out.println(i);

        int i1 = calendar.get(Calendar.DAY_OF_YEAR);
        System.out.println(i1);

        calendar.set(Calendar.MONTH, 7);

        System.out.println(calendar.getTime());

        calendar.add(Calendar.DAY_OF_MONTH, -2);

        System.out.println(calendar.getTime());

        calendar.add(Calendar.MINUTE, -33);

        System.out.println(calendar.getTime());


        Calendar instance = Calendar.getInstance();
        boolean before = calendar.after(instance);
        System.out.println(before);

        TimeZone timeZone = calendar.getTimeZone();
        System.out.println(timeZone.getID());
    }
}
