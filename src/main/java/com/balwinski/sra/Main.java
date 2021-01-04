package com.balwinski.sra;

import com.balwinski.sra.model.TestingData;
import com.balwinski.sra.model.Entry;
import com.balwinski.sra.model.ReportFile;
import com.balwinski.sra.services.FileParser;
import com.balwinski.sra.services.IOService;

import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static final String pathStringToTestDir = "d:\\javafiles\\schrack\\txt";
    private static final String pathStringResultFile = "d:\\javafiles\\schrack\\txt\\ResultList.txt";

    private static final Map<String, List<TestingData>> db = new HashMap<>();
    private static List<ReportFile> reportFiles = new ArrayList<>();

    private static boolean basicLogging = true;
    private static boolean extendedLogging = false;

    public static void main(String[] args) {

        parseReportFiles();
        createDatabase();
        Set<String> neverTested = getNeverTested();
        List<String> resultList = getResultList(neverTested);
        saveResultFile(resultList);

        //TODO next
        // getting base path from execute location and/or args
        // Exception handling (incorrect path and other), choose handle exception place
        // list of invalid text files found in directory tree - logging warnings, errors
        // tests and test methods (Header parser, ...)
    }

    private static List<String> getResultList(Set<String> neverTested) {
        return neverTested.stream()
                .map(s -> s.concat(" at loop: " + db.get(s).get(0).getFile().getHeader().getLoop()))
                .sorted()
                .collect(Collectors.toList());
    }

    private static Set<String> getNeverTested() {
        Set<String> neverTested = db.keySet().stream().filter(Main::wasNotTested).collect(Collectors.toSet());
        if (basicLogging) {
            System.out.println("Number of never tested detectors: " + neverTested.size());
        }
        return neverTested;
    }

    private static void parseReportFiles() {
        FileParser fileParser = new FileParser();
        reportFiles = fileParser.parseDir(Paths.get(pathStringToTestDir));

        if (basicLogging) {
            System.out.println("Files imported into database: " + reportFiles.size());
        }
        if (extendedLogging) {
            printReportFilesList();
        }
    }

    private static void saveResultFile(List<String> resultList) {
        IOService ios = new IOService();
        String resultMsg = ios.writeResultList(pathStringResultFile, resultList);

        if (basicLogging) {
            if (!resultMsg.equals("")) {
                System.out.println("ERROR: " + resultMsg);
            } else {
                System.out.println("INFO: Result file created: ResultList.txt");
            }
        }
    }

    private static void createDatabase() {
        for (ReportFile file : reportFiles) {
            List<Entry> entries = file.getEntries();
            for (Entry entry : entries) {
                String key = entry.getKey();
                List<TestingData> testingDataList = db.get(key);
                if (testingDataList == null) {
                    //encounter detector first time => create new entry in db
                    db.put(key, new ArrayList<>(Collections.singletonList(new TestingData(file, entry))));
                } else {
                    //update testingDataList
                    testingDataList.add(new TestingData(file, entry));
                }

            }
        }
        if (basicLogging) {
            System.out.println("Database size: " + db.size() + " entries.");
        }
    }

    private static void printReportFilesList() {
                reportFiles.stream()
                .map(ReportFile::getPath)
                .forEach(System.out::println);
    }

    private static boolean wasNotTested(String key) {
        List<TestingData> testingData = db.get(key);
        return testingData.stream().allMatch(testingData1 -> testingData1.getEntry().getCheckedCount() == 0);
    }

    private static void printDbEntry(Map.Entry<String, List<TestingData>> dbEntry) {
        System.out.print(dbEntry.getKey() + ":");
        dbEntry.getValue().forEach(testingData ->
                System.out.println("\t"+testingData.getFile().getPath() +
                        " Checked: " + testingData.getEntry().getCheckedCount())
        );
    }
}
