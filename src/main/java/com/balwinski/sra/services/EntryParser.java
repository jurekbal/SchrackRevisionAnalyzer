package com.balwinski.sra.services;

import com.balwinski.sra.model.Entry;
import com.balwinski.sra.model.ElementType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class EntryParser {

    private final Logger logger;

    public EntryParser() {
        this.logger = LoggerFactory.getLogger(EntryParser.class);
    }

    public Entry parseEntry(String entryLine) {
        String splitRegex = "\t";
        String[] words = entryLine.split(splitRegex);

        ElementType elementType = parseElementType(words[0]);
        int shortAddress = Integer.parseInt(words[1]);
        String type = words[2];
        int groupNumber = parseGroupNumber(words[3]);
        int detectorNumber = parseDetectorNumber(words[3]);
        LocalTime firstTriggering;
        try {
            firstTriggering = LocalTime.parse(words[4]);
        } catch (DateTimeParseException e) {
            firstTriggering = null;
        }
        LocalTime lastTriggering;
        try {
            lastTriggering = LocalTime.parse(words[5]);
        } catch (DateTimeParseException e) {
            lastTriggering = null;
        }
        int checkedCount = parseCheckedCount(words[6]);

        return Entry.builder()
                .elementType(elementType)
                .loopShortAddress(shortAddress)
                .type(type)
                .groupNum(groupNumber)
                .detectorNum(detectorNumber)
                .firstTriggering(firstTriggering)
                .lastTriggering(lastTriggering)
                .checkedCount(checkedCount)
                .build();
    }

    private int parseCheckedCount(String checkedInfo) {
        if (checkedInfo.matches("\\d+x CHECKED")) {
            return Integer.parseInt(checkedInfo.replaceAll("\\D", ""));
        }
        if (checkedInfo.equals("CHECKED")) {
            return 1;
        }
        return 0;
    }

    private int parseDetectorNumber(String groupDetector) {
        return Integer.parseInt(groupDetector.split("/")[1]);
    }

    private int parseGroupNumber(String groupDetector) {
        return Integer.parseInt(groupDetector.split("/")[0]);
    }

    private ElementType parseElementType(String type) {
        for (ElementType et : ElementType.values()) {
            if (et.name().equals(type)) {
                return et;
            }
        }
        logger.warn("Unrecognized element type: {}", type);
        return null;
    }
}
