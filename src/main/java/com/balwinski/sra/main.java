package com.balwinski.sra;

import com.balwinski.sra.model.Header;
import com.balwinski.sra.services.HeaderParser;
import com.balwinski.sra.services.IOService;
import com.balwinski.sra.services.IOtoList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class main {

    public static void main(String[] args) {

        IOService ioService = new IOService();

        try {
            ioService.loadFile();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidDataException e) {
            e.printStackTrace();
        }

        System.out.println("Begin of header (raw array):");
        String[] header = ioService.getHeader().clone();
        for (String line : header) {
            System.out.println(line);
        }
        System.out.println("End of Header");

        Header testHeader = new Header(
                HeaderParser.parseDateLine(header[2]),
                HeaderParser.parseSCU(header[3]),
                HeaderParser.parseLoop(header[3])
        );

        System.out.println(testHeader);

        //testing of IOListClass
        IOtoList iOtoList = new IOtoList();
        List<String> listOfLinesList = new ArrayList<>();
        listOfLinesList = iOtoList.loadAllFile();
        System.out.println("\n*** Data from list:");
        System.out.println(listOfLinesList);
        System.out.println();
        System.out.println(listOfLinesList.get(0));

    }
}
