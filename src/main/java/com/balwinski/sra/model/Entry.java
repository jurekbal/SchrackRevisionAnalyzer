package com.balwinski.sra.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
public class Entry {

    private ElementType elementType;
    private int loopShortAddress;
    private String type;
    private int groupNum;
    private int detectorNum;
    LocalTime firstTriggering;
    LocalTime lastTriggering;
    private int checkedCount;

    @Builder
    public Entry(ElementType elementType, int loopShortAddress,
                 String type, int groupNum,
                 int detectorNum, LocalTime firstTriggering,
                 LocalTime lastTriggering, int checkedCount) {
        this.elementType = elementType;
        this.loopShortAddress = loopShortAddress;
        this.type = type;
        this.groupNum = groupNum;
        this.detectorNum = detectorNum;
        this.firstTriggering = firstTriggering;
        this.lastTriggering = lastTriggering;
        this.checkedCount = checkedCount;
    }
}
