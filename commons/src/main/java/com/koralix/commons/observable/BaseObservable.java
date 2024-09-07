package com.koralix.commons.observable;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseObservable<T> implements Observable<T> {
    private final List<Observer<T>> subscribers;

    public BaseObservable() {
        subscribers = new ArrayList<>();
    }

    @Override
    public Subscription<T> subscribe(Observer<T> observer) {
        subscribers.add(observer);
        return new Subscription<>(this, observer);
    }

    @Override
    public void unsubscribe(Observer<T> observer) {
        subscribers.remove(observer);
    }

    @Override
    public void next(T value) {
        subscribers.forEach(observer -> observer.next(value));
    }

    @Override
    public <R> Observable<R> pipe(OperatorFactory<T, R> factory) {
        return factory.create(this);
    }
}
