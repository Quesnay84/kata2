package software.ulpgc.katas.io.adapters;

import software.ulpgc.katas.io.ports.ShipWriter;
import software.ulpgc.katas.io.ports.SummarySerializer;
import software.ulpgc.katas.model.ShipSummary;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;

public record OutputStreamWriter(OutputStream out, SummarySerializer serializer) implements ShipWriter {

    @Override
    public void write(ShipSummary summary) {

        try{
            out.write(serializer.serialize(summary).getBytes());
            out.flush();
        } catch (IOException e){
            throw new UncheckedIOException("The file could not be written", e);
        }
    }
}
