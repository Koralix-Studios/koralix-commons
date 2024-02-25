package com.koralix.commons.value;

public class WrapperValue<T> implements Value<T> {

    private final Class<T> type;
    private T value;

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
