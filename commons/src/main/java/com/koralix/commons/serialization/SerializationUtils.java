package com.koralix.commons.serialization;

import com.koralix.commons.value.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;

/**
 * A utility class for serialization.
 * @see Serializer
 */
public final class SerializationUtils {

    private static final Map<Class<?>, Map<Class<?>, Serializer<?, ?>>> serializers = load();

    /**
     * Returns a serializer for the specified source and destination classes if available.
     * @param source the source class
     * @param destination the destination class
     * @param <S> the source type
     * @param <D> the destination type
     * @return the serializer
     */
    @SuppressWarnings("unchecked")
    public static <S, D> Optional<Serializer<S, D>> get(Class<S> source, Class<D> destination) {
        return Optional.ofNullable((Serializer<S, D>) serializers.getOrDefault(source, Map.of()).get(destination));
    }

    /**
     * Returns a serializer for the specified source and destination value type if available.
     * @param source the source class
     * @param valueClass the value class
     * @param valueType the value type
     * @param <S> the source type
     * @param <D> the destination type
     * @return the value serializer
     * @see ValueSerializer
     */
    public static <S, D> Optional<ValueSerializer<S, D>> value(Class<S> source, Class<? extends Value<D>> valueClass, Class<D> valueType) {
        return get(source, valueType).map(serializer -> ValueSerializer.wrap(serializer, valueClass));
    }

    private static Map<Class<?>, Map<Class<?>, Serializer<?, ?>>> load() {
        Map<Class<?>, Map<Class<?>, Serializer<?, ?>>> serializers = new HashMap<>();
        for (Serializer<?, ?> serializer : ServiceLoader.load(Serializer.class)) {
            Class<?> serializedClass = serializer.getSerializedClass();
            Class<?> deserializedClass = serializer.getDeserializedClass();
            serializers.computeIfAbsent(serializedClass, k -> new HashMap<>()).put(deserializedClass, serializer);
        }
        return Map.copyOf(serializers);
    }

    private SerializationUtils() {
    }

}
