package com.balwinski.sra.services;

public class EntryValidator {

    // (\w+[- ]*\w*)\t\d{1,3}\t\w+\s\(\d+\)\t\d+\/\d+\t(\d\d:\d\d:\d\d)?\t(\d\d:\d\d:\d\d)?\t[a-zA-Z0-9\s]+\t\Z
    private static final String entryRegex = "(\\w+[- ]*\\w*)\\t\\d{1,3}\\t\\w+\\s\\(\\d+\\)\\t\\d+/\\d+\\t(\\d\\d:\\d\\d:\\d\\d)?\\t(\\d\\d:\\d\\d:\\d\\d)?\\t[a-zA-Z0-9\\s]+\\t\\Z";

    public boolean isValid(String line) {
        if (line != null) {
            return line.matches(entryRegex);
        } else {
            return false;
        }
    }
}
