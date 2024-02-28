package com.koralix.commons.network;

import com.koralix.commons.value.Value;

import java.util.UUID;

/**
 * A Resource is a distributed object that can be observed and changed.
 * @param <T> the resource type
 */
public interface Resource<T> extends Value<T> {

    /**
     * Getter for the resource identifier
     * @return the unique identifier of this resource
     */
    UUID getId();

    /**
     * Getter for the NetworkEntity that owns this resource
     * @return the owner of this resource
     */
    NetworkEntity getOwner();

    /**
     * Checks if the resource can be observed by the given identifier.
     * @param id the identifier to check
     * @return true if the resource can be observed by the given identifier
     */
    boolean canBeObservedBy(UUID id);

    /**
     * Checks if the resource can be changed by the given identifier.
     * @param id the identifier to check
     * @return true if the resource can be changed by the given identifier
     */
    boolean canBeChangedBy(UUID id);

}
