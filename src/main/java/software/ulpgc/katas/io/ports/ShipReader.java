package software.ulpgc.katas.io.ports;

import software.ulpgc.katas.model.Ship;

import java.util.List;

public interface ShipReader {
    List<Ship> load();
}
