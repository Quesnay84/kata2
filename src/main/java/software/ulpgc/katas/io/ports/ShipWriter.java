package software.ulpgc.katas.io.ports;

import software.ulpgc.katas.model.Ship;
import software.ulpgc.katas.model.ShipSummary;

public interface ShipWriter {
    void write(ShipSummary summary);
}
