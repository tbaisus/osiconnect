package com.service.osiconnect.standards;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.service.osiconnect.constants.ApplicationConstants.REQ_TIME_FORMAT;

public class ApplicationUtil {

    public static LocalDateTime getFormattedDateTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(REQ_TIME_FORMAT);
        String formattedDate = currentDateTime.format(formatter);
        LocalDateTime formattedDateTime = LocalDateTime.parse(formattedDate, formatter);
        return formattedDateTime;
    }
}
