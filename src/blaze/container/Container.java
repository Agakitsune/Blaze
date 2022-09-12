package src.blaze.container;

import src.blaze.container.exception.ContainerException;
import src.blaze.data.Data;

import java.util.*;

public class Container {

    private HashMap<String, Data<?>> data = new HashMap<>();
    private HashMap<String, String> namespaces = new HashMap<>();

    public Container() {}

    public <T extends Object> T get(String path) {
        List<String> space = new ArrayList<>();
        Collections.addAll(space, path.split(":"));

        if (space.size() > 1) {
            if (space.size() > 2) {
                throw new ContainerException("Too many Namespace");
            }
            String namespace = space.remove(0);
            String full_space = namespaces.get(namespace);
            if (full_space == null) {
                throw new ContainerException("Namespace '" + namespace + "' doesn't exist");
            }
            path = full_space + "/" + space.get(0);
        }

        List<String> split = new ArrayList<>();
        Collections.addAll(split, path.split("/"));
        String key = split.remove(split.size() - 1);

        Data<T> result = getContainer(split).getData(key);
        if (result != null) {
            return (T) result.get();
        }
        throw new ContainerException("The Key '" + key + "' wasn't found into the Container");
    }

    public <T extends Object> void set(String path, T value) {
        List<String> space = new ArrayList<>();
        Collections.addAll(space, path.split(":"));

        if (space.size() > 1) {
            if (space.size() > 2) {
                throw new ContainerException("Too many Namespace");
            }
            String namespace = space.remove(0);
            String full_space = namespaces.get(namespace);
            if (full_space == null) {
                throw new ContainerException("Namespace '" + namespace + "' doesn't exist");
            }
            path = full_space + "/" + space.get(0);
        }

        List<String> split = new ArrayList<>();
        Collections.addAll(split, path.split("/"));
        String key = split.remove(split.size() - 1);

        Container instance = getContainer(split, true);
        instance.setData(key, new Data<>(value));
    }

    public void newNamespace(String name, String space) {
        if (namespaces.get(name) != null) {
            throw new ContainerException("Namespace '" + name + "' already exist");
        }
        if (space.endsWith("/")) {
            space = space.substring(0, space.length() - 2);
        }
        namespaces.put(name, space);
    }

    private <T extends Object> Data<T> getData(String key) {
        return (Data<T>) data.get(key);
    }

    private <T extends Object> void setData(String key, Data<T> value) {
        data.put(key, value);
    }

    private Container getContainer(List<String> list) {
        return getContainer(list, false);
    }
    private Container getContainer(List<String> list, boolean creation) {
        Data<Container> tmp = null;
        Container instance = this;

        for (String key : list ) {
            tmp = instance.getData(key);
            if (tmp == null) {
                if (!creation) {
                    throw new ContainerException("The Key '" + key + "' wasn't found into the Container");
                }
                tmp = new Data<>(new Container());
                instance.setData(key, tmp);
            }
            instance = tmp.get();
        }
        return instance;
    }
}

