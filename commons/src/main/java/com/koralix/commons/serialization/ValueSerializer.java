package com.koralix.commons.serialization;

import com.koralix.commons.value.Value;

import java.lang.reflect.Constructor;

/**
 * A serializer wrapper for values.
 * @param <S> the source type
 * @param <D> the destination type
 * @see Serializer
 * @see Value
 */
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

    /**
     * Wraps the specified serializer with the specified value class.
     * @param serializer the serializer
     * @param valueClass the value class
     * @param <S> the source type
     * @param <D> the destination type
     * @return the value serializer
     */
    public static <S, D> ValueSerializer<S, D> wrap(Serializer<S, D> serializer, Class<? extends Value<D>> valueClass) {
        return new ValueSerializer<>(serializer, valueClass);
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

    /**
     * Deserializes the source object to the destination object.
     * @param s the source object
     * @param value the destination object
     */
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
