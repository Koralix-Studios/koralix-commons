package com.koralix.commons.scheduler;

import java.util.PriorityQueue;
import java.util.function.Consumer;

public class DefaultScheduler implements Scheduler {
    private final PriorityQueue<SchedulerTaskLauncher<?, ?>> launchers = new PriorityQueue<>();
    private long ticks = 0;

    public TickSchedulerTask onceAfter(long after, Consumer<TickSchedulerTask> action) {
        return onceAt(ticks + after, action);
    }

    public TickSchedulerTask everyAfter(long after, long period, Consumer<TickSchedulerTask> action) {
        return everyAt(ticks + after, period, action);
    }

    public TickSchedulerTask onceAt(long at, Consumer<TickSchedulerTask> action) {
        TickSchedulerTask task = new TickSchedulerTask(this, action);
        task.launch(new TickSchedulerTaskLauncher(task, at, null));
        return task;
    }

    public TickSchedulerTask everyAt(long at, long period, Consumer<TickSchedulerTask> action) {
        TickSchedulerTask task = new TickSchedulerTask(this, action);
        task.launch(new TickSchedulerTaskLauncher(task, at, t -> t + period));
        return task;
    }

    @Override
    public void schedule(SchedulerTaskLauncher<?, ?> launcher) {
        launchers.add(launcher);
    }

    @Override
    public void cancel(SchedulerTaskLauncher<?, ?> launcher) {
        launchers.remove(launcher);
    }

    @Override
    public void tick() {
        while (!launchers.isEmpty()) {
            SchedulerTaskLauncher<?, ?> launcher = launchers.peek();
            if (!launcher.launch()) break;
            launchers.poll();
        }

        ++ticks;
    }

    @Override
    public long ticks() {
        return ticks;
    }
}
