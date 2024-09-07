package com.koralix.commons.observable;

public class PipeSubscription<T, R> extends Subscription<R> {
    private final Subscription<T> parent;

    public PipeSubscription(Observable<R> observable, Observer<R> observer, Subscription<T> parent) {
        super(observable, observer);

        this.parent = parent;
    }

    @Override
    public void unsubscribe() {
        super.unsubscribe();
        parent.unsubscribe();
    }
}
