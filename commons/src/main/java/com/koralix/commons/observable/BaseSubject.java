package com.koralix.commons.observable;

public class BaseSubject<T> extends BaseObservable<T> implements Subject<T> {
    private T value;

    public BaseSubject(T value) {
        this.value = value;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public void next(T value) {
        this.value = value;
        super.next(value);
    }

    @Override
    public Subscription<T> subscribe(Observer<T> observer) {
        observer.next(value);
        return super.subscribe(observer);
    }
}
