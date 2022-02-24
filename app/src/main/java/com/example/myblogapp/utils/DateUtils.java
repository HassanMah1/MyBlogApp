package com.example.myblogapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String getStringDate(long longDate){
        Date date = new Date(longDate);
        String pattern = "dd MMMM, yyyy HH:mm";
        //String pattern = "dd MMMM, yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String stringDate = simpleDateFormat.format(date);
        return stringDate;
    }
}
