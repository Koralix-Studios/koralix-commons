package com.koralix.commons.observable;

public interface Subject<T> extends Observable<T> {
    T get();
    void next(T value);
}
