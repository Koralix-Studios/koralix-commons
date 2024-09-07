package com.koralix.commons.observable;

import com.koralix.commons.scheduler.*;

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

    public static <T> OperatorFactory<T, T> debounce(Scheduler scheduler, Function<Scheduler, SchedulerTask<?>> factory) {
        return observable -> new Operator<>(observable) {
            private SchedulerTask<?> currentTask;

            @Override
            protected T map(T value) {
                return value;
            }

            @Override
            public void next(T value) {
                if (currentTask != null) {
                    currentTask.cancel();
                }

                currentTask = factory.apply(scheduler).andThen(() -> super.next(value));
            }
        };
    }

    public static <T> OperatorFactory<T, T> debounceTick(DefaultScheduler scheduler, int ticks) {
        return debounce(scheduler, scheduler1 ->
            ((DefaultScheduler)scheduler1).onceAfter(ticks, tickSchedulerTask -> { })
        );
    }
}
