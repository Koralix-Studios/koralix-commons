package com.koralix.commons.value;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A ValueGroup is a group of values that can be observed and changed.
 * @see Value
 * @see ValueGroup#observe(Value)
 * @see ValueGroup#addListener(Value, Value.Listener)
 * @see ValueGroup#removeListener(Value, Value.Listener)
 */
public abstract class ValueGroup {

    private final Map<Value<?>, Set<Value.Listener<?>>> listeners = new HashMap<>();

    /**
     * Adds a listener to the specified value.
     * @param value the value
     * @param listener the listener
     * @param <T> the value type
     */
    public <T> void addListener(Value<T> value, Value.Listener<T> listener) {
        listeners.computeIfAbsent(value, k -> new HashSet<>()).add(listener);
    }

    /**
     * Removes a listener from the specified value.
     * @param value the value
     * @param listener the listener
     * @param <T> the value type
     */
    public <T> void removeListener(Value<T> value, Value.Listener<T> listener) {
        listeners.getOrDefault(value, Set.of()).remove(listener);
    }

    /**
     * Makes the specified value observable.
     * @param value the value
     * @param <T> the value type
     * @return the observable value
     */
    protected <T> Value<T> observe(Value<T> value) {
        return Value.on(value, this::onValueChanged);
    }

    @SuppressWarnings("unchecked")
    private <T> void onValueChanged(Value<T> value, T oldValue, T newValue) {
        listeners
                .getOrDefault(value, Set.of())
                .forEach(listener -> ((Value.Listener<T>) listener).onValueChanged(value, oldValue, newValue));
    }

}
