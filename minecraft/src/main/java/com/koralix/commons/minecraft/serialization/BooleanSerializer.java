package com.koralix.commons.minecraft.serialization;

import com.google.auto.service.AutoService;
import com.koralix.commons.serialization.Serializer;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Minecraft serializer for boolean to FriendlyByteBuf
 * @see Serializer
 * @see FriendlyByteBuf
 */
@SuppressWarnings("rawtypes")
@AutoService(Serializer.class)
public class BooleanSerializer extends FriendlyByteBufSerializer<Boolean> {

    @Override
    public void serialize(FriendlyByteBuf friendlyByteBuf, Boolean aBoolean) {
        friendlyByteBuf.writeBoolean(aBoolean);
    }

    @Override
    public Boolean deserialize(FriendlyByteBuf friendlyByteBuf) {
        return friendlyByteBuf.readBoolean();
    }

    @Override
    public Class<Boolean> getDeserializedClass() {
        return Boolean.class;
    }

}
