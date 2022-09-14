package src.blaze.command.input.block;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import src.blaze.command.input.Input;
import src.blaze.command.input.Token;
import src.blaze.command.input.node.KeywordNode;
import src.blaze.utils.JsonHelper;
import src.blaze.utils.exception.JsonException;

public class KeywordBlock extends Block {

    private String keyword;

    public KeywordBlock() {}
    public KeywordBlock(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public BlockResponse execute(Input input, int shift) {
        Token token = input.getToken(shift);
        if (token.value().equals(keyword)) {
            KeywordNode node = new KeywordNode(token.position(), token.position() + token.value().length(), token.value());
            return BlockResponse.makeSimpleResponse(BlockResponse.SUCCES_CODE, "", shift + 1, node.getData(), this.loop);
        }
        return new BlockResponse(BlockResponse.ERROR_CODE, "Unknown keyword '" + token.value() + "' at " + token.position(), null);
    }

    @Override
    public void fromJson(JsonObject object) {
        JsonElement keyword = object.get("keyword");
        if (keyword == null)
            throw new JsonException(this.getClass().getSimpleName() + ": keyword must be present");
        if (JsonHelper.checkElement(keyword, JsonHelper.JSONType.STRING)) {
            this.keyword = keyword.getAsString();
        } else {
            throw new JsonException(this.getClass().getSimpleName() + ": keyword must be a String");
        }
    }
}
