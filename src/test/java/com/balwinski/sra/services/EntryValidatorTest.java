package com.balwinski.sra.services;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EntryValidatorTest {

    @Test
    public void validEntryLinesMustBeAccepted() {
        // given
        String validEntryLineToBeChecked = "MCP545\t60\tDetector (1)\t5/1\t\t\tto be checked\t";
        String validEntryLineOnceChecked = "OSD2000A\t32\tDetector (1)\t13/6\t10:46:02\t10:46:02\tCHECKED\t";
        String validEntryLineDoubleChecked = "OSD2000A\t21\tDetector (1)\t15/5\t10:30:39\t10:30:49\t2x CHECKED\t";
        EntryValidator ev = new EntryValidator();
        //when
        //then
        assertThat(ev.isValid(validEntryLineToBeChecked)).isTrue();
        assertThat(ev.isValid(validEntryLineOnceChecked)).isTrue();
        assertThat(ev.isValid(validEntryLineDoubleChecked)).isTrue();
    }

    @Test
    public void invalidEntryLinesMustBeRejected() {
        //given
        String emptyLine = "";
        String onlyTab = "\t";
        String aRandomTextLine = "Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                " Donec gravida a tellus venenatis molestie.";
        String noTabs = "MCP545 60 Detector (1) 5/1 to be checked";
        String shorAddrAsLetters = "MCP545\txx\tDetector (1)\t5/1\t\t\tto be checked\t";
        String missingShortAddr = "MCP545\t\tDetector (1)\t5/1\t\t\tto be checked\t";
        EntryValidator ev = new EntryValidator();
        //then
        assertThat(ev.isValid(emptyLine)).isFalse();
        assertThat(ev.isValid(onlyTab)).isFalse();
        assertThat(ev.isValid(null)).isFalse();
        assertThat(ev.isValid(aRandomTextLine)).isFalse();
        assertThat(ev.isValid(noTabs)).isFalse();
        assertThat(ev.isValid(shorAddrAsLetters)).isFalse();
        assertThat(ev.isValid(missingShortAddr)).isFalse();
    }
}
