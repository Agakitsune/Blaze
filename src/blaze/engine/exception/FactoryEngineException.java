package src.blaze.engine.exception;

public class FactoryEngineException extends Exception {

    public FactoryEngineException() {
        super("Factory Engine Exception");
    }

    public FactoryEngineException(String message) {
        super(message);
    }

    public FactoryEngineException(String message, Throwable e) {
        super(message, e);
    }

    public FactoryEngineException(Throwable e) {
        super(e);
    }
}
