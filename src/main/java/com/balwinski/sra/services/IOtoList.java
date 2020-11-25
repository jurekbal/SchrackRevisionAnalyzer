package com.balwinski.sra.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

// Alternative loading of file - all lines at once.
public class IOtoList {

    private static final String pathString = "d:\\javafiles\\schrack\\sratest.txt";

    public List<String> loadAllFile() {
        List<String> lines = Collections.emptyList();
        try {
            lines = Files.readAllLines(Paths.get(pathString));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
