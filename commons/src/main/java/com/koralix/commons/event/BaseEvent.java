package com.koralix.commons.event;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class BaseEvent<T> implements Event<T> {
    private final Function<List<T>, T> invokerFactory;
    private T invoker;
    private ArrayList<T> listeners;

    public BaseEvent(Function<List<T>, T> invokerFactory) {
        this.invokerFactory = invokerFactory;
        this.listeners = new ArrayList<>();
    }

    @Override
    public T invoker() {
        if (invoker == null) {
            update();
        }
        return invoker;
    }

    @Override
    public void register(T listener) {
        listeners.add(listener);
        update();
    }

    @Override
    public void unregister(T listener) {
        listeners.remove(listener);
        listeners.trimToSize();
        update();
    }

    @Override
    public boolean contains(T listener) {
        return listeners.contains(listener);
    }

    @Override
    public void clear() {
        listeners.clear();
        listeners.trimToSize();
        update();
    }

    private void update() {
        if (listeners.size() == 1) {
            invoker = listeners.get(0);
        } else {
            invoker = invokerFactory.apply(listeners);
        }
    }
}
