package software.ulpgc.katas.io;
import software.ulpgc.katas.model.Ship;
import java.util.List;

public interface ShipReader {
    List<Ship> load();
}
