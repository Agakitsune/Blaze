package src.blaze.command.input.node;

import com.google.gson.JsonPrimitive;

public class LiteralNode extends Node {

    public LiteralNode(int start, int end, String string) {
        super("Literal", start, end);
        this.data.add("value", new JsonPrimitive(string.substring(1, string.length() - 1)));
        this.data.add("raw", new JsonPrimitive(string));
    }

    public LiteralNode(int start, int end, Number number) {
        super("Literal", start, end);
        this.data.add("value", new JsonPrimitive(number));
        this.data.add("raw", new JsonPrimitive(number.toString()));
    }
}
