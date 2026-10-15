package software.ulpgc.katas.io;
import software.ulpgc.katas.model.Ship;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;


public record StreamShipReader(InputStream input ) implements ShipReader {

    @Override
    public List<Ship> load() {
        try{
            try(input){
                return loadFrom(input);
            }
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
    }

private List<Ship> loadFrom(InputStream in) throws IOException{
        return loadFrom(new InputStreamReader(in));
}

private List<Ship> loadFrom(Reader reader)  throws IOException{
    return loadFrom(reader.readAllLines());
}

private List<Ship> loadFrom(List<String> lines) {
        List<Ship> ships = new ArrayList<>();
        for(int lineIndex = 1; lineIndex < lines.size(); lineIndex++){
            ships.add(shipOf(lines.get(lineIndex), lineIndex + 1));
        }
        return List.copyOf(ships);
}

private Ship shipOf(String lines, int lineIndex){
        return shipOf(lines.split("\t", -1), lineIndex);
}

private Ship shipOf(String[] lines, int lineIndex){
        if(lines.length != 3){
            throw new ShipLoadException("Line " + lineIndex + ": expected 3 columns but found: " + lines.length);
        }
        return  new Ship(lines[0], lines[1], cannonsOf(lines[2], lineIndex));
}

private int cannonsOf(String number, int lineIndex) {
        try{
            return Integer.parseInt(number);
        }
        catch (NumberFormatException e){
            throw new ShipLoadException("Line " + lineIndex + ": expected a number but it was " + e);
        }
}


}
