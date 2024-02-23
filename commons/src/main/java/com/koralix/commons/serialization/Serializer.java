package com.koralix.commons.serialization;

public interface Serializer<S, D> {

    S create();

    default S serialize(D d) {
        S s = create();
        serialize(s, d);
        return s;
    }

    void serialize(S s, D d);

    D deserialize(S s);

    void deserialize(S s, D d);

    Class<S> getSerializedClass();

    Class<D> getDeserializedClass();

}
