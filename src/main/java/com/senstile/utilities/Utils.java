package com.senstile.utilities;


import java.util.Arrays;

public class Utils {


    public static boolean isAnyNullOrEmpty(Object... strValues){
        if(strValues == null){
            return true;
        }
        return Arrays.stream(strValues).anyMatch(str -> null == str);
    }
}
