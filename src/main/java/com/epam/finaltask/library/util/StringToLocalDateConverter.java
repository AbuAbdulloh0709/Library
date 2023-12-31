package com.epam.finaltask.library.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StringToLocalDateConverter {
    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";

    public static LocalDate toLocaleDate(String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
        return LocalDate.parse(dateString, formatter);
    }
}
