package com.koralix.commons;

import com.koralix.commons.event.Event;
import com.koralix.commons.event.EventFactory;
import com.koralix.commons.event.EventResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EventTests {
    @FunctionalInterface
    public interface Foo {
        void onFoo(boolean foo);
    }

    @FunctionalInterface
    public interface SyncBar {
        EventResult<Boolean> onBar(boolean bar);
    }

    @Test
    public void simple() {
        int[] counter = {0};
        Event<Foo> event = EventFactory.simple();

        event.register(foo -> ++counter[0]);
        event.invoker().onFoo(true);
        Assertions.assertEquals(1, counter[0]);

        event.register(foo -> ++counter[0]);
        event.invoker().onFoo(true);
        Assertions.assertEquals(3, counter[0]);
    }

    @Test
    public void result() {
        int[] counter = {0};
        Event<SyncBar> event = EventFactory.result();

        event.register(bar -> {
            if (bar) ++counter[0];
            return EventResult.interrupt(bar);
        });
        EventResult<Boolean> result = event.invoker().onBar(true);
        Assertions.assertTrue(result.interrupts());
        Assertions.assertTrue(result.value());
        Assertions.assertEquals(1, counter[0]);

        event.register(bar -> {
            if (bar) ++counter[0];
            return EventResult.interrupt(bar);
        });
        result = event.invoker().onBar(true);
        Assertions.assertTrue(result.interrupts());
        Assertions.assertTrue(result.value());
        Assertions.assertEquals(2, counter[0]);

        result = event.invoker().onBar(false);
        Assertions.assertTrue(result.interrupts());
        Assertions.assertFalse(result.value());
        Assertions.assertEquals(2, counter[0]);
    }
}
