package com.koralix.commons.serialization;

import com.koralix.commons.value.Value;

import java.lang.reflect.Constructor;

public class ValueSerializer<S, D> implements Serializer<S, Value<D>> {

    private final Serializer<S, D> serializer;
    private final Class<? extends Value<D>> valueClass;
    private final Class<D> deserializedClass;
    private final Constructor<? extends Value<D>> constructor;

    private ValueSerializer(Serializer<S, D> serializer, Class<? extends Value<D>> valueClass) {
        this.serializer = serializer;
        this.valueClass = valueClass;
        this.deserializedClass = serializer.getDeserializedClass();
        try {
            constructor = valueClass.getConstructor(Class.class, Object.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static <S, D> ValueSerializer<S, D> wrap(Serializer<S, D> serializer, Class<? extends Value<D>> valueClass) {
        return new ValueSerializer<>(serializer, valueClass);
    }

    @SuppressWarnings("unchecked")
    public static <S, D> ValueSerializer<S, D> wrap(Serializer<S, D> serializer, Value<D> value) {
        return wrap(serializer, (Class<? extends Value<D>>) value.getClass());
    }

    @Override
    public S create() {
        return serializer.create();
    }

    @Override
    public void serialize(S s, Value<D> value) {
        serializer.serialize(s, value.get());
    }

    @Override
    public Value<D> deserialize(S s) {
        Value<D> value = createValue(null);
        deserialize(s, value);
        return value;
    }

    public void deserialize(S s, Value<D> value) {
        value.set(serializer.deserialize(s));
    }

    @Override
    public Class<S> getSerializedClass() {
        return serializer.getSerializedClass();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<Value<D>> getDeserializedClass() {
        return (Class<Value<D>>) valueClass;
    }

    private Value<D> createValue(D value) {
        try {
            return constructor.newInstance(deserializedClass, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
