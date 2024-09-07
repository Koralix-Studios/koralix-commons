package com.koralix.commons.event;

public final class EventResult<T> {
    private static final EventResult<?> PASS = new EventResult<>(false, null);
    private static final EventResult<?> STOP = new EventResult<>(true, null);

    @SuppressWarnings("unchecked")
    public static <T> EventResult<T> pass() {
        return (EventResult<T>) PASS;
    }

    @SuppressWarnings("unchecked")
    public static <T> EventResult<T> interrupt(T value) {
        return value == null ? (EventResult<T>) STOP : new EventResult<>(true, value);
    }

    private final boolean interrupts;
    private final T value;

    public EventResult(boolean interrupts, T value) {
        this.interrupts = interrupts;
        this.value = value;
    }

    public boolean interrupts() {
        return interrupts;
    }

    public T value() {
        return value;
    }
}
