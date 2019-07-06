package com.ubxty.gymmanagement.Util;


import android.content.Context;
import android.util.Log;


import com.ubxty.gymmanagement.database.entity.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Utility {



    public static String getFileData(File file){

        String data = "" ;
        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;


            while ((line = br.readLine()) != null) {

                Log.w("linnnee",">><<<INWHILE>>" + line);

                data = data+ line ;
                // do something with line

            }
            br.close();
            Log.w("linnnee",">><<<>>" + data);

        } catch (IOException e) {
            System.out.println("ERROR: unable to read file " + file);
            e.printStackTrace();
        }



//        String data = "" ;
//        try {
//
//            Scanner scanner = new Scanner(file) ;
//            while (scanner.hasNextLine()){
//                Log.w("linnnee","ttrtt"+scanner.nextLine());
//
//                data = data + scanner.nextLine();
//            }
//
//
//            scanner.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        Log.w("dataa","fdfdd"+ data);
        return data ;
    }


    public static void saveData(Context context , String gson , File file){

//        File newdir= context.getExternalFilesDir("Registration Files");
//       // File newdir= context.getDir("Registration Files", Context.MODE_PRIVATE);
//        if (!newdir.exists()){
//            Log.w("saveData","MakeDirs" + newdir.mkdirs()) ;
//        }
//
//        File file = new File(newdir , System.currentTimeMillis() + ".txt") ;

        if (!file.exists()){

            try {

                file.createNewFile() ;

                BufferedWriter out = new BufferedWriter(
                        new FileWriter(file , true));

                out.write(gson);

                out.close();



            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }




    public static File[] allFiles(Context context){


        File newdir= context.getExternalFilesDir("Registration Files");
        // File newdir= getContext().getDir("Registration Files", Context.MODE_PRIVATE);
        File[] files = newdir.listFiles() ;


        return files ;
    }
    public static String currentMonth(){

        Calendar c = Calendar.getInstance();
        //  System.out.println("Current time => "+c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("MMMM/yyyy");

        Log.w("Monthh","Utility" + df.format(c.getTime())) ;

        return  df.format(c.getTime());

    }
    public static  String currentplusWeek(){

        Date m = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(m);
        cal.add(Calendar.DATE, 7); // 10 is the days you want to add or subtract


        SimpleDateFormat df = new SimpleDateFormat("dd.mm.yyyy", Locale.ENGLISH);



        return  df.format(cal.getTime());


    }

    public static String todayDate(){

        Calendar c = Calendar.getInstance();

     SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        Log.w("Monthh","Utility" + df.format(c.getTime())) ;

        return  df.format(c.getTime());


    }


    public static String payfeesDateString(String date){

        SimpleDateFormat df = new SimpleDateFormat("dd.mm.yyyy");

        String strDate = "" ;
        try {

            Date date1 = df.parse(date);
            strDate =  df.format(date1.getTime()); ;

            Log.w("payfeesDateString","strDate" + strDate) ;

        } catch (Exception e) {
            e.printStackTrace();
        }


        return strDate ;


    }


    static public void  MOVEFILE(String sou  , Context context ){


        File file2= context.getExternalFilesDir("Delete File");
        // File newdir= getContext().getDir("Registration Files", Context.MODE_PRIVATE);


        File file = new File(String.valueOf(new File(sou).getAbsoluteFile()));
        //  File file2 = new File(+"/");



        if (file.renameTo(new File(file2.getAbsolutePath() + "/" + file.getName()))){


            Log.w("Move","DOONE<>><><>><><") ;

        }else {

            Log.w("Move","FAILED<>><><>><><") ;


        }

    }


    public  static  String lastPayDate(String longDate){

        SimpleDateFormat df = new SimpleDateFormat("dd.mmmm.yyyy");

        String strDate = "" ;
        try {

            Date date1 = df.parse(longDate);
            strDate =  df.format(date1.getTime()); ;

        } catch (Exception e) {
            e.printStackTrace();
        }


        return strDate ;

    }


    public static boolean UpdateFeesExpMonth(User model){

//        Gson gson= new Gson() ;
//        model.setPayfees("0");
//        model.setFullfeesStatus("not Pay") ;
//
//        File file = new File(model.getFilepath()) ;
//
//        FileWriter fwriter = null ;
//
//        try {
//
//            fwriter = new FileWriter(file , false);
//            BufferedWriter write = new BufferedWriter(fwriter); // this is the type so we can write to it.
//            write.write(""); // write empty string to delet everything
//
//            write.write(gson.toJson(model)) ;
//
//            write.close(); //



//        } catch (Exception e) {
//            e.printStackTrace();
//            return  false ;
//        }


        return true ;

    }
    public static String currentMonthYear(){

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);


        return "" + month+ "/" + year ;

}



public static String CurrentDate(){


    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
    Date dateobj = new Date();

    return df.format(dateobj) ;

    //System.out.println(df.format(dateobj));


}



}

