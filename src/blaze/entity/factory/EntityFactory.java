package src.blaze.entity.factory;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import src.blaze.entity.Entity;
import src.blaze.entity.exception.EntityFactoryException;
import src.blaze.utils.JsonHelper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class EntityFactory<T extends Entity> {

    private Constructor<T> builder;
    private Class<T> factoryClass;
    public EntityFactory(Class<T> object) {
        try {
            builder = object.getConstructor(JsonObject.class);
            factoryClass = object;
        } catch (NoSuchMethodException e) {
            throw new EntityFactoryException(e.getMessage(), e);
        }
    }

    public T newEntity(String path) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        JsonObject object = JsonHelper.deserialize(path, JsonObject.class);
        return builder.newInstance(object);
    }

    public T newEntity(JsonObject object) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        return builder.newInstance(object);
    }

    public Class<T> getFactoryClass() {
        return factoryClass;
    }
}
