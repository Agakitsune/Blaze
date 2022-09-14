package src.blaze.command.input.node;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import src.blaze.command.input.Node;

public class CommandNode extends Node {

    public CommandNode(int start, int end, String name) {
        super("Command", start, end);
        data.addProperty("name", name);
        JsonArray body = new JsonArray();
        data.add("body", body);
    }

    public CommandNode(int start, int end, String name, Node... nodes) {
        super("Command", start, end);
        data.addProperty("name", name);
        JsonArray body = new JsonArray();
        for (Node n : nodes) {
            body.add(n.getData());
        }
        data.add("body", body);
    }

    public void add(Node node) {
        data.get("body").getAsJsonArray().add(node.getData());
    }

    public void add(JsonObject object) {
        data.get("body").getAsJsonArray().add(object);
    }
}
