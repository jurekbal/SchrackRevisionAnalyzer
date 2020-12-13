package com.balwinski.sra.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HeaderValidatorTest {

    HeaderValidator hv = new HeaderValidator();
    List<String> correctHeader = new ArrayList<>();

    @Before
    public void setUp() {
        correctHeader.add("Loop Assistant - Revision Report");
        correctHeader.add("--------------------------------");
        correctHeader.add("2014-03-24 11:02:53");
        correctHeader.add("SCU: 2 / Loop: 7");
        correctHeader.add("================================");
        correctHeader.add("");
    }

    @Test
    public void validHeaderMustReturnTrue() {
        //given
        List<String> lines = new ArrayList<>(Arrays.asList("","","","","",""));
        Collections.copy(lines, correctHeader);
        assertThat(lines).isEqualTo(lines);
        //when
        boolean validateResult = hv.isValid(lines);
        //then
        assertThat(validateResult).isTrue();
    }

    @Test
    public void invalidHeadersMustReturnFalse() {
        //given
        List<String> emptyFile = Collections.emptyList();
        List<String> oneLineOfText = Collections.singletonList("One line of text");
        List<String> missingSCU = new ArrayList<>(Arrays.asList("","","","","",""));
        Collections.copy(missingSCU, correctHeader);
        missingSCU.set(3, "SCU:  / Loop: 7");
        List<String> missingLoop = new ArrayList<>(Arrays.asList("","","","","",""));
        Collections.copy(missingLoop, correctHeader);
        missingLoop.set(3, "SCU: 2 / Loop: ");
        List<String> invalidDate = new ArrayList<>(Arrays.asList("","","","","",""));
        Collections.copy(invalidDate, correctHeader);
        invalidDate.set(2, "2014-13-24 11:02:53");
        List<String> invalidTime = new ArrayList<>(Arrays.asList("","","","","",""));
        Collections.copy(invalidTime, correctHeader);
        invalidTime.set(2, "2014-03-24 11:62:53");
        //then
        assertThat(hv.isValid(emptyFile)).isFalse();
        assertThat(hv.isValid(oneLineOfText)).isFalse();
        assertThat(hv.isValid(missingSCU)).isFalse();
        assertThat(hv.isValid(missingLoop)).isFalse();
        assertThat(hv.isValid(invalidDate)).isFalse();
        assertThat(hv.isValid(invalidTime)).isFalse();
    }
}
