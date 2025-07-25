package com.psl.PenisStarLeague.util;

import java.util.HashSet;
import java.util.Set;

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

}
