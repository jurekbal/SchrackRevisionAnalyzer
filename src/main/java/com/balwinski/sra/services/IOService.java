package com.balwinski.sra.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IOService {

    private static final String pathString = "d:\\javafiles\\schrack\\P+0 L5 cz1.txt";

    public String loadFile() throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(pathString));

        int lineNum;
        String currentLine;
        while ((currentLine = reader.readLine()) != null ) {

        }

        reader.close();
    }

}
