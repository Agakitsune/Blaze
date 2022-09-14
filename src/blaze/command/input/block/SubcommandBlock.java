package src.blaze.command.input.block;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import src.blaze.command.exception.CommandException;
import src.blaze.command.input.Input;
import src.blaze.command.input.Token;
import src.blaze.command.input.node.SubcommandNode;
import src.blaze.utils.JsonHelper;
import src.blaze.utils.exception.JsonException;

import java.util.ArrayList;

public class SubcommandBlock extends Block {

    private String subcommandName;
    private ArrayList<Block> body = new ArrayList<>();

    public SubcommandBlock() {}

    public SubcommandBlock(String name) {
        subcommandName = name;
    }

    @Override
    public BlockResponse execute(Input input, int shift) throws CommandException {
        int shiftCopy = shift;
        BlockResponse response;
        SubcommandNode subcmd;
        int index = 0;
        JsonObject data;
        boolean looped = false;

        Token token = input.getToken(shiftCopy);
        if (!token.value().equals(subcommandName))
            return new BlockResponse(BlockResponse.ERROR_CODE, "Unknow subcommand '" + token.value() + "' at " + token.position(), null);
        subcmd = new SubcommandNode(token.position(),
                token.position() + input.getToken(input.getTokensNumber() - 1).value().length(),
                token.value());
        shiftCopy++;
        while (index < body.size()) {
            response = body.get(index).execute(input, shiftCopy);
            if (response.getCode().equals(BlockResponse.ERROR_CODE)) {
                if (!looped)
                    return new BlockResponse(BlockResponse.ERROR_CODE, response.getMessage(), null);
                looped = false;
                index++;
                continue;
            }
            data = response.getData();
            shiftCopy = data.get("shift").getAsInt();
            subcmd.add(data.get("node").getAsJsonObject());
            if (shiftCopy == input.getTokensNumber())
                break;
            if (!data.get("loop").getAsBoolean()) {
                index++;
            } else {
                looped = true;
            }
        }
        if (index < body.size() - 1)
            return new BlockResponse(BlockResponse.ERROR_CODE,
                    "Unexpected end of Subcommand at " + (input.getToken(shiftCopy - 1).position() + input.getToken(shiftCopy - 1).value().length()),
                    null);
        return BlockResponse.makeSimpleResponse(BlockResponse.SUCCES_CODE, "", shiftCopy, subcmd.getData(), loop);
    }

    @Override
    public void fromJson(JsonObject object) {
        JsonElement body = object.get("body");
        JsonElement name = object.get("name");
        JsonObject block;

        if (body == null)
            throw new JsonException(this.getClass().getSimpleName() + ": body must be present");
        if (name == null)
            throw new JsonException(this.getClass().getSimpleName() + ": name must be present");
        if (JsonHelper.checkElement(body, JsonHelper.JSONType.ARRAY)) {
            JsonArray array = body.getAsJsonArray();
            for (JsonElement elt: array) {
                if (JsonHelper.checkElement(elt, JsonHelper.JSONType.OBJECT)) {
                    block = elt.getAsJsonObject();
                    this.body.add(Block.constructFromJson(block));
                } else {
                    throw new JsonException(this.getClass().getSimpleName() + ": block inside body must be an Object");
                }
            }
        } else {
            throw new JsonException(this.getClass().getSimpleName() + ": body must be an Array");
        }
        if (JsonHelper.checkElement(name, JsonHelper.JSONType.STRING)) {
            this.subcommandName = name.getAsString();
        } else {
            throw new JsonException(this.getClass().getSimpleName() + ": name must be a String");
        }
    }

    public SubcommandBlock addBlock(Block block) {
        this.body.add(block);
        return this;
    }
}
