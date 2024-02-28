package com.koralix.commons.network;

import com.koralix.commons.value.Value;
import com.koralix.commons.value.ValueGroup;

import java.util.Optional;

public abstract class ResourceGroup extends ValueGroup {

    private final NetworkContext context;

    protected ResourceGroup(NetworkContext context) {
        this.context = context;
    }

    @Override
    protected <T> Value<T> observe(Value<T> value) {
        if (value instanceof Resource) {
            return observe((Resource<T>) value);
        }
        return super.observe(value);
    }

    protected <T> Value<T> observe(Resource<T> resource) {
        NetworkEntity owner = resource.getOwner();

        if (context.isLocal(owner)) return super.observe(resource);
        if (!resource.canBeObservedBy(context.localEntity().getId())) {
            throw new IllegalStateException("Resource cannot be observed by the local entity");
        }

        Optional<Resource<T>> observed = owner.observe(resource.getId());
        return observed.orElseThrow(() -> new IllegalStateException("Resource cannot be observed"));
    }

    @Override
    protected <T> void onValueChanged(Value<T> value, T oldValue, T newValue) {
        if (value instanceof Resource) {
            onResourceChanged((Resource<T>) value, oldValue, newValue);
        }
        super.onValueChanged(value, oldValue, newValue);
    }

    protected <T> void onResourceChanged(Resource<T> value, T oldValue, T newValue) {
        value.getOwner().publish(value);
    }

}
