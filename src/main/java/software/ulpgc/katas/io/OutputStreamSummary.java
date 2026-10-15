package software.ulpgc.katas.io;

import software.ulpgc.katas.model.ShipSummary;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

public record OutputStreamSummary(OutputStream out) implements SummaryWriter {


    @Override
    public void write(ShipSummary summary) {
        try{
            Writer writer = new OutputStreamWriter(out);
            writer.write(textOf(summary));
            writer.flush();
        }
        catch (IOException e){
            throw new RuntimeException( "The summary could not be written",e);
        }
    }

    private String textOf(ShipSummary summary){
        StringBuilder sb = new StringBuilder();
        Map<String, Integer> count = summary.shipPerPorts();
        for(String port: count.keySet()){
            lineOf(port, sb, count);
        }
        return sb.toString();
    }

    private static StringBuilder lineOf(String port, StringBuilder sb, Map<String, Integer> count) {
        return sb.append(port).append(" : ").append(count.get(port)).append("\n");
    }
}
