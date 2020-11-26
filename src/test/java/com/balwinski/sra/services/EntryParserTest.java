package com.balwinski.sra.services;

import com.balwinski.sra.model.Entry;
import com.balwinski.sra.model.LoopElement;
import org.junit.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;


public class EntryParserTest {

    @Test
    public void parseValidEntryLineHAveToProduceEntryObject() {
        // given
        EntryParser entryParser = new EntryParser();
        String validEntryLine = "OSD2000A\t32\tDetector (1)\t13/6\t10:46:02\t10:46:02\tCHECKED\t";
        Entry expectedEntry = Entry.builder()
                .elementType(LoopElement.OSD2000A)
                .type("Detector (1)")
                .loopShortAddress(32)
                .groupNum(13)
                .detectorNum(6)
                .firstTriggering(LocalTime.parse("10:46:02"))
                .lastTriggering(LocalTime.parse("10:46:02"))
                .checkedCount(1)
                .build();
        //when
        Entry entry = entryParser.parseEntry(validEntryLine);
        //then
        assertThat(entry).isNotNull();
        assertThat(entry).isEqualTo(expectedEntry);


    }
}
