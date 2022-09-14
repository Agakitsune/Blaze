package src.blaze.command;

import src.blaze.command.exception.CommandException;
import src.blaze.command.input.Input;
import src.blaze.command.input.SyntaxException;
import src.blaze.command.input.Token;
import src.blaze.command.input.node.CommandNode;
import src.blaze.command.input.node.KeywordNode;
import src.blaze.command.input.node.LiteralNode;

public class CommandParser {

    public CommandParser() {

    }

    public static Command parseCommand(String str) throws CommandException {
        Input input = Input.parse(str);
        CommandTemplate template;
        Token token;

        if (input.getTokensNumber() == 0)
            throw new CommandException("Input is empty");
        token = input.getToken(0);
        if (token.type() != "Identifier")
            throw new SyntaxException("Unexpected " + token.type() + " at " + token.position());
        template = CommandRegistry.getCommand(input.getToken(0).value());
        return new Command(template.execute(input));
    }
}
