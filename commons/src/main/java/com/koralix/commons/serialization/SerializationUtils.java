package com.koralix.commons.serialization;

import com.koralix.commons.value.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;

public final class SerializationUtils {

    private static final Map<Class<?>, Map<Class<?>, Serializer<?, ?>>> serializers = load();

    @SuppressWarnings("unchecked")
    public static <S, D> Optional<Serializer<S, D>> get(Class<S> source, Class<D> destination) {
        return Optional.ofNullable((Serializer<S, D>) serializers.getOrDefault(source, Map.of()).get(destination));
    }

    public static <S, D> Optional<ValueSerializer<S, D>> value(Class<S> source, Value<D> value) {
        return get(source, value.getType()).map(serializer -> ValueSerializer.wrap(serializer, value));
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
