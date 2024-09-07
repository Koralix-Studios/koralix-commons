package com.koralix.commons.event;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public final class EventFactory {
    private EventFactory() {
    }

    public static <T> Event<T> of(Function<List<T>, T> invokerFactory) {
        return new BaseEvent<>(invokerFactory);
    }

    @SuppressWarnings("unchecked")
    private static <T, R> R invokeMethod(T listener, Method method, Object[] args) throws Throwable {
        return (R) MethodHandles.lookup().unreflect(method).bindTo(listener).invokeWithArguments(args);
    }

    @SafeVarargs
    @SuppressWarnings("unchecked")
    public static <T> Event<T> simple(T... typeGetter) {
        if (typeGetter.length != 0) throw new IllegalStateException("array must be empty!");
        return simple((Class<T>) typeGetter.getClass().getComponentType());
    }

    @SuppressWarnings("unchecked")
    public static <T> Event<T> simple(Class<T> type) {
        return of(listeners -> (T) Proxy.newProxyInstance(
                EventFactory.class.getClassLoader(),
                new Class<?>[]{type},
                (proxy, method, args) -> {
                    for (T listener : listeners) {
                        invokeMethod(listener, method, args);
                    }
                    return null;
                }));
    }

    @SafeVarargs
    @SuppressWarnings("unchecked")
    public static <T> Event<T> result(T... typeGetter) {
        if (typeGetter.length != 0) throw new IllegalStateException("array must be empty!");
        return result((Class<T>) typeGetter.getClass().getComponentType());
    }

    @SuppressWarnings("unchecked")
    public static <T> Event<T> result(Class<T> type) {
        return of(listeners -> (T) Proxy.newProxyInstance(
                EventFactory.class.getClassLoader(),
                new Class<?>[]{type},
                (proxy, method, args) -> {
                    for (T listener : listeners) {
                        EventResult<?> result = Objects.requireNonNull(invokeMethod(listener, method, args));
                        if (result.interrupts()) return result;
                    }
                    return EventResult.pass();
                }));
    }
}
