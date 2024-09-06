package com.koralix.commons;

import com.koralix.commons.scheduler.DefaultScheduler;
import com.koralix.commons.scheduler.TickSchedulerTask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SchedulerTests {
    @Test
    public void test() {
        DefaultScheduler scheduler = new DefaultScheduler();
        scheduler.tick();

        int[] triggers = {0};

        TickSchedulerTask task = scheduler.everyAfter(10, 10, t -> ++triggers[0]);

        for (int i = 0; i < 10; ++i) {
            scheduler.tick();
        }

        Assertions.assertEquals(0, triggers[0]);
        scheduler.tick();
        Assertions.assertEquals(1, triggers[0]);

        for (int i = 0; i < 9; ++i) {
            scheduler.tick();
        }

        Assertions.assertEquals(1, triggers[0]);
        scheduler.tick();
        Assertions.assertEquals(2, triggers[0]);
    }
}
