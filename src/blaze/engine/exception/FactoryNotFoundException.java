package src.blaze.engine.exception;

public class FactoryNotFoundException extends Exception {

    public FactoryNotFoundException() {
        super("Factory not found");
    }

    public FactoryNotFoundException(String message) {
        super(message);
    }

    public FactoryNotFoundException(String message, Throwable e) {
        super(message, e);
    }

    public FactoryNotFoundException(Throwable e) {
        super(e);
    }
}
