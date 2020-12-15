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

    public static void main(String[] args) {

        FileParser fileParser = new FileParser();
        List<ReportFile> reportFiles = fileParser.parseDir(Paths.get(pathStringToTestDir));

        System.out.println("Files imported into database: " + reportFiles.size());
//        reportFiles.stream()
//                .map(ReportFile::getPath)
//                .forEach(System.out::println);

        // creating database with group/detector key and list of testing data
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

        //getting never tested detectors
        Set<String> neverTested = db.keySet().stream().filter(Main::wasNotTested).collect(Collectors.toSet());
        System.out.println("Number of never tested detectors: " + neverTested.size());

        // prepare presentation data and save to result file
        List<String> resultList = neverTested.stream()
                .map(s -> s.concat(" at loop: " + db.get(s).get(0).getFile().getHeader().getLoop()))
                .sorted()
                .collect(Collectors.toList());
        IOService ios = new IOService();
        String resultMsg = ios.writeResultList(pathStringResultFile, resultList);
        if (!resultMsg.equals("")) {
            System.out.println("ERROR: " + resultMsg);
        } else {
            System.out.println("INFO: Result file created: ResultList.txt");
        }

        //TODO next
        // detectors database (Map) - DONE! (to refactor)
        // test database, extract not tested detectors - DONE (may be enhanced, to refactor)
        // list of invalid text files found in directory tree - to log in summary
        // tests and test methods (Header parser, ...)
        // getting base path from execute location and/or args
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
