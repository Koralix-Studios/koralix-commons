package com.koralix.commons.value;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValueGroupTest {

    @Test
    public void test() {
        ExampleValueGroup group = new ExampleValueGroup();

        AtomicBoolean changed = new AtomicBoolean(false);

        group.addListener(group.value1, (value, oldValue, newValue) -> changed.set(true));
        group.value1.set(true);

        assertTrue(changed.get());
    }

}
