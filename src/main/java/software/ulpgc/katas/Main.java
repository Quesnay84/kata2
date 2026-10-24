package software.ulpgc.katas;

import software.ulpgc.katas.io.ShipLoadException;
import software.ulpgc.katas.io.adapters.*;

import software.ulpgc.katas.io.ports.*;
import software.ulpgc.katas.model.ShipSummary;

import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        try {
            ShipReader reader = new StreamShipReader(resource("/ships.tsv"), new TsvShipDeserializer());
            ShipWriter writer = new OutputStreamWriter(System.out, new TextSummarySerializer());
            writer.write(ShipSummary.of(reader.load()));
        } catch (ShipLoadException | UncheckedIOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static InputStream resource(String name) {
        return Optional.ofNullable(Main.class.getResourceAsStream(name))
                .orElseThrow(() -> new ShipLoadException("Resource not found: " + name));
    }
}
