package com.first.demo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:
 * @Company：众阳健康
 * @Author: jiangheng
 * @Date: 2019/12/27 8:54
 * @Version 1.0
 */
public class FormatDateUtil {

    public static String formatDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    public static String formatDate_ymd(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }
}
