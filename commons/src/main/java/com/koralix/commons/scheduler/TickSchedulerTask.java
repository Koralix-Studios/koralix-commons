package com.koralix.commons.scheduler;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class TickSchedulerTask extends SchedulerTask<TickSchedulerTask> {
    protected TickSchedulerTask(
            @NotNull Scheduler scheduler,
            @NotNull Consumer<TickSchedulerTask> action
    ) {
        super(scheduler, action);
    }
}
