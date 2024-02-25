package com.koralix.commons.value;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class ValueGroup {

    private final Map<Value<?>, Set<Value.Listener<?>>> listeners = new HashMap<>();

    public <T> void addListener(Value<T> value, Value.Listener<T> listener) {
        listeners.computeIfAbsent(value, k -> new HashSet<>()).add(listener);
    }

    public <T> void removeListener(Value<T> value, Value.Listener<T> listener) {
        listeners.getOrDefault(value, Set.of()).remove(listener);
    }

    protected <T> Value<T> create(Class<T> type, T value) {
        return Value.reactive(type, value, this::onValueChanged);
    }

    @SuppressWarnings("unchecked")
    private <T> void onValueChanged(Value<T> value, T oldValue, T newValue) {
        listeners
                .getOrDefault(value, Set.of())
                .forEach(listener -> ((Value.Listener<T>) listener).onValueChanged(value, oldValue, newValue));
    }

}
