package software.ulpgc.katas.io;

import software.ulpgc.katas.model.Ship;
import software.ulpgc.katas.model.ShipSummary;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public record OutputStreamWriter(OutputStream outputStream) implements SummaryWriter {

    @Override
    public void write(ShipSummary ShipSummary) {
        try {
            Writer writer = new OutputStreamWriter(outputStream);
            writer.write(textOf(ShipSummary));
            writer.flush();
        } catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }

    private String textOf(ShipSummary ShipSummary) {
        StringBuilder sb = new StringBuilder();
        Map<String, Integer> ships = ShipSummary.shipsPerPort();
        for(String port : ships.keySet()) {
            lineOf(port, ships, sb);
        }
        return sb.toString();
    }

    private void lineOf(String port, Map<String, Integer> ships, StringBuilder sb) {
        sb.append(port).append(": ").append(ships.get(port)).append("\n");
    }
}
