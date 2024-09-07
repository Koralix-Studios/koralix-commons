package com.koralix.commons.observable;

public class Subscription<T> {
    private final Observable<T> observable;
    private final Observer<T> observer;

    public Subscription(Observable<T> observable, Observer<T> observer) {
        this.observable = observable;
        this.observer = observer;
    }

    public void unsubscribe() {
        observable.unsubscribe(observer);
    }
}
