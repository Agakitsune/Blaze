package src.blaze.command.input.node;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class SubcommandNode extends Node {

    private boolean loop = false;

    public SubcommandNode(int start, int end, String name) {
        super("Subcommand", start, end);
        data.addProperty("name", name);
        data.addProperty("loop", false);
        JsonArray body = new JsonArray();
        data.add("body", body);
    }

    public SubcommandNode(int start, int end, String name, Node... nodes) {
        super("Subcommand", start, end);
        data.addProperty("name", name);
        data.addProperty("loop", false);
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

    public void setLoop(boolean loop) {
        this.loop = loop;
        data.get("body").getAsJsonObject().addProperty("loop", loop);
    }
}
