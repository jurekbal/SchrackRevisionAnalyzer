package com.balwinski.sra.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

// Alternative loading of file - all lines at once.
public class IOService {

    public List<Path> getFileList(Path baseDir){
        //TODO get file list from recursive directory scan
        return Collections.emptyList();
    }

    public List<String> loadLines(String pathString) {
        List<String> lines = Collections.emptyList();
        try {
            lines = Files.readAllLines(Paths.get(pathString));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
