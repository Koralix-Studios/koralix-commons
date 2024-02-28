package com.koralix.commons.network;

import java.util.Optional;
import java.util.UUID;

/**
 * A NetworkEntity is responsible for publishing and observing resources.
 * @see Resource
 */
public interface NetworkEntity {

    UUID getId();

    <D> void publish(Resource<D> resource);

    <D> Optional<Resource<D>> observe(UUID id);


}
