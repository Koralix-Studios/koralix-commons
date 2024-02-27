package com.koralix.commons.value;

public final class ExampleValueGroup extends ValueGroup {

    public final Value<Boolean> value1 = observe(Value.of(Boolean.class, false));

}
