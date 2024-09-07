package com.koralix.commons.observable;

public interface Subject<T> extends Observable<T> {
    static <T> Subject<T> of(T value) {
        return new BaseSubject<>(value);
    }

    T get();
    void next(T value);
}
