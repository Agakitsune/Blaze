package src.blaze.command.input.node;

import com.google.gson.JsonObject;

public class Node {

    protected JsonObject data = new JsonObject();

    public Node(String type, int start, int end) {
        data.addProperty("type", type);
        data.addProperty("start", start);
        data.addProperty("end", end);
    }

    public JsonObject getData() {
        return data;
    }
}
