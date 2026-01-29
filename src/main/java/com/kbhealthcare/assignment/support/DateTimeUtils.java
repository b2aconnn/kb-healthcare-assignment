package com.kbhealthcare.assignment.support;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    private static final DateTimeFormatter LOCAL_DATETIME_FORMATTER =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter ZONED_DATETIME_WITH_OFFSET_FORMATTER =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");
    private static final DateTimeFormatter ISO_OFFSET_DATETIME_NO_COLON_FORMATTER =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");

    public static ZonedDateTime parseLocalDateTimeAsZoned(String dateString) {
        return LocalDateTime.parse(dateString, LOCAL_DATETIME_FORMATTER).atZone(ZoneId.of("Asia/Seoul"));
    }

    public static ZonedDateTime parseZonedDateTimeWithOffset(String dateString) {
        return ZonedDateTime.parse(dateString, ZONED_DATETIME_WITH_OFFSET_FORMATTER);
    }

    public static LocalDateTime parseIsoOffsetDateTimeToLocal(String dateString) {
        return LocalDateTime.parse(dateString, ISO_OFFSET_DATETIME_NO_COLON_FORMATTER);
    }
}