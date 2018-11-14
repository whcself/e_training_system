package com.csu.etrainingsystem.util;

import java.sql.Timestamp;

public class TimeUtil {
    public static Timestamp getEndTime(String beginTime,String timeLen){
        Timestamp begin = Timestamp.valueOf(beginTime);
        String[] timesp = timeLen.split(":");
        long duration = Integer.valueOf(timesp[0]) * 3600000;
        if (timesp.length == 2) duration += Integer.valueOf(timesp[1]) * 60000;
        if (timesp.length == 3) duration += Integer.valueOf(timesp[2]) * 1000;
        return new Timestamp(begin.getTime() + duration);
    }
}
