package com.balwinski.sra.services;

import com.balwinski.sra.model.Entry;
import com.balwinski.sra.model.Header;
import com.balwinski.sra.model.ReportFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileParser {

    private final IOService ioService;
    private final HeaderValidator headerValidator;
    private final EntryValidator entryValidator;
    private final EntryParser entryParser;
    private final Logger log;

    public FileParser() {
        this.ioService = new IOService();
        this.headerValidator = new HeaderValidator();
        this.entryValidator = new EntryValidator();
        this.entryParser = new EntryParser();
        this.log = LoggerFactory.getLogger(FileParser.class);
    }

    public List<ReportFile> parseDir(Path dir) {
        List<Path> fileList = ioService.getFileList(dir);
        System.out.println("Text files recognized:" + fileList.size());

        List<ReportFile> reportFiles = new ArrayList<>();

        for (Path file : fileList) {
            log.info("Attempting with file: " + file);
            List<String> lines = ioService.loadLines(file);

            Header header = parseHeader(lines);
            if (isValid(header)) {
                List<Entry> entries = parseEntries(lines);
                reportFiles.add(new ReportFile(file, header, entries));
            }
        }
        return reportFiles;
    }

    private boolean isValid(Header header) {
        return (header.getDateTime() != null && header.getScu() > 0 && header.getLoop() > 0);
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
        return entries;
    }

    private Header parseHeader(List<String> lines) {
        LocalDateTime fileDT = null;
        int scu = -1;
        int loop = -1;
        if (headerValidator.isValid(lines)) {
            fileDT = HeaderParser.parseDateLine(lines.get(2));
            scu = HeaderParser.parseSCU(lines.get(3));
            loop = HeaderParser.parseLoop(lines.get(3));
        }

        if (fileDT == null || scu == -1 || loop == -1) {
            log.error("Header parse failed");
            return new Header(null, -1, -1);
        } else {
            return new Header(fileDT, scu, loop);
        }
    }
}
