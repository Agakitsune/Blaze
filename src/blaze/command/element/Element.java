package src.blaze.command.element;

import src.blaze.data.Data;

import java.util.ArrayList;

public abstract class Element<T> {

    private final Class<T> elementClass;
    private final boolean isTemplate;
    protected T value;

    public Element(T value, boolean isTemplate) {
        this.elementClass = (Class<T>) value.getClass();
        this.value = value;
        this.isTemplate = isTemplate;
    }

    public abstract boolean checkInput(String input) throws ElementException;

    public T getValue() throws ElementException {
        if (isTemplate)
            throw new ElementException("Cannot get value of Template Element " + this.getClass().getSimpleName());
        return this.value;
    }

    public void setValue(T value) throws ElementException {
        if (isTemplate)
            throw new ElementException("Cannot set value of Template Element " + this.getClass().getSimpleName());
        this.value = value;
    }

    public Class<T> getElementClass() {
        return this.elementClass;
    }
}
