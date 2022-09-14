package src.blaze.command;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import src.blaze.command.input.node.CommandNode;

public class Command {

    private final CommandNode node;

    public Command(CommandNode node) {
        this.node = node;
    }

    public String getCommandName() {
        return node.getData().get("name").getAsString();
    }
    public JsonArray getCommandBody() {
        return node.getData().get("body").getAsJsonArray();
    }

    public JsonObject getNode(int index) {
        return (JsonObject) node.getData().get("body").getAsJsonArray().get(index);
    }
}
