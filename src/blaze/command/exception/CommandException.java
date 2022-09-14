package src.blaze.command.exception;

public class CommandException extends Exception {

    public CommandException() {
        super("Command Exception");
    }

    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Throwable e) {
        super(message, e);
    }

    public CommandException(Throwable e) {
        super(e);
    }
}
