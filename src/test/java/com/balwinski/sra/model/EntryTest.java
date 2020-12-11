package com.balwinski.sra.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EntryTest {

    @Test
    public void getKey() {
        //given
        Entry entry = Entry.builder()
                .groupNum(123)
                .detectorNum(23)
                .build();
        //when
        String key = entry.getKey();
        //then
        assertThat(key).isEqualTo("123/23");
    }
}
