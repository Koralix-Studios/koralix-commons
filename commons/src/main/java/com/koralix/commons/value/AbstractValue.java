package com.koralix.commons.value;

public abstract class AbstractValue<T> implements Value<T> {

    private T value;

    public AbstractValue(T value) {
        this.value = value;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public void set(T value) {
        this.value = value;
    }
}
