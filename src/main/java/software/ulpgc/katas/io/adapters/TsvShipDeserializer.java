package software.ulpgc.katas.io.adapters;

import software.ulpgc.katas.io.ShipLoadException;
import software.ulpgc.katas.io.ports.ShipDeserializer;
import software.ulpgc.katas.model.Ship;

public record TsvShipDeserializer() implements ShipDeserializer {
    @Override
    public Ship deserialize(String shipLine, int lineNumber) {
        return deserialize(filedOf(shipLine),  lineNumber);

    }

    private String[] filedOf(String shipLine) {
        return shipLine.split("\t", -1);
    }

    private Ship deserialize(String[] shipLine, int lineNumber) {
        if(shipLine.length != 3) throw new ShipLoadException("Line " + lineNumber + " expected 3 columns but got: " + shipLine.length);
        return new Ship(shipLine[0], shipLine[1], cannonOf(shipLine[2], lineNumber));
    }

    private int cannonOf(String cannon, int lineNumber) {
        try{
            return Integer.parseInt(cannon);
        }catch(NumberFormatException e){
            throw new ShipLoadException("Line " + lineNumber+" Cannon " + cannon + " is not a number", e);
        }
    }
}
