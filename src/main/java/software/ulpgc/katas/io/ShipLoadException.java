package software.ulpgc.katas.io;

import java.io.IOException;

public class ShipLoadException extends Exception {
    public ShipLoadException(String s) {
        super(s);
    }
    public ShipLoadException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
