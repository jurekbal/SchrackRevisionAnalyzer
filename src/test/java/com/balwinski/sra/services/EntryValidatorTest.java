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
        String validEntryMTD533 = "MTD533\t42\tDetector (1)\t213/24\t07:20:41\t07:25:14\t6x CHECKED\t";
        String validEntryMTD533neu = "MTD533 neu\t44\tDetector (1)\t211/9\t07:30:46\t07:30:46\tCHECKED\t";
        String validEntryDMD2000_2 = "DMD2000-2\t92\tDetector (1)\t113/8\t\t\tto be checked\t";

        EntryValidator ev = new EntryValidator();

        //when
        //then
        assertThat(ev.isValid(validEntryLineToBeChecked)).isTrue();
        assertThat(ev.isValid(validEntryLineOnceChecked)).isTrue();
        assertThat(ev.isValid(validEntryLineDoubleChecked)).isTrue();
        assertThat(ev.isValid(validEntryMTD533)).isTrue();
        assertThat(ev.isValid(validEntryMTD533neu)).isTrue();
        assertThat(ev.isValid(validEntryDMD2000_2)).isTrue();
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
