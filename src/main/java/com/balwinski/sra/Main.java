package com.balwinski.sra;

import com.balwinski.sra.model.Entry;
import com.balwinski.sra.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Logger log = LoggerFactory.getLogger(Main.class);

        //getting of IOListClass
        log.info("Attempting to load file");
        IOtoList iOtoList = new IOtoList();
        List<String> lines;
        lines = iOtoList.loadAllFile();
        log.info("After loading file");

        HeaderValidator hv = new HeaderValidator();
        EntryValidator ev = new EntryValidator();
        EntryParser ep = new EntryParser();

        //parse header;
        log.info("Parsing header");
        LocalDateTime fileDT = null;
        int scu = -1;
        int loop = -1;
        if (lines.size() >= 6) {
            for (int i = 0; i < 6; i++) {
                try {
                    hv.parseHeaderLine(lines.get(i), i+1);
                    if (i==2) {
                         fileDT = HeaderParser.parseDateLine(lines.get(i));
                    }
                    if (i==3) {
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
        } else {
            log.info("Header parse success");
            System.out.println(fileDT);
            System.out.println("SCU: " + scu);
            System.out.println("Loop: " + loop);
        }

        log.info("Parsing entries");
        List<Entry> entries = new ArrayList<>();
        int lineNum = 0;
        for (String line : lines) {
            lineNum++;
            if (lineNum > 6) {
                if (ev.isValid(line)) {
                    entries.add(ep.parseEntry(line));
                } else {
                    log.warn("Invalid line (not pared): " + line);
                }
            }
        }

        log.info("Entries parsed: " + entries.size());
        System.out.println(entries.get(0));
        System.out.println(entries.get(entries.size()-1));

    }
}
