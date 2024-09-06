package com.koralix.commons.scheduler;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface SchedulerTaskLauncher
        <T extends SchedulerTask<T>, L extends SchedulerTaskLauncher<T, L>>
        extends Comparable<L> {
    @NotNull
    T task();

    @NotNull
    Optional<L> next();

    boolean ready();

    default boolean launch() {
        if (!ready()) return false;

        T task = task();

        task.run();
        next().ifPresent(task::launch);

        return true;
    }
}
