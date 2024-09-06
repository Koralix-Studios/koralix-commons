package com.koralix.commons.scheduler;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class SchedulerTask<T extends SchedulerTask<T>> implements Runnable {
    private final @NotNull Scheduler scheduler;
    private final @NotNull Consumer<T> action;
    private SchedulerTaskLauncher<T, ?> launcher;

    protected SchedulerTask(
            @NotNull Scheduler scheduler,
            @NotNull Consumer<T> action
    ) {
        this.scheduler = scheduler;
        this.action = action;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run() {
        action.accept((T) this);
    }

    public @NotNull Scheduler scheduler() {
        return scheduler;
    }

    public void launch(SchedulerTaskLauncher<T, ?> launcher) {
        this.launcher = launcher;
        scheduler.schedule(this.launcher);
    }
}
