package com.balwinski.sra.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HeaderParser {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss");

    public static LocalDateTime parseDateLine(String line) {
        return LocalDateTime.parse(line, formatter);
    }

    public static int parseSCU(String line) {
        String[] words = line.split("\\s");
        return Integer.parseInt(words[1]);
    }

    public static int parseLoop(String line) {
        String[] words = line.split("\\s");
        return Integer.parseInt(words[4]);
    }
}
