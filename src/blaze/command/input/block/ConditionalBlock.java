package src.blaze.command.input.block;

import com.google.gson.JsonObject;
import src.blaze.command.exception.CommandException;
import src.blaze.command.input.Input;

import java.util.ArrayList;

public class ConditionalBlock extends Block {

    private final ArrayList<SubcommandBlock> conditions = new ArrayList<>();

    public ConditionalBlock addConditions(SubcommandBlock block) {
        conditions.add(block);
        return this;
    }

    @Override
    public BlockResponse execute(Input input, int shift) throws CommandException {
        BlockResponse tmp;
        BlockResponse response = null;

        if (conditions.size() == 0)
            throw new CommandException("Cannot execute " + this.getClass().getSimpleName() + " without conditions");
        for (SubcommandBlock block : conditions) {
            tmp = block.execute(input, shift);
            if (tmp.getCode().equals(BlockResponse.SUCCES_CODE)) {
                if (response != null)
                    throw new CommandException("Ambiguous condition");
                response = tmp;
            }
        }
        if (response.getCode().equals(BlockResponse.SUCCES_CODE))
            return response;
        else
            return new BlockResponse(BlockResponse.ERROR_CODE, response.getMessage(), null);
    }

    @Override
    public void fromJson(JsonObject object) {

    }
}
