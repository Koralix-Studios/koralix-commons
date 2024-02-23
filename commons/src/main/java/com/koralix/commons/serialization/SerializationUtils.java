package com.koralix.commons.serialization;

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
