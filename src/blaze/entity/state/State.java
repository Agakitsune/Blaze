package src.blaze.entity.state;

import src.blaze.entity.state.exception.StateException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class State<T> {

    private Class<T> stateClass;
    private T state;
    private ArrayList<T> states;

    public State(T... states) throws StateException {
        if (states.length == 0)
            throw new StateException("State must have at least one value");
        this.stateClass = (Class<T>) states[0].getClass();
        this.state = states[0];
        this.states = new ArrayList<T>(List.of(states));
    }

    public State<T> useState(T value) throws StateException {
        if (!this.states.contains(value))
            throw new StateException("Value is not in the State possible values");
        this.state = value;
        return this;
    }

    public T getCurrentState() {
        return this.state;
    }

    public T getState(int index) {
        return this.states.get(index);
    }

    public ArrayList<T> getStates() {
        return this.states;
    }

    public Class<T> getStateClass() {
        return this.stateClass;
    }

    public boolean contain(T value) { return states.contains(value); }
}
