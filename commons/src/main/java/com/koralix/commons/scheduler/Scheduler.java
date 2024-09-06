package com.koralix.commons.scheduler;

public interface Scheduler {
    void schedule(SchedulerTaskLauncher<?, ?> launcher);

    void cancel(SchedulerTaskLauncher<?, ?> launcher);

    void tick();

    long ticks();
}
