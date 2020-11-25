package com.balwinski.sra.model;

import java.time.LocalDateTime;

public class Entry {

    private LoopElementType elementType;
    private int loopShortAdress;
    private String type;
    private int groupNum;
    private int detectorNum;
    LocalDateTime firstTriggering;
    LocalDateTime lastTriggering;
    private CheckedState state;
}
