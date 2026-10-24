package software.ulpgc.katas.io.adapters;

import software.ulpgc.katas.io.ports.SummarySerializer;
import software.ulpgc.katas.model.ShipSummary;

import java.util.stream.Collectors;

public record TextSummarySerializer() implements SummarySerializer {
    @Override
    public String serialize(ShipSummary shipSummary) {
        return shipSummary.shipsPerPort().entrySet().stream()
                .map(p -> p.getKey() + ": " + p.getValue() + "\n")
                .collect(Collectors.joining());
    }
}
