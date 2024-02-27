package com.koralix.commons.minecraft.serialization;

import com.koralix.commons.serialization.Serializer;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Minecraft serializer for FriendlyByteBuf
 * @param <T> the type of the object to serialize
 * @see Serializer
 * @see FriendlyByteBuf
 */
public abstract class FriendlyByteBufSerializer<T> implements Serializer<FriendlyByteBuf, T> {

    @Override
    public FriendlyByteBuf create() {
        return new FriendlyByteBuf(Unpooled.buffer());
    }

    @Override
    public Class<FriendlyByteBuf> getSerializedClass() {
        return FriendlyByteBuf.class;
    }

    @Override
    public FriendlyByteBuf serialize(T t) {
        FriendlyByteBuf buf = create();
        serialize(buf, t);
        return buf;
    }

}
