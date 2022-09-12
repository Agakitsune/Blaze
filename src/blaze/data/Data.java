package src.blaze.data;

public class Data<T> {

    private T value;

    public Data() {}

    public Data(T value) {
        this.value = value;
    }

    public T get() {
        return this.value;
    }

    public void set(T value) {
        this.value = value;
    }

    public Class<T> getValueClass() {
        return (Class<T>) this.value.getClass();
    }
}
