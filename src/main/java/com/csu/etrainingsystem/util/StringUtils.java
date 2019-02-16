package com.csu.etrainingsystem.util;

public class StringUtils {

    public static boolean isEmpty(String str){
        boolean flag=false;
        if (str==null||str.equals ("")){
            flag=true;
        }
        return flag;
    }
    public static String[] toBinary(int num){
        String toBinary= Integer.toBinaryString (num);
        String result=toBinary;
        if(toBinary.length()<6){
            for(int i=0;i<6-toBinary.length();i++)
                result = "0"+result;                  //在不足的位数前都加“0”
        }
        return result.split ("");
    }
}
