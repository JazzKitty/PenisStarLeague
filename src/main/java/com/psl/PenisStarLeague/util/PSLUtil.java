package com.psl.PenisStarLeague.util;

import java.time.ZoneId;
import java.util.HashSet;
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

}
