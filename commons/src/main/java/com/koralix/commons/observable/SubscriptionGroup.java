package com.koralix.commons.observable;

public class SubscriptionGroup<T> extends Subscription<T> {
    private final Subscription<?>[] dependencies;

    public SubscriptionGroup(
            Observable<T> observable,
            Subscription<T> subscription,
            Subscription<?>... dependencies
    ) {
        super(observable, subscription.observer);
        this.dependencies = dependencies;
    }

    @Override
    public void unsubscribe() {
        super.unsubscribe();
        for (Subscription<?> dependency : dependencies) {
            dependency.unsubscribe();
        }
    }
}
