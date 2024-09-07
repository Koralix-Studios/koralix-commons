package com.koralix.commons.observable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class BaseObservable<T> implements Observable<T> {
    protected final List<Observer<T>> subscribers;

    public BaseObservable() {
        subscribers = new ArrayList<>();
    }

    @Override
    public Subscription<T> subscribe(Observer<T> observer) {
        subscribers.add(observer);
        return new Subscription<>(this, observer);
    }

    @Override
    public <R> Observable<R> pipe(Function<Observable<T>, Observable<R>> mapper) {
        return mapper.apply(this);
    }

    @Override
    public void unsubscribe(Observer<T> observer) {
        subscribers.remove(observer);
    }
}
