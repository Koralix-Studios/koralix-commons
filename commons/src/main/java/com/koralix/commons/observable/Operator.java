package com.koralix.commons.observable;

public abstract class Operator<T, R> implements Observable<R> {
    private final Observable<T> parent;

    public Operator(final Observable<T> parent) {
        this.parent = parent;
    }

    public abstract R apply(final T t);

    @Override
    public Subscription<R> subscribe(Observer<R> observer) {
        Subscription<T> subscription = parent.subscribe(value -> observer.next(apply(value)));

        return new PipeSubscription<>(this, observer, subscription);
    }
}
