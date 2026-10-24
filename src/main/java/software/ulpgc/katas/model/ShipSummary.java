package software.ulpgc.katas.model;

import java.util.*;
import java.util.stream.Collectors;

public record ShipSummary(Map<String, Integer> shipsPerPort) {
    public ShipSummary{
        shipsPerPort = Collections.unmodifiableMap(new TreeMap<>(shipsPerPort));
    }

    public static ShipSummary of(List<Ship> ships) {
        return new ShipSummary(ships.stream().collect(Collectors.toMap(Ship::port, ship -> 1, Integer::sum)));
    }
}
