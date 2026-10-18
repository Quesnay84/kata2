package software.ulpgc.katas.io;

import software.ulpgc.katas.model.Ship;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.IntStream;

public record StreamShipReader(InputStream stream) implements ShipReader {


    @Override
    public List<Ship> load() {
        try(stream){
            return loadFrom(stream);
        } catch (IOException e){
            throw new  RuntimeException(e);
        }
    }

    private List<Ship> loadFrom(InputStream stream) throws IOException {
        return loadFrom(new InputStreamReader(stream, StandardCharsets.UTF_8));
    }
    private List<Ship> loadFrom(Reader reader) throws IOException {
        return loadFrom(reader.readAllLines());
    }
    private List<Ship> loadFrom(List<String> lines) {
        return IntStream.range(1, lines.size())
                .mapToObj(i -> shipOf(lines.get(i), i + 1))
                .toList();
    }

    private Ship shipOf(String shipLine, int line) {
        return shipOf(fieldsOf(shipLine), line);
    }

    private String[] fieldsOf(String shipLine) {
        return shipLine.split("\t", -1);
    }

    private Ship shipOf(String[] shipLine,  int line) {
        if(!(shipLine.length == 3)){
            throw new ShipLoadException("Line " + line + " expected 3 columns but found: " + shipLine.length);
        }
        return new Ship(shipLine[0], shipLine[1], cannonsOf(shipLine[2], line));
    }

    private int cannonsOf(String shipLine, int line) {
        try{
           return Integer.parseInt(shipLine);
        } catch(NumberFormatException e){
            throw new ShipLoadException("Cannon field not correct in line: " + line, e);
        }
    }

}
