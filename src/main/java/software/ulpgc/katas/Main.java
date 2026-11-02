package software.ulpgc.katas;

import software.ulpgc.katas.io.ShipLoadException;
import software.ulpgc.katas.io.adapters.*;

import software.ulpgc.katas.io.adapters.OutputStreamWriter;
import software.ulpgc.katas.io.ports.*;
import software.ulpgc.katas.model.ShipSummary;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Optional;
import java.util.concurrent.SynchronousQueue;

public class Main {

    public static void main(String[] args) {
        try {
            ShipReader reader = new StreamShipReader(URI.create("https://raw.githubusercontent.com/Quesnay84/kata2/master/src/main/resources/ships.tsv").toURL().openStream(), new TsvShipDeserializer());
            ShipWriter writer = new OutputStreamWriter(new FileOutputStream("salida.txt"), new TextSummarySerializer());
            writer.write(ShipSummary.of(reader.load()));
        } catch (ShipLoadException | UncheckedIOException | IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static InputStream resource(String name) {
        return Optional.ofNullable(Main.class.getResourceAsStream(name))
                .orElseThrow(() -> new ShipLoadException("Resource not found: " + name));
    }
}
