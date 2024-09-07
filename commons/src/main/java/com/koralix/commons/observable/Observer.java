package com.koralix.commons.observable;

public interface Observer<T> {
    void next(T value);
}
