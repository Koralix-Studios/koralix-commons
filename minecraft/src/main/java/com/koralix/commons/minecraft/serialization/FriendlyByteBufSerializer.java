package com.koralix.commons.minecraft.serialization;

import com.koralix.commons.serialization.AbstractValueSerializer;
import com.koralix.commons.value.Value;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.BiConsumer;
import java.util.function.Function;

public abstract class FriendlyByteBufSerializer<T> extends AbstractValueSerializer<FriendlyByteBuf, T> {

    protected FriendlyByteBufSerializer(Class<? extends Value<T>> valueClass, BiConsumer<FriendlyByteBuf, T> serializer, Function<FriendlyByteBuf, T> deserializer) {
        super(valueClass, serializer, deserializer);
    }

    @Override
    public FriendlyByteBuf create() {
        return new FriendlyByteBuf(Unpooled.buffer());
    }

    @Override
    public Class<FriendlyByteBuf> getSerializedClass() {
        return FriendlyByteBuf.class;
    }
}
