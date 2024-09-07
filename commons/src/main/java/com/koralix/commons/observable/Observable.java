package com.koralix.commons.observable;

public interface Observable<T> {
    Subscription<T> subscribe(Observer<T> observer);
    void unsubscribe(Observer<T> observer);
    void next(T value);
    <R> Observable<R> pipe(OperatorFactory<T, R> factory);
}
