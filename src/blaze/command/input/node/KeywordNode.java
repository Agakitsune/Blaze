package src.blaze.command.input.node;

import com.google.gson.JsonPrimitive;

public class KeywordNode extends Node {

    public KeywordNode(int start, int end, String name) {
        super("Keyword", start, end);
        data.add("name", new JsonPrimitive(name));
    }
}
