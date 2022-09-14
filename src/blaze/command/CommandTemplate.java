package src.blaze.command;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import src.blaze.command.exception.CommandException;
import src.blaze.command.input.Input;
import src.blaze.command.input.SyntaxException;
import src.blaze.command.input.Token;
import src.blaze.command.input.block.Block;
import src.blaze.command.input.block.BlockResponse;
import src.blaze.command.input.node.CommandNode;
import src.blaze.entity.exception.EntityException;
import src.blaze.entity.factory.EntityFactory;
import src.blaze.entity.factory.FactoryStorage;
import src.blaze.utils.JsonHelper;
import src.blaze.utils.exception.JsonException;

import java.util.ArrayList;

public class CommandTemplate {

    private String commandName;
    private ArrayList<Block> body = new ArrayList<>();

    public CommandTemplate() {
        commandName = "NO_NAME";
    }

    public CommandTemplate(String name) {
        commandName = name;
    }

    public String getCommandName() {
        return commandName;
    }

    public CommandTemplate addBlock(Block block) {
        this.body.add(block);
        return this;
    }

    public CommandNode execute(Input input) throws CommandException {
        int shift = 0;
        BlockResponse response;
        CommandNode cmd;
        int index = 0;
        JsonObject data;
        boolean looped = false;

        Token token = input.getToken(shift);
        cmd = new CommandNode(token.position(),
                token.position() + input.getToken(input.getTokensNumber() - 1).value().length(),
                token.value());
        shift++;
        while (index < body.size()) {
            response = body.get(index).execute(input, shift);
            if (response.getCode().equals(BlockResponse.ERROR_CODE)) {
                if (!looped)
                    throw new SyntaxException(response.getMessage());
                looped = false;
                index++;
                continue;
            }
            data = response.getData();
            shift = data.get("shift").getAsInt();
            cmd.add(data.get("node").getAsJsonObject());
            if (shift == input.getTokensNumber())
                break;
            if (!data.get("loop").getAsBoolean()) {
                index++;
            } else {
                looped = true;
            }
        }
        if (index < body.size() - 1)
            throw new SyntaxException("Unexpected end of Command at " + (input.getToken(shift - 1).position() + input.getToken(shift - 1).value().length()));
        if (shift < input.getTokensNumber())
            throw new SyntaxException("Unexpected " + input.getToken(shift).type() + " '" + input.getToken(shift).value() + "' at " + input.getToken(shift).position());
        return cmd;
    }

    public void useFile(String file) {
        JsonObject object = JsonHelper.deserialize(file, JsonObject.class);
        JsonElement body = object.get("body");
        JsonObject block;

        if (body == null) {
            throw new JsonException(this.getClass().getSimpleName() + ": body must be present");
        } else {
            if (!JsonHelper.checkElement(body, JsonHelper.JSONType.ARRAY))
                throw new JsonException(this.getClass().getSimpleName() + ": body must be an Array");
            JsonArray array = body.getAsJsonArray();
            for (JsonElement elt: array) {
                if (JsonHelper.checkElement(elt, JsonHelper.JSONType.OBJECT)) {
                    block = elt.getAsJsonObject();
                    this.body.add(Block.constructFromJson(block));
                } else {
                    throw new JsonException(this.getClass().getSimpleName() + ": block inside condtions must be an Object");
                }
            }
        }
    }
}
