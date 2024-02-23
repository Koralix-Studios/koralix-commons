package com.koralix.commons.concurrent;

import com.koralix.commons.value.Value;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;

public abstract class AtomicValue<T> implements Value<T> {

    private final AtomicReference<T> value;

    public AtomicValue(T value) {
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
