package com.koralix.commons.value;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class ValueProxy<T> implements InvocationHandler {

    private final Value<T> value;
    private final Value.Listener<T> listener;

    ValueProxy(Value<T> value, Value.Listener<T> listener) {
        this.value = value;
        this.listener = listener;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("get")) {
            return value.get();
        } else if (method.getName().equals("set")) {
            T oldValue = value.get();
            value.set((T) args[0]);
            listener.onValueChanged((Value<T>) proxy, oldValue, value.get());
            return null;
        } else {
            return value.getClass().getMethod(method.getName(), method.getParameterTypes()).invoke(value, args);
        }
    }
}
