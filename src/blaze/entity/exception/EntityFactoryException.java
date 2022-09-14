package src.blaze.entity.exception;

public class EntityFactoryException extends EntityException {

    public EntityFactoryException() {
        super("Entity Factory Exception");
    }

    public EntityFactoryException(String message) {
        super(message);
    }

    public EntityFactoryException(String message, Throwable e) {
        super(message, e);
    }
}
