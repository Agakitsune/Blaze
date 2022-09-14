package src.blaze.command.input.block;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import src.blaze.command.exception.CommandException;
import src.blaze.command.input.Input;
import src.blaze.utils.JsonHelper;
import src.blaze.utils.exception.JsonException;

import java.util.ArrayList;

public class ConditionalBlock extends Block {

    private final ArrayList<Block> conditions = new ArrayList<>();

    public ConditionalBlock addConditions(Block block) {
        conditions.add(block);
        return this;
    }

    @Override
    public BlockResponse execute(Input input, int shift) throws CommandException {
        BlockResponse tmp;
        BlockResponse response = null;

        if (conditions.size() == 0)
            throw new CommandException("Cannot execute " + this.getClass().getSimpleName() + " without conditions");
        for (Block block : conditions) {
            tmp = block.execute(input, shift);
            if (tmp.getCode().equals(BlockResponse.SUCCES_CODE)) {
                if (response != null)
                    throw new CommandException("Ambiguous condition");
                response = tmp;
            }
        }
        if (response == null)
            return new BlockResponse(BlockResponse.ERROR_CODE, "No condition were met", null);
        if (response.getCode().equals(BlockResponse.SUCCES_CODE))
            return response;
        else
            return new BlockResponse(BlockResponse.ERROR_CODE, "No condition were met", null);
    }

    @Override
    public void fromJson(JsonObject object) {
        JsonElement condtions = object.get("conditions");
        JsonElement loop = object.get("loop");
        JsonObject block;

        if (condtions == null)
            throw new JsonException(this.getClass().getSimpleName() + ": condtions must be present");
        if (loop != null) {
            if (JsonHelper.checkElement(loop, JsonHelper.JSONType.BOOLEAN)) {
                //this.keyword = keyword.getAsString();
            } else {
                throw new JsonException(this.getClass().getSimpleName() + ": loop must be a Boolean");
            }
        }
        if (JsonHelper.checkElement(condtions, JsonHelper.JSONType.ARRAY)) {
            JsonArray array = condtions.getAsJsonArray();
            for (JsonElement elt: array) {
                if (JsonHelper.checkElement(elt, JsonHelper.JSONType.OBJECT)) {
                    block = elt.getAsJsonObject();
                    this.conditions.add(Block.constructFromJson(block));
                } else {
                    throw new JsonException(this.getClass().getSimpleName() + ": block inside condtions must be an Object");
                }
            }
        } else {
            throw new JsonException(this.getClass().getSimpleName() + ": condtions must be an Array");
        }
    }
}
