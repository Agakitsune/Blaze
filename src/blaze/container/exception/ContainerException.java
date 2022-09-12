package src.blaze.container.exception;

public class ContainerException extends RuntimeException {

    public ContainerException() {
        super("Container Exception");
    }

    public ContainerException(String message) {
        super(message);
    }

    public ContainerException(String message, Throwable e) {
        super(message, e);
    }
}
