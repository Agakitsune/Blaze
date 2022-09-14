package src.blaze.command.input.block;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import src.blaze.command.exception.CommandException;
import src.blaze.command.input.Input;
import src.blaze.utils.JsonHelper;
import src.blaze.utils.exception.JsonException;

import java.lang.reflect.InvocationTargetException;

public abstract class Block {

    protected Boolean loop = false;

    public Block() {

    }

    public abstract BlockResponse execute(Input input, int shift) throws CommandException;

    public Block setLoop(boolean loop) {
        this.loop = loop;
        return this;
    }

    public abstract void fromJson(JsonObject object);
    public static Block constructFromJson(JsonObject object) {
        JsonElement type = object.get("type");
        JsonElement loop = object.get("loop");
        String typeName;
        String className;

        if (type == null)
            throw new JsonException("Block: type must be present");
        if (JsonHelper.checkElement(type, JsonHelper.JSONType.STRING)) {
            typeName = type.getAsString() + "Block";
            className = Block.class.getPackageName() + "." + typeName;
        } else if (JsonHelper.checkElement(type, JsonHelper.JSONType.OBJECT)) {
            JsonObject blockObject = type.getAsJsonObject();
            JsonElement name = blockObject.get("name");
            JsonElement packageName = blockObject.get("package");
            if (name == null)
                throw new JsonException("Block: name must be present");
            if (JsonHelper.checkElement(name, JsonHelper.JSONType.STRING)) {
                typeName = name.getAsString() + "Block";
            } else {
                throw new JsonException("Block: name must be a String");
            }
            if (packageName == null) {
                className = Block.class.getPackageName() + "." + typeName;
            } else {
                if (JsonHelper.checkElement(packageName, JsonHelper.JSONType.STRING)) {
                    className = packageName.getAsString() + "." + typeName;
                } else {
                    throw new JsonException("Block: packageName must be a String");
                }
            }
        } else {
            throw new JsonException("Block: type must be a String or an Object");
        }
        try {
            Class<?> literalClass = Class.forName(className);
            if (Block.class.isAssignableFrom(literalClass)) {
                Block block = (Block) literalClass.getConstructor().newInstance();
                block.fromJson(object);
                if (loop != null) {
                    if (JsonHelper.checkElement(loop, JsonHelper.JSONType.BOOLEAN)) {
                        block.setLoop(loop.getAsBoolean());
                    }
                }
                return block;
            }
            else
                throw new LiteralException(className + " is not a subclass of Block");
        } catch (InstantiationException e) {
            throw new LiteralException("Failed to instantiate Block of class " + typeName);
        } catch (IllegalAccessException e) {
            throw new LiteralException(typeName + "doesn't have a default public Constructor");
        } catch (InvocationTargetException e) {
            throw new LiteralException("Failed to invoke constructor of class " + typeName + ", Reason: " + e.getMessage());
        } catch (NoSuchMethodException e) {
            throw new LiteralException(typeName + "doesn't have a default Constructor");
        } catch (ClassNotFoundException e) {
            throw new LiteralException(typeName + ": No Block found with that name");
        }
    }
}
