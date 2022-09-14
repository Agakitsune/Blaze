package src.blaze.command.element;

public class ElementException extends Exception {

    public ElementException() {
        super("Element Exception");
    }

    public ElementException(String message) {
        super(message);
    }

    public ElementException(String message, Throwable e) {
        super(message, e);
    }

    public ElementException(Throwable e) {
        super(e);
    }

    public static <T, T2, T3> void throwCastError(Class<T> origin, Class<T2> from, Class<T3> to) throws ElementException {
        throw new ElementException("Invalid cast for " + origin.getSimpleName() + ": expected " + to.getName() + " but got " + from.getName());
    }

    public static <T> void throwStateError(Class<T> origin, String got, String... expect) throws ElementException {
        String message = "Invalid cast for " + origin.getSimpleName() + ": ";
        if (expect.length > 0) {
            message += "expected ";
            if (expect.length == 1) {
                message += expect[0];
                message += " ";
            } else {
                for (int i = 0; i < expect.length; i++) {
                    message += expect[i];
                    if (i < expect.length - 2)
                        message += ", ";
                    if (i == expect.length - 2)
                        message += " or ";
                }
                message += " ";
            }
            message += "but ";
        }
        message += "got " + got;
        throw new ElementException(message);
    }
}
