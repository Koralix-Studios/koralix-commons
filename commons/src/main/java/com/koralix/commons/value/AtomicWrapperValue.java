package com.koralix.commons.value;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;

public class AtomicWrapperValue<T> implements Value<T> {

    private final Class<T> type;
    private final AtomicReference<T> value;

    public AtomicWrapperValue(Class<T> type, T value) {
        this.type = type;
        this.value = new AtomicReference<>(value);
    }

    @Override
    public T get() {
        return value.get();
    }

    @Override
    public void set(T value) {
        this.value.set(value);
    }

    @Override
    public Class<T> getType() {
        return type;
    }

    public T getAndSet(T value) {
        return this.value.getAndSet(value);
    }

    public boolean compareAndSet(T expected, T value) {
        return this.value.compareAndSet(expected, value);
    }

    public T updateAndGet(UnaryOperator<T> mapper) {
        return this.value.updateAndGet(mapper);
    }

}
