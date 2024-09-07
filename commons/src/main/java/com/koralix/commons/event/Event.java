package com.koralix.commons.event;

public interface Event<T> {
    T invoker();

    void register(T listener);

    void unregister(T listener);

    boolean contains(T listener);

    void clear();
}
