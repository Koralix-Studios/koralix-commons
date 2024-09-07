package com.koralix.commons.observable;

@FunctionalInterface
public interface OperatorFactory<T, R> {
    Operator<T, R> create(Observable<T> observable);
}
