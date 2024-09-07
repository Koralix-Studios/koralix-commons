package com.koralix.commons.observable;

public abstract class Operator<T, R> extends BaseObservable<R> {
    private final Observable<T> parent;

    public Operator(final Observable<T> parent) {
        this.parent = parent;
    }

    @Override
    public Subscription<R> subscribe(Observer<R> observer) {;
        return new SubscriptionGroup<>(
                this,
                super.subscribe(observer),
                parent.subscribe(value -> next(map(value)))
        );
    }

    protected abstract R map(T value);
}
