package src.blaze.engine;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import src.blaze.command.Command;
import src.blaze.container.Container;
import src.blaze.entity.Entity;
import src.blaze.entity.exception.EntityException;
import src.blaze.entity.factory.EntityFactory;
import src.blaze.entity.factory.FactoryStorage;
import src.blaze.utils.JsonHelper;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public abstract class Engine {

    private Container container = new Container();
    private EntityFactory<?> factory;

    public abstract void executeCommand(Command cmd);

    public <T extends Object> void set(String path, T value) {
        container.set(path, value);
    }

    public <T extends Object> T get(String path) {
        return container.get(path);
    }

    public <T extends Entity> Engine useFactory(EntityFactory<T> factory) {
        if (factory == null)
            throw new NullPointerException();
        this.factory = factory;
        return this;
    }

    public Engine useFactory(String factoryName) {
        EntityFactory<?> object = FactoryStorage.getFactory(factoryName);
        if (object == null)
            throw new NullPointerException();
        this.factory = object;
        return this;
    }

    public Entity create(String file) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        JsonObject object = JsonHelper.deserialize(file, JsonObject.class);
        JsonElement factoryName = object.get("factory");
        if (factoryName == null) {
            return this.factory.newEntity(object);
        } else {
            if (!JsonHelper.checkElement(factoryName, JsonHelper.JSONType.STRING))
                throw new EntityException("field 'factory' must be a String");
            EntityFactory<?> factory = FactoryStorage.getFactory(factoryName.getAsString());
            JsonElement data = object.get("data");
            if (data == null)
                throw new EntityException("field 'data' must be present with field 'factory'");
            if (!JsonHelper.checkElement(data, JsonHelper.JSONType.OBJECT))
                throw new EntityException("field 'data' must be an Object");
            return factory.newEntity(data.getAsJsonObject());
        }
    }

    public void store(String path, String file) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        set(path, create(file));
    }

    public void storeDirectory(String path) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        File repo = new File(path);
        if (!repo.isDirectory())
            throw new RuntimeException(path + " is not a directory");
        parseDirectory(repo, repo.getName());
    }

    private void parseDirectory(File folder, String path) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                parseDirectory(file, path + "/" + file.getName());
            } else {
                int fileExtension = file.getName().lastIndexOf(".");
                store(path + "/" + file.getName().substring(0, fileExtension), path + "/" + file.getName());
            }
        }
    }
}
