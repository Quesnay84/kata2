package software.ulpgc.katas.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public record ShipSummary(Map<String, Integer> shipPerPorts) {
    public ShipSummary{
        shipPerPorts = Collections.unmodifiableMap(new TreeMap<>(shipPerPorts));
    }

    public static ShipSummary of(List<Ship> ships){
        Map<String, Integer> counts = new TreeMap<>();
        for (Ship ship : ships){
            counts.put(ship.port(), counts.getOrDefault(ship.port(), 0) + 1);
        }
        return new ShipSummary(counts);
    }
}
