package software.ulpgc.katas.io.ports;

import software.ulpgc.katas.model.ShipSummary;

public interface SummarySerializer {
    String serialize(ShipSummary shipSummary);
}
