package com.koralix.commons.value;

/**
 * A {@link Value} implementation.
 * @param <T> the value type
 * @see Value
 */
public class WrapperValue<T> implements Value<T> {

    private final Class<T> type;
    private T value;

    /**
     * Constructs a new {@link WrapperValue} with the specified type and value.
     * @param type the value type
     * @param value the value
     */
    public WrapperValue(Class<T> type, T value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public void set(T value) {
        this.value = value;
    }

    @Override
    public Class<T> getType() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        WrapperValue<?> that = (WrapperValue<?>) obj;
        return type.equals(that.type) && value.equals(that.value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
