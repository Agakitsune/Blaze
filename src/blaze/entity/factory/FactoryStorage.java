package src.blaze.entity.factory;

import src.blaze.engine.Engine;
import src.blaze.entity.Entity;

import java.util.HashMap;

public class FactoryStorage {

    private static HashMap<String, EntityFactory<?>> factories = new HashMap<>();

    public static <T extends Entity> void newFactory(String factoryName, Class<T> factoryClass) {
        factories.put(factoryName, new EntityFactory<>(factoryClass));
    }

    public static <T extends Entity> void newFactory(String factoryName, EntityFactory<T> factory) {
        factories.put(factoryName, factory);
    }

    public static EntityFactory<?> getFactory(String factoryName) {
        EntityFactory<?> factory = factories.get(factoryName);

        if (factory == null)
            throw new RuntimeException("No Factory where stored with name '" + factoryName + "'");
        return factory;
    }
}
