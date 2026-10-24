package software.ulpgc.katas.io.ports;

import software.ulpgc.katas.model.Ship;

public interface ShipDeserializer {
    Ship deserialize(String line, int lineNumber);

}
