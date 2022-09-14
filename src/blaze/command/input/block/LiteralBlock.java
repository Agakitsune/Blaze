package src.blaze.command.input.block;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import src.blaze.command.exception.CommandException;
import src.blaze.command.input.Input;
import src.blaze.utils.JsonHelper;
import src.blaze.utils.exception.JsonException;

import java.lang.reflect.InvocationTargetException;

public class LiteralBlock extends Block {

    private Block block;

    public LiteralBlock() {}

    public LiteralBlock(String name) {
        this(name, null);
    }

    public LiteralBlock(String name, String packageName) {
        String blockName = name + "Block";
        String className;

        if (packageName == null)
            className = LiteralBlock.class.getPackageName() + "." + blockName;
        else
            className = packageName + "." + blockName;
        try {
            Class<?> literalClass = Class.forName(className);
            if (LiteralBlock.class.isAssignableFrom(literalClass))
                this.block = (Block) literalClass.getConstructor().newInstance();
            else
                throw new LiteralException(className + " is not a subclass of LiteralBlock");
        } catch (InstantiationException e) {
            throw new LiteralException("Failed to instantiate Block of class " + blockName);
        } catch (IllegalAccessException e) {
            throw new LiteralException(blockName + "doesn't have a default public Constructor");
        } catch (InvocationTargetException e) {
            throw new LiteralException("Failed to invoke constructor of class " + blockName + ", Reason: " + e.getMessage());
        } catch (NoSuchMethodException e) {
            throw new LiteralException(blockName + "doesn't have a default Constructor");
        } catch (ClassNotFoundException e) {
            throw new LiteralException(blockName + ": No Block found with that name");
        }
    }
    @Override
    public BlockResponse execute(Input input, int shift) throws CommandException {
        return block.execute(input, shift);
    }

    @Override
    public void fromJson(JsonObject object) {
        JsonElement block = object.get("block");
        String blockName;
        String className;

        if (block == null)
            throw new JsonException(this.getClass().getSimpleName() + ": block must be present");
        if (JsonHelper.checkElement(block, JsonHelper.JSONType.STRING)) {
            blockName = block.getAsString() + "Block";
            className = LiteralBlock.class.getPackageName() + "." + blockName;
        } else if (JsonHelper.checkElement(block, JsonHelper.JSONType.OBJECT)) {
            JsonObject blockObject = block.getAsJsonObject();
            JsonElement name = blockObject.get("name");
            JsonElement packageName = blockObject.get("package");
            if (name == null)
                throw new JsonException(this.getClass().getSimpleName() + ": name must be present");
            if (JsonHelper.checkElement(name, JsonHelper.JSONType.STRING)) {
                blockName = name.getAsString() + "Block";
            } else {
                throw new JsonException(this.getClass().getSimpleName() + ": name must be a String");
            }
            if (packageName == null) {
                className = LiteralBlock.class.getPackageName() + "." + blockName;
            } else {
                if (JsonHelper.checkElement(packageName, JsonHelper.JSONType.STRING)) {
                    className = packageName.getAsString() + "." + blockName;
                } else {
                    throw new JsonException(this.getClass().getSimpleName() + ": packageName must be a String");
                }
            }
        } else {
            throw new JsonException(this.getClass().getSimpleName() + ": block must be a String or an Object");
        }
        try {
            Class<?> literalClass = Class.forName(className);
            if (LiteralBlock.class.isAssignableFrom(literalClass))
                this.block = (Block) literalClass.getConstructor().newInstance();
            else
                throw new LiteralException(className + " is not a subclass of LiteralBlock");
        } catch (InstantiationException e) {
            throw new LiteralException("Failed to instantiate Block of class " + blockName);
        } catch (IllegalAccessException e) {
            throw new LiteralException(blockName + "doesn't have a default public Constructor");
        } catch (InvocationTargetException e) {
            throw new LiteralException("Failed to invoke constructor of class " + blockName + ", Reason: " + e.getMessage());
        } catch (NoSuchMethodException e) {
            throw new LiteralException(blockName + "doesn't have a default Constructor");
        } catch (ClassNotFoundException e) {
            throw new LiteralException(blockName + ": No Block found with that name");
        }
    }
}

class LiteralException extends RuntimeException {

    public LiteralException(String message) {
        super(message);
    }
}
