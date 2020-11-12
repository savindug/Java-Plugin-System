package com.savindu.csv_writer;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class CSVWriter {

    //generates the .csv file inside the /java_app/ bcz it is the main application
    private static final String CSV_FILE = "./evaluations.csv";
    private static BufferedWriter writer;
    private static CSVPrinter csvPrinter;


    public CSVWriter() {
        try {
            writer = Files.newBufferedWriter(Paths.get(CSV_FILE), StandardOpenOption.CREATE);
            csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader("X", "Y"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeData(double x, double y){
        try {
            writer = Files.newBufferedWriter(Paths.get(CSV_FILE), StandardOpenOption.APPEND);
            csvPrinter.printRecord(x, y);
            csvPrinter.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
