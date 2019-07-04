package com.ubxty.gymmanagement.Util;

import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TimeUtil {


    public static String getCurrent(){

        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);

        String date =    new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());


     Log.e("CURRENT","DATE > "+date);

        return ""+date;
    }


    public static  String dateCalculate(String jobPostTime,String formet){

        Log.w("JOB ","Date >>"+jobPostTime);


        SimpleDateFormat myFormat = new SimpleDateFormat(formet, Locale.ENGLISH);
      //  String currentDate = getTime();
        long diff = 0;
        long second = 0;

        try {

            Date date12 = myFormat.parse(jobPostTime);


            Log.w("current","Time"+date12.getTime());

            Log.w("job post time","Time"+System.currentTimeMillis());


        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            Date date1 = myFormat.parse(jobPostTime);

            Date date2 = new Date();
            diff =  System.currentTimeMillis() - date1.getTime();

            second =  TimeUnit.SECONDS.convert(diff, TimeUnit.MILLISECONDS);

            // System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String str =  displayQuoteTime(second);

        Log.w("TIME","AGO >   "+str);

      //  Log.w("Cal","Time"+str);

        return str;

    }





    private static String displayQuoteTime(long seconds) {

        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds)

                - TimeUnit.DAYS.toHours(day);
        int months = day / 30;
        int years = months / 12;
        long minute = TimeUnit.SECONDS.toMinutes(seconds)
                - TimeUnit.DAYS.toMinutes(day)
                - TimeUnit.HOURS.toMinutes(hours);
        long second = TimeUnit.SECONDS.toSeconds(seconds)
                - TimeUnit.DAYS.toSeconds(day)
                - TimeUnit.HOURS.toSeconds(hours)
                - TimeUnit.MINUTES.toSeconds(minute);
        String postTime = "";
        if (years > 0) {

            if (TextUtils.isEmpty(postTime)) {
                if (years == 1) {
                    postTime = years + " year";
                } else {
                    postTime = years + " years";
                }

            } else {
                postTime = postTime + " " + years + " years";
            }
        }
        else if (months > 0) {
            if (TextUtils.isEmpty(postTime)) {
                if (months == 1) {
                    postTime = months + " month";
                } else {
                    postTime = months + " months";
                }
            } else
                postTime = postTime + " " + months + " months";
        } else if (day > 0) {
            if (day == 1) {
                postTime = String.valueOf(day);
            } else {
                postTime = String.valueOf(day);
            }
        } else if (hours > 0) {
            if (TextUtils.isEmpty(postTime)) {
                if (hours == 1) {
                    postTime = "-0";
                } else {
                    postTime = "-0";
                }

            } else {
                postTime = postTime + " -0 ";
            }

        } else if (minute > 0) {
            if (TextUtils.isEmpty(postTime)) {
                if (minute == 1) {
                    postTime = "-0";
                } else {
                    postTime =  "-0";
                }
            } else
                postTime = postTime +  "-0";
        }else {
            if (TextUtils.isEmpty(postTime))
                postTime = second + "-0";
        }






        return postTime;
    }
}
