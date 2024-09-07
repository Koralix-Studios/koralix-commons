package com.koralix.commons.observable;

import java.util.function.Function;
import java.util.function.Predicate;

public final class Operators {
    private Operators() {}

    public static <T> OperatorFactory<T, T> filter(Predicate<T> predicate) {
        return observable -> new Operator<>(observable) {
            @Override
            protected T map(T value) {
                return value;
            }

            @Override
            public void next(T value) {
                if (predicate.test(value)) super.next(value);
            }
        };
    }

    public static <T, R> OperatorFactory<T, R> map(Function<T, R> mapper) {
        return observable -> new Operator<>(observable) {
            @Override
            protected R map(T value) {
                return mapper.apply(value);
            }
        };
    }
}
