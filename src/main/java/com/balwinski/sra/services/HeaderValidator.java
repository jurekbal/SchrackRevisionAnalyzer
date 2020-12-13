package com.balwinski.sra.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class HeaderValidator {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss");

    private final Logger log;

    public HeaderValidator() {
        log = LoggerFactory.getLogger(HeaderValidator.class);
    }

    public boolean isValid(List<String> lines) {
        if (lines.size() < 6) {
            return false;
        }
        return (
                isValidHeaderLine1(lines.get(0))
                && isValidHeaderLine2(lines.get(1))
                && isValidHeaderLine3(lines.get(2))
                && isValidHeaderLine4(lines.get(3))
                && isValidHeaderLine5(lines.get(4))
                && isValidHeaderLine6(lines.get(5))
        );
    }

    private boolean isValidHeaderLine1(String currentLine) {
        return currentLine.equals("Loop Assistant - Revision Report");
    }

    private boolean isValidHeaderLine2(String currentLine) {
        return currentLine.matches("-{32}");
    }

    private boolean isValidHeaderLine3(String currentLine) {
        //dddd-dd-dd dd:dd:dd - date time line (yyy-MM-dd HH:mm:ss)
        if (currentLine.matches("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}")) {
            try {
                LocalDateTime.parse(currentLine, formatter);
                return true;
            } catch (DateTimeParseException e) {
                log.error(e.getLocalizedMessage());
            }
        };
        return false;
    }

    private boolean isValidHeaderLine4(String currentLine) {
        return currentLine.matches("SCU:\\s+\\d+\\s+\\/\\s+Loop:\\s+\\d+");
    }

    private boolean isValidHeaderLine5(String currentLine) {
        return currentLine.matches("={32}");
    }

    private boolean isValidHeaderLine6(String currentLine) {
        return currentLine.isEmpty();
    }
}
