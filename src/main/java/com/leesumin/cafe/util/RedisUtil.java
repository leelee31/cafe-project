package com.leesumin.cafe.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class RedisUtil {
    public static String generateKeyYYYYMMWEEK(LocalDateTime ldt) {
        Instant instant = ldt.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return generateKeyYYYYMMWEEK(calendar);
    }

    public static String generateKeyYYYYMMWEEK(Calendar calendar) {
        return "popular:menu:" + calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)+1) + "-" + calendar.get(Calendar.WEEK_OF_MONTH);
    }
}