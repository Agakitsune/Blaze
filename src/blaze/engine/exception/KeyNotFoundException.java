package src.blaze.engine.exception;

public class KeyNotFoundException extends RuntimeException {

    public KeyNotFoundException() {
        super("Key not found in Engine");
    }

    public KeyNotFoundException(String message) {
        super(message);
    }

    public KeyNotFoundException(String message, Throwable e) {
        super(message, e);
    }

    public KeyNotFoundException(Throwable e) {
        super(e);
    }
}
