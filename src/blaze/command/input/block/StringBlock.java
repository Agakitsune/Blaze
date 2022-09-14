package src.blaze.command.input.block;

import com.google.gson.JsonObject;
import src.blaze.command.exception.CommandException;
import src.blaze.command.input.Input;
import src.blaze.command.input.Token;
import src.blaze.command.input.node.LiteralNode;

public class StringBlock extends LiteralBlock {
    @Override
    public BlockResponse execute(Input input, int shift) throws CommandException {
        Token token = input.getToken(shift);
        if (token.type().equals("String")) {
            LiteralNode node = new LiteralNode(token.position(), token.position() + token.value().length(), token.value());
            return BlockResponse.makeSimpleResponse(BlockResponse.SUCCES_CODE, "", shift + 1, node.getData());
        }
        return new BlockResponse(BlockResponse.ERROR_CODE, "Unexpected " + token.type() + " at " + token.position() + ": expected a String", null);
    }

    @Override
    public void fromJson(JsonObject object) {

    }
}
