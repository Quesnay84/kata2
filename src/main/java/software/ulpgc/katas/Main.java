package software.ulpgc.katas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import software.ulpgc.katas.io.*;
import software.ulpgc.katas.model.*;


public class Main {
    private static final String DATA_FILE = "data/malformed.tsv";
    public static void main(String[] arg) {
        try {
            ShipReader reader = new StreamShipReader(new FileInputStream(DATA_FILE));
            SummaryWriter writer = new OutputStreamSummary(System.out);
            writer.write(ShipSummary.of(reader.load()));
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + DATA_FILE);
        } catch (ShipLoadException e) {
            System.err.println(e.getMessage());
        }


    }
}
