package com.psl.PenisStarLeague.util;

import java.text.DecimalFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

public class PSLUtil {
    private PSLUtil(){
        // Pure static class
    }

    public static Set<Integer> stringToSet(String str, String split){
        Set<Integer> set = new HashSet<>();
        String[] sArr = str.split(split);
        for(int i = 0; i< sArr.length; i++){
            set.add(Integer.parseInt(sArr[i]));
        }
        return set;
    }

    public static ZoneId getIdZone(TimeZone timeZone){

        ZoneId id;
        if(timeZone != null){
            id = timeZone.toZoneId();
        }else{
            id = ZoneId.systemDefault();
        }

        return id; 
    }

    public static String getPlaceString(int i){
        int onesPlace = i % 10;
        if(onesPlace == 1){
            return "st";
        }else if(onesPlace == 2){
            return "nd";
        }else if(onesPlace == 3){
            return "rd";
        }else{
            return "th"; 
        }
    }

    public static String getTimeString(int hour, int min){
        DecimalFormat df = new DecimalFormat("00");
        if(hour == 0){
            return "12:" + df.format(min) + "AM";
        }else if(hour > 0 && hour < 11){
            return hour + ":" + df.format(min) + "AM";
        }else if(hour == 12){
            return "12:" + df.format(min) + "PM"; 
        }else if(hour < 24){
            return (hour - 12) + ":" + df.format(min) + "PM";
        }else{
            return "N/A";
        }
    }

    public static String getYearlyString(ZonedDateTime zonedDateTime){
        return zonedDateTime.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + zonedDateTime.getDayOfMonth() 
            + PSLUtil.getPlaceString(zonedDateTime.getDayOfMonth()) + " at " + getTimeString(zonedDateTime.getHour(), zonedDateTime.getMinute());
    }

    public static String getMonthlyString(ZonedDateTime zonedDateTime){
        return zonedDateTime.getDayOfMonth() + PSLUtil.getPlaceString(zonedDateTime.getDayOfMonth()) + " at "
            + getTimeString(zonedDateTime.getHour(), zonedDateTime.getMinute());

    }
    
    public static String getWeeklyString(ZonedDateTime zonedDateTime){
        return zonedDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " at "
            + getTimeString(zonedDateTime.getHour(), zonedDateTime.getMinute());
    }

    public static String getDateString(ZonedDateTime zonedDateTime){
        return zonedDateTime.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " "
            + zonedDateTime.getDayOfMonth() + ", " + zonedDateTime.getYear() + " at " 
            + getTimeString(zonedDateTime.getHour(), zonedDateTime.getMinute());
    }

}
