package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(LocalDateTime ldt, T start, T end) {
        return (start instanceof LocalTime) ?
                ldt.toLocalTime().compareTo((LocalTime) start) >= 0 && ldt.toLocalTime().compareTo((LocalTime) end) < 0 :
                ldt.toLocalDate().compareTo((LocalDate) start) >= 0 && ldt.toLocalDate().compareTo((LocalDate) end) <= 0;

    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

