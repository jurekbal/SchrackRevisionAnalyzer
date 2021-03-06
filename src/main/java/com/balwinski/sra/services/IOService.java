package com.balwinski.sra.services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IOService {

    public List<Path> getFileList(Path baseDir) {
        try (Stream<Path>paths = Files.walk(baseDir)) {
            return paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".txt"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

    }

    public List<String> loadLines(Path filePath) {
        List<String> lines = Collections.emptyList();
        try {
            lines = Files.readAllLines(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public String writeResultList(String filePathString, List<String> resultList) {
        String errorMsg = "";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePathString))) {
            for (String line : resultList) {
                bw.write(line +"\n");
            }
        } catch (IOException e) {
            errorMsg = e.getLocalizedMessage();
        }
        return errorMsg;
    }
}
