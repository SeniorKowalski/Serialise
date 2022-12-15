package com.kowalski;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientLog {

    List<String[]> log = new ArrayList<>();

    public void log(int productNum, int amount) {
        log.add((productNum + "," + amount).split(","));
    }

    public void exportAsCSV(File txtFile) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(txtFile, true))) {
            writer.writeAll(log);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
