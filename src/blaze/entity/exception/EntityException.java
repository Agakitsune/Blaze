package src.blaze.entity.exception;

public class EntityException extends RuntimeException {

    public EntityException() {
        super("Entity Exception");
    }

    public EntityException(String message) {
        super(message);
    }

    public EntityException(String message, Throwable e) {
        super(message, e);
    }
}
