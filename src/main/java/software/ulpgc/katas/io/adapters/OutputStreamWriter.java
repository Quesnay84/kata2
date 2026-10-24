package software.ulpgc.katas.io.adapters;

import software.ulpgc.katas.io.ports.ShipWriter;
import software.ulpgc.katas.io.ports.SummarySerializer;
import software.ulpgc.katas.model.ShipSummary;

import java.io.OutputStream;

public record OutputStreamWriter(OutputStream out, SummarySerializer serializer) implements ShipWriter {

    @Override
    public void write(ShipSummary summary) {
        serializer.serialize(summary);
    }
}
