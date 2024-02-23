package com.koralix.commons.value;

import java.lang.reflect.Proxy;

public interface Value<T> {

    T get();

    void set(T value);

    static <T, V extends Value<T>> V of(Class<V> type, T value) {
        try {
            return type.getConstructor(value.getClass()).newInstance(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    static <T, V extends Value<T>> Value<T> reactive(Class<V> type, T value, Listener<T> listener) {
        return (Value<T>) Proxy.newProxyInstance(
                Value.class.getClassLoader(),
                new Class<?>[] { Value.class },
                new ValueProxy<>(Value.of(type, value), listener));
    }

    @FunctionalInterface
    interface Listener<T> {
        void onValueChanged(Value<T> value, T oldValue, T newValue);
    }

}
