package br.com.chronos.domain.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DataParser {

    public static ZonedDateTime parseSimpleData(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");

        LocalDate date = LocalDate.parse(dateString, formatter);

        LocalDateTime localDateTime = date.atStartOfDay();

        return localDateTime.atZone(ZoneId.of("UTC"));

    }

}
