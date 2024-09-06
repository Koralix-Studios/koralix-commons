package com.koralix.commons.scheduler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;

public class TickSchedulerTaskLauncher implements SchedulerTaskLauncher<TickSchedulerTask, TickSchedulerTaskLauncher> {
    private final TickSchedulerTask task;
    private final long tick;
    private final @Nullable Function<Long, Long> nextTick;

    public TickSchedulerTaskLauncher(TickSchedulerTask task, long tick, @Nullable Function<Long, Long> nextTick) {
        this.task = task;
        this.tick = tick;
        this.nextTick = nextTick;
    }

    @Override
    public @NotNull TickSchedulerTask task() {
        return task;
    }

    @Override
    public @NotNull Optional<TickSchedulerTaskLauncher> next() {
        if (nextTick == null) return Optional.empty();
        return Optional.of(new TickSchedulerTaskLauncher(task, nextTick.apply(tick), nextTick));
    }

    @Override
    public boolean ready() {
        return task.scheduler().ticks() >= tick;
    }

    @Override
    public int compareTo(@NotNull TickSchedulerTaskLauncher o) {
        return Long.compare(tick, o.tick);
    }
}
