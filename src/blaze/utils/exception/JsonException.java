package src.blaze.utils.exception;

public class JsonException extends RuntimeException {

    public JsonException() {
        super("JSON Exception");
    }

    public JsonException(String message) {
        super(message);
    }

    public JsonException(String message, Throwable e) {
        super(message, e);
    }

    public JsonException(Throwable e) {
        super(e);
    }
}
