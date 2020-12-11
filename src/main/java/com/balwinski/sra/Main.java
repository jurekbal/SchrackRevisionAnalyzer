package com.balwinski.sra;

import com.balwinski.sra.model.ReportFile;
import com.balwinski.sra.services.FilesParser;

import java.nio.file.Paths;
import java.util.List;

public class Main {

    private static final String pathStringToTestFile = "d:\\javafiles\\schrack\\sratest.txt";
    private static final String pathStringToTestDir = "d:\\javafiles\\schrack\\simple";

    public static void main(String[] args) {

        FilesParser filesParser = new FilesParser();
        List<ReportFile> reportFiles = filesParser.parseDir(Paths.get(pathStringToTestDir));

        System.out.println("Plików w bazie raportów: " + reportFiles.size());
        reportFiles.stream()
                .map(ReportFile::getPath)
                .forEach(System.out::println);

        //TODO next
        // detectors database (Map)
        // test database, extract not tested detectors
        // tests and test methods
    }
}
