package com.sboot.backend.common.core.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    public static final String dateFormat = "yyyy-MM-dd HH:mm:ss.SSS";

    public static LocalDateTime now() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.toLocalDateTime();
    }

    public static String nowFormatString(String format) {
        LocalDateTime dateTime = DateTimeUtil.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return dateTime.format(formatter);
    }

    public static String nowString() {
        return DateTimeUtil.nowFormatString(DateTimeUtil.dateFormat);
    }
}
