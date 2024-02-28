package com.koralix.commons.network;

import java.util.UUID;

public abstract class AbstractResource<T> implements Resource<T> {

    private final UUID id;
    private final NetworkEntity owner;

    protected AbstractResource(UUID id, NetworkEntity owner) {
        this.id = id;
        this.owner = owner;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public NetworkEntity getOwner() {
        return owner;
    }

}
