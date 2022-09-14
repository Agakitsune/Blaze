package src.blaze.entity.state.exception;

public class StateException extends Exception {

    public StateException() {
        super();
    }

    public StateException(String message) {
        super(message);
    }

    public StateException(String message, Throwable e) {
        super(message, e);
    }
}
