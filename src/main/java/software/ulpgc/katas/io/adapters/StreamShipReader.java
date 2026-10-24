package software.ulpgc.katas.io.adapters;

import software.ulpgc.katas.io.ShipLoadException;
import software.ulpgc.katas.io.ports.ShipDeserializer;
import software.ulpgc.katas.io.ports.ShipReader;
import software.ulpgc.katas.model.Ship;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.IntStream;

public record StreamShipReader(InputStream input, ShipDeserializer deserializer) implements ShipReader {

    @Override
    public List<Ship> load() {
        try(input){
            return loadFrom(input);
        }catch (IOException e){
            throw new ShipLoadException("The data source could not be read", e);
        }
    }

    private List<Ship> loadFrom(InputStream input) throws IOException {
        return loadFrom(new InputStreamReader(input, StandardCharsets.UTF_8));
    }

    private List<Ship> loadFrom(Reader reader) throws IOException {
        return loadFrom(reader.readAllLines());
    }
    private List<Ship> loadFrom(List<String> lines) throws IOException {
        return IntStream.range(1, lines.size()).mapToObj(i -> deserializer.deserialize(lines.get(i), i+1))
                .toList();
    }
}
