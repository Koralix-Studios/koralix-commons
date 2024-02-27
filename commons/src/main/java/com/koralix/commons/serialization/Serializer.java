package com.koralix.commons.serialization;

/**
 * A serializer for a specific source and destination type.
 * @param <S> the source type
 * @param <D> the destination type
 */
public interface Serializer<S, D> {

    /**
     * Creates a new instance of the source type.
     * @return the new instance
     */
    S create();

    /**
     * Serializes the destination object to a new source object.
     * @param d the destination object
     * @return the serialized source object
     */
    default S serialize(D d) {
        S s = create();
        serialize(s, d);
        return s;
    }

    /**
     * Serializes the destination object to the source object.
     * @param s the source object
     * @param d the destination object
     */
    void serialize(S s, D d);

    /**
     * Deserializes the source object to a new destination object.
     * @param s the source object
     * @return the deserialized destination object
     */
    D deserialize(S s);

    /**
     * Gets the class of the serialized object.
     * @return the serialized class
     */
    Class<S> getSerializedClass();

    /**
     * Gets the class of the deserialized object.
     * @return the deserialized class
     */
    Class<D> getDeserializedClass();

}
