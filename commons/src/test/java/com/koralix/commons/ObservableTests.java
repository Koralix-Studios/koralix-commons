package com.koralix.commons;

import com.koralix.commons.observable.Observable;
import com.koralix.commons.observable.Operators;
import com.koralix.commons.observable.Subject;
import com.koralix.commons.scheduler.DefaultScheduler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ObservableTests {
    @Test
    public void testFilter() {
        int[] count = {0};
        Subject<String> subject = Subject.of("Hello");
        Observable<String> filtered = subject.pipe(Operators.filter(s -> {
            ++count[0];
            return s.length() < 6;
        }));
        Assertions.assertEquals(0, count[0]);
        filtered.subscribe(value -> {
            Assertions.assertEquals("Hello", value);
            ++count[0];
        });
        Assertions.assertEquals(2, count[0]);
        subject.next("Hello, World!");
        Assertions.assertEquals(3, count[0]);
    }

    @Test
    public void testMap() {
        int[] count = {0};
        Subject<String> subject = Subject.of("Hello");
        subject.subscribe(value -> {
            Assertions.assertEquals("Hello", value);
            Assertions.assertNotEquals("HELLO", value);
            ++count[0];
        });
        Observable<String> mapped = subject.pipe(Operators.map(s -> {
            ++count[0];
            return s.toUpperCase();
        }));
        Assertions.assertEquals(1, count[0]);
        mapped.subscribe(value -> {
            Assertions.assertEquals("HELLO", value);
            Assertions.assertNotEquals("Hello", value);
            ++count[0];
        });
        Assertions.assertEquals(3, count[0]);
    }

    @Test
    public void testDebounce() {
        int[] count = {0};
        DefaultScheduler scheduler = new DefaultScheduler();

        Subject<Integer> subject = Subject.of(0);
        subject.pipe(Operators.debounce(scheduler, scheduler1 ->
            ((DefaultScheduler)scheduler1).onceAfter(1, tickSchedulerTask -> {

            })
        )).subscribe(value -> {
            ++count[0];
        });
        scheduler.tick();
        scheduler.tick();

        Assertions.assertEquals(1, count[0]);

        subject.next(5);
        scheduler.tick();
        scheduler.tick();

        Assertions.assertEquals(2, count[0]);
    }

    @Test
    public void testTickDebounce() {
        int[] count = {0};
        DefaultScheduler scheduler = new DefaultScheduler();

        Subject<Integer> subject = Subject.of(0);
        subject.pipe(Operators.debounceTick(scheduler, 0)).subscribe(value -> {
            ++count[0];
        });

        scheduler.tick();

        Assertions.assertEquals(1, count[0]);
    }
}
