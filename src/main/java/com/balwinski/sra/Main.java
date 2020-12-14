package com.balwinski.sra;

import com.balwinski.sra.model.TestingData;
import com.balwinski.sra.model.Entry;
import com.balwinski.sra.model.ReportFile;
import com.balwinski.sra.services.FileParser;

import java.nio.file.Paths;
import java.util.*;

public class Main {

    private static final String pathStringToTestDir = "d:\\javafiles\\schrack\\dbtest";

    public static void main(String[] args) {

        FileParser fileParser = new FileParser();
        List<ReportFile> reportFiles = fileParser.parseDir(Paths.get(pathStringToTestDir));

        System.out.println("Files imported into database: " + reportFiles.size());
//        reportFiles.stream()
//                .map(ReportFile::getPath)
//                .forEach(System.out::println);

        // creating database with group/detector key and list of testing data
        Map<String, List<TestingData>> db = new HashMap<>();
        for (ReportFile file : reportFiles) {
            List<Entry> entries = file.getEntries();
            for (Entry entry : entries) {
                String key = entry.getKey();
                List<TestingData> testingDataList = db.get(key);
                if (testingDataList == null) {
                    //detector first encounter - create new entry in db
                    db.put(key, new ArrayList<>(Collections.singletonList(new TestingData(file, entry))));
                } else {
                    //update testingDataList
                    testingDataList.add(new TestingData(file, entry));
                }

            }
        }

        System.out.println("Database size: " + db.size() + " entries.");
//        Set<String> keySet = db.keySet();
//        keySet.stream().sorted().limit(100).forEach(System.out::println);
        db.entrySet().stream().limit(10).forEach(Main::printDbEntry);

        //TODO next
        // detectors database (Map) - DONE!
        // test database, extract not tested detectors
        // list of invalid text files found in directory tree - to log in summary
        // tests and test methods (Header parser, ...)
        // getting base path from execute location and/or args
    }

    private static void printDbEntry(Map.Entry<String, List<TestingData>> dbEntry) {
        System.out.print(dbEntry.getKey() + ":");
        dbEntry.getValue().forEach(testingData ->
                System.out.println("\t"+testingData.getFile().getPath() +
                        " Checked: " + testingData.getEntry().getCheckedCount())
        );
    }
}
