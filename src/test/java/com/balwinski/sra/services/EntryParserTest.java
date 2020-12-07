package com.balwinski.sra.services;

import com.balwinski.sra.model.Entry;
import com.balwinski.sra.model.ElementType;
import org.junit.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;


public class EntryParserTest {

    @Test
    public void parseValidEntryToBeCheckedLineHaveToProduceEntryObject() {
        // given
        EntryParser entryParser = new EntryParser();
        String validEntryLineToBeChecked = "MCP545\t60\tDetector (1)\t5/1\t\t\tto be checked\t";
        Entry expectedEntryToBeChecked = Entry.builder()
                .elementType(ElementType.MCP545)
                .type("Detector (1)")
                .loopShortAddress(60)
                .groupNum(5)
                .detectorNum(1)
                .firstTriggering(null)
                .lastTriggering(null)
                .checkedCount(0)
                .build();
        //when
        Entry entryToBeChecked = entryParser.parseEntry(validEntryLineToBeChecked);
        //then
        assertThat(entryToBeChecked).isNotNull();
        assertThat(entryToBeChecked).isEqualTo(expectedEntryToBeChecked);
    }

    @Test
    public void parseValidOnceCheckedEntryLineHAveToProduceEntryObject() {
        // given
        EntryParser entryParser = new EntryParser();
        String validEntryLineOnceChecked = "OSD2000A\t32\tDetector (1)\t13/6\t10:46:02\t10:46:02\tCHECKED\t";
        Entry expectedEntryOnceChecked = Entry.builder()
                .elementType(ElementType.OSD2000A)
                .type("Detector (1)")
                .loopShortAddress(32)
                .groupNum(13)
                .detectorNum(6)
                .firstTriggering(LocalTime.parse("10:46:02"))
                .lastTriggering(LocalTime.parse("10:46:02"))
                .checkedCount(1)
                .build();
        //when
        Entry entryOnceChecked = entryParser.parseEntry(validEntryLineOnceChecked);
        //then
        assertThat(entryOnceChecked).isNotNull();
        assertThat(entryOnceChecked).isEqualTo(expectedEntryOnceChecked);
    }

    @Test
    public void parseValidDoubleCheckedEntryLineHAveToProduceEntryObject() {
        // given
        EntryParser entryParser = new EntryParser();
        String validEntryLineDoubleChecked = "OSD2000A\t21\tDetector (1)\t15/5\t10:30:39\t10:30:49\t2x CHECKED\t";
        Entry expectedEntryDoubleChecked = Entry.builder()
                .elementType(ElementType.OSD2000A)
                .type("Detector (1)")
                .loopShortAddress(21)
                .groupNum(15)
                .detectorNum(5)
                .firstTriggering(LocalTime.parse("10:30:39"))
                .lastTriggering(LocalTime.parse("10:30:49"))
                .checkedCount(2)
                .build();
        //when
        Entry entryOnceChecked = entryParser.parseEntry(validEntryLineDoubleChecked);
        //then
        assertThat(entryOnceChecked).isNotNull();
        assertThat(entryOnceChecked).isEqualTo(expectedEntryDoubleChecked);
    }

    //Static test for split regex
    @Test
    public void splitWordsMustIncludeEveryOneTab() {
        //given
        String toBeChecked = "MCP545\t60\tDetector (1)\t5/1\t\t\tto be checked\t";
        String onceChecked = "OSD2000A\t59\tDetector (1)\t15/21\t11:16:57\t11:16:57\tCHECKED\t";
        String doubleChecked = "OSD2000A\t58\tDetector (1)\t15/20\t10:37:03\t10:37:34\t2x CHECKED\t";
        String splitRegex = "\t";
        //when
        String[] toBeCheckedSplit = toBeChecked.split(splitRegex);
        String[] onceCheckedSplit = onceChecked.split(splitRegex);
        String[] doubleCheckedSplit = doubleChecked.split(splitRegex);
        //then
        assertThat(toBeCheckedSplit).hasSize(7);
        assertThat(onceCheckedSplit).hasSize(7);
        assertThat(doubleCheckedSplit).hasSize(7);

    }

}
