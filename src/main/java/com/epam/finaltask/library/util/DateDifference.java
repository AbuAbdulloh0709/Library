package com.epam.finaltask.library.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateDifference {
    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";

    public static int calculateDateDifference(String date1String, String date2String) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);

        LocalDate date1 = LocalDate.parse(date1String, formatter);
        LocalDate date2 = LocalDate.parse(date2String, formatter);

        return Math.abs((int) ChronoUnit.DAYS.between(date1, date2));
    }
}
