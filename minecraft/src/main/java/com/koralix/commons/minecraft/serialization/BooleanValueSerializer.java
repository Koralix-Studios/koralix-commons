package com.koralix.commons.minecraft.serialization;

import com.google.auto.service.AutoService;
import com.koralix.commons.serialization.Serializer;
import com.koralix.commons.value.BooleanValue;
import net.minecraft.network.FriendlyByteBuf;

@SuppressWarnings("rawtypes")
@AutoService(Serializer.class)
public class BooleanValueSerializer extends FriendlyByteBufSerializer<Boolean> {

    public BooleanValueSerializer() {
        super(BooleanValue.class, FriendlyByteBuf::writeBoolean, FriendlyByteBuf::readBoolean);
    }

}
