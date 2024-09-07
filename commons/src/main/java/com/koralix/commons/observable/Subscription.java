package com.koralix.commons.observable;

public class Subscription<T> {
    final Observable<T> observable;
    final Observer<T> observer;

    public Subscription(Observable<T> observable, Observer<T> observer) {
        this.observable = observable;
        this.observer = observer;
    }

    public void unsubscribe() {
        observable.unsubscribe(observer);
    }
}
