package com.shanzhu.market.common.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    /**
     * 获取两个时间的间隔(秒)
     * @param d1
     * @param d2
     * @return
     */
    public static long getDateBetween(Date d1, Date d2){
       return Math.abs((d1.getTime()-d2.getTime())/1000);//取绝对值
    }

    public static Date getEndDate(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY,23);
        c.set(Calendar.MINUTE,59);
        c.set(Calendar.SECOND,59);
        return c.getTime();
    }

}
