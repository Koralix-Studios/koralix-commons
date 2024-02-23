package com.koralix.commons.minecraft.serialization;

import com.koralix.commons.concurrent.AtomicBooleanValue;
import com.koralix.commons.serialization.SerializationUtils;
import com.koralix.commons.value.BooleanValue;
import net.minecraft.network.FriendlyByteBuf;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SerializersTest {

    @Test
    public void test() {
        BooleanValue value = new BooleanValue(true);
        FriendlyByteBuf buf = SerializationUtils.get(FriendlyByteBuf.class, BooleanValue.class).orElseThrow().serialize(value);
        value.set(false);
        Assertions.assertFalse(value.get());
        SerializationUtils.get(FriendlyByteBuf.class, BooleanValue.class).orElseThrow().deserialize(buf, value);
        Assertions.assertTrue(value.get());

        AtomicBooleanValue atomicValue = new AtomicBooleanValue(true);
        buf = SerializationUtils.get(FriendlyByteBuf.class, AtomicBooleanValue.class).orElseThrow().serialize(atomicValue);
        Assertions.assertTrue(buf.readBoolean());
    }

}
