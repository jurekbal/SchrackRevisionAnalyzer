package com.balwinski.sra.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
This class is deprecated and will be replaced by IOtoList
 */
public class IOService {

    private static final int MAX_LINE_NUM = 299;

    //    private static final String pathString = "d:\\javafiles\\schrack\\P+0 L5 cz1.txt";
    private static final String pathString = "d:\\javafiles\\schrack\\sratest.txt";

    public String[] loadFile() throws IOException {

        String[] fileLines = new String[MAX_LINE_NUM];

        BufferedReader reader = new BufferedReader(new FileReader(pathString));
        int lineNum = 0;
        String currentLine;

        while ((currentLine = reader.readLine()) != null) {
            lineNum++;
            if (lineNum > MAX_LINE_NUM) {
                throw new IOException("ERROR: Maximal line number reached (" + MAX_LINE_NUM + ") in file " + pathString);
            }
            fileLines[lineNum] = currentLine;
        }

        reader.close();
        return fileLines;
    }


}
