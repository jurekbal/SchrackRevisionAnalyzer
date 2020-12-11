package com.balwinski.sra.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.nio.file.Path;
import java.util.List;

@Data
@AllArgsConstructor
public class ReportFile {

    private Path path;
    private Header header;
    private List<Entry> entries;

}
