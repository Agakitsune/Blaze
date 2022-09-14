package src.blaze.command.input.block;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import src.blaze.command.input.node.Node;

public class BlockResponse {

    public static final String SUCCES_CODE = "Success";
    public static final String ERROR_CODE = "Error";
    private final JsonObject object = new JsonObject();

    public BlockResponse(String code, String message, JsonObject data) {
        this.object.add("code", new JsonPrimitive(code));
        this.object.add("message", new JsonPrimitive(message));
        this.object.add("data", data);
    }

    public JsonObject getObject() {
        return object;
    }

    public String getCode() {
        return this.object.get("code").getAsString();
    }

    public String getMessage() {
        return this.object.get("message").getAsString();
    }

    public JsonObject getData() {
        return this.object.get("data").getAsJsonObject();
    }

    public static BlockResponse makeSimpleResponse(String code, String message, int shift, JsonObject node) {
        return makeSimpleResponse(code, message, shift, node, false);
    }

    public static BlockResponse makeSimpleResponse(String code, String message, int shift, JsonObject node, boolean loop) {
        JsonObject data = new JsonObject();
        data.addProperty("shift", shift);
        data.add("node", node);
        data.addProperty("loop", loop);
        return new BlockResponse(code, message, data);
    }
}
