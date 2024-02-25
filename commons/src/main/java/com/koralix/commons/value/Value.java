package com.koralix.commons.value;

import java.lang.reflect.Proxy;

public interface Value<T> {

    T get();

    void set(T value);

    Class<T> getType();

    static <T> Value<T> of(Class<T> type, T value) {
        return new WrapperValue<>(type, value);
    }

    static <T> Value<T> atomic(Class<T> type, T value) {
        return new AtomicWrapperValue<>(type, value);
    }

    @SuppressWarnings("unchecked")
    static <T> Value<T> reactive(Class<T> type, T value, Listener<T> listener) {
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
