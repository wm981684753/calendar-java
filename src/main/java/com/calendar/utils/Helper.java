package com.calendar.utils;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Helper {
    /**
     * 将时间戳转换为时间
     * @param s  时间戳
     * @param t  时间
     * @return
     */
    public static String stampToDate(int s,String t){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(t);
        Date date = new Date((long) s*1000);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 将时间转换为时间戳
     * @param s  时间
     * @return  返回时间戳
     * @throws ParseException
     */
    public static int dateToStamp(String s) throws ParseException {

        //设置时间模版
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(s);
        long nt = date.getTime()/1000;
        return (int)nt;
    }
}
