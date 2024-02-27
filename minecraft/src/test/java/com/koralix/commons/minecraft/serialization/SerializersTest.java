package com.koralix.commons.minecraft.serialization;

import com.koralix.commons.serialization.SerializationUtils;
import com.koralix.commons.serialization.ValueSerializer;
import com.koralix.commons.value.Value;
import net.minecraft.network.FriendlyByteBuf;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SerializersTest {

    @Test
    public void test() {
        Value<Boolean> value = Value.of(Boolean.class, true);
        ValueSerializer<FriendlyByteBuf, Boolean> serializer = value.serializer(FriendlyByteBuf.class).orElseThrow();
        FriendlyByteBuf buf = serializer.serialize(value);
        value.set(false);
        Assertions.assertFalse(value.get());
        serializer.deserialize(buf, value);
        Assertions.assertTrue(value.get());
    }

    @Test
    public void testAtomic() {
        Value<Boolean> value = Value.atomic(Boolean.class, true);
        ValueSerializer<FriendlyByteBuf, Boolean> serializer = value.serializer(FriendlyByteBuf.class).orElseThrow();
        FriendlyByteBuf buf = serializer.serialize(value);
        value.set(false);
        Assertions.assertFalse(value.get());
        value = serializer.deserialize(buf);
        Assertions.assertTrue(value.get());
    }

}
