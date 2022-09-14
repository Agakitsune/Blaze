package src.blaze.command.input;

public class SyntaxException extends RuntimeException {

    public SyntaxException(String message) {
        super(message);
    }

    public static void unexpectedCharacter(String expected, String got) {
        throw new SyntaxException("Unexpected Character: expected " + expected + " but got " + got);
    }
}
