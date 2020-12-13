package com.balwinski.sra.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class Header {

    private LocalDateTime dateTime;
    private int scu;
    private int loop;

    public Header(LocalDateTime dateTime, int scu, int loop) {
        this.dateTime = dateTime;
        this.scu = scu;
        this.loop = loop;
    }

    @Override
    public String toString() {
        return "Header{" +
                "dateTime=" + dateTime +
                ", scu=" + scu +
                ", loop=" + loop +
                '}';
    }
}
