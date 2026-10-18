package software.ulpgc.katas.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record ShipSummary(Map<String, Integer> shipsPerPort) {

    public ShipSummary{
        shipsPerPort = Collections.unmodifiableMap(new HashMap<>(shipsPerPort));
    }
    public static ShipSummary of(List<Ship> ships){
        Map<String, Integer> counts = new HashMap<>();
        ships.forEach(ship -> counts.put(ship.port(), counts.getOrDefault(ship.port(), 0) + 1));
        return new ShipSummary(counts);
    }
}
