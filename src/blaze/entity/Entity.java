package src.blaze.entity;

import src.blaze.entity.exception.EntityException;
import src.blaze.entity.state.State;
import src.blaze.entity.state.exception.StateException;

import java.lang.reflect.InvocationTargetException;

public interface Entity {


    default Object getField(String name) throws NoSuchFieldException, IllegalAccessException {
        return this.getClass().getField(name).get(this);
    }

    default Entity setField(String name, Object value) throws NoSuchFieldException, IllegalAccessException {
        this.getClass().getField(name).set(this, value);
        return this;
    }

    default Entity invoke(String name, Object... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        this.getClass().getMethod(name, args.getClass()).invoke(this, args);
        return this;
    }

    default <T extends Object> Entity useState(String stateName, T value) throws NoSuchFieldException, IllegalAccessException, StateException {
        Object object = getField(stateName);
        if (object.getClass().getName() != State.class.getName())
            throw new EntityException("Field '" + stateName + "' is not a State");
        State<T> state = (State<T>) getField(stateName);
        if (state.getStateClass().getName() != value.getClass().getName())
            throw new EntityException("Invalid Type for State of type " + state.getStateClass().getName() + " (Got type " + value.getClass().getName() + ")");
        state.useState(value);
        return this;
    }
}
