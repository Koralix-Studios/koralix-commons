package com.koralix.commons.observable;

import java.util.function.Function;

public interface Observable<T> {
    Subscription<T> subscribe(Observer<T> observer);
    <R> Observable<R> pipe(Function<Observable<T>, Observable<R>> mapper);
    void unsubscribe(Observer<T> observer);
}
