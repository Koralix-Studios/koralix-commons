package com.koralix.commons.serialization;

import com.koralix.commons.value.Value;

import java.util.function.BiConsumer;
import java.util.function.Function;

public abstract class AbstractValueSerializer<S, D> implements Serializer<S, Value<D>> {

    private final Class<? extends Value<D>> valueClass;
    private final BiConsumer<S, D> serializer;
    private final Function<S, D> deserializer;

    protected AbstractValueSerializer(Class<? extends Value<D>> valueClass, BiConsumer<S, D> serializer, Function<S, D> deserializer) {
        this.valueClass = valueClass;
        this.serializer = serializer;
        this.deserializer = deserializer;
    }

    @Override
    public void serialize(S s, Value<D> value) {
        serializer.accept(s, value.get());
    }

    @Override
    public Value<D> deserialize(S s) {
        return Value.of(valueClass, deserializer.apply(s));
    }

    @Override
    public void deserialize(S s, Value<D> value) {
        value.set(deserializer.apply(s));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<Value<D>> getDeserializedClass() {
        return (Class<Value<D>>) valueClass;
    }
}
