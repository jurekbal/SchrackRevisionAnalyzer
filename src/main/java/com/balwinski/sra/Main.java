package com.balwinski.sra;

import com.balwinski.sra.model.ReportFile;
import com.balwinski.sra.services.FileParser;

import java.nio.file.Paths;
import java.util.List;

public class Main {

    private static final String pathStringToTestFile = "d:\\javafiles\\schrack\\sratest.txt";
    private static final String pathStringToTestDir = "d:\\javafiles\\schrack\\txt";

    public static void main(String[] args) {

        FileParser fileParser = new FileParser();
        List<ReportFile> reportFiles = fileParser.parseDir(Paths.get(pathStringToTestDir));

        System.out.println("Files imported into database: " + reportFiles.size());
//        reportFiles.stream()
//                .map(ReportFile::getPath)
//                .forEach(System.out::println);

        //TODO next
        // detectors database (Map)
        // test database, extract not tested detectors
        // list of invalid text files found in directory tree - to log in summary
        // tests and test methods (Header parser, ...)
        // getting base path from execute location and/or args
    }
}
