package com.balwinski.sra.services;

import com.balwinski.sra.InvalidDataException;
import com.balwinski.sra.model.Entry;
import com.balwinski.sra.model.Header;
import com.balwinski.sra.model.ReportFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FilesParser {

    private final IOService ioService;
    private final HeaderValidator headerValidator;
    private final EntryValidator entryValidator;
    private final EntryParser entryParser;
    private final Logger log;

    public FilesParser() {
        this.ioService = new IOService();
        this.headerValidator = new HeaderValidator();
        this.entryValidator = new EntryValidator();
        this.entryParser = new EntryParser();
        this.log = LoggerFactory.getLogger(FilesParser.class);
    }

    public List<ReportFile> parseDir(Path dir) {
        List<Path> fileList = ioService.getFileList(dir);
        System.out.println("File num recognized:" + fileList.size());

        List<ReportFile> reportFiles = new ArrayList<>();

        for (Path file : fileList) {
            log.info("Attempting with file" + file);
            List<String> lines = ioService.loadLines(file);

            Header header = parseHeader(lines);
            List<Entry> entries = parseEntries(lines);
            reportFiles.add(new ReportFile(file, header, entries));

        }
        return reportFiles;
    }

    private List<Entry> parseEntries(List<String> lines) {
        List<Entry> entries = new ArrayList<>();
        int lineNum = 0;
        for (String line : lines) {
            lineNum++;
            if (lineNum > 6) {
                if (entryValidator.isValid(line)) {
                    entries.add(entryParser.parseEntry(line));
                } else {
                    log.warn("Invalid line (not parsed): " + line);
                }
            }
        }

        //debug
//        log.info("Entries parsed: " + entries.size());
//        System.out.println(entries.get(0));
//        System.out.println(entries.get(entries.size() - 1));

        return entries;
    }

    private Header parseHeader(List<String> lines) {
        LocalDateTime fileDT = null;
        int scu = -1;
        int loop = -1;
        if (lines.size() >= 6) {
            for (int i = 0; i < 6; i++) {
                try {
                    headerValidator.parseHeaderLine(lines.get(i), i + 1);
                    if (i == 2) {
                        fileDT = HeaderParser.parseDateLine(lines.get(i));
                    }
                    if (i == 3) {
                        scu = HeaderParser.parseSCU(lines.get(3));
                        loop = HeaderParser.parseLoop(lines.get(3));
                    }
                } catch (InvalidDataException e) {
                    e.printStackTrace();
                }
            }
        }
        if (fileDT == null || scu == -1 || loop == -1) {
            log.error("Header parse failed");
            return new Header(null, -1, -1);
        } else {
            return new Header(fileDT, scu, loop);
        }
    }
}
