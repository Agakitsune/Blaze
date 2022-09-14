package src.blaze.entity.exception;

public class EntityInvalidJsonException extends EntityException {

    public EntityInvalidJsonException() {
        super("Invalid JSON");
    }

    public EntityInvalidJsonException(String message) {
        super(message);
    }

    public EntityInvalidJsonException(String message, Throwable e) {
        super(message, e);
    }
}
