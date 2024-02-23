package com.koralix.commons.minecraft.serialization;

import com.google.auto.service.AutoService;
import com.koralix.commons.concurrent.AtomicBooleanValue;
import com.koralix.commons.serialization.Serializer;
import net.minecraft.network.FriendlyByteBuf;

@SuppressWarnings("rawtypes")
@AutoService(Serializer.class)
public class AtomicBooleanValueSerializer extends FriendlyByteBufSerializer<Boolean> {

    public AtomicBooleanValueSerializer() {
        super(AtomicBooleanValue.class, FriendlyByteBuf::writeBoolean, FriendlyByteBuf::readBoolean);
    }

}
