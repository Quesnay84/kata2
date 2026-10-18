package software.ulpgc.katas.io;

import software.ulpgc.katas.model.ShipSummary;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.stream.Collectors;

public record OutputStreamSummaryWriter(OutputStream outputStream) implements SummaryWriter {

    @Override
    public void write(ShipSummary summary) {
        try {
            Writer writer = new OutputStreamWriter(outputStream);
            writer.write(textOf(summary));
            writer.flush();
        } catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }

    private String textOf(ShipSummary summary) {
        return summary.shipsPerPort().entrySet().stream()
                .map(port_number -> lineOf(port_number.getKey(), port_number.getValue()))
                .collect(Collectors.joining());
    }

    private String lineOf( String port, int port_number) {
        return port +  ":" + port_number + "\n";
    }
}
