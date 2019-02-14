package com.csu.etrainingsystem.util;

public class StringUtils {

    public static boolean isEmpty(String str){
        boolean flag=false;
        if (str==null||str.equals ("")){
            flag=true;
        }
        return flag;
    }
}
