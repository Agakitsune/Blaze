package src.blaze.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import src.blaze.utils.exception.JsonException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

public class JsonHelper {

    public static final Gson GSON = new Gson();

    public static <T extends JsonElement> T deserialize(String path, Class<T> type) {
        try {
            FileInputStream file = new FileInputStream(path);
            InputStreamReader stream = new InputStreamReader(file);
            JsonReader reader = new JsonReader(stream);
            return GSON.getAdapter(type).read(reader);
        } catch (FileNotFoundException e) {
            throw new JsonException(e);
        } catch (IOException e) {
            throw new JsonException(e);
        }
    }

    public static boolean checkElement(JsonElement element, JSONType type) {
        if (element == null)
            return false;
        switch (type) {
            case OBJECT:
                return element.isJsonObject();
            case ARRAY:
                return element.isJsonArray();
            case NUMBER:
                if (element.isJsonPrimitive())
                    return element.getAsJsonPrimitive().isNumber();
                return false;
            case STRING:
                if (element.isJsonPrimitive())
                    return element.getAsJsonPrimitive().isString();
                return false;
            case BOOLEAN:
                if (element.isJsonPrimitive())
                    return element.getAsJsonPrimitive().isBoolean();
                return false;
            case NULL:
                return element.isJsonNull();
        }
        return false;
    }

    public enum JSONType {
        OBJECT,
        ARRAY,
        NUMBER,
        NULL,
        STRING,
        BOOLEAN
    }
}
