package software.ulpgc.katas;
import software.ulpgc.katas.io.*;
import software.ulpgc.katas.model.ShipSummary;
import java.io.*;
import java.net.URI;


public class Main {
    public static void main(String[] args) {
        try (OutputStream output = new FileOutputStream("summary.txt")) {
            ShipReader reader = new StreamShipReader(open("https://raw.githubusercontent.com/Quesnay84/kata2/master/data/ships.tsv"));
            SummaryWriter writer = new OutputStreamSummaryWriter(output);
            writer.write(ShipSummary.of(reader.load()));
        } catch (ShipLoadException | UncheckedIOException | IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static InputStream open(String url) {
        try {
            return URI.create(url).toURL().openStream();
        } catch (IOException | IllegalArgumentException e) {
            throw new ShipLoadException("The URL could not be opened: " + url, e);
        }
    }
}

