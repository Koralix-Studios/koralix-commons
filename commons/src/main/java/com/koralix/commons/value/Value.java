package com.koralix.commons.value;

import com.koralix.commons.serialization.SerializationUtils;
import com.koralix.commons.serialization.ValueSerializer;

import java.lang.reflect.Proxy;
import java.util.Optional;

/**
 * A Value is the core of the value system, which provides a way to observe and change values.
 * It represents a value that can be observed and changed.
 * @param <T> the value type
 * @see ValueGroup
 */
public interface Value<T> {

    /**
     * Getter for the value.
     * @return the value
     */
    T get();

    /**
     * Setter for the value.
     * @param value the value
     */
    void set(T value);

    /**
     * Getter for the value type.
     * @return the value type
     */
    Class<T> getType();

    /**
     * Creates a new {@link WrapperValue} with the specified type and value.
     * @param type the value type
     * @param value the value
     * @param <T> the value type
     * @return the new {@link WrapperValue}
     * @see WrapperValue
     */
    static <T> Value<T> of(Class<T> type, T value) {
        return new WrapperValue<>(type, value);
    }

    /**
     * Creates a new {@link AtomicWrapperValue} with the specified type and value.
     * @param type the value type
     * @param value the value
     * @param <T> the value type
     * @return the new {@link AtomicWrapperValue}
     * @see AtomicWrapperValue
     */
    static <T> Value<T> atomic(Class<T> type, T value) {
        return new AtomicWrapperValue<>(type, value);
    }

    /**
     * Makes the specified value observable.
     * @param value the value
     * @param listener the listener
     * @return the observable value
     * @param <T> the value type
     */
    @SuppressWarnings("unchecked")
    static <T> Value<T> on(Value<T> value, Listener<T> listener) {
        return (Value<T>) Proxy.newProxyInstance(
                Value.class.getClassLoader(),
                new Class<?>[] { Value.class },
                new ValueProxy<>(value, listener));
    }

    /**
     * Returns a value serializer for the specified source.
     * @param source the source type
     * @param <S> the source type
     * @return the value serializer
     * @see ValueSerializer
     */
    @SuppressWarnings("unchecked")
    default <S> Optional<ValueSerializer<S, T>> serializer(Class<S> source) {
        return SerializationUtils.value(source, (Class<? extends Value<T>>) getClass(), getType());
    }

    /**
     * A listener for value changes.
     * @param <T> the value type
     */
    @FunctionalInterface
    interface Listener<T> {

        /**
         * Called when the value changes.
         * @param value the value
         * @param oldValue the old value
         * @param newValue the new value
         */
        void onValueChanged(Value<T> value, T oldValue, T newValue);

    }

}
