package com.ubxty.gymmanagement.Util;

import java.util.Calendar;

public class Utility {

    public static String currentMonthYear(){

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);



        return "" + month+ "/" + year ;
    }




}
