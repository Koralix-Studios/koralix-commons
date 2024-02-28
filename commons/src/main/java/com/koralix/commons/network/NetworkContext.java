package com.koralix.commons.network;

import java.util.UUID;

public interface NetworkContext {

    NetworkEntity getEntity(UUID id);

    NetworkEntity localEntity();

    default boolean isLocal(UUID id) {
        return localEntity().getId().equals(id);
    }

    default boolean isLocal(NetworkEntity entity) {
        return isLocal(entity.getId());
    }

}
