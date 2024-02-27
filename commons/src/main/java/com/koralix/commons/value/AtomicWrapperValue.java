package com.koralix.commons.value;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;

/**
 * An atomic {@link WrapperValue} variant.
 * @param <T> the value type
 * @see WrapperValue
 * @see Value
 */
public class AtomicWrapperValue<T> implements Value<T> {

    private final Class<T> type;
    private final AtomicReference<T> value;

    /**
     * Constructs a new {@link AtomicWrapperValue} with the specified type and value.
     * @param type the value type
     * @param value the value
     */
    public AtomicWrapperValue(Class<T> type, T value) {
        this.type = type;
        this.value = new AtomicReference<>(value);
    }

    @Override
    public T get() {
        return value.get();
    }

    @Override
    public void set(T newValue) {
        this.value.set(newValue);
    }

    @Override
    public Class<T> getType() {
        return type;
    }

    /**
     * Atomically sets the value to newValue and returns the old value.
     * @param newValue the new value
     * @return the old value
     */
    public T getAndSet(T newValue) {
        return this.value.getAndSet(newValue);
    }

    /**
     * Atomically sets the value to the given updated value if the current value {@code ==} the expected value.
     * @param expected the expected value
     * @param newValue the new value
     * @return true if successful
     */
    public boolean compareAndSet(T expected, T newValue) {
        return this.value.compareAndSet(expected, newValue);
    }

    /**
     * Atomically updates the value with the results of applying the given function, returning the old value.
     * @param mapper the function
     * @return the old value
     */
    public T updateAndGet(UnaryOperator<T> mapper) {
        return this.value.updateAndGet(mapper);
    }

}
